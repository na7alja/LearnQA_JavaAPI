package model;

public class UserAgentResponse {
    String platform;
    String browser;
    String device;

    public UserAgentResponse(String platform, String browser, String device) {
        this.platform = platform;
        this.browser = browser;
        this.device = device;
    }

    public String getPlatform() {
        return platform;
    }

    public String getBrowser() {
        return browser;
    }

    public String getDevice() {
        return device;
    }
}
