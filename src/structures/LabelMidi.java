/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

/**
 *
 * @author aurea
 */
public class LabelMidi {
    String path;
    String name;

    public LabelMidi(String name, String path) {
        this.path = path;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLabel(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
}
