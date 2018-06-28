/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package midi;

import distance.DistanceMatrix;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import projection.util.ProjectionConstants;
import structures.InstancePoint;
import structures.LabelMidi;

/**
 *
 * @author aurea64
 */
public class MidiIconePlayerView extends JFrame implements ActionListener, WindowListener {

    List<String> listPaths;
    List<String> listNames;
    List<String> listLabels;
    List<ImageIcon> listImages;
    List<String> listIconePaths;
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
    JLabel labelSong;
    String pathsPlaylists;
    BufferedImage subImage;
    DistanceMatrix distanceMatrix;

    public MidiIconePlayerView(List<InstancePoint> listPoints, List<LabelMidi> listLabelMidi, String pathIcones, String pathPlaylist, BufferedImage image, DistanceMatrix distanceMatrix) {

        this.distanceMatrix = distanceMatrix;
        int maxSizeImage = 80;
        int width, height;
        if (image.getWidth() > image.getHeight()) {
            width = maxSizeImage;
            height = image.getHeight() * maxSizeImage / image.getWidth();
        } else {
            height = maxSizeImage;
            width = image.getWidth() * maxSizeImage / image.getHeight();
        }
        subImage = this.getScaledImage(image, width, height);
        pathsPlaylists = pathPlaylist;
        controlPanel = new JPanel();
        scrollpane = new JScrollPane();
        listPaths = new ArrayList<String>();
        listNames = new ArrayList<String>();
        listLabels = new ArrayList<String>();
        listImages = new ArrayList<ImageIcon>();
        listIconePaths = new ArrayList<String>();
        buttonPanel = new JPanel();
        saveButton = new JButton();
        playButton = new JButton();
        stopButton = new JButton();
        nextButton = new JButton();
        previousButton = new JButton();
        midiPlayer = new MidiPlayer();
        fileChooser = new JFileChooser();

        if (distanceMatrix != null) {
            //sacando el primer punto.
            Integer indexLabel = findLabelinList(listLabelMidi, listPoints.get(0).getLabel());

            String nameData = listLabelMidi.get(indexLabel).getPath();
            nameData = nameData.substring(nameData.lastIndexOf("/") + 1);
            nameData = nameData.substring(0, nameData.lastIndexOf("."));
            listNames.add(nameData);
            listPaths.add(listLabelMidi.get(indexLabel).getPath());
            listLabels.add(listPoints.get(0).getLabel());

            listIconePaths.add(pathIcones + "/" + listPoints.get(0).getLabel() + ".png");

            ImageIcon imageIcon = new ImageIcon(pathIcones + "/" + listPoints.get(0).getLabel() + ".png");
            Image img = imageIcon.getImage();
            listImages.add(new ImageIcon(this.getScaledImage(img, 300, 35)));


            //vamos a sacar todos los otros puntos
            while (listLabels.size() < listPoints.size()) {
                
                Integer indexNextPoint = this.findNextObjectSorted(this.listLabels.get(this.listLabels.size() - 1), this.listLabels, listPoints);
                indexLabel = findLabelinList(listLabelMidi, listPoints.get(indexNextPoint).getLabel());
                nameData = listLabelMidi.get(indexLabel).getPath();
                nameData = nameData.substring(nameData.lastIndexOf("/") + 1);
                nameData = nameData.substring(0, nameData.lastIndexOf("."));
                listNames.add(nameData);
                listPaths.add(listLabelMidi.get(indexLabel).getPath());
                listLabels.add(listPoints.get(indexNextPoint).getLabel());

                listIconePaths.add(pathIcones + "/" + listPoints.get(indexNextPoint).getLabel() + ".png");

                imageIcon = new ImageIcon(pathIcones + "/" + listPoints.get(indexNextPoint).getLabel() + ".png");
                img = imageIcon.getImage();
                listImages.add(new ImageIcon(this.getScaledImage(img, 300, 35)));

            }

            //distanceMatrix.getLabels()

        } else {
            for (int i = 0; i < listPoints.size(); i++) {
                Integer indexLabel = findLabelinList(listLabelMidi, listPoints.get(i).getLabel());
                if (indexLabel != -1) {
                    String nameData = listLabelMidi.get(indexLabel).getPath();
                    nameData = nameData.substring(nameData.lastIndexOf("/") + 1);
                    nameData = nameData.substring(0, nameData.lastIndexOf("."));
                    listNames.add(nameData);
                    listPaths.add(listLabelMidi.get(indexLabel).getPath());
                    listLabels.add(listPoints.get(i).getLabel());

                    listIconePaths.add(pathIcones + "/" + listPoints.get(i).getLabel() + ".png");

                    ImageIcon imageIcon = new ImageIcon(pathIcones + "/" + listPoints.get(i).getLabel() + ".png");
                    Image img = imageIcon.getImage();
                    listImages.add(new ImageIcon(this.getScaledImage(img, 300, 35)));

                }

            }

        }

        loadPlayerMidi();
    }

