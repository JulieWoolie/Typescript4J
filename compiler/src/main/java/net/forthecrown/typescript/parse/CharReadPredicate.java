package net.forthecrown.typescript.parse;

public interface CharReadPredicate {

  Result matchesCharacter(int codePoint);

  enum Result {
    MATCHES,
    SKIP,
    INVALID
  }
}