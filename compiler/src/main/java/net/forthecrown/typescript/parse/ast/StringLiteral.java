package net.forthecrown.typescript.parse.ast;

import net.forthecrown.typescript.parse.Location;

public class StringLiteral extends Expression {

  private final String value;

  public StringLiteral(Location start, String value) {
    super(start);
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitStringLiteral(this, context);
  }

  @Override
  public String toString() {
    return "StringLiteral{" +
        "value='" + value + '\'' +
        '}';
  }
}