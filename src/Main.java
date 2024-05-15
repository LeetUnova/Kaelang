package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Kaelang kaelang = new Kaelang();
        Lang other = new JavaLang();
        String text = "";

        try (Scanner scanner = new Scanner(new File("Main.kae"))) {
            while (scanner.hasNextLine())
                text += scanner.nextLine() + " ";
        }

        SyntaxNode tree = kaelang.createSyntaxTree(text);
        System.out.println(other.fromSyntaxTree(tree));
    }
}