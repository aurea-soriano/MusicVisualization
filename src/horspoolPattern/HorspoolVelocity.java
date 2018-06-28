/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package horspoolPattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author aurea
 */

public class HorspoolVelocity {

  
    public List<Integer> velocityList;
    public List<Integer> positionsMatches;
    public List<Integer> pattern;//, text;       // pattern, text
    private int patternSize;//, textSize;          // pattern length, text length
    private static int alphabetsize=128;//256;
    private int[] occurrences;         // occurence function


    public HorspoolVelocity()
    {
        
        positionsMatches = new ArrayList<>();
        pattern = new ArrayList<>();
        velocityList = new ArrayList<>();
        for(int i=0; i<128;i++)
        {
            velocityList.add(i);
        }
        occurrences=new int[alphabetsize];
    }
       
    /** searches the text tt for the pattern pp
     */ 
    public void search(List<Integer> tt, List<Integer> pp)
    {
      //  setText(tt);
        setPattern(pp);
        horspoolSearch(tt);
    }

    /** sets the text
     */ 
    /*private void setText(List<String> tt)
    {
        textSize=tt.size();
        text=tt;
    }*/
    
    /** sets the pattern
     */ 
    private void setPattern(List<Integer> pp)
    {
        patternSize=pp.size();
        pattern=pp;
        horspoolInitocc();
    }

    
    /** computation of the occurrence function
     */ 
    private void horspoolInitocc()
    {
        int a, j;

        for (a=0; a<alphabetsize; a++)
            occurrences[a]=-1;

        for (j=0; j<patternSize-1; j++)
        {
            //a=pattern[j];
            a = velocityList.indexOf(pattern.get(j));//Character.toString(pattern[j]));
            occurrences[a]=j;
        }
    }

    /** searches the text for all occurences of the pattern
     */ 
    private void horspoolSearch(List<Integer> text)
    {
        int textSize = text.size();
        int i=0, j;
        while (i<=textSize-patternSize)
        {
            j=patternSize-1;
            while (j>=0 && pattern.get(j).equals(text.get(i+j)))
            {
                j--;
            }
            if (j<0) 
            {
                if(positionsMatches.size()>0)
                {
                    if((positionsMatches.get(positionsMatches.size()-1)+patternSize)<i)
                    {
                        positionsMatches.add(i);
                    }
                }
                else
                {
                    positionsMatches.add(i);
                }
                
            }
            i+=patternSize-1;
            try{
                i-=occurrences[velocityList.indexOf(text.get(i))];
            }
            catch(Exception e)
            {
                i = textSize-patternSize+2;
            }
            
          
        }
    }
}