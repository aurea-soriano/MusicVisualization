/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

/**
 *
 * @author aurea
 */
public class AABSNodeIconSummary {

    Integer x0;
    Integer y0;
    Integer x1;
    Integer y1;
    AABSNodeIconSummary childrenLeft;
    AABSNodeIconSummary childrenRight;
    Integer row;
    Integer column;
    Boolean flag; //true = x , false = y
    

    public AABSNodeIconSummary() {
        this.childrenLeft = null;
        this.childrenRight = null;
    }

    public AABSNodeIconSummary(Integer x0, Integer y0, Integer x1, Integer y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
        this.childrenLeft = null;
        this.childrenRight = null;
    }

    public Integer getX0() {
        return x0;
    }

    public void setX0(Integer x0) {
        this.x0 = x0;
    }

    public Integer getY0() {
        return y0;
    }

    public void setY0(Integer y0) {
        this.y0 = y0;
    }

    public Integer getX1() {
        return x1;
    }

    public void setX1(Integer x1) {
        this.x1 = x1;
    }

    public Integer getY1() {
        return y1;
    }

    public void setY1(Integer y1) {
        this.y1 = y1;
    }


    public AABSNodeIconSummary getChildrenLeft() {
        return childrenLeft;
    }

    public void setChildrenLeft(AABSNodeIconSummary childrenLeft) {
        this.childrenLeft = childrenLeft;
    }

    public AABSNodeIconSummary getChildrenRight() {
        return childrenRight;
    }

    public void setChildrenRight(AABSNodeIconSummary childrenRight) {
        this.childrenRight = childrenRight;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
    
    public void print()
    {
        System.out.println("("+x0+","+y0+") ("+x1+","+y1+")");
       // System.out.println("R:"+this.getRow()+" C:"+this.getColumn());
    }
    
    public Boolean isLeaf()
    {
        if(childrenLeft==null && childrenRight==null)
        {
            return true;
        }
        return false;
        
    }
    
    public Boolean isInside(Integer x, Integer y)
    {
        if(x<x0)
            return false;
        if(x>x1)
            return false;
        if(y>y0)
            return false;
        if(y<y1)
            return false;
        return true;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }
    
    
    
}
