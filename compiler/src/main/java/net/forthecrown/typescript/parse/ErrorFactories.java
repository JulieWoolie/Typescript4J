package net.forthecrown.typescript.parse;

public final class ErrorFactories {
  private ErrorFactories() {}

  public static ErrorFactory named(StringBuffer input, String fileName) {
    return new ErrorFactory(fileName, input);
  }

  public static ErrorFactory unnamed(StringBuffer input) {
    return new ErrorFactory(null, input);
  }
}