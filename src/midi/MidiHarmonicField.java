/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package midi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 *
 * @author aurea
 */
public class MidiHarmonicField 
{

    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;
    public static final int PATCH_CHANGE = 0xC0;
    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    //public static final String[] NOTE_NAMES = {"DO", "DOS", "RE", "RES", "MI", "FA", "FAS", "SOL", "SOLS", "LA", "LAS", "SI"};
    public List<String> listNotes = new ArrayList<String>();
    public List<Integer> listVelocities = new ArrayList<Integer>();
    public List<Integer> listInstruments = new ArrayList<Integer>();
    public List< List< String>> listHarmonicField = new ArrayList< List<String>>();
    public List<String> listNomeNotes = new ArrayList<String>();

    public void createListHarmonicField() {
        //mayores
        // C
        listNomeNotes.add("C");
        List<String> harmonicField = new ArrayList<String>();
        harmonicField.add("C");
        harmonicField.add("Dm");
        harmonicField.add("Em");
        harmonicField.add("F");
        harmonicField.add("G");
        harmonicField.add("Am");
        harmonicField.add("A#");
        harmonicField.add("C");
        this.listHarmonicField.add(harmonicField);

        // C#
        listNomeNotes.add("C#");
        harmonicField = new ArrayList<String>();
        harmonicField.add("C#");
        harmonicField.add("D#m");
        harmonicField.add("Fm");
        harmonicField.add("F#");
        harmonicField.add("G#");
        harmonicField.add("A#m");
        harmonicField.add("B");
        harmonicField.add("C#");
        this.listHarmonicField.add(harmonicField);

        // D
        listNomeNotes.add("D");
        harmonicField = new ArrayList<String>();
        harmonicField.add("D");
        harmonicField.add("Em");
        harmonicField.add("F#m");
        harmonicField.add("G");
        harmonicField.add("A");
        harmonicField.add("Bm");
        harmonicField.add("C");
        harmonicField.add("D");
        this.listHarmonicField.add(harmonicField);

        // D#
        listNomeNotes.add("D#");
        harmonicField = new ArrayList<String>();
        harmonicField.add("D#");
        harmonicField.add("Fm");
        harmonicField.add("Gm");
        harmonicField.add("G#");
        harmonicField.add("A#");
        harmonicField.add("Cm");
        harmonicField.add("C#");
        harmonicField.add("D#");
        this.listHarmonicField.add(harmonicField);

        // E
        listNomeNotes.add("E");
        harmonicField = new ArrayList<String>();
        harmonicField.add("E");
        harmonicField.add("F#m");
        harmonicField.add("G#m");
        harmonicField.add("A");
        harmonicField.add("B");
        harmonicField.add("C#m");
        harmonicField.add("D");
        harmonicField.add("E");
        this.listHarmonicField.add(harmonicField);

        // F
        listNomeNotes.add("F");
        harmonicField = new ArrayList<String>();
        harmonicField.add("F");
        harmonicField.add("Gm");
        harmonicField.add("Am");
        harmonicField.add("A#");
        harmonicField.add("C");
        harmonicField.add("Dm");
        harmonicField.add("D#");
        harmonicField.add("F");
        this.listHarmonicField.add(harmonicField);

        // F#
        listNomeNotes.add("F#");
        harmonicField = new ArrayList<String>();
        harmonicField.add("F#");
        harmonicField.add("G#m");
        harmonicField.add("A#m");
        harmonicField.add("B");
        harmonicField.add("C#");
        harmonicField.add("D#m");
        harmonicField.add("E");
        harmonicField.add("F#");
        this.listHarmonicField.add(harmonicField);

        // G
        listNomeNotes.add("G");
        harmonicField = new ArrayList<String>();
        harmonicField.add("G");
        harmonicField.add("Am");
        harmonicField.add("Bm");
        harmonicField.add("C");
        harmonicField.add("D");
        harmonicField.add("Em");
        harmonicField.add("F");
        harmonicField.add("G");
        this.listHarmonicField.add(harmonicField);

        // G#
        listNomeNotes.add("G#");
        harmonicField = new ArrayList<String>();
        harmonicField.add("G#");
        harmonicField.add("A#m");
        harmonicField.add("Cm");
        harmonicField.add("C#");
        harmonicField.add("D#");
        harmonicField.add("Fm");
        harmonicField.add("F#");
        harmonicField.add("G#");
        this.listHarmonicField.add(harmonicField);
        // A
        listNomeNotes.add("A");
        harmonicField = new ArrayList<String>();
        harmonicField.add("A");
        harmonicField.add("Bm");
        harmonicField.add("C#m");
        harmonicField.add("D");
        harmonicField.add("E");
        harmonicField.add("F#m");
        harmonicField.add("G");
        harmonicField.add("A");
        this.listHarmonicField.add(harmonicField);


        // A#
        listNomeNotes.add("A#");
        harmonicField = new ArrayList<String>();
        harmonicField.add("A#");
        harmonicField.add("Cm");
        harmonicField.add("Dm");
        harmonicField.add("D#");
        harmonicField.add("F");
        harmonicField.add("Gm");
        harmonicField.add("G#");
        harmonicField.add("A#");
        this.listHarmonicField.add(harmonicField);

        // B
        listNomeNotes.add("B");
        harmonicField = new ArrayList<String>();
        harmonicField.add("B");
        harmonicField.add("C#m");
        harmonicField.add("D#m");
        harmonicField.add("E");
        harmonicField.add("F#");
        harmonicField.add("G#m");
        harmonicField.add("A");
        harmonicField.add("B");
        this.listHarmonicField.add(harmonicField);


        //mayores
        // Cm
        listNomeNotes.add("Cm");
        harmonicField = new ArrayList<String>();
        harmonicField.add("Cm");
        harmonicField.add("C#");
        harmonicField.add("D#");
        harmonicField.add("Fm");
        harmonicField.add("Gm");
        harmonicField.add("G#");
        harmonicField.add("A#");
        harmonicField.add("Cm");
        this.listHarmonicField.add(harmonicField);

        // C#m
        listNomeNotes.add("C#m");
        harmonicField = new ArrayList<String>();
        harmonicField.add("C#m");
        harmonicField.add("D");
        harmonicField.add("E");
        harmonicField.add("F#m");
        harmonicField.add("G#m");
        harmonicField.add("A");
        harmonicField.add("B");
        harmonicField.add("C#m");
        this.listHarmonicField.add(harmonicField);

        // Dm
        listNomeNotes.add("Dm");
        harmonicField = new ArrayList<String>();
        harmonicField.add("Dm");
        harmonicField.add("D#");
        harmonicField.add("F");
        harmonicField.add("Gm");
        harmonicField.add("Am");
        harmonicField.add("A#");
        harmonicField.add("C");
        harmonicField.add("Dm");
        this.listHarmonicField.add(harmonicField);

        // D#m
        listNomeNotes.add("D#m");
        harmonicField = new ArrayList<String>();
        harmonicField.add("D#m");
        harmonicField.add("E");
        harmonicField.add("F#");
        harmonicField.add("G#m");
        harmonicField.add("A#m");
        harmonicField.add("B");
        harmonicField.add("C#");
        harmonicField.add("D#m");
        this.listHarmonicField.add(harmonicField);

        // Em
        listNomeNotes.add("Em");
        harmonicField = new ArrayList<String>();
        harmonicField.add("Em");
        harmonicField.add("F");
        harmonicField.add("G");
        harmonicField.add("Am");
        harmonicField.add("Bm");
        harmonicField.add("C");
        harmonicField.add("D");
        harmonicField.add("Em");
        this.listHarmonicField.add(harmonicField);

        // Fm
        listNomeNotes.add("Fm");
        harmonicField = new ArrayList<String>();
        harmonicField.add("Fm");
        harmonicField.add("F#");
        harmonicField.add("G#");
        harmonicField.add("A#m");
        harmonicField.add("Cm");
        harmonicField.add("C#");
        harmonicField.add("D#");
        harmonicField.add("Fm");
        this.listHarmonicField.add(harmonicField);

        // F#m
        listNomeNotes.add("F#m");
        harmonicField = new ArrayList<String>();
        harmonicField.add("F#m");
        harmonicField.add("G");
        harmonicField.add("A");
        harmonicField.add("Bm");
        harmonicField.add("C#m");
        harmonicField.add("D");
        harmonicField.add("E");
        harmonicField.add("F#m");
        this.listHarmonicField.add(harmonicField);
        
        // Gm
        listNomeNotes.add("Gm");
        harmonicField = new ArrayList<String>();
        harmonicField.add("Gm");
        harmonicField.add("G#");
        harmonicField.add("A#");
        harmonicField.add("Cm");
        harmonicField.add("Dm");
        harmonicField.add("D#");
        harmonicField.add("F");
        harmonicField.add("Gm");
        this.listHarmonicField.add(harmonicField);

        // G#m
        listNomeNotes.add("G#m");
        harmonicField = new ArrayList<String>();
        harmonicField.add("G#m");
        harmonicField.add("A");
        harmonicField.add("B");
        harmonicField.add("C#m");
        harmonicField.add("D#m");
        harmonicField.add("E");
        harmonicField.add("F#");
        harmonicField.add("G#m");
        this.listHarmonicField.add(harmonicField);
        
        // Am
        listNomeNotes.add("Am");
        harmonicField = new ArrayList<String>();
        harmonicField.add("Am");
        harmonicField.add("A#");
        harmonicField.add("C");
        harmonicField.add("Dm");
        harmonicField.add("Em");
        harmonicField.add("F");
        harmonicField.add("G");
        harmonicField.add("Am");
        this.listHarmonicField.add(harmonicField);

        // A#m
        listNomeNotes.add("A#m");
        harmonicField = new ArrayList<String>();
        harmonicField.add("A#m");
        harmonicField.add("B");
        harmonicField.add("C#");
        harmonicField.add("D#m");
        harmonicField.add("Fm");
        harmonicField.add("F#");
        harmonicField.add("G#");
        harmonicField.add("A#m");
        this.listHarmonicField.add(harmonicField);

        // Bm
        listNomeNotes.add("Bm");
      harmonicField = new ArrayList<String>();
        harmonicField.add("Bm");
        harmonicField.add("C");
        harmonicField.add("D");
        harmonicField.add("Em");
        harmonicField.add("F#m");
        harmonicField.add("G");
        harmonicField.add("A");
        harmonicField.add("Bm");
        this.listHarmonicField.add(harmonicField);


    }

