package net.forthecrown.typescript.parse;

import static net.forthecrown.typescript.parse.TokenType.ADD;
import static net.forthecrown.typescript.parse.TokenType.AND;
import static net.forthecrown.typescript.parse.TokenType.ARROW_FUNC;
import static net.forthecrown.typescript.parse.TokenType.ASSIGN;
import static net.forthecrown.typescript.parse.TokenType.ASSIGN_ADD;
import static net.forthecrown.typescript.parse.TokenType.ASSIGN_AND;
import static net.forthecrown.typescript.parse.TokenType.ASSIGN_DIV;
import static net.forthecrown.typescript.parse.TokenType.ASSIGN_MOD;
import static net.forthecrown.typescript.parse.TokenType.ASSIGN_MUL;
import static net.forthecrown.typescript.parse.TokenType.ASSIGN_OR;
import static net.forthecrown.typescript.parse.TokenType.ASSIGN_SHIFT_LEFT;
import static net.forthecrown.typescript.parse.TokenType.ASSIGN_SHIFT_RIGHT;
import static net.forthecrown.typescript.parse.TokenType.ASSIGN_SUB;
import static net.forthecrown.typescript.parse.TokenType.ASSIGN_USHIFT_LEFT;
import static net.forthecrown.typescript.parse.TokenType.ASSIGN_USHIFT_RIGHT;
import static net.forthecrown.typescript.parse.TokenType.ASSIGN_XOR;
import static net.forthecrown.typescript.parse.TokenType.BINARY_LITERAL;
import static net.forthecrown.typescript.parse.TokenType.BIT_AND;
import static net.forthecrown.typescript.parse.TokenType.BIT_NOT;
import static net.forthecrown.typescript.parse.TokenType.BIT_OR;
import static net.forthecrown.typescript.parse.TokenType.COALESCE;
import static net.forthecrown.typescript.parse.TokenType.COALESCE_ASSIGN;
import static net.forthecrown.typescript.parse.TokenType.COLON;
import static net.forthecrown.typescript.parse.TokenType.COLON_COLON;
import static net.forthecrown.typescript.parse.TokenType.COMMA;
import static net.forthecrown.typescript.parse.TokenType.CURLY_CLOSE;
import static net.forthecrown.typescript.parse.TokenType.CURLY_START;
import static net.forthecrown.typescript.parse.TokenType.DEC;
import static net.forthecrown.typescript.parse.TokenType.DIV;
import static net.forthecrown.typescript.parse.TokenType.DOT;
import static net.forthecrown.typescript.parse.TokenType.DOT_DOT;
import static net.forthecrown.typescript.parse.TokenType.ELLIPSES;
import static net.forthecrown.typescript.parse.TokenType.EOF;
import static net.forthecrown.typescript.parse.TokenType.EQUALS;
import static net.forthecrown.typescript.parse.TokenType.EXPONENTIAL;
import static net.forthecrown.typescript.parse.TokenType.GT;
import static net.forthecrown.typescript.parse.TokenType.GT_EQ;
import static net.forthecrown.typescript.parse.TokenType.HASHTAG;
import static net.forthecrown.typescript.parse.TokenType.HEX_LITERAL;
import static net.forthecrown.typescript.parse.TokenType.ID;
import static net.forthecrown.typescript.parse.TokenType.INC;
import static net.forthecrown.typescript.parse.TokenType.LT;
import static net.forthecrown.typescript.parse.TokenType.LT_EQ;
import static net.forthecrown.typescript.parse.TokenType.MOD;
import static net.forthecrown.typescript.parse.TokenType.MUL;
import static net.forthecrown.typescript.parse.TokenType.NEGATE;
import static net.forthecrown.typescript.parse.TokenType.NUMBER_LITERAL;
import static net.forthecrown.typescript.parse.TokenType.N_EQUALS;
import static net.forthecrown.typescript.parse.TokenType.OCTAL_LITERAL;
import static net.forthecrown.typescript.parse.TokenType.OPTIONAL;
import static net.forthecrown.typescript.parse.TokenType.OR;
import static net.forthecrown.typescript.parse.TokenType.PAREN_CLOSE;
import static net.forthecrown.typescript.parse.TokenType.PAREN_START;
import static net.forthecrown.typescript.parse.TokenType.SEMICOLON;
import static net.forthecrown.typescript.parse.TokenType.SHIFT_LEFT;
import static net.forthecrown.typescript.parse.TokenType.SHIFT_RIGHT;
import static net.forthecrown.typescript.parse.TokenType.SQUARE_CLOSE;
import static net.forthecrown.typescript.parse.TokenType.SQUARE_START;
import static net.forthecrown.typescript.parse.TokenType.STRICT_EQUALS;
import static net.forthecrown.typescript.parse.TokenType.STRICT_N_EQUALS;
import static net.forthecrown.typescript.parse.TokenType.STRING_LITERAL;
import static net.forthecrown.typescript.parse.TokenType.SUB;
import static net.forthecrown.typescript.parse.TokenType.TEMPLATE_STRING;
import static net.forthecrown.typescript.parse.TokenType.TERNARY;
import static net.forthecrown.typescript.parse.TokenType.UNKNOWN;
import static net.forthecrown.typescript.parse.TokenType.USHIFT_LEFT;
import static net.forthecrown.typescript.parse.TokenType.USHIFT_RIGHT;
import static net.forthecrown.typescript.parse.TokenType.XOR;

