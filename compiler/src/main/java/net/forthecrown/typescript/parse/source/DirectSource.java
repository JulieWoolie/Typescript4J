package net.forthecrown.typescript.parse.source;

record DirectSource(CharSequence src, String name) implements Source {

  @Override
  public StringBuffer read() {
    return new StringBuffer(src);
  }
}