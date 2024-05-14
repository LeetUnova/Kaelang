package src;

public interface Lang {
    public SyntaxNode createSyntaxTree(String text);
    public String fromSyntaxTree(SyntaxNode tree);
}