public class Lexer {

  final CharReader reader;
  final ErrorFactory factory;

  Token peeked;

  Location currentLocation;

  public Lexer(CharReader reader) {
    this.reader = reader;
    this.factory = reader.factory();
  }

  public boolean hasNext() {
    var peek = peek();
    return !peek.is(EOF);
  }

  public Token peek() {
    if (peeked != null) {
      return peeked;
    }

    return peeked = lex();
  }

  public Token next() {
    if (peeked != null) {
      var p = peeked;
      peeked = null;
      return p;
    }

    return lex();
  }

  private Token lex() {
    reader.skipEmpty();

    var location = reader.location();
    currentLocation = location;

    Token parsed = parse();
    currentLocation = null;
    return parsed;
  }

  private Token parse() {
    int peek = reader.peek();

    return switch (peek) {
      case  -1 -> token(EOF);

      case '(' -> singleCharToken(PAREN_START);
      case ')' -> singleCharToken(PAREN_CLOSE);
      case '{' -> singleCharToken(CURLY_START);
      case '}' -> singleCharToken(CURLY_CLOSE);
      case '[' -> singleCharToken(SQUARE_START);
      case ']' -> singleCharToken(SQUARE_CLOSE);

      case '~' -> singleCharToken(BIT_NOT);
      case '#' -> singleCharToken(HASHTAG);
      case ';' -> singleCharToken(SEMICOLON);
      case ',' -> singleCharToken(COMMA);

      case '?' -> {
        reader.skip();
        int p = reader.peek();

        if (p == '.') {
          reader.skip();
          yield token(OPTIONAL);
        }

        if (p == '?') {
          reader.skip();
          p = reader.peek();

          if (p == '=') {
            reader.skip();
            yield token(COALESCE_ASSIGN);
          }

          yield token(COALESCE);
        }

        yield token(TERNARY);
      }

      case '\'', '"' -> {
        String quoted = reader.readQuoted(false);
        yield token(STRING_LITERAL, quoted);
      }

      case '`' -> {
        String quoted = reader.readQuoted(true);
        yield token(TEMPLATE_STRING, quoted);
      }

      case '.' -> {
        reader.skip();
        int p = reader.peek();

        if (p == '.') {
          reader.skip();
          p = reader.peek();

          if (p == '.') {
            reader.skip();
            yield token(ELLIPSES);
          }

          yield token(DOT_DOT);
        }

        yield token(DOT);
      }

      case ':' -> onceOrTwice(':', COLON, COLON_COLON);

      case '&' -> mathOperator('&',  AND, BIT_AND, ASSIGN_AND);
      case '|' -> mathOperator('|',   OR,  BIT_OR,  ASSIGN_OR);

      case '-' -> {
        Location start = reader.location();
        Token numberToken = tryReadNumber();

        if (numberToken != null) {
          yield numberToken;
        } else {
          reader.location(start);
        }

        yield mathOperator('-', DEC, SUB, ASSIGN_SUB);
      }

      case '+' -> {
        Location start = reader.location();
        Token numberToken = tryReadNumber();

        if (numberToken != null) {
          yield numberToken;
        } else {
          reader.location(start);
        }

        yield mathOperator('+', INC, ADD, ASSIGN_ADD);
      }

      case '0' -> tryReadNumber();

      case '%' -> loneOrAssign(MOD, ASSIGN_MOD);
      case '/' -> loneOrAssign(DIV, ASSIGN_DIV);
      case '^' -> loneOrAssign(XOR, ASSIGN_XOR);

      case '*' -> {
        reader.skip();
        int p = reader.peek();

        if (p == '*') {
          reader.skip();
          yield token(EXPONENTIAL);
        }

        if (p == '=') {
          reader.skip();
          yield token(ASSIGN_MUL);
        }

        yield token(MUL);
      }

      case '<' -> comparisonOrBitshift('<',
          LT_EQ, LT, ASSIGN_SHIFT_LEFT,  SHIFT_LEFT,  ASSIGN_USHIFT_LEFT,  USHIFT_LEFT
      );

      case '>' -> comparisonOrBitshift('>',
          GT_EQ, GT, ASSIGN_SHIFT_RIGHT, SHIFT_RIGHT, ASSIGN_USHIFT_RIGHT, USHIFT_RIGHT
      );

      case '!' -> {
        reader.skip();
        int p = reader.peek();

        if (p == '=') {
          reader.skip();
          p = reader.peek();

          if (p == '=') {
            reader.skip();
            yield token(STRICT_N_EQUALS);
          }

          yield token(N_EQUALS);
        }

        yield token(NEGATE);
      }

      case '=' -> {
        reader.skip();
        int p = reader.peek();

        if (p == '>') {
          reader.skip();
          yield token(ARROW_FUNC);
        }

        if (p == '=') {
          reader.skip();
          p = reader.peek();

          if (p == '=') {
            reader.skip();
            yield token(STRICT_EQUALS);
          }

          yield token(EQUALS);
        }

        yield token(ASSIGN);
      }

      default -> {
        if (peek >= '0' && peek <= '9') {
          Token numberToken = tryReadNumber();

          if (numberToken == null) {
            throw factory.create(currentLocation, "Invalid number");
          }

          yield numberToken;
        }

        String id = reader.readIdentifier();

        if (id.isEmpty()) {
          reader.skip();
          yield token(UNKNOWN, Character.toString(peek));
        }

        TokenType keyword = TokenType.keywordLookup.get(id);
        System.out.printf("Looked up id '%s', found=%s\n", id, keyword);

        if (keyword == null) {
          yield token(ID, id);
        } else {
          yield token(keyword);
        }
      }
    };
  }