    public Integer findNextObjectSorted(String label, List<String> listLabelSorted, List<InstancePoint> listPoints) {

        Float minDistance = Float.POSITIVE_INFINITY;
        Integer indexMin = -1;
        Integer indexA = this.distanceMatrix.getIds().indexOf(Integer.valueOf(label));
        for (int i = 0; i < listPoints.size(); i++) {
            if (!listLabelSorted.contains(listPoints.get(i).getLabel())) {
                Integer indexB = this.distanceMatrix.getIds().indexOf(Integer.valueOf(listPoints.get(i).getLabel()));
                if (this.distanceMatrix.getDistance(indexA, indexB) < minDistance) {
                    minDistance = this.distanceMatrix.getDistance(indexA, indexB);
                    indexMin = i;
                }

            }
        }
        return indexMin;

    }

    public Integer findLabelinList(List<LabelMidi> listLabelMidi, String label) {
        for (int j = 0; j < listLabelMidi.size(); j++) {
            if (label.equals(listLabelMidi.get(j).getName())) {
                return j;
            }

        }
        return -1;

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

        constraints.gridx = 0;
        constraints.gridy = 0;
        controlPanel.add(new JLabel(new ImageIcon(subImage)), constraints);
        if (listLabels != null) {
            DefaultTableModel aModel = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            aModel.addColumn("Music", listImages.toArray());

            tableMidis = new JTable() {
                //  Returning the Class of each column will allow different
                //  renderers to be used based on Class
                public Class getColumnClass(int column) {
                    return getValueAt(0, column).getClass();
                }
            };
            tableMidis.setBackground(Color.BLACK);
            tableMidis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            //tableMidis.setPreferredScrollableViewportSize(new Dimension(600, 200));


            tableMidis.setModel(aModel);
            tableMidis.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        playSelectedSong();
                    }
                }
            });
            scrollpane = new JScrollPane(tableMidis);


            constraints.gridx = 0;
            constraints.gridy = 1;
            controlPanel.add(scrollpane, constraints);
        }

        constraints.gridx = 0;
        constraints.gridy = 2;
        labelSong = new JLabel("");
        controlPanel.add(labelSong, constraints);


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
        constraints.gridy = 3;
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
            labelSong.setText(this.listNames.get(index));
            File file = new File(this.listPaths.get(index));
            midiPlayer.play(file);
        }
        if ((e.getActionCommand()).equals("stop")) {
            midiPlayer.stop();
            labelSong.setText("");
        }
        if ((e.getActionCommand()).equals("next")) {

            int index = this.tableMidis.getSelectedRow();
            index = index + 1;
            if (index >= listPaths.size()) {
                index = listPaths.size() - 1;
            }
            tableMidis.getSelectionModel().setSelectionInterval(index, index);
            labelSong.setText(this.listNames.get(index));
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
            labelSong.setText(this.listNames.get(index));
            File file = new File(this.listPaths.get(index));
            midiPlayer.play(file);
        }

        if ((e.getActionCommand()).equals("save")) {
            saveListActionPerformed(e);
        }

    }

    public void playSelectedSong() {
        int index = this.tableMidis.getSelectedRow();
        if (index < 0) {
            index = 0;
            tableMidis.getSelectionModel().setSelectionInterval(index, index);
        }
        labelSong.setText(this.listNames.get(index));
        File file = new File(this.listPaths.get(index));
        midiPlayer.play(file);
    }

    public void saveListActionPerformed(java.awt.event.ActionEvent evt) {
        if (listPaths != null) {
            if (listPaths.size() > 0) {
                try {

                    int result = fileChooser.showSaveDialog(this);


                    if (result == JFileChooser.APPROVE_OPTION) {
                        File fileNameChooser = fileChooser.getSelectedFile();
                        String fileName = fileNameChooser.toString();

                        if (fileName == null || fileName.equals("")) {
                            JOptionPane.showMessageDialog(null, "Enter a name for the playlist.");
                        } else {
                            fileName = pathsPlaylists + "/" + fileName;
                            if (!fileName.contains(".lst")) {
                                fileName = fileName + ".lst";
                            }
                            try {
                                FileWriter fstreamOut = new FileWriter(fileName);
                                BufferedWriter out = new BufferedWriter(fstreamOut);
                                for (int i = 0; i < listPaths.size(); i++) {

                                    out.write(listLabels.get(i) + "; " + listPaths.get(i) + "\n");

                                }
                                out.close();
                            } catch (IOException e) {

                                JOptionPane.showMessageDialog(this, e.getMessage(),
                                        "Problems saving the file", JOptionPane.ERROR_MESSAGE);
                            }
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

    private BufferedImage getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }
}