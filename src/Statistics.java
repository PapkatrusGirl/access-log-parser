import java.time.Duration;
import java.time.LocalDateTime;

public class Statistics {
    private long totalTraffic; // Используем long вместо int
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private int processedCount;

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
}