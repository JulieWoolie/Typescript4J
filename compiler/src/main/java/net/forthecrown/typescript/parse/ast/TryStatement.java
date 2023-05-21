package net.forthecrown.typescript.parse.ast;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import net.forthecrown.typescript.parse.type.Type;

@Getter @Setter
public class TryStatement extends Statement {

  private Block body;

  private final List<Catch> catches = new ArrayList<>();

  private Block finallyBody;

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitTry(this, context);
  }

  @Getter @Setter
  public static class Catch extends Statement {
    private Identifier label;
    private Type errorType;

    private Block body;

    @Override
    public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
      return visitor.visitCatch(this, context);
    }
  }
}