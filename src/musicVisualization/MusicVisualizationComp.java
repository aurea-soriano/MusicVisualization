/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicVisualization;

import distance.DistanceMatrix;
import java.awt.Color;
import java.io.IOException;
import matrix.AbstractMatrix;
import vispipelinebasics.annotations.Param;
import vispipelinebasics.annotations.VisComponent;
import vispipelinebasics.interfaces.AbstractComponent;
import vispipelinebasics.interfaces.AbstractParametersView;

/**
 *
 * @author aurea
 */
@VisComponent(hierarchy = "Density",
name = "Density",
description = "description",
howtocite = "")
public class MusicVisualizationComp implements AbstractComponent {

    @Override
    public void execute() throws IOException {
        if (projection != null) {
            if (title == "" || title == null) {
                title = " ";
            }
            MusicVisualization frame = new MusicVisualization(title + " ", 0, "projection", basicColor);
            if (matrix != null) {
                frame.setMatrix(matrix);
            }
            if (distanceMatrix != null) {
                frame.setDistanceMatrix(distanceMatrix);
            }
            frame.setProjection(projection);
            frame.setPathsIcones(pathsIcone);
            frame.setPathsLabel(pathsLabel);
            frame.setPathsPlaylists(pathsPlaylist);
            frame.setNameX("X");
            frame.setNameX("Y");
            frame.loadInitial();
            frame.setSize(1300, 650);
            frame.setVisible(true);


        } else {
            throw new IOException("A projection should be provided.");
        }
    }

    public void input(@Param(name = "2D projection") AbstractMatrix projection, @Param(name = "points matrix") AbstractMatrix matrix) {
        this.projection = projection;
        this.matrix = matrix;
    }

    public void input(@Param(name = "2D projection") AbstractMatrix projection) {
        this.projection = projection;

    }

    public void input(@Param(name = "2D projection") AbstractMatrix projection, @Param(name = "points matrix") AbstractMatrix matrix, @Param(name = "distance matrix") DistanceMatrix distanceMatrix) {
        this.projection = projection;
        this.matrix = matrix;
        this.distanceMatrix = distanceMatrix;
    }

    public void input(@Param(name = "2D projection") AbstractMatrix projection, @Param(name = "distance matrix") DistanceMatrix distanceMatrix) {
        this.projection = projection;
        this.distanceMatrix = distanceMatrix;

    }

    /* public void input(@Param(name = "2D projection") AbstractMatrix projection){
     this.projection = projection;
     }*/
    @Override
    public AbstractParametersView getParametersEditor() {
        if (paramview == null) {
            paramview = new MusicVisualizationParamView(this);
        }

        return paramview;
    }

    @Override
    public void reset() {
        projection = null;
        matrix = null;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPathsLabel(String pathsLabel) {
        this.pathsLabel = pathsLabel;
    }

    public String getPathsLabel() {
        return pathsLabel;
    }

    public void setPathsIcone(String pathsIcone) {
        this.pathsIcone = pathsIcone;
    }

    public String getPathsIcone() {
        return pathsIcone;
    }

    public void setPathsPlaylist(String pathsPlaylist) {
        this.pathsPlaylist = pathsPlaylist;
    }

    public String getPathsPlaylist() {
        return pathsPlaylist;
    }

    public Color getBasicColor() {
        return basicColor;
    }

    public void setBasicColor(Color basicColor) {
        this.basicColor = basicColor;
    }
    public static final long serialVersionUID = 1L;
    private String title = "";
    private String pathsLabel = "";
    private String pathsIcone = "";
    private String pathsPlaylist = "";
    private transient AbstractMatrix projection;
    private transient AbstractMatrix matrix;
    private transient DistanceMatrix distanceMatrix;
    private transient MusicVisualizationParamView paramview;
    private Color basicColor;
}
