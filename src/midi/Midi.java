/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package midi;



import java.io.File;

import java.util.ArrayList;
import java.util.List;
import javax.sound.midi.*;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Midi {

    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;
    public static final int PATCH_CHANGE = 0xC0;
    //public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    public static final String[] NOTE_NAMES = {"DO", "DOS", "RE", "RES", "MI", "FA", "FAS", "SOL", "SOLS", "LA", "LAS", "SI"};
    public List<Integer> listNotes = new ArrayList<Integer>();
    public List<Integer> listVelocities = new ArrayList<Integer>();
    public List<Integer> listInstruments = new ArrayList<Integer>();
    

    public List<Integer> getListNotes() {
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
                        listNotes.add(key);
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
                        //System.out.println("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity);
                        //listNotes.add(noteName+octave);
                        listNotes.add(key);
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

   
}