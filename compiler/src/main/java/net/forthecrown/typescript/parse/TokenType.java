package net.forthecrown.typescript.parse;

import static net.forthecrown.typescript.parse.TokenType.Kind.BINARY;
import static net.forthecrown.typescript.parse.TokenType.Kind.BRACKET;
import static net.forthecrown.typescript.parse.TokenType.Kind.KEYWORD;
import static net.forthecrown.typescript.parse.TokenType.Kind.LITERAL;
import static net.forthecrown.typescript.parse.TokenType.Kind.OTHER;
import static net.forthecrown.typescript.parse.TokenType.Kind.UNARY;
import static net.forthecrown.typescript.parse.TokenType.Kind.UNSUPPORTED;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum TokenType {

  EOF                   (OTHER),
  EOL                   (OTHER),
  UNKNOWN               (OTHER),

  HASHTAG               (OTHER, '#'),

  // Operators
  NEGATE                (UNARY, '!'),
  INC                   (UNARY, "++"),
  DEC                   (UNARY, "--"),
  BIT_NOT               (UNARY, "~"),

  DOT                   (BINARY, '.'),
  DOT_DOT               (BINARY, ".."),
  ELLIPSES              (BINARY, "..."),
  COLON                 (BINARY, ':'),
  COLON_COLON           (BINARY, "::"),
  SEMICOLON             (BINARY, ';'),
  COMMA                 (BINARY, ','),
  ASSIGN                (BINARY, '='),
  EQUALS                (BINARY, "=="),
  N_EQUALS              (BINARY, "!="),
  STRICT_EQUALS         (BINARY, "==="),
  STRICT_N_EQUALS       (BINARY, "!=="),
  MOD                   (BINARY, "%"),
  ASSIGN_MOD            (BINARY, "%="),
  BIT_AND               (BINARY, "&"),
  AND                   (BINARY, "&&"),
  ASSIGN_AND            (BINARY, "&="),
  BIT_OR                (BINARY, "|"),
  OR                    (BINARY, "||"),
  ASSIGN_OR             (BINARY, "|="),
  XOR                   (BINARY, "^"),
  ASSIGN_XOR            (BINARY, "^="),
  ADD                   (BINARY, "+"),
  ASSIGN_ADD            (BINARY, "+="),
  SUB                   (BINARY, "-"),
  ASSIGN_SUB            (BINARY, "-="),
  MUL                   (BINARY, "*"),
  EXPONENTIAL           (BINARY, "**"),
  ASSIGN_MUL            (BINARY, "*="),
  DIV                   (BINARY, "/"),
  ASSIGN_DIV            (BINARY, "/="),
  LT                    (BINARY, "<"),
  LT_EQ                 (BINARY, "<="),
  GT                    (BINARY, ">"),
  GT_EQ                 (BINARY, ">="),
  SHIFT_LEFT            (BINARY, "<<"),
  ASSIGN_SHIFT_LEFT     (BINARY, "<<="),
  USHIFT_LEFT           (BINARY, "<<<"),
  ASSIGN_USHIFT_LEFT    (BINARY, "<<<="),
  SHIFT_RIGHT           (BINARY, ">>"),
  USHIFT_RIGHT          (BINARY, ">>>"),
  ASSIGN_SHIFT_RIGHT    (BINARY, ">>="),
  ASSIGN_USHIFT_RIGHT   (BINARY, ">>>="),
  TERNARY               (BINARY, "?"),
  OPTIONAL              (BINARY, "?."),
  COALESCE              (BINARY, "??"),
  ASSIGN_COALESCE       (BINARY, "??="),

  ARROW(OTHER, "=>"),

  // Keywords
  YIELD                 (UNSUPPORTED, "yield"),

  ABSTRACT              (KEYWORD, "abstract"),
  BREAK                 (KEYWORD, "break"),
  CASE                  (KEYWORD, "case"),
  CATCH                 (KEYWORD, "catch"),
  CLASS                 (KEYWORD, "class"),
  CONST                 (KEYWORD, "const"),
  CONTINUE              (KEYWORD, "continue"),
  DEBUGGER              (KEYWORD, "debugger"),
  DEFAULT               (KEYWORD, "default"),
  DECLARE               (KEYWORD, "declare"),
  DELETE                (KEYWORD, "delete"),
  DO                    (KEYWORD, "do"),
  ELSE                  (KEYWORD, "else"),
  ENUM                  (KEYWORD, "enum"),
  EXTENDS               (KEYWORD, "extends"),
  EXPORT                (KEYWORD, "export"),
  FINALLY               (KEYWORD, "finally"),
  FOR                   (KEYWORD, "for"),
  FUNCTION              (KEYWORD, "function"),
  IF                    (KEYWORD, "if"),
  IMPORT                (KEYWORD, "import"),
  IN                    (KEYWORD, "in"),
  INSTANCE_OF           (KEYWORD, "instanceof"),
  INTERFACE             (KEYWORD, "interface"),
  LET                   (KEYWORD, "let"),
  NEW                   (KEYWORD, "new"),
  NULL                  (KEYWORD, "null"),
  UNDEFINED             (KEYWORD, "undefined"),
  RETURN                (KEYWORD, "return"),
  SUPER                 (KEYWORD, "super"),
  SWITCH                (KEYWORD, "switch"),
  THIS                  (KEYWORD, "this"),
  THROW                 (KEYWORD, "throw"),
  TRY                   (KEYWORD, "try"),
  TYPE_OF               (KEYWORD, "typeof"),
  VAR                   (KEYWORD, "var"),
  VOID                  (KEYWORD, "void"),
  WHILE                 (KEYWORD, "while"),
  WITH                  (KEYWORD, "with"),
  TRUE                  (KEYWORD, "true"),
  FALSE                 (KEYWORD, "false"),

  // Brackets
  CURLY_START           (BRACKET, '{'),
  CURLY_CLOSE           (BRACKET, '}'),
  SQUARE_START          (BRACKET, '['),
  SQUARE_CLOSE          (BRACKET, ']'),
  PAREN_START           (BRACKET, '('),
  PAREN_CLOSE           (BRACKET, ')'),

  ID                    (OTHER),

  TEMPLATE_QUOTE        (OTHER),
  TEMPLATE_EXPR         (OTHER),
  
  STRING_LITERAL        (LITERAL),
  NUMBER_LITERAL        (LITERAL),
  HEX_LITERAL           (LITERAL),
  OCTAL_LITERAL         (LITERAL),
  BINARY_LITERAL        (LITERAL),

  ;

  public static final Map<String, TokenType> keywordLookup;

  static {
    Map<String, TokenType> keywords = new HashMap<>();

    for (TokenType type : values()) {
      if (type.kind != KEYWORD) {
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
      return "'" + value + "'";
    }

    return name().toLowerCase();
  }

  public enum Kind {
    KEYWORD,
    BINARY,
    UNARY,
    BRACKET,
    UNSUPPORTED,
    LITERAL,
    OTHER
  }
}