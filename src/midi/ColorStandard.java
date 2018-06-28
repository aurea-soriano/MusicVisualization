/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package midi;

import colors.Aurea;
import colors.CategoryScale;
import colors.PseudoRainbow;
import colors.Rainbow;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author aurea
 */
public class ColorStandard {

    public List<List<Integer>> points = new ArrayList<List<Integer>>();
    public List<String> labels = new ArrayList<String>();
    Integer maxTotal = 0;
    Integer minTotal = Integer.MAX_VALUE;

    public BufferedImage createImage(List<Integer> colors) {
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
            //int color = ((colors.get(i) - min) * 200 / (max - min)) + 55;
            int color = ((colors.get(i) - 1)*(200)/ (maxTotal - 1))+55;
            PseudoRainbow colorRainbow = new PseudoRainbow();
            
            Color[] rgb = new Color[256];
            rgb = colorRainbow.rgb;
            g.setColor(rgb[color]);
            //System.out.println("color "+colors.get(i));

            //System.out.println("("+posCol+", "+posRow+") - ("+(posCol+4)+", "+(posRow+4)+")");
            g.fillRect(posCol, posRow, colors.get(i),sizeSquare);
            g.setColor(Color.BLACK);
            g.fillRect(posCol+colors.get(i)+1, posRow, 4,sizeSquare);
            posCol+=(colors.get(i)+4);
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

    private void uploadDataSet(String fileName) {


        String data[];
        try {
            InputStream fstream = new FileInputStream(fileName);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            strLine = br.readLine(); // 1st line ignored
            strLine = br.readLine(); // 2nd line ignored
            strLine = br.readLine(); // 3rd line ignored
            strLine = br.readLine();
            data = strLine.split(";");

            while ((strLine = br.readLine()) != null) {

                data = strLine.split(";");
                List<Integer> lstRow = new ArrayList<Integer>();
                labels.add(data[0]);
                for (int i = 1; i < data.length - 1; i++) {
                    lstRow.add(Math.round(Float.valueOf(data[i].toString())));
                }

                if (maxTotal < Collections.max(lstRow)) {
                    maxTotal = Collections.max(lstRow);
                }
                if(minTotal > Collections.min(lstRow))
                {
                    minTotal = Collections.min(lstRow);
                }
                points.add(lstRow);
            }



            // Close the input stream
            in.close();

        } catch (Exception e) {// Catch exception if any
        }

    }

    public static void saveImage(BufferedImage img, String ref) {
        try {
            String format = (ref.endsWith(".png")) ? "png" : "jpg";
            ImageIO.write(img, format, new File(ref));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws IOException {

        ColorStandard colorStandard = new ColorStandard();
        colorStandard.uploadDataSet("dataset23.data");
      
        for (int i = 0; i < colorStandard.points.size(); i++) {
           BufferedImage bi = colorStandard.createImage(colorStandard.points.get(i));
           colorStandard.saveImage(bi, "../../../../image-midi3/" + String.valueOf(colorStandard.labels.get(i)) + ".png");
        }


    }
}
