/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package midi;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import structures.LabelMidi;

/**
 *
 * @author aurea
 */
public class CreateMidiMoments {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String previous = "";


        String fileName = "listMineracao.txt";

        InputStream fstream = new FileInputStream(fileName);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
        int count = 0;
        List<Integer> notes = null,
                velocities = null, instruments = null;
        List<List<String>> instances = new ArrayList<List<String>>();
        List<LabelMidi> listLabelData = new ArrayList<LabelMidi>();
      

         while ((strLine = br.readLine()) != null) {
             count++;
             System.out.println(count);
            try {
                
                Midi midi = new Midi();
                midi.readMidi(strLine);
                notes = midi.getListNotes();
                velocities = midi.getListVelocities();
                //instruments2 = midi.getListInstruments();
                int scaleNotes = 128;
                int scaleVelocities = 128;
                int scaleInstruments = 128;
                int numNotes = notes.size();
                Moments moments = new Moments();
                List<String> result = new ArrayList<String>();
                String line = String.valueOf(count);
         
                result = moments.getMomentsString(line, notes, scaleNotes, velocities, scaleVelocities, instruments, scaleInstruments);

                listLabelData.add(new LabelMidi(line, strLine));
                instances.add(result);
                
                previous = String.valueOf(count) + strLine;
                 //System.out.println(count);
                
                
            } catch (Exception e) {
                
                // System.out.println(previous);
                System.out.println(count+" "+strLine);
      
            }

        }
        System.out.println("Acabo de calcular distancias");
        in.close();
        FileWriter fstreamOut = new FileWriter("momentsMineracao.data");
        FileWriter fstreamOut1 = new FileWriter("momentsMineracao.label");
        BufferedWriter out = new BufferedWriter(fstreamOut);
        BufferedWriter out1 = new BufferedWriter(fstreamOut1);
        out.write("DY\n");
        out.write(String.valueOf(instances.size()) + '\n');
        out.write("8\n");
        out.write("mn1;mn2;mn3;mn4;mv1;mv2;mv3;mv4\n");
        for (int i = 0; i < instances.size(); i++) {
            out1.write(listLabelData.get(i).getName());
            out1.write(";");
            out1.write(listLabelData.get(i).getPath()+'\n');
            out.write(listLabelData.get(i).getName()+";");
            for (int j = 0; j < instances.get(i).size(); j++) {
                if (j != instances.get(i).size() - 1) {
                    out.write(instances.get(i).get(j) + ';');
                } else {
                    out.write(instances.get(i).get(j));
                }
            }
            out.write('\n');
        }
        out.close();
        out1.close();



    }
}
