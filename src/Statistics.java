import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Statistics {
    private long totalTraffic; // Используем long вместо int
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private int processedCount;
    private final Set<String> existingPages = new HashSet<>();
    private final Set<String> notFoundPages = new HashSet<>();
    private final Map<String, Integer> osCounts = new HashMap<>();
    private final Map<String, Integer> browserCounts = new HashMap<>();


    public Statistics() {
        this.totalTraffic = 0L;
        this.processedCount = 0;
    }

    public void addEntry(LogEntry entry) {
        if (entry == null) return;

        this.totalTraffic += entry.getDataSize();

        LocalDateTime entryTime = entry.getTime();
        if (this.minTime == null || entryTime.isBefore(this.minTime)) {
            this.minTime = entryTime;
        }
        if (this.maxTime == null || entryTime.isAfter(this.maxTime)) {
            this.maxTime = entryTime;
        }
        this.processedCount++;

        if (entry.getResponseCode() == 200) {
            existingPages.add(entry.getPath());
        } else if (entry.getResponseCode() == 404) {
            notFoundPages.add(entry.getPath());
        }

        String os = entry.getUserAgent().getOsType();
        osCounts.put(os, osCounts.getOrDefault(os, 0) + 1);

        String browser = entry.getUserAgent().getBrowser();
        browserCounts.put(browser, browserCounts.getOrDefault(browser, 0) + 1);

    }

    public Set<String> getNotFoundPages() {
        return new HashSet<>(notFoundPages);
    }

    public Map<String, Double> getOsStatistics() {
        Map<String, Double> result = new HashMap<>();
        if (processedCount == 0) return result;

        for (Map.Entry<String, Integer> entry : osCounts.entrySet()) {
            result.put(entry.getKey(), (double) entry.getValue() / processedCount);
        }
        return result;
    }

    public Map<String, Double> getBrowserStatistics() {
        Map<String, Double> result = new HashMap<>();
        if (processedCount == 0) return result;

        for (Map.Entry<String, Integer> entry : browserCounts.entrySet()) {
            result.put(entry.getKey(), (double) entry.getValue() / processedCount);
        }
        return result;
    }

    public double getTrafficRate() {
        if (this.minTime == null || this.maxTime == null || this.processedCount == 0) {
            return 0.0;
        }

        long hours = Duration.between(this.minTime, this.maxTime).toHours();
        if (hours == 0) hours = 1;

        return (double) this.totalTraffic / hours;
    }

    public long getTotalTraffic() {
        return totalTraffic;
    }

    public int getProcessedCount() {
        return processedCount;
    }

    public Set<String> getExistingPages() {
        return new HashSet<>(existingPages);
    }
}