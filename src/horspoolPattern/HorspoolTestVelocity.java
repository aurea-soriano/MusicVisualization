/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package horspoolPattern;

import colors.Aurea;
import colors.Aurea10;
import colors.PseudoRainbow;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import midiStructure.VelocityStructureRecognition;

/**
 *
 * @author aurea
 */
public class HorspoolTestVelocity {
    boolean findPattern(List<HorspoolVelocity> listHorspool, List<Integer> pattern) {
        for (HorspoolVelocity horspool : listHorspool) {
            if (horspool.pattern.equals(pattern)) {
                return true;
            }
        }
        return false;
    }

    public List<HorspoolVelocity> patternRecognition(List<Integer> velocities) {
        int sizeStep = (int) Math.floor((double) velocities.size() / 2.0);
        // System.out.println("Step"+sizeStep);
        List<HorspoolVelocity> listHorspool = new ArrayList<>();

        for (int i = sizeStep; i > 0; i--) {
            //System.out.println("i Step"+i);
            for (int j = 0; j <= velocities.size() - i; j++) {
                List<Integer> pp = new ArrayList<>();

                for (int k = 0; k < i; k++) {
                    pp.add(velocities.get(k + j));
                }
                //System.out.println("sizeStep "+i+" padroes"+pp);
                if (!findPattern(listHorspool, pp)) {
                    HorspoolVelocity horspool = new HorspoolVelocity();
                    horspool.search(velocities, pp);
                    if (horspool.pattern.size() > 1 && horspool.positionsMatches.size() > 1) {
                        listHorspool.add(horspool);
                        j = j + pp.size();
                    } else {
                        if (horspool.pattern.size() == 1 && !findPattern(listHorspool, pp)) {
                            listHorspool.add(horspool);
                            j = j + pp.size();
                        }
                    }

                }


            }
        }
        return listHorspool;
    }
    
   
    void print(List<HorspoolVelocity> listHorspool) {
        for (HorspoolVelocity horspool : listHorspool) {
            System.out.println("*****");
            System.out.println(horspool.pattern);
            //System.out.println(horspool.text);
            System.out.println(horspool.positionsMatches);
        }
        System.out.println("*****");
    }

    public List<Integer> calculateColor(List<HorspoolVelocity> listHorspool, List<Integer> text) {
        List<Integer> colors = new ArrayList<>();
        int color = 0;
        for (int i = 0; i < text.size(); i++) {
            colors.add(0);
        }
        int numberPaintedColors = 0;
        for (HorspoolVelocity horspool : listHorspool) {

            //System.out.println("pattern " + horspool.pattern + "positions " + horspool.positionsMatches);
            //estoy viendo si no hay cruce
            int numberStringWithoutCross = 0;
            int patternSize = horspool.pattern.size();

            for (int i = 0; i < horspool.positionsMatches.size(); i++) {
                int numberSubStringWithoutCross = 0;
                int position = horspool.positionsMatches.get(i);

                //System.out.println("positions "+horspool.positionsMatches +" size Pattern " + patternSize + " position previous " + positionPrevious + " position" + position);

                for (int j = position; j < (position + patternSize); j++) {
                    if (colors.get(j).equals(0)) {
                        numberSubStringWithoutCross++;
                    }
                }

                if (numberSubStringWithoutCross == patternSize) {
                    numberStringWithoutCross++;

                    //System.out.println("substring "+numberSubStringWithoutCross + "patternSize"+ patternSize);
                }


            }

            //System.out.println("pattern " + horspool.pattern + " number " + numberStringWithoutCross);
            if (numberStringWithoutCross >= 2 || patternSize == 1) {

                //System.out.println("pattern " + horspool.pattern + " number " + numberStringWithoutCross);
                color++;
                for (int i = 0; i < horspool.positionsMatches.size(); i++) {

                    int numberSubStringWithoutCross = 0;
                    int position = horspool.positionsMatches.get(i);

                    for (int j = position; j < (position + patternSize); j++) {
                        if (colors.get(j).equals(0)) {
                            numberSubStringWithoutCross++;
                        }
                    }
                    if (numberSubStringWithoutCross == patternSize) {
                        for (int j = position; j < (position + horspool.pattern.size()); j++) {
                            colors.set(j, color);
                            numberPaintedColors++;
                            if (numberPaintedColors == colors.size()) {
                                return colors;
                            }
                        }
                    }
                }
            }
        }

        return colors;
    }

