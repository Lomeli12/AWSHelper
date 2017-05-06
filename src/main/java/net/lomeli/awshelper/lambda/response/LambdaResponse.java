package net.lomeli.awshelper.lambda.response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class LambdaResponse {
    private Map<String, String> sessionAttributes;

    public LambdaResponse() {
        this.sessionAttributes = new HashMap<String, String>();
    }

    public LambdaResponse(Map<String, String> sessionAttributes) {
        this();
        if (sessionAttributes != null && !sessionAttributes.isEmpty()) this.sessionAttributes.putAll(sessionAttributes);
    }

    public Map<String, String> getSessionAttributes() {
        return Collections.unmodifiableMap(sessionAttributes);
    }

    @Override
    public String toString() {
        return String.format("{sessionAttributes=%s}", getSessionAttributes().toString());
    }
}