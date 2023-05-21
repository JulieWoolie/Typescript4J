package net.forthecrown.typescript.parse.ast;

import net.forthecrown.typescript.parse.Location;

public class UpdateExpr extends Expression {

  private Expression expr;
  private UpdateOp operation;

  public UpdateExpr(Location start, Expression expr, UpdateOp operation) {
    super(start);
    this.expr = expr;
    this.operation = operation;
  }

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitUpdate(this, context);
  }

  public enum UpdateOp {
    POST_INC,
    PRE_INC,

    POST_DEC,
    PRE_DEC,
  }
}