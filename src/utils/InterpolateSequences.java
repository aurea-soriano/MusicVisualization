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
public class InterpolateSequences {

    public static Float getPendiente(int x0, int y0, int x1, int y1) {
        return (y1 - y0) * 1.f / (x1 - x0) * 1.f;
    }

    public static Integer getY(Float pendiente, int x, int x0, int y0) {
        return Math.round(pendiente * (x - x0) + y0);
    }

    public static void main(String args[]) {
        String format = "";
        String numberAttributes = "";
        String attributes = "";

        String pathData = "/home/aurea/Dropbox/Vispipeline 64/Vispipeline-Trunk/VisPipeline-Aurea/Density/sequencesBeatlesTamanhoNuevoC.data";
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

            }
            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
        }
        System.out.println(maxLength);
        System.out.println("Empezando a escribir");
        try {
            // Create file 
            FileWriter fstream = new FileWriter("/home/aurea/Dropbox/Vispipeline 64/Vispipeline-Trunk/VisPipeline-Aurea/Density/sequencesBeatlesTamanhoNuevoCApprox2.data");
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(format + "\n");
            out.write(listIds.size() + "\n");
            out.write(numberAttributes + "\n");
            out.write(attributes + "\n");



            for (int m = 0; m < listData.size(); m++) {
                out.write(listIds.get(m) + ";");

                int count = 0;
                Integer tam = (int) Math.round(Math.floor(maxLength / (listData.get(m).size() - 1)));
                ArrayList<Integer> listValues = new ArrayList<>();
                //System.out.println(tam);
                for (int n = 0; n < listData.get(m).size() && listValues.size()< maxLength; n++) {
                    if (n == listData.get(m).size() - 1) {
                        if (listValues.size() < maxLength) {
                            int tmp = maxLength - listValues.size();
                            int countFinal = 0;
                            while (countFinal < tmp) {
                                String[] datito = listData.get(m).get(n).split(":");
                                listValues.add(Math.round(Float.valueOf(datito[1])));
                                countFinal++;
                            }
                        }
                    } else {

                        String[] datito = listData.get(m).get(n).split(":");
                        listValues.add(Math.round(Float.valueOf(datito[1])));
                        String[] datito1 = listData.get(m).get(n + 1).split(":");
                        Float pendiente = getPendiente(listValues.size()-1, Math.round(Float.valueOf(datito[1])), listValues.size()-1+tam+1, Math.round(Float.valueOf(datito1[1])));
                        Integer xDatito = listValues.size()-1;
                        Integer yDatito = Math.round(Float.valueOf(datito[1]));
                        int countMedio = 0;
                        while (countMedio < tam && listValues.size()< maxLength) {
                            
                            listValues.add(getY(pendiente,listValues.size() , xDatito, yDatito));
                            countMedio++;
                        }
                    }
                }
                for (int n = 0; n < listValues.size(); n++) {
                     out.write(listValues.get(n) + ".0;");
                }
                System.out.println(listValues.size());
                out.write(listClusters.get(m) + "\n");
            }
            //Close the output stream
            out.close();
            System.out.println("1300");
        } catch (Exception e) {//Catch exception if any
        }

    }
}
