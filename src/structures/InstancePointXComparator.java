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
public class InstancePointXComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        float o1X = ((InstancePoint) o1).getX();
        float o2X = ((InstancePoint) o2).getX();

        if (o1X > o2X) {
            return 1;
        } else if (o1X < o2X) {
            return -1;
        } else {
            return 0;
        }

    }
}
