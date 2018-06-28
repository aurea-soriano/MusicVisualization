/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicVisualization;

import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import utils.ExtensionFileFilter;
import vispipelinebasics.interfaces.AbstractParametersView;

/**
 *
 * @author aurea
 */
public class MusicVisualizationParamView extends AbstractParametersView {

    /**
     * Creates new form FiberSamplingParamView
     */
    public MusicVisualizationParamView(MusicVisualizationComp comp) {
        initComponents();
        this.comp = comp;
        reset();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        titleLabel = new javax.swing.JLabel();
        titleTextField = new javax.swing.JTextField();


        setBorder(javax.swing.BorderFactory.createTitledBorder("Density"));
        setLayout(new java.awt.GridBagLayout());

        titleLabel.setText("Frame Title:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(titleLabel, gridBagConstraints);

        titleTextField.setColumns(25);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        add(titleTextField, gridBagConstraints);


        labelPathLabel = new javax.swing.JLabel();
        labelPathTextField = new javax.swing.JTextField();
        labelPathButton = new javax.swing.JButton();



        labelPathLabel.setText("Labels Path:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(labelPathLabel, gridBagConstraints);

        labelPathTextField.setColumns(25);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(labelPathTextField, gridBagConstraints);

        labelPathButton.setText("Search...");
        labelPathButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labelPathButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(labelPathButton, gridBagConstraints);


        iconePathLabel = new javax.swing.JLabel();
        iconePathTextField = new javax.swing.JTextField();
        iconePathButton = new javax.swing.JButton();



        iconePathLabel.setText("Icones Path:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(iconePathLabel, gridBagConstraints);

        iconePathTextField.setColumns(25);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(iconePathTextField, gridBagConstraints);

        iconePathButton.setText("Search...");
        iconePathButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iconePathButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(iconePathButton, gridBagConstraints);
        
        
        
        playlistPathLabel = new javax.swing.JLabel();
        playlistPathTextField = new javax.swing.JTextField();
        playlistPathButton = new javax.swing.JButton();



        playlistPathLabel.setText("Playlists Path:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(playlistPathLabel, gridBagConstraints);

        playlistPathTextField.setColumns(25);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(playlistPathTextField, gridBagConstraints);

        playlistPathButton.setText("Search...");
        playlistPathButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playlistPathButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(playlistPathButton, gridBagConstraints);

    }// </editor-fold>                        

    private void iconePathButtonActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Path of icones");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File fileNameChooser = fileChooser.getSelectedFile();

                String strPathIcone = fileNameChooser.toString();
                iconePathTextField.setText(strPathIcone);
            }
        } catch (Exception ex1) {
        }
    }

    private void labelPathButtonActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Path of labels");
            ExtensionFileFilter filter = new ExtensionFileFilter("LABEL and label", new String[]{"LABEL", "label"});
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File fileNameChooser = fileChooser.getSelectedFile();

                String strPathLabel = fileNameChooser.toString();
                labelPathTextField.setText(strPathLabel);
            }
        } catch (Exception ex1) {
        }
    }

    private void playlistPathButtonActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Path of playlists");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File fileNameChooser = fileChooser.getSelectedFile();
                String strPathPlaylist = fileNameChooser.toString();
                playlistPathTextField.setText(strPathPlaylist);
            }
        } catch (Exception ex1) {
        }
    }

    @Override
    public void finished() throws IOException {

        if (!titleTextField.getText().isEmpty()) {
            comp.setTitle(titleTextField.getText());
        }

        if (!labelPathTextField.getText().isEmpty()) {
            comp.setPathsLabel(labelPathTextField.getText());
        }

        if (!iconePathTextField.getText().isEmpty()) {
            comp.setPathsIcone(iconePathTextField.getText());
        }
        if (!playlistPathTextField.getText().isEmpty()) {
            comp.setPathsPlaylist(playlistPathTextField.getText());
        }
    }

    @Override
    public void reset() {
        if (comp.getTitle() != null) {
            this.titleTextField.setText(comp.getTitle());
        }
        if (comp.getPathsLabel() != null) {
            this.labelPathTextField.setText(comp.getPathsLabel());
        }
        if (comp.getPathsIcone() != null) {
            this.iconePathTextField.setText(comp.getPathsIcone());
        }
        if (comp.getPathsPlaylist() != null) {
            this.playlistPathTextField.setText(comp.getPathsPlaylist());
        }

    }
    private MusicVisualizationComp comp;
    // Variables declaration - do not modify                     
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTextField titleTextField;
    private javax.swing.JButton labelPathButton;
    private javax.swing.JTextField labelPathTextField;
    private javax.swing.JLabel labelPathLabel;
    private javax.swing.JButton iconePathButton;
    private javax.swing.JTextField iconePathTextField;
    private javax.swing.JLabel iconePathLabel;
    private javax.swing.JButton playlistPathButton;
    private javax.swing.JTextField playlistPathTextField;
    private javax.swing.JLabel playlistPathLabel;
    // End of variables declaration                   
}
