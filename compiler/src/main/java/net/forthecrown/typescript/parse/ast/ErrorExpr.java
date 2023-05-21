package net.forthecrown.typescript.parse.ast;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorExpr extends Expression {

  private String message;

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitErrorExpr(this, context);
  }
}