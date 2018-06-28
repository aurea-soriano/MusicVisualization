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
public class ScaleSequences {

    public static void main(String args[]) {
        String format = "";
        String numberAttributes = "";
        String attributes = "";

        String pathData = "/home/aurea/Dropbox/data_midi/Sequencias/new_icons/sequenceSinjazz.data";
        ArrayList<String> listIds = new ArrayList<>();
        ArrayList<ArrayList<String>> listData = new ArrayList<>();
        ArrayList<String> listClusters = new ArrayList<>();
        Integer maxLength = 0;
        try {

            FileInputStream fstream = new FileInputStream(pathData);
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
                String[] datito = strLine.split(";");
                listIds.add(datito[0]);
                ArrayList<String> listita = new ArrayList<>();
                if (maxLength < datito.length - 2) {
                    maxLength = datito.length - 2;
                }
                for (int k = 1; k < datito.length - 1; k++) {
                    listita.add(datito[k]);
                }
                listData.add(listita);
                listClusters.add(datito[datito.length - 1]);
                System.out.println(listData.size());

            }
            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
        }
        System.out.println("Empezando a escribir");
        try {
            // Create file 
            FileWriter fstream = new FileWriter("/home/aurea/Dropbox/data_midi/Sequencias/new_icons/sequenceSinjazzEscalada.data");
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(format + "\n");
            out.write(listIds.size() + "\n");
            out.write(numberAttributes + "\n");
            out.write(attributes + "\n");
          
            for (int m = 0; m < listIds.size(); m++) {
                //System.out.println(m);
                Float divi = (maxLength *1.f)/ (listData.get(m).size()*1.f);
                Integer repet = (int)Math.floor(divi);
                
                out.write(listIds.get(m) + ";");
                 
                int countFila = 0;
                int index = 1;
                for (int n = 0; n < listData.get(m).size(); n++) {
                    if (countFila>=maxLength|| (n== listData.get(m).size()-1)) {
                        if(countFila>=maxLength)
                        {
                            repet = 0;
                        }
                        else
                        {
                            repet = maxLength - countFila;
                        }
                      
                        System.out.println("excedente: "+repet);
                    }
                    else
                    {
                        float tmp3 = (divi)/Math.round(divi);
                        if(tmp3>1.4 && tmp3<1.7)
                        {
                            if(n%2==0)
                            {
                                repet = (int)Math.floor(divi);
                            }
                            else
                            {
                                 repet = (int)Math.ceil(divi);
                            }
                            
                        }
                    }
                    int count = 0;
                    countFila = countFila+repet;
                    while (count < repet) {
                        String [] dato = listData.get(m).get(n).split(":");
                        out.write( index+":"+dato[1]+";");
                        index++;
                        count++;
                    }

                }
                out.write(
                        listClusters.get(m) + 
                        "\n");
                System.out.println(countFila);
                System.out.println("****");
            }
            //Close the output stream
            out.close();
        
        } catch (Exception e) {//Catch exception if any
        }

    }
}
