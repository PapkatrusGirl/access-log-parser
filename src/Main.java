import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите путь к файлу: ");
            String path = scanner.nextLine();
            File file = new File(path);

            if (!file.exists() || !file.isFile()) {
                System.out.println("Файл не существует или это не файл");
                continue;
            }

            try (FileReader fileReader = new FileReader(file);
                 BufferedReader reader = new BufferedReader(fileReader)) {

                int totalLines = 0;
                int googleBotCount = 0;
                int yandexBotCount = 0;

                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.length() > 1024) {
                        throw new LineTooLongException("Слишком длинная строка: " + line.length() + " символов");
                    }

                    totalLines++;

                    int lastQuote = line.lastIndexOf("\"");
                    if (lastQuote <= 0) continue;

                    int secondLastQuote = line.lastIndexOf("\"", lastQuote - 1);
                    if (secondLastQuote == -1) continue;

                    String userAgent = line.substring(secondLastQuote + 1, lastQuote).trim();

                    int start = userAgent.indexOf('(');
                    int end = userAgent.indexOf(')');

                    boolean found = false;

                    while (start != -1 && end != -1 && start < end) {
                        String inside = userAgent.substring(start + 1, end).trim();

                        if (inside.contains("Googlebot")) {
                            googleBotCount++;
                            found = true;
                        } else if (inside.contains("YandexBot")) {
                            yandexBotCount++;
                            found = true;
                        }

                        if (found) break;

                        start = userAgent.indexOf('(', end);
                        end = userAgent.indexOf(')', end + 1);
                    }
                }

                double total = totalLines;
                double googlePercent = total > 0 ? googleBotCount / total * 100 : 0;
                double yandexPercent = total > 0 ? yandexBotCount / total * 100 : 0;

                System.out.println("Всего строк: " + totalLines);
                System.out.println("Запросы от Googlebot: " + googleBotCount);
                System.out.println("Запросы от YandexBot: " + yandexBotCount);
                System.out.printf("Доля Googlebot: %.2f%%\n", googlePercent);
                System.out.printf("Доля YandexBot: %.2f%%\n", yandexPercent);
                System.out.printf("Доля ботов: %.2f%%\n", googlePercent + yandexPercent);

            } catch (Exception e) {
                System.err.println("Ошибка при обработке файла: " + e.getMessage());
            }
        }
    }
}