    public List<String> getListNotes() {
        return listNotes;
    }

    public List<Integer> getListVelocities() {
        return listVelocities;
    }

    public List<Integer> getListInstruments() {
        return listInstruments;
    }

    public void readMidi(String midiName) throws Exception {
        Sequence sequence = MidiSystem.getSequence(new File(midiName));
        int trackNumber = 0;
        int previousInstrument = -1;
        for (Track track : sequence.getTracks()) {
            trackNumber++;
            // System.out.println("Track " + trackNumber + ": size = " + track.size());
            //System.out.println();
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                //System.out.print("@" + event.getTick() + " ");
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    
                    //System.out.println("Channel: " + sm.getChannel() + " ");
                    if (sm.getCommand() == NOTE_ON) {
                        int key = sm.getData1();
                        int octave = (key / 12) - 1;
                        int note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();
                        //System.out.println("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);
                        //listNotes.add(noteName+octave);
                        //listNotes.add(key);
                        listNotes.add(noteName);
                        listVelocities.add(velocity);
                        //System.out.println(key);
                        //listInstruments.add(previousInstrument);
                        //System.out.println(previousInstrument);

                    } else if (sm.getCommand() == NOTE_OFF) {

                        int key = sm.getData1();
                        int octave = (key / 12) - 1;
                        int note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();
                       // System.out.println("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity);
                        //listNotes.add(noteName+octave);
                        //listNotes.add(key);
                        listNotes.add(noteName);
                        listVelocities.add(velocity);
                        //System.out.println(key);
                        //listInstruments.add(previousInstrument);
                        //System.out.println(previousInstrument);


                    } else if (sm.getCommand() == PATCH_CHANGE) {
                        int instrument = sm.getData1();
                        // Synthesizer synth = MidiSystem.getSynthesizer();
                        // synth.open();
                        // Instrument[] instr = synth.getDefaultSoundbank().getInstruments();
                        //System.out.println(instr[instrument]);  //
                        previousInstrument = instrument;
                        listInstruments.add(instrument);
                    } else {
                        // System.out.println("Command:" + sm.getCommand());
                    }
                } else {
                    //System.out.println("Other message: " + message.getClass());
                }
            }

