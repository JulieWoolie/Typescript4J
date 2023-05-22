package net.forthecrown.typescript.parse.ast;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PropertyAccessExpr extends Expression {

  private Expression target;
  private Expression property;

  private boolean elementAccess; // Whether '[' and ']' were used

  private boolean optional;

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitPropertyAccess(this, context);
  }
}