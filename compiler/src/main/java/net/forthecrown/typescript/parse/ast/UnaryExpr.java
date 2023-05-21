package net.forthecrown.typescript.parse.ast;

import lombok.Getter;
import lombok.Setter;
import net.forthecrown.typescript.parse.Location;

@Getter @Setter
public class UnaryExpr extends OperatorExpr {

  private Expression expr;
  private UnaryOp op;

  public UnaryExpr(Location start, Expression expr, UnaryOp op) {
    super(start);
    this.expr = expr;
    this.op = op;
  }

  public UnaryExpr() {
  }

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitUnary(this, context);
  }

  @Override
  public Expression getOperand(Side side) {
    return expr;
  }

  public enum UnaryOp {
    POSITIVE,
    NEGATIVE,

    BIT_NOT,
    LOG_NOT,
    VOID,
    TYPEOF,

    DELETE
  }
}