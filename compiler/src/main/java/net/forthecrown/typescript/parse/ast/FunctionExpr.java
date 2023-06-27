package net.forthecrown.typescript.parse.ast;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FunctionExpr extends Expression {

  private FunctionSignature signature;

  private Statement body;

  private boolean arrowFunction = false;

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitFunction(this, context);
  }
}