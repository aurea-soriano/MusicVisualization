/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

/**
 *
 * @author aurea
 */
public class InstancePoint {

    Float x;
    Float y;
    String label;
    Float cluster;



    public InstancePoint(Float x, Float y, String label, Float cluster) {
        this.x = x;
        this.y = y;
        this.label = label;
        this.cluster = cluster;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }
    
    public Float getCluster()
    {
        return cluster;
    }
    
    public void setCluster(Float cluster)
    {
        this.cluster = cluster;
    }

    public boolean isInside(int x, int y) {
        return (Math.abs((this.x - x)) <= 1 && Math.abs((this.y - y)) <= 1);
    }

}
