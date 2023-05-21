package net.forthecrown.typescript.parse.ast;

import lombok.Getter;
import net.forthecrown.typescript.parse.Name;
import net.forthecrown.typescript.parse.Location;

public class Identifier extends Expression {

  @Getter
  private final Name name;

  public Identifier(Location start, Name name) {
    super(start);
    this.name = name;
  }

  public Identifier(Name name) {
    this.name = name;
  }

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitIdentifier(this, context);
  }
}