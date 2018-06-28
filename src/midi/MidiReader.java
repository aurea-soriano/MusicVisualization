/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package midi;

import java.io.File;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.HashSet;
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
public class MidiReader {

    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;
    public static final int PATCH_CHANGE = 0xC0;
    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    public List<String> listNotes = new ArrayList<String>();
    public List<List<String>>matrixNotes = new ArrayList<List<String>>();
    public List< List< String>> listTriads = new ArrayList< List<String>>();
    public List< List< String>> listHarmonicField = new ArrayList< List<String>>();
    public List< List< String>> listTonality = new ArrayList< List<String>>();
    public List<String> listNameNotes = new ArrayList<String>();
    public List<String> listNameNotesHarmonicField = new ArrayList<String>();
    public List<List<String>> listNameTonality = new ArrayList<List<String>>();
    public HashSet<String> listMainNotes = new HashSet<String>();

    public void createListHarmonicField() {
        //mayores
        // C
        listNameNotesHarmonicField.add("C");
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
        listNameNotesHarmonicField.add("C#");
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
        listNameNotesHarmonicField.add("D");
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
        listNameNotesHarmonicField.add("D#");
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
        listNameNotesHarmonicField.add("E");
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
        listNameNotesHarmonicField.add("F");
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
        listNameNotesHarmonicField.add("F#");
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
        listNameNotesHarmonicField.add("G");
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
        listNameNotesHarmonicField.add("G#");
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
        listNameNotesHarmonicField.add("A");
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
        listNameNotesHarmonicField.add("A#");
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
        listNameNotesHarmonicField.add("B");
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
        listNameNotesHarmonicField.add("Cm");
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
        listNameNotesHarmonicField.add("C#m");
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
        listNameNotesHarmonicField.add("Dm");
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
        listNameNotesHarmonicField.add("D#m");
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
        listNameNotesHarmonicField.add("Em");
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
        listNameNotesHarmonicField.add("Fm");
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
        listNameNotesHarmonicField.add("F#m");
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
        listNameNotesHarmonicField.add("Gm");
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
        listNameNotesHarmonicField.add("G#m");
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
        listNameNotesHarmonicField.add("Am");
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
        listNameNotesHarmonicField.add("A#m");
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
        listNameNotesHarmonicField.add("Bm");
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

    public void createListTriads() {
        //mayores
        // C
        listNameNotes.add("C");
        List<String> triad = new ArrayList<String>();
        triad.add("C");
        triad.add("E");
        triad.add("G");
        this.listTriads.add(triad);

        // C#
        listNameNotes.add("C#");
        triad = new ArrayList<String>();
        triad.add("C#");
        triad.add("F");
        triad.add("G#");
        this.listTriads.add(triad);

        // D
        listNameNotes.add("D");
        triad = new ArrayList<String>();
        triad.add("D");
        triad.add("F#");
        triad.add("A");
        this.listTriads.add(triad);

        // D#
        listNameNotes.add("D#");
        triad = new ArrayList<String>();
        triad.add("D#");
        triad.add("G");
        triad.add("A#");
        this.listTriads.add(triad);

        // E
        listNameNotes.add("E");
        triad = new ArrayList<String>();
        triad.add("E");
        triad.add("G#");
        triad.add("B");
        this.listTriads.add(triad);

        // F
        listNameNotes.add("F");
        triad = new ArrayList<String>();
        triad.add("F");
        triad.add("A");
        triad.add("C");
        this.listTriads.add(triad);

        // F#
        listNameNotes.add("F#");
        triad = new ArrayList<String>();
        triad.add("F#");
        triad.add("A#");
        triad.add("C#");
        this.listTriads.add(triad);

        // G
        listNameNotes.add("G");
        triad = new ArrayList<String>();
        triad.add("G");
        triad.add("B");
        triad.add("D");
        this.listTriads.add(triad);

        // G#
        listNameNotes.add("G#");
        triad = new ArrayList<String>();
        triad.add("G#");
        triad.add("C");
        triad.add("D#");
        this.listTriads.add(triad);

        // A
        listNameNotes.add("A");
        triad = new ArrayList<String>();
        triad.add("A");
        triad.add("C#");
        triad.add("E");
        this.listTriads.add(triad);


        // A#
        listNameNotes.add("A#");
        triad = new ArrayList<String>();
        triad.add("A#");
        triad.add("D");
        triad.add("F");
        this.listTriads.add(triad);

        // B
        listNameNotes.add("B");
        triad = new ArrayList<String>();
        triad.add("B");
        triad.add("D#");
        triad.add("F#");
        this.listTriads.add(triad);


        //mayores
        // Cm
        listNameNotes.add("Cm");
        triad = new ArrayList<String>();
        triad.add("C");
        triad.add("D#");
        triad.add("G");
        this.listTriads.add(triad);

        // C#m
        listNameNotes.add("C#m");
        triad = new ArrayList<String>();
        triad.add("C#");
        triad.add("E");
        triad.add("G#");
        this.listTriads.add(triad);

        // Dm
        listNameNotes.add("Dm");
        triad = new ArrayList<String>();
        triad.add("D");
        triad.add("F");
        triad.add("A");
        this.listTriads.add(triad);

        // D#m
        listNameNotes.add("D#m");
        triad = new ArrayList<String>();
        triad.add("D#");
        triad.add("F#");
        triad.add("A#");
        this.listTriads.add(triad);

        // Em
        listNameNotes.add("Em");
        triad = new ArrayList<String>();
        triad.add("E");
        triad.add("G");
        triad.add("B");
        this.listTriads.add(triad);

        // Fm
        listNameNotes.add("Fm");
        triad = new ArrayList<String>();
        triad.add("F");
        triad.add("G#");
        triad.add("C");
        this.listTriads.add(triad);

        // F#m
        listNameNotes.add("F#m");
        triad = new ArrayList<String>();
        triad.add("F");
        triad.add("G#");
        triad.add("C");
        this.listTriads.add(triad);

        // Gm
        listNameNotes.add("Gm");
        triad = new ArrayList<String>();
        triad.add("G");
        triad.add("A#");
        triad.add("D");
        this.listTriads.add(triad);

        // G#m
        listNameNotes.add("G#m");
        triad = new ArrayList<String>();
        triad.add("G#");
        triad.add("B");
        triad.add("D#");
        this.listTriads.add(triad);

        // Am
        listNameNotes.add("Am");
        triad = new ArrayList<String>();
        triad.add("A");
        triad.add("C");
        triad.add("E");
        this.listTriads.add(triad);

        // A#m
        listNameNotes.add("A#m");
        triad = new ArrayList<String>();
        triad.add("A#");
        triad.add("C#");
        triad.add("F");
        this.listTriads.add(triad);

        // Bm
        listNameNotes.add("Bm");
        triad = new ArrayList<String>();
        triad.add("B");
        triad.add("D");
        triad.add("F#");
        this.listTriads.add(triad);


        //séptimas
        // C7
        listNameNotes.add("C7");
        triad = new ArrayList<String>();
        triad.add("E");
        triad.add("C");
        triad.add("A#");
        triad.add("G#");
        this.listTriads.add(triad);

        // D7
        listNameNotes.add("D7");
        triad = new ArrayList<String>();
        triad.add("F#");
        triad.add("C");
        triad.add("A");
        triad.add("D");
        this.listTriads.add(triad);

        // E7
        listNameNotes.add("E7");
        triad = new ArrayList<String>();
        triad.add("E");
        triad.add("B");
        triad.add("G#");
        triad.add("D");
        this.listTriads.add(triad);

        // G7
        listNameNotes.add("G7");
        triad = new ArrayList<String>();
        triad.add("F");
        triad.add("B");
        triad.add("G");
        triad.add("D");
        this.listTriads.add(triad);

        // A7
        listNameNotes.add("A7");
        triad = new ArrayList<String>();
        triad.add("C#");
        triad.add("G");
        triad.add("E");
        triad.add("A");
        this.listTriads.add(triad);

        // B7
        listNameNotes.add("B7");
        triad = new ArrayList<String>();
        triad.add("F#");
        triad.add("B");
        triad.add("A");
        triad.add("R#");
        this.listTriads.add(triad);

        /*
         //Sus4
         // CSus4
         listNameNotes.add("CSus4");
         triad = new ArrayList<String>();
         triad.add("C");
         triad.add("F");
         triad.add("G");
         this.listTriads.add(triad);

         // C#Sus4
         listNameNotes.add("C#Sus4");
         triad = new ArrayList<String>();
         triad.add("C#");
         triad.add("F#");
         triad.add("G#");
         this.listTriads.add(triad);

         //DSus4 
         listNameNotes.add("DSus4");
         triad = new ArrayList<String>();
         triad.add("D");
         triad.add("G");
         triad.add("A");
         this.listTriads.add(triad);

         // D#Sus4
         listNameNotes.add("D#Sus4");
         triad = new ArrayList<String>();
         triad.add("D#");
         triad.add("G#");
         triad.add("A#");
         this.listTriads.add(triad);

         // ESus4
         listNameNotes.add("ESus4");
         triad = new ArrayList<String>();
         triad.add("E");
         triad.add("A");
         triad.add("B");
         this.listTriads.add(triad);

         // FSus4
         listNameNotes.add("FSus4");
         triad = new ArrayList<String>();
         triad.add("F");
         triad.add("A#");
         triad.add("C");
         this.listTriads.add(triad);

         // F#Sus4
         listNameNotes.add("F#Sus4");
         triad = new ArrayList<String>();
         triad.add("F#");
         triad.add("B");
         triad.add("C#");
         this.listTriads.add(triad);


         // GSus4
         listNameNotes.add("GSus4");
         triad = new ArrayList<String>();
         triad.add("G");
         triad.add("C");
         triad.add("D");
         this.listTriads.add(triad);

         // G#Sus4
         listNameNotes.add("G#Sus4");
         triad = new ArrayList<String>();
         triad.add("G#");
         triad.add("C#");
         triad.add("D#");
         this.listTriads.add(triad);

         // ASus4
         listNameNotes.add("ASus4");
         triad = new ArrayList<String>();
         triad.add("A");
         triad.add("D");
         triad.add("E");
         this.listTriads.add(triad);

         // A#Sus4
         listNameNotes.add("A#Sus4");
         triad = new ArrayList<String>();
         triad.add("A#");
         triad.add("D#");
         triad.add("F");
         this.listTriads.add(triad);

         // BSus4
         listNameNotes.add("BSus4");
         triad = new ArrayList<String>();
         triad.add("B");
         triad.add("E");
         triad.add("F#");
         this.listTriads.add(triad);*/
    }

    public void createListTonality() {
        List< String> nameTonality = new ArrayList<String>();
        nameTonality.add("B");
        nameTonality.add("G#m");
        listNameTonality.add(nameTonality);

        List<String> notesTonality = new ArrayList<String>();
        notesTonality.add("A#");
        notesTonality.add("D#");
        notesTonality.add("G#");
        notesTonality.add("C#");
        notesTonality.add("F#");
        notesTonality.add("B");
        notesTonality.add("E");
        listTonality.add(notesTonality);

        //****************************+
        nameTonality = new ArrayList<String>();
        nameTonality.add("C#");
        nameTonality.add("A#m");
        listNameTonality.add(nameTonality);

        notesTonality = new ArrayList<String>();
        notesTonality.add("F#");
        notesTonality.add("C#");
        notesTonality.add("G#");
        notesTonality.add("D#");
        notesTonality.add("A#");
        notesTonality.add("F");
        notesTonality.add("C");
        listTonality.add(notesTonality);

        //****************************+
        nameTonality = new ArrayList<String>();
        nameTonality.add("F#");
        nameTonality.add("D#m");
        listNameTonality.add(nameTonality);

        notesTonality = new ArrayList<String>();
        notesTonality.add("A#");
        notesTonality.add("D#");
        notesTonality.add("G#");
        notesTonality.add("C#");
        notesTonality.add("F#");
        notesTonality.add("B");
        listTonality.add(notesTonality);

        //****************************+
        nameTonality = new ArrayList<String>();
        nameTonality.add("F#");
        nameTonality.add("D#m");
        listNameTonality.add(nameTonality);

        notesTonality = new ArrayList<String>();
        notesTonality.add("F#");
        notesTonality.add("C#");
        notesTonality.add("G#");
        notesTonality.add("D#");
        notesTonality.add("A#");
        notesTonality.add("F");
        listTonality.add(notesTonality);

        //****************************+

        nameTonality = new ArrayList<String>();
        nameTonality.add("C#");
        nameTonality.add("A#m");
        listNameTonality.add(nameTonality);

        notesTonality = new ArrayList<String>();
        notesTonality.add("A#");
        notesTonality.add("D#");
        notesTonality.add("G#");
        notesTonality.add("C#");
        notesTonality.add("F#");
        listTonality.add(notesTonality);

        //****************************+
        nameTonality = new ArrayList<String>();
        nameTonality.add("B");
        nameTonality.add("G#m");
        listNameTonality.add(nameTonality);

        notesTonality = new ArrayList<String>();
        notesTonality.add("F#");
        notesTonality.add("C#");
        notesTonality.add("G#");
        notesTonality.add("D#");
        notesTonality.add("A#");
        listTonality.add(notesTonality);

        //****************************+

        nameTonality = new ArrayList<String>();
        nameTonality.add("G#");
        nameTonality.add("Fm");
        listNameTonality.add(nameTonality);

        notesTonality = new ArrayList<String>();
        notesTonality.add("A#");
        notesTonality.add("D#");
        notesTonality.add("G#");
        notesTonality.add("C#");
        listTonality.add(notesTonality);

        //****************************+
        nameTonality = new ArrayList<String>();
        nameTonality.add("E");
        nameTonality.add("C#m");
        listNameTonality.add(nameTonality);

        notesTonality = new ArrayList<String>();
        notesTonality.add("F#");
        notesTonality.add("C#");
        notesTonality.add("G#");
        notesTonality.add("D#");
        listTonality.add(notesTonality);

        //****************************+

        nameTonality = new ArrayList<String>();
        nameTonality.add("D#");
        nameTonality.add("Cm");
        listNameTonality.add(nameTonality);

        notesTonality = new ArrayList<String>();
        notesTonality.add("A#");
        notesTonality.add("D#");
        notesTonality.add("G#");
        listTonality.add(notesTonality);

        //****************************+
        nameTonality = new ArrayList<String>();
        nameTonality.add("A");
        nameTonality.add("F#m");
        listNameTonality.add(nameTonality);

        notesTonality = new ArrayList<String>();
        notesTonality.add("F#");
        notesTonality.add("C#");
        notesTonality.add("G#");
        listTonality.add(notesTonality);

        //****************************+
        nameTonality = new ArrayList<String>();
        nameTonality.add("A#");
        nameTonality.add("Gm");
        listNameTonality.add(nameTonality);

        notesTonality = new ArrayList<String>();
        notesTonality.add("A#");
        notesTonality.add("D#");
        listTonality.add(notesTonality);

        //****************************+
        nameTonality = new ArrayList<String>();
        nameTonality.add("D");
        nameTonality.add("Bm");
        listNameTonality.add(nameTonality);

        notesTonality = new ArrayList<String>();
        notesTonality.add("F#");
        notesTonality.add("C#");
        listTonality.add(notesTonality);

        //****************************+

        nameTonality = new ArrayList<String>();
        nameTonality.add("F");
        nameTonality.add("Dm");
        listNameTonality.add(nameTonality);

        notesTonality = new ArrayList<String>();
        notesTonality.add("A#");
        listTonality.add(notesTonality);

        //****************************+
        nameTonality = new ArrayList<String>();
        nameTonality.add("G");
        nameTonality.add("Em");
        listNameTonality.add(nameTonality);

        notesTonality = new ArrayList<String>();
        notesTonality.add("F#");
        listTonality.add(notesTonality);

        //****************************+

        nameTonality = new ArrayList<String>();
        nameTonality.add("C");
        nameTonality.add("Am");
        listNameTonality.add(nameTonality);

        notesTonality = new ArrayList<String>();
        notesTonality.add("");
        listTonality.add(notesTonality);


    }

    public List<String> getListNotes() {
        return listNotes;
    }

    public void readMidi(String midiName) throws Exception {

        listMainNotes = new HashSet<String>();
        Sequence sequence = MidiSystem.getSequence(new File(midiName));
        int trackNumber = 0;
      
        
        for (Track track : sequence.getTracks()) {
            trackNumber++;
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);

                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;

                    if (sm.getCommand() == NOTE_ON) {
                        int key = sm.getData1();
                        int octave = (key / 12) - 1;
                        int note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();
                        
                        //System.out.println("canal "+sm.getChannel() );
                        if (sm.getChannel() == 1) {
                            listNotes.add(noteName);
                            listMainNotes.add(noteName);
                        }

                    } else if (sm.getCommand() == NOTE_OFF) {

                        int key = sm.getData1();
                        int octave = (key / 12) - 1;
                        int note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();
                         //System.out.println("canal "+sm.getChannel() );
                        if (sm.getChannel() == 1) {
                            listNotes.add(noteName);
                            listMainNotes.add(noteName);
                        }


                    } else if (sm.getCommand() == PATCH_CHANGE) {
                        int instrument = sm.getData1();

                    } else {
                    }
                } else {
                }
            }

