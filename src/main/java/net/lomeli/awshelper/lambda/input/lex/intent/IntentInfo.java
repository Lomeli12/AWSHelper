package net.lomeli.awshelper.lambda.input.lex.intent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IntentInfo {
    private String name;
    private Map<String, String> slots;
    private ConfirmStatus confirmationStatus;

    public IntentInfo(){
        this.slots = new HashMap<String, String>();
    }

    public IntentInfo(String name, Map<String, String> slots, ConfirmStatus confirmationStatus, String inputTranscript) {
        this();
        this.name = name;
        if (slots != null && !slots.isEmpty()) this.slots.putAll(slots);
        this.confirmationStatus = confirmationStatus;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getSlots() {
        return Collections.unmodifiableMap(slots);
    }

    public ConfirmStatus getConfirmationStatus() {
        return confirmationStatus;
    }

    @Override
    public String toString() {
        return String.format("{name=%s, slots=%s, confirmationStstus=%s}",
                getName(), getSlots().toString(), getConfirmationStatus());
    }
}
