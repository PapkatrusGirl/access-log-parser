import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int count = 1;
        while (true) {
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExist = file.exists();
            boolean isDirectory = file.isDirectory();
            if (!fileExist || isDirectory) {
                System.out.println("Файл не существует или является папкой");
                continue;
            }
            System.out.println("Путь указан верно");
            System.out.println("Это файл номер " + count++);
        }
    }
}