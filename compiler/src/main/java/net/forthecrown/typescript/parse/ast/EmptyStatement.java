package net.forthecrown.typescript.parse.ast;

public class EmptyStatement extends Statement {

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitEmptyStatement(this, context);
  }
}