  private Token tryReadNumber() {
    int peek = reader.peek();

    boolean negative;

    if (peek == '-') {
      negative = true;
      reader.skip();
      peek = reader.peek();
    } else if (peek == '+') {
      negative = false;
      reader.skip();
      peek = reader.peek();
    } else {
      negative = false;
    }

    String sign = negative ? "-" : "";

    if (peek == '0') {
      reader.skip();
      int p = reader.peek();

      if (p == 'x' || p == 'X') {
        reader.skip();
        String hex = reader.readHex();

        if (hex.isEmpty()) {
          throw factory.create(currentLocation, "Invalid hex sequence");
        }

        return token(HEX_LITERAL, sign + hex);
      }

      if (p == 'b' || p == 'B') {
        reader.skip();
        String binary = reader.readBinary();

        if (binary.isEmpty()) {
          throw factory.create(currentLocation, "Invalid binary sequence");
        }

        return token(BINARY_LITERAL, binary);
      }

      boolean octalEnforced;

      if (p == 'o' || p == 'O') {
        reader.skip();
        octalEnforced = true;
      } else {
        octalEnforced = false;
      }

      String octal = reader.readOctal();

      if (octal.isEmpty()) {
        if (octalEnforced) {
          throw factory.create(currentLocation, "Invalid octal sequence");
        }

        return token(NUMBER_LITERAL, "0");
      } else {
        return token(OCTAL_LITERAL, octal);
      }
    }

    String number = reader.readNumber();

    if (number.isEmpty()) {
      return null;
    }

    // Hacky-ahh solution to this method throwing parsing correctly when there's illegal
    // characters following the number literal
    int p = reader.peek();
    if (Character.isJavaIdentifierStart(p) || Character.isJavaIdentifierPart(p)) {
      return null;
    }

    return token(NUMBER_LITERAL, sign + number);
  }

  private Token mathOperator(char ch, TokenType twice, TokenType lone, TokenType assign) {
    reader.skip();
    int p = reader.peek();

    if (p == '=') {
      reader.skip();
      return token(assign);
    }

    if (p == ch) {
      reader.skip();
      return token(twice);
    }

    return token(lone);
  }

  private Token onceOrTwice(char ch, TokenType once, TokenType twice) {
    reader.skip();
    int p = reader.peek();

    if (p == ch) {
      reader.skip();
      return token(twice);
    }

    return token(once);
  }

  private Token loneOrAssign(TokenType lone, TokenType assign) {
    reader.skip();
    int p = reader.peek();

    if (p == '=') {
      reader.skip();
      return token(assign);
    }

    return token(lone);
  }

  private Token comparisonOrBitshift(
      char ch,
      TokenType compareEq,
      TokenType alone,
      TokenType assignShift,
      TokenType shift,
      TokenType assignUShift,
      TokenType uShift
  ) {
    reader.skip();
    int p = reader.peek();

    if (p == '=') {
      reader.skip();
      return token(compareEq);
    }

    if (p == ch) {
      reader.skip();
      p = reader.peek();

      if (p == '=') {
        return token(assignShift);
      }

      if (p == ch) {
        reader.skip();
        p = reader.peek();

        if (p == '=') {
          return token(assignUShift);
        }

        return token(uShift);
      }

      return token(shift);
    }

    return token(alone);
  }

  private Token singleCharToken(TokenType type) {
    reader.skip();
    return token(type);
  }

  private Token token(TokenType type) {
    return token(type, "");
  }

  private Token token(TokenType type, String value) {
    return new Token(type, value, currentLocation, reader.location());
  }
}