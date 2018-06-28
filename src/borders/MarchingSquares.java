/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package borders;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aurea
 */
public class MarchingSquares {

    List<List<Float>> colorMap;
    List<Path> marchingSquaresPaths;
    Float threshold;

    public MarchingSquares(List<List<Float>> colorMap, Float threshold) {
        this.colorMap = colorMap;
        this.threshold = threshold;
        marchingSquaresPaths = new ArrayList<Path>();
    }
    


    private int getCaseValue(int x, int y, float value) {


        if ((colorMap.get(x).get(y) > value)
                && (colorMap.get(x + 1).get(y + 1) > value)
                && (colorMap.get(x + 1).get(y) > value)
                && (colorMap.get(x).get(y + 1) > value)) {
            return 0;
        }
        if ((colorMap.get(x).get(y) >= value)
                && (colorMap.get(x + 1).get(y + 1) >= value)
                && (colorMap.get(x + 1).get(y) >= value)
                && (colorMap.get(x).get(y + 1) < value)) {
            return 1;
        }
        if ((colorMap.get(x).get(y) >= value)
                && (colorMap.get(x + 1).get(y + 1) < value)
                && (colorMap.get(x + 1).get(y) >= value)
                && (colorMap.get(x).get(y + 1) >= value)) {
            return 2;
        }
        if ((colorMap.get(x).get(y) >= value)
                && (colorMap.get(x + 1).get(y + 1) < value)
                && (colorMap.get(x + 1).get(y) >= value)
                && (colorMap.get(x).get(y + 1) < value)) {
            return 3;
        }
        if ((colorMap.get(x).get(y) >= value)
                && (colorMap.get(x + 1).get(y + 1) >= value)
                && (colorMap.get(x + 1).get(y) < value)
                && (colorMap.get(x).get(y + 1) >= value)) {
            return 4;
        }
        if ((colorMap.get(x).get(y) >= value)
                && (colorMap.get(x + 1).get(y + 1) >= value)
                && (colorMap.get(x + 1).get(y) < value)
                && (colorMap.get(x).get(y + 1) < value)) {
            return 5;
        }
        if ((colorMap.get(x).get(y) >= value)
                && (colorMap.get(x + 1).get(y + 1) < value)
                && (colorMap.get(x + 1).get(y) < value)
                && (colorMap.get(x).get(y + 1) >= value)) {
            return 6;
        }
        if ((colorMap.get(x).get(y) >= value)
                && (colorMap.get(x + 1).get(y + 1) < value)
                && (colorMap.get(x + 1).get(y) < value)
                && (colorMap.get(x).get(y + 1) < value)) {
            return 7;
        }
        if ((colorMap.get(x).get(y) < value)
                && (colorMap.get(x + 1).get(y + 1) >= value)
                && (colorMap.get(x + 1).get(y) >= value)
                && (colorMap.get(x).get(y + 1) >= value)) {
            return 8;
        }
        if ((colorMap.get(x).get(y) < value)
                && (colorMap.get(x + 1).get(y + 1) >= value)
                && (colorMap.get(x + 1).get(y) >= value)
                && (colorMap.get(x).get(y + 1) < value)) {
            return 9;
        }
        if ((colorMap.get(x).get(y) < value)
                && (colorMap.get(x + 1).get(y + 1) < value)
                && (colorMap.get(x + 1).get(y) >= value)
                && (colorMap.get(x).get(y + 1) >= value)) {
            return 10;
        }
        if ((colorMap.get(x).get(y) < value)
                && (colorMap.get(x + 1).get(y + 1) < value)
                && (colorMap.get(x + 1).get(y) >= value)
                && (colorMap.get(x).get(y + 1) < value)) {
            return 11;
        }
        if ((colorMap.get(x).get(y) < value)
                && (colorMap.get(x + 1).get(y + 1) >= value)
                && (colorMap.get(x + 1).get(y) < value)
                && (colorMap.get(x).get(y + 1) >= value)) {
            return 12;
        }
        if ((colorMap.get(x).get(y) < value)
                && (colorMap.get(x + 1).get(y + 1) >= value)
                && (colorMap.get(x + 1).get(y) < value)
                && (colorMap.get(x).get(y + 1) < value)) {
            return 13;
        }
        if ((colorMap.get(x).get(y) < value)
                && (colorMap.get(x + 1).get(y + 1) < value)
                && (colorMap.get(x + 1).get(y) < value)
                && (colorMap.get(x).get(y + 1) >= value)) {
            return 14;
        }
        if ((colorMap.get(x).get(y) < value)
                && (colorMap.get(x + 1).get(y + 1) < value)
                && (colorMap.get(x + 1).get(y) < value)
                && (colorMap.get(x).get(y + 1) < value)) {
            return 15;
        }

        return -1;
    }

    public   List<Path>  marchingSquares() {


        int x1, y1, x2, y2;
        for (Integer i = 0; i < colorMap.size(); i++) {
            for (Integer j = 0; j < colorMap.get(i).size(); j++) {
                if ((i + 2) < colorMap.size()
                        && (j + 2) < colorMap.get(i).size()) {
                    int caseMarching = getCaseValue(i, j, this.threshold);
                    /* if (caseMarching == 0 || caseMarching == 15) {
                    // no hace nada
                    }*/
                    if (caseMarching == 1 || caseMarching == 14
                            || caseMarching == 10) {
                        x1 = i;
                        y1 = j + 1;
                        x2 = i + 1;
                        y2 = j + 2;
                        Path path = new Path(x1, y1, x2, y2, caseMarching);
                        marchingSquaresPaths.add(path);

                    }
                    if (caseMarching == 2 || caseMarching == 13
                            || caseMarching == 5) {
                        x1 = i + 1;
                        y1 = j + 2;
                        x2 = i + 2;
                        y2 = j + 1;
                        Path path = new Path(x1, y1, x2, y2, caseMarching);
                        marchingSquaresPaths.add(path);
                    }
                    if (caseMarching == 3 || caseMarching == 12) {
                        x1 = i;
                        y1 = j + 1;
                        x2 = i + 2;
                        y2 = j + 1;
                        Path path = new Path(x1, y1, x2, y2, caseMarching);
                        marchingSquaresPaths.add(path);
                    }
                    if (caseMarching == 4 || caseMarching == 11
                            || caseMarching == 10) {
                        x1 = i + 1;
                        y1 = j;
                        x2 = i + 2;
                        y2 = j + 1;
                        Path path = new Path(x1, y1, x2, y2, caseMarching);
                        marchingSquaresPaths.add(path);
                    }

                    if (caseMarching == 6 || caseMarching == 9) {
                        x1 = i + 1;
                        y1 = j;
                        x2 = i + 1;
                        y2 = j + 2;
                        Path path = new Path(x1, y1, x2, y2, caseMarching);
                        marchingSquaresPaths.add(path);
                    }
                    if (caseMarching == 7 || caseMarching == 8
                            || caseMarching == 5) {
                        x1 = i + 1;
                        y1 = j;
                        x2 = i;
                        y2 = j + 1;
                        Path path = new Path(x1, y1, x2, y2, caseMarching);
                        marchingSquaresPaths.add(path);
                    }

                }

            }
        }
        return marchingSquaresPaths;
    }
    
}
