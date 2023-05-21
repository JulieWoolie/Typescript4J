package net.forthecrown.typescript.parse.ast;

import lombok.Getter;
import lombok.Setter;
import net.forthecrown.typescript.parse.Location;

@Getter @Setter
public abstract class OperatorExpr extends Expression {

  public OperatorExpr(Location start) {
    super(start);
  }

  public OperatorExpr() {
  }

  public abstract Expression getOperand(Side side);

  public enum Side {
    LEFT, RIGHT
  }

}