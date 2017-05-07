package net.lomeli.awshelper.lambda.confirm;

public class SlotConfirmation {
    private String name, question, value;

    public SlotConfirmation(String name, String question, String value) {
        this.name = name;
        this.question = question;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getQuestion() {
        return question;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("{name=%s, question=%s, value=%s}", name, question, value != null ? value : "null");
    }
}
