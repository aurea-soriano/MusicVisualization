package densityMethods;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import structures.InstancePoint;


/**
 *
 * @author aurea
 */
public class Splatting {

    List<InstancePoint> listPoints;
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
    List<List<Float>> colorMap;
    

    public Splatting(List<InstancePoint> listPoints, Integer width, Integer height, Integer fieldSize, Float sigma, float maxX, float maxY, float minX, float minY) {
        this.listPoints = listPoints;
        this.height = height;
        this.width = width;
        this.fieldSize = fieldSize;
        this.sigma = sigma;
        this.maxX = maxX;
        this.maxY = maxY;
        this.minX = minX;
        this.minY = minY;

        this.splatValues = new ArrayList<Float>();
        this.gaussianField = new ArrayList<List<Float>>();

        this.colorMap = new ArrayList<List<Float>>(width);

        for (Integer i = 0; i < width; i++) {
            colorMap.add(i, new ArrayList<Float>(height));
            for (Integer j = 0; j < height; j++) {
                colorMap.get(i).add(j, 1.f);
            }
        }

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
                if ((Math.pow(x, 2) + Math.pow(y, 2)) > Math.pow(fieldSize / 2, 2)) // dentro del circulo
                {
                    gaussianField.get(i).add(j, -1.f);

                } else {
                    
                   // gaussValue = 1.f / (float) (2 * Math.PI * Math.pow(sigma, 2));
                    //gaussValue *= (float) Math.pow(Math.E,
                      //      -(Math.pow(x, 2) + Math.pow(y, 2))
                        //    / (2 * Math.pow(sigma, 2)));
                    gaussValue = (float) (1.f/(2.f* Math.PI*Math.pow(sigma, 2))) * (float)(Math.pow(Math.E, -(Math.pow(x, 2) + Math.pow(y, 2)) / (2.f * Math.pow(sigma, 2))));
                    //gaussianField.get(i).add(j, gaussValue / value00);
                    gaussianField.get(i).add(j, gaussValue/ value00);

                }
                x++;
            }
            y--;
        }
    }

    /**
     * calculate splatting values
     */
    private void splattings() {

        float splattingValue = 0.f;
        for (Integer i = 0; i < listPoints.size(); i++) {
            splattingValue = 0.f;
            for (Integer j = 0; j < listPoints.size(); j++) {
                splattingValue += basisFunction(listPoints.get(i).getX(), listPoints.get(i).getY(), listPoints.get(j).getX(), listPoints.get(j).getY());

            }
            splatValues.add(i, splattingValue);

        }

    }

    private void normalizeSplattings() {
        Float maxValue = Collections.max(splatValues);

        for (Integer i = 0; i < splatValues.size(); i++) {
            splatValues.set(i, splatValues.get(i) / maxValue);

        }
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
        //basisValue = (float) ((1.f / 2 * ((float) Math.pow(sigma, 2) * Math.PI)) * (Math.pow(Math.E, -(x_2 + y_2) / 2 * sigma2)));
        basisValue = (float) (1.f/(2.f* Math.PI*sigma2)) * (float)(Math.pow(Math.E, -(x_2 + y_2) / (2.f * sigma2)));
        return basisValue;
    }

    private void calculateSplatField(Integer column, Integer row, Float splatValue) {
        Integer x = column - (fieldSize / 2);
        for (Integer i = 0; i < fieldSize; i++, x++) {
            Integer y = row - (fieldSize / 2);
            for (Integer j = 0; j < fieldSize; j++, y++) {
                if (gaussianField.get(i).get(j) > 0) {
                    Float color = 1 - (splatValue * gaussianField.get(i).get(j));
                    // System.out.println(x+", "+y);
                    if (x >= 0 && y >= 0 && x < width && y < height) {
                        Float currentColor = colorMap.get(x).get(y);
                        color *= currentColor;
                        colorMap.get(x).set(y, color);
                        // graphic.setPaint(new Color(color ,color, color));
                        // graphic.drawString(".", x, y);
                    }
                }
            }
        }
    }

    private void calculateSplatFields() {

        Float x, y;
        for (int i = 0; i < listPoints.size(); i++) {
            x = ((listPoints.get(i).getX() - minX) * (float) (this.width - 60) / (maxX - minX)) + 30;
            y = ((listPoints.get(i).getY() - minY) * (float) (this.height - 60) / (maxY - minY)) + 30;
            y = this.height - y;
            calculateSplatField(Math.round(x), Math.round(y), splatValues.get(i));
        }
    }

    private void calculateSplatFields(Integer beginPoint, Integer endPoint) {

        Float x, y;
        for (int i = beginPoint; i <= endPoint; i++) {
            x = ((listPoints.get(i).getX() - minX) * (float) (this.width - 60) / (maxX - minX)) + 30;
            y = ((listPoints.get(i).getY() - minY) * (float) (this.height - 60) / (maxY - minY)) + 30;
            y = this.height - y;
            calculateSplatField(Math.round(x), Math.round(y), splatValues.get(i));
        }
    }

    private void splattingsThreads(Integer beginPoint, Integer endPoint) {

        float splattingValue = 0.f;
        for (int i = beginPoint; i <= endPoint; i++) {
            splattingValue = 0.f;
            for (Integer j = 0; j < listPoints.size(); j++) {
             
                    splattingValue += basisFunction(listPoints.get(i).getX(), listPoints.get(i).getY(), listPoints.get(j).getX(), listPoints.get(j).getY());
             
            }
            splatValues.set(i, splattingValue);

        }

    }

    public List<List<Float>> createSplatting() {

        calculateGaussianField();


        for (int i = 0; i < listPoints.size(); i++) {
            splatValues.add(i, 0.f);
        }
        //splattings();
        //splattings(0,listX.size()-1);

        Runtime runtime = Runtime.getRuntime();
        int nrOfProcessors = runtime.availableProcessors();
        
        Integer numberThreads = nrOfProcessors*2;
        List<Thread> listThreads = new ArrayList<Thread>();
        Integer step = (int)Math.ceil((listPoints.size() *1.0) / (numberThreads*1.0));
        Integer beginPoint = 0;
        Integer endPoint = 0;


        for (int i = 0; i < listPoints.size(); i = endPoint + 1) {
            beginPoint = i;
            endPoint = i + step;
            if (endPoint >= listPoints.size()) {
                endPoint = listPoints.size() - 1;
            }

            listThreads.add(new SplattingThread(beginPoint, endPoint));
            listThreads.get(listThreads.size() - 1).start();
        }

        for (int i = 0; i < listThreads.size(); i++) {
            try {
                listThreads.get(i).join();
            } catch (InterruptedException ex) {
                System.out.print("Join interrupted\n");
            }
        }
        normalizeSplattings();
        calculateSplatFields();
        return colorMap;
    }

    class SplattingThread extends Thread {

        Integer beginPoint;
        Integer endPoint;

        public SplattingThread(Integer beginPoint, Integer endPoint) {
            this.beginPoint = beginPoint;
            this.endPoint = endPoint;
        }

        public void run() {
           // System.out.println(beginPoint + " - " + endPoint);
            splattingsThreads(this.beginPoint, this.endPoint);

        }
    }
}
