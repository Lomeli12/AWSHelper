package net.lomeli.awshelper.util.sms;

public class InvalidPhoneNumberFormatException extends Exception {
    public InvalidPhoneNumberFormatException() {
        super();
    }

    public InvalidPhoneNumberFormatException(String number) {
        super(number + " does not match E.164 format.!");
    }
}
