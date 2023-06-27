package net.forthecrown.typescript.parse.ast;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DoWhileStatement extends Statement {

  private Block body;
  private Expression condition;

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitDoWhile(this, context);
  }
}