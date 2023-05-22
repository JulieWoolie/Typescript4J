package net.forthecrown.typescript.parse.ast;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ConditionalExpr extends Expression {

  private Expression condition;
  private Expression onTrue;
  private Expression onFalse;

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitConditional(this, context);
  }
}