package net.forthecrown.typescript.parse.ast;

import net.forthecrown.typescript.parse.Location;

public abstract class Statement extends Node {

  public Statement(Location start) {
    super(start);
  }

  public Statement() {
  }
}