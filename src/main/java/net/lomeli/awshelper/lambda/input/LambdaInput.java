package net.lomeli.awshelper.lambda.input;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class LambdaInput {
    private String messageVersion;
    private String userId;
    private InvokeSource invocationSource;
    private Map<String, String> sessionAttributes;

    public LambdaInput() {
        this.sessionAttributes = new HashMap<>();
    }

    public LambdaInput(String messageVersion, String userId, InvokeSource invokeSource, Map<String, String> sessionAttributes) {
        this();
        this.messageVersion = messageVersion;
        this.userId = userId;
        this.invocationSource = invokeSource;
        if (sessionAttributes != null && !sessionAttributes.isEmpty()) this.sessionAttributes.putAll(sessionAttributes);
    }

    public String getMessageVersion() {
        return messageVersion;
    }

    public String getUserId() {
        return userId;
    }

    public InvokeSource getInvocationSource() {
        return invocationSource;
    }

    public Map<String, String> getSessionAttributes() {
        return Collections.unmodifiableMap(sessionAttributes);
    }

    @Override
    public String toString() {
        return String.format("{messageVersion=%s, userId=%s, invocationSource=%s, sessionAttributes=%s}",
                getMessageVersion(), getUserId(), getInvocationSource(), getSessionAttributes().toString());
    }
}
