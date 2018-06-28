package densityMethods;


import java.util.ArrayList;
import java.util.List;
import structures.Data;
import structures.InstancePoint;
import structures.SparseMatrix;

/**
 *
 * @author aurea
 */
public class SplattingWithNN{

  
    Integer height;
    Integer width;
    Integer fieldSize;
    Float sigma;
    List<List<Float>> gaussianField;
    List<Float> splatValues;
    float maxX;
    float maxY;
    float minX;
    float minY;
    Float maxSplatValue;
    float radiusNN;
    List<List<Float>> colorMap;
    SparseMatrix sparseMatrix;
    

    public SplattingWithNN(List<InstancePoint> listInstancePoints, Integer width, Integer height, Integer fieldSize, Float sigma, float maxX, float maxY, float minX, float minY, float radiusNN) {
       
        this.height = height;
        this.width = width;
        this.fieldSize = fieldSize;
        this.sigma = sigma;
        this.maxX = maxX;
        this.maxY = maxY;
        this.minX = minX;
        this.minY = minY;
        this.radiusNN = (float)Math.sqrt(radiusNN);//radiusNN;//(float)Math.pow(radiusNN,2);
        this.maxSplatValue = 0.0f;
        this.splatValues = new ArrayList<Float>();
        this.gaussianField = new ArrayList<List<Float>>();
        this.colorMap = new ArrayList<List<Float>>(width);
        for (Integer i = 0; i < width; i++) {
            colorMap.add(i, new ArrayList<Float>(height));
            for (Integer j = 0; j < height; j++) {
                colorMap.get(i).add(j, 1.f);
            }
        }
        this.sparseMatrix= this.createSparseMatrix(listInstancePoints,width, height, maxX, maxY, minX, minY );

    }
    
    
      public SparseMatrix createSparseMatrix(List<InstancePoint> listInstancePoints, Integer width, Integer height,float maxX, float maxY, float minX, float minY ) {

        long start = System.nanoTime();
        SparseMatrix sparseMatrixtmp = new SparseMatrix();
        for (int i = 0; i < listInstancePoints.size(); i++) {
            Integer x = Math.round(((listInstancePoints.get(i).getX() - minX) * (float) (width - 60) / (maxX - minX)) + 30);
            Integer y = Math.round(height - (((listInstancePoints.get(i).getY() - minY) * (float) (height - 60) / (maxY - minY)) + 30));
            sparseMatrixtmp.insert(new Data(y, x));
        }
        long end = System.nanoTime();
        System.out.println("Creacao da matriz esparza: " + (double) (end - start) / 1000000000.0 + " secs.");
        return sparseMatrixtmp;

    }

    private void calculateGaussianField() {
        gaussianField = new ArrayList<List<Float>>();
        Float gaussValue = 0.f;

        Integer y = fieldSize / 2;
        Integer x = y * -1;
        Float value00 = 1.f / (float) (Math.PI * Math.pow(sigma, 2));

        for (Integer i = 0; i < fieldSize; i++) {
            gaussianField.add(i, new ArrayList<Float>());

            x = (fieldSize / 2) * -1;
            for (Integer j = 0; j < fieldSize; j++) {
                if ((Math.pow(x, 2) + Math.pow(y, 2)) > Math.pow(fieldSize / 2,
                        2)) // dentro del circulo
                {
                    gaussianField.get(i).add(j, -1.f);

                } else {
                    // gaussValue = 1.f / (float) (2 * Math.PI * Math.pow(sigma, 2));
                    //gaussValue *= (float) Math.pow(Math.E,
                      //      -(Math.pow(x, 2) + Math.pow(y, 2))
                        //    / (2 * Math.pow(sigma, 2)));
                    gaussValue = (float) (1.f/(2.f* Math.PI*Math.pow(sigma, 2))) * (float)(Math.pow(Math.E, -(Math.pow(x, 2) + Math.pow(y, 2)) / (2.f * Math.pow(sigma, 2))));
                 gaussianField.get(i).add(j, gaussValue / value00);

                }
                x++;
            }
            y--;

        }

    }
   
   
    private void calculateSplatField(Integer column, Integer row,
            Float splatValue) {
        Integer x = column - (fieldSize / 2);
        for (Integer i = 0; i < fieldSize; i++, x++) {
            Integer y = row - (fieldSize / 2);
            for (Integer j = 0; j < fieldSize; j++, y++) {
                if (gaussianField.get(i).get(j) > 0) {
                    Float color = 1 - (splatValue * gaussianField.get(i).get(j));
                    if (x >= 0 && y >= 0 && x < width && y < height) {
                        Float currentColor = colorMap.get(x).get(y);
                        color *= currentColor;
                        colorMap.get(x).set(y, color);
                    }
                }

            }

        }

    }

