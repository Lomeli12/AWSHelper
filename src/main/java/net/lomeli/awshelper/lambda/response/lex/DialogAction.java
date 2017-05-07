package net.lomeli.awshelper.lambda.response.lex;

import java.util.HashMap;
import java.util.Map;

import net.lomeli.awshelper.lambda.response.lex.message.LexMessage;

public class DialogAction {
    private ActionType type;
    private FulfillmentState fulfillmentState;
    private LexMessage message;
    private String intentName;
    private Map<String, String> slots;

    public DialogAction() {
    }

    public DialogAction(ActionType type, FulfillmentState state, LexMessage message, String intent, Map<String, String> slots) {
        this.type = type;
        this.fulfillmentState = state;
        this.message = message;
        this.intentName = intent;
        this.slots = new HashMap<>();
        if (slots != null && !slots.isEmpty())
            this.slots.putAll(slots);
    }

    public LexMessage getMessage() {
        return message;
    }

    public String getIntentName() {
        return intentName;
    }

    public ActionType getType() {
        return type;
    }

    public FulfillmentState getFulfillmentState() {
        return fulfillmentState;
    }

    public void setSlot(String key, String value) {
        slots.put(key, value);
    }

    public Map<String, String> getSlots() {
        return slots;
    }

    @Override
    public String toString() {
        return String.format("{type=%s, fulfillmentState=%s, message=%s, slots=%s, intentName=%s}", getType(), getFulfillmentState(),
                getMessage().toString(), getSlots().toString(), getIntentName());
    }
}
