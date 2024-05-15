package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        final Kaelang kaelang = new Kaelang();

        final Lang other = switch (args[0]) {
            case "jv" -> new JavaLang();
            case "js" -> new JavaScriptLang();
            case "mc" -> new DataPackLang();
            default -> throw new IllegalArgumentException("Unknown language");
        };

        final String kae;

        try {
            kae = readKae(args[1]);
        }
        catch (FileNotFoundException e) {
            System.out.printf("File %s not found%n", args[1]);
            return;
        }

        final SyntaxNode tree = kaelang.createSyntaxTree(kae);
        final String code = other.fromSyntaxTree(tree);
    
        saveFile(getName(args[1], switch (args[0]) {
            case "jv" -> ".java";
            case "js" -> ".js";
            case "mc" -> ".mcfunction";
            default -> throw new IllegalArgumentException("Unknown language");
        }), code);
    }

    private static String readKae(String filename) throws FileNotFoundException {
        String text = "";

        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine())
                text += scanner.nextLine() + " ";
        }

        return text;
    }

    private static String getName(String original, String ending) {
        if (!original.contains("."))
            return original + ending;

        return original.split("\\.")[0] + ending;
    }

    private static void saveFile(String filename, String text) {
        final File file = new File(filename);

        if (!file.exists())
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        try (PrintStream out = new PrintStream(file)) {
            out.print(text);
        }
        catch (FileNotFoundException e) {}
    }
}