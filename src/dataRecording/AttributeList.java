package dataRecording;

import java.util.ArrayList;

public class AttributeList {

    private ArrayList<String> attributes;

    public AttributeList() {
        this.attributes = new ArrayList<>();
        attributes.add("Is ghosts scared?");
        attributes.add("Score");
        attributes.add("Distance to dangerous ghost");
        attributes.add("Distance to non-dangerous ghost");
    }

    public ArrayList<String> getAttributes() {
        return attributes;
    }
}
