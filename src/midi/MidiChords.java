/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package midi;

import java.awt.image.LookupTable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class MidiChords {

    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;
    public static final int PATCH_CHANGE = 0xC0;
    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    //public static final String[] NOTE_NAMES = {"DO", "DOS", "RE", "RES", "MI", "FA", "FAS", "SOL", "SOLS", "LA", "LAS", "SI"};
    public List<String> listNotes = new ArrayList<String>();
    public List<Integer> listVelocities = new ArrayList<Integer>();
    public List<Integer> listInstruments = new ArrayList<Integer>();
    public List< List< String>> listTriads = new ArrayList< List<String>>();
    public List<String> listNomeNotes = new ArrayList<String>();
    public List< List< String>> listScale = new ArrayList< List<String>>();

   

    public void createListTriads() {
        //mayores
        // C
        listNomeNotes.add("C");
        List<String> triad = new ArrayList<String>();
        triad.add("C");
        triad.add("E");
        triad.add("G");
        this.listTriads.add(triad);

        // C#
        listNomeNotes.add("C#");
        triad = new ArrayList<String>();
        triad.add("C#");
        triad.add("F");
        triad.add("G#");
        this.listTriads.add(triad);

        // D
        listNomeNotes.add("D");
        triad = new ArrayList<String>();
        triad.add("D");
        triad.add("F#");
        triad.add("A");
        this.listTriads.add(triad);

        // D#
        listNomeNotes.add("D#");
        triad = new ArrayList<String>();
        triad.add("D#");
        triad.add("G");
        triad.add("A#");
        this.listTriads.add(triad);

        // E
        listNomeNotes.add("E");
        triad = new ArrayList<String>();
        triad.add("E");
        triad.add("G#");
        triad.add("B");
        this.listTriads.add(triad);

        // F
        listNomeNotes.add("F");
        triad = new ArrayList<String>();
        triad.add("F");
        triad.add("A");
        triad.add("C");
        this.listTriads.add(triad);

        // F#
        listNomeNotes.add("F#");
        triad = new ArrayList<String>();
        triad.add("F#");
        triad.add("A#");
        triad.add("C#");
        this.listTriads.add(triad);

        // G
        listNomeNotes.add("G");
        triad = new ArrayList<String>();
        triad.add("G");
        triad.add("B");
        triad.add("D");
        this.listTriads.add(triad);

        // G#
        listNomeNotes.add("G#");
        triad = new ArrayList<String>();
        triad.add("G#");
        triad.add("C");
        triad.add("D#");
        this.listTriads.add(triad);

        // A
        listNomeNotes.add("A");
        triad = new ArrayList<String>();
        triad.add("A");
        triad.add("C#");
        triad.add("E");
        this.listTriads.add(triad);


        // A#
        listNomeNotes.add("A#");
        triad = new ArrayList<String>();
        triad.add("A#");
        triad.add("D");
        triad.add("F");
        this.listTriads.add(triad);

        // B
        listNomeNotes.add("B");
        triad = new ArrayList<String>();
        triad.add("B");
        triad.add("D#");
        triad.add("F#");
        this.listTriads.add(triad);


        //mayores
        // Cm
        listNomeNotes.add("Cm");
        triad = new ArrayList<String>();
        triad.add("C");
        triad.add("D#");
        triad.add("G");
        this.listTriads.add(triad);

        // C#m
        listNomeNotes.add("C#m");
        triad = new ArrayList<String>();
        triad.add("C#");
        triad.add("E");
        triad.add("G#");
        this.listTriads.add(triad);

        // Dm
        listNomeNotes.add("Dm");
        triad = new ArrayList<String>();
        triad.add("D");
        triad.add("F");
        triad.add("A");
        this.listTriads.add(triad);

        // D#m
        listNomeNotes.add("D#m");
        triad = new ArrayList<String>();
        triad.add("D#");
        triad.add("F#");
        triad.add("A#");
        this.listTriads.add(triad);

        // Em
        listNomeNotes.add("Em");
        triad = new ArrayList<String>();
        triad.add("E");
        triad.add("G");
        triad.add("B");
        this.listTriads.add(triad);

        // Fm
        listNomeNotes.add("Fm");
        triad = new ArrayList<String>();
        triad.add("F");
        triad.add("G#");
        triad.add("C");
        this.listTriads.add(triad);

        // F#m
        listNomeNotes.add("F#m");
        triad = new ArrayList<String>();
        triad.add("F");
        triad.add("G#");
        triad.add("C");
        this.listTriads.add(triad);

        // Gm
        listNomeNotes.add("Gm");
        triad = new ArrayList<String>();
        triad.add("G");
        triad.add("A#");
        triad.add("D");
        this.listTriads.add(triad);

        // G#m
        listNomeNotes.add("G#m");
        triad = new ArrayList<String>();
        triad.add("G#");
        triad.add("B");
        triad.add("D#");
        this.listTriads.add(triad);

        // Am
        listNomeNotes.add("Am");
        triad = new ArrayList<String>();
        triad.add("A");
        triad.add("C");
        triad.add("E");
        this.listTriads.add(triad);

        // A#m
        listNomeNotes.add("A#m");
        triad = new ArrayList<String>();
        triad.add("A#");
        triad.add("C#");
        triad.add("F");
        this.listTriads.add(triad);

        // Bm
        listNomeNotes.add("Bm");
        triad = new ArrayList<String>();
        triad.add("B");
        triad.add("D");
        triad.add("F#");
        this.listTriads.add(triad);


        //séptimas
        // C7
        listNomeNotes.add("C7");
        triad = new ArrayList<String>();
        triad.add("E");
        triad.add("C");
        triad.add("A#");
        triad.add("G#");
        this.listTriads.add(triad);

        // D7
        listNomeNotes.add("D7");
        triad = new ArrayList<String>();
        triad.add("F#");
        triad.add("C");
        triad.add("A");
        triad.add("D");
        this.listTriads.add(triad);

        // E7
        listNomeNotes.add("E7");
        triad = new ArrayList<String>();
        triad.add("E");
        triad.add("B");
        triad.add("G#");
        triad.add("D");
        this.listTriads.add(triad);

        // G7
        listNomeNotes.add("G7");
        triad = new ArrayList<String>();
        triad.add("F");
        triad.add("B");
        triad.add("G");
        triad.add("D");
        this.listTriads.add(triad);

        // A7
        listNomeNotes.add("A7");
        triad = new ArrayList<String>();
        triad.add("C#");
        triad.add("G");
        triad.add("E");
        triad.add("A");
        this.listTriads.add(triad);

        // B7
        listNomeNotes.add("B7");
        triad = new ArrayList<String>();
        triad.add("F#");
        triad.add("B");
        triad.add("A");
        triad.add("R#");
        this.listTriads.add(triad);

        /*
         //Sus4
         // CSus4
         listNomeNotes.add("CSus4");
         triad = new ArrayList<String>();
         triad.add("C");
         triad.add("F");
         triad.add("G");
         this.listTriads.add(triad);

         // C#Sus4
         listNomeNotes.add("C#Sus4");
         triad = new ArrayList<String>();
         triad.add("C#");
         triad.add("F#");
         triad.add("G#");
         this.listTriads.add(triad);

         //DSus4 
         listNomeNotes.add("DSus4");
         triad = new ArrayList<String>();
         triad.add("D");
         triad.add("G");
         triad.add("A");
         this.listTriads.add(triad);

         // D#Sus4
         listNomeNotes.add("D#Sus4");
         triad = new ArrayList<String>();
         triad.add("D#");
         triad.add("G#");
         triad.add("A#");
         this.listTriads.add(triad);

         // ESus4
         listNomeNotes.add("ESus4");
         triad = new ArrayList<String>();
         triad.add("E");
         triad.add("A");
         triad.add("B");
         this.listTriads.add(triad);

         // FSus4
         listNomeNotes.add("FSus4");
         triad = new ArrayList<String>();
         triad.add("F");
         triad.add("A#");
         triad.add("C");
         this.listTriads.add(triad);

         // F#Sus4
         listNomeNotes.add("F#Sus4");
         triad = new ArrayList<String>();
         triad.add("F#");
         triad.add("B");
         triad.add("C#");
         this.listTriads.add(triad);


         // GSus4
         listNomeNotes.add("GSus4");
         triad = new ArrayList<String>();
         triad.add("G");
         triad.add("C");
         triad.add("D");
         this.listTriads.add(triad);

         // G#Sus4
         listNomeNotes.add("G#Sus4");
         triad = new ArrayList<String>();
         triad.add("G#");
         triad.add("C#");
         triad.add("D#");
         this.listTriads.add(triad);

         // ASus4
         listNomeNotes.add("ASus4");
         triad = new ArrayList<String>();
         triad.add("A");
         triad.add("D");
         triad.add("E");
         this.listTriads.add(triad);

         // A#Sus4
         listNomeNotes.add("A#Sus4");
         triad = new ArrayList<String>();
         triad.add("A#");
         triad.add("D#");
         triad.add("F");
         this.listTriads.add(triad);

         // BSus4
         listNomeNotes.add("BSus4");
         triad = new ArrayList<String>();
         triad.add("B");
         triad.add("E");
         triad.add("F#");
         this.listTriads.add(triad);*/
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

            if (listTriads.get(previousListIndex.get(i)).contains(note)) {
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
                    System.out.println(" añadiendo " + listNomeNotes.get(previousListIndex.get(0)));
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
            MidiChords midiChords = new MidiChords();
            midiChords.readMidi("../../../../database-midi/u2/withorwithoutyou6.mid");
            //../../../../database-midi/los-peces-en-el-rio.mid");
            System.out.println(midiChords.listNotes);


            midiChords.createListTriads();
            List<String> listResult = midiChords.getListChords();
            System.out.println(listResult);

            System.out.println(midiChords.listNomeNotes);
        } catch (Exception ex) {
        }
    }
}
