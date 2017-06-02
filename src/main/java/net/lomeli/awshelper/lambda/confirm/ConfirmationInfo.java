package net.lomeli.awshelper.lambda.confirm;

import java.util.*;

import net.lomeli.awshelper.lambda.input.lex.LexInput;
import net.lomeli.awshelper.lambda.response.lex.ActionType;
import net.lomeli.awshelper.lambda.response.lex.DialogAction;
import net.lomeli.awshelper.lambda.response.lex.FulfillmentState;
import net.lomeli.awshelper.lambda.response.lex.LexResponse;
import net.lomeli.awshelper.lambda.response.lex.message.ContentType;
import net.lomeli.awshelper.lambda.response.lex.message.LexMessage;

public abstract class ConfirmationInfo {
    private Map<String, SlotConfirmation> slots;
    private String lastSlot;

    public ConfirmationInfo() {
        this.slots = new HashMap<>();
    }

    public List<SlotConfirmation> getSlotInfo() {
        List<SlotConfirmation> slots = new ArrayList<>();
        slots.addAll(this.slots.values());
        return Collections.unmodifiableList(slots);
    }

    public void addSlot(SlotConfirmation slot) {
        this.slots.put(slot.getName(), slot);
        lastSlot = slot.getName();
    }

    public void setSlotValue(String key, String value) {
        SlotConfirmation slot = this.slots.get(key);
        if (slot == null) return;
        slot.setValue(value);
        this.slots.put(key, slot);
    }

    public String getSlotValue(String key) {
        SlotConfirmation slot = this.slots.get(key);
        return slot != null ? slot.getValue() : null;
    }

    public abstract String confirmSlots();

    public abstract String finalResponse(boolean yes);

    public String getLastSlot() {
        return lastSlot;
    }

    @Override
    public String toString() {
        return String.format("{lastSlot=%s, slots=%s}", lastSlot != null ? lastSlot : "null", slots);
    }

    public static LexResponse respondToConfirmation(LexInput lexInput, ConfirmationInfo confirmationInfo) {
        LexResponse response = null;
        DialogAction action = null;
        boolean finish = "true".equalsIgnoreCase(lexInput.getSessionAttributes().get("finished"));
        boolean fullSlots = "true".equalsIgnoreCase(lexInput.getSessionAttributes().get("fullSlots"));
        String prevSlot = lexInput.getSessionAttributes().get("prevSlot");
        System.out.printf("%s %s %s\n", finish, fullSlots, prevSlot != null ? prevSlot : "null");
        if (!fullSlots) {
            for (int i = 0; i < confirmationInfo.getSlotInfo().size(); i++) {
                SlotConfirmation slot = confirmationInfo.getSlotInfo().get(i);
                if (!lexInput.getCurrentIntent().getSlots().containsKey(slot.getName())) {
                    LexMessage message = new LexMessage(ContentType.PlainText, slot.getQuestion());
                    action = new DialogAction(ActionType.ConfirmIntent, null, message,
                            lexInput.getCurrentIntent().getName(), lexInput.getCurrentIntent().getSlots());
                    action.setSlot(slot.getName(), "true");
                    for (SlotConfirmation slot0 : confirmationInfo.getSlotInfo()) {
                        if (!action.getSlots().containsKey(slot0.getName()))
                            action.setSlot(slot0.getName(), null);
                    }
                    if (slot.getName().equals(confirmationInfo.getLastSlot()))
                        fullSlots = true;
                    if (prevSlot != null && !prevSlot.equals("")) {
                        boolean flag = isConfirmation(lexInput.getInputTranscript());
                        action.setSlot(prevSlot, flag ? "true" : "false");
                    }
                    prevSlot = slot.getName();
                    finish = false;
                    break;
                }
            }
        } else if (!finish) {
            boolean flag = isConfirmation(lexInput.getInputTranscript());
            if (prevSlot != null && !prevSlot.equals(""))
                confirmationInfo.setSlotValue(prevSlot, flag ? "true" : "false");
            LexMessage message = new LexMessage(ContentType.PlainText, confirmationInfo.confirmSlots());
            action = new DialogAction(ActionType.ConfirmIntent, null, message,
                    lexInput.getCurrentIntent().getName(), lexInput.getCurrentIntent().getSlots());
            if (prevSlot != null && !prevSlot.equals(""))
                action.setSlot(prevSlot, flag ? "true" : "false");
            finish = true;
        } else {
            LexMessage message = new LexMessage(ContentType.PlainText,
                    confirmationInfo.finalResponse(lexInput.getInputTranscript().equalsIgnoreCase("yes")));
            action = new DialogAction(ActionType.Close, FulfillmentState.Fulfilled, message, null, null);
            finish = false;
            fullSlots = false;
            prevSlot = null;
        }
        if (action != null) {
            response = new LexResponse(lexInput.getSessionAttributes(), action);
            response.setSessionAttributes("finished", finish ? "true" : "false");
            response.setSessionAttributes("fullSlots", fullSlots ? "true" : "false");
            response.setSessionAttributes("prevSlot", prevSlot != null ? prevSlot : "");
        }
        return response;
    }

    public static boolean isConfirmation(String str) {
        str = str.trim().toLowerCase();
        return str.contains("yes")
                || str.contains("ok")
                || str.contains("okay")
                || str.contains("whatever")
                || str.contains("yeah")
                || str.contains("yep")
                || str.contains("yup");
    }
}
