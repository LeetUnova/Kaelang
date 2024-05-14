package src;

public class DataPackLang implements Lang {
    private String namespace = "";

    @Override
    public SyntaxNode createSyntaxTree(String text) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createSyntaxTree'");
    }

    @Override
    public String fromSyntaxTree(SyntaxNode tree) {
        String text = "";
        namespace = tree.value;

        for (SyntaxNode node : tree.children)
            text += fromTreeMinor(node);

        return text;
    }

    public String fromTreeMinor(SyntaxNode node) {
        return switch (node.value) {
            case String s when s.startsWith("{") -> s;
            case "attr" -> STR."attribute \{node.children.get(0).value} \{node.children.get(1).value} base set \{node.children.get(2).value}";
            case "print" -> STR."say \{fromTreeMinor(node.children.get(0))}";
            case "var" -> STR."data merge storage \{namespace}:\{node.children.get(0).value} \{fromTreeMinor(node.children.get(1))}";
            case "from" -> STR."from \{node.children.get(0).value} \{node.children.get(1).value}";
            case "summon" -> switch (node.children.size()) {
                case 4 -> STR."summon \{node.children.get(0).value} \{node.children.get(1).value} \{node.children.get(2).value} \{node.children.get(3).value}";
                default -> STR."summon \{node.children.get(0).value}";
            };
            default -> node.value;
        } + "\n";
    }
}
