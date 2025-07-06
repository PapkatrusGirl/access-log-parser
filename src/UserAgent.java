public class UserAgent {
    private final String osType;
    private final String browser;

    public UserAgent(String userAgentString) {
        this.osType = parseOsType(userAgentString);
        this.browser = parseBrowser(userAgentString);
    }

    private String parseOsType(String userAgent) {
        if (userAgent.contains("Windows")) {
            return "Windows";
        } else if (userAgent.contains("Macintosh") || userAgent.contains("Mac OS X")) {
            return "macOS";
        } else if (userAgent.contains("Linux")) {
            return "Linux";
        } else if (userAgent.contains("Android")) {
            return "Android";
        } else if (userAgent.contains("iPhone") || userAgent.contains("iPad")) {
            return "iOS";
        } else {
            return "Unknown";
        }
    }

    private String parseBrowser(String userAgent) {
        userAgent = userAgent.toLowerCase();

        if (userAgent.contains("edge") || userAgent.contains("edg/")) {
            return "Edge";
        } else if (userAgent.contains("firefox") || userAgent.contains("fxios")) {
            return "Firefox";
        } else if (userAgent.contains("chrome") && !userAgent.contains("chromium")) {
            return "Chrome";
        } else if (userAgent.contains("safari") && !userAgent.contains("chrome")) {
            return "Safari";
        } else if (userAgent.contains("opera") || userAgent.contains("opr/")) {
            return "Opera";
        } else if (userAgent.contains("yandex")) {
            return "Yandex";
        } else {
            return "Other";
        }
    }

    public String getOsType() {
        return osType;
    }

    public String getBrowser() {
        return browser;
    }
}