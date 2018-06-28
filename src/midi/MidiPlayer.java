/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package midi;

/**
 *
 * @author aurea
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

/**
 * Una clase sencilla para reproducir archivos o secuencias MIDI con Java.
 *
 * Si falla bajo Linux (en mi caso, Ubuntu) con "Midi no disponible..." usa
 * 'aoss ant run' en lugar de 'ant run' (desde una consola fuera de NetBeans)
 * 'aoss' hace que cosas OSS se comporten bien bajo Alsa
 *
 * @author mfreire
 */
public class MidiPlayer {

    private static Sequencer sequencer;

    /**
     * Cambia la velocidad de reproducción. 1.0f = normal, 2.0f = 2x más rápido,
     * etcétera.
     *
     * @param factor de velocidad
     */
    public void setTempoFactor(float factor) {
        if (sequencer != null) {
            sequencer.setTempoFactor(factor);
        }
    }

    /**
     * Reproduce un archivo. La reproducción se hace en un hilo aparte, de forma
     * que la aplicación no acaba hasta que se finaliza la reproducción en este
     * hilo. Si ya había algo en reproducción, lo descarta y empieza a tocar lo
     * nuevo.
     *
     * @param is InputStream con la cosa a reproducir. Usa Util.
     */
    public void play(InputStream is) {
        try {
            if (sequencer == null) {
                sequencer = MidiSystem.getSequencer();
            }
            if (sequencer.isRunning()) {
                sequencer.stop();
            }
            sequencer.setSequence(MidiSystem.getSequence(is));
            if (!sequencer.isOpen()) {
                sequencer.open();
            }
            sequencer.start();
        } catch (InvalidMidiDataException imde) {
            System.err.println("Secuencia midi contiene errores...");
        } catch (IOException ioe) {
            System.err.println("Error de lectura...");
        } catch (MidiUnavailableException mue) {
            System.err.println("Midi no disponible...");
        }
    }

    public void stop() {
        if (sequencer.isRunning()) {
            sequencer.stop();
        }
    }

    /**
     * Reproduce un archivo. La reproducción se hace en un hilo aparte, de forma
     * que la aplicación no acaba hasta que se finaliza la reproducción en este
     * hilo. Si ya había algo en reproducción, lo descarta y empieza a tocar lo
     * nuevo.
     *
     * @param f Fichero MIDI a reproducir.
     */
    public void play(File f) {
        try {
            play(new FileInputStream(f));
        } catch (FileNotFoundException fnfe) {
            System.err.println("Error abriendo archivo midi '"
                    + f.getAbsolutePath() + "'.");
        }
    }

    /**
     * Reproduce un archivo. La reproducción se hace en un hilo aparte, de forma
     * que la aplicación no acaba hasta que se finaliza la reproducción en este
     * hilo. Si ya había algo en reproducción, lo descarta y empieza a tocar lo
     * nuevo.
     *
     * @param recurso Nombre de recurso a reproducir (ej.: musica/comienzo.mid).
     */
    public void play(String recurso) {
        play(MidiPlayer.class.getClassLoader().getResourceAsStream(recurso));
    }

    /**
     * Devuelve 'true' si hay alto reproduciendose
     *
     * @return True si hay algo en el tocadiscos
     */
    public boolean isPlaying() {
        return sequencer != null && sequencer.isRunning();
    }

    public static void main(String args[]) {
        String midi = "../../../../database-midi/latino/un_beso_y_una_flor.mid";
        File file = new File(midi);
        MidiPlayer midiPlayer = new MidiPlayer();
        midiPlayer.play(file);
    }
}