/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author aurea
 */
public class ExtractOutliers {

    public static void main(String args[]) {
        String pathLabels = "/home/aurea/Dropbox/data_midi/Sequencias/sequences2013.label";
        ArrayList<String> listLabels = new ArrayList<>();

        try {
            // Open the file that is the first 
            // command line parameter
            FileInputStream fstream = new FileInputStream(pathLabels);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                listLabels.add(strLine);
            }
            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
        }

        String pathExtraction = "/home/aurea/sacar";
        ArrayList<String> listExtraction = new ArrayList<>();

        try {
            // Open the file that is the first 
            // command line parameter
            FileInputStream fstream = new FileInputStream(pathExtraction);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                listExtraction.add(strLine);
            }
            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
        }


        String format="";
        String numberAttributes="";
        String attributes="";

        String pathData = "/home/aurea/Dropbox/data_midi/Sequencias/mini_dataset/1400-clasica-sertanejo-rock/dataset.data";
        ArrayList<String> listData = new ArrayList<>();

        try {
            // Open the file that is the first 
            // command line parameter
            FileInputStream fstream = new FileInputStream(pathData);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            format = br.readLine();
            strLine = br.readLine();
            numberAttributes = br.readLine();
            attributes = br.readLine();
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                listData.add(strLine);
            }
            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
        }
        System.out.println(listData.size());

        ArrayList<String> newListData = new ArrayList<>();
        for (int i = 0; i < listData.size(); i++) {
            String[] datito = listData.get(i).split(";");
            String labelDatito = "";
            for (int j = 0; j < listLabels.size(); j++) {
                String[] labelito = listLabels.get(j).split(";");

                Integer value1 = Integer.valueOf(datito[0]);
                Integer value2 = Integer.valueOf(labelito[0]);

                if (value1.equals(value2)) {
                    labelDatito = labelito[1];
                }

            }
            if (labelDatito != "") {
                Boolean estaDentro = false;
                for (int k = 0; k < listExtraction.size(); k++) {
                    if ((labelDatito.contains(listExtraction.get(k) + ".mid"))|| (labelDatito.contains(listExtraction.get(k) + ".MID"))) {
                        estaDentro = true;
                        k = listExtraction.size();
                    }
                }
                if (estaDentro == false) {
                    newListData.add(listData.get(i));
                }
            }

        }
        try {
            // Create file 
            FileWriter fstream = new FileWriter("mini_dataset2.data");
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(format+"\n");
            out.write(newListData.size()+"\n");
            out.write(numberAttributes+"\n");
            out.write(attributes+"\n");
            for(int m =0; m< newListData.size(); m++)
            {
                out.write(newListData.get(m)+"\n");
            }
            //Close the output stream
            out.close();
        } catch (Exception e) {//Catch exception if any
            
        }
         System.out.println(newListData.size());

    }
}
