package net.forthecrown.typescript.parse.ast;

import lombok.Getter;
import lombok.Setter;
import net.forthecrown.typescript.parse.Location;

@Getter @Setter
public class BinaryExpr extends OperatorExpr {

  private Expression left;
  private Expression right;

  private Operation operation;

  public BinaryExpr() {
  }

  public BinaryExpr(Location start, Operation operation, Expression left, Expression right) {
    super(start);
    this.operation = operation;
    this.left = left;
    this.right = right;
  }

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitBinaryExpr(this, context);
  }

  @Override
  public Expression getOperand(Side side) {
    return switch (side) {
      case LEFT  -> left;
      case RIGHT -> right;
    };
  }

  public enum Operation {
    INSTANCEOF,

    COALESCE,

    GT,
    GTE,
    LT,
    LTE,

    EQ,
    NEQ,

    S_EQ,
    S_NEQ,

    ADD,
    SUB,
    MUL,
    DIV,
    MOD,
    EXP,

    SH_LEFT,
    SH_RIGHT,

    USH_LEFT,
    USH_RIGHT,

    XOR,
    AND,
    OR,
  }
}