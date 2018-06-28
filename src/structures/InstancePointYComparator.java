/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import java.util.Comparator;

/**
 *
 * @author aurea
 */
public class InstancePointYComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        float o1Y = ((InstancePoint) o1).getY();
        float o2Y = ((InstancePoint) o2).getY();

        if (o1Y > o2Y) {
            return 1;
        } else if (o1Y < o2Y) {
            return -1;
        } else {
            return 0;
        }

    }
}