    public List<PositionPatternVelocity> calculateColorPositions(List<HorspoolVelocity> listHorspool, List<Integer> text) {
        List<Integer> valuePosition = new ArrayList<>();
        int color = 0;
        for (int i = 0; i < text.size(); i++) {
            valuePosition.add(-1);
        }
        List<PositionPatternVelocity> listPosPat = new ArrayList<>();
        int numberPaintedColors = 0;
        for (HorspoolVelocity horspool : listHorspool) {

            //System.out.println("pattern " + horspool.pattern + "positions " + horspool.positionsMatches);
            //estoy viendo si no hay cruce
            int numberStringWithoutCross = 0;
            int patternSize = horspool.pattern.size();

            for (int i = 0; i < horspool.positionsMatches.size(); i++) {
                int numberSubStringWithoutCross = 0;
                int position = horspool.positionsMatches.get(i);

                //System.out.println("positions "+horspool.positionsMatches +" size Pattern " + patternSize + " position previous " + positionPrevious + " position" + position);

                for (int j = position; j < (position + patternSize); j++) {
                    if (valuePosition.get(j).equals(-1)) {
                        numberSubStringWithoutCross++;
                    }
                }

                if (numberSubStringWithoutCross == patternSize) {
                    numberStringWithoutCross++;

                    //System.out.println("substring "+numberSubStringWithoutCross + "patternSize"+ patternSize);
                }


            }

            //System.out.println("pattern " + horspool.pattern + " number " + numberStringWithoutCross);
            if (numberStringWithoutCross >= 2 || patternSize == 1) {

                //System.out.println("pattern " + horspool.pattern + " number " + numberStringWithoutCross);
                color++;
                for (int i = 0; i < horspool.positionsMatches.size(); i++) {

                    int numberSubStringWithoutCross = 0;
                    int position = horspool.positionsMatches.get(i);

                    for (int j = position; j < (position + patternSize); j++) {
                        if (valuePosition.get(j).equals(-1)) {
                            numberSubStringWithoutCross++;
                        }
                    }
                    if (numberSubStringWithoutCross == patternSize) {
                        PositionPatternVelocity posPat = new PositionPatternVelocity(position, horspool.pattern);
                        listPosPat.add(posPat);
                        for (int j = position; j < (position + horspool.pattern.size()); j++) {
                            valuePosition.set(j, 1);

                            numberPaintedColors++;
                            if (numberPaintedColors == valuePosition.size()) {
                                return listPosPat;
                            }
                        }
                    }
                }
            }
        }
        return listPosPat;
    }

    public BufferedImage createImageWithDominantVelocity(List<PositionPatternVelocity> listPositionPattern) {
        Collections.sort(listPositionPattern);
        int sizeSquare = 30;
        int sizeWidth = 0;
        for (int i = 0; i < listPositionPattern.size(); i++) {
            sizeWidth += (listPositionPattern.get(i).numberPattern * 2);
        }
        int cols = sizeWidth + (5 * listPositionPattern.size()) + 5;//listPositionPattern.size();
        int rows = 1;
        int width = cols;
        int height = rows * sizeSquare + 10;

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        img.createGraphics();
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        Integer max = 12;
        Integer min = 0;


        //int col = 0;
        //int row = 0;
        //int posCol = 0;
        //int posRow = 0;

        List<Integer> listVelocities = new ArrayList<>();
        for(int k=0; k<128; k++)
        {
            listVelocities.add(k);
        }
       

        List<Integer> listHistogram = new ArrayList<>();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 5, sizeSquare + 10);

        g.setColor(Color.BLACK);
//        g.fillRect(0, 0, cols, 5);

        g.fillRect(0, 0, cols, 40);

        int position = 5;

