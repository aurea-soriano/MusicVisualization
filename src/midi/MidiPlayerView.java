/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package midi;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import structures.InstancePoint;
import structures.LabelMidi;

/**
 *
 * @author aurea
 */
public class MidiPlayerView extends JFrame implements ActionListener, WindowListener {

    List<String> listPaths;
    List<String> listNames;
    List<String> listLabels;
    JPanel controlPanel;
    JPanel buttonPanel;
    JTable tableMidis;
    JScrollPane scrollpane;
    JButton saveButton;
    JButton playButton;
    JButton stopButton;
    JButton nextButton;
    JButton previousButton;
    MidiPlayer midiPlayer;
    JFileChooser fileChooser;

    public MidiPlayerView(List<InstancePoint> listPoints, List<LabelMidi> listLabelMidi) {
        
        controlPanel = new JPanel();
        scrollpane = new JScrollPane();
        listPaths = new ArrayList<String>();
        listNames = new ArrayList<String>();
        listLabels = new ArrayList<String>();
        buttonPanel = new JPanel();
        saveButton = new JButton();
        playButton = new JButton();
        stopButton = new JButton();
        nextButton = new JButton();
        previousButton = new JButton();
        midiPlayer = new MidiPlayer();
        fileChooser = new JFileChooser();
        for (int i = 0; i < listPoints.size(); i++) {
            for (int j = 0; j < listLabelMidi.size(); j++) {
                if (listPoints.get(i).getLabel().equals(listLabelMidi.get(j).getName())) {
                    String nameData = listLabelMidi.get(j).getPath();
                    nameData = nameData.substring(nameData.indexOf("/") + 1);
                    nameData = nameData.substring(nameData.indexOf("/") + 1);
                    nameData = nameData.substring(nameData.indexOf("/") + 1);
                    nameData = nameData.substring(nameData.indexOf("/") + 1);
                    nameData = nameData.substring(nameData.indexOf("/") + 1);
                    nameData = nameData.substring(0, nameData.lastIndexOf("."));
                    nameData = nameData.replaceAll("/", "-");
                    listNames.add(nameData);
                    listPaths.add(listLabelMidi.get(j).getPath());
                    listLabels.add(listPoints.get(i).getLabel());
            
                    
                    break;
                }
            }
        }



        loadPlayerMidi();
    }

    public void loadPlayerMidi() {
        try {
            remove(controlPanel);
            remove(scrollpane);
            remove(buttonPanel);
            remove(saveButton);
            remove(playButton);
            remove(stopButton);
            remove(nextButton);
            remove(previousButton);
            remove(fileChooser);
            controlPanel = new JPanel();
            scrollpane = new JScrollPane();
            saveButton = new JButton();
            playButton = new JButton();
            stopButton = new JButton();
            nextButton = new JButton();
            previousButton = new JButton();
            fileChooser = new JFileChooser();
        } catch (Exception e) {
            remove(controlPanel);
            remove(scrollpane);
            remove(saveButton);
            remove(buttonPanel);
            remove(playButton);
            remove(stopButton);
            remove(nextButton);
            remove(previousButton);
            remove(fileChooser);
        }
        this.setTitle("Player of Midi");

        GridBagConstraints constraints = new GridBagConstraints();
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());


