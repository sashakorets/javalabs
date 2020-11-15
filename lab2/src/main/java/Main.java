import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter name of file .csv ");
        String inDate = scanner.next();
        System.out.print("\nEnter delim in .csv: ");
        String inDelim = scanner.next();
        System.out.print("\nEnter a file name to output .txt: ");
        String outDate = scanner.next();
        System.out.print("\nEnter delim in .txt: ");
        String outDelim = scanner.next();
        Parser parser = new Parser(inDelim, outDelim);
        parser.checkFile(inDate, outDate);
    }
}