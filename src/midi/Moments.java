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
public class Moments {

    float mean, variance, patternDeviation, rNormalized, thirdMoment,
            uniformity, entropy, meanVelocity, varianceVelocity,
            patternDeviationVelocity, rNormalizedVelocity, thirdMomentVelocity,
            uniformityVelocity, entropyVelocity, meanInstrument,
            varianceInstrument, patternDeviationInstrument,
            rNormalizedInstrument, thirdMomentInstrument, uniformityInstrument,
            entropyInstrument;

    public List<Integer> getHistogram(List<Integer> list, int scale) {
        List<Integer> listHistogramNotes = new ArrayList<Integer>();
        for (int i = 0; i < scale; i++) {
            listHistogramNotes.add(0);
        }
        for (int i = 0; i < list.size(); i++) {
            int position = list.get(i);
            int value = listHistogramNotes.get(position) + 1;
            listHistogramNotes.set(position, value);
        }
        return listHistogramNotes;
    }

    public float getMean(List<Integer> list ) {
        float meanTmp = 0.f;
        for (int i = 0; i < list.size(); i++) {
            float pzi = (float)list.get(i)/(float)list.size();
            meanTmp = meanTmp + pzi;
        }

        //mean = mean / scaleNotes;
        return meanTmp;
    }

    public float getVariance(List<Integer> list, List<Integer> listHistogram,float meanTmp) {
        float varianceTmp = 0.f;
        for (int i = 0; i < list.size(); i++) {
            float pzi = (float) listHistogram.get(list.get(i))/(float)list.size();
            varianceTmp = varianceTmp + (float) (Math.pow((list.get(i) - meanTmp), 2)) * pzi ;
        }
        //variance = variance /(float)scaleNotes;

        return varianceTmp;
    }

    public float getPatternDeviation(float varianceTmp) {
        float patternDeviationTmp = (float) Math.sqrt(varianceTmp);
        return patternDeviationTmp;
    }

    public float getRNormalized(float varianceTmp) {
        float rNormalizedTmp = 1.f - (1.f / (1.f + varianceTmp));
        return rNormalizedTmp;
    }

    public float getThirdMoment(List<Integer> list, List<Integer> histogram, float meanTmp) {
        
        float thirdMomentTmp = 0.f;
        for (int i = 0; i < list.size(); i++) {
            float pzi = (float)histogram.get(list.get(i))/(float)list.size();
            thirdMomentTmp = thirdMomentTmp + (float) (Math.pow((list.get(i) - meanTmp), 3)) * pzi ;
        }
        //variance = variance /(float)scaleNotes;
        return thirdMomentTmp;
    }

    public float getUniformity(List<Integer> histogram,Integer sizeList) {
        float uniformityTmp = 0.f;
        for (int i = 0; i < histogram.size(); i++) {
            float pzi = (float)histogram.get(i)/(float)sizeList;
            uniformityTmp = uniformityTmp + (float) Math.pow(pzi, 2);
        }
        return uniformityTmp;
    }

    public static double log2(double num) {
        if (num == 0) {
            return 0;
        }
        return (Math.log10(num) / Math.log10(2));
    }

    public float getEntropy(List<Integer> histogram, Integer sizeList) {
        float entropyTmp = 0.f;
        for (int i = 0; i < histogram.size(); i++) {
            float pzi = (float) histogram.get(i) / (float) sizeList;
            entropyTmp = entropyTmp + pzi * (float) log2((double) pzi);
        }
        return -entropyTmp;
    }

  

