package src;

public class JavaLang implements Lang {
    @Override
    public SyntaxNode createSyntaxTree(String text) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createSyntaxTree'");
    }

    @Override
    public String fromSyntaxTree(SyntaxNode tree) {
        String text = STR."public class \{tree.value} {\n";

        for (SyntaxNode node : tree.children)
            text += fromTreeMinor(node, "    ");

        return text + "\n}";
    }

    public String fromTreeMinor(SyntaxNode node, String indent) {
        return switch (node.value) {
            case String v when v.startsWith("\"") && v.endsWith("\"") -> v;
            case "if" -> {
                String text = STR."\{indent}if (\{fromTreeMinor(node.children.get(0), "")}) {\n";

                for (int i = 1; i < node.children.size(); i++)
                    text += fromTreeMinor(node.children.get(i), indent + "    ") + "\n";

                yield text + indent + "}";
            }
            case "print" -> STR."\{indent}System.out.print(\{fromTreeMinor(node.children.get(0), "")});";
            case "proc" -> {
                String text = STR."\{indent}public void \{node.children.get(0).value}() {\n";

                for (int i = 1; i < node.children.size(); i++)
                    text += fromTreeMinor(node.children.get(i), indent + "    ") + "\n";

                yield text + indent + "}";
            }
            case "==" -> STR."\{fromTreeMinor(node.children.get(0), "")} == \{fromTreeMinor(node.children.get(1), "")}";
            case "var" -> STR."\{indent}var \{node.children.get(0).value} = \{node.children.get(1).value};";
            case "=" -> STR."(\{node.children.get(0).value} = \{node.children.get(1).value})";
            case "set" -> STR."\{indent}\{node.children.get(0).value} = \{node.children.get(1).value};";
            case "else" -> STR."\{indent}else {\n\{fromTreeMinor(node.children.get(0), indent + "    ")}\n\{indent}}";
            default -> node.value;
        };
    }
}
