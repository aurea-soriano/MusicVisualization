/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package midi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import structures.LabelMidi;

/**
 *
 * @author aurea
 */
public class Histogram {

    public String getHistogram(List<Integer> list, int scale) {
        List<Integer> listHistogramNotes = new ArrayList<Integer>();
        for (int i = 0; i < scale; i++) {
            listHistogramNotes.add(0);
        }
        for (int i = 0; i < list.size(); i++) {
            int position = list.get(i) % scale;
            int value = listHistogramNotes.get(position) + 1;
            listHistogramNotes.set(position, value);
        }
        String result = "";
        for (int i = 0; i < listHistogramNotes.size(); i++) {

            result = result + ";" + listHistogramNotes.get(i);

        }
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String previous = "";
        String fileName = "listMineracao.txt";

        InputStream fstream = new FileInputStream(fileName);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        int count = 0;
        List<Integer> notes = null;

        List<String> idList = new ArrayList<String>();
        List<String> clusterList = new ArrayList<String>();
        List<String> pathList = new ArrayList<String>();
        List<String> result = new ArrayList<String>();


        while ((strLine = br.readLine()) != null) {
            String[] line = strLine.split(";");


            count++;
            System.out.println(count);
            try {


                Midi midi = new Midi();
                midi.readMidi(line[2]);

                idList.add(line[0]);
                clusterList.add(line[1]);
                pathList.add(line[2]);

                notes = midi.getListNotes();

                //instruments2 = midi.getListInstruments();
                int scaleNotes = 12;

                int numNotes = notes.size();
                Histogram histogram = new Histogram();

                String line2 = String.valueOf(count);

                result.add(histogram.getHistogram(notes, scaleNotes));




                previous = String.valueOf(count) + strLine;
                //System.out.println(count);


            } catch (Exception e) {

                // System.out.println(previous);
                System.out.println(count + " " + strLine);

            }

        }
        System.out.println("Acabo de calcular distancias");
        in.close();
        FileWriter fstreamOut = new FileWriter("histogramMineracao.data");
        FileWriter fstreamOut1 = new FileWriter("histogramMineracao.label");
        BufferedWriter out = new BufferedWriter(fstreamOut);
        BufferedWriter out1 = new BufferedWriter(fstreamOut1);
        out.write("DY\n");
        out.write(String.valueOf(result.size()) + '\n');
        out.write("9\n");
        out.write("mn1;mn2;mn3;mn4;mv1;mv2;mv3;mv4;c\n");
        for (int i = 0; i < result.size(); i++) {
            out1.write(idList.get(i));
            out1.write(";");
            out1.write(pathList.get(i) + '\n');
            out.write(idList.get(i) + ";");

            out.write(result.get(i) + ';');


            out.write(clusterList.get(i));
            out.write('\n');
        }
        out.close();
        out1.close();



    }
}
