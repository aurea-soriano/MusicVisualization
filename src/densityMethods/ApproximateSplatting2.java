package densityMethods;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import structures.InstancePoint;
/*
 * Esta basado en la cantidad de sobreposiciones
 */

/**
 *
 * @author aurea
 */
public class ApproximateSplatting2 {

    List<Point> listPoints;
    List<Point> hashPoints;
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
    List<List<Integer>> countMap;
    Float maxCount;

    public ApproximateSplatting2(List<InstancePoint> listPoints, Integer width, Integer height, Integer fieldSize, Float sigma, float maxX, float maxY, float minX, float minY) {
        this.height = height;
        this.width = width;
        this.listPoints = new ArrayList<Point>();
         this.hashPoints = new ArrayList<Point>();
        for (int i = 0; i < listPoints.size(); i++) {
            Integer x = Math.round(((listPoints.get(i).getX() - minX) * (float) (width - 60) / (maxX - minX)) + 30);
            Integer y = Math.round(height - (((listPoints.get(i).getY() - minY) * (float) (height - 60) / (maxY - minY)) + 30));

            this.listPoints.add(new Point(x, y));
             if (!hashPoints.contains(new Point(x, y))) {
                hashPoints.add(new Point(x, y));
            }
        }
        this.fieldSize = fieldSize;
        this.sigma = sigma;
        this.maxX = maxX;
        this.maxY = maxY;
        this.minX = minX;
        this.minY = minY;

        this.splatValues = new ArrayList<Float>();
        this.gaussianField = new ArrayList<List<Float>>();

        this.countMap = new ArrayList<List<Integer>>();
        this.colorMap = new ArrayList<List<Float>>(width);

        for (Integer i = 0; i < width; i++) {
            colorMap.add(i, new ArrayList<Float>(height));
            for (Integer j = 0; j < height; j++) {
                colorMap.get(i).add(j, 1.f);
            }
        }

    }

    private void calculateApproximateSplatting2(Integer column, Integer row) {

        Float contributionValue = (countMap.get(column).get(row) * 1.0f) / maxCount;

        Integer x = column - (fieldSize / 2);
        for (Integer i = 0; i < fieldSize; i++, x++) {
            Integer y = row - (fieldSize / 2);
            for (Integer j = 0; j < fieldSize; j++, y++) {
                if (gaussianField.get(i).get(j) > 0) {

                    Float color = 1 - (contributionValue * gaussianField.get(i).get(j));

                    if (x >= 0 && y >= 0 && x < width && y < height) {
                        Float currentColor = colorMap.get(x).get(y);
                        color *= currentColor;
                        colorMap.get(x).set(y, color);
                    }
                }
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
                 
                    gaussianField.get(i).add(j, gaussValue / value00);

                }
                x++;
            }
            y--;
        }
    }

    public void calculateMatrixCount() {
        maxCount = 0.0f;
        countMap = new ArrayList<List<Integer>>(width);
        for (Integer i = 0; i < width; i++) {
            countMap.add(i, new ArrayList<Integer>(height));
            for (Integer j = 0; j < height; j++) {
                countMap.get(i).add(j, 0);
            }
        }

        Integer x, y;
        for (int i = 0; i < listPoints.size(); i++) {
            x = listPoints.get(i).x;
            y = listPoints.get(i).y;
            int value = countMap.get(x).get(y) + 1;
            countMap.get(x).set(y, value);
            if (maxCount < (value * 1.0f)) {
                maxCount = value * 1.0f;
            }
        }

    }

    private void calculateApproximateSplatting2() {

        Integer x, y;
        for (int i = 0; i < hashPoints.size(); i++) {
            x = hashPoints.get(i).x;
            y = hashPoints.get(i).y;
            calculateApproximateSplatting2(x, y);

        }

    }

    private void calculateApproximateSplatting2Threads(Integer beginPoint, Integer endPoint) {

        Integer x, y;
        for (int i = beginPoint; i <= endPoint; i++) {
            x = hashPoints.get(i).x;
            y = hashPoints.get(i).y;
            calculateApproximateSplatting2(x, y);

        }

    }

    public List<List<Float>> createApproximateSplatting2() {
        calculateMatrixCount();
        calculateGaussianField();
        //calculateApproximateSplatting2();

        Runtime runtime = Runtime.getRuntime();
        int nrOfProcessors = runtime.availableProcessors();

        Integer numberThreads = nrOfProcessors * 2;
        List<Thread> listThreads = new ArrayList<Thread>();
        Integer step = (int) Math.ceil((hashPoints.size() * 1.0) / (numberThreads * 1.0));

        Integer beginPoint = 0;
        Integer endPoint = 0;


        for (int i = 0; i < hashPoints.size(); i = endPoint + 1) {
            beginPoint = i;
            endPoint = i + step;
            if (endPoint >= hashPoints.size()) {
                endPoint = hashPoints.size() - 1;
            }

            listThreads.add(new ApproximateSplatting2Thread(beginPoint, endPoint));
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

    class ApproximateSplatting2Thread extends Thread {

        Integer beginPoint;
        Integer endPoint;

        public ApproximateSplatting2Thread(Integer beginPoint, Integer endPoint) {
            this.beginPoint = beginPoint;
            this.endPoint = endPoint;
        }

        public void run() {
            // System.out.println(beginPoint + " - " + endPoint);
            calculateApproximateSplatting2Threads(this.beginPoint, this.endPoint);

        }
    }
}
