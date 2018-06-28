/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package midi;

import colors.PseudoRainbow;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author aurea64
 */
public class CreateIconeMidi {

    public List<List<Integer>> points = new ArrayList<List<Integer>>();
    public List<String> labels = new ArrayList<String>();


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
                for (int i = 1; i < data.length; i++) {
                    lstRow.add(Integer.valueOf(data[i]));
                }
               
                points.add(lstRow);
            }



            // Close the input stream
            in.close();

        } catch (Exception e) {// Catch exception if any
        }

    }

    public void createIconeMidi() {
       
        for (int i = 0; i < points.size(); i++) {
           
             int countColor =20 ;
            PseudoRainbow colorRainbow = new PseudoRainbow();
            Color[] rgb = new Color[256];
            rgb = colorRainbow.rgb;
           
            int maxHistogram, minHistogram;
            maxHistogram = Collections.max(points.get(i));
            minHistogram = Collections.min(points.get(i));
            BufferedImage img = new BufferedImage(100, 48, BufferedImage.TYPE_3BYTE_BGR);
            img.createGraphics();
            Graphics2D g = (Graphics2D) img.getGraphics();
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 100, 48);
            
            for (int j = 0; j < points.get(i).size(); j++) {
                g.setColor(rgb[countColor]);
                int porcentage;
                if(maxHistogram!=0)
                {
                    porcentage =(int) Math.round((points.get(i).get(j)*100)/(float)maxHistogram);
                }
                else
                {
                    porcentage = (int) Math.round((points.get(i).get(j)*100)/0.000001);
                }
                g.fillRect(0,j*4,porcentage,4);
                 countColor+=20;
                
            }
            this.saveImage(img, "../../../../image-midi3/" + labels.get(i) + ".png");
           
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

    public static void main(String[] args) throws FileNotFoundException, IOException {

        CreateIconeMidi createIconeMidi = new CreateIconeMidi();
        createIconeMidi.uploadDataSet("../../../../Dropbox/dados chords midi/Histograma/histogramdb.data");
        createIconeMidi.createIconeMidi();
        System.out.println(createIconeMidi.points.size());

    }
}
