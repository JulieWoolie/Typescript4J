package net.forthecrown.typescript.parse;

import java.util.StringJoiner;

public record Token(TokenType type, String value, Location location, Location end) {

  public boolean is(TokenType type) {
    return this.type == type;
  }

  public boolean is(TokenType... types) {
    for (var t: types) {
      if (t == type) {
        return true;
      }
    }

    return false;
  }

  public void expect(ParseErrorFactory factory, TokenType type) {
    if (is(type)) {
      return;
    }

    throw factory.create(location, "Expected %s, found %s",
        type.toString(), this.type.name().toLowerCase()
    );
  }

  public void expect(ParseErrorFactory factory, TokenType... types) {
    if (is(types)) {
      return;
    }

    throw factory.create(location, "Expected %s, found %s",
        typesToString(types), type.name().toLowerCase()
    );
  }

  public static String typesToString(TokenType... types) {
    if (types.length == 1) {
      return types[0].name().toLowerCase();
    }

    StringJoiner joiner = new StringJoiner(", ");
    for (TokenType type : types) {
      joiner.add(type.name().toLowerCase());
    }

    return joiner.toString();
  }

  @Override
  public String toString() {
    String typeName = type.toString();

    if (value != null && !value.isEmpty()) {
      return typeName + "(" + value + ")";
    }

    return typeName;
  }
}