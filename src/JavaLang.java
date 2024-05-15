package src;

public class JavaLang implements Lang {
    @Override
    public SyntaxNode createSyntaxTree(String text) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createSyntaxTree'");
    }

    @Override
    public String fromSyntaxTree(SyntaxNode tree) {
        String text = String.format("public class %s {\n", tree.value);

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
            case "print" -> String.format("%sSystem.out.print(%s);", indent, fromTreeMinor(node.children.get(0), ""));
            case "proc" -> {
                String text = String.format("%s%s %s(%s) {\n", indent, node.children.get(0).value, node.children.get(1).value, fromTreeMinor(node.children.get(2), ""));

                for (int i = 3; i < node.children.size(); i++)
                    text += fromTreeMinor(node.children.get(i), indent + "    ") + "\n";

                yield text + indent + "}\n";
            }
            case "pub" -> {
                String text = String.format("%spublic %s %s(%s) {\n", indent, node.children.get(0).value, node.children.get(1).value, node.children.get(2).value);

                for (int i = 3; i < node.children.size(); i++)
                    text += fromTreeMinor(node.children.get(i), indent + "    ") + "\n";

                yield text + indent + "}\n";
            }
            case "priv" -> {
                String text = String.format("%sprivate %s %s(%s) {\n", indent, node.children.get(0).value, node.children.get(1).value, node.children.get(2).value);

                for (int i = 3; i < node.children.size(); i++)
                    text += fromTreeMinor(node.children.get(i), indent + "    ") + "\n";

                yield text + indent + "}\n";
            }
            case "prot" -> {
                String text = String.format("%sprotected %s %s(%s) {\n", indent, node.children.get(0).value, node.children.get(1).value, node.children.get(2).value);

                for (int i = 3; i < node.children.size(); i++)
                    text += fromTreeMinor(node.children.get(i), indent + "    ") + "\n";

                yield text + indent + "}\n";
            }
            case "==" -> String.format("%s == %s", fromTreeMinor(node.children.get(0), ""), fromTreeMinor(node.children.get(1), ""));
            case "var" -> String.format("%svar %s = %s;", indent, node.children.get(0).value, fromTreeMinor(node.children.get(1), indent).trim());
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
            case "default" -> String.format("%sdefault:\n%s\n%s    break;", indent, fromTreeMinor(node.children.get(0), indent + "    "), indent);
            case "+" -> String.format("(%s + %s)", node.children.get(0).value, node.children.get(1).value);
            case "-" -> String.format("(%s - %s)", node.children.get(0).value, node.children.get(1).value);
            case "*" -> String.format("(%s * %s)", node.children.get(0).value, node.children.get(1).value);
            case "/" -> String.format("(%s / %s)", node.children.get(0).value, node.children.get(1).value);
            case "new" -> String.format("new %s(%s)", node.children.get(0).value, fromTreeMinor(node.children.get(1), ""));
            case "args" -> {
                if (node.children.size() == 1)
                    yield fromTreeMinor(node.children.get(0), "");

                String text = fromTreeMinor(node.children.get(0), "");

                for (int i = 1; i < node.children.size(); i++)
                    text += ", " + fromTreeMinor(node.children.get(i), "");

                yield text;
            }
            default -> {
                if (!node.value.contains("\""))
                    yield node.value.replace(";", ", ").replace("-", " ");
                yield indent + node.value;
            }
        };
    }
}
