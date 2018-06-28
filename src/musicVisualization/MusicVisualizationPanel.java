/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicVisualization;

/**
 *
 * @author asoriano
 */
import borders.MarchingSquares;
import borders.Path;
import colors.*;
import densityMethods.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import structures.AABSNodeIconSummary;
import structures.AABSTreeIconSummary;
import structures.CellMatrixIconSummary;
import structures.Connection;
import structures.InstancePoint;
import structures.LabelMidi;
import structures.PointDragged;

public class MusicVisualizationPanel extends JPanel implements MouseListener, MouseWheelListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    Color[] rgb;
    boolean zoom;
    boolean dragging;
    double offsetX, offsetY;
    double factor;
    Integer fieldSize;
    Integer numData;
    Integer zoomLens;
    Integer widthPanel;
    Integer heightPanel;
    Integer valueBrightness;
    HashSet<Float> hashSetClusters;
    Float radiusNN;
    Float maxX;
    Float maxY;
    Float minX;
    Float minY;
    Float threshold;
    Float sigma;
    String timeString;
    String colorScale;
    String nameX;
    String nameY;
    Shape rectangle;
    Point startDragPoint;
    Point endDragPoint;
    PointDragged startPointRectangle;
    PointDragged endPointRectangle;
    Point currentPoint;
    Point2D point2D;
    Point2D pointDragged2D;
    BufferedImage image;
    BufferedImage imagePrevious;
    BufferedImage imageScaled;
    BufferedImage lensImage;
    BufferedImage lensSplattingImage;
    AffineTransform tx = new AffineTransform();
    List<InstancePoint> listPoints;
    List<List<Float>> colorMap;
    List<Path> marchingSquaresPaths;
    boolean checkMarchingSquares;
    boolean checkSplatting;
    boolean checkTriangularSplatting;
    boolean checkGaussianBlur;
    boolean checkTriangularBlur;
    boolean checkWelchSpklatting;
    boolean checkApproximateSplatting1;
    boolean checkApproximateSplatting2;
    boolean checkApproximateSplatting3;
    boolean checkSplattingWithNN;
    boolean checkPointsBorder;
    boolean checkPointsFilled;
    boolean checkPointsTransparency;
    boolean checkPointsIcones;
    boolean checkPointsGraph;
    boolean checkLines;
    boolean checkZoomOriginal;
    boolean checkZoomPrevious;
    boolean checkLens;
    boolean checkLensSplatting;
    boolean checkZoomLocal;
    boolean checkLabel;
    boolean checkNameSongsLabelLabel;
    boolean checkWithoutReDoing;
    boolean checkBrightness;
    boolean checkIconSummary;
    BufferedImage imageDensity;
    List<List<Integer>> countMap;
    List<List<String>> labelMap;
    List<List<String>> nameSongsLabelMap;
    List<LabelMidi> listLabelMidi;
    List<Connection> listConnections;
    String labelInstance = "";
    String pathsIcones = "";
    Color basicColor;
    Integer widthTable = 10;
    Integer heightTable = 10;
    String formatIcon = "";
    Integer sizeWidthCell;
    Integer sizeHeightCell;
    AABSTreeIconSummary tree;
    ArrayList<ArrayList<CellMatrixIconSummary>> matrixIconSummary;
    BufferedImage imageIconSummaryPrevious;
    AABSNodeIconSummary cellBoundingSquare;
    boolean checkClickScreenIconSummary;
    boolean checkDrawGridScreenIconSummary;
    List<InstancePoint> listSelectedPointsIconSummary;

    public void setLists(List<InstancePoint> listPoints, String nameX,
            String nameY, Float minX, Float maxX, Float minY, Float maxY, Integer numData, HashSet<Float> hashSetClusters, Color basicColor, List<LabelMidi> listLabelMidi) {
        this.listLabelMidi = listLabelMidi;
        this.valueBrightness = 50;
        this.timeString = "";
        this.addMouseWheelListener(this);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);

        this.listPoints = new ArrayList<>(listPoints);
        this.nameX = nameX;
        this.nameY = nameY;
        colorMap = new ArrayList<>();
        this.hashSetClusters = hashSetClusters;

        marchingSquaresPaths = new ArrayList<>();
        checkWithoutReDoing = false;
        this.maxX = maxX;
        this.minX = minX;
        this.maxY = maxY;
        this.minY = minY;
        this.numData = numData;

        setBackground(Color.WHITE);
        factor = 0.0;
        Dimension size = new Dimension(this.getWidth(), this.getHeight());
        this.setPreferredSize(size);
        zoom = false;
        dragging = false;
        try {
            lensImage = ImageIO.read(getClass().getResourceAsStream("/images/lupa.png"));

        } catch (IOException ex) {
        }

        try {
            lensSplattingImage = ImageIO.read(getClass().getResourceAsStream("/images/lensRectangle.png"));

        } catch (IOException ex) {
        }

        zoomLens = 2;
        if (basicColor != null) {
            this.basicColor = basicColor;
        }

        listSelectedPointsIconSummary = new ArrayList<>();
    }

    public void clear() {
        this.timeString = "";
        this.listPoints = new ArrayList<>();
        this.nameX = "";
        this.nameY = "";
        this.colorMap = new ArrayList<>();
        this.marchingSquaresPaths = new ArrayList<>();
        this.checkMarchingSquares = false;

        this.checkTriangularSplatting = false;
        this.checkSplatting = false;
        this.checkGaussianBlur = false;
        this.checkTriangularBlur = false;
        this.checkWelchSpklatting = false;
        this.checkLines = false;
        this.checkPointsFilled = true;
        this.checkPointsIcones = false;
        this.checkPointsBorder = false;
        this.checkPointsTransparency = false;
        this.checkPointsGraph = false;
        this.checkSplattingWithNN = false;
        this.checkZoomOriginal = false;
        this.checkZoomPrevious = false;
        this.checkLens = false;
        this.checkLensSplatting = false;
        this.checkWithoutReDoing = false;
        this.checkIconSummary = false;
        this.maxX = 0.f;
        this.maxY = 0.f;
        this.minX = 0.f;
        this.minY = 0.f;
        this.threshold = 0.2f;
        this.sigma = 11f;
        this.fieldSize = 51;
        this.radiusNN = 1.0f;
        this.zoomLens = 2;
        this.hashSetClusters = new HashSet<>();
        this.checkClickScreenIconSummary = false;
        this.checkDrawGridScreenIconSummary = false;
        listSelectedPointsIconSummary = new ArrayList<>();
        repaint();
    }

    public MusicVisualizationPanel(List<InstancePoint> listPoints, String nameX,
            String nameY, Float minX, Float maxX, Float minY, Float maxY, Integer numData, HashSet<Float> hashSetClusters, Color basicColor, List<LabelMidi> listLabelMidi) {
        this.listLabelMidi = listLabelMidi;
        this.valueBrightness = 50;
        this.timeString = "";
        this.addMouseWheelListener(this);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.listPoints = new ArrayList<>(listPoints);
        colorMap = new ArrayList<>();
        marchingSquaresPaths = new ArrayList<>();
        checkMarchingSquares = false;
        checkLines = false;
        checkPointsFilled = true;
        checkPointsBorder = false;
        checkPointsGraph = false;
        checkPointsIcones = false;
        checkPointsTransparency = false;
        checkLens = false;
        checkLensSplatting = false;
        checkWithoutReDoing = false;
        checkIconSummary = false;
        this.hashSetClusters = hashSetClusters;

        this.nameX = nameX;
        this.nameY = nameY;

        this.maxX = maxX;
        this.minX = minX;
        this.maxY = maxY;
        this.minY = minY;
        this.numData = numData;

        threshold = 0.2f;
        sigma = 11f;
        fieldSize = 51;
        radiusNN = 1.0f;
        factor = 1.0;
        setBackground(Color.WHITE);
        Dimension size = new Dimension(this.getWidth(), this.getHeight());
        this.setPreferredSize(size);
        zoom = false;
        dragging = false;

        try {
            //File myFile = new File("images/lupa.png");
            //lensImage = ImageIO.read(myFile.toURI().toURL());

            lensImage = ImageIO.read(getClass().getResourceAsStream("/images/lupa.png"));

        } catch (IOException ex) {
        }

        try {
            //File myFile = new File("images/lupa.png");
            //lensImage = ImageIO.read(myFile.toURI().toURL());
            lensSplattingImage = ImageIO.read(getClass().getResourceAsStream("/images/lensRectangle.png"));
        } catch (IOException ex) {
        }

        zoomLens = 2;
        if (basicColor != null) {
            this.basicColor = basicColor;
        }
        checkClickScreenIconSummary = false;
        checkDrawGridScreenIconSummary = false;
    }

    private void setSetColor() {
        rgb = new Color[256];
        if (colorScale == null || colorScale.equals("")) {
            Aurea colorAurea = new Aurea();
            rgb = colorAurea.rgb;

        } else {
            if (colorScale.equals("Rainbow")) {
                Rainbow colorRainbow = new Rainbow();
                rgb = colorRainbow.rgb;
            } else {
                if (colorScale.equals("Gray")) {
                    Gray colorGray = new Gray();
                    rgb = colorGray.rgb;
                } else {
                    if (colorScale.equals("BTC")) {
                        BTC colorBTC = new BTC();
                        rgb = colorBTC.rgb;
                    } else {
                        if (colorScale.equals("HeatedObject")) {
                            HeatedObject colorHeatedObject = new HeatedObject();
                            rgb = colorHeatedObject.rgb;
                        } else {
                            if (colorScale.equals("LinGray")) {
                                LinGray colorLinGray = new LinGray();
                                rgb = colorLinGray.rgb;
                            } else {
                                if (colorScale.equals("LOCS")) {
                                    LOCS colorLOCS = new LOCS();
                                    rgb = colorLOCS.rgb;
                                } else {
                                    if (colorScale.equals("Magenta")) {
                                        Magenta colorMagenta = new Magenta();
                                        rgb = colorMagenta.rgb;
                                    } else {
                                        if (colorScale.equals("OCS")) {
                                            OCS colorOCS = new OCS();
                                            rgb = colorOCS.rgb;
                                        } else {
                                            if (colorScale.equals("BTY")) {
                                                BTY colorBTY = new BTY();
                                                rgb = colorBTY.rgb;
                                            } else {
                                                if (colorScale.equals("PseudoRainbow")) {
                                                    PseudoRainbow colorPseudoRainbow = new PseudoRainbow();
                                                    rgb = colorPseudoRainbow.rgb;
                                                } else {
                                                    if (colorScale.equals("GTW")) {
                                                        GTW colorGTW = new GTW();
                                                        rgb = colorGTW.rgb;
                                                    } else {
                                                        if (colorScale.equals("CategoryScale")) {
                                                            CategoryScale colorCategoryScale = new CategoryScale();
                                                            rgb = colorCategoryScale.rgb;
                                                        } else {
                                                            if (colorScale.equals("Aurea")) {
                                                                Aurea colorAurea = new Aurea();
                                                                rgb = colorAurea.rgb;
                                                            } else {
                                                                if (colorScale.equals("ColorPaperJazz")) {
                                                                    ColorPaperJazz colorPaperJazz = new ColorPaperJazz();
                                                                    rgb = colorPaperJazz.rgb;
                                                                }
                                                            }
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    private void drawDensity(Graphics2D graphic) {
        long start = System.nanoTime();

        setSetColor();
        for (int i = 0; i < colorMap.size(); i++) {
            for (int j = 0; j < colorMap.get(i).size(); j++) {
                Float color = colorMap.get(i).get(j);
                int roundedColor = Math.round(color * 255);
                graphic.setPaint(rgb[roundedColor]);
                graphic.drawString(".", i, j);

            }

        }
        long end = System.nanoTime();
        System.out.println("Tempo de desenhar densidade: " + (double) (end - start) / 1000000000.0 + " secs.");

    }

    public void getCountMap() {
        List<List<Integer>> countMap1 = new ArrayList<>(this.getWidth());
        for (Integer i = 0; i < this.getWidth(); i++) {
            countMap1.add(i, new ArrayList<Integer>(this.getHeight()));
            for (Integer j = 0; j < this.getHeight(); j++) {
                countMap1.get(i).add(j, 0);
            }
        }

        Integer x, y;
        for (int i = 0; i < listPoints.size(); i++) {

            x = Math.round(((listPoints.get(i).getX() - minX) * (float) (this.getWidth() - 60) / (maxX - minX)) + 30);
            y = Math.round(((listPoints.get(i).getY() - minY) * (float) (this.getHeight() - 60) / (maxY - minY)) + 30);
            y = this.getHeight() - y;
            int value = countMap1.get(x).get(y) + 1;
            countMap1.get(x).set(y, value);
        }
        countMap = countMap1;
    }

    public void getLabelMap() {
        List<List<String>> labelMap1 = new ArrayList<>(this.getWidth());
        for (Integer i = 0; i < this.getWidth(); i++) {
            labelMap1.add(i, new ArrayList<String>(this.getHeight()));
            for (Integer j = 0; j < this.getHeight(); j++) {
                labelMap1.get(i).add(j, "");
            }
        }

        Integer x, y;
        for (int i = 0; i < listPoints.size(); i++) {

            x = Math.round(((listPoints.get(i).getX() - minX) * (float) (this.getWidth() - 60) / (maxX - minX)) + 30);
            y = Math.round(((listPoints.get(i).getY() - minY) * (float) (this.getHeight() - 60) / (maxY - minY)) + 30);
            y = this.getHeight() - y;
            String value = labelMap1.get(x).get(y);
            if (!value.equals("")) {
                value = value + " / " + String.valueOf(listPoints.get(i).getLabel());
            } else {
                value = String.valueOf(listPoints.get(i).getLabel());
            }
            //System.out.println(x + " - " + y + " - " + value);
            labelMap1.get(x).set(y, value);
        }

        labelMap = labelMap1;
    }

    public String getNameMidiByLabel(String label) {
        for (int i = 0; i < this.listLabelMidi.size(); i++) {
            if (this.listLabelMidi.get(i).getName().equals(label)) {
                String name = this.listLabelMidi.get(i).getPath();
                String strName = name.substring(name.lastIndexOf("/") + 1, name.lastIndexOf("."));
                return strName;
            }
        }
        return null;
    }

    public void getNameSongsLabelMap() {
        if (this.listLabelMidi != null && this.listLabelMidi.size() > 0) {
            List<List<String>> labelMap1 = new ArrayList<>(this.getWidth());
            for (Integer i = 0; i < this.getWidth(); i++) {
                labelMap1.add(i, new ArrayList<String>(this.getHeight()));
                for (Integer j = 0; j < this.getHeight(); j++) {
                    labelMap1.get(i).add(j, "");
                }
            }

            Integer x, y;
            for (int i = 0; i < listPoints.size(); i++) {

                x = Math.round(((listPoints.get(i).getX() - minX) * (float) (this.getWidth() - 60) / (maxX - minX)) + 30);
                y = Math.round(((listPoints.get(i).getY() - minY) * (float) (this.getHeight() - 60) / (maxY - minY)) + 30);
                y = this.getHeight() - y;
                String value = labelMap1.get(x).get(y);
                if (!value.equals("")) {
                    value = value + " / " + String.valueOf(getNameMidiByLabel(listPoints.get(i).getLabel()));
                } else {
                    value = String.valueOf(getNameMidiByLabel(listPoints.get(i).getLabel()));
                }
                //System.out.println(x + " - " + y + " - " + value);
                labelMap1.get(x).set(y, value);
            }

            nameSongsLabelMap = labelMap1;
        }
    }

    private void drawPoints(Graphics2D graphic) {
        Float x, y;

        Float maxClusters = Collections.max(hashSetClusters);
        Float minClusters = Collections.min(hashSetClusters);

        if (checkPointsGraph == true) {
            Comparator<InstancePoint> comparator = new Comparator<InstancePoint>() {
                @Override
                public int compare(InstancePoint ip1, InstancePoint ip2) {
                    return ip1.getLabel().compareTo(ip2.getLabel());
                }
            };
            for (int iconn = 0; iconn < listConnections.size(); iconn++) {
                int indexInstancePointX = Collections.binarySearch(listPoints, new InstancePoint(null, null, listConnections.get(iconn).getX(), null), comparator);
                int indexInstancePointY = Collections.binarySearch(listPoints, new InstancePoint(null, null, listConnections.get(iconn).getY(), null), comparator);

                if (indexInstancePointX > -1 && indexInstancePointY > -1) {

                    int xPos1 = Math.round(((listPoints.get(indexInstancePointX).getX() - minX) * (float) (this.getWidth() - 60) / (maxX - minX)) + 30);
                    int yPos1 = Math.round(((listPoints.get(indexInstancePointX).getY() - minY) * (float) (this.getHeight() - 60) / (maxY - minY)) + 30);
                    yPos1 = this.getHeight() - yPos1;

                    int xPos2 = Math.round(((listPoints.get(indexInstancePointY).getX() - minX) * (float) (this.getWidth() - 60) / (maxX - minX)) + 30);
                    int yPos2 = Math.round(((listPoints.get(indexInstancePointY).getY() - minY) * (float) (this.getHeight() - 60) / (maxY - minY)) + 30);
                    yPos2 = this.getHeight() - yPos2;

                    graphic.setPaint(Color.BLACK);
                    graphic.drawLine(xPos1, yPos1, xPos2, yPos2);
                }
            }

        }
        for (int i = 0; i < listPoints.size(); i++) {
            x = ((listPoints.get(i).getX() - minX) * (float) (this.getWidth() - 60) / (maxX - minX)) + 30;
            y = ((listPoints.get(i).getY() - minY) * (float) (this.getHeight() - 60) / (maxY - minY)) + 30;
            y = this.getHeight() - y;
            Float clusterPoint = listPoints.get(i).getCluster();
            Integer colorPoint = 0;
            Integer hitCount = 235 / hashSetClusters.size();

            if (hashSetClusters.size() > 1) {
                colorPoint = Math.round(((clusterPoint - minClusters) * (245 - 10)) / (maxClusters - minClusters)) + 10;
//                colorPoint = Math.round(hitCount *(clusterPoint-1) )+10;

            }

            int sizeOval = 0;
            if ((this.getWidth() - 30) < (this.getHeight() - 30)) {
                sizeOval = (this.getWidth() - 30) / 90; //100;
            } else {
                sizeOval = (this.getHeight() - 30) / 90;// 100;
            }
            if (checkPointsBorder == true) {
                if (colorPoint == 0) {
                    if (basicColor != null) {
                        graphic.setPaint(basicColor);
                    } else {
                        graphic.setPaint(rgb[127]);
                    }
                } else {
                    graphic.setPaint(rgb[colorPoint]);
                }
                graphic.drawOval(Math.round(x), Math.round(y - 2), sizeOval,
                        sizeOval);
            } else {
                if (checkPointsFilled == true) {
                    //graphic.setPaint(Color.DARK_GRAY);
                    if (colorPoint == 0) {
                        if (basicColor != null) {
                            graphic.setPaint(basicColor);
                        } else {
                            graphic.setPaint(rgb[127]);
                        }
                    } else {
                        graphic.setPaint(rgb[colorPoint]);
                    }
                    graphic.fillOval(Math.round(x), Math.round(y - 2), sizeOval,
                            sizeOval);
                    graphic.setPaint(Color.BLACK);
                    graphic.drawOval(Math.round(x), Math.round(y - 2), sizeOval,
                            sizeOval);
                } else {
                    if (checkPointsTransparency == true) {
                        float alpha = 0.2f;
                        Composite originalComposite = graphic.getComposite();
                        graphic.setComposite(makeComposite(alpha));
                        if (colorPoint == 0) {
                            if (basicColor != null) {
                                graphic.setPaint(basicColor);
                            } else {
                                graphic.setPaint(rgb[127]);
                            }
                        } else {
                            graphic.setPaint(rgb[colorPoint]);
                        }

                        graphic.fillOval(Math.round(x), Math.round(y - 2), sizeOval,
                                sizeOval);
                        graphic.setComposite(originalComposite);

                    } else {
                        if (checkPointsIcones == true) {
                            try {
                                // String labelPoint = getLabelByInstance(Integer.valueOf(listPoints.get(i).getX()) ,Integer.valueOf(listPoints.get(i).getY()));
                                String labelPoint = listPoints.get(i).getLabel();

                                Image imageConvert = ImageIO.read(new File(pathsIcones + "/" + labelPoint + ".png"));
                                BufferedImage buffered = (BufferedImage) imageConvert;
                                graphic.drawImage(buffered, Math.round(x), Math.round(y - 2), 32, 16, this);

                            } catch (IOException ex) {
                            }
                        } else {
                            if (checkPointsGraph == true) {

                                if (colorPoint == 0) {
                                    if (basicColor != null) {
                                        graphic.setPaint(basicColor);
                                    } else {
                                        graphic.setPaint(rgb[127]);
                                    }
                                } else {
                                    graphic.setPaint(rgb[colorPoint]);
                                }
                                graphic.fillOval(Math.round(x), Math.round(y - 2), sizeOval * 2,
                                        sizeOval * 2);
                                graphic.setPaint(Color.BLACK);
                                graphic.drawOval(Math.round(x), Math.round(y - 2), sizeOval * 2,
                                        sizeOval * 2);
                            }
                        }
                    }
                }
            }
        }
    }

    private void drawAxisIconSummary(Graphics2D graphic) {
        graphic.setPaint(Color.BLUE);

        // axe x
        graphic.draw(new Line2D.Double(tree.getRoot().getX0(), tree.getRoot().getY0(), tree.getRoot().getX1(), tree.getRoot().getY0()));
        graphic.draw(new Line2D.Double(tree.getRoot().getX0(), tree.getRoot().getY1(), tree.getRoot().getX1(), tree.getRoot().getY1()));

        // axe y
        graphic.draw(new Line2D.Double(tree.getRoot().getX0(), tree.getRoot().getY0(), tree.getRoot().getX0(), tree.getRoot().getY1()));
        graphic.draw(new Line2D.Double(tree.getRoot().getX1(), tree.getRoot().getY0(), tree.getRoot().getX1(), tree.getRoot().getY1()));

        for (int i = 0; i < this.widthTable; i++) {
            graphic.draw(new Line2D.Double(tree.getRoot().getX0() + (sizeWidthCell * i), tree.getRoot().getY0(), tree.getRoot().getX0() + (sizeWidthCell * i), tree.getRoot().getY1()));

        }

        for (int i = 0; i < this.heightTable; i++) {
            graphic.draw(new Line2D.Double(tree.getRoot().getX0(), tree.getRoot().getY0() - (sizeHeightCell * i), tree.getRoot().getX1(), tree.getRoot().getY0() - (sizeHeightCell * i)));

        }

    }

    private void drawAxis(Graphics2D graphic) {
        graphic.setPaint(Color.BLUE);

        // axe x
        graphic.draw(new Line2D.Double(30, this.getHeight() - 30, this.getWidth() - 30, this.getHeight() - 30));
        graphic.draw(new Line2D.Double(30, 30, this.getWidth() - 30, 30));

        // axe y
        graphic.draw(new Line2D.Double(30, 30, 30, this.getHeight() - 30));
        graphic.draw(new Line2D.Double(this.getWidth() - 30, 30, this.getWidth() - 30, this.getHeight() - 30));

    }

    public Float round(Float number) {
        return (float) Math.rint(number * 10) / 10;
    }

    private void drawAxisLegends(Graphics2D graphic) {
        graphic.setPaint(Color.BLUE);
        Float x, y;
        Font oldFont = getFont();
        Font newFont = new Font(graphic.getFont().toString(), Font.BOLD, 10);
        graphic.setFont(newFont);
        if (nameX == null) {
            nameX = "X";
        }
        if (nameY == null) {
            nameY = "Y";
        }
        graphic.drawString(nameX.toUpperCase(), this.getWidth() / 2 - 30, 20);

        for (int i = 0; i < nameY.length(); i++) {
            graphic.setFont(newFont);
            graphic.drawString(nameY.toUpperCase().substring(i, i + 1),
                    this.getWidth() - 20, this.getHeight() / 2 - 30 + i * 15);
        }

        newFont = new Font(graphic.getFont().toString(), Font.PLAIN, 9);
        graphic.setFont(newFont);
        int scaleX = Math.round((float) (this.getWidth() - 60) / (float) 50);
        int scaleY = Math.round((float) (this.getHeight() - 60) / (float) 50);

        // grilla y numeros del eje X
        for (int i = 0; i <= scaleX; i++) {
            x = (((float) i * (float) (getWidth() - 60)) / (scaleX)) + 30.f;
            Float value = (((float) i * (float) (maxX - minX)) / (scaleX))
                    + minX;

            y = this.getHeight() - 30.f;
            graphic.setPaint(Color.BLUE);

            graphic.drawLine(Math.round(x), Math.round(y), Math.round(x), 30);
            //graphic.drawString(round(value).toString(),
            //        Math.round(x)
            //        - ((new Integer(Math.round(value))).toString().length() / 2 * 7), Math.round(y) + 15);
            Float valueTo = (float) roundTo(value, 4 - new Integer(scaleX).toString().length());
            graphic.drawString(valueTo.toString(),
                    Math.round(x)
                    - ((new Integer(Math.round(value))).toString().length() / 2 * 7), Math.round(y) + 15);

            y = 30.f;

        }

        // grilla y numeros del eje Y
        for (int i = 0; i <= scaleY; i++) {
            y = (((float) i * (float) (getHeight() - 60)) / (scaleY)) + 30.f;
            y = this.getHeight() - y;
            Float value = (((float) i * (float) (maxY - minY)) / (scaleY))
                    + minY;

            x = 30.f;
            graphic.setPaint(Color.BLUE);

            graphic.drawLine(Math.round(x), Math.round(y), getWidth() - 30,
                    Math.round(y));
            Float valueTo = (float) roundTo(value, 4 - new Integer(scaleY).toString().length());
            //Integer substraction = new Integer(graphic.getFontMetrics().stringWidth(round(value).toString()));
            Integer substraction = new Integer(graphic.getFontMetrics().stringWidth(valueTo.toString()));
            graphic.drawString(valueTo.toString(), Math.round(x)
                    - substraction, Math.round(y) + 5);
            //graphic.drawString(round(value).toString(), Math.round(x)
            //      - substraction, Math.round(y) + 5);

            x = this.getWidth() - 30.f;

        }
        graphic.setFont(oldFont);

    }

    private void drawAxisLegendsIconSummary(Graphics2D graphic) {
        graphic.setPaint(Color.BLUE);
        Float x, y;
        Font oldFont = getFont();
        Font newFont = new Font(graphic.getFont().toString(), Font.PLAIN, 9);
        graphic.setFont(newFont);
        //int scaleX = Math.round((float) (this.getWidth() - 60) / (float) this.widthTable);
        //int scaleY = Math.round((float) (this.getHeight() - 60) / (float) this.heightTable);

        int scaleX = this.widthTable;
        int scaleY = this.heightTable;
        // grilla y numeros del eje X
        for (int i = 0; i <= scaleX; i++) {
            x = (((float) i * (float) (getWidth() - 60)) / (scaleX)) + 30.f;
            Float value = (((float) i * (float) (maxX - minX)) / (scaleX))
                    + minX;

            y = this.getHeight() - 30.f;
            graphic.setPaint(Color.BLUE);

            graphic.drawLine(Math.round(x), Math.round(y), Math.round(x), 30);
            //graphic.drawString(round(value).toString(),
            //        Math.round(x)
            //        - ((new Integer(Math.round(value))).toString().length() / 2 * 7), Math.round(y) + 15);
            Float valueTo = (float) roundTo(value, 4 - new Integer(scaleX).toString().length());
            y = 30.f;

        }

        // grilla y numeros del eje Y
        for (int i = 0; i <= scaleY; i++) {
            y = (((float) i * (float) (getHeight() - 60)) / (scaleY)) + 30.f;
            y = this.getHeight() - y;
            Float value = (((float) i * (float) (maxY - minY)) / (scaleY))
                    + minY;

            x = 30.f;
            graphic.setPaint(Color.BLUE);

            graphic.drawLine(Math.round(x), Math.round(y), getWidth() - 30,
                    Math.round(y));
            Float valueTo = (float) roundTo(value, 4 - new Integer(scaleY).toString().length());
            //Integer substraction = new Integer(graphic.getFontMetrics().stringWidth(round(value).toString()));
            Integer substraction = new Integer(graphic.getFontMetrics().stringWidth(valueTo.toString()));

            x = this.getWidth() - 30.f;

        }
        graphic.setFont(oldFont);

    }

    private void drawMarchingSquares(Graphics2D graphic) {
        MarchingSquares marchingSquares = new MarchingSquares(colorMap, threshold);
        marchingSquaresPaths = marchingSquares.marchingSquares();

        Float x, y;
        graphic.setPaint(Color.RED);
        for (int i = 0; i < marchingSquaresPaths.size(); i++) {

            Path path = marchingSquaresPaths.get(i);
            graphic.drawLine(path.getX1(), path.getY1(), path.getX2(), path.getY2());

        }
        this.zoom = false;
    }

    String getTimeString() {
        return this.timeString;
    }

    public void setHashSetClusters(HashSet<Float> hashSetClusters) {
        this.hashSetClusters = hashSetClusters;
    }

    public void setCheckLens(boolean checkLens) {
        this.checkLens = checkLens;
    }

    public void setCheckLensSplatting(boolean checkLensSplatting) {
        if (checkLensSplatting == true) {
            imageDensity = null;
        }
        this.checkLensSplatting = checkLensSplatting;
    }

    public void setCheckZoomLocal(boolean checkZoomLocal) {
        this.checkZoomLocal = checkZoomLocal;
    }

    public void setCheckLabel(boolean checkLabel) {
        this.checkLabel = checkLabel;
        this.labelInstance = "";

    }

    public void setCheckNameSongsLabel(boolean checkNameSongsLabel) {
        this.checkNameSongsLabelLabel = checkNameSongsLabel;
        this.labelInstance = "";

    }

    public void setCheckMarchingSquares(boolean checkMarchingSquares) {
        this.checkMarchingSquares = checkMarchingSquares;
    }

    public void setCheckIconSummary(boolean checkIconSummary) {
        this.checkIconSummary = checkIconSummary;
    }

    public boolean getCheckIconSummary() {
        return this.checkIconSummary;
    }

    public void setRadiusNN(Float radiusNN) {
        this.radiusNN = radiusNN;
        this.zoom = false;
    }

    public void setCheckLines(boolean checkLines) {
        this.checkLines = checkLines;
        this.zoom = false;
    }

    public void setThresholdField(float thresholdField) {
        this.threshold = thresholdField;
        this.zoom = false;

    }

    public void setfieldSizeSplat(Integer fieldSize) {
        this.fieldSize = fieldSize;
        this.zoom = false;

    }

    public void setSplatSigmaField(float splatSigmaField) {
        this.sigma = splatSigmaField;
        this.zoom = false;

    }

    public void setZoomOriginal(boolean b) {
        this.checkZoomOriginal = b;
    }

    public void setZoomPrevious(boolean b) {
        this.checkZoomPrevious = b;
    }

    public void setColorScale(Object selectedItem) {
        this.colorScale = selectedItem.toString();
        this.zoom = false;
    }

    public void setPathsIcones(String strPathsIcones) {
        this.pathsIcones = strPathsIcones;
    }

    public void setTypePoint(Object selectedItem) {
        String namePoint = selectedItem.toString();
        if (namePoint.equals("Points")) {
            this.checkPointsFilled = true;
            this.checkPointsBorder = false;
            this.checkPointsTransparency = false;
            this.checkPointsIcones = false;
            this.checkPointsGraph = false;
        } else {
            if (namePoint.equals("Boundary Points")) {
                this.checkPointsFilled = false;
                this.checkPointsBorder = true;
                this.checkPointsTransparency = false;
                this.checkPointsIcones = false;
                this.checkPointsGraph = false;
            } else {
                if (namePoint.equals("Transparency")) {
                    this.checkPointsFilled = false;
                    this.checkPointsBorder = false;
                    this.checkPointsTransparency = true;
                    this.checkPointsIcones = false;
                    this.checkPointsGraph = false;
                } else {
                    if (namePoint.equals("Icones")) {
                        this.checkPointsFilled = false;
                        this.checkPointsBorder = false;
                        this.checkPointsTransparency = false;
                        this.checkPointsIcones = true;
                        this.checkPointsGraph = false;
                    } else {
                        if (namePoint.equals("Graph")) {
                            this.checkPointsFilled = false;
                            this.checkPointsBorder = false;
                            this.checkPointsTransparency = false;
                            this.checkPointsIcones = false;
                            this.checkPointsGraph = true;
                        } else {
                            this.checkPointsFilled = false;
                            this.checkPointsBorder = false;
                            this.checkPointsTransparency = false;
                            this.checkPointsIcones = false;
                            this.checkPointsGraph = false;
                        }
                    }
                }
            }
        }
        this.zoom = false;
    }

    public void setTypeDensity(Object selectedItem) {
        String nameDensity = selectedItem.toString();
        if (nameDensity.equals("Splatting")) {
            this.checkSplatting = true;
            this.checkTriangularSplatting = false;
            this.checkGaussianBlur = false;
            this.checkTriangularBlur = false;
            this.checkWelchSpklatting = false;
            this.checkApproximateSplatting1 = false;
            this.checkApproximateSplatting2 = false;
            this.checkApproximateSplatting3 = false;
            this.checkSplattingWithNN = false;
        } else {
            if (nameDensity.equals("Gaussian Blur")) {
                this.checkSplatting = false;
                this.checkTriangularSplatting = false;
                this.checkWelchSpklatting = false;
                this.checkGaussianBlur = true;
                this.checkTriangularBlur = false;
                this.checkApproximateSplatting1 = false;
                this.checkApproximateSplatting2 = false;
                this.checkApproximateSplatting3 = false;
                this.checkSplattingWithNN = false;
            } else {
                if (nameDensity.equals("Triangular Blur")) {
                    this.checkSplatting = false;
                    this.checkTriangularSplatting = false;
                    this.checkGaussianBlur = false;
                    this.checkTriangularBlur = true;
                    this.checkWelchSpklatting = false;
                    this.checkApproximateSplatting1 = false;
                    this.checkApproximateSplatting2 = false;
                    this.checkApproximateSplatting3 = false;
                    this.checkSplattingWithNN = false;
                } else {
                    if (nameDensity.equals("Apprx. Splatting 1")) {
                        this.checkSplatting = false;
                        this.checkTriangularSplatting = false;
                        this.checkGaussianBlur = false;
                        this.checkTriangularBlur = false;
                        this.checkWelchSpklatting = false;
                        this.checkApproximateSplatting1 = true;
                        this.checkApproximateSplatting2 = false;
                        this.checkApproximateSplatting3 = false;
                        this.checkSplattingWithNN = false;
                    } else {
                        if (nameDensity.equals("Apprx. Splatting 2")) {
                            this.checkSplatting = false;
                            this.checkTriangularSplatting = false;
                            this.checkGaussianBlur = false;
                            this.checkTriangularBlur = false;
                            this.checkWelchSpklatting = false;
                            this.checkApproximateSplatting1 = false;
                            this.checkApproximateSplatting2 = true;
                            this.checkApproximateSplatting3 = false;
                            this.checkSplattingWithNN = false;
                        } else {
                            if (nameDensity.equals("Splatting with NN")) {
                                this.checkSplatting = false;
                                this.checkTriangularSplatting = false;
                                this.checkGaussianBlur = false;
                                this.checkTriangularBlur = false;
                                this.checkWelchSpklatting = false;
                                this.checkApproximateSplatting1 = false;
                                this.checkApproximateSplatting2 = false;
                                this.checkApproximateSplatting3 = false;
                                this.checkSplattingWithNN = true;
                            } else {
                                if (nameDensity.equals("Triangular Splatting")) {
                                    this.checkSplatting = false;
                                    this.checkTriangularSplatting = true;
                                    this.checkGaussianBlur = false;
                                    this.checkTriangularBlur = false;
                                    this.checkWelchSpklatting = false;
                                    this.checkApproximateSplatting1 = false;
                                    this.checkApproximateSplatting2 = false;
                                    this.checkApproximateSplatting3 = false;
                                    this.checkSplattingWithNN = false;
                                } else {
                                    if (nameDensity.equals("Apprx. Splatting 3")) {
                                        this.checkSplatting = false;
                                        this.checkTriangularSplatting = false;
                                        this.checkGaussianBlur = false;
                                        this.checkTriangularBlur = false;
                                        this.checkWelchSpklatting = false;
                                        this.checkApproximateSplatting1 = false;
                                        this.checkApproximateSplatting2 = false;
                                        this.checkApproximateSplatting3 = true;
                                        this.checkSplattingWithNN = false;
                                    } else {
                                        if (nameDensity.equals("Welch Splatting")) {

                                            this.checkSplatting = false;
                                            this.checkTriangularSplatting = false;
                                            this.checkGaussianBlur = false;
                                            this.checkTriangularBlur = false;
                                            this.checkWelchSpklatting = true;
                                            this.checkApproximateSplatting1 = false;
                                            this.checkApproximateSplatting2 = false;
                                            this.checkApproximateSplatting3 = false;
                                            this.checkSplattingWithNN = false;
                                        } else {
                                            this.checkSplatting = false;
                                            this.checkTriangularSplatting = false;
                                            this.checkGaussianBlur = false;
                                            this.checkTriangularBlur = false;
                                            this.checkWelchSpklatting = false;
                                            this.checkApproximateSplatting1 = false;
                                            this.checkApproximateSplatting2 = false;
                                            this.checkApproximateSplatting3 = false;
                                            this.checkSplattingWithNN = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        this.zoom = false;
    }

    public Integer getWidthTable() {
        return widthTable;
    }

    public void setWidthTable(Integer widthTable) {
        this.widthTable = widthTable;
    }

    public Integer getHeightTable() {
        return heightTable;
    }

    public void setHeightTable(Integer heightTable) {
        this.heightTable = heightTable;
    }

    public String getFormatIcon() {
        return formatIcon;
    }

    public void setFormatIcon(String formatIcon) {
        this.formatIcon = formatIcon;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }

    public Integer getHeightPanel() {
        return heightPanel;
    }

    public Integer getWidthPanel() {
        return widthPanel;
    }

    public void drawMultipleIcons(ArrayList<ArrayList<CellMatrixIconSummary>> matrixIconSummary, Integer maxNumberLog2Points, Graphics2D graphic) {
        for (int i = 0; i < matrixIconSummary.size(); i++) {
            for (int j = 0; j < matrixIconSummary.get(i).size(); j++) {
                if (matrixIconSummary.get(i).get(j).getImage() != null && matrixIconSummary.get(i).get(j).getNumberLog2() > 0) {
                    Integer sizeLocalWidthCell = matrixIconSummary.get(i).get(j).getNumberLog2() * sizeWidthCell / maxNumberLog2Points;
                    Integer sizeLocalHeightCell = matrixIconSummary.get(i).get(j).getNumberLog2() * sizeHeightCell / maxNumberLog2Points;
                    Integer tmpWidth = (sizeWidthCell - sizeLocalWidthCell) / 2;
                    Integer tmpHeight = (sizeHeightCell - sizeLocalHeightCell) / 2;
                    graphic.drawImage(matrixIconSummary.get(i).get(j).getImage(), 30 + (j * sizeWidthCell) + tmpWidth, 30 + (i * sizeHeightCell) + tmpHeight, sizeLocalWidthCell, sizeLocalHeightCell, this);
                }

            }
        }
    }

    public void drawSingleIcon(ArrayList<ArrayList<CellMatrixIconSummary>> matrixIconSummary, Integer maxNumberLog2Points, Graphics2D graphic) {

        for (int i = 0; i < matrixIconSummary.size(); i++) {
            for (int j = 0; j < matrixIconSummary.get(i).size(); j++) {
                if (matrixIconSummary.get(i).get(j).getImage() != null && matrixIconSummary.get(i).get(j).getNumberLog2() > 0) {
                    Integer sizeLocalWidthCell = matrixIconSummary.get(i).get(j).getNumberLog2() * sizeWidthCell / maxNumberLog2Points;
                    Integer sizeLocalHeightCell = matrixIconSummary.get(i).get(j).getNumberLog2() * sizeHeightCell / maxNumberLog2Points;
                    Integer tmpWidth = (sizeWidthCell - sizeLocalWidthCell) / 2;
                    Integer tmpHeight = (sizeHeightCell - sizeLocalHeightCell) / 2;
                    graphic.drawImage(matrixIconSummary.get(i).get(j).getImage(), 30 + (j * sizeWidthCell) + tmpWidth, 30 + (i * sizeHeightCell) + tmpHeight, sizeLocalWidthCell, sizeLocalHeightCell, this);
                }

            }
        }
    }

    public BufferedImage createOffIconSummaryImage() {
        BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();
        super.paintComponent((Graphics) g2);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);

        colorMap = new ArrayList<>(this.getWidth());
        for (Integer i = 0; i < getWidth(); i++) {
            colorMap.add(i, new ArrayList<Float>(this.getHeight()));
            for (Integer j = 0; j < getHeight(); j++) {
                colorMap.get(i).add(j, 1.f);
            }
        }

        tree = new AABSTreeIconSummary((this.getWidth() - 60 - 30) / this.widthTable, (this.getHeight() - 60 - 30) / this.heightTable, 30, this.getHeight() - 60, this.getWidth() - 60, 30);
        //System.out.println("puntos iniciales : (" + 30 + "," + (this.getHeight() - 60) + ") (" + (this.getWidth() - 60) + "," + 30 + ")");
        //System.out.println("tamaño del cuadradito height: " + (this.getHeight() - 60 - 30) / this.heightTable);
        //System.out.println("tamaño del cuadradito width: " + (this.getWidth() - 60 - 30) / this.widthTable);

        sizeWidthCell = (this.getWidth() - 60 - 30) / this.widthTable;
        sizeHeightCell = (this.getHeight() - 60 - 30) / this.heightTable;
        matrixIconSummary = new ArrayList<>();

        for (int i = 0; i < this.heightTable; i++) {

            ArrayList<CellMatrixIconSummary> rowMatrixIconSummary = new ArrayList<>();
            for (int j = 0; j < this.widthTable; j++) {
                CellMatrixIconSummary cell = new CellMatrixIconSummary(i, j);
                rowMatrixIconSummary.add(cell);
            }
            matrixIconSummary.add(rowMatrixIconSummary);
        }
        if (checkDrawGridScreenIconSummary == true) {
            drawAxisIconSummary(g2);
        }

        Float x, y;

        for (int i = 0; i < listPoints.size(); i++) {
            x = ((listPoints.get(i).getX() - minX) * (float) (this.getWidth() - 60) / (maxX - minX)) + 30;
            y = ((listPoints.get(i).getY() - minY) * (float) (this.getHeight() - 60) / (maxY - minY)) + 30;

            if (x > (this.getWidth() - 60.f)) {
                x = this.getWidth() - 60.f;
            }
            if (x < 30.f) {
                x = 30.f;
            }
            if (y > (this.getHeight() - 60.f)) {
                y = (this.getHeight() - 60.f);
            }
            if (y < 30.f) {
                y = 30.f;
            }

            AABSNodeIconSummary node = tree.getBoundingNode(Math.round(x), Math.round(y));
            Integer column = (node.getX0() - 30) / sizeWidthCell;
            Integer row = heightTable - (node.getY0() - 30) / sizeHeightCell;
            matrixIconSummary.get(row).get(column).insertPoint(listPoints.get(i));
        }

        Integer maxNumberLog2Points = 0;
        Integer maxNumberPoints = 0;
        for (int i = 0; i < matrixIconSummary.size(); i++) {
            for (int j = 0; j < matrixIconSummary.get(i).size(); j++) {

                Integer tmpValue = Math.round(log2(matrixIconSummary.get(i).get(j).getNumberPoints()));
                matrixIconSummary.get(i).get(j).setNumberLog2(tmpValue);

                if (maxNumberPoints < matrixIconSummary.get(i).get(j).getNumberPoints()) {
                    maxNumberPoints = matrixIconSummary.get(i).get(j).getNumberPoints();
                }
                if (maxNumberLog2Points < tmpValue) {
                    maxNumberLog2Points = tmpValue;
                }

            }

        }

        // @aurea recuerda el maximo de iconos es 5 está estático
        Integer maxNumberIconesInScreen = 5;
        if (formatIcon.equals("multiple icons")) {

            for (int i = 0; i < matrixIconSummary.size(); i++) {
                for (int j = 0; j < matrixIconSummary.get(i).size(); j++) {
                    Integer numberIcons;
                    if (maxNumberLog2Points > maxNumberIconesInScreen) {

                        numberIcons = matrixIconSummary.get(i).get(j).getNumberLog2() * maxNumberIconesInScreen / maxNumberLog2Points;
                    } else {
                        numberIcons = matrixIconSummary.get(i).get(j).getNumberLog2();
                    }
                    if (matrixIconSummary.get(i).get(j).getListPoints().size() > 0) {
                        ArrayList<InstancePoint> listMainPoints = getListMassCenter(matrixIconSummary.get(i).get(j).getListPoints(), numberIcons);
                        matrixIconSummary.get(i).get(j).setListMainPoints(listMainPoints);
                        BufferedImage[] buffImages = new BufferedImage[listMainPoints.size()];
                        int chunkWidth = 0, chunkHeight = 0;
                        for (int k = 0; k < listMainPoints.size(); k++) {
                            try {
                                String labelPoint = listMainPoints.get(k).getLabel();
                                Image imageConvert = ImageIO.read(new File(pathsIcones + "/" + labelPoint + ".png"));
                                buffImages[k] = (BufferedImage) imageConvert;
                                int widthImg1 = buffImages[0].getWidth();
                                int heightImg1 = buffImages[0].getHeight();
                                if (widthImg1 > chunkWidth) {
                                    chunkWidth = widthImg1;
                                }
                                if (heightImg1 > chunkHeight) {
                                    chunkHeight = heightImg1;
                                }

                            } catch (IOException ex) {
                            }
                        }

                        if (listMainPoints.size() > 0 && chunkWidth > 0 && chunkHeight > 0) {

                            BufferedImage finalImg = new BufferedImage(chunkWidth, chunkHeight * listMainPoints.size(), BufferedImage.TYPE_INT_RGB);

                            int num = 0;
                            for (int k = 0; k < listMainPoints.size(); k++) {

                                boolean imageDrawn = finalImg.createGraphics().drawImage(buffImages[num], 0, chunkHeight * k, chunkWidth, chunkHeight, null); // 0, 0 are the x and y positions
                                num++;

                            }
                            matrixIconSummary.get(i).get(j).setImage(finalImg);

                        }
                    }
                }

            }
            drawMultipleIcons(matrixIconSummary, maxNumberLog2Points, g2);
        } else {

            for (int i = 0; i < matrixIconSummary.size(); i++) {
                for (int j = 0; j < matrixIconSummary.get(i).size(); j++) {
                    try {
                        if (matrixIconSummary.get(i).get(j).getListPoints().size() > 0) {
                            ArrayList<InstancePoint> listMainPoints = getListMassCenter(matrixIconSummary.get(i).get(j).getListPoints(), 1);
                            matrixIconSummary.get(i).get(j).setListMainPoints(listMainPoints);
                            if (listMainPoints.size() > 0) {
                                String labelPoint = listMainPoints.get(0).getLabel();
                                Image imageConvert = ImageIO.read(new File(pathsIcones + "/" + labelPoint + ".png"));
                                BufferedImage buffered = (BufferedImage) imageConvert;
                                matrixIconSummary.get(i).get(j).setImage(buffered);
                            }
                        }
                    } catch (IOException ex) {
                    }
                }

            }
            drawSingleIcon(matrixIconSummary, maxNumberLog2Points, g2);
        }

        //drawPoints(g2);
        g2.dispose();
        return bi;
    }

    public static Float log2(Integer a) {
        return (float) (Math.log(a)) / (float) (Math.log(2));
    }

    public InstancePoint getMassCenter(ArrayList<InstancePoint> listPoints) {
        Float x = 0.f;
        Float y = 0.f;
        for (int i = 0; i < listPoints.size(); i++) {
            x = x + listPoints.get(i).getX();
            y = y + listPoints.get(i).getY();
        }
        x = x / listPoints.size();
        y = y / listPoints.size();

        Float minDistance = Float.POSITIVE_INFINITY;
        InstancePoint point = null;

        for (int i = 0; i < listPoints.size(); i++) {
            Float tmpDistance = Math.abs(x - listPoints.get(i).getX()) + Math.abs(y - listPoints.get(i).getY());
            if (minDistance > tmpDistance) {
                minDistance = tmpDistance;
                point = listPoints.get(i);
            }
        }
        return point;

    }

    public ArrayList<InstancePoint> getListMassCenter(ArrayList<InstancePoint> listPoints, Integer numberPoints) {

        InstancePoint point = getMassCenter(listPoints);
        ArrayList<Float> listDistances = new ArrayList<>();
        ArrayList<InstancePoint> listResult = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        listResult.add(point);
        labels.add(point.getLabel());

        for (int i = 0; i < listPoints.size(); i++) {
            Float tmpDistance = Math.abs(point.getX() - listPoints.get(i).getX()) + Math.abs(point.getY() - listPoints.get(i).getY());
            listDistances.add(tmpDistance);
        }

        while (listResult.size() < numberPoints) {
            Float minDistance = listDistances.get(0);
            InstancePoint tmpPoint = listPoints.get(0);
            for (int i = 1; i < listPoints.size(); i++) {
                if ((listDistances.get(i) < minDistance) && (!labels.contains(listPoints.get(i).getLabel()))) {
                    minDistance = listDistances.get(i);
                    tmpPoint = listPoints.get(i);
                }
            }
            listResult.add(tmpPoint);
            labels.add(tmpPoint.getLabel());

        }
        return listResult;

    }

    public BufferedImage createOffscreenImage() {

        BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();
        super.paintComponent((Graphics) g2);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);

        colorMap = new ArrayList<>(this.getWidth());
        for (Integer i = 0; i < getWidth(); i++) {
            colorMap.add(i, new ArrayList<Float>(this.getHeight()));
            for (Integer j = 0; j < getHeight(); j++) {
                colorMap.get(i).add(j, 1.f);
            }
        }
        //super.paintComponent();

        if (checkSplatting) {
            long start = System.nanoTime();
            Splatting splatting = new Splatting(listPoints, this.getWidth(), this.getHeight(), fieldSize, sigma, maxX, maxY, minX, minY);
            colorMap = splatting.createSplatting();
            long end = System.nanoTime();
            this.timeString = "Splatting: " + (double) (end - start) / 1000000000.0 + " secs.";
            System.out.println(this.timeString);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JOptionPane.showMessageDialog(MusicVisualizationPanel.this, timeString);

                }
            });
            drawDensity(g2);
        } else {
            if (checkGaussianBlur) {
                long start2 = System.nanoTime();
                GaussianBlur gaussianBlur = new GaussianBlur(listPoints, this.getWidth(), this.getHeight(), fieldSize, sigma, maxX, maxY, minX, minY);
                colorMap = gaussianBlur.createGaussianBlur();
                long end2 = System.nanoTime();

                this.timeString = "Gaussian Blur: " + (double) (end2 - start2) / 1000000000.0 + " secs.";
                System.out.println(this.timeString);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JOptionPane.showMessageDialog(MusicVisualizationPanel.this, timeString);

                    }
                });
                drawDensity(g2);

            } else {
                if (checkTriangularBlur) {
                    long start3 = System.nanoTime();
                    TriangularBlur triangularBlur = new TriangularBlur(listPoints, this.getWidth(), this.getHeight(), fieldSize, maxX, maxY, minX, minY);
                    colorMap = triangularBlur.createTriangularBlur();
                    long end3 = System.nanoTime();

                    this.timeString = "Triangular Blur: " + (double) (end3 - start3) / 1000000000.0 + " secs.";
                    System.out.println(this.timeString);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            JOptionPane.showMessageDialog(MusicVisualizationPanel.this, timeString);

                        }
                    });
                    drawDensity(g2);
                } else {
                    if (checkApproximateSplatting1) {
                        long start4 = System.nanoTime();
                        ApproximateSplatting1 approximateSplatting1 = new ApproximateSplatting1(listPoints, this.getWidth(), this.getHeight(), fieldSize, sigma, maxX, maxY, minX, minY);
                        colorMap = approximateSplatting1.createApproximateSplatting1();
                        long end4 = System.nanoTime();

                        //System.out.println("Time: Approximation Splatting 1 " + (double) (end4 - start4) / 1000000000.0 + " secs.");
                        this.timeString = "Approximation Splatting 1: " + (double) (end4 - start4) / 1000000000.0 + " secs.";
                        System.out.println(this.timeString);
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                JOptionPane.showMessageDialog(MusicVisualizationPanel.this, timeString);

                            }
                        });
                        drawDensity(g2);
                    } else {
                        if (checkApproximateSplatting2) {
                            long start5 = System.nanoTime();
                            ApproximateSplatting2 approximateSplatting2 = new ApproximateSplatting2(listPoints, this.getWidth(), this.getHeight(), fieldSize, sigma, maxX, maxY, minX, minY);
                            colorMap = approximateSplatting2.createApproximateSplatting2();
                            long end5 = System.nanoTime();

                            //System.out.println("Time: Approximation Splatting 2 " + (double) (end5 - start5) / 1000000000.0 + " secs.");
                            this.timeString = "Approximation Splatting 2: " + (double) (end5 - start5) / 1000000000.0 + " secs.";
                            System.out.println(this.timeString);
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    JOptionPane.showMessageDialog(MusicVisualizationPanel.this, timeString);

                                }
                            });
                            drawDensity(g2);
                        } else {
                            if (checkSplattingWithNN) {
                                long start6 = System.nanoTime();
                                SplattingWithNN splattingWithNN = new SplattingWithNN(listPoints, this.getWidth(), this.getHeight(), fieldSize, sigma, maxX, maxY, minX, minY, radiusNN);
                                colorMap = splattingWithNN.createSplattingWithNN();
                                long end6 = System.nanoTime();

                                this.timeString = "Splatting With NN: " + (double) (end6 - start6) / 1000000000.0 + " secs.";
                                System.out.println(this.timeString);
                                SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        JOptionPane.showMessageDialog(MusicVisualizationPanel.this, timeString);

                                    }
                                });
                                drawDensity(g2);
                            } else {
                                if (checkTriangularSplatting) {
                                    long start7 = System.nanoTime();
                                    TriangularSplatting triangularSplatting = new TriangularSplatting(listPoints, this.getWidth(), this.getHeight(), fieldSize, maxX, maxY, minX, minY);
                                    colorMap = triangularSplatting.createTriangularSplatting();
                                    long end7 = System.nanoTime();

                                    this.timeString = "Triangular Splatting: " + (double) (end7 - start7) / 1000000000.0 + " secs.";
                                    System.out.println(this.timeString);
                                    SwingUtilities.invokeLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            JOptionPane.showMessageDialog(MusicVisualizationPanel.this, timeString);

                                        }
                                    });
                                    drawDensity(g2);
                                } else {
                                    if (checkApproximateSplatting3) {
                                        long start8 = System.nanoTime();
                                        ApproximateSplatting3 approximateSplatting3 = new ApproximateSplatting3(listPoints, this.getWidth(), this.getHeight(), fieldSize, sigma, maxX, maxY, minX, minY);
                                        colorMap = approximateSplatting3.createApproximateSplatting3();
                                        long end8 = System.nanoTime();

                                        this.timeString = "Approximation Splatting 3: " + (double) (end8 - start8) / 1000000000.0 + " secs.";
                                        System.out.println(this.timeString);
                                        SwingUtilities.invokeLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                JOptionPane.showMessageDialog(MusicVisualizationPanel.this, timeString);

                                            }
                                        });
                                        drawDensity(g2);
                                    } else {
                                    
                                    if (checkWelchSpklatting) {
                                        long start9 = System.nanoTime();
                                        WelchSplatting welchSplatting = new WelchSplatting(listPoints, this.getWidth(), this.getHeight(), fieldSize, maxX, maxY, minX, minY);
                                        colorMap = welchSplatting.createWelchSplatting();
                                        long end9 = System.nanoTime();

                                        this.timeString = "Wech Splatting: " + (double) (end9 - start9) / 1000000000.0 + " secs.";
                                        System.out.println(this.timeString);
                                        SwingUtilities.invokeLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                JOptionPane.showMessageDialog(MusicVisualizationPanel.this, timeString);

                                            }
                                        });
                                        drawDensity(g2);
                                    } else {
                                    }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
        drawAxis(g2);
        if (checkLines) {
            drawAxisLegends(g2);
        }
        drawPoints(g2);
        if (checkMarchingSquares) {
            drawMarchingSquares(g2);
        }

        if (rgb != null) {
            g2.drawString("-", 23, 15);
            for (int i = 255; i >= 0; i--) {
                g2.setPaint(rgb[i]);
                g2.fill(new Rectangle(30 + (1 * (255 - i)), 10, 1, 5));
            }
            g2.drawString("+", 33 + (255), 15);
        }

        g2.dispose();
        return bi;
    }

    public BufferedImage createOffscreenImageDensity(int width, int height) {

        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();
        super.paintComponent((Graphics) g2);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);

        colorMap = new ArrayList<>(width);
        for (Integer i = 0; i < width; i++) {
            colorMap.add(i, new ArrayList<Float>(height));
            for (Integer j = 0; j < height; j++) {
                colorMap.get(i).add(j, 1.f);
            }
        }
        //super.paintComponent();

        if (checkSplatting) {
            long start = System.nanoTime();
            Splatting splatting = new Splatting(listPoints, width, height, fieldSize, sigma, maxX, maxY, minX, minY);
            colorMap = splatting.createSplatting();
            long end = System.nanoTime();
            this.timeString = "Splatting: " + (double) (end - start) / 1000000000.0 + " secs.";
            System.out.println(this.timeString);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JOptionPane.showMessageDialog(MusicVisualizationPanel.this, timeString);

                }
            });
            drawDensity(g2);
        } else {
            if (checkGaussianBlur) {
                long start2 = System.nanoTime();
                GaussianBlur gaussianBlur = new GaussianBlur(listPoints, width, height, fieldSize, sigma, maxX, maxY, minX, minY);
                colorMap = gaussianBlur.createGaussianBlur();
                long end2 = System.nanoTime();

                this.timeString = "Gaussian Blur: " + (double) (end2 - start2) / 1000000000.0 + " secs.";
                System.out.println(this.timeString);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JOptionPane.showMessageDialog(MusicVisualizationPanel.this, timeString);

                    }
                });
                drawDensity(g2);

            } else {
                if (checkTriangularBlur) {
                    long start3 = System.nanoTime();
                    TriangularBlur triangularBlur = new TriangularBlur(listPoints, width, height, fieldSize, maxX, maxY, minX, minY);
                    colorMap = triangularBlur.createTriangularBlur();
                    long end3 = System.nanoTime();

                    this.timeString = "Triangular Blur: " + (double) (end3 - start3) / 1000000000.0 + " secs.";
                    System.out.println(this.timeString);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            JOptionPane.showMessageDialog(MusicVisualizationPanel.this, timeString);

                        }
                    });
                    drawDensity(g2);
                } else {
                    if (checkApproximateSplatting1) {
                        long start4 = System.nanoTime();
                        ApproximateSplatting1 approximateSplatting1 = new ApproximateSplatting1(listPoints, width, height, fieldSize, sigma, maxX, maxY, minX, minY);
                        colorMap = approximateSplatting1.createApproximateSplatting1();
                        long end4 = System.nanoTime();

                        //System.out.println("Time: Approximation Splatting 1 " + (double) (end4 - start4) / 1000000000.0 + " secs.");
                        this.timeString = "Approximation Splatting 1: " + (double) (end4 - start4) / 1000000000.0 + " secs.";
                        System.out.println(this.timeString);
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                JOptionPane.showMessageDialog(MusicVisualizationPanel.this, timeString);

                            }
                        });
                        drawDensity(g2);
                    } else {
                        if (checkApproximateSplatting2) {
                            long start5 = System.nanoTime();
                            ApproximateSplatting2 approximateSplatting2 = new ApproximateSplatting2(listPoints, width, height, fieldSize, sigma, maxX, maxY, minX, minY);
                            colorMap = approximateSplatting2.createApproximateSplatting2();
                            long end5 = System.nanoTime();

                            //System.out.println("Time: Approximation Splatting 2 " + (double) (end5 - start5) / 1000000000.0 + " secs.");
                            this.timeString = "Approximation Splatting 2: " + (double) (end5 - start5) / 1000000000.0 + " secs.";
                            System.out.println(this.timeString);
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    JOptionPane.showMessageDialog(MusicVisualizationPanel.this, timeString);

                                }
                            });
                            drawDensity(g2);
                        } else {
                            if (checkSplattingWithNN) {
                                long start6 = System.nanoTime();
                                SplattingWithNN splattingWithNN = new SplattingWithNN(listPoints, width, height, fieldSize, sigma, maxX, maxY, minX, minY, radiusNN);
                                colorMap = splattingWithNN.createSplattingWithNN();
                                long end6 = System.nanoTime();

                                this.timeString = "Splatting With NN: " + (double) (end6 - start6) / 1000000000.0 + " secs.";
                                System.out.println(this.timeString);
                                SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        JOptionPane.showMessageDialog(MusicVisualizationPanel.this, timeString);

                                    }
                                });
                                drawDensity(g2);
                            } else {
                                if (checkTriangularSplatting) {
                                    long start7 = System.nanoTime();
                                    TriangularSplatting triangularSplatting = new TriangularSplatting(listPoints, width, height, fieldSize, maxX, maxY, minX, minY);
                                    colorMap = triangularSplatting.createTriangularSplatting();
                                    long end7 = System.nanoTime();

                                    this.timeString = "Triangular Splatting: " + (double) (end7 - start7) / 1000000000.0 + " secs.";
                                    System.out.println(this.timeString);
                                    SwingUtilities.invokeLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            JOptionPane.showMessageDialog(MusicVisualizationPanel.this, timeString);

                                        }
                                    });
                                    drawDensity(g2);
                                } else {
                                    if (checkApproximateSplatting3) {
                                        long start8 = System.nanoTime();
                                        ApproximateSplatting3 approximateSplatting3 = new ApproximateSplatting3(listPoints, width, height, fieldSize, sigma, maxX, maxY, minX, minY);
                                        colorMap = approximateSplatting3.createApproximateSplatting3();
                                        long end8 = System.nanoTime();

                                        this.timeString = "Approximation Splatting 3: " + (double) (end8 - start8) / 1000000000.0 + " secs.";
                                        System.out.println(this.timeString);
                                        SwingUtilities.invokeLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                JOptionPane.showMessageDialog(MusicVisualizationPanel.this, timeString);

                                            }
                                        });
                                        drawDensity(g2);
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

        drawAxis(g2);
        if (checkLines) {
            drawAxisLegends(g2);
        }

        if (checkMarchingSquares) {
            drawMarchingSquares(g2);
        }

        g2.dispose();
        return bi;
    }

    private BufferedImage getScaledImage() {
        Dimension d = getSize();
        int width = d.width;
        int height = d.height;
        int w = (int) (this.factor * image.getWidth());
        int h = (int) (this.factor * image.getHeight());
        BufferedImage bi = new BufferedImage(width, height, image.getType());
        Graphics2D g2 = bi.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        AffineTransform at = AffineTransform.getScaleInstance(this.factor, this.factor);
        at.setToIdentity();
        at.translate(point2D.getX(), point2D.getY());
        at.scale(factor, factor);
        at.translate(-point2D.getX(), -point2D.getY());
        g2.drawRenderedImage(image, at);
        g2.dispose();
        return bi;
    }

    private Rectangle2D.Float createRectangle(int x1, int y1, int x2, int y2) {
        return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    public void setWithoutReDoing(boolean b) {
        checkWithoutReDoing = b;
    }

    public void setValueBrightness(Integer value) {
        this.valueBrightness = value;
    }

    private AlphaComposite makeComposite(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        return (AlphaComposite.getInstance(type, alpha));
    }

    private BufferedImage getBrightnessImage(Integer value) {
        Dimension d = getSize();
        int width = d.width;
        int height = d.height;
        int w = (int) (this.factor * image.getWidth());
        int h = (int) (this.factor * image.getHeight());
        BufferedImage bi = new BufferedImage(width, height, image.getType());
        Graphics2D g2 = bi.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        AffineTransform at = AffineTransform.getScaleInstance(this.factor, this.factor);

        RescaleOp rescaleOp = new RescaleOp(1.f, (value - 50) * 4.f, null);
        rescaleOp.filter(imagePrevious, bi);  // Source and destination are the same.

        g2.drawRenderedImage(bi, at);
        g2.dispose();
        return bi;
    }

    public void setCheckBrightness(boolean value) {
        this.checkBrightness = value;
    }

    private void drawSquareRed(Graphics2D graphic) {
        graphic.drawImage(imageIconSummaryPrevious, 0, 0, this);
        graphic.setColor(Color.RED);
        if (cellBoundingSquare != null) { // se esta arrastrando el raton?
            Composite originalComposite = graphic.getComposite();
            Shape rectangle2 = createRectangle(cellBoundingSquare.getX0(), cellBoundingSquare.getY0(), cellBoundingSquare.getX1(), cellBoundingSquare.getY1());
            graphic.setComposite(makeComposite(0.2f));
            graphic.fill(rectangle2);
            graphic.setComposite(originalComposite);
            graphic.setColor(Color.RED);
            graphic.draw(rectangle2);
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        getLabelMap();
        getNameSongsLabelMap();
        getCountMap();
        Graphics2D g2 = (Graphics2D) g;
        setSetColor();
        if (checkIconSummary == true && checkClickScreenIconSummary == true) {
            drawSquareRed(g2);
        } else {

            if (checkBrightness == true && valueBrightness != 50) {
                this.factor = 1.0;
                if (image != null) {
                    BufferedImage imageBrigthness = getBrightnessImage(this.valueBrightness);
                    image = imageBrigthness;
                    g2.drawImage(image, 0, 0, this);
                }

            } else {
                if (checkLens == true || checkLensSplatting == true) {
                    if (checkLens) {
                        super.paintComponent(g);
                        try {
                            lensImage = ImageIO.read(getClass().getResourceAsStream("/images/lupa.png"));
                        } catch (IOException ex) {
                        }
                        BufferedImage imageWithLens;
                        if (this.factor == 1.0) {
                            imageWithLens = image;
                        } else {
                            imageWithLens = imageScaled;
                        }

                        g2.drawImage(imageWithLens, 0, 0, this);
                        if (zoomLens > 1) {
                            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                    RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                            //((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            //RenderingHints.VALUE_ANTIALIAS_ON);
                            Shape clip = g.getClip();
                            Area newClip = new Area(clip);
                            if (currentPoint == null) {
                                currentPoint.x = this.getWidth() / 2;
                                currentPoint.y = this.getHeight() / 2;
                            }
                            newClip.intersect(new Area(new Ellipse2D.Double(currentPoint.x + 3 - 120, currentPoint.y + 3 - 120, 138.0, 138.0)));

                            g.setClip(newClip);
                            g.drawImage(imageWithLens,
                                    (int) (-currentPoint.x + 30),// * zoomLens - getWidth()+30* (zoomLens - 1)),
                                    (int) (-currentPoint.y + 30),// * zoomLens - getHeight()+30 * (zoomLens - 1)),
                                    imageWithLens.getWidth() * zoomLens,
                                    imageWithLens.getHeight() * zoomLens, null);
                            g.setClip(clip);
                        }
                        g.drawImage(lensImage, currentPoint.x - 120, currentPoint.y - 120, null);
                    } else {

                        super.paintComponent(g);
                        if (this.factor == 1.0) {
                            if (image != null) {
                                g2.drawImage(image, 0, 0, this);
                            }
                        } else {

                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    JOptionPane.showMessageDialog(MusicVisualizationPanel.this, "The zoom by zone must be done with a original representation (not scaled)");

                                }
                            });
                            this.factor = 1.0;
                            imageScaled = image;
                            if (image != null) {
                                g2.drawImage(image, 0, 0, this);
                            }

                        }
                        try {
                            lensSplattingImage = ImageIO.read(getClass().getResourceAsStream("/images/lensRectangle.png"));
                        } catch (IOException ex) {
                        }
                        BufferedImage imageWithLensSplatting;
                        //if (this.factor == 1.0) {
                        imageWithLensSplatting = image;
                        //} else {
                        //    imageWithLensSplatting = imageScaled;
                        //}
                        if (imageDensity == null) {
                            imageDensity = createOffscreenImageDensity(image.getWidth(), image.getHeight());
                        }

                        g2.drawImage(imageWithLensSplatting, 0, 0, this);
                        if (zoomLens > 1) {
                            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                    RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                            //((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            //RenderingHints.VALUE_ANTIALIAS_ON);
                            Shape clip = g.getClip();
                            Area newClip = new Area(clip);
                            if (currentPoint == null) {
                                currentPoint.x = this.getWidth() / 2;
                                currentPoint.y = this.getHeight() / 2;
                            }
                            newClip.intersect(new Area(new Rectangle2D.Double(currentPoint.x + 6 - 150, currentPoint.y + 6 - 150, 385.0, 220.0)));

                            g.setClip(newClip);

                            g.drawImage(imageDensity, 0, 0, this);
                            //(int) (-currentPoint.x+30 ),//+30
                            //(int) (-currentPoint.y+30 ),//+30
                            //(int)(Math.round(imageWithLensSplatting.getWidth() -30)*2),
                            //(int)(Math.round(imageWithLensSplatting.getHeight()-30)*2), null);
                            g.setClip(clip);
                        }
                        g.drawImage(lensSplattingImage, currentPoint.x - 150, currentPoint.y - 150, null);

                    }

                } else {

                    if (checkZoomLocal == true) {
                        super.paintComponent(g);
                        if (this.factor == 1.0) {
                            if (image != null) {
                                g2.drawImage(image, 0, 0, this);
                            }
                        } else {

                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    JOptionPane.showMessageDialog(MusicVisualizationPanel.this, "The zoom by zone must be done with a original representation (not scaled)");

                                }
                            });
                            this.factor = 1.0;
                            imageScaled = image;
                            if (image != null) {
                                g2.drawImage(image, 0, 0, this);
                            }

                        }

                        g2.setColor(Color.RED);
                        if (startDragPoint != null && endDragPoint != null) { // se esta arrastrando el raton?
                            Composite originalComposite = g2.getComposite();
                            Shape rectangle2 = createRectangle(startDragPoint.x, startDragPoint.y, endDragPoint.x, endDragPoint.y);
                            g2.setComposite(makeComposite(0.2f));
                            g2.fill(rectangle2);
                            g2.setComposite(originalComposite);
                            g2.setColor(Color.RED);
                            g2.draw(rectangle2);

                        } else {
                            if (rectangle != null) {
                                g2.draw(rectangle);
                            }
                        }
                    } else {

                        if (checkZoomOriginal == true) {
                            this.setZoomOriginal(false);
                            this.factor = 1.0;
                            if (imagePrevious != null) {
                                g2.drawImage(imagePrevious, 0, 0, this);
                            }

                        } else {
                            if (checkZoomPrevious == true && zoom == true) {
                                this.setZoomPrevious(false);
                                if (imagePrevious != null) {
                                    g2.drawImage(imagePrevious, 0, 0, this);

                                }

                            } else {
                                if (zoom == true || dragging == true) {
                                    imageScaled = getScaledImage();
                                    imagePrevious = image;
                                    g2.drawImage(imageScaled, 0, 0, this);
                                } else {

                                    if (checkIconSummary == true) {
                                        image = createOffIconSummaryImage();
                                        imageIconSummaryPrevious = image;
                                        imagePrevious = image;
                                        g2.drawImage(image, 0, 0, this);

                                    } else {
                                        this.factor = 1.0;
                                        if (checkWithoutReDoing == false) {
                                            if (zoom == false && dragging == false) {
                                                image = createOffscreenImage();
                                                imagePrevious = image;
                                            }
                                        }
                                        g2.drawImage(image, 0, 0, this);
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

    }

    public BufferedImage getImage() {
        return image;
    }

    public static double roundTo(double num, Integer value) {
        double result = num * Math.pow(10, value);
        result = Math.round(result);
        result = result / Math.pow(10, value);
        return result;
    }

    public boolean getStatusRectangle() {
        return rectangle != null;
    }

    public BufferedImage getImageWithRectangle() {
        BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();
        super.paintComponent((Graphics) g2);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);
        g2.drawImage(image, 0, 0, this);
        g2.setColor(Color.RED);
        g2.draw(rectangle);
        g2.dispose();

        return bi;

    }

    public List<PointDragged> getPointsRectangleOriginals() {
        Float maxDragPointX, minDragPointX, maxDragPointY, minDragPointY;
        if (startDragPoint != null && endDragPoint != null) {
            if (startDragPoint.x < endDragPoint.x) {
                minDragPointX = startDragPoint.x * 1.f;
                maxDragPointX = endDragPoint.x * 1.f;
            } else {
                minDragPointX = endDragPoint.x * 1.f;
                maxDragPointX = startDragPoint.x * 1.f;
            }
            if (startDragPoint.y < endDragPoint.y) {
                minDragPointY = startDragPoint.y * 1.f;
                maxDragPointY = endDragPoint.y * 1.f;
            } else {
                minDragPointY = endDragPoint.y * 1.f;
                maxDragPointY = startDragPoint.y * 1.f;
            }
            PointDragged p1 = new PointDragged(minDragPointX, minDragPointY);
            PointDragged p2 = new PointDragged(maxDragPointX, maxDragPointY);
            List<PointDragged> listPointDragged = new ArrayList<>();
            listPointDragged.add(p1);
            listPointDragged.add(p2);
            return listPointDragged;
        }
        return null;
    }

    public List<PointDragged> getPointsRectangle() {
        if (startDragPoint != null && endDragPoint != null) {
            Float maxDragPointX = ((startDragPoint.x - 30) * (this.maxX - this.minX) / (this.getWidth() - 60)) + this.minX;
            Float minDragPointX = ((endDragPoint.x - 30) * (this.maxX - this.minX) / (this.getWidth() - 60)) + this.minX;

            Float maxDragPointY = startDragPoint.y * 1.0f;
            maxDragPointY = this.getHeight() - maxDragPointY;
            maxDragPointY = ((maxDragPointY - 30) * (this.maxY - this.minY) / (this.getHeight() - 60)) + this.minY;

            Float minDragPointY = endDragPoint.y * 1.0f;
            minDragPointY = this.getHeight() - minDragPointY;
            minDragPointY = ((minDragPointY - 30) * (this.maxY - this.minY) / (this.getHeight() - 60)) + this.minY;

            if (maxDragPointX < minDragPointX) {
                Float tmp = maxDragPointX;
                maxDragPointX = minDragPointX;
                minDragPointX = tmp;
            }
            if (maxDragPointY < minDragPointY) {
                Float tmp = maxDragPointY;
                maxDragPointY = minDragPointY;
                minDragPointY = tmp;
            }
            startPointRectangle = new PointDragged(minDragPointX, minDragPointY);
            endPointRectangle = new PointDragged(maxDragPointX, maxDragPointY);
            List<PointDragged> listPointDragged = new ArrayList<>();
            listPointDragged.add(startPointRectangle);
            listPointDragged.add(endPointRectangle);

            return listPointDragged;
        }
        return null;
    }

    public InstancePoint getInstanceByPosition(Point point) {
        for (int i = 0; i < listPoints.size(); i++) {
            InstancePoint pi = (InstancePoint) listPoints.get(i);
            if (pi.isInside(point.x, point.y)) {
                return pi;
            }
        }

        return null;
    }

    public void clearRectangle() {
        this.rectangle = null;
        this.startDragPoint = null;
        this.endDragPoint = null;
    }

    @Override
    public void mouseDragged(MouseEvent evt) {
        int x = evt.getX();
        int y = evt.getY();
        if (x < 20) {
            x = 20;
        }
        if (y < 20) {
            y = 20;
        }

        if (x > this.getWidth() + 40) {
            x = this.getWidth() + 40;
        }
        if (y > this.getHeight() + 40) {
            y = this.getHeight() + 40;
        }
        currentPoint = new Point(x, y);
        this.repaint();
        if (checkZoomLocal) {
            x = evt.getX();
            y = evt.getY();
            if (x < 3) {
                x = 3;
            }
            if (y < 3) {
                y = 3;
            }

            if (x > this.getWidth() - 3) {
                x = this.getWidth() - 3;
            }
            if (y > this.getHeight() - 3) {
                y = this.getHeight() - 3;
            }
            endDragPoint = new Point(x, y);
            repaint();
        } else {
            if (checkLens || checkLensSplatting) {
                x = evt.getX();
                y = evt.getY();
                if (x < 20) {
                    x = 20;
                }
                if (y < 20) {
                    y = 20;
                }

                if (x > this.getWidth() + 40) {
                    x = this.getWidth() + 40;
                }
                if (y > this.getHeight() + 40) {
                    y = this.getHeight() + 40;
                }
                currentPoint = new Point(x, y);
                this.repaint();
            } else {

                if (dragging == false) {
                    return;
                }
                if (factor > 1.0) {
                    double x1 = evt.getX(); // posición del mouse
                    double y1 = evt.getY();
                    if (x1 > 0 && x1 < this.getWidth() && y1 > 0 && y1 < this.getHeight()) {

                        //point2D.setLocation(x - offsetX, y - offsetY);
                        //point2D.setLocation(x + pointDragged2D.getX(), y + pointDragged2D.getY());
                        point2D.setLocation(x1 + pointDragged2D.getX(), y1 + pointDragged2D.getY());
                        this.repaint();
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                    }
                }
            }
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (!checkLens && !checkLensSplatting) {
            if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
                this.zoom = true;
                factor += (-.1 * e.getWheelRotation());
                factor = Math.max(.1, roundTo(factor, 2));
                point2D = e.getPoint();
                if (factor < 1) {
                    factor = 1;
                }
                if (factor > 5) {
                    factor = 5;
                }
                this.repaint();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent evt) {
        if (checkZoomLocal) {
            startDragPoint = new Point(evt.getX(), evt.getY());
        } else {
            if (dragging) {

                return;
            }
            if (factor > 1.0) {
                double x = evt.getX(); // clic inicial
                double y = evt.getY();
                // offsetX = x - point2D.getX();
                // offsetY = y - point2D.getY();
                pointDragged2D = this.getLocation();
                dragging = true;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent evt) {

        if (checkZoomLocal) {
            int x = evt.getX();
            int y = evt.getY();
            if (x < 3) {
                x = 3;
            }
            if (y < 3) {
                y = 3;
            }
            if (x > this.getWidth() - 3) {
                x = this.getWidth() - 3;
            }
            if (y > this.getHeight() - 3) {
                y = this.getHeight() - 3;
            }
            endDragPoint = new Point(x, y);
            rectangle = createRectangle(startDragPoint.x, startDragPoint.y, endDragPoint.x, endDragPoint.y);
            repaint();
        } else {
            dragging = false;
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

    }

    public String getLabelByInstance(Integer x, Integer y) {

        try {
            //Integer x1 =Math.round(((x - 30) * (this.maxX - this.minX) / (this.getWidth() - 60)) + this.minX);
            //y = this.getHeight()-y;
            //Integer y1 =Math.round(((y - 30) * (this.maxY - this.minY) / (this.getHeight() - 60)) + this.minY);

            String text = String.valueOf(labelMap.get(x).get(y));
            if (!text.equals("")) {
                return text;
            }
            for (int i = 1; i <= 3; i++) {
                for (int j = 1; j <= 3; j++) {
                    text = String.valueOf(labelMap.get(x + i).get(y + j));
                    if (!text.equals("")) {
                        return text;
                    }
                    text = String.valueOf(labelMap.get(x - i).get(y - j));
                    if (!text.equals("")) {
                        return text;
                    }
                    text = String.valueOf(labelMap.get(x - i).get(y + j));
                    if (!text.equals("")) {
                        return text;
                    }
                    text = String.valueOf(labelMap.get(x + i).get(y - j));
                    if (!text.equals("")) {
                        return text;
                    }
                }
            }

            return text;
        } catch (Exception e) {
            return "";
        }

    }

    public String getNameSongsLabelByInstance(Integer x, Integer y) {

        try {
            //Integer x1 =Math.round(((x - 30) * (this.maxX - this.minX) / (this.getWidth() - 60)) + this.minX);
            //y = this.getHeight()-y;
            //Integer y1 =Math.round(((y - 30) * (this.maxY - this.minY) / (this.getHeight() - 60)) + this.minY);

            String text = String.valueOf(nameSongsLabelMap.get(x).get(y));
            if (!text.equals("")) {
                return text;
            }
            for (int i = 1; i <= 3; i++) {
                for (int j = 1; j <= 3; j++) {
                    text = String.valueOf(nameSongsLabelMap.get(x + i).get(y + j));
                    if (!text.equals("")) {
                        return text;
                    }
                    text = String.valueOf(nameSongsLabelMap.get(x - i).get(y - j));
                    if (!text.equals("")) {
                        return text;
                    }
                    text = String.valueOf(nameSongsLabelMap.get(x - i).get(y + j));
                    if (!text.equals("")) {
                        return text;
                    }
                    text = String.valueOf(nameSongsLabelMap.get(x + i).get(y - j));
                    if (!text.equals("")) {
                        return text;
                    }
                }
            }

            return text;
        } catch (Exception e) {
            return "";
        }

    }

    public Integer getCountOfPointsInRectangle(List<PointDragged> pointsRectangle) {

        boolean statusRectangle = getStatusRectangle();
        if (statusRectangle) {

            Integer count = 0;

            for (int i = Math.round(pointsRectangle.get(0).getX()); i <= Math.round(pointsRectangle.get(1).getX()); i++) {
                for (int j = Math.round(pointsRectangle.get(0).getY()); j <= Math.round(pointsRectangle.get(1).getY()); j++) {
                    count += this.countMap.get(i).get(j);
                }
            }

            return count;
        } else {
            return 0;
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (checkIconSummary == true) {
            if (tree != null && matrixIconSummary != null) {
                Integer x = (int) me.getX(); // clic inicial
                Integer y = (int) me.getY();

                cellBoundingSquare = tree.getBoundingNode(x, y);
                if (cellBoundingSquare != null) {
                    Integer column = (cellBoundingSquare.getX0() - 30) / sizeWidthCell;
                    Integer row = (cellBoundingSquare.getY0() - 30) / sizeHeightCell - 1;
                    listSelectedPointsIconSummary = matrixIconSummary.get(row).get(column).getListPoints();
                    checkClickScreenIconSummary = true;
                    repaint();
                } else {
                    checkClickScreenIconSummary = false;
                    repaint();
                }

            } else {
                checkClickScreenIconSummary = false;
                repaint();

            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mouseMoved(MouseEvent me) {

        currentPoint = new Point(this.getWidth() / 2, this.getHeight() / 2);

        if (this.checkLabel == true) {
            this.labelInstance = getLabelByInstance(me.getX(), me.getY());


            /*
             * message = String.valueOf(numberInstances) + " instances.";
             * textAreaMessage.setText(message);
             */
        }
        if (this.checkNameSongsLabelLabel == true) {
            this.labelInstance = getNameSongsLabelByInstance(me.getX(), me.getY());
        }

    }

    public boolean isCheckDrawGridScreenIconSummary() {
        return checkDrawGridScreenIconSummary;
    }

    public void setCheckDrawGridScreenIconSummary(boolean checkDrawGridScreenIconSummary) {
        this.checkDrawGridScreenIconSummary = checkDrawGridScreenIconSummary;
    }

    public String getLabelInstance() {
        return labelInstance;
    }

    public List<Integer> hsvToRgb(Integer hue, Integer saturation, Integer value) {

        List<Integer> result = new ArrayList<>();
        int h = (int) (hue * 6);
        Integer f = hue * 6 - h;
        Integer p = value * (1 - saturation);
        Integer q = value * (1 - f * saturation);
        Integer t = value * (1 - (1 - f) * saturation);

        switch (h) {
            case 0:
                result.add(value);
                result.add(t);
                result.add(p);
                return result; //rgbToString(value, t, p);
            case 1:
                result.add(q);
                result.add(value);
                result.add(p);
                return result;//rgbToString(q, value, p);
            case 2:
                result.add(p);
                result.add(value);
                result.add(t);
                return result;//return rgbToString(p, value, t);
            case 3:
                result.add(p);
                result.add(q);
                result.add(value);
                return result;//return rgbToString(p, q, value);
            case 4:
                result.add(t);
                result.add(p);
                result.add(value);
                return result;//return rgbToString(t, p, value);
            case 5:
                result.add(value);
                result.add(p);
                result.add(q);
                return result;//return rgbToString(value, p, q);
            default:
                throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
        }
    }

    public List<Integer> rgbToHsv(int r, int g, int b) {

        List<Integer> result = new ArrayList<>();
        int min;    //Min. value of RGB
        int max;    //Max. value of RGB"
        int delMax; //Delta RGB value

        if (r > g) {
            min = g;
            max = r;
        } else {
            min = r;
            max = g;
        }
        if (b > max) {
            max = b;
        }
        if (b < min) {
            min = b;
        }

        delMax = max - min;

        float H = 0, S;
        float V = max;

        if (delMax == 0) {
            H = 0;
            S = 0;
        } else {
            S = delMax / 255f;
            if (r == max) {
                H = ((g - b) / (float) delMax) * 60;
            } else if (g == max) {
                H = (2 + (b - r) / (float) delMax) * 60;
            } else if (b == max) {
                H = (4 + (r - g) / (float) delMax) * 60;
            }
        }
        result.add((int) H);
        result.add((int) (S * 100));
        result.add((int) (V * 100));
        return result;
    }

    public List<Integer> rgbToHsb(int r, int g, int b) {
        float[] hsbvals = new float[3];
        Color.RGBtoHSB(r, g, b, hsbvals);
        List<Integer> result = new ArrayList<>();
        result.add((int) hsbvals[0]);
        result.add((int) hsbvals[1]);
        result.add((int) hsbvals[2]);
        return result;
    }

    public List<Integer> hsbToRgb(Integer hue, Integer saturation, Integer brightness) {
        int rgbInput = Color.HSBtoRGB(hue, saturation, brightness);
        int red = (rgbInput >> 16) & 0xFF;
        int green = (rgbInput >> 8) & 0xFF;
        int blue = rgbInput & 0xFF;
        List<Integer> result = new ArrayList<>();
        result.add((int) red);
        result.add((int) green);
        result.add((int) green);
        return result;

    }

    public BufferedImage getSubImage() {
        if (this.checkIconSummary) {
            Integer column = (cellBoundingSquare.getX0() - 30) / sizeWidthCell;
            Integer row = (cellBoundingSquare.getY0() - 30) / sizeHeightCell - 1;
            return this.matrixIconSummary.get(row).get(column).getImage();
        } else {

            Integer maxDragPointX, minDragPointX, maxDragPointY, minDragPointY;
            if (startDragPoint != null && endDragPoint != null) {
                if (startDragPoint.x < endDragPoint.x) {
                    minDragPointX = startDragPoint.x;
                    maxDragPointX = endDragPoint.x;
                } else {
                    minDragPointX = endDragPoint.x;
                    maxDragPointX = startDragPoint.x;
                }
                if (startDragPoint.y < endDragPoint.y) {
                    minDragPointY = startDragPoint.y;
                    maxDragPointY = endDragPoint.y;
                } else {
                    minDragPointY = endDragPoint.y;
                    maxDragPointY = startDragPoint.y;
                }
                return image.getSubimage(minDragPointX, minDragPointY, maxDragPointX - minDragPointX, maxDragPointY - minDragPointY);
            }

        }
        return null;
    }

    public List<InstancePoint> getListSelectedPointsIconSummary() {
        return listSelectedPointsIconSummary;
    }

    void setListConnections(List<Connection> listConn) {
        this.listConnections = listConn;
    }
}
