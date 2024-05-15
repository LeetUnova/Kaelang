package src;

public class JavaScriptLang implements Lang {
    @Override
    public SyntaxNode createSyntaxTree(String text) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createSyntaxTree'");
    }

    public String fromSyntaxTree(SyntaxNode tree) {
        String text = String.format("class %s {\n", tree.value);

        for (SyntaxNode node : tree.children)
            text += fromTreeMinor(node, "    ");

        return text + "\n}";
    }

    public String fromTreeMinor(SyntaxNode node, String indent) {
        return switch (node.value) {
            case "if" -> {
                String text = String.format("%sif (%s) {\n", indent, fromTreeMinor(node.children.get(0), ""));

                for (int i = 1; i < node.children.size(); i++)
                    text += fromTreeMinor(node.children.get(i), indent + "    ") + "\n";

                yield text + indent + "}";
            }
            case "print" -> String.format("%sconsole.log(%s);", indent, fromTreeMinor(node.children.get(0), ""));
            case "proc" -> {
                String text = String.format("%sfunction %s() {\n", indent, node.children.get(0).value);

                for (int i = 1; i < node.children.size(); i++)
                    text += fromTreeMinor(node.children.get(i), indent + "    ") + "\n";

                yield text + indent + "}";
            }
            case "==" -> String.format("%s == %s", fromTreeMinor(node.children.get(0), ""), fromTreeMinor(node.children.get(1), ""));
            case "var" -> String.format("%slet %s = %n%s;", indent, node.children.get(0).value, fromTreeMinor(node.children.get(1), indent));
            case "=" -> String.format("(%s = %s)", node.children.get(0).value, node.children.get(1).value);
            case "set" -> String.format("%s%s = %s;", indent, node.children.get(0).value, node.children.get(1).value);
            case "else" -> String.format("%selse {\n%s\n%s}", indent, fromTreeMinor(node.children.get(0), indent + "    "), indent);
            case "switch" -> {
                String text = String.format("%sswitch (%s) {\n", indent, fromTreeMinor(node.children.get(0), ""));

                for (int i = 1; i < node.children.size(); i++)
                    text += fromTreeMinor(node.children.get(i), indent + "    ") + "\n";

                yield text + indent + "}";
            }
            case "case" -> {
                String text = String.format("%scase %s:\n", indent, fromTreeMinor(node.children.get(0), ""));

                for (int i = 1; i < node.children.size(); i++)
                    text += fromTreeMinor(node.children.get(i), indent + "    ") + "\n";

                yield text + indent + "    break;";

            }
            case "yield" -> String.format("%syield %s;", indent, fromTreeMinor(node.children.get(0), ""));
            case "return" -> String.format("%sreturn %s;", indent, fromTreeMinor(node.children.get(0), ""));
            case "default" -> String.format("%sdefault:\n%s\n%s", indent, fromTreeMinor(node.children.get(0), indent + "    "), indent);
            default -> indent + node.value;
        };
    }
}