        for (int i = 0; i < listPositionPattern.size(); i++) {
            listHistogram = new ArrayList<>();

            for (int j = 0; j < 12; j++) {
                listHistogram.add(0);
            }

            for (int j = 0; j < listPositionPattern.get(i).pattern.size(); j++) {
                for (int k = 0; k < listVelocities.size(); k++) {
                    if (listPositionPattern.get(i).pattern.get(j).equals(listVelocities.get(k))) {
                        listHistogram.set(k, listHistogram.get(k) + 1);
                    }
                }
            }
            //verificar se tds sao iguais
            Boolean verifyGray = true;


            Integer maxValueHistogram = listHistogram.get(0);
            Integer maxIndexHistogram = 0;
            for (int j = 1; j < listHistogram.size(); j++) {
                if (!listHistogram.get(j - 1).equals(listHistogram.get(j)) && verifyGray.equals(true)) {
                    verifyGray = false;
                }
                if (listHistogram.get(j) > maxValueHistogram) {
                    maxValueHistogram = listHistogram.get(j);
                    maxIndexHistogram = j;
                }

            }
            int color = ((maxIndexHistogram - min) * 200 / (max - min)) + 55;


            //PseudoRainbow colorRainbow = new PseudoRainbow();
            Aurea colorRainbow = new Aurea();

            Color[] rgb = new Color[256];
            rgb = colorRainbow.rgb;
            if (verifyGray.equals(true)) {
                g.setColor(Color.GRAY);
            } else {
                g.setColor(rgb[color]);
            }
            g.fillRect(position, 5, listPositionPattern.get(i).numberPattern * 2, sizeSquare);

            position = position + listPositionPattern.get(i).numberPattern * 2;


            g.setColor(Color.BLACK);
            g.fillRect(position, 0, 5, sizeSquare + 10);
            position += 5;

        }
        return img;

    }

    public BufferedImage createImageWithComplexity(List<PositionPatternVelocity> listPositionPattern) {
        
        Collections.sort(listPositionPattern);
        int sizeSquare = 30;
        int sizeWidth = 0;
        for (int i = 0; i < listPositionPattern.size(); i++) {
            sizeWidth += (listPositionPattern.get(i).numberPattern * 2);
        }
        int cols = sizeWidth + (5 * listPositionPattern.size()) + 5;//listPositionPattern.size();
        int rows = 1;
        int width = cols;
        int height = rows * sizeSquare + 10;

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        img.createGraphics();
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);



        List<Integer> listVelocities = new ArrayList<>();
         for(int k=0; k<128; k++)
        {
            listVelocities.add(k);
        }

        Integer max = 127;
        Integer min = 0;

        List<Integer> listHistogram = new ArrayList<>();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 5, sizeSquare + 10);

        g.setColor(Color.BLACK);

        g.fillRect(0, 0, cols, 40);

        int position = 5;

        for (int i = 0; i < listPositionPattern.size(); i++) {
            listHistogram = new ArrayList<>();

            for (int j = 0; j < listVelocities.size(); j++) {
                listHistogram.add(0);
            }

           
            for (int j = 0; j < listPositionPattern.get(i).pattern.size(); j++) {
                for (int k = 0; k < listVelocities.size(); k++) {
                    if (listPositionPattern.get(i).pattern.get(j).equals(listVelocities.get(k))) {
                        listHistogram.set(k, listHistogram.get(k) + 1);
                    }
                }
            }



            Integer countVelocities = 0;
            for (int j = 0; j < listHistogram.size(); j++) {
                if (listHistogram.get(j) > 0) {
                    countVelocities++;
                }
            }
            int color = ((countVelocities - min) * 200 / (max - min)) + 55;
            if(countVelocities.equals(0))
            {
                System.out.println("pattern "+listPositionPattern.get(i).pattern);
            }

            Aurea colorRainbow = new Aurea();

            Color[] rgb = new Color[256];
            rgb = colorRainbow.rgb;

            g.setColor(rgb[color]);
            g.fillRect(position, 5, listPositionPattern.get(i).numberPattern * 2, sizeSquare);

            position = position + listPositionPattern.get(i).numberPattern * 2;


            g.setColor(Color.BLACK);
            g.fillRect(position, 0, 5, sizeSquare + 10);
            position += 5;

        }
        return img;

    }

    public Integer getNumberColorSegment(Integer minValue, Integer maxValue, Float sizeSegment, Integer numberOfColorSegments, Integer value) {

        return Math.round(((value - minValue) * 1.f) * (numberOfColorSegments - 1.f) / (maxValue - minValue) * 1.f);
    }

    public BufferedImage createImageByColorSegment(List<PositionPatternVelocity> listPositionPattern, Integer numberOfColorSegments) {
        Collections.sort(listPositionPattern);
        Integer minValue = Integer.MAX_VALUE;
        Integer maxValue = Integer.MIN_VALUE;
        for (int i = 0; i < listPositionPattern.size(); i++) {
            if (minValue > listPositionPattern.get(i).numberPattern) {
                minValue = listPositionPattern.get(i).numberPattern;
            }
         //   if (maxValue < listPositionPattern.get(i).numberPattern) {
           //     maxValue = listPositionPattern.get(i).numberPattern;
           // }
        }
        maxValue= 162; //189
        minValue=1;
        
        Float sizeSegment = (maxValue - minValue) / numberOfColorSegments * 1.f;
        int sizeSquare = 30;
        int sizeWidth = 0;
        for (int i = 0; i < listPositionPattern.size(); i++) {
            sizeWidth += (listPositionPattern.get(i).numberPattern * 2);
        }
        int cols = sizeWidth + (5 * listPositionPattern.size()) + 5;//listPositionPattern.size();
        int rows = 1;
        int width = cols;
        int height = rows * sizeSquare + 10;
        int min = 0;
        int max = numberOfColorSegments - 1;

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        img.createGraphics();
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);




        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 5, sizeSquare + 10);

        g.setColor(Color.BLACK);
