/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

/**
 *
 * @author aurea
 */
public class PointDragged {
    
    Float x;
    Float y;
    public PointDragged(Float x, Float y)
    {
        this.x = x;
        this.y = y;
    }
    PointDragged()
    {
        this.x = 0.0f;
        this.y = 0.0f;
        
    }

    public Float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }
    
    
}
