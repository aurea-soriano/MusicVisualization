package musicVisualization;

import distance.DistanceMatrix;
import distance.dissimilarity.AbstractDissimilarity;
import distance.dissimilarity.DissimilarityFactory;
import distance.dissimilarity.DissimilarityFactory.DissimilarityType;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import matrix.AbstractMatrix;
import matrix.AbstractVector;
import matrix.MatrixFactory;
import matrix.dense.DenseMatrix;
import matrix.dense.DenseVector;
import midi.MidiIconePlayerView;
import midi.MidiPlayer;
import projection.util.ProjectionConstants;
import projection.view.ProjectionFrame;
import structures.*;
import tree.basics.Tree;
import tree.layout.radial.RadialLayoutComp;
import tree.model.TreeModel;
import tree.model.TreeModelComp;
import tree.technique.packagenj.PackageNJConnectionComp;
import tree.view.TreeFrameComp;
import utils.ExtensionFileFilter;
import visualizationbasics.util.PropertiesManager;
import visualizationbasics.util.SaveDialog;
import visualizationbasics.util.filter.DATAFilter;
import visualizationbasics.util.filter.PNGFilter;

/**
 *
 * @author asoriano
 */
public class MusicVisualization extends JFrame implements ActionListener, MouseListener, MouseMotionListener, ChangeListener, WindowListener, ComponentListener {

    private static final long serialVersionUID = 1L;
    Boolean flagLoad = false;
    String nameX;
    String nameY;
    String fileName;
    String pathsLabel;
    String pathsIcones;
    String pathsPlaylists;
    HashSet<Float> hashSetClusters;
    boolean checkLens;
    boolean checkLensSplatting;
    boolean checkIconSummary;
    boolean checkZoomLocal;
    boolean checkLabel;
    boolean checkNameSongsLabel;
    double factor;
    Float radiusNN;
    List<InstancePoint> listPoints;
    List<String> listTypePoints;
    List<String> listColors;
    List<String> listDensity;
    List<String> listPlayList;
    String[] strPlaylist;
    String[] strPoints;
    String[] strPlayList;
    String[] strColors;
    String[] strDensity;
    Integer numData;
    Float maxX;
    Float maxY;
    Float minX;
    Float minY;
    JPanel controlPanel;
    JPanel playListPanel;
    JPanel buttonPanel;
    JPanel buttonPanel2;
    JButton drawButton;
    JButton semanticZoomButton;
    JButton reproduceButton;
    JButton reproduceIconeButton;
    JButton treeButton;
    JButton buttonIconSummary;
    //JButton landscapeButton;
    //JButton openButton;
    JTextField radiusNNtext;
    JTextField thresholdField;
    JTextField splatSigmaField;
    JTextField fieldSizeSplat;
    JComboBox columns1;
    JComboBox columns2;
    JComboBox comboColors;
    JComboBox comboPoints;
    JCheckBox checkLines;
    JComboBox comboDensity;
    JComboBox comboPlayList;
    JCheckBox checkMarchingSquares;
    JPanel drawingArea;
    JFileChooser fileChooser;
    JButton buttonZoomOriginal;
    JButton buttonZoomPrevious;
    JButton buttonLens;
    JButton buttonLensSplatting;
    JButton buttonZoomLocal;
    JButton buttonLabel;
    JButton buttonNameSongsLabel;
    JTextArea textAreaMessage;
    String title;
    Integer numTitle;
    BufferedImage imageOriginal;
    boolean boolListX;
    boolean boolListY;
    String option;
    private JMenuBar menubar;
    private JMenu filemenu, exportmenu;
    private JMenuItem fileItemOpen, fileItemClose, exportItemPNG, exportItemPoints;
    String message = "";
    String label = "";
    List<LabelMidi> listLabelMidi;
    List<Connection> listConnections;
    AbstractMatrix matrix;
    DistanceMatrix distanceMatrix;
    JSlider sliderBrightness;
    List<String> listPaths;
    List<String> listNames;
    List<String> listLabels;
    List<String> listPathsPlayList;
    List<String> listNamesPlayList;
    List<String> listLabelsPlayList;
    JTable tableMidis;
    JTable tableMidisPlaylist;
    JScrollPane scrollpane;
    JScrollPane scrollpanePlaylist;
    JButton addAllButton;
    JButton addSelectionButton;
    JPanel buttonAddPlaylistPanel;
    JTextField newPlayListField;
    JButton playSelectedSongsButton;
    JButton stopSelectedSongsButton;
    JButton nextSelectedSongsButton;
    JButton previousSelectedSongsButton;
    JButton removeSelectedSongsButton;
    JPanel buttonSelectedSongsPlaylistPanel;
    JButton savePlaylistButton;
    JButton playPlaylistButton;
    JButton stopPlaylistButton;
    JButton nextPlaylistButton;
    JButton previousPlaylistButton;
    JButton removePlaylistButton;
    JButton removeFilePlaylistButton;
    JPanel buttonPlaylistPanel;
    JLabel lineDivisionLabel;
    JLabel messagePlaylistLabel;
    JTextArea currentSongTextArea;
    JLabel iconeLabel;
    MidiPlayer midiPlayer;
    Color basicColor;
    Integer widthTable = 10;
    Integer heightTable = 10;

