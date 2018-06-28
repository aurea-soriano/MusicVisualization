/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author aurea
 */
public class HistogramSequences {

    public static void main(String args[]) {
        String format = "";
        String numberAttributes = "";
        String attributes = "";

        String pathData = "/home/aurea/dataset.data";
        ArrayList<String> listIds = new ArrayList<>();
        ArrayList<ArrayList<String>> listData = new ArrayList<>();
        ArrayList<String> listClusters = new ArrayList<>();
        Integer maxValue = 0;
        try {

            FileInputStream fstream = new FileInputStream(pathData);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            format = br.readLine();
            strLine = br.readLine();
            numberAttributes = br.readLine();
            attributes = br.readLine();
                int count = 0;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                count++; 
            
                // Print the content on the console
                String[] datito = strLine.split(";");
                listIds.add(datito[0]);
                ArrayList<String> listita = new ArrayList<>();

                for (int k = 1; k < datito.length - 2; k++) {
                    String[] minidatito = datito[k].split(":");
                    Float minidatito1 = Float.valueOf(minidatito[1]);
                    
                    
                    if (maxValue < minidatito1) {
                        maxValue = Math.round(minidatito1);
                    }
                    listita.add(datito[k]);
                }
                listData.add(listita);
                listClusters.add(datito[datito.length - 1]);

            }
            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
        }
        System.out.println(maxValue);
        System.out.println("Empezando a escribir");
        try {
            // Create file 
            FileWriter fstream = new FileWriter("datasetHistogramSequences.data");
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(format + "\n");
            out.write(listIds.size() + "\n");
            out.write("177\n");
            out.write(attributes + "\n");

            for (int m = 0; m < listIds.size(); m++) {
               
                out.write(listIds.get(m) + ";");
                
                ArrayList<Integer>  listHistogram = new ArrayList<>();
                for(int n = 0; n< maxValue;n++)
                {
                    listHistogram.add(0);
                }
              
               for (int n = 0; n < listData.get(m).size(); n++) {
                   String [] datito = listData.get(m).get(n).split(":");
                   Integer datito1 = Math.round(Float.valueOf(datito[1]))-1;
                   Integer value = listHistogram.get(datito1)+1; 
                   //System.out.println(value);
                    listHistogram.set(datito1,value);
                }
    
                 for (int n = 0; n < listHistogram.size(); n++) {
                     
                   out.write((listHistogram.get(n)*1.f/listData.get(m).size()*1.f) + ";");
                }
                out.write(listClusters.get(m) + "\n");           
                
            }
            //Close the output stream
            out.close();
            System.out.println(listIds.size());
        } catch (Exception e) {//Catch exception if any
        }
    }
}
