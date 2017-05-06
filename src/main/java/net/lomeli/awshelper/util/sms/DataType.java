package net.lomeli.awshelper.util.sms;

public enum DataType {
    STRING("String"), NUMBER("Number"), BINARY("Binary");

    private final String type;

    DataType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
