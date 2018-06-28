/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import java.util.ArrayList;

/**
 * Axis Aligned Bounding Square
 *
 * @author aurea
 */
public class AABSTreeIconSummary {

    AABSNodeIconSummary root;
    Integer widthSquare;
    Integer heightSquare;

    public AABSTreeIconSummary() {
        root = null;
    }

    public AABSTreeIconSummary(Integer widthSquare, Integer heightSquare, Integer x0, Integer y0, Integer x1, Integer y1) {
        this.widthSquare = widthSquare;
        this.heightSquare = heightSquare;
        this.root = new AABSNodeIconSummary(x0, y0, x1, y1);
        this.root.setFlag(Boolean.FALSE);
        this.root.setColumn(0);
        this.root.setRow(0);
        this.createABBSTreeIconSummary(root);
    }

    public Boolean splitX(AABSNodeIconSummary node) {
        Integer width = node.x1 - node.x0;
        Integer numberSquares = width / this.widthSquare;

        if (numberSquares <= 1) {
            return false;
        }
        Integer numberSquaresLeft = numberSquares / 2;
        Integer numberSquaresRight = numberSquares - numberSquaresLeft;
        node.childrenLeft = new AABSNodeIconSummary(node.x0, node.y0, node.x0 + (this.widthSquare * numberSquaresLeft), node.y1);
        node.childrenRight = new AABSNodeIconSummary(node.x0 + (this.widthSquare * numberSquaresLeft), node.y0, node.x1, node.y1);
        node.childrenLeft.setFlag(Boolean.TRUE);
        node.childrenRight.setFlag(Boolean.TRUE);
        node.childrenLeft.setRow(node.getRow());
        node.childrenLeft.setColumn(node.getColumn());
        node.childrenRight.setRow(node.getRow());
        node.childrenRight.setColumn(node.getColumn() + 1);
        return true;

    }

    public Boolean splitY(AABSNodeIconSummary node) {
        Integer height = node.y0 - node.y1;
        Integer numberSquares = height / this.heightSquare;

        if (numberSquares <= 1) {
            return false;
        }
        Integer numberSquaresTop = numberSquares / 2;
        Integer numberSquaresDown = numberSquares - numberSquaresTop;

        node.childrenLeft = new AABSNodeIconSummary(node.x0, node.y0, node.x1, node.y0 - (this.heightSquare * numberSquaresTop));
        node.childrenRight = new AABSNodeIconSummary(node.x0, node.y0 - (this.heightSquare * numberSquaresTop), node.x1, node.y1);
        node.childrenLeft.setFlag(Boolean.FALSE);
        node.childrenRight.setFlag(Boolean.FALSE);
        node.childrenLeft.setRow(node.getRow());
        node.childrenLeft.setColumn(node.getColumn());
        node.childrenRight.setRow(node.getRow() + 1);
        node.childrenRight.setColumn(node.getColumn());
        return true;



    }

    public AABSNodeIconSummary getRoot() {
        return root;
    }

 

    public void createABBSTreeIconSummary(AABSNodeIconSummary node) {
        if (node == null) {
            return;
        }

        if (node.getFlag().equals(Boolean.TRUE)) {
            if (splitX(node).equals(false)) {
                splitY(node);
            }
        } else {
            if (splitY(node).equals(false)) {
                splitX(node);
            }
        }
        createABBSTreeIconSummary(node.childrenRight);
        createABBSTreeIconSummary(node.childrenLeft);


    }

    public void printLeaves(AABSNodeIconSummary node) {
        if (node == null) {
            return;
        }
        if (node.isLeaf()) {
            node.print();
            
            return;
        } else {
            printLeaves(node.getChildrenLeft());
            printLeaves(node.getChildrenRight());
        }
    }

    public AABSNodeIconSummary getBoundingNode(Integer x, Integer y) {

        AABSNodeIconSummary node = this.root;
        if (node == null) {
            return null;
        }
        while (!node.isLeaf()) {
            if (node.childrenLeft.isInside(x, y)) {
                node = node.childrenLeft;
            } else {
                node = node.childrenRight;
            }
        }

        if (node.isInside(x, y)) {
            return node;
        } else {
            return null;
        }

    }

    public static void main(String args[]) {
        AABSTreeIconSummary tree = new AABSTreeIconSummary(141, 102, 30, 540, 738, 30);


        ArrayList<ArrayList<CellMatrixIconSummary>> matrixIconSummary = new ArrayList<ArrayList<CellMatrixIconSummary>>();

        for (int i = 0; i < 10; i++) {

            ArrayList<CellMatrixIconSummary> rowMatrixIconSummary = new ArrayList<CellMatrixIconSummary>();
            for (int j = 0; j < 10; j++) {
                CellMatrixIconSummary cell = new CellMatrixIconSummary(i, j);
                rowMatrixIconSummary.add(cell);
            }
            matrixIconSummary.add(rowMatrixIconSummary);
        }

        AABSNodeIconSummary node = tree.getBoundingNode(30, 30);




        /*       if (node != null) {
         node.print();
         }
         System.out.println(node.getRow()+" - "+node.getColumn());
         System.out.println(row + " - " + column);*/

        tree.printLeaves(tree.root);

        
    }
    
}
