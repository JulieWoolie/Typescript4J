package net.forthecrown.typescript.parse.ast;

public class ThrowStatement extends Statement {

  private Expression throwValue;

  public Expression getThrowValue() {
    return throwValue;
  }

  public void setThrowValue(Expression throwValue) {
    this.throwValue = throwValue;
  }

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitThrow(this, context);
  }
}