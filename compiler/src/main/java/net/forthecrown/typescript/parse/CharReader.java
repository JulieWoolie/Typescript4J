package net.forthecrown.typescript.parse;

import java.util.function.IntPredicate;
import net.forthecrown.typescript.parse.CharReadPredicate.Result;

public interface CharReader {

  IntPredicate IS_ID = ch -> {
    return (ch >= 'a' && ch <= 'z')
        || (ch >= 'A' && ch <= 'Z')
        || (ch >= '0' && ch <= '9')
        || ch == '_'
        || ch == '+'
        || ch == '.'
        || ch == '-'
        || ch == '$';
  };

  CharReadPredicate IS_HEX_CHAR = ch -> {
    if ((ch >= '0' && ch <= '9')
        || (ch >= 'a' && ch <= 'f')
        || (ch >= 'A' && ch <= 'F')
    ) {
      return Result.MATCHES;
    }

    return ch == '_' ? Result.SKIP : Result.INVALID;
  };

  CharReadPredicate IS_OCTAL_CHAR = ch -> {
    if (ch >= '0' && ch <= '7') {
      return Result.MATCHES;
    }

    return ch == '_' ? Result.SKIP : Result.INVALID;
  };

  CharReadPredicate IS_BINARY_CHAR = ch -> {
    if (ch == '1' || ch == '0') {
      return Result.MATCHES;
    }

    return ch == '_' ? Result.SKIP : Result.INVALID;
  };

  ParseErrorFactory factory();

  int read() throws ParseException;

  int peek() throws ParseException;

  boolean startsWith(String s);

  int expect(int... chars) throws ParseException;

  default String readString(CharReadPredicate codepointPredicate) throws ParseException {
    return readString(-1, codepointPredicate);
  }

  default String readString(IntPredicate predicate) throws ParseException {
    return readString(-1, predicate);
  }

  default String readOctal() throws ParseException {
    return readString(IS_OCTAL_CHAR);
  }

  default String readHex() throws ParseException {
    return readString(IS_HEX_CHAR);
  }

  default String readBinary() throws ParseException {
    return readString(IS_BINARY_CHAR);
  }

  default String readNumber() throws ParseException {
    return readString(new NumberCharPredicate());
  }

  default String readJavaIdentifier() throws ParseException {
    return readString(new JavaIdPredicate());
  }

  String readString(int maxLength, CharReadPredicate codepointPredicate) throws ParseException;

  default String readString(int maxLength, IntPredicate codepointPredicate)
      throws ParseException
  {
    return readString(maxLength, (CharReadPredicate) codePoint -> {
      return codepointPredicate.test(codePoint) ? Result.MATCHES : Result.INVALID;
    });
  }

  default String readIdentifier() throws ParseException {
    return readString(IS_ID);
  }

  default String readQuoted() throws ParseException {
    return readQuoted(false);
  }

  String readQuoted(boolean allowNewLine) throws ParseException;

  String readTripleQuoted() throws ParseException;

  default void skip() {
    skip(1);
  }

  void skip(int chars);

  void skipEmpty() throws ParseException;

  Location location();

  void location(Location location);

  StringBuffer input();

  void skipNewline();
}