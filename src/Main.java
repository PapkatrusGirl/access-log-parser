import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = 1;

        while (true) {
            System.out.println("Введите путь к файлу логов:");
            String path = scanner.nextLine();
            File file = new File(path);

            if (!file.exists() || file.isDirectory()) {
                System.out.println("Файл не существует или является папкой");
                continue;
            }

            System.out.println("Путь указан верно");
            System.out.println("Анализ файла номер " + count++);

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                Statistics stats = new Statistics();
                List<String> sampleLines = new ArrayList<>();
                int totalLines = 0;

                String line;
                while ((line = reader.readLine()) != null) {
                    try {
                        LogEntry entry = new LogEntry(line);
                        stats.addEntry(entry);
                        sampleLines.add(line);
                        totalLines++;
                    } catch (Exception e) {
                        System.out.println("Ошибка в строке " + (totalLines + 1) + ": " + e.getMessage());
                    }
                }

                System.out.println("\nОбщая статистика:");
                System.out.println("Всего строк: " + totalLines);
                System.out.println("Обработано строк: " + stats.getProcessedCount());
                System.out.println("Общий трафик: " + stats.getTotalTraffic() + " байт");
                System.out.printf("Средний трафик в час: %.2f байт/час\n", stats.getTrafficRate());

                System.out.println("\nСуществующие страницы:");
                for (String page : stats.getExistingPages()) {
                    System.out.println("- " + page);
                }

                System.out.println("\nНесуществующие страницы:");
                for (String page : stats.getNotFoundPages()) {
                    System.out.println("- " + page);
                }

                System.out.println("\nСтатистика браузеров:");
                Map<String, Double> browserStats = stats.getBrowserStatistics();
                for (Map.Entry<String, Double> entry : browserStats.entrySet()) {
                    System.out.printf("- %s: %.2f%%\n", entry.getKey(), entry.getValue() * 100);
                }

                System.out.println("\nСтатистика операционных систем:");
                Map<String, Double> osStats = stats.getOsStatistics();
                for (Map.Entry<String, Double> entry : osStats.entrySet()) {
                    System.out.printf("- %s: %.2f%%\n", entry.getKey(), entry.getValue() * 100);
                }

            } catch (Exception e) {
                System.out.println("Ошибка при чтении файла: " + e.getMessage());
            }
        }
    }
}