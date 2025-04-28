import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите первое число:");
        int FirstNumber = scanner.nextInt();
        System.out.println("Введите второе число:");
        int SecondNumber = scanner.nextInt();

        int sum = FirstNumber + SecondNumber;
        int difference = FirstNumber - SecondNumber;
        int product = FirstNumber * SecondNumber;
        double quotient = (double) FirstNumber / SecondNumber;
        System.out.println("Сумма:" + sum);
        System.out.println("Разность:" + difference);
        System.out.println("Произведение:" +product);
        System.out.println("Частное:" + quotient);
    }
}