    public Float euclideanDistance(Float x1, Float y1, Float x2, Float y2) {
        //return (float) Math.sqrt((Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)));
        return (float) (Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        
    }
    
      public Float euclideanDistance(Integer x1, Integer y1, Integer x2, Integer y2) {
        //return (float) Math.sqrt((Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)));
        return (float) (Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        
    }

    private Float basisFunction(Float x1, Float y1, Float x2, Float y2) // nomenclatura del
    // paper...
    {
        Float basisValue = 0.f;
        Float x_2 = x1 - x2; // x^2
        Float y_2 = y1 - y2; // y^2
        x_2 *= x_2;
        y_2 *= y_2;
        Float sigma2 = (float) Math.pow(sigma, 2); // sigma^2
        basisValue = (float) ((1.f / 2 * ((float) Math.pow(sigma, 2) * Math.PI)) * (Math.pow(Math.E, -(x_2 + y_2) / 2 * sigma2)));
        return basisValue;

    }
    
      private Float basisFunction(Integer x1, Integer y1, Integer x2, Integer y2) // nomenclatura del
    // paper...
    {
        Float basisValue = 0.f;
        Integer x_2 = x1 - x2; // x^2
        Integer y_2 = y1 - y2; // y^2
        x_2 *= x_2;
        y_2 *= y_2;
        Float sigma2 = (float) Math.pow(sigma, 2); // sigma^2
        basisValue = (float) ((1.f / 2 * ((float) Math.pow(sigma, 2) * Math.PI)) * (Math.pow(Math.E, -(x_2 + y_2) / 2 * sigma2)));
        return basisValue;

    }

    public Float nearestNeighbors(Integer pointX, Integer pointY, Integer col, Integer row, int i, int j) {
        Integer count = 0;
        Integer pointComparableX, pointComparableY;
        Float splatValue = new Float(0.f);
        for (int a = i; a >= 0; a--) {
            /*
             * Ver si esta fila aun esta dentro del radio y el y es mayor que el punto para breakearlo todo
             */
            pointComparableX = pointX;
            pointComparableY = this.sparseMatrix.getMatrix().get(a).get(0).getRow();
            // Float distance = euclideanDistance(pointX, pointY, pointComparableX, pointComparableY);
            Float distance = (float)Math.abs(pointComparableY - pointY);
            if ((distance > this.radiusNN)) {
                break;
            } else {
                /*
                 * ya estamos en el cuadrado dentro del radio aunque hay punto que no cumplen el radio
                 */
                /*
                 * esta es la comparacion con el eje de derecha en X
                 */
                Float limitX = pointX + this.radiusNN;
                for (int b = 0; b < this.sparseMatrix.getMatrix().get(a).size(); b++) {

                    if (this.sparseMatrix.getMatrix().get(a).get(b).getCol() <= limitX) {
                        pointComparableY = this.sparseMatrix.getMatrix().get(a).get(b).getRow();
                        pointComparableX = this.sparseMatrix.getMatrix().get(a).get(b).getCol();
                        distance = (euclideanDistance(pointX, pointY, pointComparableX, pointComparableY));
                        if (distance <= this.radiusNN) {

                            splatValue += this.sparseMatrix.getMatrix().get(a).get(b).getSobrepositions()* basisFunction(pointComparableX, pointComparableY, pointX, pointY);
                            
                            count++;

                        }
                    } else {
                        break;
                    }
                }
            }
        }

        for (int a = i + 1; a < this.sparseMatrix.getMatrix().size(); a++) {
            /*
             * Ver si esta fila aun esta dentro del radio y el y es mayor que el punto para breakearlo todo
             */
            pointComparableX = pointX;
            pointComparableY = this.sparseMatrix.getMatrix().get(a).get(0).getRow();
            Float distance = euclideanDistance(pointX, pointY, pointComparableX, pointComparableY);
            if ((distance > this.radiusNN)) {
                break;
            } else {
                /*
                 * ya estamos en el cuadrado dentro del radio aunque hay punto que no cumplen el radio
                 */
                /*
                 * esta es la comparacion con el eje de derecha en X
                 */
                Float limitX = pointX + this.radiusNN;
                for (int b = 0; b < this.sparseMatrix.getMatrix().get(a).size(); b++) {

                    if (this.sparseMatrix.getMatrix().get(a).get(b).getCol() <= limitX) {
                        pointComparableY = this.sparseMatrix.getMatrix().get(a).get(b).getRow();
                        pointComparableX = this.sparseMatrix.getMatrix().get(a).get(b).getCol();
                        distance = (euclideanDistance(pointX, pointY, pointComparableX, pointComparableY));
                        if (distance <= this.radiusNN) {

                            splatValue += this.sparseMatrix.getMatrix().get(a).get(b).getSobrepositions() * basisFunction(pointComparableX, pointComparableY, pointX, pointY);


                            //System.out.print("(" + pointComparableX + "," + pointComparableY + ") ");
                            count++;

                        }
                    } else {
                        break;
                    }
                }
            }
        }

        return splatValue;
    }

    private void splattingsWithNN() {


        
        float minSparseX = 0;//((minX - minX) * (float) (dim.width) / (maxX - minX));
        float minSparseY = 0;//((minY - minY) * (float) (dim.height) / (maxY - minY));
        float maxSparseX =this.width;//((maxX - minX) * (float) (dim.width) / (maxX - minX));
        float maxSparseY = this.height;//((maxY - minY) * (float) (dim.height) / (maxY - minY));

        Integer pointX, pointY;
        Integer x, y;
        Float splatValue;
        for (int i = 0; i < this.sparseMatrix.getMatrix().size(); i++) {
            for (int j = 0; j < this.sparseMatrix.getMatrix().get(i).size(); j++) {

                pointX = this.sparseMatrix.getMatrix().get(i).get(j).getCol();
                pointY = this.sparseMatrix.getMatrix().get(i).get(j).getRow();
                //x = ((pointX - minSparseX) * (float) (width - 60) / (maxSparseX - minSparseX)) + 30;
                //y = ((pointY - minSparseY) * (float) (height - 60) / (maxSparseY - minSparseY)) + 30;
                //y = height - y;
                x = pointX;
                y = pointY;

                splatValue = nearestNeighbors(pointX, pointY, x,y, i, j);
                this.sparseMatrix.getMatrix().get(i).get(j).setSplatValue(splatValue);
                if (this.maxSplatValue < splatValue) {
                    this.maxSplatValue = splatValue;
                }
            }
        }
    }
    
     private void calculateSplattingWithNN() {
        Integer x, y;
        for (int i = 0; i < this.sparseMatrix.getMatrix().size(); i++) {
            for (int j = 0; j < this.sparseMatrix.getMatrix().get(i).size(); j++) {
                x = this.sparseMatrix.getMatrix().get(i).get(j).getCol();
                y = this.sparseMatrix.getMatrix().get(i).get(j).getRow();
                
                calculateSplatField(Math.round(x), Math.round(y), this.sparseMatrix.getMatrix().get(i).get(j).getSplatValue() / this.maxSplatValue);
            }
        }

    }
     
       private void calculateSplattingWithNNThreads(Integer beginPoint, Integer endPoint) {
        Integer x, y;
    
        for (int i =beginPoint; i <= endPoint; i++) {
            for (int j = 0; j < this.sparseMatrix.getMatrix().get(i).size(); j++) {
                x = this.sparseMatrix.getMatrix().get(i).get(j).getCol();
                y = this.sparseMatrix.getMatrix().get(i).get(j).getRow();
                calculateSplatField(x, y, this.sparseMatrix.getMatrix().get(i).get(j).getSplatValue() / this.maxSplatValue);
            }
        }

    }



    public List<List<Float>> createSplattingWithNN() {

        calculateGaussianField();
        splattingsWithNN();
        //calculateSplattingWithNN();
         Runtime runtime = Runtime.getRuntime();
        int nrOfProcessors = runtime.availableProcessors();
        
        Integer numberThreads = nrOfProcessors*2;
        List<Thread> listThreads = new ArrayList<Thread>();
        Integer step = (int)Math.ceil((this.sparseMatrix.getMatrix().size() *1.0) / (numberThreads*1.0));
        
        Integer beginPoint = 0;
        Integer endPoint = 0;


        for (int i = 0; i < this.sparseMatrix.getMatrix().size(); i = endPoint + 1) {
            beginPoint = i;
            endPoint = i + step;
            if (endPoint >= this.sparseMatrix.getMatrix().size()) {
                endPoint = this.sparseMatrix.getMatrix().size() - 1;
            }

            listThreads.add(new SplattingWithNNThread(beginPoint, endPoint));
            listThreads.get(listThreads.size() - 1).start();
        }

        for (int i = 0; i < listThreads.size(); i++) {
            try {
                listThreads.get(i).join();
            } catch (InterruptedException ex) {
                System.out.print("Join interrupted\n");
            }
        }
        return colorMap;
    }
    
     class SplattingWithNNThread extends Thread {

        Integer beginPoint;
        Integer endPoint;

        public SplattingWithNNThread(Integer beginPoint, Integer endPoint) {
            this.beginPoint = beginPoint;
            this.endPoint = endPoint;
        }

        public void run() {
            calculateSplattingWithNNThreads(this.beginPoint, this.endPoint);
        }
    }
}