        if (listNames != null) {
            DefaultTableModel aModel = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            aModel.addColumn("Music", listNames.toArray());

            tableMidis = new JTable();
            tableMidis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            //tableMidis.setPreferredScrollableViewportSize(new Dimension(600, 200));


            tableMidis.setModel(aModel);
            scrollpane = new JScrollPane(tableMidis);


            constraints.gridx = 0;
            constraints.gridy = 1;
            controlPanel.add(scrollpane, constraints);
        }
        GridBagConstraints constraintsButton = new GridBagConstraints();
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());


        constraintsButton.gridx = 0;
        constraintsButton.gridy = 0;
        saveButton = new JButton(new ImageIcon(MidiPlayerView.class.getResource("/images/save.png")));
        saveButton.setActionCommand("save");
        saveButton.setPreferredSize(new Dimension(40, 40));
        saveButton.addActionListener(this);
        saveButton.setToolTipText("save");

        buttonPanel.add(saveButton, constraintsButton);

        constraintsButton.gridx = 1;
        constraintsButton.gridy = 0;
        previousButton = new JButton(new ImageIcon(MidiPlayerView.class.getResource("/images/previous.png")));
        previousButton.setActionCommand("previous");
        previousButton.setPreferredSize(new Dimension(40, 40));
        previousButton.addActionListener(this);
        previousButton.setToolTipText("previous");

        buttonPanel.add(previousButton, constraintsButton);

        constraintsButton.gridx = 2;
        constraintsButton.gridy = 0;
        stopButton = new JButton(new ImageIcon(MidiPlayerView.class.getResource("/images/stop.png")));
        stopButton.setActionCommand("stop");
        stopButton.setPreferredSize(new Dimension(40, 40));
        stopButton.addActionListener(this);
        stopButton.setToolTipText("stop");

        buttonPanel.add(stopButton, constraintsButton);

        constraintsButton.gridx = 3;
        constraintsButton.gridy = 0;
        playButton = new JButton(new ImageIcon(MidiPlayerView.class.getResource("/images/play.png")));
        playButton.setActionCommand("play");
        playButton.setPreferredSize(new Dimension(40, 40));
        playButton.addActionListener(this);
        playButton.setToolTipText("play");

        buttonPanel.add(playButton, constraintsButton);

        constraintsButton.gridx = 4;
        constraintsButton.gridy = 0;
        nextButton = new JButton(new ImageIcon(MidiPlayerView.class.getResource("/images/next.png")));
        nextButton.setActionCommand("next");
        nextButton.setPreferredSize(new Dimension(40, 40));
        nextButton.addActionListener(this);
        nextButton.setToolTipText("next");

        buttonPanel.add(nextButton, constraintsButton);


        constraints.gridx = 0;
        constraints.gridy = 2;
        controlPanel.add(buttonPanel, constraints);

        add("Center", controlPanel);
        this.addWindowListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getActionCommand()).equals("play")) {
            int index = this.tableMidis.getSelectedRow();
            if (index < 0) {
                index = 0;
                tableMidis.getSelectionModel().setSelectionInterval(index, index);
            }
            File file = new File(this.listPaths.get(index));
            midiPlayer.play(file);
        }
        if ((e.getActionCommand()).equals("stop")) {
            midiPlayer.stop();
        }
        if ((e.getActionCommand()).equals("next")) {

            int index = this.tableMidis.getSelectedRow();
            index = index + 1;
            if (index >= listPaths.size()) {
                index = listPaths.size() - 1;
            }
            tableMidis.getSelectionModel().setSelectionInterval(index, index);
            File file = new File(this.listPaths.get(index));
            midiPlayer.play(file);
        }
        if ((e.getActionCommand()).equals("previous")) {
            int index = this.tableMidis.getSelectedRow();
            index = index - 1;
            if (index < 0) {
                index = 0;
            }
            tableMidis.getSelectionModel().setSelectionInterval(index, index);
            File file = new File(this.listPaths.get(index));
            midiPlayer.play(file);
        }

        if ((e.getActionCommand()).equals("save")) {
            saveListActionPerformed(e);
        }

    }

    public void saveListActionPerformed(java.awt.event.ActionEvent evt) {
        if (listPaths != null) {
            if (listPaths.size() > 0) {
                try {

                    int result = fileChooser.showSaveDialog(this);


                    if (result == JFileChooser.APPROVE_OPTION) {
                        File fileNameChooser = fileChooser.getSelectedFile();
                        String fileName = fileNameChooser.toString();

                        try {
                            FileWriter fstreamOut = new FileWriter(fileName);
                            BufferedWriter out = new BufferedWriter(fstreamOut);
                            for (int i = 0; i < listPaths.size(); i++) {

                                out.write(listLabels.get(i)+"; ");
                                out.write(listPaths.get(i));

                                out.write('\n');
                            }
                            out.close();
                        } catch (IOException e) {

                            JOptionPane.showMessageDialog(this, e.getMessage(),
                                    "Problems saving the file", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(),
                            "Problems saving the file", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Nothing to save.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nothing to save.");
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        try {
            midiPlayer.stop();
        } catch (Exception e2) {
        }

    }

    @Override
    public void windowClosed(WindowEvent e) {
        try {
            midiPlayer.stop();
        } catch (Exception e1) {
        }
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
