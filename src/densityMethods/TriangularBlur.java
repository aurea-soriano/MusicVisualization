package densityMethods;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import structures.InstancePoint;

/**
 *
 * @author aurea
 */
public class TriangularBlur {

    List<Point> listPoints;
    Integer height;
    Integer width;
    Integer fieldSize;
    List<List<Float>> triangularField;
    List<Float> splatValues;
    float maxX;
    float maxY;
    float minX;
    float minY;
    List<List<Float>> colorMap;

    public TriangularBlur(List<InstancePoint> listPoints, Integer width, Integer height, Integer fieldSize, float maxX, float maxY, float minX, float minY) {
      
        this.height = height;
        this.width = width;
        this.listPoints = new ArrayList<Point>();
        for(int i = 0; i<listPoints.size();i++)
        {
            Integer x = Math.round(((listPoints.get(i).getX() - minX) * (float) (width - 60) / (maxX - minX)) + 30);
            Integer y = Math.round(height - (((listPoints.get(i).getY() - minY) * (float) (height - 60) / (maxY - minY)) + 30));

            this.listPoints.add(new Point(x, y));
        }
        this.fieldSize = fieldSize;
        this.maxX = maxX;
        this.maxY = maxY;
        this.minX = minX;
        this.minY = minY;

        this.splatValues = new ArrayList<Float>();
        this.triangularField = new ArrayList<List<Float>>();

        this.colorMap = new ArrayList<List<Float>>(width);

        for (Integer i = 0; i < width; i++) {
            colorMap.add(i, new ArrayList<Float>(height));
            for (Integer j = 0; j < height; j++) {
                colorMap.get(i).add(j, 1.f);
            }
        }

    }

    private void calculateTriangularField() {
        triangularField = new ArrayList<List<Float>>();

        Float triangleValue = 0.f;
        Integer y = fieldSize  / 2;
        Integer x = y * -1;

        for (Integer i = 0; i < fieldSize; i++) {
            triangularField.add(i, new ArrayList<Float>());

            x = (fieldSize / 2) * -1;
            for (Integer j = 0; j < fieldSize; j++) {

                float u = (float) (Math.abs(x) + Math.abs(y))
                        / (float) fieldSize;
                if (u < 1.0f) {

                    triangleValue = 1.0f - u;
                    triangularField.get(i).add(j, triangleValue);
                } else {
                    triangleValue = 0.0f;
                    triangularField.get(i).add(j, triangleValue);
                }

                x++;
            }

            y--;

        }

    }

    private void calculateTriangularBlur(Integer column, Integer row) {
        Integer x = column - (fieldSize / 2);

        for (Integer i = 0; i < fieldSize; i++, x++) {
            Integer y = row - (fieldSize / 2);
            for (Integer j = 0; j < fieldSize; j++, y++) {
                if (triangularField.get(i).get(j) > 0) {

                    Float color = 1 - (1.0f * triangularField.get(i).get(j));

                    // System.out.println(x+", "+y);
                    if (x >= 0 && y >= 0 && x < width && y < height) {

                        Float currentColor = colorMap.get(x).get(y);

                        color *= currentColor;

                        colorMap.get(x).set(y, color);

                    }
                }

            }

        }
    }

    private void calculateTriangularBlur() {
        Integer x, y;
        for (int i = 0; i < listPoints.size(); i++) {
            x = listPoints.get(i).x;
            y = listPoints.get(i).y;
            calculateTriangularBlur(x,y);
        }
    }

    private void calculateTriangularBlurThreads(Integer beginPoint, Integer endPoint) {
        Integer x, y;
        for (int i = beginPoint; i <= endPoint; i++) {
            x = listPoints.get(i).x;
            y = listPoints.get(i).y;
            calculateTriangularBlur(x,y);
        }
    }

    public List<List<Float>> createTriangularBlur() {

        calculateTriangularField();
        //calculateTriangularBlur();
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

            listThreads.add(new TriangularBlurThread(beginPoint, endPoint));
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

    class TriangularBlurThread extends Thread {

        Integer beginPoint;
        Integer endPoint;

        public TriangularBlurThread(Integer beginPoint, Integer endPoint) {
            this.beginPoint = beginPoint;
            this.endPoint = endPoint;
        }

        public void run() {
            // System.out.println(beginPoint + " - " + endPoint);
            calculateTriangularBlurThreads(this.beginPoint, this.endPoint);

        }
    }
}
