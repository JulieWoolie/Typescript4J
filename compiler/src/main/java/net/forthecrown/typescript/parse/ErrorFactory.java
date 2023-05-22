package net.forthecrown.typescript.parse;

import java.util.Objects;

final class ErrorFactory {

  private final String messagePrefix;
  private final StringBuffer input;

  public ErrorFactory(String name, StringBuffer input) {
    this.input = input;

    this.messagePrefix = name == null || name.isBlank()
        ? ""
        : "Error reading '%s':\n".formatted(name);
  }

  public ParseException wrap(Location location, Throwable exc) {
    var res = create(location, exc.getMessage());
    res.setStackTrace(exc.getStackTrace());
    return res;
  }

  public ParseException create(Location location, String format, Object... args) {
    Objects.requireNonNull(location);

    String message = messagePrefix + (format == null ? "" : format.formatted(args));
    String formatted = ErrorMessages.format(input, location, message);

    return new ParseException(formatted);
  }
}