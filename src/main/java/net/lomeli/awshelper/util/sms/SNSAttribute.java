package net.lomeli.awshelper.util.sms;

public enum SNSAttribute {
    SENDER_ID("AWS.SNS.SMS.SenderID", DataType.STRING),
    MAX_PRICE("AWS.SNS.SMS.MaxPrice", DataType.NUMBER),
    SMS_TYPE("AWS.SNS.SMS.SMSType", DataType.STRING);
    private final String name;
    private final DataType dataType;

    SNSAttribute(String name, DataType dataType) {
        this.name = name;
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public DataType getDataType() {
        return dataType;
    }
}
