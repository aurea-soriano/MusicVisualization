/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midiStructure;

import horspoolPattern.HorspoolTestVelocity;
import horspoolPattern.HorspoolVelocity;
import horspoolPattern.PositionPatternVelocity;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;
import java.util.List;
import javax.sound.midi.*;

/**
 *
 * @author aurea
 */
public class VelocityStructureRecognition {

    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;
    public static final int PATCH_CHANGE = 0xC0;
    public static final int CONTROLLER = 0xFF;
    public List<List<Integer>> matrixRhythm = new ArrayList<>();
    public List<Integer> histogramRhythm = new ArrayList<>();
    public List<Long> listTicks = new ArrayList<>();
    public String tonalityStr = "";
    public Integer ticksPerBeat = 24;
    public boolean boolTicksPerBeat = false;
    public long tickLength;
    public float divisionType;
    public Integer resolution;
    public static final int TIMING_CLOCK = 0xF8;
    public Integer maxNumber = 0;

    public VelocityStructureRecognition() {

    }

    private String messageInfotoString(MidiMessage message) {
        if (!(message instanceof ShortMessage)) {
            return message.toString();
        }
        ShortMessage m = (ShortMessage) message;
        return String.format("Channel %d  command: %d data %d %d", m.getChannel(), m.getCommand(), m.getData1(), m.getData2());
    }

    private static final String[] sm_astrKeyNames = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    private static final String[] sm_astrKeySignatures = {"Cb", "Gb", "Db", "Ab", "Eb", "Bb", "F", "C", "G", "D", "A", "E", "B", "F#", "C#"};
    private static final String[] SYSTEM_MESSAGE_TEXT = {
        "System Exclusive (should not be in ShortMessage!)",
        "MTC Quarter Frame: ",
        "Song Position: ",
        "Song Select: ",
        "Undefined",
        "Undefined",
        "Tune Request",
        "End of SysEx (should not be in ShortMessage!)",
        "Timing clock",
        "Undefined",
        "Start",
        "Continue",
        "Stop",
        "Undefined",
        "Active Sensing",
        "System Reset"
    };
    private static final String[] QUARTER_FRAME_MESSAGE_TEXT = {
        "frame count LS: ",
        "frame count MS: ",
        "seconds count LS: ",
        "seconds count MS: ",
        "minutes count LS: ",
        "minutes count MS: ",
        "hours count LS: ",
        "hours count MS: "
    };
    private static final String[] FRAME_TYPE_TEXT = {
        "24 frames/second",
        "25 frames/second",
        "30 frames/second (drop)",
        "30 frames/second (non-drop)",};

    public static String getKeyName(int nKeyNumber) {
        if (nKeyNumber > 127) {
            return "illegal value";
        } else {
            int nNote = nKeyNumber % 12;
            int nOctave = nKeyNumber / 12;
            return sm_astrKeyNames[nNote] + (nOctave - 1);
        }
    }

    public static int get14bitValue(int nLowerPart, int nHigherPart) {
        return (nLowerPart & 0x7F) | ((nHigherPart & 0x7F) << 7);
    }

    private static int signedByteToUnsigned(byte b) {
        if (b >= 0) {
            return (int) b;
        } else {
            return 256 + (int) b;
        }
    }

    public static String getHexString(byte[] aByte) {
        StringBuffer sbuf = new StringBuffer(aByte.length * 3 + 2);
        for (int i = 0; i < aByte.length; i++) {
            sbuf.append(' ');
            byte bhigh = (byte) ((aByte[i] & 0xf0) >> 4);
            sbuf.append((char) (bhigh > 9 ? bhigh + 'A' - 10 : bhigh + '0'));
            byte blow = (byte) (aByte[i] & 0x0f);
            sbuf.append((char) (blow > 9 ? blow + 'A' - 10 : blow + '0'));
        }
        return new String(sbuf);
    }

