package net.forthecrown.typescript.parse.ast;

import lombok.Getter;
import net.forthecrown.typescript.parse.Location;

@Getter
public class NumberLiteral extends Expression {

  private final double value;

  public NumberLiteral(Location start, double value) {
    super(start);
    this.value = value;
  }

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitNumberLiteral(this, context);
  }
}