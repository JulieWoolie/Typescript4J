package net.forthecrown.typescript.parse.ast;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ForInOfStatement extends Statement {

  private Expression declaration;
  private Expression object;

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitForIn(this, context);
  }
}