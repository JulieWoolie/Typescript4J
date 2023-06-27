package net.forthecrown.typescript.parse.ast;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import net.forthecrown.typescript.parse.type.Type;

@Getter @Setter
public class LexDeclarationStatement extends Statement {

  Kind kind;

  private final List<Expression> declarations = new ArrayList<>();

  @Override
  public String toString() {
    return "VariableDeclarationNode{" +
        "kind=" + kind +
        ", declarations=" + declarations +
        '}';
  }

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitLexicalDeclaration(this, context);
  }

  @Getter @Setter
  public static class SingleDeclaration extends Expression {

    private Identifier identifier;
    private Expression value;

    private Type type;

    @Override
    public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
      return visitor.visitSingleLexDeclaration(this, context);
    }

    @Override
    public String toString() {
      return "SingleDeclaration{" +
          "identifier=" + identifier +
          ", value=" + value +
          '}';
    }
  }

  public enum Kind {
    VAR, LET, CONST
  }
}