    public String decodeMessage(ShortMessage message) {
        String strMessage = null;
        switch (message.getCommand()) {
            case 0x80:
                strMessage = "note Off " + getKeyName(message.getData1()) + " velocity: " + message.getData2();
                break;

            case 0x90:
                strMessage = "note On " + getKeyName(message.getData1()) + " velocity: " + message.getData2();
                break;

            case 0xa0:
                strMessage = "polyphonic key pressure " + getKeyName(message.getData1()) + " pressure: " + message.getData2();
                break;

            case 0xb0:
                strMessage = "control change " + message.getData1() + " value: " + message.getData2();
                break;

            case 0xc0:
                strMessage = "program change " + message.getData1();
                break;

            case 0xd0:
                strMessage = "key pressure " + getKeyName(message.getData1()) + " pressure: " + message.getData2();
                break;

            case 0xe0:
                strMessage = "pitch wheel change " + get14bitValue(message.getData1(), message.getData2());
                break;

            case 0xF0:
                strMessage = SYSTEM_MESSAGE_TEXT[message.getChannel()];
                switch (message.getChannel()) {
                    case 0x1:
                        int nQType = (message.getData1() & 0x70) >> 4;
                        int nQData = message.getData1() & 0x0F;
                        if (nQType == 7) {
                            nQData = nQData & 0x1;
                        }
                        strMessage += QUARTER_FRAME_MESSAGE_TEXT[nQType] + nQData;
                        if (nQType == 7) {
                            int nFrameType = (message.getData1() & 0x06) >> 1;
                            strMessage += ", frame type: " + FRAME_TYPE_TEXT[nFrameType];
                        }
                        break;

                    case 0x2:
                        strMessage += get14bitValue(message.getData1(), message.getData2());
                        break;

                    case 0x3:
                        strMessage += message.getData1();
                        break;
                }
                break;

            default:
                strMessage = "unknown message: status = " + message.getStatus() + ", byte1 = " + message.getData1() + ", byte2 = " + message.getData2();
                break;
        }
        if (message.getCommand() != 0xF0) {
            int nChannel = message.getChannel() + 1;
            String strChannel = "channel " + nChannel + ": ";
            strMessage = strChannel + strMessage;
        }
        return strMessage;
    }

    public String decodeMessage(SysexMessage message) {
        byte[] abData = message.getData();
        String strMessage = null;
        if (message.getStatus() == SysexMessage.SYSTEM_EXCLUSIVE) {
            strMessage = "Sysex message: F0" + getHexString(abData);
        } else if (message.getStatus() == SysexMessage.SPECIAL_SYSTEM_EXCLUSIVE) {
            strMessage = "Special Sysex message (F7):" + getHexString(abData);
        }
        return strMessage;
    }

    public String decodeMessage(MetaMessage message) {
        byte[] abMessage = message.getMessage();
        byte[] abData = message.getData();
        int nDataLength = message.getLength();
        String strMessage = null;
        switch (message.getType()) {
            case 0:
                int nSequenceNumber = abData[0] * 256 + abData[1];
                strMessage = "Sequence Number: " + nSequenceNumber;
                break;

            case 1:
                String strText = new String(abData);
                strMessage = "Text Event: " + strText;
                break;

            case 2:
                String strCopyrightText = new String(abData);
                strMessage = "Copyright Notice: " + strCopyrightText;
                break;

            case 3:
                String strTrackName = new String(abData);
                strMessage = "Sequence/Track Name: " + strTrackName;
                break;

            case 4:
                String strInstrumentName = new String(abData);
                strMessage = "Instrument Name: " + strInstrumentName;
                break;

            case 5:
                String strLyrics = new String(abData);
                strMessage = "Lyric: " + strLyrics;
                break;

            case 6:
                String strMarkerText = new String(abData);
                strMessage = "Marker: " + strMarkerText;
                break;

            case 7:
                String strCuePointText = new String(abData);
                strMessage = "Cue Point: " + strCuePointText;
                break;

            case 0x20:
                int nChannelPrefix = abData[0];
                strMessage = "MIDI Channel Prefix: " + nChannelPrefix;
                break;

            case 0x2F:
                strMessage = "End of Track";
                break;

            case 0x51:
                int nTempo = signedByteToUnsigned(abData[0]) * 65536
                        + signedByteToUnsigned(abData[1]) * 256
                        + signedByteToUnsigned(abData[2]);
                strMessage = "Set Tempo (µs/quarter note): " + nTempo;
                break;

            case 0x54:
                strMessage = "SMTPE Offset: " + abData[0] + ":" + abData[1] + ":" + abData[2] + "." + abData[3] + "." + abData[4];
                break;

            case 0x58:
                strMessage = "Time Signature: " + abData[0] + "/" + (1 << abData[1]) + ", MIDI clocks per metronome tick: " + abData[2] + ", 1/32 per 24 MIDI clocks: " + abData[3];

                int numerator = Integer.valueOf(1 << abData[1]);
                int denominator = abData[0];//Integer.valueOf((int)Math.pow(2,abData[0]));
                if (denominator == 4) {
                        this.ticksPerBeat = this.resolution * numerator;
                    } else {
                        if (denominator < 4) {
                            this.ticksPerBeat = (this.resolution) * numerator/denominator;
                        } else {
                            //vai ser maior ou seja corchea semicorchea fusa ou semidifusa
                            this.ticksPerBeat = (this.resolution)  * numerator *(denominator/4)/denominator;
                        }
                    }
                break;

            case 0x59:
                String strGender = (abData[1] == 1) ? "minor" : "major";
                strMessage = "Key Signature: " + sm_astrKeySignatures[abData[0] + 7] + " " + strGender;
                break;

            case 0x7F:
                // TODO: decode vendor code, dump data in rows
                String strDataDump = "";
                for (int i = 0; i < abData.length; i++) {
                    strDataDump += abData[i] + " ";
                }
                strMessage = "Sequencer-Specific Meta event: " + strDataDump;
                break;

            default:
                String strUnknownDump = "";
                for (int i = 0; i < abData.length; i++) {
                    strUnknownDump += abData[i] + " ";
                }
                strMessage = "unknown Meta event: " + strUnknownDump;
                break;

        }
        return strMessage;
    }
    /**
     * Tempo-based timing. Resolution is specified in ticks per beat.
     */
    public static final float PPQ = 0.0f;
    /**
     * 24 frames/second timing. Resolution is specific in ticks per frame.
     */
    public static final float SMPTE_24 = 24.0f;
    /**
     * 25 frames/second timing. Resolution is specific in ticks per frame.
     */
    public static final float SMPTE_25 = 25.0f;
    /**
     * 30 frames/second timing. Resolution is specific in ticks per frame.
     */
    public static final float SMPTE_30 = 30.0f;
    /**
     * 29.97 frames/second timing. Resolution is specific in ticks per frame.
     */
    public static final float SMPTE_30DROP = 29.97f;

