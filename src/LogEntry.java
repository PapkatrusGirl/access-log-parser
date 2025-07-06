import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class LogEntry {
    private final String ipAddress;
    private final LocalDateTime time;
    private final HttpMethod method;
    private final String path;
    private final int responseCode;
    private final int dataSize;
    private final String referer;
    private final UserAgent userAgent;

    public LogEntry(String logLine) {
        String[] parts = logLine.split(" ", 12);
        this.ipAddress = parts[0];
        String dateTimeStr = parts[3].substring(1) + " " + parts[4].replace("]", "");
        this.time = parseDateTime(dateTimeStr);
        String request = parts[5] + " " + parts[6] + " " + parts[7];
        request = request.substring(1, request.length() - 1);
        String[] requestParts = request.split(" ");
        this.method = HttpMethod.valueOf(requestParts[0]);
        this.path = requestParts[1];
        this.responseCode = Integer.parseInt(parts[8]);
        this.dataSize = Integer.parseInt(parts[9]);
        this.referer = parts[10].equals("\"-\"") ? null : parts[10].substring(1, parts[10].length() - 1);
        String userAgentStr = parts[11];
        userAgentStr = userAgentStr.substring(1, userAgentStr.length() - 1); // Удаляем кавычки
        this.userAgent = new UserAgent(userAgentStr);
    }

    private LocalDateTime parseDateTime(String dateTimeStr) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("dd/MMM/yyyy:HH:mm:ss Z")
                .toFormatter(Locale.ENGLISH);
        return LocalDateTime.parse(dateTimeStr, formatter);
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getDataSize() {
        return dataSize;
    }

    public String getReferer() {
        return referer;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }
}