    public MusicVisualization(String title, Integer numTitle, String option, Color basicColor) {

        flagLoad = false;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addWindowListener(this);
        this.addComponentListener(this);


        listNames = new ArrayList<>();
        listPaths = new ArrayList<>();
        listLabels = new ArrayList<>();
        listNamesPlayList = new ArrayList<>();
        listPathsPlayList = new ArrayList<>();
        listLabelsPlayList = new ArrayList<>();
        hashSetClusters = new HashSet<>();
        menubar = new JMenuBar();
        filemenu = new JMenu("File");
        filemenu.setMnemonic('F');
        filemenu.add(new JSeparator());
        exportmenu = new JMenu("Export");
        exportmenu.setMnemonic('E');
        exportmenu.add(new JSeparator());
        if (numTitle == 0 && !option.equals("projection")) {
            fileItemOpen = new JMenuItem("Open");
            fileItemOpen.setMnemonic('O');
            fileItemOpen.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    fileOpenActionPerformed(evt);
                }
            });
        }

        fileItemClose = new JMenuItem("Close");
        fileItemClose.setMnemonic('C');
        fileItemClose.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileCloseActionPerformed(evt);
            }
        });
        if (numTitle == 0) {
            fileItemClose.add(new JSeparator());
        }
        exportItemPNG = new JMenuItem("PNG file");
        exportItemPNG.setMnemonic('P');
        exportItemPNG.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportItemPNGActionPerformed(evt);
            }
        });
        exportItemPoints = new JMenuItem("2D Points file");
        exportItemPNG.setMnemonic('2');
        exportItemPoints.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportItem2DPointsActionPerformed(evt);
            }
        });
        exportItemPoints.add(new JSeparator());
        if (numTitle == 0 && !option.equals("projection")) {
            filemenu.add(fileItemOpen);
        }
        filemenu.add(fileItemClose);
        exportmenu.add(exportItemPNG);
        exportmenu.add(exportItemPoints);
        menubar.add(filemenu);
        menubar.add(exportmenu);
        this.setJMenuBar(menubar);

        this.title = title;
        this.numTitle = numTitle;
        this.option = option;
        setTitle(title + numTitle);

        controlPanel = new JPanel();
        playListPanel = new JPanel();
        fileChooser = new JFileChooser();
        //openButton = new JButton("open");
        //openButton.setPreferredSize(new Dimension(150, 20));
        //openButton.addActionListener(this);

        listTypePoints = new ArrayList<>();
        listTypePoints.add("None");
        listTypePoints.add("Points");
        listTypePoints.add("Boundary Points");
        listTypePoints.add("Transparency");
        listTypePoints.add("Icones");
        listTypePoints.add("Graph");
        strPoints = new String[listTypePoints.size()];
        listTypePoints.toArray(strPoints);


        listDensity = new ArrayList<>();
        listDensity.add("None");
        listDensity.add("Splatting");
        listDensity.add("Triangular Splatting");
        listDensity.add("Splatting with NN");
        listDensity.add("Gaussian Blur");
        listDensity.add("Triangular Blur");
        listDensity.add("Welch Splatting");
        listDensity.add("Apprx. Splatting 1");
        listDensity.add("Apprx. Splatting 2");
        listDensity.add("Apprx. Splatting 3");

        listColors = new ArrayList<>();
        listColors.add("Aurea");
        listColors.add("BTC");
        listColors.add("BTY");
        listColors.add("CategoryScale");
        listColors.add("ColorPaperJazz");
        listColors.add("Gray");
        listColors.add("GTW");
        listColors.add("HeatedObject");
        listColors.add("LinGray");
        listColors.add("LOCS");
        listColors.add("Magenta");
        listColors.add("OCS");
        listColors.add("PseudoRainbow");
        listColors.add("Rainbow");

        strColors = new String[listColors.size()];
        listColors.toArray(strColors);

        strDensity = new String[listDensity.size()];
        listDensity.toArray(strDensity);
        factor = 1.0;
        checkLens = false;
        checkLensSplatting = false;
        checkIconSummary = false;

        //strNameDataset1 = "";
        //strNameDataset2 = "";

        boolListX = false;
        boolListY = false;

        listPoints = new ArrayList<>();

        numData = 0;
        this.basicColor = basicColor;

    }

    public void loadInitial() throws IOException {
        if (numTitle != 0 || option.equals("projection")) {
            loadDrawPanel();
        }
    }

    public void loadDrawPanelError() {
        clean();

        add(drawingArea);//"Center"

        GridBagConstraints constraints = new GridBagConstraints();
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());

        /*
         * if (numTitle == 0 && !option.equals("projection")) {
         * constraints.gridx = 0; constraints.gridy = 0; fileChooser = new
         * JFileChooser(); openButton = new JButton("open");
         * openButton.addActionListener(this); openButton.setPreferredSize(new
         * Dimension(150, 20)); controlPanel.add(openButton, constraints); }
         */

        GridBagConstraints constraintsButton = new GridBagConstraints();
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());


        constraintsButton.gridx = 0;
        constraintsButton.gridy = 0;
        buttonZoomOriginal = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/zoom-original.png")));
        buttonZoomOriginal.setActionCommand("zoomOriginal");

        buttonZoomOriginal.setPreferredSize(new Dimension(30, 20));
        buttonZoomOriginal.addActionListener(this);
        buttonZoomOriginal.setToolTipText("original");
        buttonZoomOriginal.setEnabled(false);
        buttonPanel.add(buttonZoomOriginal, constraintsButton);


        constraintsButton.gridx = 1;
        constraintsButton.gridy = 0;
        buttonLens = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/system-search.png")));
        buttonLens.setActionCommand("lens");

        buttonLens.setPreferredSize(new Dimension(30, 20));
        buttonLens.addActionListener(this);
        buttonLens.setToolTipText("lens");
        buttonLens.setEnabled(false);
        buttonPanel.add(buttonLens, constraintsButton);

        constraintsButton.gridx = 2;
        constraintsButton.gridy = 0;
        buttonLensSplatting = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/splat.png")));
        buttonLensSplatting.setActionCommand("lensSplatting");
        buttonLensSplatting.setPreferredSize(new Dimension(30, 20));
        buttonLensSplatting.addActionListener(this);
        buttonLensSplatting.setToolTipText("splat");
        buttonLensSplatting.setEnabled(false);
        buttonPanel.add(buttonLensSplatting, constraintsButton);

        constraintsButton.gridx = 3;
        constraintsButton.gridy = 0;
        buttonZoomLocal = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/mail_new.png")));
        buttonZoomLocal.setActionCommand("zoomLocal");
        buttonZoomLocal.setPreferredSize(new Dimension(30, 20));
        buttonZoomLocal.addActionListener(this);
        buttonZoomLocal.setToolTipText("zoom local");
        buttonZoomLocal.setEnabled(false);
        buttonPanel.add(buttonZoomLocal, constraintsButton);

        constraintsButton.gridx = 4;
        constraintsButton.gridy = 0;
        buttonLabel = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/numero5.png")));
        buttonLabel.setActionCommand("labelInstances");
        buttonLabel.setPreferredSize(new Dimension(30, 20));
        buttonLabel.addActionListener(this);
        buttonLabel.setToolTipText("labels");
        buttonLabel.setEnabled(false);
        buttonPanel.add(buttonLabel, constraintsButton);

        constraintsButton.gridx = 5;
        constraintsButton.gridy = 0;
        buttonNameSongsLabel = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/letraA.png")));
        buttonNameSongsLabel.setActionCommand("nameSongsLabelInstances");
        buttonNameSongsLabel.setPreferredSize(new Dimension(30, 20));
        buttonNameSongsLabel.addActionListener(this);
        buttonNameSongsLabel.setToolTipText("names");
        buttonNameSongsLabel.setEnabled(false);
        buttonPanel.add(buttonNameSongsLabel, constraintsButton);

        constraints.gridx = 0;
        constraints.gridy = 1;
        controlPanel.add(buttonPanel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        controlPanel.add(new JLabel("Database"), constraints);

        constraints.gridx = 0;
        constraints.gridy = 6;
        JLabel labelFileName = new JLabel("");
        controlPanel.add(labelFileName, constraints);

        constraints.gridx = 0;
        constraints.gridy = 7;
        controlPanel.add(new JLabel("Data number"), constraints);

        constraints.gridx = 0;
        constraints.gridy = 8;
        JTextField dataNumber = new JTextField(13);
        dataNumber.setText(this.numData.toString() + " instances");
        dataNumber.setEditable(false);
        controlPanel.add(dataNumber, constraints);

        constraints.gridx = 0;
        constraints.gridy = 9;
        controlPanel.add(new JLabel("Axis X"), constraints);

        constraints.gridx = 0;
        constraints.gridy = 10;
        columns1 = new JComboBox();
        columns1.setPreferredSize(new Dimension(150, 20));
        controlPanel.add(columns1, constraints);

        constraints.gridx = 0;
        constraints.gridy = 11;
        controlPanel.add(new JLabel("Axis Y"), constraints);

        constraints.gridx = 0;
        constraints.gridy = 12;
        columns2 = new JComboBox();
        columns2.setPreferredSize(new Dimension(150, 20));
        controlPanel.add(columns2, constraints);

        constraints.gridx = 0;
        constraints.gridy = 13;
        controlPanel.add(new JLabel("Radius - NN"), constraints);

        constraints.gridx = 0;
        constraints.gridy = 14;
        radiusNNtext = new JTextField(13);
        radiusNNtext.setText("1.0f");
        controlPanel.add(radiusNNtext, constraints);

        constraints.gridx = 0;
        constraints.gridy = 15;
        controlPanel.add(new JLabel("Sigma"), constraints);

        constraints.gridx = 0;
        constraints.gridy = 16;
        splatSigmaField = new JTextField(13);
        splatSigmaField.setText("11.0f");
        controlPanel.add(splatSigmaField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 17;
        controlPanel.add(new JLabel("Control Resolution"), constraints);

        constraints.gridx = 0;
        constraints.gridy = 18;
        fieldSizeSplat = new JTextField(13);
        fieldSizeSplat.setText(Integer.toString(51));
        controlPanel.add(fieldSizeSplat, constraints);

        constraints.gridx = 0;
        constraints.gridy = 19;
        controlPanel.add(new JLabel("Threshold(MSquares)"), constraints);

        constraints.gridx = 0;
        constraints.gridy = 20;
        thresholdField = new JTextField(13);
        thresholdField.setText("0.5");
        controlPanel.add(thresholdField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 21;
        controlPanel.add(new JLabel("Type of point"), constraints);

        constraints.gridx = 0;
        constraints.gridy = 22;
        comboPoints = new JComboBox(strPoints);
        comboPoints.setPreferredSize(new Dimension(150, 20));
        comboPoints.setSelectedIndex(1);
        controlPanel.add(comboPoints, constraints);


        constraints.gridx = 0;
        constraints.gridy = 23;
        controlPanel.add(new JLabel("Type of density"), constraints);

        constraints.gridx = 0;
        constraints.gridy = 24;
        comboDensity = new JComboBox(strDensity);
        comboDensity.setPreferredSize(new Dimension(150, 20));
        comboDensity.setSelectedIndex(0);
        controlPanel.add(comboDensity, constraints);

        constraints.gridx = 0;
        constraints.gridy = 25;
        checkMarchingSquares = new JCheckBox("Marching Squares");
        controlPanel.add(checkMarchingSquares, constraints);

        constraints.gridx = 0;
        constraints.gridy = 26;
        checkLines = new JCheckBox("Legends                ");
        controlPanel.add(checkLines, constraints);

        constraints.gridx = 0;
        constraints.gridy = 27;
        controlPanel.add(new JLabel("COLOR"), constraints);

        constraints.gridx = 0;
        constraints.gridy = 28;
        comboColors = new JComboBox(strColors);
        comboColors.setPreferredSize(new Dimension(150, 20));
        comboColors.setSelectedIndex(0);
        controlPanel.add(comboColors, constraints);


        GridBagConstraints constraintsButton2 = new GridBagConstraints();
        buttonPanel2 = new JPanel();
        buttonPanel2.setLayout(new GridBagLayout());




        constraintsButton2.gridx = 1;
        constraintsButton2.gridy = 0;
        semanticZoomButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/semanticZoom.png")));
        semanticZoomButton.setActionCommand("semanticZoom");
        semanticZoomButton.setPreferredSize(new Dimension(37, 37));
        semanticZoomButton.addActionListener(this);
        semanticZoomButton.setToolTipText("Semantic Zoom");
        buttonPanel2.add(semanticZoomButton, constraintsButton2);


        if (!option.equals("projection") || (option.equals("projection") && matrix != null)) {
            constraintsButton2.gridx = 2;
            constraintsButton2.gridy = 0;
            treeButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/tree2.png")));
            treeButton.setActionCommand("phylogeneticTree");
            treeButton.setPreferredSize(new Dimension(37, 37));
            treeButton.addActionListener(this);
            treeButton.setToolTipText("Phylogenetic Tree");
            buttonPanel2.add(treeButton, constraintsButton2);
        }

        constraintsButton2.gridx = 3;
        constraintsButton2.gridy = 0;
        buttonIconSummary = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/iconSummary.png")));
        buttonIconSummary.setActionCommand("iconSummary");
        buttonIconSummary.setPreferredSize(new Dimension(37, 37));
        buttonIconSummary.addActionListener(this);
        buttonIconSummary.setToolTipText("Icon Summary");
        buttonPanel2.add(buttonIconSummary, constraintsButton2);



        constraintsButton2.gridx = 4;
        constraintsButton2.gridy = 0;
        reproduceButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/reproduce.png")));
        reproduceButton.setActionCommand("reproduceList");
        reproduceButton.setPreferredSize(new Dimension(37, 37));
        reproduceButton.addActionListener(this);
        reproduceButton.setToolTipText("Reproduce List");
        buttonPanel2.add(reproduceButton, constraintsButton2);


        constraintsButton2.gridx = 5;
        constraintsButton2.gridy = 0;
        reproduceIconeButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/reproduceIcone.png")));
        reproduceIconeButton.setActionCommand("reproduceIconeList");
        reproduceIconeButton.setPreferredSize(new Dimension(37, 37));
        reproduceIconeButton.addActionListener(this);
        reproduceIconeButton.setToolTipText("Reproduce Icone List");
        buttonPanel2.add(reproduceIconeButton, constraintsButton2);


        constraints.gridx = 0;
        constraints.gridy = 29;
        controlPanel.add(buttonPanel2, constraints);


        constraints.gridx = 0;
        constraints.gridy = 30;
        drawButton = new JButton("Draw");//new ImageIcon(MusicVisualization.class.getResource("/images/execute.png")));
        drawButton.setActionCommand("draw");
        drawButton.setPreferredSize(new Dimension(80, 20));
        drawButton.addActionListener(this);
        drawButton.setToolTipText("Draw");
        controlPanel.add(drawButton, constraints);



        constraints.gridx = 0;
        constraints.gridy = 31;
        textAreaMessage = new JTextArea(1, 12);
        textAreaMessage.setEditable(false);
        controlPanel.add(textAreaMessage, constraints);

        constraints.gridx = 0;
        constraints.gridy = 32;
        sliderBrightness = new JSlider();
        sliderBrightness.setBorder(BorderFactory.createTitledBorder("Brightness (%)"));
        sliderBrightness.setMajorTickSpacing(20);
        sliderBrightness.setMinorTickSpacing(5);
        sliderBrightness.setPaintTicks(true);
        sliderBrightness.setPaintLabels(true);
        sliderBrightness.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent arg0) {
                JSlider d = (JSlider) arg0.getSource();
                if (!d.getValueIsAdjusting()) {
                    ((MusicVisualizationPanel) drawingArea).setWithoutReDoing(true);
                    Integer value = d.getValue();
                    ((MusicVisualizationPanel) drawingArea).setValueBrightness(value);
                    ((MusicVisualizationPanel) drawingArea).setCheckBrightness(true);
                    ((MusicVisualizationPanel) drawingArea).repaint();
                }

            }
        });
        controlPanel.add(sliderBrightness, constraints);

        add("West", controlPanel);

        GridBagConstraints constraintsPlayList = new GridBagConstraints();
        playListPanel.setLayout(new GridBagLayout());
        /*constraintsPlayList.gridx = 0;
         constraintsPlayList.gridy = 0;
         playListPanel.add(new JLabel("Selected songs:"), constraintsPlayList);*/


        if (listNames != null) {
            DefaultTableModel aModel = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            aModel.addColumn("Selected Songs", listNames.toArray());
            tableMidis = new JTable();
            tableMidis.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            tableMidis.setPreferredScrollableViewportSize(new Dimension(280, 120));
            tableMidis.setModel(aModel);
            tableMidis.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        playTableMidis();
                    }
                    clearSelectedTableMidisPlaylist();


                }
            });
            scrollpane = new JScrollPane(tableMidis);
            constraintsPlayList.gridx = 0;
            constraintsPlayList.gridy = 1;
            playListPanel.add(scrollpane, constraintsPlayList);
        }
        GridBagConstraints constraintsPlaylistButtonAdd = new GridBagConstraints();
        buttonSelectedSongsPlaylistPanel = new JPanel();
        buttonSelectedSongsPlaylistPanel.setLayout(new GridBagLayout());

        constraintsPlaylistButtonAdd.gridx = 0;
        constraintsPlaylistButtonAdd.gridy = 0;
        previousSelectedSongsButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/previous.png")));
        previousSelectedSongsButton.setActionCommand("previousSelectedSongs");
        previousSelectedSongsButton.setPreferredSize(new Dimension(20, 20));
        previousSelectedSongsButton.addActionListener(this);
        previousSelectedSongsButton.setToolTipText("previous");

        buttonSelectedSongsPlaylistPanel.add(previousSelectedSongsButton, constraintsPlaylistButtonAdd);

        constraintsPlaylistButtonAdd.gridx = 1;
        constraintsPlaylistButtonAdd.gridy = 0;
        stopSelectedSongsButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/stop.png")));
        stopSelectedSongsButton.setActionCommand("stopMidi");
        stopSelectedSongsButton.setPreferredSize(new Dimension(20, 20));
        stopSelectedSongsButton.addActionListener(this);
        stopSelectedSongsButton.setToolTipText("stop");

        buttonSelectedSongsPlaylistPanel.add(stopSelectedSongsButton, constraintsPlaylistButtonAdd);

        constraintsPlaylistButtonAdd.gridx = 2;
        constraintsPlaylistButtonAdd.gridy = 0;
        playSelectedSongsButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/play.png")));
        playSelectedSongsButton.setActionCommand("playSelectedSongs");
        playSelectedSongsButton.setPreferredSize(new Dimension(20, 20));
        playSelectedSongsButton.addActionListener(this);
        playSelectedSongsButton.setToolTipText("play");
        buttonSelectedSongsPlaylistPanel.add(playSelectedSongsButton, constraintsPlaylistButtonAdd);

        constraintsPlaylistButtonAdd.gridx = 3;
        constraintsPlaylistButtonAdd.gridy = 0;
        nextSelectedSongsButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/next.png")));
        nextSelectedSongsButton.setActionCommand("nextSelectedSongs");
        nextSelectedSongsButton.setPreferredSize(new Dimension(20, 20));
        nextSelectedSongsButton.addActionListener(this);
        nextSelectedSongsButton.setToolTipText("next");
        buttonSelectedSongsPlaylistPanel.add(nextSelectedSongsButton, constraintsPlaylistButtonAdd);


        constraintsPlaylistButtonAdd.gridx = 4;
        constraintsPlaylistButtonAdd.gridy = 0;
        removeSelectedSongsButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/remove.png")));
        removeSelectedSongsButton.setActionCommand("removeSelectedSongs");
        removeSelectedSongsButton.setPreferredSize(new Dimension(20, 20));
        removeSelectedSongsButton.addActionListener(this);
        removeSelectedSongsButton.setToolTipText("remove");
        buttonSelectedSongsPlaylistPanel.add(removeSelectedSongsButton, constraintsPlaylistButtonAdd);


        constraintsPlayList.gridx = 0;
        constraintsPlayList.gridy = 2;
        playListPanel.add(buttonSelectedSongsPlaylistPanel, constraintsPlayList);

        constraintsPlaylistButtonAdd = new GridBagConstraints();
        buttonAddPlaylistPanel = new JPanel();
        buttonAddPlaylistPanel.setLayout(new GridBagLayout());

        constraintsPlaylistButtonAdd.gridx = 0;
        constraintsPlaylistButtonAdd.gridy = 0;
        addAllButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/addAll.png")));
        addAllButton.setActionCommand("addAll");
        addAllButton.setPreferredSize(new Dimension(100, 20));
        addAllButton.addActionListener(this);
        addAllButton.setToolTipText("Add All");
        buttonAddPlaylistPanel.add(addAllButton, constraintsPlaylistButtonAdd);

        constraintsPlaylistButtonAdd.gridx = 1;
        constraintsPlaylistButtonAdd.gridy = 0;
        addSelectionButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/addSelection.png")));
        addSelectionButton.setActionCommand("addSelection");
        addSelectionButton.setPreferredSize(new Dimension(100, 20));
        addSelectionButton.addActionListener(this);
        addSelectionButton.setToolTipText("Add Selection");
        buttonAddPlaylistPanel.add(addSelectionButton, constraintsPlaylistButtonAdd);

        constraintsPlayList.gridx = 0;
        constraintsPlayList.gridy = 3;
        playListPanel.add(buttonAddPlaylistPanel, constraintsPlayList);

        constraintsPlayList.gridx = 0;
        constraintsPlayList.gridy = 4;
        playListPanel.add(new JLabel(new ImageIcon(MusicVisualization.class.getResource("/images/line.png"))), constraintsPlayList);

        constraintsPlayList.gridx = 0;
        constraintsPlayList.gridy = 5;
        messagePlaylistLabel = new JLabel("Select a playlist:");
        playListPanel.add(messagePlaylistLabel, constraintsPlayList);

        constraintsPlayList.gridx = 0;
        constraintsPlayList.gridy = 6;
        strPlayList = new String[listPlayList.size()];
        listPlayList.toArray(strPlayList);

        comboPlayList = new JComboBox();
        DefaultComboBoxModel model = new DefaultComboBoxModel(strPlayList);
        comboPlayList.setModel(model);
        comboPlayList.setPreferredSize(new Dimension(270, 20));
        comboPlayList.setActionCommand("changePlayList");
        comboPlayList.addActionListener(this);
        playListPanel.add(comboPlayList, constraintsPlayList);

        constraintsPlayList.gridx = 0;
        constraintsPlayList.gridy = 7;
        messagePlaylistLabel = new JLabel("or create a new playlist:");
        playListPanel.add(messagePlaylistLabel, constraintsPlayList);

        constraintsPlayList.gridx = 0;
        constraintsPlayList.gridy = 8;
        newPlayListField = new JTextField();
        newPlayListField.setPreferredSize(new Dimension(270, 20));

        playListPanel.add(newPlayListField, constraintsPlayList);


        DefaultTableModel aModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        aModel.addColumn("Playlist Songs", listNames.toArray());

        tableMidisPlaylist = new JTable();
        tableMidisPlaylist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tableMidisPlaylist.setPreferredScrollableViewportSize(new Dimension(280, 120));
        tableMidisPlaylist.setModel(aModel);
        tableMidisPlaylist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    playTableMidisPlaylist();

                }

                clearSelectedTableMidis();

            }
        });

        scrollpanePlaylist = new JScrollPane(tableMidisPlaylist);

        constraintsPlayList.gridx = 0;
        constraintsPlayList.gridy = 9;
        playListPanel.add(scrollpanePlaylist, constraintsPlayList);

        GridBagConstraints constraintsPlaylistButton = new GridBagConstraints();
        buttonPlaylistPanel = new JPanel();
        buttonPlaylistPanel.setLayout(new GridBagLayout());



        constraintsPlaylistButton.gridx = 0;
        constraintsPlaylistButton.gridy = 0;
        savePlaylistButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/save.png")));
        savePlaylistButton.setActionCommand("savePlaylist");
        savePlaylistButton.setPreferredSize(new Dimension(20, 20));
        savePlaylistButton.addActionListener(this);
        savePlaylistButton.setToolTipText("save");

        buttonPlaylistPanel.add(savePlaylistButton, constraintsPlaylistButton);


        constraintsPlaylistButton.gridx = 1;
        constraintsPlaylistButton.gridy = 0;
        previousPlaylistButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/previous.png")));
        previousPlaylistButton.setActionCommand("previousPlaylist");
        previousPlaylistButton.setPreferredSize(new Dimension(20, 20));
        previousPlaylistButton.addActionListener(this);
        previousPlaylistButton.setToolTipText("previous");

        buttonPlaylistPanel.add(previousPlaylistButton, constraintsPlaylistButton);

        constraintsPlaylistButton.gridx = 2;
        constraintsPlaylistButton.gridy = 0;
        stopPlaylistButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/stop.png")));
        stopPlaylistButton.setActionCommand("stopMidi");
        stopPlaylistButton.setPreferredSize(new Dimension(20, 20));
        stopPlaylistButton.addActionListener(this);
        stopPlaylistButton.setToolTipText("stop");

        buttonPlaylistPanel.add(stopPlaylistButton, constraintsPlaylistButton);

        constraintsPlaylistButton.gridx = 3;
        constraintsPlaylistButton.gridy = 0;
        playPlaylistButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/play.png")));
        playPlaylistButton.setActionCommand("playPlaylist");
        playPlaylistButton.setPreferredSize(new Dimension(20, 20));
        playPlaylistButton.addActionListener(this);
        playPlaylistButton.setToolTipText("play");

        buttonPlaylistPanel.add(playPlaylistButton, constraintsPlaylistButton);

        constraintsPlaylistButton.gridx = 4;
        constraintsPlaylistButton.gridy = 0;
        nextPlaylistButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/next.png")));
        nextPlaylistButton.setActionCommand("nextPlaylist");
        nextPlaylistButton.setPreferredSize(new Dimension(20, 20));
        nextPlaylistButton.addActionListener(this);
        nextPlaylistButton.setToolTipText("next");

        buttonPlaylistPanel.add(nextPlaylistButton, constraintsPlaylistButton);

        constraintsPlaylistButton.gridx = 5;
        constraintsPlaylistButton.gridy = 0;
        removePlaylistButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/remove.png")));
        removePlaylistButton.setActionCommand("removePlaylist");
        removePlaylistButton.setPreferredSize(new Dimension(20, 20));
        removePlaylistButton.addActionListener(this);
        removePlaylistButton.setToolTipText("remove");

        buttonPlaylistPanel.add(removePlaylistButton, constraintsPlaylistButton);


        constraintsPlaylistButton.gridx = 6;
        constraintsPlaylistButton.gridy = 0;
        removeFilePlaylistButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/removeFilePlaylist.png")));
        removeFilePlaylistButton.setActionCommand("removeFilePlaylist");
        removeFilePlaylistButton.setPreferredSize(new Dimension(20, 20));
        removeFilePlaylistButton.addActionListener(this);
        removeFilePlaylistButton.setToolTipText("remove Playlist");

        buttonPlaylistPanel.add(removeFilePlaylistButton, constraintsPlaylistButton);



        constraintsPlayList.gridx = 0;
        constraintsPlayList.gridy = 10;
        playListPanel.add(buttonPlaylistPanel, constraintsPlayList);

        constraintsPlayList.gridx = 0;
        constraintsPlayList.gridy = 11;
        lineDivisionLabel = new JLabel(new ImageIcon(MusicVisualization.class.getResource("/images/line.png")));
        playListPanel.add(lineDivisionLabel, constraintsPlayList);

        constraintsPlayList.gridx = 0;
        constraintsPlayList.gridy = 12;
        messagePlaylistLabel = new JLabel("Playing...");
        playListPanel.add(messagePlaylistLabel, constraintsPlayList);

        constraintsPlayList.gridx = 0;
        constraintsPlayList.gridy = 13;
        currentSongTextArea = new JTextArea("");
        currentSongTextArea.setEditable(false);
        playListPanel.add(currentSongTextArea, constraintsPlayList);

        constraintsPlayList.gridx = 0;
        constraintsPlayList.gridy = 14;
        iconeLabel = new JLabel();
        iconeLabel.setSize(new Dimension(100, 20));
        playListPanel.add(iconeLabel, constraintsPlayList);

        add("East", playListPanel);
        this.drawingArea.addMouseListener(this);
        this.drawingArea.addMouseMotionListener(this);

    }

    public void clean() {
        midiPlayer = new MidiPlayer();
        listNames = new ArrayList<>();
        listPaths = new ArrayList<>();
        listLabels = new ArrayList<>();
        listNamesPlayList = new ArrayList<>();
        listPathsPlayList = new ArrayList<>();
        listLabelsPlayList = new ArrayList<>();
        factor = 1.0;
        checkLens = false;
        checkLensSplatting = false;
        checkIconSummary = false;

        //strNameDataset1 = "";
        //strNameDataset2 = "";

        boolListX = false;
        boolListY = false;

        tableMidis = new JTable();
        tableMidisPlaylist = new JTable();



        try {
            remove(drawingArea);
        } catch (Exception e) {
        }
        try {
            remove(controlPanel);
        } catch (Exception e) {
        }
        try {
            remove(playListPanel);
        } catch (Exception e) {
        }
        try {
            remove(drawButton);
        } catch (Exception e) {
        }
        try {
            remove(semanticZoomButton);
        } catch (Exception e) {
        }
        try {
            remove(reproduceButton);
        } catch (Exception e) {
        }
        try {
            remove(reproduceIconeButton);
        } catch (Exception e) {
        }
        try {
            remove(treeButton);
        } catch (Exception e) {
        }
        try {
            remove(buttonIconSummary);
        } catch (Exception e) {
        }
        try {
            remove(buttonZoomOriginal);
        } catch (Exception e) {
        }
        try {
            remove(buttonZoomPrevious);
        } catch (Exception e) {
        }
        try {
            remove(buttonLens);
        } catch (Exception e) {
        }
        try {
            remove(buttonLensSplatting);
        } catch (Exception e) {
        }
        try {
            remove(buttonZoomLocal);
        } catch (Exception e) {
        }
        try {
            remove(buttonLabel);
        } catch (Exception e) {
        }
        try {
            remove(buttonNameSongsLabel);
        } catch (Exception e) {
        }
        try {
            remove(thresholdField);
        } catch (Exception e) {
        }
        try {
            remove(radiusNNtext);
        } catch (Exception e) {
        }
        try {
            remove(splatSigmaField);
        } catch (Exception e) {
        }
        try {
            remove(fieldSizeSplat);
        } catch (Exception e) {
        }
        try {
            remove(columns1);
        } catch (Exception e) {
        }
        try {
            remove(columns2);
        } catch (Exception e) {
        }
        try {
            remove(checkLines);
        } catch (Exception e) {
        }
        try {
            remove(checkMarchingSquares);
        } catch (Exception e) {
        }
        try {
            remove(fileChooser);
        } catch (Exception e) {
        }
        try {
            remove(comboPoints);
        } catch (Exception e) {
        }
        try {
            remove(comboDensity);
        } catch (Exception e) {
        }
        try {
            remove(comboColors);
        } catch (Exception e) {
        }
        try {
            remove(sliderBrightness);
        } catch (Exception e) {
        }
        try {
            remove(comboPlayList);
        } catch (Exception e) {
        }
        try {
            remove(scrollpane);
        } catch (Exception e) {
        }
        try {
            remove(scrollpanePlaylist);
        } catch (Exception e) {
        }
        try {
            remove(tableMidis);
        } catch (Exception e) {
        }
        try {
            remove(tableMidisPlaylist);
        } catch (Exception e) {
        }
        try {
            remove(addAllButton);
        } catch (Exception e) {
        }
        try {
            remove(addSelectionButton);
        } catch (Exception e) {
        }
        try {
            remove(buttonAddPlaylistPanel);
        } catch (Exception e) {
        }
        try {
            remove(newPlayListField);
        } catch (Exception e) {
        }
        try {
            remove(playSelectedSongsButton);
        } catch (Exception e) {
        }
        try {
            remove(stopSelectedSongsButton);
        } catch (Exception e) {
        }
        try {
            remove(nextSelectedSongsButton);
        } catch (Exception e) {
        }
        try {
            remove(previousSelectedSongsButton);
        } catch (Exception e) {
        }
        try {
            remove(removeSelectedSongsButton);
        } catch (Exception e) {
        }
        try {
            remove(buttonSelectedSongsPlaylistPanel);
        } catch (Exception e) {
        }
        try {
            remove(playPlaylistButton);
        } catch (Exception e) {
        }
        try {
            remove(stopPlaylistButton);
        } catch (Exception e) {
        }
        try {
            remove(nextPlaylistButton);
        } catch (Exception e) {
        }
        try {
            remove(previousPlaylistButton);
        } catch (Exception e) {
        }
        try {
            remove(removePlaylistButton);
        } catch (Exception e) {
        }
        try {
            remove(removeFilePlaylistButton);
        } catch (Exception e) {
        }
        try {
            remove(buttonPlaylistPanel);
        } catch (Exception e) {
        }
        try {
            remove(lineDivisionLabel);
        } catch (Exception e) {
        }
        try {
            remove(messagePlaylistLabel);
        } catch (Exception e) {
        }
        try {
            remove(currentSongTextArea);
        } catch (Exception e) {
        }
        try {
            remove(iconeLabel);
        } catch (Exception e) {
        }

        controlPanel = new JPanel();
        playListPanel = new JPanel();
        drawingArea = new JPanel();
        fileChooser = new JFileChooser();

    }

    public void loadDrawPanel() throws IOException {

        clean();
        if (numTitle == 0 && option.equals("projection")) {
            loadLabelMidi(this.pathsLabel);
        }
        int column1 = 0;
        int column2 = 1;
        if (numTitle == 0 && !option.equals("projection")) {
            listPoints = new ArrayList<>();

            minX = 0.0f;
            minY = 0.0f;
            maxX = 0.0f;
            maxY = 0.0f;
            numData = 0;
            factor = 1.0;
            checkLens = false;
            checkLensSplatting = false;
            checkIconSummary = false;

            if (fileName != null) {
                uploadDataSet(fileName, column1, column2);


            }
        }

        if (numTitle == 0 && option.equals("projection")) {
            factor = 1.0;
            checkLens = false;
            checkLensSplatting = false;
            checkIconSummary = false;
        }
        drawingArea = new MusicVisualizationPanel(listPoints, nameX, nameY, minX, maxX, minY, maxY, numData, this.hashSetClusters, this.basicColor, this.listLabelMidi);


        add(drawingArea);//center
        GridBagConstraints constraints = new GridBagConstraints();
        controlPanel = new JPanel();

        controlPanel.setLayout(
                new GridBagLayout());

        GridBagConstraints constraintsButton = new GridBagConstraints();
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        constraintsButton.gridx = 0;
        constraintsButton.gridy = 0;
        buttonZoomOriginal = new JButton(new ImageIcon(getClass().getResource("/images/zoom-original.png")));
        buttonZoomOriginal.setActionCommand("zoomOriginal");
        buttonZoomOriginal.setPreferredSize(new Dimension(30, 20));
        buttonZoomOriginal.addActionListener(this);
        buttonZoomOriginal.setToolTipText("original");
        buttonPanel.add(buttonZoomOriginal, constraintsButton);

        constraintsButton.gridx = 1;
        constraintsButton.gridy = 0;
        buttonLens = new JButton(new ImageIcon(getClass().getResource("/images/system-search.png")));
        buttonLens.setActionCommand("lens");
        buttonLens.setPreferredSize(new Dimension(30, 20));
        buttonLens.addActionListener(this);
        buttonLens.setToolTipText("lens");
        buttonPanel.add(buttonLens, constraintsButton);


        constraintsButton.gridx = 2;
        constraintsButton.gridy = 0;
        buttonLensSplatting = new JButton(new ImageIcon(getClass().getResource("/images/splat.png")));
        buttonLensSplatting.setActionCommand("lensSplatting");
        buttonLensSplatting.setPreferredSize(new Dimension(30, 20));
        buttonLensSplatting.addActionListener(this);
        buttonLensSplatting.setToolTipText("splat");
        buttonPanel.add(buttonLensSplatting, constraintsButton);

        constraintsButton.gridx = 3;
        constraintsButton.gridy = 0;
        buttonZoomLocal = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/mail_new.png")));
        buttonZoomLocal.setActionCommand("zoomLocal");
        buttonZoomLocal.setPreferredSize(new Dimension(30, 20));
        buttonZoomLocal.addActionListener(this);
        buttonZoomLocal.setToolTipText("zoom local");
        buttonPanel.add(buttonZoomLocal, constraintsButton);

        constraintsButton.gridx = 4;
        constraintsButton.gridy = 0;
        buttonLabel = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/numero5.png")));
        buttonLabel.setActionCommand("labelInstances");
        buttonLabel.setPreferredSize(new Dimension(30, 20));
        buttonLabel.addActionListener(this);
        buttonLabel.setToolTipText("labels");
        buttonPanel.add(buttonLabel, constraintsButton);


        constraintsButton.gridx = 5;
        constraintsButton.gridy = 0;
        buttonNameSongsLabel = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/letraA.png")));
        buttonNameSongsLabel.setActionCommand("nameSongsLabelInstances");
        buttonNameSongsLabel.setPreferredSize(new Dimension(30, 20));
        buttonNameSongsLabel.addActionListener(this);
        buttonNameSongsLabel.setToolTipText("names");
        buttonPanel.add(buttonNameSongsLabel, constraintsButton);

        constraints.gridx = 0;
        constraints.gridy = 1;
        controlPanel.add(buttonPanel, constraints);


        constraints.gridx = 0;
        constraints.gridy = 5;

        controlPanel.add(new JLabel("Database"), constraints);

        constraints.gridx = 0;
        constraints.gridy = 6;
        String[] pointData = null;
        JLabel labelFileName;

        if (fileName != null) {
            if (fileName.contains("/")) {
                pointData = fileName.split("/"); // linux
            } else {
                pointData = fileName.split("\\\\");
            }

            String labelFileNameStr = pointData[pointData.length - 1];
            Integer numberCharacters = labelFileNameStr.length() - 5;
            if (numberCharacters > 13) {
                numberCharacters = 13;
            }
            labelFileNameStr = labelFileNameStr.substring(0,
                    numberCharacters);

            labelFileName = new JLabel(labelFileNameStr.toUpperCase());
        } else {
            labelFileName = new JLabel(fileName);

        }

        controlPanel.add(labelFileName, constraints);
        constraints.gridx = 0;
        constraints.gridy = 7;

        controlPanel.add(new JLabel("Data number"), constraints);

        constraints.gridx = 0;
        constraints.gridy = 8;
        JTextField dataNumber = new JTextField(13);

        dataNumber.setText(
                this.numData.toString() + " instances");
        dataNumber.setEditable(
                false);
        controlPanel.add(dataNumber, constraints);
        constraints.gridx = 0;
        constraints.gridy = 9;

        controlPanel.add(new JLabel("Axis X"), constraints);

        constraints.gridx = 0;
        constraints.gridy = 10;


        if (option.equals("projection")) {
            nameX = "x";
            nameY = "y";
        }  /*else {
         if (numTitle != 0) {
         strNameDataset1 = nameX;
         strNameDataset2 = nameY;
         }
         }*/
        if (numTitle == 0 && !option.equals("projection")) {
            if (fileName != null) {
                columns1 = new JComboBox(uploadNamesDataSet(fileName));
            }

            columns1.setPreferredSize(new Dimension(150, 20));
            columns1.setSelectedIndex(column1);
            columns1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolListX = true;
                }
            });
            controlPanel.add(columns1, constraints);
        } else {
            if (nameX != null && !nameX.equals("") && !option.equals("projection")) {
                //controlPanel.add(new JLabel(nameX), constraints);
                if (fileName != null) {
                    columns1 = new JComboBox(uploadNamesDataSet(fileName));
                }

                columns1.setPreferredSize(new Dimension(150, 20));
                columns1.setSelectedItem(nameX);
                columns1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        boolListX = true;
                    }
                });
                controlPanel.add(columns1, constraints);
            } else {
                if (option.equals("projection")) {
                    controlPanel.add(new JLabel(nameX), constraints);
                }
            }
        }


        constraints.gridx = 0;
        constraints.gridy = 11;

        controlPanel.add(new JLabel("Axis Y"), constraints);

        constraints.gridx = 0;
        constraints.gridy = 12;

        if (numTitle == 0 && !option.equals("projection")) {
            if (fileName != null) {
                columns2 = new JComboBox(uploadNamesDataSet(fileName));
            }

            columns2.setPreferredSize(new Dimension(150, 20));
            columns2.setSelectedIndex(column2);
            columns2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolListX = true;
                }
            });
            controlPanel.add(columns2, constraints);
        } else {
            if (nameY != null && !nameY.equals("") && !option.equals("projection")) {
                //controlPanel.add(new JLabel(nameX), constraints);
                if (fileName != null) {
                    columns2 = new JComboBox(uploadNamesDataSet(fileName));
                }

                columns2.setPreferredSize(new Dimension(150, 20));
                columns2.setSelectedItem(nameY);
                columns2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        boolListX = true;
                    }
                });
                controlPanel.add(columns2, constraints);
            } else {
                if (option.equals("projection")) {
                    controlPanel.add(new JLabel(nameY), constraints);
                }
            }
        }

        constraints.gridx = 0;
        constraints.gridy = 13;

        controlPanel.add(new JLabel("Radius NN"), constraints);

        constraints.gridx = 0;
        constraints.gridy = 14;
        radiusNNtext = new JTextField(13);
        Float radiusNNtmp = (float) Math.sqrt(Math.pow((this.maxX - this.minX), 2) + Math.pow((this.maxY - this.minY), 2));
        radiusNNtmp = radiusNNtmp * 0.1f;

        radiusNNtext.setText(radiusNNtmp.toString());
        controlPanel.add(radiusNNtext, constraints);

        constraints.gridx = 0;
        constraints.gridy = 15;

        controlPanel.add(
                new JLabel("Sigma"), constraints);

        constraints.gridx = 0;
        constraints.gridy = 16;
        splatSigmaField = new JTextField(13);

        splatSigmaField.setText(
                "11.0f");
        controlPanel.add(splatSigmaField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 17;

        controlPanel.add(
                new JLabel("Control Resolution"), constraints);

        constraints.gridx = 0;
        constraints.gridy = 18;
        fieldSizeSplat = new JTextField(13);

        fieldSizeSplat.setText(Integer.toString(51));
        controlPanel.add(fieldSizeSplat, constraints);
        constraints.gridx = 0;
        constraints.gridy = 19;

        controlPanel.add(
                new JLabel("Threshold(MSquares)"), constraints);

        constraints.gridx = 0;
        constraints.gridy = 20;
        thresholdField = new JTextField(13);

        thresholdField.setText(
                "0.5");
        controlPanel.add(thresholdField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 21;

        controlPanel.add(new JLabel("Type of point"), constraints);

        constraints.gridx = 0;
        constraints.gridy = 22;
        comboPoints = new JComboBox(strPoints);

        comboPoints.setPreferredSize(
                new Dimension(150, 20));
        comboPoints.setSelectedIndex(1);
        controlPanel.add(comboPoints, constraints);
        constraints.gridx = 0;
        constraints.gridy = 23;

        controlPanel.add(new JLabel("Type of density"), constraints);

        constraints.gridx = 0;
        constraints.gridy = 24;
        comboDensity = new JComboBox(strDensity);

        comboDensity.setPreferredSize(new Dimension(150, 20));
        comboDensity.setSelectedIndex(0);
        controlPanel.add(comboDensity, constraints);
        constraints.gridx = 0;
        constraints.gridy = 25;
        checkMarchingSquares = new JCheckBox("Marching Squares");

        controlPanel.add(checkMarchingSquares, constraints);
        constraints.gridx = 0;
        constraints.gridy = 26;
        checkLines = new JCheckBox("Legends                ");

        controlPanel.add(checkLines, constraints);
        constraints.gridx = 0;
        constraints.gridy = 27;

        controlPanel.add(new JLabel("COLOR"), constraints);

        constraints.gridx = 0;
        constraints.gridy = 28;
        comboColors = new JComboBox(strColors);

        comboColors.setPreferredSize(new Dimension(150, 20));
        comboColors.setSelectedIndex(0);
        controlPanel.add(comboColors, constraints);

        GridBagConstraints constraintsButton2 = new GridBagConstraints();
        buttonPanel2 = new JPanel();
        buttonPanel2.setLayout(new GridBagLayout());

        constraintsButton2.gridx = 1;
        constraintsButton2.gridy = 0;
        semanticZoomButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/semanticZoom.png")));
        semanticZoomButton.setActionCommand("semanticZoom");
        semanticZoomButton.setPreferredSize(new Dimension(37, 37));
        semanticZoomButton.addActionListener(this);
        semanticZoomButton.setToolTipText("Semantic Zoom");
        buttonPanel2.add(semanticZoomButton, constraintsButton2);


        if (!option.equals("projection") || (option.equals("projection") && matrix != null)) {

            constraintsButton2.gridx = 2;
            constraintsButton2.gridy = 0;
            treeButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/tree2.png")));
            treeButton.setActionCommand("phylogeneticTree");
            treeButton.setPreferredSize(new Dimension(37, 37));
            treeButton.addActionListener(this);
            treeButton.setToolTipText("Phylogenetic Tree");
            buttonPanel2.add(treeButton, constraintsButton2);


        }
        constraintsButton2.gridx = 3;
        constraintsButton2.gridy = 0;
        buttonIconSummary = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/iconSummary.png")));
        buttonIconSummary.setActionCommand("iconSummary");
        buttonIconSummary.setPreferredSize(new Dimension(37, 37));
        buttonIconSummary.addActionListener(this);
        buttonIconSummary.setToolTipText("Icon Summary");
        buttonPanel2.add(buttonIconSummary, constraintsButton2);



        constraintsButton2.gridx = 4;
        constraintsButton2.gridy = 0;
        reproduceButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/reproduce.png")));
        reproduceButton.setActionCommand("reproduceList");
        reproduceButton.setPreferredSize(new Dimension(37, 37));
        reproduceButton.addActionListener(this);
        reproduceButton.setToolTipText("Reproduce List");
        buttonPanel2.add(reproduceButton, constraintsButton2);

        constraintsButton2.gridx = 5;
        constraintsButton2.gridy = 0;
        reproduceIconeButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/reproduceIcone.png")));
        reproduceIconeButton.setActionCommand("reproduceIconeList");
        reproduceIconeButton.setPreferredSize(new Dimension(37, 37));
        reproduceIconeButton.addActionListener(this);
        reproduceIconeButton.setToolTipText("Reproduce Icone List");
        buttonPanel2.add(reproduceIconeButton, constraintsButton2);

        constraints.gridx = 0;
        constraints.gridy = 29;
        controlPanel.add(buttonPanel2, constraints);



        constraints.gridx = 0;
        constraints.gridy = 30;
        drawButton = new JButton("Draw");//new ImageIcon(MusicVisualization.class.getResource("/images/execute.png")));
        drawButton.setActionCommand("draw");
        drawButton.setPreferredSize(new Dimension(80, 20));
        drawButton.addActionListener(this);
        drawButton.setToolTipText("Draw");
        controlPanel.add(drawButton, constraints);


        constraints.gridx = 0;
        constraints.gridy = 31;
        textAreaMessage = new JTextArea(1, 12);
        textAreaMessage.setEditable(false);
        controlPanel.add(textAreaMessage, constraints);

        constraints.gridx = 0;
        constraints.gridy = 32;
        sliderBrightness = new JSlider();
        sliderBrightness.setBorder(BorderFactory.createTitledBorder("Brightness (%)"));
        sliderBrightness.setMajorTickSpacing(20);
        sliderBrightness.setMinorTickSpacing(5);
        sliderBrightness.setPaintTicks(true);
        sliderBrightness.setPaintLabels(true);
        sliderBrightness.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent arg0) {
                JSlider d = (JSlider) arg0.getSource();
                if (!d.getValueIsAdjusting()) {
                    ((MusicVisualizationPanel) drawingArea).setWithoutReDoing(true);
                    Integer value = d.getValue();
                    ((MusicVisualizationPanel) drawingArea).setValueBrightness(value);
                    ((MusicVisualizationPanel) drawingArea).setCheckBrightness(true);
                    ((MusicVisualizationPanel) drawingArea).repaint();
                }

            }
        });
        controlPanel.add(sliderBrightness, constraints);

        add("West", controlPanel);

        GridBagConstraints constraintsPlayList = new GridBagConstraints();
        playListPanel.setLayout(new GridBagLayout());
        /* constraintsPlayList.gridx = 0;
         constraintsPlayList.gridy = 0;
         playListPanel.add(new JLabel("Selected Songs:"), constraintsPlayList);*/


        DefaultTableModel aModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        aModel.addColumn("Selected Songs", listNames.toArray());


        tableMidis = new JTable();
        tableMidis.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tableMidis.setPreferredScrollableViewportSize(new Dimension(280, 120));
        tableMidis.setModel(aModel);
        tableMidis.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    playTableMidis();
                }
                clearSelectedTableMidisPlaylist();

            }
        });

        scrollpane = new JScrollPane(tableMidis);
        constraintsPlayList.gridx = 0;
        constraintsPlayList.gridy = 1;

        playListPanel.add(scrollpane, constraintsPlayList);
        GridBagConstraints constraintsPlaylistButtonAdd = new GridBagConstraints();
        buttonSelectedSongsPlaylistPanel = new JPanel();

        buttonSelectedSongsPlaylistPanel.setLayout(
                new GridBagLayout());

        constraintsPlaylistButtonAdd.gridx = 0;
        constraintsPlaylistButtonAdd.gridy = 0;
        previousSelectedSongsButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/previous.png")));

        previousSelectedSongsButton.setActionCommand("previousSelectedSongs");
        previousSelectedSongsButton.setPreferredSize(new Dimension(20, 20));
        previousSelectedSongsButton.addActionListener(this);
        previousSelectedSongsButton.setToolTipText("previous");

        buttonSelectedSongsPlaylistPanel.add(previousSelectedSongsButton, constraintsPlaylistButtonAdd);
        constraintsPlaylistButtonAdd.gridx = 1;
        constraintsPlaylistButtonAdd.gridy = 0;
        stopSelectedSongsButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/stop.png")));

        stopSelectedSongsButton.setActionCommand("stopMidi");
        stopSelectedSongsButton.setPreferredSize(new Dimension(20, 20));
        stopSelectedSongsButton.addActionListener(this);
        stopSelectedSongsButton.setToolTipText("stop");

        buttonSelectedSongsPlaylistPanel.add(stopSelectedSongsButton, constraintsPlaylistButtonAdd);
        constraintsPlaylistButtonAdd.gridx = 2;
        constraintsPlaylistButtonAdd.gridy = 0;
        playSelectedSongsButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/play.png")));

        playSelectedSongsButton.setActionCommand("playSelectedSongs");
        playSelectedSongsButton.setPreferredSize(new Dimension(20, 20));
        playSelectedSongsButton.addActionListener(this);
        playSelectedSongsButton.setToolTipText("play");

        buttonSelectedSongsPlaylistPanel.add(playSelectedSongsButton, constraintsPlaylistButtonAdd);

        constraintsPlaylistButtonAdd.gridx = 3;
        constraintsPlaylistButtonAdd.gridy = 0;
        nextSelectedSongsButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/next.png")));

        nextSelectedSongsButton.setActionCommand("nextSelectedSongs");
        nextSelectedSongsButton.setPreferredSize(new Dimension(20, 20));
        nextSelectedSongsButton.addActionListener(this);
        nextSelectedSongsButton.setToolTipText("next");

        buttonSelectedSongsPlaylistPanel.add(nextSelectedSongsButton, constraintsPlaylistButtonAdd);


        constraintsPlaylistButtonAdd.gridx = 4;
        constraintsPlaylistButtonAdd.gridy = 0;
        removeSelectedSongsButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/remove.png")));
        removeSelectedSongsButton.setActionCommand("removeSelectedSongs");
        removeSelectedSongsButton.setPreferredSize(new Dimension(20, 20));
        removeSelectedSongsButton.addActionListener(this);
        removeSelectedSongsButton.setToolTipText("remove");
        buttonSelectedSongsPlaylistPanel.add(removeSelectedSongsButton, constraintsPlaylistButtonAdd);

        constraintsPlayList.gridx = 0;
        constraintsPlayList.gridy = 2;

        playListPanel.add(buttonSelectedSongsPlaylistPanel, constraintsPlayList);
        constraintsPlaylistButtonAdd = new GridBagConstraints();
        buttonAddPlaylistPanel = new JPanel();

        buttonAddPlaylistPanel.setLayout(
                new GridBagLayout());

        constraintsPlaylistButtonAdd.gridx = 0;
        constraintsPlaylistButtonAdd.gridy = 0;
        addAllButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/addAll.png")));

        addAllButton.setActionCommand(
                "addAll");
        addAllButton.setPreferredSize(
                new Dimension(100, 20));
        addAllButton.addActionListener(
                this);
        addAllButton.setToolTipText(
                "Add All");
        buttonAddPlaylistPanel.add(addAllButton, constraintsPlaylistButtonAdd);
        constraintsPlaylistButtonAdd.gridx = 1;
        constraintsPlaylistButtonAdd.gridy = 0;
        addSelectionButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/addSelection.png")));

        addSelectionButton.setActionCommand(
                "addSelection");
        addSelectionButton.setPreferredSize(
                new Dimension(100, 20));
        addSelectionButton.addActionListener(
                this);
        addSelectionButton.setToolTipText(
                "Add Selection");
        buttonAddPlaylistPanel.add(addSelectionButton, constraintsPlaylistButtonAdd);
        constraintsPlayList.gridx = 0;
        constraintsPlayList.gridy = 3;

        playListPanel.add(buttonAddPlaylistPanel, constraintsPlayList);
        constraintsPlayList.gridx = 0;
        constraintsPlayList.gridy = 4;

        playListPanel.add(
                new JLabel(new ImageIcon(MusicVisualization.class
                .getResource("/images/line.png"))), constraintsPlayList);

        constraintsPlayList.gridx = 0;
        constraintsPlayList.gridy = 5;
        messagePlaylistLabel = new JLabel("Select a playlist:");

        playListPanel.add(messagePlaylistLabel, constraintsPlayList);
        constraintsPlayList.gridx = 0;
        constraintsPlayList.gridy = 6;
        if (listPlayList != null && listPlayList.size() > 0) {
            strPlayList = new String[listPlayList.size()];
            listPlayList.toArray(strPlayList);
        } else {
            strPlayList = new String[0];
            //listPlayList.toArray(strPlayList);
        }
        comboPlayList = new JComboBox();
        DefaultComboBoxModel model = new DefaultComboBoxModel(strPlayList);

        comboPlayList.setModel(model);

        comboPlayList.setPreferredSize(
                new Dimension(270, 20));
        comboPlayList.setActionCommand(
                "changePlayList");
        comboPlayList.addActionListener(
                this);
        //aurea
        playListPanel.add(comboPlayList, constraintsPlayList);
        constraintsPlayList.gridx = 0;
        constraintsPlayList.gridy = 7;
        messagePlaylistLabel = new JLabel("or create a new playlist:");

        playListPanel.add(messagePlaylistLabel, constraintsPlayList);
        constraintsPlayList.gridx = 0;
        constraintsPlayList.gridy = 8;
        newPlayListField = new JTextField();

        newPlayListField.setPreferredSize(
                new Dimension(270, 20));

        playListPanel.add(newPlayListField, constraintsPlayList);
        if (listNames
                != null) {
            DefaultTableModel aModel1 = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            aModel1.addColumn("Playlist Songs", listNames.toArray());

            tableMidisPlaylist = new JTable();
            tableMidisPlaylist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            tableMidisPlaylist.setPreferredScrollableViewportSize(new Dimension(280, 120));
            tableMidisPlaylist.setModel(aModel1);
            scrollpanePlaylist = new JScrollPane(tableMidisPlaylist);
            tableMidisPlaylist.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        playTableMidisPlaylist();
                    }
                    clearSelectedTableMidis();


                }
            });

            constraintsPlayList.gridx = 0;
            constraintsPlayList.gridy = 9;
            playListPanel.add(scrollpanePlaylist, constraintsPlayList);
        }
        GridBagConstraints constraintsPlaylistButton = new GridBagConstraints();
        buttonPlaylistPanel = new JPanel();

        buttonPlaylistPanel.setLayout(
                new GridBagLayout());



        constraintsPlaylistButton.gridx = 0;
        constraintsPlaylistButton.gridy = 0;
        savePlaylistButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/save.png")));

        savePlaylistButton.setActionCommand(
                "savePlaylist");
        savePlaylistButton.setPreferredSize(
                new Dimension(20, 20));
        savePlaylistButton.addActionListener(
                this);
        savePlaylistButton.setToolTipText(
                "save");

        buttonPlaylistPanel.add(savePlaylistButton, constraintsPlaylistButton);
        constraintsPlaylistButton.gridx = 1;
        constraintsPlaylistButton.gridy = 0;
        previousPlaylistButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/previous.png")));

        previousPlaylistButton.setActionCommand("previousPlaylist");
        previousPlaylistButton.setPreferredSize(new Dimension(20, 20));
        previousPlaylistButton.addActionListener(this);
        previousPlaylistButton.setToolTipText("previous");

        buttonPlaylistPanel.add(previousPlaylistButton, constraintsPlaylistButton);
        constraintsPlaylistButton.gridx = 2;
        constraintsPlaylistButton.gridy = 0;
        stopPlaylistButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/stop.png")));

        stopPlaylistButton.setActionCommand("stopMidi");
        stopPlaylistButton.setPreferredSize(new Dimension(20, 20));
        stopPlaylistButton.addActionListener(this);
        stopPlaylistButton.setToolTipText("stop");

        buttonPlaylistPanel.add(stopPlaylistButton, constraintsPlaylistButton);
        constraintsPlaylistButton.gridx = 3;
        constraintsPlaylistButton.gridy = 0;
        playPlaylistButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/play.png")));

        playPlaylistButton.setActionCommand("playPlaylist");
        playPlaylistButton.setPreferredSize(new Dimension(20, 20));
        playPlaylistButton.addActionListener(this);
        playPlaylistButton.setToolTipText("play");

        buttonPlaylistPanel.add(playPlaylistButton, constraintsPlaylistButton);
        constraintsPlaylistButton.gridx = 4;
        constraintsPlaylistButton.gridy = 0;
        nextPlaylistButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/next.png")));

        nextPlaylistButton.setActionCommand("nextPlaylist");
        nextPlaylistButton.setPreferredSize(new Dimension(20, 20));
        nextPlaylistButton.addActionListener(this);
        nextPlaylistButton.setToolTipText("next");

        buttonPlaylistPanel.add(nextPlaylistButton, constraintsPlaylistButton);


        constraintsPlaylistButton.gridx = 5;
        constraintsPlaylistButton.gridy = 0;
        removePlaylistButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/remove.png")));
        removePlaylistButton.setActionCommand("removePlaylist");
        removePlaylistButton.setPreferredSize(new Dimension(20, 20));
        removePlaylistButton.addActionListener(this);
        removePlaylistButton.setToolTipText("remove");

        buttonPlaylistPanel.add(removePlaylistButton, constraintsPlaylistButton);


        constraintsPlaylistButton.gridx = 6;
        constraintsPlaylistButton.gridy = 0;
        removeFilePlaylistButton = new JButton(new ImageIcon(MusicVisualization.class.getResource("/images/removeFilePlaylist.png")));
        removeFilePlaylistButton.setActionCommand("removeFilePlaylist");
        removeFilePlaylistButton.setPreferredSize(new Dimension(20, 20));
        removeFilePlaylistButton.addActionListener(this);
        removeFilePlaylistButton.setToolTipText("remove Playlist");

        buttonPlaylistPanel.add(removeFilePlaylistButton, constraintsPlaylistButton);

        constraintsPlayList.gridx = 0;
        constraintsPlayList.gridy = 10;

        playListPanel.add(buttonPlaylistPanel, constraintsPlayList);
        constraintsPlayList.gridx = 0;
        constraintsPlayList.gridy = 11;
        lineDivisionLabel = new JLabel(new ImageIcon(MusicVisualization.class.getResource("/images/line.png")));

        playListPanel.add(lineDivisionLabel, constraintsPlayList);
        constraintsPlayList.gridx = 0;
        constraintsPlayList.gridy = 12;
        messagePlaylistLabel = new JLabel("Playing...");

        playListPanel.add(messagePlaylistLabel, constraintsPlayList);
        constraintsPlayList.gridx = 0;
        constraintsPlayList.gridy = 13;
        currentSongTextArea = new JTextArea("");
        currentSongTextArea.setEditable(false);
        playListPanel.add(currentSongTextArea, constraintsPlayList);
        constraintsPlayList.gridx = 0;
        constraintsPlayList.gridy = 14;
        iconeLabel = new JLabel();

        iconeLabel.setSize(new Dimension(100, 20));
        playListPanel.add(iconeLabel, constraintsPlayList);

        add("East", playListPanel);

        this.drawingArea.addMouseListener(this);
        this.drawingArea.addMouseMotionListener(this);


    }

    public void sePathsIcones(String pathsIcones) {
        this.pathsIcones = pathsIcones;
    }

    public void sePathsLabels(String pathsLabels) {
        this.pathsLabel = pathsLabels;
    }

    public void setMatrix(AbstractMatrix matrix) {
        this.matrix = matrix;
    }

    public void setDistanceMatrix(DistanceMatrix distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public void setProjection(AbstractMatrix projection) {

        fileName = "projection";
        listPoints = new ArrayList<>();
        hashSetClusters = new HashSet<>();

        numData = 0;
        int nrows = projection.getRowCount();

        for (int i = 0; i < nrows; i++) {
            AbstractVector row = projection.getRow(i);
            float x = row.getValue(0);
            float y = row.getValue(1);
            float cluster = 0.f;
            try {
                cluster = row.getKlass();
            } catch (Exception e) {
                cluster = 0.f;
            }
            hashSetClusters.add(cluster);
            listPoints.add(new InstancePoint(x, y, projection.getLabel(i), cluster));
            numData++;
        }


        minX = Collections.min(listPoints, new InstancePointXComparator()).getX();
        maxX = Collections.max(listPoints, new InstancePointXComparator()).getX();
        minY = Collections.min(listPoints, new InstancePointYComparator()).getY();
        maxY = Collections.max(listPoints, new InstancePointYComparator()).getY();
        if (minX.equals(maxX)) {
            minX = minX - 30;
            maxX = maxX + 30;
        }
        if (minY.equals(maxY)) {
            minY = minY - 30;
            maxY = maxY + 30;
        }

    }

    private String[] uploadNamesDataSet(String fileName) {
        String data[] = null;
        try {
            InputStream fstream = new FileInputStream(fileName);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            strLine = br.readLine(); // 1st line ignored
            strLine = br.readLine(); // 2nd line ignored
            strLine = br.readLine(); // 3rd line ignored
            strLine = br.readLine();
            data = strLine.split(";");

        } catch (Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        return data;
    }

    private void uploadDataSet(String fileName, int column1, int column2) throws IOException {

        this.matrix = MatrixFactory.getInstance(fileName);

        listPoints = new ArrayList<>();
        hashSetClusters = new HashSet<>();
        //this.nameX = this.matrix.getAttributes().get(column1);
        //this.nameY = this.matrix.getAttributes().get(column2);
        numData = 0;
        int nrows = this.matrix.getRowCount();

        for (int i = 0; i < nrows; i++) {
            AbstractVector row = this.matrix.getRow(i);
            float x = row.getValue(column1);
            float y = row.getValue(column2);
            float cluster = 0.f;
            try {
                cluster = row.getKlass();
            } catch (Exception e) {
                cluster = 0.f;
            }
            hashSetClusters.add(cluster);
            listPoints.add(new InstancePoint(x, y, String.valueOf(this.matrix.getRow(i).getId()), cluster));
            numData++;
        }


        minX = Collections.min(listPoints, new InstancePointXComparator()).getX();
        maxX = Collections.max(listPoints, new InstancePointXComparator()).getX();
        minY = Collections.min(listPoints, new InstancePointYComparator()).getY();
        maxY = Collections.max(listPoints, new InstancePointYComparator()).getY();
        if (minX.equals(maxX)) {
            minX = minX - 30;
            maxX = maxX + 30;
        }
        if (minY.equals(maxY)) {
            minY = minY - 30;
            maxY = maxY + 30;
        }
    }

    private void uploadDataSetFromMatrix(List<InstancePoint> listPointsXY, int column1, int column2) throws IOException {

        listPoints = new ArrayList<>();
        hashSetClusters = new HashSet<>();
        numData = 0;
        int nrows = listPointsXY.size();

        for (int i = 0; i < nrows; i++) {

            Integer index = this.matrix.getIds().indexOf(Integer.valueOf(listPointsXY.get(i).getLabel()));
            AbstractVector row = this.matrix.getRow(index);
            float x = row.getValue(column1);
            float y = row.getValue(column2);
            float cluster = 0.f;
            try {
                cluster = row.getKlass();
            } catch (Exception e) {
                cluster = 0.f;
            }
            hashSetClusters.add(cluster);
            listPoints.add(new InstancePoint(x, y, String.valueOf(this.matrix.getRow(index).getId()), cluster));
            numData++;
        }


        minX = Collections.min(listPoints, new InstancePointXComparator()).getX();
        maxX = Collections.max(listPoints, new InstancePointXComparator()).getX();
        minY = Collections.min(listPoints, new InstancePointYComparator()).getY();
        maxY = Collections.max(listPoints, new InstancePointYComparator()).getY();
        if (minX.equals(maxX)) {
            minX = minX - 30;
            maxX = maxX + 30;
        }
        if (minY.equals(maxY)) {
            minY = minY - 30;
            maxY = maxY + 30;
        }
    }

    public static double roundTo(double num, int value) {

        int value2 = (int) (Math.pow(10, value));
        double result = num * value2;
        result = Math.round(result);
        result = result / value2;
        return result;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setListPoints(List<InstancePoint> listPoints) {
        this.listPoints = listPoints;
    }

    public void setHashSetClusters(HashSet<Float> hashSetClusters) {
        this.hashSetClusters = hashSetClusters;
    }

    public void setMaxX(Float maxX) {
        this.maxX = maxX;
    }

    public void setMaxY(Float maxY) {
        this.maxY = maxY;
    }

    public void setMinX(Float minX) {
        this.minX = minX;
    }

    public void setMinY(Float minY) {
        this.minY = minY;
    }

    public void setNameX(String nameX) {
        this.nameX = nameX;
    }

    public void setNameY(String nameY) {
        this.nameY = nameY;
    }

    /* public void setStrNameDataset1(String strNameDataset1) {
     this.strNameDataset1 = strNameDataset1;
     }

     public void setStrNameDataset2(String strNameDataset2) {
     this.strNameDataset2 = strNameDataset2;
     }*/
    public void setNumData(Integer numData) {
        this.numData = numData;
    }

    public void setImageOriginal(BufferedImage imageOriginal) {
        this.imageOriginal = imageOriginal;
    }

    public void setPathsLabel(String strPathLabel) {
        this.pathsLabel = strPathLabel;
    }

    public void setPathsIcones(String strPathIcones) {
        this.pathsIcones = strPathIcones;
    }

    public void setPathsPlaylists(String strPathPlaylists) {
        this.pathsPlaylists = strPathPlaylists;
        this.listPlayList = getListPlaylist();
    }

    public void setListLabelMidi(List<LabelMidi> lstLabelMidi) {
        this.listLabelMidi = lstLabelMidi;
    }

    public List<InstancePoint> getListsWithParameters(List<InstancePoint> listPoints, Float minX, Float minY, Float maxX, Float maxY, List<PointDragged> pointsRectangle) {

        List<InstancePoint> listPointsXY = new ArrayList<>();
        Float x, y;
        for (int i = 0; i < listPoints.size(); i++) {
            x = listPoints.get(i).getX();
            y = listPoints.get(i).getY();
            if ((pointsRectangle.get(0).getX() <= x) && (x <= pointsRectangle.get(1).getX()) && (pointsRectangle.get(0).getY() <= y) && (y <= pointsRectangle.get(1).getY())) {
                listPointsXY.add(listPoints.get(i));
            }
        }
        return listPointsXY;
    }

    public Integer getNumberOfPointsInRectangle(List<InstancePoint> listPoints, List<PointDragged> pointsRectangle) {

        boolean statusRectangle = ((MusicVisualizationPanel) drawingArea).getStatusRectangle();
        if (statusRectangle) {

            Integer count = 0;
            Float x, y;
            for (int i = 0; i < listPoints.size(); i++) {
                x = listPoints.get(i).getX();
                y = listPoints.get(i).getY();
                if ((pointsRectangle.get(0).getX() <= x) && (x <= pointsRectangle.get(1).getX())) {
                    if ((pointsRectangle.get(0).getY() <= y) && (y <= pointsRectangle.get(1).getY())) {
                        count++;
                    }

                }
            }


            return count;
        } else {
            return 0;
        }
    }

    public static void main(String args[]) throws IOException {
        MusicVisualization frame = new MusicVisualization("Visualization ", 0, "dataset", null);
        frame.loadInitial();
        frame.setSize(1300, 650);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if ((e.getActionCommand()).equals("draw")) {
            drawDrawingPanel();
        }
        if ((e.getActionCommand()).equals("open")) {
            int returnVal = fileChooser.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    this.fileName = null;

                    File fileNameChooser = fileChooser.getSelectedFile();
                    this.fileName = fileNameChooser.toString();
                    loadDrawPanel();
                    setVisible(true);

                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "This file is not a correct database.");
                    loadDrawPanelError();
                    setVisible(true);
                }

            }

        }
        if ((e.getActionCommand()).equals("zoomOriginal")) {

            message = "";
            textAreaMessage.setText(message);
            this.buttonZoomLocal.setBackground(new Color(238, 238, 238));
            ((MusicVisualizationPanel) drawingArea).setCheckZoomLocal(false);
            checkZoomLocal = false;

            this.buttonLens.setBackground(new Color(238, 238, 238));
            ((MusicVisualizationPanel) drawingArea).setCheckLens(false);
            checkLens = false;

            this.buttonLensSplatting.setBackground(new Color(238, 238, 238));
            ((MusicVisualizationPanel) drawingArea).setCheckLensSplatting(false);
            checkLensSplatting = false;

            this.buttonLabel.setBackground(new Color(238, 238, 238));
            ((MusicVisualizationPanel) drawingArea).setCheckLabel(false);
            checkLabel = false;

            this.buttonNameSongsLabel.setBackground(new Color(238, 238, 238));
            ((MusicVisualizationPanel) drawingArea).setCheckNameSongsLabel(false);
            checkNameSongsLabel = false;

            this.buttonIconSummary.setBackground(new Color(238, 238, 238));
            ((MusicVisualizationPanel) drawingArea).setCheckIconSummary(false);
            checkIconSummary = false;

            ((MusicVisualizationPanel) drawingArea).setZoomOriginal(true);
            ((MusicVisualizationPanel) drawingArea).setWithoutReDoing(true);
            this.sliderBrightness.setValue(50);
            ((MusicVisualizationPanel) drawingArea).setValueBrightness(50);
            ((MusicVisualizationPanel) drawingArea).setCheckBrightness(false);
            drawingArea.repaint();
        }


        if ((e.getActionCommand()).equals("lens")) {
            message = "";
            textAreaMessage.setText(message);

            if (checkLens == true) {
                checkLens = false;
                this.buttonLens.setBackground(new Color(238, 238, 238));
            } else {
                this.buttonLensSplatting.setBackground(new Color(238, 238, 238));
                ((MusicVisualizationPanel) drawingArea).setCheckLensSplatting(false);
                checkLensSplatting = false;

                this.buttonZoomLocal.setBackground(new Color(238, 238, 238));
                ((MusicVisualizationPanel) drawingArea).setCheckZoomLocal(false);
                checkZoomLocal = false;


                this.buttonLabel.setBackground(new Color(238, 238, 238));
                ((MusicVisualizationPanel) drawingArea).setCheckLabel(false);
                checkLabel = false;

                this.buttonNameSongsLabel.setBackground(new Color(238, 238, 238));
                ((MusicVisualizationPanel) drawingArea).setCheckNameSongsLabel(false);
                checkNameSongsLabel = false;

                this.buttonIconSummary.setBackground(new Color(238, 238, 238));
                ((MusicVisualizationPanel) drawingArea).setCheckIconSummary(false);
                checkIconSummary = false;

                checkLens = true;
                this.buttonLens.setBackground(Color.red);


            }

            ((MusicVisualizationPanel) drawingArea).setCheckLens(checkLens);
            ((MusicVisualizationPanel) drawingArea).setCheckLensSplatting(checkLensSplatting);
            ((MusicVisualizationPanel) drawingArea).setWithoutReDoing(true);
            ((MusicVisualizationPanel) drawingArea).setCheckBrightness(false);
            drawingArea.repaint();
        }

        if ((e.getActionCommand()).equals("lensSplatting")) {
            message = "";
            textAreaMessage.setText(message);

            if (checkLensSplatting == true) {
                checkLensSplatting = false;
                this.buttonLensSplatting.setBackground(new Color(238, 238, 238));
            } else {
                if (this.comboDensity.getSelectedIndex() != 0) {
                    this.buttonLens.setBackground(new Color(238, 238, 238));
                    ((MusicVisualizationPanel) drawingArea).setCheckLens(false);
                    checkLens = false;

                    this.buttonZoomLocal.setBackground(new Color(238, 238, 238));
                    ((MusicVisualizationPanel) drawingArea).setCheckZoomLocal(false);
                    checkZoomLocal = false;

                    this.buttonLabel.setBackground(new Color(238, 238, 238));
                    ((MusicVisualizationPanel) drawingArea).setCheckLabel(false);
                    checkLabel = false;

                    this.buttonNameSongsLabel.setBackground(new Color(238, 238, 238));
                    ((MusicVisualizationPanel) drawingArea).setCheckNameSongsLabel(false);
                    checkNameSongsLabel = false;

                    this.buttonIconSummary.setBackground(new Color(238, 238, 238));
                    ((MusicVisualizationPanel) drawingArea).setCheckIconSummary(false);
                    checkIconSummary = false;

                    checkLensSplatting = true;
                    this.buttonLensSplatting.setBackground(Color.red);

                    ((MusicVisualizationPanel) drawingArea).setTypeDensity(comboDensity.getSelectedItem());
                    ((MusicVisualizationPanel) drawingArea).setColorScale(comboColors.getSelectedItem());

                } else {
                    JOptionPane.showMessageDialog(null, "Parameter of density isn't defined.");
                }

            }

            ((MusicVisualizationPanel) drawingArea).setCheckLens(checkLens);
            ((MusicVisualizationPanel) drawingArea).setCheckLensSplatting(checkLensSplatting);
            ((MusicVisualizationPanel) drawingArea).setWithoutReDoing(true);
            ((MusicVisualizationPanel) drawingArea).setCheckBrightness(false);
            drawingArea.repaint();
        }

        if ((e.getActionCommand()).equals("zoomLocal")) {


            if (checkZoomLocal == true) {
                checkZoomLocal = false;
                this.buttonZoomLocal.setBackground(new Color(238, 238, 238));
                ((MusicVisualizationPanel) drawingArea).setWithoutReDoing(true);
                ((MusicVisualizationPanel) drawingArea).clearRectangle();

                message = "";
                textAreaMessage.setText(message);

            } else {
                this.buttonLens.setBackground(new Color(238, 238, 238));
                ((MusicVisualizationPanel) drawingArea).setCheckLens(false);
                checkLens = false;

                this.buttonLensSplatting.setBackground(new Color(238, 238, 238));
                ((MusicVisualizationPanel) drawingArea).setCheckLensSplatting(false);
                checkLensSplatting = false;

                this.buttonLabel.setBackground(new Color(238, 238, 238));
                ((MusicVisualizationPanel) drawingArea).setCheckLabel(false);
                checkLabel = false;

                this.buttonNameSongsLabel.setBackground(new Color(238, 238, 238));
                ((MusicVisualizationPanel) drawingArea).setCheckNameSongsLabel(false);
                checkNameSongsLabel = false;

                this.buttonIconSummary.setBackground(new Color(238, 238, 238));
                ((MusicVisualizationPanel) drawingArea).setCheckIconSummary(false);
                checkIconSummary = false;

                checkZoomLocal = true;
                this.buttonZoomLocal.setBackground(Color.red);
                //((DensityPanel) drawingArea).getCountMap();
            }
            ((MusicVisualizationPanel) drawingArea).setWithoutReDoing(true);
            ((MusicVisualizationPanel) drawingArea).setCheckZoomLocal(checkZoomLocal);
            ((MusicVisualizationPanel) drawingArea).setCheckBrightness(false);
            drawingArea.repaint();
        }

        if ((e.getActionCommand()).equals("labelInstances")) {

            message = "";
            textAreaMessage.setText(message);

            if (checkLabel == true) {
                checkLabel = false;
                this.buttonLabel.setBackground(new Color(238, 238, 238));

            } else {

                this.buttonLens.setBackground(new Color(238, 238, 238));
                ((MusicVisualizationPanel) drawingArea).setCheckLens(false);
                checkLens = false;

                this.buttonLensSplatting.setBackground(new Color(238, 238, 238));
                ((MusicVisualizationPanel) drawingArea).setCheckLensSplatting(false);
                checkLensSplatting = false;

                this.buttonZoomLocal.setBackground(new Color(238, 238, 238));
                ((MusicVisualizationPanel) drawingArea).setCheckZoomLocal(false);
                checkZoomLocal = false;

                this.buttonNameSongsLabel.setBackground(new Color(238, 238, 238));
                ((MusicVisualizationPanel) drawingArea).setCheckNameSongsLabel(false);
                checkNameSongsLabel = false;

                this.buttonIconSummary.setBackground(new Color(238, 238, 238));
                ((MusicVisualizationPanel) drawingArea).setCheckIconSummary(false);
                checkIconSummary = false;

                checkLabel = true;
                this.buttonLabel.setBackground(Color.red);
                //((DensityPanel) drawingArea).getLabelMap();
            }
            ((MusicVisualizationPanel) drawingArea).setWithoutReDoing(true);
            ((MusicVisualizationPanel) drawingArea).setCheckLabel(checkLabel);
            ((MusicVisualizationPanel) drawingArea).setCheckBrightness(false);
            drawingArea.repaint();
        }

        if ((e.getActionCommand()).equals("nameSongsLabelInstances")) {

            message = "";
            textAreaMessage.setText(message);

            if (checkNameSongsLabel == true) {
                checkNameSongsLabel = false;
                this.buttonNameSongsLabel.setBackground(new Color(238, 238, 238));

            } else {

                if (this.listLabelMidi == null || this.listLabelMidi.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Files with names of songs not defined.");
                    this.buttonLens.setBackground(new Color(238, 238, 238));
                    ((MusicVisualizationPanel) drawingArea).setCheckLens(false);
                    checkLens = false;

                    this.buttonLensSplatting.setBackground(new Color(238, 238, 238));
                    ((MusicVisualizationPanel) drawingArea).setCheckLensSplatting(false);
                    checkLensSplatting = false;

                    this.buttonZoomLocal.setBackground(new Color(238, 238, 238));
                    ((MusicVisualizationPanel) drawingArea).setCheckZoomLocal(false);
                    checkZoomLocal = false;

                    this.buttonLabel.setBackground(new Color(238, 238, 238));
                    ((MusicVisualizationPanel) drawingArea).setCheckLabel(false);
                    checkLabel = false;

                    this.buttonNameSongsLabel.setBackground(new Color(238, 238, 238));
                    ((MusicVisualizationPanel) drawingArea).setCheckNameSongsLabel(false);
                    checkNameSongsLabel = false;

                    this.buttonIconSummary.setBackground(new Color(238, 238, 238));
                    ((MusicVisualizationPanel) drawingArea).setCheckIconSummary(false);
                    checkIconSummary = false;

                } else {

                    this.buttonLens.setBackground(new Color(238, 238, 238));
                    ((MusicVisualizationPanel) drawingArea).setCheckLens(false);
                    checkLens = false;

                    this.buttonLensSplatting.setBackground(new Color(238, 238, 238));
                    ((MusicVisualizationPanel) drawingArea).setCheckLensSplatting(false);
                    checkLensSplatting = false;

                    this.buttonZoomLocal.setBackground(new Color(238, 238, 238));
                    ((MusicVisualizationPanel) drawingArea).setCheckZoomLocal(false);
                    checkZoomLocal = false;

                    this.buttonLabel.setBackground(new Color(238, 238, 238));
                    ((MusicVisualizationPanel) drawingArea).setCheckLabel(false);
                    checkLabel = false;


                    this.buttonIconSummary.setBackground(new Color(238, 238, 238));
                    ((MusicVisualizationPanel) drawingArea).setCheckIconSummary(false);
                    checkIconSummary = false;

                    this.buttonNameSongsLabel.setBackground(Color.red);
                    checkNameSongsLabel = true;
                }
                //((DensityPanel) drawingArea).getLabelMap();
            }
            ((MusicVisualizationPanel) drawingArea).setWithoutReDoing(true);
            ((MusicVisualizationPanel) drawingArea).setCheckNameSongsLabel(checkNameSongsLabel);
            ((MusicVisualizationPanel) drawingArea).setCheckBrightness(false);
            drawingArea.repaint();
        }


        if ((e.getActionCommand()).equals("semanticZoom")) {
            if (this.checkZoomLocal) {
                boolean statusRectangle = ((MusicVisualizationPanel) drawingArea).getStatusRectangle();
                if (statusRectangle) {
                    List<InstancePoint> listPointsXY = new ArrayList<>();
                    List<PointDragged> pointsRectangle = ((MusicVisualizationPanel) drawingArea).getPointsRectangle();
                    listPointsXY = getListsWithParameters(listPoints, minX, minY, maxX, maxY, pointsRectangle);
                    HashSet<Float> hashSetClustersXY = new HashSet<>();

                    if (listPointsXY.size() > 0) {
                        Float newMinX = Collections.min(listPointsXY, new InstancePointXComparator()).getX();
                        Float newMaxX = Collections.max(listPointsXY, new InstancePointXComparator()).getX();
                        Float newMinY = Collections.min(listPointsXY, new InstancePointYComparator()).getY();
                        Float newMaxY = Collections.max(listPointsXY, new InstancePointYComparator()).getY();

                        if (newMinX.equals(newMaxX)) {
                            newMinX = newMinX - 30;
                            newMaxX = newMaxX + 30;
                        }
                        if (newMinY.equals(newMaxY)) {
                            newMinY = newMinY - 30;
                            newMaxY = newMaxY + 30;
                        }

                        for (int k = 0; k < listPointsXY.size(); k++) {
                            hashSetClustersXY.add(listPointsXY.get(k).getCluster());
                        }
                        MusicVisualization frame2 = new MusicVisualization(title, numTitle + 1, this.option, this.basicColor);
                        frame2.setFileName(this.fileName);

                        if (!this.option.equals("projection")) {
                            frame2.setNameX(this.columns1.getSelectedItem().toString());
                            frame2.setNameY(this.columns2.getSelectedItem().toString());
                        } else {
                            frame2.setNameX("x");
                            frame2.setNameX("y");
                        }


                        frame2.setMatrix(matrix);


                        BufferedImage image = ((MusicVisualizationPanel) drawingArea).getImageWithRectangle();
                        frame2.setImageOriginal(image);
                        frame2.setListPoints(listPointsXY);//x
                        frame2.setHashSetClusters(hashSetClustersXY);

                        frame2.setMinX(newMinX);
                        frame2.setMaxX(newMaxX);
                        frame2.setMinY(newMinY);
                        frame2.setMaxY(newMaxY);
                        frame2.setNumData(listPointsXY.size());
                        frame2.setPathsIcones(pathsIcones);
                        frame2.setPathsLabel(pathsLabel);
                        frame2.setPathsPlaylists(pathsPlaylists);
                        frame2.setListLabelMidi(listLabelMidi);

                        frame2.setSize(1200, 650);
                        try {
                            frame2.loadDrawPanel();
                        } catch (IOException ex) {
                            Logger.getLogger(MusicVisualization.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        frame2.setVisible(true);
                        frame2.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);



                    } else {
                        JOptionPane.showMessageDialog(this, "Can't have less than 1 elements.");
                    }


                } else {
                    JOptionPane.showMessageDialog(this, "Parameters of zoom semantic aren't defined.");
                }
            } else {
                if (this.checkIconSummary) {
                    List<InstancePoint> listSelectedPointsXY = new ArrayList<>();

                    listSelectedPointsXY = ((MusicVisualizationPanel) drawingArea).getListSelectedPointsIconSummary();
                    HashSet<Float> hashSetClustersXY = new HashSet<>();
                    if (listSelectedPointsXY.size() > 0) {
                        Float newMinX = Collections.min(listSelectedPointsXY, new InstancePointXComparator()).getX();
                        Float newMaxX = Collections.max(listSelectedPointsXY, new InstancePointXComparator()).getX();
                        Float newMinY = Collections.min(listSelectedPointsXY, new InstancePointYComparator()).getY();
                        Float newMaxY = Collections.max(listSelectedPointsXY, new InstancePointYComparator()).getY();

                        if (newMinX.equals(newMaxX)) {
                            newMinX = newMinX - 30;
                            newMaxX = newMaxX + 30;
                        }
                        if (newMinY.equals(newMaxY)) {
                            newMinY = newMinY - 30;
                            newMaxY = newMaxY + 30;
                        }

                        for (int k = 0; k < listSelectedPointsXY.size(); k++) {
                            hashSetClustersXY.add(listSelectedPointsXY.get(k).getCluster());
                        }
                        MusicVisualization frame2 = new MusicVisualization(title, numTitle + 1, this.option, this.basicColor);
                        frame2.setFileName(this.fileName);

                        if (!this.option.equals("projection")) {
                            frame2.setNameX(this.columns1.getSelectedItem().toString());
                            frame2.setNameY(this.columns2.getSelectedItem().toString());
                        } else {
                            frame2.setNameX("x");
                            frame2.setNameX("y");
                        }


                        frame2.setMatrix(matrix);


                        BufferedImage image = ((MusicVisualizationPanel) drawingArea).getImage();
                        frame2.setImageOriginal(image);
                        frame2.setListPoints(listSelectedPointsXY);//x
                        frame2.setHashSetClusters(hashSetClustersXY);

                        frame2.setMinX(newMinX);
                        frame2.setMaxX(newMaxX);
                        frame2.setMinY(newMinY);
                        frame2.setMaxY(newMaxY);
                        frame2.setNumData(listSelectedPointsXY.size());
                        frame2.setPathsIcones(pathsIcones);
                        frame2.setPathsLabel(pathsLabel);
                        frame2.setPathsPlaylists(pathsPlaylists);
                        frame2.setListLabelMidi(listLabelMidi);

                        frame2.setSize(1200, 650);
                        try {
                            frame2.loadDrawPanel();
                        } catch (IOException ex) {
                            Logger.getLogger(MusicVisualization.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        frame2.setVisible(true);
                        frame2.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Button of zoom semantic isn't actived.");
                }
            }
        }

        if ((e.getActionCommand()).equals("phylogeneticTree")) {

            if (matrix != null) {


                if (this.checkZoomLocal) {
                    boolean statusRectangle = ((MusicVisualizationPanel) drawingArea).getStatusRectangle();
                    if (statusRectangle) {
                        List<InstancePoint> listPointsXY = new ArrayList<>();
                        List<PointDragged> pointsRectangle = ((MusicVisualizationPanel) drawingArea).getPointsRectangle();
                        listPointsXY = getListsWithParameters(listPoints, minX, minY, maxX, maxY, pointsRectangle);
                        if (listPointsXY.size() > 2) {
                            AbstractMatrix matrixTree = null;


                            try {
                                matrixTree = MatrixFactory.getInstance(this.matrix.getClass());
                            } catch (IOException ex) {
                            }

                            if (listPointsXY.size() > 0) {
                                for (int i = 0; i < listPointsXY.size(); i++) {
                                    try {
                                        for (int j = 0; j < matrix.getRowCount(); j++) {

                                            if (listPointsXY.get(i).getLabel().equals(matrix.getLabel(j))) {
                                                matrixTree.addRow(matrix.getRow(j), matrix.getLabel(j));

                                                continue;
                                            }
                                        }
                                    } catch (Exception e2) {
                                    }

                                }
                                try {

                                    DissimilarityType disstype = DissimilarityType.DYNAMIC_TIME_WARPING;
                                    AbstractDissimilarity diss = DissimilarityFactory.getInstance(disstype);
                                    DistanceMatrix dmat;
                                    //SimpleTree tree;
                                    boolean pnj = false;

                                    //matrixTree = MatrixFactory.getInstance(fileName);

                                    PackageNJConnectionComp treeComp = new PackageNJConnectionComp();
                                    treeComp.input(matrixTree);
                                    treeComp.execute();
                                    Tree tree = treeComp.output();
                                    TreeModelComp modelComp = new TreeModelComp();
                                    modelComp.input(tree);
                                    modelComp.execute();
                                    TreeModel treeModel = modelComp.output();
                                    RadialLayoutComp radialLayout = new RadialLayoutComp();
                                    radialLayout.input(treeModel);
                                    radialLayout.execute();
                                    TreeFrameComp treeFrame = new TreeFrameComp();
                                    treeFrame.input(radialLayout.output());
                                    treeFrame.execute();
                                } catch (IOException ex) {
                                    Logger.getLogger(MusicVisualization.class
                                            .getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                JOptionPane.showMessageDialog(this, "Can't have less than 1 elements.");
                            }


                        } else {
                            JOptionPane.showMessageDialog(this, "Mas de un elemento.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Parameters of zoom semantic aren't defined.");
                    }

                } else {
                    if (this.checkIconSummary) {
                        List<InstancePoint> listSelectedPointsXY = new ArrayList<>();
                        listSelectedPointsXY = ((MusicVisualizationPanel) drawingArea).getListSelectedPointsIconSummary();

                        if (listSelectedPointsXY.size() > 2) {
                            AbstractMatrix matrixTree = null;
                            try {
                                matrixTree = MatrixFactory.getInstance(this.matrix.getClass());
                            } catch (IOException ex) {
                            }
                            if (listSelectedPointsXY.size() > 0) {
                                for (int i = 0; i < listSelectedPointsXY.size(); i++) {
                                    try {
                                        for (int j = 0; j < matrix.getRowCount(); j++) {

                                            if (listSelectedPointsXY.get(i).getLabel().equals(matrix.getLabel(j))) {
                                                matrixTree.addRow(matrix.getRow(j), matrix.getLabel(j));

                                                continue;
                                            }
                                        }
                                    } catch (Exception e2) {
                                    }

                                }
                                try {

                                    DissimilarityType disstype = DissimilarityType.EUCLIDEAN;
                                    AbstractDissimilarity diss = DissimilarityFactory.getInstance(disstype);
                                    DistanceMatrix dmat;
                                    //SimpleTree tree;
                                    boolean pnj = false;

                                    //matrixTree = MatrixFactory.getInstance(fileName);

                                    PackageNJConnectionComp treeComp = new PackageNJConnectionComp();
                                    treeComp.input(matrixTree);
                                    treeComp.execute();
                                    Tree tree = treeComp.output();
                                    TreeModelComp modelComp = new TreeModelComp();
                                    modelComp.input(tree);
                                    modelComp.execute();
                                    TreeModel treeModel = modelComp.output();
                                    RadialLayoutComp radialLayout = new RadialLayoutComp();
                                    radialLayout.input(treeModel);
                                    radialLayout.execute();
                                    TreeFrameComp treeFrame = new TreeFrameComp();
                                    treeFrame.input(radialLayout.output());
                                    treeFrame.execute();
                                } catch (IOException ex) {
                                    Logger.getLogger(MusicVisualization.class
                                            .getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Button of zoom semantic isn't actived.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Matrix isn't defined.");
            }
        }

        if ((e.getActionCommand()).equals("iconSummary")) {

            if (checkIconSummary == false) {

                this.buttonIconSummary.setBackground(Color.red);
                checkIconSummary = true;


                this.buttonLensSplatting.setBackground(new Color(238, 238, 238));
                ((MusicVisualizationPanel) drawingArea).setCheckLensSplatting(false);
                checkLensSplatting = false;

                this.buttonZoomLocal.setBackground(new Color(238, 238, 238));
                ((MusicVisualizationPanel) drawingArea).setCheckZoomLocal(false);
                checkZoomLocal = false;


                this.buttonLabel.setBackground(new Color(238, 238, 238));
                ((MusicVisualizationPanel) drawingArea).setCheckLabel(false);
                checkLabel = false;

                this.buttonNameSongsLabel.setBackground(new Color(238, 238, 238));
                ((MusicVisualizationPanel) drawingArea).setCheckNameSongsLabel(false);
                checkNameSongsLabel = false;


                this.buttonLens.setBackground(new Color(238, 238, 238));
                ((MusicVisualizationPanel) drawingArea).setCheckLens(false);
                checkLens = false;



                String widthTableInput = null;

                if (widthTableInput == null) {
                    widthTableInput = (String) JOptionPane.showInputDialog(
                            this,
                            "Width",
                            "Icon Summary",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            null,
                            "10");
                    if ((widthTableInput != null) && (widthTableInput.length() > 0)) {
                        try {
                            this.widthTable = Integer.valueOf(widthTableInput);
                        } catch (NumberFormatException ee1) {
                            this.widthTable = 10;
                            widthTableInput = null;
                        }
                    } else {
                        widthTableInput = null;
                    }

                }



                String heightTableInput = null;

                if (heightTableInput == null) {
                    heightTableInput = (String) JOptionPane.showInputDialog(
                            this,
                            "Height",
                            "Icon Summary",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            null,
                            "10");
                    if ((heightTableInput != null) && (heightTableInput.length() > 0)) {
                        try {
                            this.heightTable = Integer.valueOf(heightTableInput);
                        } catch (Exception ee1) {
                            this.heightTable = 10;
                            heightTableInput = null;
                        }
                    } else {
                        heightTableInput = null;
                    }

                }
                if (this.widthTable < 1) {
                    this.widthTable = 1;
                }
                if (this.widthTable > ((MusicVisualizationPanel) drawingArea).getWidth()) {
                    this.widthTable = ((MusicVisualizationPanel) drawingArea).getWidth();
                }
                if (this.heightTable < 1) {
                    this.heightTable = 1;
                }
                if (this.heightTable > ((MusicVisualizationPanel) drawingArea).getHeight()) {
                    this.heightTable = ((MusicVisualizationPanel) drawingArea).getHeight();
                }


                Object[] possibilities = {"single icon", "multiple icons"};
                String formatIcon = (String) JOptionPane.showInputDialog(
                        this,
                        "Format to visualizate",
                        "Icon Summary",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        possibilities,
                        "single icon");


                Object[] possibilitiesGrid = {"no", "yes"};
                String gridIcon = (String) JOptionPane.showInputDialog(
                        this,
                        "Draw the grid?",
                        "Icon Summary",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        possibilitiesGrid,
                        "no");
                if (gridIcon.equals("yes")) {
                    ((MusicVisualizationPanel) drawingArea).setCheckDrawGridScreenIconSummary(true);
                } else {
                    ((MusicVisualizationPanel) drawingArea).setCheckDrawGridScreenIconSummary(false);
                }
                ((MusicVisualizationPanel) drawingArea).setHeightTable(heightTable);
                ((MusicVisualizationPanel) drawingArea).setWidthTable(widthTable);
                ((MusicVisualizationPanel) drawingArea).setFormatIcon(formatIcon);
                ((MusicVisualizationPanel) drawingArea).setCheckIconSummary(checkIconSummary);



            } else {
                this.buttonIconSummary.setBackground(new Color(238, 238, 238));
                checkIconSummary = false;
                ((MusicVisualizationPanel) drawingArea).setCheckIconSummary(checkIconSummary);
                drawDrawingPanel();
            }

        }

        if ((e.getActionCommand()).equals("reproduceList")) {

            if (this.checkZoomLocal) {
                boolean statusRectangle = ((MusicVisualizationPanel) drawingArea).getStatusRectangle();
                if (statusRectangle) {

                    if (listLabelMidi == null || listLabelMidi.isEmpty()) {
                        int returnVal = fileChooser.showOpenDialog(this);

                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            try {
                                listLabelMidi = null;

                                File fileNameChooser = fileChooser.getSelectedFile();
                                String labelMidiFile = fileNameChooser.toString();
                                loadLabelMidi(labelMidiFile);
                                setVisible(true);

                            } catch (Exception e1) {
                                JOptionPane.showMessageDialog(null, "This file is not a correct label midi.");
                                setVisible(true);
                            }

                        }
                    }

                    if (this.listLabelMidi != null && this.listLabelMidi.size() > 0) {
                        List<InstancePoint> listPointsXY = new ArrayList<>();
                        List<PointDragged> pointsRectangle = ((MusicVisualizationPanel) drawingArea).getPointsRectangle();
                        listPointsXY = getListsWithParameters(listPoints, minX, minY, maxX, maxY, pointsRectangle);
                        //  MidiPlayerView playerMidi = new MidiPlayerView(listPointsXY, this.listLabelMidi);
                        //  playerMidi.setSize(500, 500);
                        //  playerMidi.setVisible(true);
                        //  playerMidi.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                        this.listNames = new ArrayList<>();
                        this.listPaths = new ArrayList<>();
                        this.listLabels = new ArrayList<>();
                        for (int i = 0; i < listPointsXY.size(); i++) {
                            for (int j = 0; j < listLabelMidi.size(); j++) {
                                if (listPointsXY.get(i).getLabel().equals(listLabelMidi.get(j).getName())) {

                                    String nameData = listLabelMidi.get(j).getPath();
                                    while (nameData.contains("/")) {
                                        nameData = nameData.substring(nameData.indexOf("/") + 1);
                                    }

                                    nameData = nameData.substring(0, nameData.lastIndexOf("."));
                                    nameData = nameData.replaceAll("/", "-");
                                    listNames.add(nameData.trim());
                                    listPaths.add(listLabelMidi.get(j).getPath().trim());
                                    listLabels.add(listPointsXY.get(i).getLabel().trim());
                                    break;
                                }
                            }
                        }
                        DefaultTableModel aModel = new DefaultTableModel() {
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        };
                        aModel.addColumn("Selected Songs", listNames.toArray());

                        tableMidis.setModel(aModel);
                        this.tableMidis.revalidate();
                    }


                } else {
                    JOptionPane.showMessageDialog(this, "Rectangle isn't defined.");
                }
            } else {
                if (this.checkIconSummary) {
                    if (listLabelMidi == null || listLabelMidi.isEmpty()) {
                        int returnVal = fileChooser.showOpenDialog(this);

                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            try {
                                listLabelMidi = null;

                                File fileNameChooser = fileChooser.getSelectedFile();
                                String labelMidiFile = fileNameChooser.toString();
                                loadLabelMidi(labelMidiFile);
                                setVisible(true);

                            } catch (Exception e1) {
                                JOptionPane.showMessageDialog(null, "This file is not a correct label midi.");
                                setVisible(true);
                            }

                        }
                    }

                    if (this.listLabelMidi != null && this.listLabelMidi.size() > 0) {
                        List<InstancePoint> listSelectedPointsXY = new ArrayList<>();
                        listSelectedPointsXY = ((MusicVisualizationPanel) drawingArea).getListSelectedPointsIconSummary();

                        this.listNames = new ArrayList<>();
                        this.listPaths = new ArrayList<>();
                        this.listLabels = new ArrayList<>();
                        for (int i = 0; i < listSelectedPointsXY.size(); i++) {
                            for (int j = 0; j < listLabelMidi.size(); j++) {
                                if (listSelectedPointsXY.get(i).getLabel().equals(listLabelMidi.get(j).getName())) {

                                    String nameData = listLabelMidi.get(j).getPath();
                                    while (nameData.contains("/")) {
                                        nameData = nameData.substring(nameData.indexOf("/") + 1);
                                    }

                                    nameData = nameData.substring(0, nameData.lastIndexOf("."));
                                    nameData = nameData.replaceAll("/", "-");
                                    listNames.add(nameData.trim());
                                    listPaths.add(listLabelMidi.get(j).getPath().trim());
                                    listLabels.add(listSelectedPointsXY.get(i).getLabel().trim());
                                    break;
                                }
                            }
                        }
                        DefaultTableModel aModel = new DefaultTableModel() {
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        };
                        aModel.addColumn("Selected Songs", listNames.toArray());

                        tableMidis.setModel(aModel);
                        this.tableMidis.revalidate();
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Button of zoom semantic isn't actived.");
                }
            }

        }


        if ((e.getActionCommand()).equals("reproduceIconeList")) {

            if (this.checkZoomLocal) {
                boolean statusRectangle = ((MusicVisualizationPanel) drawingArea).getStatusRectangle();
                if (statusRectangle) {

                    if (listLabelMidi == null || listLabelMidi.isEmpty()) {
                        int returnVal = fileChooser.showOpenDialog(this);

                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            try {
                                listLabelMidi = null;

                                File fileNameChooser = fileChooser.getSelectedFile();
                                String labelMidiFile = fileNameChooser.toString();
                                loadLabelMidi(labelMidiFile);
                                setVisible(true);

                            } catch (Exception e1) {
                                JOptionPane.showMessageDialog(null, "This file is not a correct label midi.");
                                setVisible(true);
                            }

                        }
                    }

                    if (this.listLabelMidi != null && this.listLabelMidi.size() > 0) {
                        List<InstancePoint> listPointsXY = new ArrayList<>();
                        List<PointDragged> pointsRectangle = ((MusicVisualizationPanel) drawingArea).getPointsRectangle();
                        listPointsXY = getListsWithParameters(listPoints, minX, minY, maxX, maxY, pointsRectangle);
                        BufferedImage subImage = ((MusicVisualizationPanel) drawingArea).getSubImage();
                        MidiIconePlayerView playerMidi = new MidiIconePlayerView(listPointsXY, this.listLabelMidi, this.pathsIcones, this.pathsPlaylists, subImage, distanceMatrix);
                        playerMidi.setSize(600, 700);
                        playerMidi.setVisible(true);
                        playerMidi.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    }


                } else {
                    JOptionPane.showMessageDialog(this, "Rectangle isn't defined.");
                }
            } else {
                if (this.checkIconSummary) {

                    if (listLabelMidi == null || listLabelMidi.isEmpty()) {
                        int returnVal = fileChooser.showOpenDialog(this);

                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            try {
                                listLabelMidi = null;

                                File fileNameChooser = fileChooser.getSelectedFile();
                                String labelMidiFile = fileNameChooser.toString();
                                loadLabelMidi(labelMidiFile);
                                setVisible(true);

                            } catch (Exception e1) {
                                JOptionPane.showMessageDialog(null, "This file is not a correct label midi.");
                                setVisible(true);
                            }

                        }
                    }

                    if (this.listLabelMidi != null && this.listLabelMidi.size() > 0) {
                        List<InstancePoint> listSelectedPointsXY = new ArrayList<>();
                        listSelectedPointsXY = ((MusicVisualizationPanel) drawingArea).getListSelectedPointsIconSummary();
                        BufferedImage subImage = ((MusicVisualizationPanel) drawingArea).getSubImage();
                        MidiIconePlayerView playerMidi = new MidiIconePlayerView(listSelectedPointsXY, this.listLabelMidi, this.pathsIcones, this.pathsPlaylists, subImage, distanceMatrix);
                        playerMidi.setSize(600, 700);
                        playerMidi.setVisible(true);
                        playerMidi.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Button of zoom semantic isn't actived.");
                }
            }

        }
        if ((e.getActionCommand()).equals("addAll")) {
            addAll(e);
        }

        if ((e.getActionCommand()).equals("addSelection")) {
            addSelection(e);
        }
        if ((e.getActionCommand()).equals("changePlayList")) {
            changePlayList(e);
        }
        if ((e.getActionCommand()).equals("savePlaylist")) {
            savePlaylist(e);
        }
        if ((e.getActionCommand()).equals("previousSelectedSongs")) {
            previousSelectedSongs();
        }
        if ((e.getActionCommand()).equals("previousPlaylist")) {
            previousPlaylist();
        }
        if ((e.getActionCommand()).equals("stopMidi")) {
            stopMidi();
        }
        if ((e.getActionCommand()).equals("playSelectedSongs")) {
            playSelectedSongs();
        }
        if ((e.getActionCommand()).equals("playPlaylist")) {
            playPlaylist();
        }
        if ((e.getActionCommand()).equals("nextSelectedSongs")) {
            nextSelectedSongs();
        }
        if ((e.getActionCommand()).equals("nextPlaylist")) {
            nextPlaylist();
        }
        if ((e.getActionCommand()).equals("removePlaylist")) {
            removePlaylist();
        }
        if ((e.getActionCommand()).equals("removeSelectedSongs")) {
            removeSelectedSongs();
        }
        if ((e.getActionCommand()).equals("removeFilePlaylist")) {
            removeFilePlaylist();
        }

    }

    public static AbstractMatrix getInstance(List<InstancePoint> listPointsXY) throws IOException {
        if (listPointsXY.size() > 0) {
            AbstractMatrix matrixTmp = null;
            matrixTmp = new DenseMatrix();

            for (int i = 0; i < listPointsXY.size(); i++) {
                String label = listPointsXY.get(i).getLabel();
                //class data
                float klass = 0.0f;
                //the vector
                float[] vector = new float[2];
                vector[0] = listPointsXY.get(i).getX();
                vector[1] = listPointsXY.get(i).getY();
                AbstractVector abstractVector = null;
                abstractVector = new DenseVector(vector);
                ((AbstractMatrix) matrixTmp).addRow(abstractVector, label);

            }
            return matrixTmp;

        }
        return null;

    }
    private void loadConnections(String connFile) {
        this.listConnections = new ArrayList<>();
        String data[];
        try {
            InputStream fstream = new FileInputStream(connFile);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine = br.readLine(); //descartar 1era linea
            // Read File Line By Line
            
            while ((strLine = br.readLine()) != null) {
                data = strLine.split(";");
                try
                {
                String x = this.listPoints.get(Integer.valueOf(data[0])).getLabel();
                String y = this.listPoints.get(Integer.valueOf(data[1])).getLabel();
                listConnections.add(new Connection(x, y));
                }
                catch(NumberFormatException list1)
                {
                    
                }
            }
            // Close the input stream
            in.close();


        } catch (IOException e) {// Catch exception if any
            JOptionPane.showMessageDialog(null, "This file is not a correct label midi.");

        }



    }
    private void loadLabelMidi(String labelMidiFile) {
        this.listLabelMidi = new ArrayList<>();
        String data[];
        try {
            InputStream fstream = new FileInputStream(labelMidiFile);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            // Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                data = strLine.split(";");
                String labelMidi = data[0];
                String path = data[1];
                listLabelMidi.add(new LabelMidi(labelMidi, path));
            }
            // Close the input stream
            in.close();
        } catch (Exception e) {// Catch exception if any
            JOptionPane.showMessageDialog(null, "This file is not a correct label midi.");

        }
    }

    public void removeFilePlaylist() {
        if (this.comboPlayList.getSelectedIndex() != 0) {
            File file = new File(pathsPlaylists + "/" + this.comboPlayList.getSelectedItem());
            boolean success = file.delete();
            if (!success) {
                JOptionPane.showMessageDialog(null, "Deletion failed.");
            } else {
                JOptionPane.showMessageDialog(null, "File deleted.");
                this.listPlayList = getListPlaylist();
                strPlayList = new String[listPlayList.size()];
                listPlayList.toArray(strPlayList);


                //this.comboPlayList = new JComboBox();
                DefaultComboBoxModel model = new DefaultComboBoxModel(strPlayList);
                this.comboPlayList.removeAllItems();
                this.comboPlayList.setModel(model);
                this.comboPlayList.revalidate();
                this.comboPlayList.repaint();
            }

        }
    }

    public void removePlaylist() {
        int[] rows = this.tableMidisPlaylist.getSelectedRows();
        if (rows.length > 0) {

            for (int j = 0; j < rows.length; j++) {

                if (listNamesPlayList.get(rows[j]).equals(currentSongTextArea.getText())) {
                    stopMidi();
                }
            }
            List<String> listPathsTmp = new ArrayList<>();
            List<String> listNamesTmp = new ArrayList<>();
            List<String> listLabelsTmp = new ArrayList<>();

            for (int i = 0; i < listPathsPlayList.size(); i++) {
                boolean isSelected = false;
                for (int j = 0; j < rows.length; j++) {
                    if (rows[j] == i) {
                        isSelected = true;
                    }
                }
                if (isSelected == false) {
                    listPathsTmp.add(listPathsPlayList.get(i));
                    listNamesTmp.add(listNamesPlayList.get(i));
                    listLabelsTmp.add(listLabelsPlayList.get(i));
                }
            }

            listPathsPlayList = listPathsTmp;
            listNamesPlayList = listNamesTmp;
            listLabelsPlayList = listLabelsTmp;
            DefaultTableModel aModel = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            aModel.addColumn("PlayList", listNamesPlayList.toArray());

            tableMidisPlaylist.setModel(aModel);
            this.tableMidisPlaylist.revalidate();
        }
    }

    public void removeSelectedSongs() {
        int[] rows = this.tableMidis.getSelectedRows();
        if (rows.length > 0) {

            for (int j = 0; j < rows.length; j++) {

                if (listNames.get(rows[j]).equals(currentSongTextArea.getText())) {
                    stopMidi();
                }
            }
            List<String> listPathsTmp = new ArrayList<>();
            List<String> listNamesTmp = new ArrayList<>();
            List<String> listLabelsTmp = new ArrayList<>();

            for (int i = 0; i < listPaths.size(); i++) {
                boolean isSelected = false;
                for (int j = 0; j < rows.length; j++) {
                    if (rows[j] == i) {
                        isSelected = true;
                    }
                }
                if (isSelected == false) {
                    listPathsTmp.add(listPaths.get(i));
                    listNamesTmp.add(listNames.get(i));
                    listLabelsTmp.add(listLabels.get(i));
                }
            }

            listPaths = listPathsTmp;
            listNames = listNamesTmp;
            listLabels = listLabelsTmp;
            DefaultTableModel aModel = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            aModel.addColumn("PlayList", listNames.toArray());

            tableMidis.setModel(aModel);
            this.tableMidis.revalidate();
        }
    }

    public void stopMidi() {
        clearSelectedTableMidisPlaylist();
        clearSelectedTableMidis();
        this.currentSongTextArea.setText("");
        this.currentSongTextArea.revalidate();
        this.currentSongTextArea.repaint();

        this.iconeLabel.setIcon(null);
        this.iconeLabel.revalidate();
        this.iconeLabel.repaint();
        try {
            midiPlayer.stop();

        } catch (Exception e) {
        }

    }

    public void playSelectedSongs() {
        clearSelectedTableMidisPlaylist();
        if (this.tableMidis.getRowCount() == 0) {
            stopMidi();
        } else {
            int index = this.tableMidis.getSelectedRow();
            if (index < 0) {
                index = 0;
            }
            tableMidis.getSelectionModel().setSelectionInterval(index, index);
            File file = new File(this.listPaths.get(index));


            this.currentSongTextArea.setLineWrap(true);
            this.currentSongTextArea.setWrapStyleWord(true);
            this.currentSongTextArea.setText(this.listNames.get(index));
            this.currentSongTextArea.setSize(250, 4);

            this.currentSongTextArea.revalidate();
            this.currentSongTextArea.repaint();


            try {
                ImageIcon imageIcon = new ImageIcon(pathsIcones + "/" + this.listLabels.get(index) + ".png");
                Image img = imageIcon.getImage();
                this.iconeLabel.setIcon(new ImageIcon(this.getScaledImage(img, 150, 30)));
                this.iconeLabel.revalidate();
                this.iconeLabel.repaint();
            } catch (Exception e) {
                this.iconeLabel.setText("");
                this.iconeLabel.revalidate();
                this.iconeLabel.repaint();
            }

            midiPlayer.play(file);
        }
    }

    public void playPlaylist() {
        clearSelectedTableMidis();
        if (this.tableMidisPlaylist.getRowCount() == 0) {
            stopMidi();
        } else {
            int index = this.tableMidisPlaylist.getSelectedRow();
            if (index < 0) {
                index = 0;
            }
            tableMidisPlaylist.getSelectionModel().setSelectionInterval(index, index);
            File file = new File(this.listPathsPlayList.get(index));
            this.currentSongTextArea.setLineWrap(true);
            this.currentSongTextArea.setWrapStyleWord(true);
            this.currentSongTextArea.setText(this.listNamesPlayList.get(index));
            this.currentSongTextArea.setSize(250, 4);
            this.currentSongTextArea.revalidate();
            this.currentSongTextArea.repaint();



            try {
                ImageIcon imageIcon = new ImageIcon(pathsIcones + "/" + this.listLabelsPlayList.get(index) + ".png");
                Image img = imageIcon.getImage();
                this.iconeLabel.setIcon(new ImageIcon(this.getScaledImage(img, 150, 30)));
                this.iconeLabel.revalidate();

                this.iconeLabel.repaint();
            } catch (Exception e) {
                this.iconeLabel.setText("");
                this.iconeLabel.revalidate();

                this.iconeLabel.repaint();
            }

            midiPlayer.play(file);
        }
    }

    public void nextSelectedSongs() {
        clearSelectedTableMidisPlaylist();

        if (this.tableMidis.getRowCount() == 0) {
            stopMidi();
        } else {
            int index = this.tableMidis.getSelectedRow();
            index = index + 1;
            index = index % this.tableMidis.getRowCount();
            tableMidis.getSelectionModel().setSelectionInterval(index, index);
            File file = new File(this.listPaths.get(index));
            this.currentSongTextArea.setLineWrap(true);
            this.currentSongTextArea.setWrapStyleWord(true);
            this.currentSongTextArea.setText(this.listNames.get(index));
            this.currentSongTextArea.setSize(250, 4);
            this.currentSongTextArea.revalidate();
            this.currentSongTextArea.repaint();



            try {
                ImageIcon imageIcon = new ImageIcon(pathsIcones + "/" + this.listLabels.get(index) + ".png");
                Image img = imageIcon.getImage();
                this.iconeLabel.setIcon(new ImageIcon(this.getScaledImage(img, 150, 30)));
                this.iconeLabel.revalidate();
                this.iconeLabel.repaint();
            } catch (Exception e) {
                this.iconeLabel.setText("");
                this.iconeLabel.revalidate();
                this.iconeLabel.repaint();
            }

            midiPlayer.play(file);
        }
    }

    public void nextPlaylist() {
        clearSelectedTableMidis();

        if (this.tableMidisPlaylist.getRowCount() == 0) {
            stopMidi();
        } else {
            int index = this.tableMidisPlaylist.getSelectedRow();
            index = index + 1;
            index = index % this.tableMidisPlaylist.getRowCount();
            tableMidisPlaylist.getSelectionModel().setSelectionInterval(index, index);
            File file = new File(this.listPathsPlayList.get(index));
            this.currentSongTextArea.setLineWrap(true);
            this.currentSongTextArea.setWrapStyleWord(true);
            this.currentSongTextArea.setText(this.listNamesPlayList.get(index));
            this.currentSongTextArea.setSize(250, 4);
            this.currentSongTextArea.revalidate();
            this.currentSongTextArea.repaint();



            try {

                ImageIcon imageIcon = new ImageIcon(pathsIcones + "/" + this.listLabelsPlayList.get(index) + ".png");
                Image img = imageIcon.getImage();
                this.iconeLabel.setIcon(new ImageIcon(this.getScaledImage(img, 150, 30)));
                this.iconeLabel.revalidate();

                this.iconeLabel.repaint();
            } catch (Exception e) {
                this.iconeLabel.setText("");
                this.iconeLabel.revalidate();

                this.iconeLabel.repaint();
            }

            midiPlayer.play(file);
        }
    }

    public void previousSelectedSongs() {
        clearSelectedTableMidisPlaylist();

        if (this.tableMidis.getRowCount() == 0) {
            stopMidi();
        } else {
            int index = this.tableMidis.getSelectedRow();
            index = index - 1;
            if (index < 0) {
                index = 0;
            }
            tableMidis.getSelectionModel().setSelectionInterval(index, index);
            File file = new File(this.listPaths.get(index));
            this.currentSongTextArea.setLineWrap(true);
            this.currentSongTextArea.setWrapStyleWord(true);
            this.currentSongTextArea.setText(this.listNames.get(index));
            this.currentSongTextArea.setSize(250, 4);
            this.currentSongTextArea.revalidate();
            this.currentSongTextArea.repaint();



            try {

                ImageIcon imageIcon = new ImageIcon(pathsIcones + "/" + this.listLabels.get(index) + ".png");
                Image img = imageIcon.getImage();
                this.iconeLabel.setIcon(new ImageIcon(this.getScaledImage(img, 150, 30)));
                this.iconeLabel.revalidate();

                this.iconeLabel.repaint();
            } catch (Exception e) {
                this.iconeLabel.setText("");
                this.iconeLabel.revalidate();

                this.iconeLabel.repaint();
            }

            midiPlayer.play(file);
        }
    }

    public void previousPlaylist() {
        clearSelectedTableMidis();
        if (this.tableMidisPlaylist.getRowCount() == 0) {
            stopMidi();
        } else {
            int index = this.tableMidisPlaylist.getSelectedRow();
            index = index - 1;
            if (index < 0) {
                index = 0;
            }
            tableMidisPlaylist.getSelectionModel().setSelectionInterval(index, index);
            File file = new File(this.listPathsPlayList.get(index));
            this.currentSongTextArea.setLineWrap(true);
            this.currentSongTextArea.setWrapStyleWord(true);
            this.currentSongTextArea.setText(this.listNamesPlayList.get(index));
            this.currentSongTextArea.setSize(250, 4);
            this.currentSongTextArea.revalidate();
            this.currentSongTextArea.repaint();



            try {

                ImageIcon imageIcon = new ImageIcon(pathsIcones + "/" + this.listLabelsPlayList.get(index) + ".png");
                Image img = imageIcon.getImage();
                this.iconeLabel.setIcon(new ImageIcon(this.getScaledImage(img, 150, 30)));
                this.iconeLabel.revalidate();

                this.iconeLabel.repaint();
            } catch (Exception e) {
                this.iconeLabel.setText("");
                this.iconeLabel.revalidate();
                this.iconeLabel.repaint();
            }

            midiPlayer.play(file);
        }
    }

    public void playTableMidis() {

        this.currentSongTextArea.setText("");
        this.currentSongTextArea.revalidate();
        this.currentSongTextArea.repaint();

        this.iconeLabel.setIcon(null);
        this.iconeLabel.revalidate();
        this.iconeLabel.repaint();
        try {
            midiPlayer.stop();

        } catch (Exception e) {
        }
        try {
            int index = this.tableMidis.getSelectedRow();
            if (index < 0) {
                index = 0;
                tableMidis.getSelectionModel().setSelectionInterval(index, index);
            }
            File file = new File(this.listPaths.get(index));
            this.currentSongTextArea.setLineWrap(true);
            this.currentSongTextArea.setWrapStyleWord(true);
            this.currentSongTextArea.setText(this.listNames.get(index));
            this.currentSongTextArea.setSize(250, 4);
            this.currentSongTextArea.revalidate();
            this.currentSongTextArea.repaint();



            try {
                ImageIcon imageIcon = new ImageIcon(pathsIcones + "/" + this.listLabels.get(index) + ".png");
                Image img = imageIcon.getImage();
                this.iconeLabel.setIcon(new ImageIcon(this.getScaledImage(img, 150, 30)));
                this.iconeLabel.revalidate();
                this.iconeLabel.repaint();
            } catch (Exception e) {
                this.iconeLabel.setText("");
                this.iconeLabel.revalidate();
                this.iconeLabel.repaint();
            }

            midiPlayer.play(file);
        } catch (Exception e1) {
        }
    }

    public void drawDrawingPanel() {

        this.sliderBrightness.setValue(50);
        if (comboPoints.getSelectedItem().toString().equals("Graph")) {
         
            fileChooser = new JFileChooser();
            File connectionsFile = null;
            String connectionsFilestr = null;
            ExtensionFileFilter filter = new ExtensionFileFilter("CON and con", new String[]{"CON", "con"});
            fileChooser.setFileFilter(filter);
            fileChooser.setDialogTitle("Path of the connections file");
            int returnVal1 = fileChooser.showOpenDialog(this);

            if (returnVal1 == JFileChooser.APPROVE_OPTION) {
                try {
                    connectionsFile = fileChooser.getSelectedFile();
                    connectionsFilestr = connectionsFile.toString();
                    this.loadConnections(connectionsFilestr);
                    ((MusicVisualizationPanel) drawingArea).setListConnections(this.listConnections);
                } catch (Exception fileException) {
                    JOptionPane.showMessageDialog(null, "This file is not a correct connections file.");
                }
            }

        }

        ((MusicVisualizationPanel) drawingArea).setValueBrightness(50);
        ((MusicVisualizationPanel) drawingArea).setCheckBrightness(false);
        if (numTitle.equals(0) && (boolListX == true || boolListY == true) && !option.equals("projection")) {
            listPoints = new ArrayList<>();
            minX = 0.0f;
            minY = 0.0f;
            maxX = 0.0f;
            maxY = 0.0f;
            numData = 0;
            factor = 1.0;
        }
        ((MusicVisualizationPanel) drawingArea).clear();

        ((MusicVisualizationPanel) drawingArea).setCheckMarchingSquares(checkMarchingSquares.isSelected());
        ((MusicVisualizationPanel) drawingArea).setTypeDensity(comboDensity.getSelectedItem());

        ((MusicVisualizationPanel) drawingArea).setCheckLines(checkLines.isSelected());


        ((MusicVisualizationPanel) drawingArea).setTypePoint(comboPoints.getSelectedItem());

        ((MusicVisualizationPanel) drawingArea).setColorScale(comboColors.getSelectedItem());
        ((MusicVisualizationPanel) drawingArea).setPathsIcones(pathsIcones);



        /*
         * String thresholdOutlierStr = thresholdOutliers.getText();
         * this.thresholdOutlier = 1.0f; if
         * (thresholdOutlierStr.contains(",")) { thresholdOutlierStr =
         * thresholdOutlierStr.replace(",", ".");
         * thresholdOutliers.setText(thresholdOutlierStr); } try {
         * this.thresholdOutlier = Float.parseFloat(thresholdOutlierStr); if
         * (this.thresholdOutlier < 0.0f) { this.thresholdOutlier = 0.0f; }
         * if (this.thresholdOutlier > 1.0f) { this.thresholdOutlier = 1.0f;
         * }
         * thresholdOutliers.setText(Float.toString(this.thresholdOutlier));
         * } catch (Exception e1) { this.thresholdOutlier = 1.0f;
         * thresholdOutliers.setText(Float.toString(this.thresholdOutlier));
         * }
         */

        String radiusNNStr = radiusNNtext.getText();

        // this.radiusNN = 1.0f;
        if (radiusNNStr.contains(",")) {
            radiusNNStr = radiusNNStr.replace(",", ".");
            radiusNNtext.setText(radiusNNStr);
        }
        try {
            this.radiusNN = Float.parseFloat(radiusNNStr);
            if (this.radiusNN < 0.0f) {
                this.radiusNN = 0.0f;
            }
            /*
             * if (this.radiusNN > 1.0f) { this.radiusNN = 1.0f; }
             */
            radiusNNtext.setText(Float.toString(this.radiusNN));
        } catch (Exception e1) {
            this.radiusNN = 1.0f;
            radiusNNtext.setText(Float.toString(this.radiusNN));
        }

        ((MusicVisualizationPanel) drawingArea).setRadiusNN(radiusNN);

        Float threshold = 0.5f;
        String thresholdStr = thresholdField.getText();
        if (thresholdStr.contains(",")) {
            thresholdStr = thresholdStr.replace(",", ".");
            thresholdField.setText(thresholdStr);
        }
        try {
            threshold = Float.parseFloat(thresholdStr);
            if (threshold < 0.0f) {
                threshold = 0.0f;
            }
            if (threshold > 1.0f) {
                threshold = 1.0f;
            }
            thresholdField.setText(Float.toString(threshold));
        } catch (NumberFormatException e1) {
            threshold = 0.5f;
            thresholdField.setText(Float.toString(threshold));
        }

        ((MusicVisualizationPanel) drawingArea).setThresholdField(threshold);

        Integer fieldSize = 51;
        String fieldSizeSplatStr = fieldSizeSplat.getText();
        if (fieldSizeSplatStr.contains(",")) {
            fieldSizeSplatStr = fieldSizeSplatStr.replace(",", "");
            fieldSizeSplat.setText(fieldSizeSplatStr);
        }
        try {
            fieldSize = Integer.parseInt(fieldSizeSplatStr);
            if (fieldSize < 0) {
                fieldSize = 0;
            }
            fieldSizeSplat.setText(Integer.toString(fieldSize));
        } catch (NumberFormatException e1) {
            fieldSize = 51;
            fieldSizeSplat.setText(Integer.toString(fieldSize));
        }

        ((MusicVisualizationPanel) drawingArea).setfieldSizeSplat(fieldSize);

        Float sigma = 11.0f;
        String sigmaStr = splatSigmaField.getText();
        if (sigmaStr.contains(",")) {
            sigmaStr = sigmaStr.replace(",", ".");
            splatSigmaField.setText(sigmaStr);
        }
        try {
            sigma = Float.parseFloat(sigmaStr);
            if (sigma < 0.0f) {
                sigma = 0.0f;
            }

            splatSigmaField.setText(Float.toString(sigma));
        } catch (Exception e1) {
            sigma = 11.0f;
            splatSigmaField.setText(Float.toString(sigma));
        }

        ((MusicVisualizationPanel) drawingArea).setSplatSigmaField(sigma);
        if (numTitle == 0 && !option.equals("projection")) {
            if (fileName != null && (boolListX == true || boolListY == true)) {
                try {
                    uploadDataSet(fileName, columns1.getSelectedIndex(), columns2.getSelectedIndex());
                } catch (IOException ex) {
                    Logger.getLogger(MusicVisualization.class.getName()).log(Level.SEVERE, null, ex);
                }


            }

        } else {
            if (!option.equals("projection") && matrix != null) {
                try {
                    uploadDataSetFromMatrix(listPoints, columns1.getSelectedIndex(), columns2.getSelectedIndex());
                } catch (IOException ex) {
                    Logger.getLogger(MusicVisualization.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        //((DensityPanel) drawingArea).setNumberClusters(hashSetClusters.size());
        ((MusicVisualizationPanel) drawingArea).setLists(listPoints, nameX, nameY, minX, maxX, minY, maxY, numData, this.hashSetClusters, this.basicColor, this.listLabelMidi);


        this.checkLens = false;
        ((MusicVisualizationPanel) drawingArea).setCheckLens(checkLens);
        this.buttonLens.setBackground(new Color(238, 238, 238));

        this.checkLensSplatting = false;
        ((MusicVisualizationPanel) drawingArea).setCheckLensSplatting(checkLensSplatting);
        this.buttonLensSplatting.setBackground(new Color(238, 238, 238));


        this.checkZoomLocal = false;
        ((MusicVisualizationPanel) drawingArea).setCheckZoomLocal(checkZoomLocal);
        this.buttonZoomLocal.setBackground(new Color(238, 238, 238));

        this.checkLabel = false;
        ((MusicVisualizationPanel) drawingArea).setCheckLabel(checkLabel);
        this.buttonLabel.setBackground(new Color(238, 238, 238));

        this.checkNameSongsLabel = false;
        ((MusicVisualizationPanel) drawingArea).setCheckNameSongsLabel(checkNameSongsLabel);
        this.buttonNameSongsLabel.setBackground(new Color(238, 238, 238));

        this.buttonIconSummary.setBackground(new Color(238, 238, 238));
        ((MusicVisualizationPanel) drawingArea).setCheckIconSummary(false);
        checkIconSummary = false;

        ((MusicVisualizationPanel) drawingArea).setWithoutReDoing(false);

        drawingArea.repaint();
    }

    public void playTableMidisPlaylist() {
        this.currentSongTextArea.setText("");
        this.currentSongTextArea.revalidate();
        this.currentSongTextArea.repaint();

        this.iconeLabel.setIcon(null);
        this.iconeLabel.revalidate();
        this.iconeLabel.repaint();
        try {
            midiPlayer.stop();

        } catch (Exception e) {
        }
        try {
            int index = this.tableMidisPlaylist.getSelectedRow();
            if (index < 0) {
                index = 0;
                tableMidisPlaylist.getSelectionModel().setSelectionInterval(index, index);
            }
            File file = new File(this.listPathsPlayList.get(index));

            this.currentSongTextArea.setLineWrap(true);
            this.currentSongTextArea.setWrapStyleWord(true);
            this.currentSongTextArea.setText(this.listNamesPlayList.get(index));
            this.currentSongTextArea.setSize(250, 4);


            this.currentSongTextArea.revalidate();
            this.currentSongTextArea.repaint();



            try {
                ImageIcon imageIcon = new ImageIcon(pathsIcones + "/" + this.listLabelsPlayList.get(index) + ".png");
                Image img = imageIcon.getImage();
                this.iconeLabel.setIcon(new ImageIcon(this.getScaledImage(img, 150, 30)));
                this.iconeLabel.revalidate();
                this.iconeLabel.repaint();
            } catch (Exception e) {
                this.iconeLabel.setText("");
                this.iconeLabel.revalidate();
                this.iconeLabel.repaint();
            }

            midiPlayer.play(file);
        } catch (Exception e1) {
        }

    }

    private void fileOpenActionPerformed(java.awt.event.ActionEvent evt) {




        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle("Path of the icones");
        int returnVal2 = fileChooser.showOpenDialog(this);



        if (returnVal2 == JFileChooser.APPROVE_OPTION) {
            try {


                File fileNameChooser = fileChooser.getSelectedFile();
                String directoryString = fileNameChooser.toString();
                this.pathsIcones = directoryString;


                fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setDialogTitle("Path of the playlists");
                int returnVal3 = fileChooser.showOpenDialog(this);


                if (returnVal3 == JFileChooser.APPROVE_OPTION) {
                    try {


                        fileNameChooser = fileChooser.getSelectedFile();
                        directoryString = fileNameChooser.toString();
                        this.pathsPlaylists = directoryString;
                        this.listPlayList = getListPlaylist();
                        strPlayList = new String[listPlayList.size()];
                        listPlayList.toArray(strPlayList);

                        fileChooser = new JFileChooser();
                        ExtensionFileFilter filter = new ExtensionFileFilter("LABEL and label", new String[]{"LABEL", "label"});
                        fileChooser.setFileFilter(filter);
                        fileChooser.setDialogTitle("Path of the label file");
                        int returnVal1 = fileChooser.showOpenDialog(this);

                        if (returnVal1 == JFileChooser.APPROVE_OPTION) {
                            try {
                                listLabelMidi = null;

                                fileNameChooser = fileChooser.getSelectedFile();
                                this.pathsLabel = fileNameChooser.toString();
                                loadLabelMidi(this.pathsLabel);

                                //setVisible(true);


                                fileChooser = new JFileChooser();
                                filter = new ExtensionFileFilter("DATA and data", new String[]{"DATA", "data"});
                                fileChooser.setFileFilter(filter);
                                fileChooser.setDialogTitle("Path of the data file");
                                int returnVal = fileChooser.showOpenDialog(this);
                                if (returnVal == JFileChooser.APPROVE_OPTION) {
                                    try {
                                        this.fileName = null;

                                        fileNameChooser = fileChooser.getSelectedFile();
                                        this.fileName = fileNameChooser.toString();
                                        loadDrawPanel();
                                        setVisible(true);


                                    } catch (Exception e1) {
                                        JOptionPane.showMessageDialog(null, "This file is not a correct database.");
                                        loadDrawPanelError();
                                        setVisible(true);
                                    }

                                }


                            } catch (Exception e1) {
                                JOptionPane.showMessageDialog(null, "This file is not a correct label midi.");
                                // setVisible(true);
                            }
                        }


                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, "This file is not a directory.");
                        // setVisible(true);
                    }
                }


            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "This file is not a directory.");
                // setVisible(true);
            }
        }





        if (drawingArea != null) {

            ((MusicVisualizationPanel) drawingArea).setWithoutReDoing(false);
            ((MusicVisualizationPanel) drawingArea).repaint();
        }
    }

    private void fileCloseActionPerformed(java.awt.event.ActionEvent evt) {
        if (this.numTitle.equals(0) && !option.equals("projection")) {
            try {
                midiPlayer.stop();
            } catch (Exception e) {
            }
            System.exit(0);
        } else {
            try {
                midiPlayer.stop();
            } catch (Exception e) {
            }
            this.hide();
        }
    }

    private void exportItemPNGActionPerformed(java.awt.event.ActionEvent evt) {
        if (((MusicVisualizationPanel) drawingArea) != null) {
            try {
                PropertiesManager spm = PropertiesManager.getInstance(ProjectionConstants.PROPFILENAME);
                int result = SaveDialog.showSaveDialog(spm, new PNGFilter(), this, "image.png");

                if (result == JFileChooser.APPROVE_OPTION) {
                    String filename = SaveDialog.getFilename();

                    try {
                        this.saveToPngImageFile(filename);
                    } catch (IOException e) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
                        JOptionPane.showMessageDialog(this, e.getMessage(),
                                "Problems saving the file", JOptionPane.ERROR_MESSAGE);




                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(ProjectionFrame.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nothing to save.");
        }
    }

    public void saveToPngImageFile(String filename) throws IOException {
        try {

            if (((MusicVisualizationPanel) drawingArea) != null) {
                BufferedImage buffer = ((MusicVisualizationPanel) drawingArea).getImage();
                ImageIO.write(buffer, "png", new File(filename));
            } else {
                JOptionPane.showMessageDialog(null, "Nothing to save.");
            }
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void exportItem2DPointsActionPerformed(java.awt.event.ActionEvent evt) {
        if (listPoints != null) {
            if (listPoints.size() > 0) {
                try {
                    PropertiesManager spm = PropertiesManager.getInstance(ProjectionConstants.PROPFILENAME);
                    int result = SaveDialog.showSaveDialog(spm, new DATAFilter(), this, "projection.data");

                    if (result == JFileChooser.APPROVE_OPTION) {
                        String filename = SaveDialog.getFilename();

                        try {
                            FileWriter fstreamOut = new FileWriter(filename);
                            BufferedWriter out = new BufferedWriter(fstreamOut);
                            boolean existCluster = false;


                            if (hashSetClusters.size() > 1) {
                                out.write("DY\n");
                                existCluster = true;
                            } else {
                                out.write("DN\n");
                            }
                            out.write(String.valueOf(numData) + '\n');
                            if (existCluster) {
                                out.write("3\n");
                            } else {
                                out.write("2\n");
                            }

                            if (existCluster) {
                                out.write("x;y;c\n");
                            } else {
                                out.write("x;y\n");
                            }

                            for (int i = 0; i < listPoints.size(); i++) {

                                out.write(listPoints.get(i).getLabel() + ";" + String.valueOf(listPoints.get(i).getX()) + ";"
                                        + String.valueOf(listPoints.get(i).getY()));

                                if (existCluster) {
                                    out.write(";" + String.valueOf(listPoints.get(i).getCluster()));
                                }
                                out.write('\n');
                            }
                            out.close();
                        } catch (IOException e) {
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
                            JOptionPane.showMessageDialog(this, e.getMessage(),
                                    "Problems saving the file", JOptionPane.ERROR_MESSAGE);




                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ProjectionFrame.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nothing to save.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nothing to save.");
        }
    }

    private void addSelection(java.awt.event.ActionEvent evt) {
        int[] selectedRows = this.tableMidis.getSelectedRows();
        for (int i = 0; i < selectedRows.length; i++) {
            this.listPathsPlayList.add(this.listPaths.get(selectedRows[i]));
            this.listNamesPlayList.add(this.listNames.get(selectedRows[i]));
            this.listLabelsPlayList.add(this.listLabels.get(selectedRows[i]));
        }
        DefaultTableModel aModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        aModel.addColumn("PlayList", listNamesPlayList.toArray());

        tableMidisPlaylist.setModel(aModel);
        this.tableMidisPlaylist.revalidate();
    }

    private void addAll(java.awt.event.ActionEvent evt) {
        for (int i = 0; i < this.listPaths.size(); i++) {
            this.listPathsPlayList.add(this.listPaths.get(i));
            this.listNamesPlayList.add(this.listNames.get(i));
            this.listLabelsPlayList.add(this.listLabels.get(i));
        }
        DefaultTableModel aModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        aModel.addColumn("PlayList", listNamesPlayList.toArray());

        tableMidisPlaylist.setModel(aModel);
        this.tableMidisPlaylist.revalidate();

    }

    private void changePlayList(java.awt.event.ActionEvent evt) {
        this.newPlayListField.setText("");
        JComboBox cb = (JComboBox) evt.getSource();
        String namePlayList = (String) cb.getSelectedItem();
        Integer indexPlayList;
        indexPlayList = (Integer) cb.getSelectedIndex();

        this.listLabelsPlayList = new ArrayList<>();
        this.listNamesPlayList = new ArrayList<>();
        this.listPathsPlayList = new ArrayList<>();
        if (!indexPlayList.equals(0)) {
            this.newPlayListField.setText(namePlayList);
            this.newPlayListField.revalidate();
            FileInputStream fstream = null;
            try {
                String pathPlayList = pathsPlaylists + "/" + namePlayList;
                fstream = new FileInputStream(pathPlayList);
                DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;
                int count = 0;
                while ((strLine = in.readLine()) != null) {
                    String[] data = strLine.split(";");

                    String nameData = data[1];
                    while (nameData.contains("/")) {
                        nameData = nameData.substring(nameData.indexOf("/") + 1);
                    }

                    nameData = nameData.substring(0, nameData.lastIndexOf("."));
                    nameData = nameData.replaceAll("/", "-");
                    this.listLabelsPlayList.add(data[0].trim());
                    this.listPathsPlayList.add(data[1].trim());
                    this.listNamesPlayList.add(nameData);

                }
            } catch (Exception ex) {
            }

        }
        DefaultTableModel aModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        aModel.addColumn("PlayList", listNamesPlayList.toArray());

        tableMidisPlaylist.setModel(aModel);
        this.tableMidisPlaylist.revalidate();

    }

    public void clearSelectedTableMidisPlaylist() {
        this.tableMidisPlaylist.clearSelection();
        this.tableMidisPlaylist.revalidate();

    }

    public void clearSelectedTableMidis() {
        this.tableMidis.clearSelection();
        this.tableMidis.revalidate();

    }

    private void savePlaylist(java.awt.event.ActionEvent evt) {
        String name = this.newPlayListField.getText();
        if (name == null || name.equals("") || name.equals("Select...")) {
            JOptionPane.showMessageDialog(null, "Enter a name for the playlist.");
        } else {
            name = pathsPlaylists + "/" + name;
            if (!name.contains(".lst")) {
                name = name + ".lst";
            }

            FileWriter fData;
            try {
                fData = new FileWriter(name);
                BufferedWriter outData = new BufferedWriter(fData);
                for (int k = 0; k < listPathsPlayList.size(); k++) {
                    outData.write(listLabelsPlayList.get(k) + "; " + listPathsPlayList.get(k) + "\n");
                }
                outData.close();
                JOptionPane.showMessageDialog(null, "Successfully saved.");
            } catch (IOException ex) {
            }
            this.listPlayList = getListPlaylist();
            strPlayList = new String[listPlayList.size()];
            listPlayList.toArray(strPlayList);


            //this.comboPlayList = new JComboBox();
            DefaultComboBoxModel model = new DefaultComboBoxModel(strPlayList);
            this.comboPlayList.removeAllItems();
            this.comboPlayList.setModel(model);
            this.comboPlayList.revalidate();
            this.comboPlayList.repaint();

        }



    }

    public List<String> getListPlaylist() {

        List<String> names = new ArrayList<>();
        names.add("Select...");
        try {
            File dir = new File(pathsPlaylists + "/");

            File[] archivos = dir.listFiles();
            if (archivos != null) {
                for (int i = 0; i < archivos.length; i++) {
                    if (!archivos[i].isDirectory()) {
                        String lista = archivos[i].getName().substring(archivos[i].getName().lastIndexOf(".") + 1);
                        if (lista.equals("lst") || lista.equals("lst")) {
                            names.add(archivos[i].getName());
                        }
                    }
                }
            }
            Collections.sort(names);
        } catch (Exception e) {
        }
        return names;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (this.checkLabel == true) {
            this.label = ((MusicVisualizationPanel) drawingArea).getLabelInstance();
            drawingArea.setToolTipText(this.label);
        } else {
            if (this.checkNameSongsLabel == true) {
                this.label = ((MusicVisualizationPanel) drawingArea).getLabelInstance();
                drawingArea.setToolTipText(this.label);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.checkIconSummary == true) {

            Integer numberInstances = ((MusicVisualizationPanel) drawingArea).getListSelectedPointsIconSummary().size();
            message = String.valueOf(numberInstances) + " instances.";
            textAreaMessage.setText(message);

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (this.checkZoomLocal == true) {
            List<PointDragged> pointsRectangle = ((MusicVisualizationPanel) drawingArea).getPointsRectangleOriginals();
            Integer numberInstances = ((MusicVisualizationPanel) drawingArea).getCountOfPointsInRectangle(pointsRectangle);
            message = String.valueOf(numberInstances) + " instances.";
            textAreaMessage.setText(message);

        }


    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void stateChanged(ChangeEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (this.numTitle.equals(0) && !option.equals("projection")) {
            try {
                midiPlayer.stop();
            } catch (Exception ee) {
            }
            System.exit(0);
        } else {
            try {
                midiPlayer.stop();
            } catch (Exception ee1) {
            }
            this.hide();
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {

        if (drawingArea != null) {
            ((MusicVisualizationPanel) drawingArea).setWithoutReDoing(true);
            ((MusicVisualizationPanel) drawingArea).repaint();
            if (flagLoad.equals(false)) {
                drawDrawingPanel();
                flagLoad = true;
            }
        }
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        if (drawingArea != null) {
            ((MusicVisualizationPanel) drawingArea).setWithoutReDoing(true);
            ((MusicVisualizationPanel) drawingArea).repaint();
        }
    }

    private BufferedImage getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if (drawingArea != null) {
            this.checkLens = false;
            ((MusicVisualizationPanel) drawingArea).setCheckLens(checkLens);
            this.buttonLens.setBackground(new Color(238, 238, 238));

            this.checkLensSplatting = false;
            ((MusicVisualizationPanel) drawingArea).setCheckLensSplatting(checkLensSplatting);
            this.buttonLensSplatting.setBackground(new Color(238, 238, 238));


            this.checkZoomLocal = false;
            ((MusicVisualizationPanel) drawingArea).setCheckZoomLocal(checkZoomLocal);
            this.buttonZoomLocal.setBackground(new Color(238, 238, 238));

            this.checkLabel = false;
            ((MusicVisualizationPanel) drawingArea).setCheckLabel(checkLabel);
            this.buttonLabel.setBackground(new Color(238, 238, 238));

            this.buttonNameSongsLabel.setBackground(new Color(238, 238, 238));
            ((MusicVisualizationPanel) drawingArea).setCheckNameSongsLabel(false);
            checkNameSongsLabel = false;

            this.buttonIconSummary.setBackground(new Color(238, 238, 238));
            ((MusicVisualizationPanel) drawingArea).setCheckIconSummary(false);
            checkIconSummary = false;

            ((MusicVisualizationPanel) drawingArea).setWithoutReDoing(false);
            int height = this.getHeight(), width = this.getWidth();
            if (this.getHeight() < 630) {
                height = 630;

            }
            if (this.getWidth() < 623) {
                width = 623;

            }
            this.setSize(width, height);
            ((MusicVisualizationPanel) drawingArea).setWithoutReDoing(false);
            ((MusicVisualizationPanel) drawingArea).repaint();
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {

        if (drawingArea != null) {
            ((MusicVisualizationPanel) drawingArea).setWithoutReDoing(true);
            ((MusicVisualizationPanel) drawingArea).repaint();
        }
    }

    @Override
    public void componentShown(ComponentEvent e) {
        if (drawingArea != null) {
            ((MusicVisualizationPanel) drawingArea).setWithoutReDoing(false);
            ((MusicVisualizationPanel) drawingArea).repaint();
        }

    }

    @Override
    public void componentHidden(ComponentEvent e) {
        if (drawingArea != null) {
            ((MusicVisualizationPanel) drawingArea).setWithoutReDoing(true);
            ((MusicVisualizationPanel) drawingArea).repaint();
        }

    }
}
