package net.forthecrown.typescript.parse;

public record LocationImpl(int line, int column, int index) implements Location {

  @Override
  public String toString() {
    return line + ":" + column;
  }
}