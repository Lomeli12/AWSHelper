package net.lomeli.awshelper.util.sms;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMSMessage {
    private static final Pattern NUMBER_FORMAT = Pattern.compile("^\\+?[1-9]\\d{1,14}$");
    private Map<String, MessageAttributeValue> smsAttributes;
    private String message, phoneNumber;
    private AmazonSNSClient snsClient;

    public SMSMessage(String phoneNumber, String message) throws InvalidPhoneNumberFormatException {
        Matcher match = NUMBER_FORMAT.matcher(phoneNumber);
        if (!match.matches()) throw new InvalidPhoneNumberFormatException(phoneNumber);
        this.phoneNumber = phoneNumber;
        if (this.phoneNumber.length() == 10) this.phoneNumber = "+1" + phoneNumber;
        this.message = message;
        this.snsClient = (AmazonSNSClient) AmazonSNSClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
        this.smsAttributes = new HashMap<>();
    }

    public SMSMessage addAttribute(SNSAttribute attribute, Object value) {
        if (attribute.getDataType() != DataType.BINARY) {
            smsAttributes.put(attribute.getName(), new MessageAttributeValue()
                    .withStringValue(value.toString())
                    .withDataType(attribute.getDataType().getType()));
        } else {
            smsAttributes.put(attribute.getName(), new MessageAttributeValue()
                    .withBinaryValue((ByteBuffer) value)
                    .withDataType(attribute.getDataType().getType()));
        }
        return this;
    }

    public AmazonSNSClient getSNSClient() {
        return snsClient;
    }

    public String getMessage() {
        return message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Map<String, MessageAttributeValue> getSmsAttributes() {
        return Collections.unmodifiableMap(smsAttributes);
    }

    public String sendMessage() {
        PublishResult result = snsClient.publish(new PublishRequest()
            .withMessage(message)
            .withPhoneNumber(phoneNumber)
            .withMessageAttributes(smsAttributes));
        return result.toString();
    }
}
