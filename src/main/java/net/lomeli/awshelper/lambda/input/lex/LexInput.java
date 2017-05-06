package net.lomeli.awshelper.lambda.input.lex;

import java.util.Map;

import net.lomeli.awshelper.lambda.input.InvokeSource;
import net.lomeli.awshelper.lambda.input.LambdaInput;
import net.lomeli.awshelper.lambda.input.lex.intent.IntentInfo;

public class LexInput extends LambdaInput {
    private BotInfo bot;
    private DialogMode outputDialogMode;
    private IntentInfo currentIntent;
    private String inputTranscript;

    public LexInput() {
        super();
    }

    public LexInput(String messageVersion, String userId, InvokeSource invokeSource, Map<String, String> sessionAttributes, BotInfo bot, DialogMode outputDialogMode, IntentInfo currentIntent, String inputTranscript) {
        super(messageVersion, userId, invokeSource, sessionAttributes);
        this.bot = bot;
        this.outputDialogMode = outputDialogMode;
        this.currentIntent = currentIntent;
        this.inputTranscript = inputTranscript;
    }

    public BotInfo getBot() {
        return bot;
    }

    public DialogMode getDialogMode() {
        return outputDialogMode;
    }

    public IntentInfo getCurrentIntent() {
        return currentIntent;
    }

    public String getInputTranscript() {
        return inputTranscript;
    }

    @Override
    public String toString() {
        return String.format("{messageVersion=%s, userId=%s, invocationSource=%s, sessionAttributes=%s, bot=%s, " +
                        "outputDialogMode=%s, currentIntent=%s, inputTranscript=%s}",
                getMessageVersion(), getUserId(), getInvocationSource(), getSessionAttributes().toString(),
                getBot().toString(), getDialogMode(), getCurrentIntent().toString(), getInputTranscript());
    }
}
