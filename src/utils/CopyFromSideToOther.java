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
import java.util.List;

/**
 *
 * @author aurea
 */
public class CopyFromSideToOther {

    public static void main(String args[]) {
        List<Integer> listIndexAntigua = new ArrayList<>();
        List<String> listSequenceAntigua = new ArrayList<>();
        List<Integer> listIndexPathAntigua = new ArrayList<>();
        List<String> listPathAntigua = new ArrayList<>();

        String formatAntigua = "";
        String numberAttributesAntigua = "";
        String attributesAntigua = "";

        String pathSequencesAntigua = "/home/aurea/Dropbox/data_midi/Sequencias/mini_dataset/1396-clasica-sertanejo-rock-jazz/data-escalada.data";
        String pathLabelsAntigua = "/home/aurea/Dropbox/data_midi/Sequencias/mini_dataset/sequences2013.label";
       
        
        List<Integer> listIndexNueva = new ArrayList<>();
        List<Integer> listIndexPathNueva = new ArrayList<>();
        List<String> listPathNueva = new ArrayList<>();
        
        String formatNueva = "";
        String numberAttributesNueva = "";
        String attributesNueva = "";
        
        String pathSequencesNueva= "/home/aurea/Dropbox/Vispipeline 64/Vispipeline-Trunk/VisPipeline-Aurea/Density/sequencesConJazzSinAprox.data";
        String pathLabelsNueva = "/home/aurea/Dropbox/Vispipeline 64/Vispipeline-Trunk/VisPipeline-Aurea/Density/sequencesConJazzSinAprox.label";
        
        try {

            FileInputStream fstream = new FileInputStream(pathSequencesAntigua);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            formatAntigua = br.readLine();
            strLine = br.readLine();
            numberAttributesAntigua = br.readLine();
            attributesAntigua = br.readLine();

            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                String[] data = strLine.split(";");
                listIndexAntigua.add(Integer.valueOf(data[0]));
                String newData = "";
                for(int i=1; i<data.length;i++)
                {
                    newData+=";"+data[i];
                }
                listSequenceAntigua.add(newData);
            }
            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
        }
        
        try {

            FileInputStream fstream = new FileInputStream(pathLabelsAntigua);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
      
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                String[] data = strLine.split(";");
                listIndexPathAntigua.add(Integer.valueOf(data[0]));
                listPathAntigua.add(data[1]);
            }
            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
        }
        
        
          try {

            FileInputStream fstream = new FileInputStream(pathSequencesNueva);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            formatNueva = br.readLine();
            strLine = br.readLine();
            numberAttributesNueva = br.readLine();
            attributesNueva = br.readLine();

            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                String[] data = strLine.split(";");
                listIndexNueva.add(Integer.valueOf(data[0]));
                String newData = "";
                for(int i=1; i<data.length;i++)
                {
                    newData+=";"+data[i];
                }
                //listSequenceNueva.add(newData);
            }
            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
        }
        
        try {

            FileInputStream fstream = new FileInputStream(pathLabelsNueva);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
      
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                String[] data = strLine.split(";");
                listIndexPathNueva.add(Integer.valueOf(data[0]));
                listPathNueva.add(data[1]);
            }
            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
        }
        
        System.out.println("Comenzando a escribir....");
        
         try {
            // Create file 
            FileWriter fstream = new FileWriter("/home/aurea/sequencesNewIcons1388ConJazz.data");
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(formatAntigua + "\n");
            out.write(listIndexNueva.size() + "\n");
            out.write(numberAttributesAntigua + "\n");
            out.write(attributesAntigua + "\n");


            int count=0;
            for(int i=0; i<listIndexNueva.size(); i++)
            {
                String pathNuevo ="";
                
                for(int j=0; j<listIndexPathNueva.size(); j++)
                {
                    if(listIndexNueva.get(i).equals(listIndexPathNueva.get(j)))
                    {
                        pathNuevo = listPathNueva.get(j);
                        break;
                    }
                }
                
                String subPathNuevo = pathNuevo.substring(pathNuevo.lastIndexOf("/")+1);
                subPathNuevo = subPathNuevo.substring(subPathNuevo.indexOf("_")+1);
                Integer indexAntiguo=-1;
                
                 for(int j=0; j<listIndexPathAntigua.size(); j++)
                {
                    String subPathAntiguoTmp= listPathAntigua.get(j).substring(listPathAntigua.get(j).lastIndexOf("database-midi/")+14);
                    subPathAntiguoTmp= subPathAntiguoTmp.replace("/","_");
                    subPathAntiguoTmp= subPathAntiguoTmp.replace("(","");
                    subPathAntiguoTmp= subPathAntiguoTmp.replace(")","");
                    subPathAntiguoTmp= subPathAntiguoTmp.replace(" ","_");
                    subPathAntiguoTmp= subPathAntiguoTmp.replace("'","");
                    subPathAntiguoTmp = subPathAntiguoTmp.substring(subPathAntiguoTmp.indexOf("_")+1);
                    //System.out.println(subPathAntiguoTmp);
                    //System.out.println(subPathAntiguoTmp);
                    //System.out.println(subPathNuevo +" - "+ subPathAntiguoTmp);
                    
                    if(subPathNuevo.indexOf(subPathAntiguoTmp)!=-1)
                    {
                        count++;
                        System.out.println(count);
                        indexAntiguo = listIndexPathAntigua.get(j);
                        break;
                    }
                }
                
                String sequenceAntigua ="";
              
                for(int j=0; j<listIndexAntigua.size();j++)
                {
                    if(listIndexAntigua.get(j).equals(indexAntiguo))
                    {
                        sequenceAntigua = listSequenceAntigua.get(j);
                    }
                }
                out.write(listIndexNueva.get(i)+sequenceAntigua+"\n");
                
            
                
            }
            //Close the output stream
            out.close();
            
        } catch (Exception e) {//Catch exception if any
        }

          try {
            // Create file 
            FileWriter fstream = new FileWriter("/home/aurea/sequencesNewIcons1388ConJazz.label");
            BufferedWriter out = new BufferedWriter(fstream);
            

            for(int i=0; i<listIndexNueva.size(); i++)
            {
                
                 out.write(listIndexNueva.get(i)+";"+listPathNueva.get(i)+"\n");
                 
                 
                
            }
            //Close the output stream
            out.close();
            
        } catch (Exception e) {//Catch exception if any
        }
        
    }
}
