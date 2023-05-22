package net.forthecrown.typescript.parse.ast;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
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

  @RequiredArgsConstructor
  public enum Operation {
    INSTANCEOF ("instanceof"),

    COALESCE ("??"),

    GT (">"),
    GTE (">="),
    LT ("<"),
    LTE ("<="),

    EQ ("=="),
    NEQ ("!="),

    S_EQ ("==="),
    S_NEQ ("!=="),

    ADD ("+"),
    SUB ("-"),
    MUL ("*"),
    DIV ("/"),
    MOD ("%"),
    EXP ("**"),

    SH_LEFT ("<<"),
    SH_RIGHT (">>"),

    USH_LEFT ("<<<"),
    USH_RIGHT (">>>"),

    XOR ("^"),
    AND ("&"),
    OR ("|"),

    ASSIGN ("="),
    ASSIGN_OR ("|="),
    ASSIGN_AND ("&="),
    ASSIGN_XOR ("^="),
    ASSIGN_SUB ("-="),
    ASSIGN_ADD ("+="),
    ASSIGN_DIV ("/="),
    ASSIGN_MUL ("*="),
    ASSIGN_MOD ("%="),
    ASSIGN_SHIFT_LEFT ("=<<"),
    ASSIGN_SHIFT_RIGHT ("=>>"),
    ASSIGN_USHIFT_LEFT ("=<<<"),
    ASSIGN_USHIFT_RIGHT ("=>>>"),
    ASSIGN_COALESCE ("??="),

    COMMA (","),
    ;

    private final String stringValue;

    @Override
    public String toString() {
      return stringValue;
    }
  }
}