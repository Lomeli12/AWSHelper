package net.lomeli.awshelper.util.sms;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.lomeli.awshelper.util.MiscUtil;

public class SMSMessage {
    private static final Pattern PHONE_NUMBER_FORMAT = Pattern.compile("^\\+?[1-9]\\d{1,14}$");
    private static final Pattern NUMBER_SYNTAX = Pattern.compile("[-.()]");
    private Map<String, MessageAttributeValue> smsAttributes;
    private String message, phoneNumber;
    private AmazonSNSClient snsClient;

    public SMSMessage(String phoneNumber, String message) throws InvalidPhoneNumberFormatException {
        this.phoneNumber = verifyNumber(phoneNumber);
        if (this.phoneNumber.length() == 10) this.phoneNumber = "+1" + this.phoneNumber;
        Matcher match = PHONE_NUMBER_FORMAT.matcher(this.phoneNumber);
        if (!match.matches()) throw new InvalidPhoneNumberFormatException(this.phoneNumber);
        this.message = message;
        this.snsClient = (AmazonSNSClient) AmazonSNSClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
        this.smsAttributes = new HashMap<>();
    }

    String verifyNumber(String phoneNumber) {
        phoneNumber = phoneNumber.trim().toLowerCase();
        Matcher match = NUMBER_SYNTAX.matcher(phoneNumber);
        if (match.find()) return verifyNumber(phoneNumber.replaceAll("[-.()]", ""));
        if (MiscUtil.isStringNumeric(phoneNumber)) return phoneNumber;
        String[] separateNumbers = phoneNumber.split(" ");
        StringBuilder newNumber = new StringBuilder();
        Arrays.asList(separateNumbers).stream().filter(potentialNumber -> potentialNumber != null && !potentialNumber.isEmpty())
                .forEach(potentialNumber -> {
            Object val = MiscUtil.strNums.get(potentialNumber.toLowerCase());
            if (val != null) newNumber.append(val.toString());
        });
        return newNumber.toString();
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
