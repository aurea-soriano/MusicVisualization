/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package horspoolPattern;

import java.util.List;

/**
 *
 * @author aurea
 */
public class PositionPatternVelocity implements Comparable<PositionPatternVelocity>{

    public Integer positionPattern;
    public List<Integer> pattern;//, text;       // pattern, text
    public Integer numberPattern;

    public PositionPatternVelocity(Integer positionPattern, List<Integer> pattern) {
        this.positionPattern = positionPattern;
        this.pattern = pattern;
        this.numberPattern = pattern.size();
    }

    @Override
    public int compareTo(PositionPatternVelocity o) {
        Integer positionPattern1 = ((PositionPatternVelocity) o).positionPattern; 
 
		return positionPattern.compareTo(positionPattern1);
 
    }
    
  
    
}
