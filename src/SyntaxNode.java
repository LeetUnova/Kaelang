package src;

import java.util.ArrayList;

public class SyntaxNode {
    public String value;
    public ArrayList<SyntaxNode> children = new ArrayList<>();

    public SyntaxNode(String value) {
        this.value = value;
    }

    public void add(SyntaxNode node) {
        children.add(node);
    }

    @Override
    public String toString() {
        return String.format("(SyntaxNode %s %s)", value, children);
    }
}
