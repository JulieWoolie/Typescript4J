package net.forthecrown.typescript.parse.ast;

public class ReturnStatement extends Statement {

  private Expression returnValue;

  public Expression getReturnValue() {
    return returnValue;
  }

  public void setReturnValue(Expression returnValue) {
    this.returnValue = returnValue;
  }

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitReturn(this, context);
  }
}