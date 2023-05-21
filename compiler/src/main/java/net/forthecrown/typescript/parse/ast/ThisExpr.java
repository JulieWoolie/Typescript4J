package net.forthecrown.typescript.parse.ast;

import net.forthecrown.typescript.parse.Location;

public class ThisExpr extends Expression {

  public ThisExpr(Location start) {
    super(start);
  }

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitThisExpr(this, context);
  }
}