    public List<Float> getMoments(List<Integer> notes, int scaleNotes,
            List<Integer> velocities, int scaleVelocities, List<Integer> instruments, int scaleInstruments) {

       
        
        List<Float> result = new ArrayList<Float>();
        List<Integer> notesHistogram = getHistogram(notes, scaleNotes);
        mean = getMean(notes);
        variance = getVariance(notes, notesHistogram, mean);
        patternDeviation = getPatternDeviation(variance);
        rNormalized = getRNormalized(variance);
        thirdMoment = getThirdMoment(notes, notesHistogram,mean);
        uniformity = getUniformity(notesHistogram, notes.size());
        entropy = getEntropy(notesHistogram, notes.size());

        result.add(mean);
        result.add(patternDeviation);
        //result.add(rNormalized);
        //result2.add(thirdMoment);
        result.add(uniformity);
        result.add(entropy);



        List<Integer> velocitiesHistogram = getHistogram(velocities, scaleVelocities);
        meanVelocity = getMean(velocities);
        varianceVelocity = getVariance(velocities,velocitiesHistogram, meanVelocity);
        patternDeviationVelocity = getPatternDeviation(varianceVelocity);
        rNormalizedVelocity = getRNormalized(varianceVelocity);
        thirdMomentVelocity = getThirdMoment(velocities, velocitiesHistogram, meanVelocity);
        uniformityVelocity = getUniformity(velocitiesHistogram, velocities.size());
        entropyVelocity = getEntropy(velocitiesHistogram, velocities.size());

        result.add(meanVelocity);
        result.add(patternDeviationVelocity);
        //result.add(rNormalized);
        //result2.add(thirdMoment);
        result.add(uniformityVelocity);
        result.add(entropyVelocity);




        // meanInstrument = getMean(instruments);
        // varianceInstrument = getVariance(instruments, meanInstrument);
        // patternDeviationInstrument = getPatternDeviation(varianceInstrument);
        // rNormalizedInstrument = getRNormalized(varianceInstrument);
        // thirdMomentInstrument = getThirdMoment(instruments, meanInstrument);
        //uniformityInstrument = getUniformity(instruments);
        // entropyInstrument = getEntropy(instruments);

        //result.add(meanInstrument);
        //result.add(patternDeviationInstrument);
        //result.add(rNormalizedInstrument);
        //result2.add(thirdMomentInstrument);
        //result.add(uniformityInstrument);
        //result.add(entropyInstrument);

        return result;
    }

    public void print() {
        System.out.println("Notas");
        System.out.println("Media " + mean);
        System.out.println("Varianza " + variance);
        System.out.println("Desvío Padrón " + patternDeviation);
        //System.out.println("R normalizado " + rNormalized);
        //System.out.println("Tercer momento " + thirdMoment);
        System.out.println("Uniformidad " + uniformity);
        System.out.println("Entropia " + entropy);

        System.out.println("Velocidad");
        System.out.println("Media Velocidad " + meanVelocity);
        //System.out.println("Varianza " + variance);
        System.out.println("Desvío Padrón Velocidad " + patternDeviationVelocity);
        //System.out.println("R normalizado " + rNormalized);
        //System.out.println("Tercer momento " + thirdMoment);
        System.out.println("Uniformidad Velocidad " + uniformityVelocity);
        System.out.println("Entropia Velocidad" + entropyVelocity);

        // System.out.println("Instrumentos");
        //  System.out.println("Media Instrumentos " + meanInstrument);
        //System.out.println("Varianza " + variance);
        //  System.out.println("Desvío Padrón Instrumentos " + patternDeviationInstrument);
        //System.out.println("R normalizado " + rNormalized);
        //System.out.println("Tercer momento " + thirdMoment);
        // System.out.println("Uniformidad Instrumentos " + uniformityInstrument);
        // System.out.println("Entropia Instrumentos" + entropyInstrument);
    }
    
    
    
    
    public List<String> getMomentsString(String name, List<Integer> notes, int scaleNotes,
            List<Integer> velocities, int scaleVelocities, List<Integer> instruments, int scaleInstruments) {

        List<String> result = new ArrayList<String>();
        int numDecim = 2;
        //String subName = name.substring(name.lastIndexOf('/')+1, name.lastIndexOf('.'));
        //try
        //{
        //subName = subName.substring(name.lastIndexOf(' ')+1);
        //}
        //catch(Exception e)
        //{
            
        //}
        //result.add(subName);
       // result.add(name);
             
        
        
        List<Integer> notesHistogram = getHistogram(notes, scaleNotes);
        mean = getMean(notes);
        variance = getVariance(notes, notesHistogram, mean);
        patternDeviation = getPatternDeviation(variance);
        rNormalized = getRNormalized(variance);
        thirdMoment = getThirdMoment(notes, notesHistogram,mean);
        uniformity = getUniformity(notesHistogram, notes.size());
        entropy = getEntropy(notesHistogram, notes.size());

        result.add(String.valueOf(roundTo(mean,numDecim)));
        result.add(String.valueOf(roundTo(patternDeviation,numDecim)));
        //result.add(rNormalized);
        //result2.add(thirdMoment);
        result.add(String.valueOf(roundTo(uniformity,numDecim)));
        result.add(String.valueOf(roundTo(entropy,numDecim)));



        List<Integer> velocitiesHistogram = getHistogram(velocities, scaleVelocities);
        meanVelocity = getMean(velocities);
        varianceVelocity = getVariance(velocities,velocitiesHistogram, meanVelocity);
        patternDeviationVelocity = getPatternDeviation(varianceVelocity);
        rNormalizedVelocity = getRNormalized(varianceVelocity);
        thirdMomentVelocity = getThirdMoment(velocities, velocitiesHistogram, meanVelocity);
        uniformityVelocity = getUniformity(velocitiesHistogram, velocities.size());
        entropyVelocity = getEntropy(velocitiesHistogram, velocities.size());

        result.add(String.valueOf(roundTo(meanVelocity,numDecim)));
        result.add(String.valueOf(roundTo(patternDeviationVelocity,numDecim)));
        //result.add(rNormalized);
        //result2.add(thirdMoment);
        result.add(String.valueOf(roundTo(uniformityVelocity,numDecim)));
        result.add(String.valueOf(roundTo(entropyVelocity,numDecim)));




        // meanInstrument = getMean(instruments);
        // varianceInstrument = getVariance(instruments, meanInstrument);
        // patternDeviationInstrument = getPatternDeviation(varianceInstrument);
        // rNormalizedInstrument = getRNormalized(varianceInstrument);
        // thirdMomentInstrument = getThirdMoment(instruments, meanInstrument);
        //uniformityInstrument = getUniformity(instruments);
        // entropyInstrument = getEntropy(instruments);

        //result.add(meanInstrument);
        //result.add(patternDeviationInstrument);
        //result.add(rNormalizedInstrument);
        //result2.add(thirdMomentInstrument);
        //result.add(uniformityInstrument);
        //result.add(entropyInstrument);

        return result;
    }

