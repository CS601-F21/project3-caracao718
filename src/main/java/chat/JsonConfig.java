package chat;

public class JsonConfig {
    private String text;

    public JsonConfig(String content) {
        this.text = content;
    }

//    public String getContent() {
//        return content;
//    }

    @Override
    public String toString() {
        return "{" +
                "\"channel\":\"" + "cs601-project3" + '\"' + "," +
                "\"text\":\"" + text + '\"' +
                '}';
    }
}
