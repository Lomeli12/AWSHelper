package net.lomeli.awshelper.lambda.response.lex.message;

public class LexMessage {
    private ContentType contentType;
    private String content;

    public LexMessage() {
    }

    public LexMessage(ContentType type, String content) {
        this.contentType = type;
        this.content = content;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return String.format("{contentType=%s, content=%s}", getContentType(), getContent());
    }
}
