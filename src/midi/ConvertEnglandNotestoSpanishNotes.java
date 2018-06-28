/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package midi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aurea
 */
public class ConvertEnglandNotestoSpanishNotes {

    public static List<String> names = new ArrayList<String>();

    public static void lsDirectory(File dir, String formatPrint) {

        File[] archivos = dir.listFiles();
        for (int i = 0; i < archivos.length; i++) {

            if (archivos[i].isDirectory()) {
                lsDirectory(archivos[i], formatPrint + "/" + archivos[i].getName());
            } else {
                //System.out.println(formatPrint + "/" + archivos[i].getName());
                String midi = archivos[i].getName().substring(archivos[i].getName().lastIndexOf(".") + 1);
                if (midi.equals("txt") || midi.equals("TXT")) {
                    names.add(formatPrint + "/" + archivos[i].getName());
                }
            }

        }


    }
    
    public static String getConvertNote(String str)
    {
        if(str.contains("A"))
        {
            str = str.replace("A", "LA");
        }
         if(str.contains("E"))
        {
            str = str.replace("E", "MI");
        }
          if(str.contains("D"))
        {
            str = str.replace("D", "RE");
        }
        if(str.contains("C"))
        {
            str = str.replace("C", "DO");
        }
       
       
        if(str.contains("F"))
        {
            str = str.replace("F", "FA");
        }
        if(str.contains("G"))
        {
            str = str.replace("G", "SOL");
        }
        
        if(str.contains("B"))
        {
            str = str.replace("B", "SI");
        }
        if(str.contains("#"))
        {
            str = str.replace("#", "S");
        }
        return str;
    }

    public static void main(String[] args) throws IOException {
        String path = null;
        path = "../../../../Notas";


        File dir = new File(path);
        if (!dir.exists()) {
            System.out.println("Directorio no existe");
        } else {
            lsDirectory(dir, path);
        }

      System.out.println(names.size());
        for (int i = 0; i < names.size(); i++) {
           
            BufferedReader in = new BufferedReader(new FileReader(names.get(i)));
            String[] pointData = null;
            String nameOut;

            if (names.get(i).contains("/")) {
                pointData = names.get(i).split("/"); // linux
            } else {
                pointData = names.get(i).split("\\\\");
            }
          
            String nomeFileOutput = pointData[pointData.length - 1];
            nameOut = nomeFileOutput.substring(0, nomeFileOutput.length() - 4);
               System.out.println(nameOut);

            String str;
            FileWriter outFile = new FileWriter("../../../../NotasSpanish/"+nameOut+".txt");
            PrintWriter out = new PrintWriter(outFile);
            while ((str = in.readLine()) != null) {
                str = getConvertNote(str);
                out.write(str);
            }
            out.close();
            in.close();
        }
    }
}