//        g.fillRect(0, 0, cols, 5);

        g.fillRect(0, 0, cols, 40);

        int position = 5;

        for (int i = 0; i < listPositionPattern.size(); i++) {



            Integer index = this.getNumberColorSegment(minValue, maxValue, sizeSegment, numberOfColorSegments, listPositionPattern.get(i).numberPattern);

            int color = ((index - min) * 200 / (max - min)) + 55;


            
            //PseudoRainbow colorRainbow = new PseudoRainbow();
            //Aurea colorRainbow = new Aurea();
            Aurea10 colorRainbow = new Aurea10();
            //OCS colorRainbow = new OCS();
            //AureaInverse colorRainbow = new AureaInverse();
            //ColorPaperJazz colorRainbow = new ColorPaperJazz();
            //CategoryScale colorRainbow = new CategoryScale();
            
            
            Color[] rgb = new Color[256];
            rgb = colorRainbow.rgb;

            g.setColor(rgb[color]);

            g.fillRect(position, 5, listPositionPattern.get(i).numberPattern * 2, sizeSquare);

            position = position + listPositionPattern.get(i).numberPattern * 2;


            g.setColor(Color.BLACK);
            g.fillRect(position, 0, 5, sizeSquare + 10);
            position += 5;

        }
        return img;

    }

    public static void saveImage(BufferedImage img, String ref) {
        try {
            String format = (ref.endsWith(".png")) ? "png" : "jpg";
            ImageIO.write(img, format, new File(ref));
        } catch (IOException e) {
        }
    }

    public BufferedImage createImage(List<Integer> colors) {
        int sizeSquare = 4;

        int cols = (int) Math.sqrt((double) colors.size());
        int rows = (int) Math.round((double) colors.size() / cols);
        int width = cols * sizeSquare;
        int height = rows * sizeSquare;

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        img.createGraphics();
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        Integer max = Collections.max(colors);
        Integer min = Collections.min(colors);
        //System.out.println("max "+max);
        //System.out.println("min "+min);

        int col = 0;
        int row = 0;
        int posCol = 0;
        int posRow = 0;


        System.out.println("rows " + rows);
        System.out.println("cols " + cols);

        for (int i = 0; i < colors.size(); i++) {
            int color = ((colors.get(i) - min) * 200 / (max - min)) + 55;
            PseudoRainbow colorRainbow = new PseudoRainbow();

            Color[] rgb = new Color[256];
            rgb = colorRainbow.rgb;
            g.setColor(rgb[color]);
            //System.out.println("color "+colors.get(i));

            //System.out.println("("+posCol+", "+posRow+") - ("+(posCol+4)+", "+(posRow+4)+")");
            g.fillRect(posCol, posRow, posCol + sizeSquare, posRow + sizeSquare);
            col++;
            if (col == cols) {
                col = 0;
                row++;
                posRow = posRow + sizeSquare;
                posCol = 0;
            } else {
                posCol = posCol + sizeSquare;
                posRow = posRow;
            }



        }
        return img;
    }

    public BufferedImage createImage2(List<Integer> colors) {
        int sizeSquare = 30;

        int cols = colors.size();
        int rows = 1;
        int width = cols;
        int height = rows * sizeSquare;

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        img.createGraphics();
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        Integer max = Collections.max(colors);
        Integer min = Collections.min(colors);
        //System.out.println("max "+max);
        //System.out.println("min "+min);

        int col = 0;
        int row = 0;
        int posCol = 0;
        int posRow = 0;


        // System.out.println("rows " + rows);
        // System.out.println("cols " + cols);

        for (int i = 0; i < colors.size(); i++) {
            int color = ((colors.get(i) - min) * 200 / (max - min)) + 55;
            PseudoRainbow colorRainbow = new PseudoRainbow();

            Color[] rgb = new Color[256];
            rgb = colorRainbow.rgb;
            g.setColor(rgb[color]);
            //System.out.println("color "+colors.get(i));

            //System.out.println("("+posCol+", "+posRow+") - ("+(posCol+4)+", "+(posRow+4)+")");
            g.fillRect(posCol, posRow, posCol, posRow + sizeSquare);
            posCol++;
            //if (col == cols) {
            //    col = 0;
            //  row++;
            //  posRow = posRow + sizeSquare;
            //  posCol = 0;
            //} else {
            //  posCol = posCol + sizeSquare;
            //  posRow = posRow;
            //}



        }
        return img;
    }

    public static List<Integer> getListWithoutOutliers(List<Integer> listColors) {
        List<Integer> listSequence = new ArrayList<>();
        int leftValue, rightValue, leftDistance, rightDistance;
        for (int i = 0; i < listColors.size(); i++) {
            try {
                leftValue = listColors.get(i - 1);
                if (leftValue == listColors.get(i)) {
                    leftDistance = 1;
                } else {
                    leftDistance = -1;
                }
            } catch (Exception e) {
                leftDistance = -1;
            }

            try {
                rightValue = listColors.get(i + 1);
                if (rightValue == listColors.get(i)) {
                    rightDistance = 1;
                } else {
                    rightDistance = -1;
                }
            } catch (Exception e) {
                rightDistance = -1;
            }

            if ((leftDistance + rightDistance) >= 0) {
                listSequence.add(listColors.get(i));

            }
        }
        return listSequence;
    }

    public static List<Integer> getListOnlySequence(List<Integer> listColors) {
        List<Integer> listSequence = new ArrayList<Integer>();
        for (int i = 0; i < listColors.size(); i++) {
            int previous;
            try {
                previous = listColors.get(i - 1);
                if (previous != listColors.get(i)) {
                    listSequence.add(listColors.get(i));
                }

            } catch (Exception e) {
                listSequence.add(listColors.get(i));
            }
        }
        return listSequence;
    }

    public static List<Integer> getSequence(List<Integer> listColors) {
        List<Integer> listSequence = new ArrayList<Integer>();
        int leftValue, rightValue, leftDistance, rightDistance;
        for (int i = 0; i < listColors.size(); i++) {
            try {
                leftValue = listColors.get(i - 1);
                if (leftValue == listColors.get(i)) {
                    leftDistance = 1;
                } else {
                    leftDistance = -1;
                }
            } catch (Exception e) {
                leftDistance = -1;
            }

            try {
                rightValue = listColors.get(i + 1);
                if (rightValue == listColors.get(i)) {
                    rightDistance = 1;
                } else {
                    rightDistance = -1;
                }
            } catch (Exception e) {
                rightDistance = -1;
            }

            if ((leftDistance + rightDistance) >= 0) {
                int previous;
                try {
                    previous = listColors.get(i - 1);
                    if (previous != listColors.get(i)) {
                        listSequence.add(listColors.get(i));
                    }

                } catch (Exception e) {
                    listSequence.add(listColors.get(i));
                }
            }
        }
        return listSequence;
    }

    // only for test purposes
    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {

        BufferedReader in = new BufferedReader(new FileReader("list_midi_file_2013.txt"));
        String str;
        int count = 0;

        while ((str = in.readLine()) != null) {
            count++;
            try {
                VelocityStructureRecognition velocityRecognition = new VelocityStructureRecognition();

                //System.out.println("count "+count);

                velocityRecognition.readMidi(str);

                List<Integer> tt = velocityRecognition.getListVelocity();
                //System.out.println("hola");

                if (tt.size() > 0) {
                    if (tt.size() > 1000) {
                        System.out.println("count " + count);
                        System.out.println(str);
                        System.out.println("tam acordes" + tt.size());
                    }

                    HorspoolTestVelocity horspoolTest = new HorspoolTestVelocity();

                    List<HorspoolVelocity> listHorspool = horspoolTest.patternRecognition(tt);
                    List<Integer> colors = horspoolTest.calculateColor(listHorspool, tt);

                    //List<Integer> sequence = getSequence(colors);
                    BufferedImage img = horspoolTest.createImage2(colors);
                    String[] pointData = null;

                    if (str.contains("/")) {
                        pointData = str.split("/"); // linux
                    } else {
                        pointData = str.split("\\\\");
                    }

                    String nomeImageMidi = pointData[pointData.length - 1];
                    nomeImageMidi = nomeImageMidi.substring(0, nomeImageMidi.length() - 4);



                    horspoolTest.saveImage(img, "../../../../image-midi/" + String.valueOf(count) + ".png");

                }

            } catch (Exception e) {
                System.out.println("count " + count);
                System.out.println("erro" + str);

            }
        }

      
    }
    
}
