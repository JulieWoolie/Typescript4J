package net.forthecrown.typescript.parse.ast;

import net.forthecrown.typescript.parse.Location;

public class NullLiteral extends Expression {

  public NullLiteral(Location start) {
    super(start);
  }

  public NullLiteral() {
  }

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitNullLiteral(this, context);
  }
}