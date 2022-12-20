package dataRecording;

import java.util.ArrayList;

public class Attribute {

    private String name;
    private ArrayList<Condition> conditions;

    public Attribute(String name,  ArrayList<Condition> conditions) {
        this.name = name;
        this.conditions = conditions;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Condition> getConditions() {
        return conditions;
    }
}
