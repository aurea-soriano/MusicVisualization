/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package midi;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author aurea
 */
public class ListMidiDatabase {
    public static List<String> names = new ArrayList<String>();

    public static void lsDirectory(File dir, String formatPrint) {

        File[] archivos = dir.listFiles();
        for (int i = 0; i < archivos.length; i++) {

            if (archivos[i].isDirectory()) {
                lsDirectory(archivos[i], formatPrint + "/" + archivos[i].getName());
            } else {
                //System.out.println(formatPrint + "/" + archivos[i].getName());
                String midi = archivos[i].getName().substring(archivos[i].getName().lastIndexOf(".")+1);
                if(midi.equals("mid")||midi.equals("MID"))
                    names.add(formatPrint + "/" + archivos[i].getName());
            }

        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String path = null;
        path = "../../../../database-midi1400";
      
       
        File dir = new File(path);
        if (!dir.exists()) {
            System.out.println("Directorio no existe");
        } else {
            lsDirectory(dir, path);
        }
        FileWriter fstream = new FileWriter("listSalsa.txt");
        BufferedWriter out = new BufferedWriter(fstream);
        for(int i = 0; i<names.size(); i++)
        {
              out.write(names.get(i)+'\n');
        }      
        out.close();

    }
}
