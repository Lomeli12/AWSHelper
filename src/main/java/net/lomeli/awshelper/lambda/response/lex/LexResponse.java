package net.lomeli.awshelper.lambda.response.lex;

import java.util.Map;

import net.lomeli.awshelper.lambda.response.LambdaResponse;

public class LexResponse extends LambdaResponse {
    private DialogAction dialogAction;

    public LexResponse() {
        super();
    }

    public LexResponse(Map<String, String> sessionAttributes, DialogAction action) {
        super(sessionAttributes);
        this.dialogAction = action;
    }

    public DialogAction getDialogAction() {
        return dialogAction;
    }

    @Override
    public String toString() {
        return String.format("{sessionAttributes=%s, dialogAction=%s}", getSessionAttributes().toString(), dialogAction.toString());
    }
}
