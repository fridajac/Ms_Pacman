package dataRecording;

import java.util.ArrayList;

public class Attribute {

    private String name;
    private ArrayList<String> conditions;

    public Attribute(String name,  ArrayList<String> conditions) {
        this.name = name;
        this.conditions = conditions;
    }

    public ArrayList<String> getConditions() {
        return conditions;
    }
}
