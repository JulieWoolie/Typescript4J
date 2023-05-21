package net.forthecrown.typescript.parse.ast;

public class EmptyExpr extends Expression {

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitEmptyExpr(this, context);
  }
}