    public Integer getPositionTick(long tick) {
        for (int i = 0; i < listTicks.size(); i++) {

            if (tick == listTicks.get(i)) {
                return i;
            }
        }
        return -1;
    }

    public void readMidi(String midiName) throws Exception {

        Sequence sequence = MidiSystem.getSequence(new File(midiName));
        this.tickLength = sequence.getTickLength();
        this.divisionType = sequence.getDivisionType();
        this.resolution = sequence.getResolution();
        matrixRhythm = new ArrayList<>();

        int trackNumber = 0;
        for (Track track : sequence.getTracks()) {
            trackNumber++;
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof MetaMessage) {
                    MetaMessage sm = (MetaMessage) message;
                    decodeMessage(sm);
                }
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == NOTE_ON) {
                        long number = event.getTick();

                        if (!listTicks.contains(number)) {
                            this.listTicks.add(number);
                            matrixRhythm.add(new ArrayList<Integer>());
                        }
                    } else {
                        if (sm.getCommand() == NOTE_OFF) {
                            long number = event.getTick();
                            if (!listTicks.contains(number)) {
                                this.listTicks.add(number);
                                matrixRhythm.add(new ArrayList<Integer>());
                            }
                        }
                    }
                }
            }
        }
        Collections.sort(listTicks);
        
       
        trackNumber = 0;

        for (Track track : sequence.getTracks()) {
            trackNumber++;
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();

                if (message instanceof ShortMessage) {

                    ShortMessage sm = (ShortMessage) message;

                    if (sm.getCommand() == NOTE_ON) {
                        long number = event.getTick();

                        Integer position = getPositionTick(number);

                        int velocity = sm.getData2();
                        int statusCurrent = sm.getStatus();
                        int channelCurrent = sm.getChannel();

                        matrixRhythm.get(position).add(velocity);

                        //this.matrixNotes.get(sm.getChannel()).add(noteName);
                        //histogramNotes.set(note, histogramNotes.get(note) + 1);
                    } else if (sm.getCommand() == NOTE_OFF) {
                        /*long number = event.getTick();

                         Integer position = getPositionTick(number);
                         int velocity = sm.getData2();
                         int statusCurrent = sm.getStatus();
                         int channelCurrent = sm.getChannel();
                         matrixRhythm.get(position).add(velocity);*/

                        //this.matrixNotes.get(sm.getChannel()).add(noteName);
                        //histogramNotes.set(note, histogramNotes.get(note) + 1);
                    } else if (sm.getCommand() == PATCH_CHANGE) {
                    } else {
                    }
                } else {
                }
            }

        }

    }

    public void printFile() {
        FileWriter outFile = null;
        try {
            outFile = new FileWriter("prueba.txt");
            try (PrintWriter out = new PrintWriter(outFile)) {
                for (int i = 0; i < this.matrixRhythm.size(); i++) {
                    out.write(i + "-");
                    for (int j = 0; j < this.matrixRhythm.get(i).size(); j++) {
                        out.write(this.matrixRhythm.get(i).get(j));

                        out.write(" ");
                    }
                    out.write("\n");
                }
            }
        } catch (IOException ex) {

        } finally {
            try {
                outFile.close();
            } catch (IOException ex) {
            }
        }
    }

    public Integer getMeanList(List<Integer> listVel) {
        int sum = 0;
        for (int i = 0; i < listVel.size(); i++) {
            sum += listVel.get(i);
        }
        if (listVel.isEmpty()) {
            return 0;
        }
        return sum / listVel.size();
    }

    public List<Integer> getListVelocity() {
        
        List<Integer> listIndexResult = new ArrayList<>();
        int tam = (int) ticksPerBeat;
        long resol = ticksPerBeat;
        ListIterator it= this.listTicks.listIterator();
        List<Integer> subListRhythm = new ArrayList<>();
        while(it.hasNext())
        {
            
            long value = (long)it.next();
            //System.out.println("value "+value);
            if(value < resol)
            {
                for(int j = 0; j < matrixRhythm.get(this.getPositionTick(value)).size(); j++) {
                        subListRhythm.add(matrixRhythm.get(this.getPositionTick(value)).get(j));
                 }
            }
            else
            {
                Integer velocity = this.getMeanList(subListRhythm);
                listIndexResult.add(velocity);
                subListRhythm = new ArrayList<>();
                it.previous();
                resol+=tam;
            }
        }
        System.out.println("tam listIndex "+listIndexResult.size()+" - "+(listTicks.get(listTicks.size()-1)/tam));
        return listIndexResult;

    }

    public static void main(String args[]) throws IOException {

        List<String> listStringHistogram = new ArrayList<>();
        List<String> listLabelHistogram = new ArrayList<>();

        List<String> listHeightHistogram = new ArrayList<>();
        int mayorT = 0;
        int mayorSequence = 5;//Integer.MIN_VALUE;
        int menorSequence = 1;//Integer.MAX_VALUE;
        System.out.println("Empezo");
        int count = 0;
        try {
            try (BufferedReader in = new BufferedReader(new FileReader("listWind.txt"))) {
                String str;
                while ((str = in.readLine()) != null) {
                    count++;
                    System.out.println(count);
                    try {
                        if (str != null) {
                            VelocityStructureRecognition recognition = new VelocityStructureRecognition();
                            recognition.readMidi(str);

                            List<Integer> listVelocityWrite = new ArrayList<>();
                            listVelocityWrite = recognition.getListVelocity();
                            HorspoolTestVelocity horspoolTest = new HorspoolTestVelocity();
                            List<HorspoolVelocity> listHorspool = horspoolTest.patternRecognition(listVelocityWrite);
                            List<Integer> colors = horspoolTest.calculateColor(listHorspool, listVelocityWrite);

                            List<PositionPatternVelocity> listPositionPattern = horspoolTest.calculateColorPositions(listHorspool, listVelocityWrite);
                            for (int m = 0; m < listPositionPattern.size(); m++) {
                                if (mayorSequence < listPositionPattern.get(m).numberPattern) {
                                    mayorSequence = listPositionPattern.get(m).numberPattern;
                                }
                            }

                            for (int m = 0; m < listPositionPattern.size(); m++) {
                                if (menorSequence > listPositionPattern.get(m).numberPattern) {
                                    menorSequence = listPositionPattern.get(m).numberPattern;
                                }
                            }
                            //BufferedImage bi = horspoolTest.createImageWithComplexity(listPositionPattern);

                            Integer numberOfColorSegments = 20;
                            BufferedImage bi = horspoolTest.createImageByColorSegment(listPositionPattern, numberOfColorSegments);
                            //HorspoolTestVelocity.saveImage(bi, "../../../../wind/" + String.valueOf(count) + ".png");
                            HorspoolTestVelocity.saveImage(bi, "../../../../wind/pruebaveloc.png");

                            List<String> listLocalHeights = new ArrayList<>();
                            Integer previous = -1;
                            int countLocal = 0;

                            for (int t = 0; t < colors.size(); t++) {

                                if (t != 0) {

                                    if (previous.equals(colors.get(t))) {
                                        countLocal++;
                                    } else {
                                        //if(countLocal>1)
                                        listLocalHeights.add(String.valueOf(countLocal * 1.0f));

                                        countLocal = 0;
                                        previous = colors.get(t);
                                        countLocal++;

                                    }
                                } else {
                                    previous = colors.get(t);
                                    countLocal++;
                                }
                            }

                            String result = "";
                            result = String.valueOf(count) + ";";
                            if (mayorT < listLocalHeights.size()) {
                                mayorT = listLocalHeights.size();
                            }
                            for (int t = 0; t < listLocalHeights.size(); t++) {
                                if (t == listLocalHeights.size() - 1) {
                                    result = result + String.valueOf(t + 1) + ":" + listLocalHeights.get(t);
                                } else {
                                    result = result + String.valueOf(t + 1) + ":" + listLocalHeights.get(t) + ";";
                                }
                            }

                            listHeightHistogram.add(result);

                            //String[] pointData = null;
                            //if (str.contains("/")) {
                            //     pointData = str.split("/"); // linux
                            //} else {
                            //   pointData = str.split("\\\\");
                            //}//
                            //String nomeFileOutput = pointData[pointData.length - 1];
                            //str = nomeFileOutput.substring(0, nomeFileOutput.length() - 4);
                            String result1 = "";
                            result1 = String.valueOf(count) + ";";
                            for (int t = 0; t < recognition.histogramRhythm.size(); t++) {
                                if (t == recognition.histogramRhythm.size() - 1) {
                                    result1 = result1 + recognition.histogramRhythm.get(t);
                                } else {
                                    result1 = result1 + recognition.histogramRhythm.get(t) + ";";
                                }
                            }

                            listStringHistogram.add(result1);

                            listLabelHistogram.add(String.valueOf(count) + ";" + str);

                        }
                    } catch (Exception ex) {
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("error");
        }

        System.out.println("Maior sequenciaaaaa :" + mayorSequence);
        System.out.println("Menor sequenciaaaaa :" + menorSequence);
        System.out.println("acabo");
        FileWriter fData = new FileWriter("sequenceVelocity.data");
        BufferedWriter outData = new BufferedWriter(fData);
        outData.write("DY\n");
        outData.write(String.valueOf(listHeightHistogram.size()) + "\n");
        outData.write(String.valueOf(mayorT) + "\n");
        for (int w = 0; w < mayorT - 1; w++) {
            outData.write("value" + String.valueOf(w) + ";");
        }

        outData.write("value" + String.valueOf(mayorT - 1) + ";" + "\n");
        System.out.println(listHeightHistogram.size());
        for (int k = 0; k < listHeightHistogram.size(); k++) {
            try {
                outData.write(listHeightHistogram.get(k));
                if (k != listHeightHistogram.size() - 1) {
                    outData.write("\n");
                }
            } catch (IOException e) {
            }
        }
        outData.close();

        FileWriter fLabel = new FileWriter("sequenceVelocity.label");
        BufferedWriter outLabel = new BufferedWriter(fLabel);

        System.out.println(listLabelHistogram.size());
        for (int k = 0; k < listLabelHistogram.size(); k++) {
            try {
                outLabel.write(listLabelHistogram.get(k));
                if (k != listLabelHistogram.size() - 1) {
                    outLabel.write("\n");
                }
            } catch (IOException e) {
            }
        }
        outLabel.close();
        System.out.println("acabo");
        fData = new FileWriter("histogramVelocity.data");
        outData = new BufferedWriter(fData);
        outData.write("DY\n");
        outData.write(String.valueOf(listStringHistogram.size()) + "\n");
        outData.write("128\n");
        for (int u = 0; u < 128; u++) {
            outData.write(u);
            if (u < 127) {
                outData.write(";");
            } else {
                outData.write("\n");
            }
        }

        System.out.println(listStringHistogram.size());
        for (int k = 0; k < listStringHistogram.size(); k++) {
            try {
                outData.write(listStringHistogram.get(k));
                if (k != listStringHistogram.size() - 1) {
                    outData.write("\n");
                }
            } catch (IOException e) {
            }
        }
        outData.close();

        fLabel = new FileWriter("histogramVelocity.label");
        outLabel = new BufferedWriter(fLabel);

        System.out.println(listLabelHistogram.size());
        for (int k = 0; k < listLabelHistogram.size(); k++) {
            try {
                outLabel.write(listLabelHistogram.get(k));
                if (k != listLabelHistogram.size() - 1) {
                    outLabel.write("\n");
                }
            } catch (IOException e) {
            }
        }
        outLabel.close();
    }

}
