package dataRecording;

public class Condition {

    private Operator operator;
    private int value;

    public Condition(Operator operator, int value) {
        this.operator = operator;
        this.value = value;
    }

    public Operator getOperator() {
        return operator;
    }

    public int getValue() {
        return value;
    }
}
