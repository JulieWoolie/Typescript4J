package net.forthecrown.typescript.parse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum TokenType {

  EOF                   (Kind.OTHER),
  EOL                   (Kind.OTHER),
  UNKNOWN               (Kind.OTHER),

  HASHTAG               (Kind.OTHER, '#'),

  // Operators
  NEGATE                (Kind.UNARY, '!'),
  INC                   (Kind.UNARY, "++"),
  DEC                   (Kind.UNARY, "--"),
  BIT_NOT               (Kind.UNARY, "~"),

  DOT                   (Kind.BINARY, '.'),
  DOT_DOT               (Kind.BINARY, ".."),
  ELLIPSES              (Kind.BINARY, "..."),
  COLON                 (Kind.BINARY, ':'),
  COLON_COLON           (Kind.BINARY, "::"),
  SEMICOLON             (Kind.BINARY, ';'),
  COMMA                 (Kind.BINARY, ','),
  ASSIGN                (Kind.BINARY, '='),
  EQUALS                (Kind.BINARY, "=="),
  N_EQUALS              (Kind.BINARY, "!="),
  STRICT_EQUALS         (Kind.BINARY, "==="),
  STRICT_N_EQUALS       (Kind.BINARY, "!=="),
  MOD                   (Kind.BINARY, "%"),
  ASSIGN_MOD            (Kind.BINARY, "%="),
  BIT_AND               (Kind.BINARY, "&"),
  AND                   (Kind.BINARY, "&&"),
  ASSIGN_AND            (Kind.BINARY, "&="),
  BIT_OR                (Kind.BINARY, "|"),
  OR                    (Kind.BINARY, "||"),
  ASSIGN_OR             (Kind.BINARY, "|="),
  XOR                   (Kind.BINARY, "^"),
  ASSIGN_XOR            (Kind.BINARY, "^="),
  ADD                   (Kind.BINARY, "+"),
  ASSIGN_ADD            (Kind.BINARY, "+="),
  SUB                   (Kind.BINARY, "-"),
  ASSIGN_SUB            (Kind.BINARY, "-="),
  MUL                   (Kind.BINARY, "*"),
  EXPONENTIAL           (Kind.BINARY, "**"),
  ASSIGN_MUL            (Kind.BINARY, "*="),
  DIV                   (Kind.BINARY, "/"),
  ASSIGN_DIV            (Kind.BINARY, "/="),
  LT                    (Kind.BINARY, "<"),
  LT_EQ                 (Kind.BINARY, "<="),
  GT                    (Kind.BINARY, ">"),
  GT_EQ                 (Kind.BINARY, ">="),
  SHIFT_LEFT            (Kind.BINARY, "<<"),
  ASSIGN_SHIFT_LEFT     (Kind.BINARY, "<<="),
  USHIFT_LEFT           (Kind.BINARY, "<<<"),
  ASSIGN_USHIFT_LEFT    (Kind.BINARY, "<<<="),
  SHIFT_RIGHT           (Kind.BINARY, ">>"),
  USHIFT_RIGHT          (Kind.BINARY, ">>>"),
  ASSIGN_SHIFT_RIGHT    (Kind.BINARY, ">>="),
  ASSIGN_USHIFT_RIGHT   (Kind.BINARY, ">>>="),
  TERNARY               (Kind.BINARY, "?"),
  OPTIONAL              (Kind.BINARY, "?."),
  COALESCE              (Kind.BINARY, "??"),
  COALESCE_ASSIGN       (Kind.BINARY, "??="),

  ARROW_FUNC            (Kind.OTHER, "=>"),

  // Keywords
  EXPORT                (Kind.UNSUPPORTED, "export"),
  YIELD                 (Kind.UNSUPPORTED, "yield"),

  BREAK                 (Kind.KEYWORD, "break"),
  CASE                  (Kind.KEYWORD, "case"),
  CATCH                 (Kind.KEYWORD, "catch"),
  CLASS                 (Kind.KEYWORD, "class"),
  CONST                 (Kind.KEYWORD, "const"),
  CONTINUE              (Kind.KEYWORD, "continue"),
  DEBUGGER              (Kind.KEYWORD, "debugger"),
  DEFAULT               (Kind.KEYWORD, "default"),
  DELETE                (Kind.KEYWORD, "delete"),
  DO                    (Kind.KEYWORD, "do"),
  ELSE                  (Kind.KEYWORD, "else"),
  ENUM                  (Kind.KEYWORD, "enum"),
  EXTENDS               (Kind.KEYWORD, "extends"),
  FINALLY               (Kind.KEYWORD, "finally"),
  FOR                   (Kind.KEYWORD, "for"),
  FUNCTION              (Kind.KEYWORD, "function"),
  IF                    (Kind.KEYWORD, "if"),
  IMPORT                (Kind.KEYWORD, "import"),
  IN                    (Kind.KEYWORD, "in"),
  INSTANCE_OF           (Kind.KEYWORD, "instanceof"),
  INTERFACE             (Kind.KEYWORD, "interface"),
  LET                   (Kind.KEYWORD, "let"),
  NEW                   (Kind.KEYWORD, "new"),
  NULL                  (Kind.KEYWORD, "null"),
  RETURN                (Kind.KEYWORD, "return"),
  SUPER                 (Kind.KEYWORD, "super"),
  SWITCH                (Kind.KEYWORD, "switch"),
  THIS                  (Kind.KEYWORD, "this"),
  THROW                 (Kind.KEYWORD, "throw"),
  TRY                   (Kind.KEYWORD, "try"),
  TYPE_OF               (Kind.KEYWORD, "typeof"),
  VAR                   (Kind.KEYWORD, "var"),
  VOID                  (Kind.KEYWORD, "void"),
  WHILE                 (Kind.KEYWORD, "while"),
  WITH                  (Kind.KEYWORD, "with"),
  TRUE                  (Kind.KEYWORD, "true"),
  FALSE                 (Kind.KEYWORD, "false"),

  // Brackets
  CURLY_START           (Kind.BRACKET, '{'),
  CURLY_CLOSE           (Kind.BRACKET, '}'),
  SQUARE_START          (Kind.BRACKET, '['),
  SQUARE_CLOSE          (Kind.BRACKET, ']'),
  PAREN_START           (Kind.BRACKET, '('),
  PAREN_CLOSE           (Kind.BRACKET, ')'),

  ID                    (Kind.OTHER),
  STRING_LITERAL        (Kind.OTHER),
  TEMPLATE_STRING       (Kind.OTHER),
  NUMBER_LITERAL        (Kind.OTHER),
  HEX_LITERAL           (Kind.OTHER),
  OCTAL_LITERAL         (Kind.OTHER),
  BINARY_LITERAL        (Kind.OTHER),

  ;

  public static final Map<String, TokenType> keywordLookup;

  static {
    Map<String, TokenType> keywords = new HashMap<>();

    for (TokenType type : values()) {
      if (type.kind != Kind.KEYWORD) {
        continue;
      }

      keywords.put(type.value, type);
    }

    keywordLookup = Collections.unmodifiableMap(keywords);
  }

  private final String value;
  private final Kind kind;

  TokenType(Kind kind, int ch) {
    this(kind, Character.toString(ch));
  }

  TokenType(Kind kind, String value) {
    this.value = value;
    this.kind = kind;
  }

  TokenType(Kind kind) {
    this(kind, "");
  }

  public Kind kind() {
    return kind;
  }

  @Override
  public String toString() {
    if (this == EOF) {
      return "<eof>";
    }

    if (this == EOL) {
      return "<eol>";
    }

    if (this == UNKNOWN) {
      return "<error>";
    }

    if (value != null && !value.isEmpty()) {
      return value;
    }

    return "'" + value + "'";
  }

  public enum Kind {
    KEYWORD,
    BINARY,
    UNARY,
    BRACKET,
    UNSUPPORTED,
    OTHER
  }
}