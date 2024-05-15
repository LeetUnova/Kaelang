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
            case "attr" -> String.format("attribute %s %s base set %s",
                    node.children.get(0).value,
                    node.children.get(1).value,
                    node.children.get(2).value);
            case "print" -> String.format("say %s", fromTreeMinor(node.children.get(0)));
            case "var" -> String.format("data merge storage %s:%s %s",
                    namespace,
                    node.children.get(0).value,
                    fromTreeMinor(node.children.get(1)));
            case "from" -> String.format("from %s %s",
                    node.children.get(0).value,
                    node.children.get(1).value);
            case "summon" -> switch (node.children.size()) {
                case 4 -> String.format("summon %s %s %s %s",
                        node.children.get(0).value,
                        node.children.get(1).value,
                        node.children.get(2).value,
                        node.children.get(3).value);
                default -> String.format("summon %s", node.children.get(0).value);
            };
            default -> node.value;
        } + "\n";
    }
}
