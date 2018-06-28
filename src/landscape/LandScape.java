/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package landscape;

//import vtk.*;

import javax.swing.*;
import java.awt.*;
import structures.InstancePoint;
import java.util.List;

/**
 * This example demonstrates how to combine a "geometric" implicit
 * function with noise at different frequencies to produce the
 * appearance of a landscape.
 */

public class LandScape extends JComponent  {
/*
 private vtkPanel renWin;

  public LandScape(List<InstancePoint> listPointsXY) {
    System.out.println(System.getProperty("java.library.path")); 
    // Create the buttons.
    renWin = new vtkPanel();

    vtkPlane plane = new vtkPlane();

    vtkPerlinNoise p1 = new vtkPerlinNoise();
    p1.SetFrequency(1, 1, 0);

    vtkPerlinNoise p2 = new vtkPerlinNoise();
    p2.SetFrequency(3, 5, 0);
    //p2.SetPhase(0.5, 0.5, 0);

    vtkPerlinNoise p3 = new vtkPerlinNoise();
    p3.SetFrequency(16, 16, 0);

    vtkImplicitSum sum = new vtkImplicitSum();
    sum.SetNormalizeByWeight(1);
    sum.AddFunction(plane);
    sum.AddFunction(p1, 0.2);
    sum.AddFunction(p2, 0.1);
    sum.AddFunction(p3, 0.02);

    vtkSampleFunction sample = new vtkSampleFunction();
    sample.SetImplicitFunction(sum);
    sample.SetSampleDimensions(65, 65, 20);
    sample.SetModelBounds(-1, 1, -1, 1, -0.5, 0.5);
    sample.ComputeNormalsOff();
    vtkContourFilter surface = new vtkContourFilter();
    surface.SetInput(sample.GetOutput());
    surface.SetValue(0, 0.0);

    vtkPolyDataNormals smooth = new vtkPolyDataNormals();
    smooth.SetInput(surface.GetOutput());
    smooth.SetFeatureAngle(90);

    vtkPolyDataMapper mapper = new vtkPolyDataMapper();
    mapper.SetInput(smooth.GetOutput());
    mapper.ScalarVisibilityOff();
    vtkActor actor = new vtkActor();
    actor.SetMapper(mapper);
    actor.GetProperty().SetColor(0.4, 0.2, 0.1);

    // Add the actors to the renderer, set the background and size
    renWin.GetRenderer().AddActor(actor);
    renWin.GetRenderer().SetBackground(1, 1, 1);
    renWin.GetRenderer().GetActiveCamera().Elevation(-45);
    renWin.GetRenderer().GetActiveCamera().Azimuth(10);
    renWin.GetRenderer().GetActiveCamera().Dolly(1.35);
    renWin.GetRenderer().ResetCameraClippingRange();

    // Setup pabel
    setLayout(new BorderLayout());
    add(renWin, BorderLayout.CENTER);
  }


  public vtkPanel getRenWin() {
    return renWin;
  }


  public static void main(String s[]) {
    LandScape panel = new LandScape(null);

    JFrame frame = new JFrame("LandScape");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800,400);
    frame.getContentPane().add("Center", panel);
    frame.setVisible(true);
  }*/
}