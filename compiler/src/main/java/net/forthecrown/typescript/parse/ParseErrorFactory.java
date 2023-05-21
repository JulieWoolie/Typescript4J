package net.forthecrown.typescript.parse;

public interface ParseErrorFactory {

  ParseException wrap(Location location, Throwable exc);

  ParseException create(Location location, String message, Object... args);
}