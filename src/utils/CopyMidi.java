/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author aurea
 */
public class CopyMidi {

    public static void main(String args[]) throws IOException {
        String pathLabels = "/home/aurea/Dropbox/Vispipeline 64/Vispipeline-Trunk/VisPipeline-Aurea/Density/label.label";
        ArrayList<String> labelsIds = new ArrayList<>();
        ArrayList<String> labelsPaths = new ArrayList<>();

        try {
            // Open the file that is the first 
            // command line parameter
            FileInputStream fstream = new FileInputStream(pathLabels);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                String[] datito = strLine.split(";");
                labelsIds.add(datito[0]);
                labelsPaths.add(datito[1]);

            }
            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
        }


        String format = "";
        String numberAttributes = "";
        String attributes = "";

        String pathData = "/home/aurea/Dropbox/Vispipeline 64/Vispipeline-Trunk/VisPipeline-Aurea/Density/projection.data";
        ArrayList<String> listIds = new ArrayList<>();
        ArrayList<String> listClusters = new ArrayList<>();

        try {
            // Open the file that is the first 
            // command line parameter
            FileInputStream fstream = new FileInputStream(pathData);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            format = br.readLine();
            strLine = br.readLine();
            numberAttributes = br.readLine();
            attributes = br.readLine();
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                String[] datito = strLine.split(";");
                listIds.add(datito[0]);
                listClusters.add(datito[3]);

            }
            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
        }

        Process process;
        int count = 0;
        int countRock = 0;
        int countClassica = 0;
        int countSertanejo = 0;
        for (int i = 0; i < listIds.size(); i++) {
            String id = listIds.get(i);
            String cluster = listClusters.get(i);
            String pathLabel = "";

            // System.out.println(cluster);
            for (int j = 0; j < labelsIds.size(); j++) {
                if (id.equals(labelsIds.get(j))) {
                    pathLabel = labelsPaths.get(j);

                    if (cluster.equals("1.0"))//clasica
                    {
                        String partPath;
                        partPath = "/home/aurea/Dropbox/" + pathLabel.substring(pathLabel.lastIndexOf("../") + 3);
                        String tmp = pathLabel.substring(pathLabel.lastIndexOf("database-midi/") + 14);
                        tmp = tmp.replace("/", "_");
                        tmp = tmp.replace(" ", "_");

                        String copyPath = "/home/aurea/classica/" + tmp;
                        copyPath = copyPath.replace("(", "");
                        copyPath = copyPath.replace(")", "");
                        partPath = partPath.replace(" ", "\\ ");
                        partPath = partPath.replace("(", "\\(");
                        partPath = partPath.replace(")", "\\)");
                        System.out.println("cp " + partPath + " " + copyPath);
                        //process = Runtime.getRuntime().exec("cp " + partPath + " " + copyPath);
                        count++;
                        countClassica++;
                        break;
                    } else {
                        if (cluster.equals("2.0"))//sertanejo
                        {
                            String partPath;
                            partPath = "/home/aurea/Dropbox/" + pathLabel.substring(pathLabel.lastIndexOf("../") + 3);
                            String tmp = pathLabel.substring(pathLabel.lastIndexOf("database-midi/") + 14);
                            tmp = tmp.replace("/", "_");
                            tmp = tmp.replace(" ", "_");

                            String copyPath = "/home/aurea/sertanejo/" + tmp;
                            copyPath = copyPath.replace("(", "");
                            copyPath = copyPath.replace(")", "");
                            partPath = partPath.replace(" ", "\\ ");
                            partPath = partPath.replace("(", "\\(");
                            partPath = partPath.replace(")", "\\)");
                            //System.out.println("cp " + partPath + " " + copyPath);
                            System.out.println("cp " + partPath + " " + copyPath);
                            String[] cmd = {"cp", partPath, copyPath};
                            //process = Runtime.getRuntime().exec("cp " + partPath + " " + copyPath);
                            count++;
                            countSertanejo++;
                            break;
                        } else {
                            if (cluster.equals("3.0"))//rock
                            {

                                String partPath;
                                partPath = "/home/aurea/Dropbox/" + pathLabel.substring(pathLabel.lastIndexOf("../") + 3);
                                String tmp = pathLabel.substring(pathLabel.lastIndexOf("database-midi/") + 14);
                                tmp = tmp.replace("/", "_");
                                tmp = tmp.replace(" ", "_");

                                String copyPath = "/home/aurea/rock/" + tmp;
                                copyPath = copyPath.replace("(", "");
                                copyPath = copyPath.replace(")", "");
                                partPath = partPath.replace(" ", "\\ ");
                                partPath = partPath.replace("(", "\\(");
                                partPath = partPath.replace(")", "\\)");
                                System.out.println("cp " + partPath + " " + copyPath);
                                //process = Runtime.getRuntime().exec("cp " + partPath + " " + copyPath);
                                count++;
                                countRock++;
                                break;
                            }
                        }
                    }
                }
            }

        }

        // System.out.println("Rock "+ countRock);
        // System.out.println("Sertanejo "+ countSertanejo);
        // System.out.println("Clasica "+ countClassica);
        // System.out.println(listIds.size());



    }
}