     public static double roundTo(double num, Integer value) {
        double result = num * Math.pow(10, value);
        result = Math.round(result);
        result = result / Math.pow(10, value);
        return result;
    }
  public static void main(String[] args) throws FileNotFoundException, IOException {
        String previous = "";


        String fileName = "id_cluster_path.txt";

        InputStream fstream = new FileInputStream(fileName);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
        int count = 0;
        List<Integer> notes = null,
                velocities = null, instruments = null;
        List<List<String>> instances = new ArrayList<List<String>>();
        List<LabelMidi> listLabelData = new ArrayList<LabelMidi>();
      

	  List<String> idList=new ArrayList<String>();
	  List<String> clusterList= new ArrayList<String>();
	  List<String> pathList = new ArrayList<String>();
		

         while ((strLine = br.readLine()) != null) 
		 {
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
                velocities = midi.getListVelocities();
                //instruments2 = midi.getListInstruments();
                int scaleNotes = 128;
                int scaleVelocities = 128;
                int scaleInstruments = 128;
                int numNotes = notes.size();
                Moments moments = new Moments();
                List<String> result = new ArrayList<String>();
                String line2 = String.valueOf(count);
         
                result = moments.getMomentsString(line2, notes, scaleNotes, velocities, scaleVelocities, instruments, scaleInstruments);

                listLabelData.add(new LabelMidi(line2, strLine));
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
        FileWriter fstreamOut = new FileWriter("midiMoments2.data");
        FileWriter fstreamOut1 = new FileWriter("midiMoments2.label");
        BufferedWriter out = new BufferedWriter(fstreamOut);
        BufferedWriter out1 = new BufferedWriter(fstreamOut1);
        out.write("DY\n");
        out.write(String.valueOf(instances.size()) + '\n');
        out.write("9\n");
        out.write("mn1;mn2;mn3;mn4;mv1;mv2;mv3;mv4;c\n");
        for (int i = 0; i < instances.size(); i++) {
            out1.write(idList.get(i));
            out1.write(";");
            out1.write( pathList.get(i)  +'\n');
           	out.write(idList.get(i)+";" );
		   for (int j = 0; j < instances.get(i).size(); j++) {
                if (j != instances.get(i).size() - 1) {
                    out.write(instances.get(i).get(j) + ';');
                } else 
				{
                    out.write(instances.get(i).get(j));
                }
            }

			out.write(";"+clusterList.get(i));
            out.write('\n');
        }
        out.close();
        out1.close();



    }
}
