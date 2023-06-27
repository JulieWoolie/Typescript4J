package net.forthecrown.typescript.parse.ast;

import lombok.Getter;
import net.forthecrown.typescript.parse.Location;

@Getter
public class NullLiteral extends Expression {

  private boolean undefined;

  public NullLiteral(Location start, boolean undefined) {
    super(start);
    this.undefined = undefined;
  }

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitNullLiteral(this, context);
  }
}