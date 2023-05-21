package net.forthecrown.typescript.parse.ast;

import net.forthecrown.typescript.parse.Location;

public abstract class Expression extends Node {

  public Expression(Location start) {
    super(start);
  }

  public Expression() {
  }
}