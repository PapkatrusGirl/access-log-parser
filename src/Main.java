import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = 1;

        while (true) {
            String path = scanner.nextLine();
            File file = new File(path);
            boolean fileExist = file.exists();
            boolean isDirectory = file.isDirectory();
            if (!fileExist || isDirectory) {
                System.out.println("Файл не существует или является папкой");
                continue;
            }
            System.out.println("Путь указан верно");
            System.out.println("Это файл номер " + count++);

            try (FileReader fileReader = new FileReader(file);
                 BufferedReader reader = new BufferedReader(fileReader)) {

                String line;
                int totalLines = 0;
                int maxLength = 0;
                int minLength = Integer.MAX_VALUE;

                while ((line = reader.readLine()) != null) {
                    int length = line.length();

                    if (length > 1024) {
                        throw new LineTooLongException("Строка слишком длинная: " + length + " символов");
                    }

                    totalLines++;
                    maxLength = Math.max(maxLength, length);
                    minLength = Math.min(minLength, length);
                }

                System.out.println("Всего строк: " + totalLines);
                System.out.println("Самая длинная строка: " + maxLength);
                System.out.println("Самая короткая строка: " + minLength);

            } catch (Exception e) {
                System.out.println("Ошибка при чтении файла: " + e.getMessage());
            }
        }
    }
}