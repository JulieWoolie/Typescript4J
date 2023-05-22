package net.forthecrown.typescript.parse;

public record Location(int line, int column, int index) {

  public static final Location ZERO = new Location(1, 0, 0);

  public static Location of(int line, int col, int index) {
    if (line <= 1 && col == 0 && index == 0) {
      return ZERO;
    }

    return new Location(line, col, index);
  }

  @Override
  public String toString() {
    return line + ":" + column;
  }
}