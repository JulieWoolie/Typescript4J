package net.forthecrown.typescript.parse.ast;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WhileStatement extends Statement {

  private Expression condition;
  private Block body;

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitWhile(this, context);
  }
}