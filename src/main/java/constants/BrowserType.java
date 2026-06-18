package constants;

public enum BrowserType {

    CHROME("chrome"),
    FIREFOX("firefox"),
    WEBKIT("webkit"),
    MSEDGE("msedge");

    private final String value;
    BrowserType(String value) {
        this.value = value;
    };

    public String getValue() { return value; }

    public static BrowserType fromValue(String value) {
        if (value == null) { return  CHROME; }
        for (BrowserType browserType : BrowserType.values()) {
            if (browserType.getValue().equals(value)) {
                return browserType;
            }
        }
        throw new IllegalArgumentException();
    }

}