            //System.out.println();
        }

    }

    public List<String> getTonality() {
        boolean bResult = false;
        for (int i = 0; i < listTonality.size() - 1; i++) {
            int count = 0;
            for (int j = 0; j < listTonality.get(i).size(); j++) {


                if (listMainNotes.contains(listTonality.get(i).get(j))) {
                    count++;
                } else {
                    break;
                }
            }

            if (count == listTonality.get(i).size()) {
                return listNameTonality.get(i);
            }
        }
        return listNameTonality.get(listTonality.size() - 2);

    }

    public List<Integer> getListIndex(String note, List<Integer> previousListIndex, List<List<String>> listTriadsAnalized) {
        List<Integer> listIndex = new ArrayList<Integer>();
        for (int i = 0; i < previousListIndex.size(); i++) {


            if (listTriadsAnalized.get(previousListIndex.get(i)).contains(note)) {
                //System.out.println(note+" :: "+listTriads.get(previousListIndex.get(i)));
                listIndex.add(previousListIndex.get(i));
            }
        }
        return listIndex;

    }

    public List<String> getListChords(String tonality) {

        List<String> listNameNotesOfTonality = new ArrayList<String>();
        List<List<String>> listTriadsOfTonality = new ArrayList<List<String>>();

        for (int i = 0; i < listNameNotesHarmonicField.size(); i++) {
            if (tonality.equals(listNameNotesHarmonicField.get(i))) {
                listNameNotesOfTonality = listHarmonicField.get(i);
                System.out.println(listNameNotesOfTonality);
                break;
            }
        }

        for (int i = 0; i < listNameNotesOfTonality.size(); i++) {
            for (int j = 0; j < listTriads.size(); j++) {
                if (listNameNotesOfTonality.get(i).equals(listNameNotes.get(j))) {
                    listTriadsOfTonality.add(listTriads.get(j));
                    break;
                }
            }
        }

        boolean statusAnalise = false; //false acabou de analizar ou nao esta analizando & true em processo.
        List<Integer> listIndex = new ArrayList<Integer>();
        List<Integer> previousListIndex = new ArrayList<Integer>();
        List<String> listIndexResult = new ArrayList<String>();

        for (int i = 0; i < listNotes.size(); i++) {

            if (statusAnalise == false) {
                listIndex = new ArrayList<Integer>();
                previousListIndex = new ArrayList<Integer>();
                for (int j = 0; j < listNameNotesOfTonality.size(); j++) {

                    listIndex.add(j);

                }
                previousListIndex = listIndex;
                statusAnalise = true;
            } else {
                listIndex = getListIndex(listNotes.get(i), previousListIndex, listTriadsOfTonality);
                if (listIndex != null && listIndex.size() > 0) {
                    //System.out.println("No esta nula y mayor a 0");
                    previousListIndex = listIndex;
                    statusAnalise = true;
                } else {
                    //System.out.println("Sorry hay quiebre");
                    //System.out.println(" añadiendo " + listNameNotesOfTonality.get(previousListIndex.get(0)));
                    listIndexResult.add(listNameNotesOfTonality.get(previousListIndex.get(0)));
                    statusAnalise = false;
                    i = i - 1;

                }
            }

        }
        return listIndexResult;
    }

    public static void main(String args[]) {
        try {
            MidiReader midiReader = new MidiReader();
            midiReader.readMidi("../../../../Public/database-midi/los-peces-en-el-rio.mid");
            midiReader.createListHarmonicField();
            midiReader.createListTriads();
            midiReader.createListTonality();
            System.out.println(midiReader.listMainNotes);
            System.out.println(midiReader.getTonality());
            System.out.println("Result :::" + midiReader.getListChords(midiReader.getTonality().get(0)));




        } catch (Exception ex) {
        }
    }
}
