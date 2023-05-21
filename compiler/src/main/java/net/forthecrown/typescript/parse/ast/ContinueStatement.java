package net.forthecrown.typescript.parse.ast;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ContinueStatement extends Statement {

  private Identifier label;

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitContinue(this, context);
  }
}