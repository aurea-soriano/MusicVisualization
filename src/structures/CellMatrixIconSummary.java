/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author aurea
 */
public class CellMatrixIconSummary {
    
    ArrayList<InstancePoint> listPoints; 
    Integer numberPoints;
    Integer numberLog2;
    Integer column;
    Integer row;
    ArrayList<InstancePoint> listMainPoints;
    Float iconSize;
    BufferedImage image;
    Boolean showed;
 
    
    public CellMatrixIconSummary(Integer row, Integer column) {
        this.listPoints = new ArrayList<>();
        this.listMainPoints = new ArrayList<>();
        this.numberPoints = 0;
        this.column = column;
        this.row = row;
        this.iconSize=0.f;
        this.showed=false;
    }

    public CellMatrixIconSummary(Integer row, Integer column, ArrayList<InstancePoint> listPoints, Integer numberPoints) {
        this.listPoints = listPoints;
        this.numberPoints = numberPoints;
        this.column = column;
        this.row = row;
        this.listMainPoints = new ArrayList<>();
        this.iconSize=0.f;
        this.showed=false;
    }
    public void insertPoint(InstancePoint point)
    {
        this.listPoints.add(point);
        this.numberPoints++;
        
    }

    public ArrayList<InstancePoint> getListPoints() {
        return listPoints;
    }

    public void setListPoints(ArrayList<InstancePoint> listPoints) {
        this.listPoints = listPoints;
    }

    public Integer getNumberPoints() {
        return numberPoints;
    }

    public void setNumberPoints(Integer numberPoints) {
        this.numberPoints = numberPoints;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public ArrayList<InstancePoint> getListMainPoints() {
        return listMainPoints;
    }

    public void setListMainPoints(ArrayList<InstancePoint> listMainPoints) {
        this.listMainPoints = listMainPoints;
    }

    public Float getIconSize() {
        return iconSize;
    }

    public void setIconSize(Float iconSize) {
        this.iconSize = iconSize;
    }

    public Integer getNumberLog2() {
        return numberLog2;
    }

    public void setNumberLog2(Integer numberLog2) {
        this.numberLog2 = numberLog2;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Boolean getShowed() {
        return showed;
    }

    public void setShowed(Boolean showed) {
        this.showed = showed;
    }

}
