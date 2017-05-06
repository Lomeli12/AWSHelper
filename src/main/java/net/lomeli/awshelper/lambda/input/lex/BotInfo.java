package net.lomeli.awshelper.lambda.input.lex;

public class BotInfo {
    private String name;
    private String alias;
    private String version;

    public BotInfo() {
    }

    public BotInfo(String name, String alias, String version) {
        this.name = name;
        this.alias = alias;
        this.version = version;
    }

    public String getAlias() {
        return alias;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return String.format("{name=%s, alias=%s, version=%s}", getName(), getAlias(), getVersion());
    }
}
