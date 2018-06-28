package densityMethods;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import structures.InstancePoint;

/**
 *
 * @author aurea
 */
public class GaussianBlur {

    List<Point> listPoints;
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

    public GaussianBlur(List<InstancePoint> listInstancePoints, Integer width, Integer height, Integer fieldSize, Float sigma, float maxX, float maxY, float minX, float minY) {

        this.height = height;
        this.width = width;
        this.listPoints = new ArrayList<Point>();
        for (int i = 0; i < listInstancePoints.size(); i++) {
            Integer x = Math.round(((listInstancePoints.get(i).getX() - minX) * (float) (width - 60) / (maxX - minX)) + 30);
            Integer y = Math.round(height - (((listInstancePoints.get(i).getY() - minY) * (float) (height - 60) / (maxY - minY)) + 30));

            this.listPoints.add(new Point(x, y));
        }
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
                 gaussianField.get(i).add(j, gaussValue / value00);

                }
                x++;
            }
            y--;

        }

    }

    private void calculateGaussianBlur(Integer column, Integer row) {
        Integer x = column - (fieldSize / 2);

        for (Integer i = 0; i < fieldSize; i++, x++) {
            Integer y = row - (fieldSize / 2);
            for (Integer j = 0; j < fieldSize; j++, y++) {
                if (gaussianField.get(i).get(j) > 0) {

                    Float color = 1 - (1.0f * gaussianField.get(i).get(j));

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

    private void calculateGaussianBlur() {
        Integer x, y;
        for (int i = 0; i < listPoints.size(); i++) {
            x = listPoints.get(i).x;
            y = listPoints.get(i).y;
            calculateGaussianBlur(x, y);

        }

    }

    private void calculateGaussianBlurThreads(Integer beginPoint, Integer endPoint) {
        Integer x, y;

        for (int i = beginPoint; i <= endPoint; i++) {
           x = listPoints.get(i).x;
            y = listPoints.get(i).y;
            calculateGaussianBlur(x,y);

        }

    }

    public List<List<Float>> createGaussianBlur() {

        calculateGaussianField();
        //calculateGaussianBlur();

        Runtime runtime = Runtime.getRuntime();
        int nrOfProcessors = runtime.availableProcessors();

        Integer numberThreads = nrOfProcessors * 2;
        List<Thread> listThreads = new ArrayList<Thread>();
        Integer step = (int) Math.ceil((listPoints.size() * 1.0) / (numberThreads * 1.0));

        Integer beginPoint = 0;
        Integer endPoint = 0;


        for (int i = 0; i < listPoints.size(); i = endPoint + 1) {
            beginPoint = i;
            endPoint = i + step;
            if (endPoint >= listPoints.size()) {
                endPoint = listPoints.size() - 1;
            }

            listThreads.add(new GaussianBlurThread(beginPoint, endPoint));
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

    class GaussianBlurThread extends Thread {

        Integer beginPoint;
        Integer endPoint;

        public GaussianBlurThread(Integer beginPoint, Integer endPoint) {
            this.beginPoint = beginPoint;
            this.endPoint = endPoint;
        }

        public void run() {
            // System.out.println(beginPoint + " - " + endPoint);
            calculateGaussianBlurThreads(this.beginPoint, this.endPoint);

        }
    }
}
