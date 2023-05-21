package net.forthecrown.typescript.parse.ast;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.forthecrown.typescript.parse.Location;

@Getter
@Accessors(fluent = true)
public class BooleanLiteral extends Expression {

  private final boolean value;

  public BooleanLiteral(Location start, boolean value) {
    super(start);
    this.value = value;
  }

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitBooleanLiteral(this, context);
  }
}