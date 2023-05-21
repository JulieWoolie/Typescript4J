package net.forthecrown.typescript.parse.ast;

import lombok.Getter;
import net.forthecrown.typescript.parse.Location;

@Getter
public class ExprStatement extends Statement {

  private final Expression expr;

  public ExprStatement(Location start, Expression expr) {
    super(start);
    this.expr = expr;
  }

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitExprStatement(this, context);
  }
}