            //System.out.println();
        }

    }

    public List<Integer> getSimplifyMidi(List<Integer> listNotes) {
        List<Integer> listSimplifyNotes = new ArrayList<Integer>();
        for (int i = 2; i < listNotes.size(); i++) {
            listSimplifyNotes.add(listNotes.get(i) - listNotes.get(i - 1));
        }
        return listSimplifyNotes;
    }

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

    public List<Integer> getHistogram(List<Integer> listNotes, int scaleNotes, List<Integer> listVelocities) {
        List<Integer> listHistogramNotes = new ArrayList<Integer>();
        for (int i = 0; i < scaleNotes; i++) {
            listHistogramNotes.add(0);
        }
        for (int i = 0; i < listNotes.size(); i++) {
            int position = listNotes.get(i);
            int velocity = listVelocities.get(i);
            int value = listHistogramNotes.get(position) + velocity;
            listHistogramNotes.set(position, value);
        }
        return listHistogramNotes;
    }

    public List<Integer> getListIndex(String note, List<Integer> previousListIndex) {
        List<Integer> listIndex = new ArrayList<Integer>();
        for (int i = 0; i < previousListIndex.size(); i++) {
         
            if (listHarmonicField.get(previousListIndex.get(i)).contains(note)) {
                  //System.out.println(note+" :: "+listTriads.get(previousListIndex.get(i)));
                listIndex.add(previousListIndex.get(i));
            }
        }
        return listIndex;

    }

    public List<String> getListChords() {
        boolean statusAnalise = false; //false acabou de analizar ou nao esta analizando & true em processo.
        List<Integer> listIndex = new ArrayList<Integer>();
        List<Integer> previousListIndex = new ArrayList<Integer>();
        List<String> listIndexResult = new ArrayList<String>();

        for (int i = 0; i < listNotes.size(); i++) {

            if (statusAnalise == false) {
                //System.out.println("Entro por quiebre o por nada");
                listIndex = new ArrayList<Integer>();
                previousListIndex = new ArrayList<Integer>();
                for (int j = 0; j < listNomeNotes.size(); j++) {
                   
                    listIndex.add(j);

                }
                previousListIndex = listIndex;
                statusAnalise = true;
            } else {
                //System.out.println("Esta por aqui??");
                
                listIndex = getListIndex(listNotes.get(i), previousListIndex);

                if (listIndex != null && listIndex.size() > 0) {
                    //System.out.println("No esta nula y mayor a 0");
                    previousListIndex = listIndex;
                    statusAnalise = true;
                } else {
                    //System.out.println("Sorry hay quiebre");
                    System.out.println(" añadiendo "+ listNomeNotes.get(previousListIndex.get(0)));
                    listIndexResult.add(listNomeNotes.get(previousListIndex.get(0)));
                    statusAnalise = false;
                    i = i - 1;
                  
                }
            }

        }
        return listIndexResult;
    }

    public static void main(String args[]) {
        try {
            MidiHarmonicField midiHarmonicField = new MidiHarmonicField();
            midiHarmonicField.readMidi("../../../../database-midi/los-peces-en-el-rio.mid");
            System.out.println(midiHarmonicField.listNotes);
           
            midiHarmonicField.createListHarmonicField();
            List<String> listResult = midiHarmonicField.getListChords();
            System.out.println(listResult);
            
            //System.out.println(midiHarmonicField.listNomeNotes);
        } catch (Exception ex) {
        }
    }
}
