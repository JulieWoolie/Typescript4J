package net.forthecrown.typescript.parse.ast;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ArrowFunction extends Expression {

  private FunctionSignature node;

  private Statement body;

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitArrowFunction(this, context);
  }
}