package src;

import java.util.ArrayList;

public class Kaelang implements Lang {
    @Override
    public SyntaxNode createSyntaxTree(String text) {
        ArrayList<String> list = new ArrayList<>();
        text = text.trim();

        for (int i = 0; i < text.length(); i++) {
            switch (text.charAt(i)) {
                case ' ' -> {
                    while (Character.isWhitespace(text.charAt(i)))
                        i++;
                    i--;
                }
                case '(' -> {
                    String subNode = "";
                    int level = 0;
                    i++;
                    while (!(level == 0 && text.charAt(i) == ')')) {
                        subNode += text.charAt(i);
                        level += switch (text.charAt(i)) {
                            case '(' -> 1;
                            case ')' -> -1;
                            default -> 0;
                        };
                        i++;
                    }
                    list.add(":#" + subNode);
                }
                default -> {
                    String term = "";
                    boolean inQuote = false;
                    int inCurly = 0;
                    while (i < text.length() && (!Character.isWhitespace(text.charAt(i)) || inQuote || inCurly != 0)) {
                        switch (text.charAt(i)) {
                            case '"' -> inQuote = !inQuote;
                            case '{' -> inCurly++;
                            case '}' -> inCurly--;
                        }
                        term += text.charAt(i++);
                    }
                    list.add(term);
                }
            }
        }

        if (list.size() <= 0)
            return new SyntaxNode("");

        SyntaxNode node = new SyntaxNode(list.get(0));

        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).startsWith(":#"))
                node.add(createSyntaxTree(list.get(i).substring(2)));
            else
                node.add(new SyntaxNode(list.get(i)));
        }

        return node;
    }

    @Override
    public String fromSyntaxTree(SyntaxNode tree) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromSyntaxTree'");
    }
}
