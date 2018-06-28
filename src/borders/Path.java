/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package borders;

/**
 *
 * @author asoriano
 */


public class Path
{
	int x1;
	int x2;
	int y1;
	int y2;
	int caseMarching;

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }

	Path( int x1, int y1, int x2, int y2, int caseMarching)
	{
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.caseMarching = caseMarching;

	}


}
