package net.lomeli.awshelper.lambda.response.lex;

import net.lomeli.awshelper.lambda.response.lex.message.LexMessage;

public class DialogAction {
    private ActionType type;
    private FulfillmentState fulfillmentState;
    private LexMessage message;
    private String intentName;

    public DialogAction() {
    }

    public DialogAction(ActionType type, FulfillmentState state, LexMessage message, String intent) {
        this.type = type;
        this.fulfillmentState = state;
        this.message = message;
        this.intentName = intent;
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

    @Override
    public String toString() {
        return String.format("{type=%s, fulfillmentState=%s, message=%s, intentName=%s}", getType(), getFulfillmentState(),
                getMessage().toString(), getIntentName());
    }
}
