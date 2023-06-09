package net.forthecrown.typescript.parse.ast;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FunctionDeclaration extends Statement {

  private Identifier name;

  private FunctionExpr expr;

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitFunction(this, context);
  }
}