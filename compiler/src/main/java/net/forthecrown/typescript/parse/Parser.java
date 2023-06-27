package net.forthecrown.typescript.parse;

import static net.forthecrown.typescript.parse.TokenType.ABSTRACT;
import static net.forthecrown.typescript.parse.TokenType.ADD;
import static net.forthecrown.typescript.parse.TokenType.AND;
import static net.forthecrown.typescript.parse.TokenType.ARROW;
import static net.forthecrown.typescript.parse.TokenType.ASSIGN;
import static net.forthecrown.typescript.parse.TokenType.ASSIGN_ADD;
import static net.forthecrown.typescript.parse.TokenType.ASSIGN_AND;
import static net.forthecrown.typescript.parse.TokenType.ASSIGN_COALESCE;
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
import static net.forthecrown.typescript.parse.TokenType.BIT_OR;
import static net.forthecrown.typescript.parse.TokenType.BREAK;
import static net.forthecrown.typescript.parse.TokenType.CASE;
import static net.forthecrown.typescript.parse.TokenType.CATCH;
import static net.forthecrown.typescript.parse.TokenType.CLASS;
import static net.forthecrown.typescript.parse.TokenType.COALESCE;
import static net.forthecrown.typescript.parse.TokenType.COLON;
import static net.forthecrown.typescript.parse.TokenType.COMMA;
import static net.forthecrown.typescript.parse.TokenType.CONST;
import static net.forthecrown.typescript.parse.TokenType.CURLY_CLOSE;
import static net.forthecrown.typescript.parse.TokenType.CURLY_START;
import static net.forthecrown.typescript.parse.TokenType.DEBUGGER;
import static net.forthecrown.typescript.parse.TokenType.DEC;
import static net.forthecrown.typescript.parse.TokenType.DEFAULT;
import static net.forthecrown.typescript.parse.TokenType.DIV;
import static net.forthecrown.typescript.parse.TokenType.DO;
import static net.forthecrown.typescript.parse.TokenType.DOT;
import static net.forthecrown.typescript.parse.TokenType.ELLIPSES;
import static net.forthecrown.typescript.parse.TokenType.ELSE;
import static net.forthecrown.typescript.parse.TokenType.EOF;
import static net.forthecrown.typescript.parse.TokenType.EQUALS;
import static net.forthecrown.typescript.parse.TokenType.EXPONENTIAL;
import static net.forthecrown.typescript.parse.TokenType.EXPORT;
import static net.forthecrown.typescript.parse.TokenType.EXTENDS;
import static net.forthecrown.typescript.parse.TokenType.FALSE;
import static net.forthecrown.typescript.parse.TokenType.FINALLY;
import static net.forthecrown.typescript.parse.TokenType.FOR;
import static net.forthecrown.typescript.parse.TokenType.FUNCTION;
import static net.forthecrown.typescript.parse.TokenType.GT;
import static net.forthecrown.typescript.parse.TokenType.GT_EQ;
import static net.forthecrown.typescript.parse.TokenType.HASHTAG;
import static net.forthecrown.typescript.parse.TokenType.HEX_LITERAL;
import static net.forthecrown.typescript.parse.TokenType.ID;
import static net.forthecrown.typescript.parse.TokenType.IF;
import static net.forthecrown.typescript.parse.TokenType.IMPORT;
import static net.forthecrown.typescript.parse.TokenType.IN;
import static net.forthecrown.typescript.parse.TokenType.INC;
import static net.forthecrown.typescript.parse.TokenType.INSTANCE_OF;
import static net.forthecrown.typescript.parse.TokenType.INTERFACE;
import static net.forthecrown.typescript.parse.TokenType.LET;
import static net.forthecrown.typescript.parse.TokenType.LT;
import static net.forthecrown.typescript.parse.TokenType.LT_EQ;
import static net.forthecrown.typescript.parse.TokenType.MOD;
import static net.forthecrown.typescript.parse.TokenType.MUL;
import static net.forthecrown.typescript.parse.TokenType.NEW;
import static net.forthecrown.typescript.parse.TokenType.NULL;
import static net.forthecrown.typescript.parse.TokenType.NUMBER_LITERAL;
import static net.forthecrown.typescript.parse.TokenType.N_EQUALS;
import static net.forthecrown.typescript.parse.TokenType.OCTAL_LITERAL;
import static net.forthecrown.typescript.parse.TokenType.OPTIONAL;
import static net.forthecrown.typescript.parse.TokenType.OR;
import static net.forthecrown.typescript.parse.TokenType.PAREN_CLOSE;
import static net.forthecrown.typescript.parse.TokenType.PAREN_START;
import static net.forthecrown.typescript.parse.TokenType.RETURN;
import static net.forthecrown.typescript.parse.TokenType.SEMICOLON;
import static net.forthecrown.typescript.parse.TokenType.SHIFT_LEFT;
import static net.forthecrown.typescript.parse.TokenType.SHIFT_RIGHT;
import static net.forthecrown.typescript.parse.TokenType.SQUARE_CLOSE;
import static net.forthecrown.typescript.parse.TokenType.SQUARE_START;
import static net.forthecrown.typescript.parse.TokenType.STRICT_EQUALS;
import static net.forthecrown.typescript.parse.TokenType.STRICT_N_EQUALS;
import static net.forthecrown.typescript.parse.TokenType.STRING_LITERAL;
import static net.forthecrown.typescript.parse.TokenType.SUB;
import static net.forthecrown.typescript.parse.TokenType.SWITCH;
import static net.forthecrown.typescript.parse.TokenType.TEMPLATE_EXPR;
import static net.forthecrown.typescript.parse.TokenType.TEMPLATE_QUOTE;
import static net.forthecrown.typescript.parse.TokenType.TERNARY;
import static net.forthecrown.typescript.parse.TokenType.THIS;
import static net.forthecrown.typescript.parse.TokenType.THROW;
import static net.forthecrown.typescript.parse.TokenType.TRUE;
import static net.forthecrown.typescript.parse.TokenType.TRY;
import static net.forthecrown.typescript.parse.TokenType.UNDEFINED;
import static net.forthecrown.typescript.parse.TokenType.USHIFT_LEFT;
import static net.forthecrown.typescript.parse.TokenType.USHIFT_RIGHT;
import static net.forthecrown.typescript.parse.TokenType.VAR;
import static net.forthecrown.typescript.parse.TokenType.VOID;
import static net.forthecrown.typescript.parse.TokenType.WHILE;
import static net.forthecrown.typescript.parse.TokenType.XOR;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import net.forthecrown.typescript.parse.ast.ArrayLiteral;
import net.forthecrown.typescript.parse.ast.ClassDeclaration.ClassType;
import net.forthecrown.typescript.parse.ast.ForInOfStatement;
import net.forthecrown.typescript.parse.ast.ForStatement;
import net.forthecrown.typescript.parse.ast.FunctionExpr;
import net.forthecrown.typescript.parse.ast.BinaryExpr;
import net.forthecrown.typescript.parse.ast.BinaryExpr.Operation;
import net.forthecrown.typescript.parse.ast.Block;
import net.forthecrown.typescript.parse.ast.BooleanLiteral;
import net.forthecrown.typescript.parse.ast.BreakStatement;
import net.forthecrown.typescript.parse.ast.CallExpr;
import net.forthecrown.typescript.parse.ast.ClassDeclaration;
import net.forthecrown.typescript.parse.ast.ClassDeclaration.AccessLevel;
import net.forthecrown.typescript.parse.ast.ClassDeclaration.ClassComponent;
import net.forthecrown.typescript.parse.ast.ClassDeclaration.FieldComponent;
import net.forthecrown.typescript.parse.ast.ClassDeclaration.FuncComponent;
import net.forthecrown.typescript.parse.ast.CompilationUnit;
import net.forthecrown.typescript.parse.ast.ConditionalExpr;
import net.forthecrown.typescript.parse.ast.ContinueStatement;
import net.forthecrown.typescript.parse.ast.DebuggerStatement;
import net.forthecrown.typescript.parse.ast.DoWhileStatement;
import net.forthecrown.typescript.parse.ast.EmptyExpr;
import net.forthecrown.typescript.parse.ast.EmptyStatement;
import net.forthecrown.typescript.parse.ast.ErrorExpr;
import net.forthecrown.typescript.parse.ast.ExportStatement;
import net.forthecrown.typescript.parse.ast.ExprStatement;
import net.forthecrown.typescript.parse.ast.Expression;
import net.forthecrown.typescript.parse.ast.FunctionDeclaration;
import net.forthecrown.typescript.parse.ast.FunctionSignature;
import net.forthecrown.typescript.parse.ast.Identifier;
import net.forthecrown.typescript.parse.ast.IfStatement;
import net.forthecrown.typescript.parse.ast.ImportStatement;
import net.forthecrown.typescript.parse.ast.ImportStatement.ImportedBinding;
import net.forthecrown.typescript.parse.ast.LabelledStatement;
import net.forthecrown.typescript.parse.ast.LexDeclarationStatement;
import net.forthecrown.typescript.parse.ast.LexDeclarationStatement.Kind;
import net.forthecrown.typescript.parse.ast.LexDeclarationStatement.SingleDeclaration;
import net.forthecrown.typescript.parse.ast.NewExpr;
import net.forthecrown.typescript.parse.ast.NullLiteral;
import net.forthecrown.typescript.parse.ast.NumberLiteral;
import net.forthecrown.typescript.parse.ast.ObjectLiteral;
import net.forthecrown.typescript.parse.ast.ObjectLiteral.ObjectProperty;
import net.forthecrown.typescript.parse.ast.ParameterStatement;
import net.forthecrown.typescript.parse.ast.ParenExpr;
import net.forthecrown.typescript.parse.ast.PropertyAccessExpr;
import net.forthecrown.typescript.parse.ast.ReturnStatement;
import net.forthecrown.typescript.parse.ast.Statement;
import net.forthecrown.typescript.parse.ast.StringLiteral;
import net.forthecrown.typescript.parse.ast.StringTemplateExpr;
import net.forthecrown.typescript.parse.ast.StringTemplateExpr.ExprPart;
import net.forthecrown.typescript.parse.ast.StringTemplateExpr.LiteralPart;
import net.forthecrown.typescript.parse.ast.SwitchStatement;
import net.forthecrown.typescript.parse.ast.SwitchStatement.ClauseCase;
import net.forthecrown.typescript.parse.ast.SwitchStatement.DefaultCase;
import net.forthecrown.typescript.parse.ast.SwitchStatement.SwitchCase;
import net.forthecrown.typescript.parse.ast.TaggedTemplateLiteral;
import net.forthecrown.typescript.parse.ast.ThisExpr;
import net.forthecrown.typescript.parse.ast.ThrowStatement;
import net.forthecrown.typescript.parse.ast.TryStatement;
import net.forthecrown.typescript.parse.ast.TryStatement.Catch;
import net.forthecrown.typescript.parse.ast.TypeParameter;
import net.forthecrown.typescript.parse.ast.UnaryExpr;
import net.forthecrown.typescript.parse.ast.UnaryExpr.UnaryOp;
import net.forthecrown.typescript.parse.ast.UpdateExpr;
import net.forthecrown.typescript.parse.ast.UpdateExpr.UpdateOp;
import net.forthecrown.typescript.parse.ast.WhileStatement;
import net.forthecrown.typescript.parse.type.FunctionType;
import net.forthecrown.typescript.parse.type.FunctionType.ParameterType;
import net.forthecrown.typescript.parse.type.ObjectType;
import net.forthecrown.typescript.parse.type.Type;
import net.forthecrown.typescript.parse.type.Types;

public class Parser {

  // Radix constants
  public static final int DEF_RADIX = 10;
  public static final int HEX_RADIX = 16;
  public static final int BIN_RADIX  = 2;
  public static final int OCT_RADIX  = 8;

  public static Set<TokenType> ASSIGNMENT_TOKENS = EnumSet.of(
      ASSIGN,
      ASSIGN_OR,
      ASSIGN_AND,
      ASSIGN_XOR,
      ASSIGN_SUB,
      ASSIGN_ADD,
      ASSIGN_DIV,
      ASSIGN_MUL,
      ASSIGN_MOD,
      ASSIGN_SHIFT_LEFT,
      ASSIGN_SHIFT_RIGHT,
      ASSIGN_USHIFT_LEFT,
      ASSIGN_USHIFT_RIGHT,
      ASSIGN_COALESCE
  );

  private final Lexer lexer;
  private final CompilerErrors errors;
  private final Types types;

  private final NameTable symbolTable;

  private final List<Token> tokenbuf = new ArrayList<>();

  private int cursor = 0;

  private boolean insideForIn = false;

  public Parser(Lexer lexer, CompilerErrors errors, Types types, NameTable table) {
    this.lexer = lexer;
    this.errors = errors;
    this.types = types;
    this.symbolTable = table;
  }

  public int getCursor() {
    return cursor;
  }

  public void setCursor(int cursor) {
    this.cursor = cursor;
  }

  public Token next() {
    if (cursor < tokenbuf.size()) {
      return tokenbuf.get(cursor++);
    }

    cursor++;
    var token = lexer.next();
    tokenbuf.add(token);
    return token;
  }

  public Token peek() {
    if (cursor < tokenbuf.size()) {
      return tokenbuf.get(cursor);
    }

    return lexer.peek();
  }

  public boolean matches(TokenType type) {
    return peek().is(type);
  }

  public boolean matches(TokenType... types) {
    return peek().is(types);
  }

  public boolean identifierMatches(String value) {
    return matches(ID) && peek().value().equals(value);
  }

  void expectEndOrComma(TokenType end) {
    if (matches(COMMA)) {
      next();
    } else if (!matches(end)) {
      expect(COMMA, end);
    }
  }

  public Token expect(String message, TokenType... types) {
    Token next = next();

    if (!next.is(types)) {
      String formatted = message
          .replace("${expected}", Token.typesToString(types))
          .replace("${actual}", next.type().toString());

      errors.error(next.location(), formatted);
    }

    return next;
  }

  public Token expectIdentifier(String id) {
    Token token = expect("Expected keyword '" + id + "', found ${actual}");

    if (!token.value().equals(id)) {
      errors.error(token.location(), "Expected keyword '%s' found '%s'", id, token.value());
    }

    return token;
  }

  public Token expect(TokenType types) {
    Token next = next();
    next.expect(errors.getFactory(), types);
    return next;
  }

  public Token expect(TokenType... types) {
    Token next = next();
    next.expect(errors.getFactory(), types);
    return next;
  }

  void notEof(String extra) {
    notEof(peek(), extra);
  }

  void notEof(Token token, String extra) {
    if (!token.is(EOF)) {
      return;
    }

    errors.error(token.location(), "Encountered EOF%s", extra == null ? "" : (" " + extra));
  }

  public CompilationUnit parse() {
    CompilationUnit unit = new CompilationUnit();
    unit.setStart(peek().location());

    while (lexer.hasNext()) {
      var peek = peek();

      if (peek.is(EXPORT)) {
        ExportStatement export = export();
        unit.getExports().add(export);
      } else if (peek.is(IMPORT)) {
        ImportStatement tsImport = importStatement();
        unit.getImports().add(tsImport);
      } else {
        Statement statement = statement();
        unit.getStatements().add(statement);
      }

    }

    return unit;
  }

  Statement statement() {
    var peek = peek();

    return switch (peek.type()) {
      case IF               -> ifStatement();
      case FOR              -> forStatement();
      case DO               -> doWhileStatement();
      case WHILE            -> whileStatement();
      case TRY              -> tryStatement();
      case SWITCH           -> switchStatement();

      case CONTINUE         -> continueStatement();
      case BREAK            -> breakStatement();
      case THROW            -> throwStatement();
      case RETURN           -> returnStatement();

      case CURLY_START      -> blockStatement();

      case SEMICOLON        -> emptyStatement();

      case DEBUGGER         -> debuggerStatement();

      // Declarations
      case VAR, LET, CONST  -> lexicalDeclaration();
      case CLASS,
          INTERFACE,
          ABSTRACT          -> classDeclaration();
      case FUNCTION         -> functionDeclaration();

      default -> {
        if (matches(ID)) {
          final int cursor = getCursor();
          Identifier id = id();

          if (matches(COLON)) {
            next();

            LabelledStatement labelled = new LabelledStatement();
            Statement stat = statement();
            labelled.setStart(id.getStart());
            labelled.setLabel(id);
            labelled.setStatement(stat);

            yield labelled;
          }

          setCursor(cursor);
        }

        yield exprStatement();
      }
    };
  }

  ExportStatement export() {
    Location start = expect(EXPORT).location();

    ExportStatement stat = new ExportStatement();
    stat.setStart(start);



    Statement exported;

    switch (peek().type()) {
      case VAR, LET, CONST -> {
        exported = lexicalDeclaration();
      }

      case INTERFACE, CLASS -> {
        exported = classDeclaration();
      }

      case FUNCTION -> {
        exported = functionDeclaration();
      }

      default -> {
        expect("Expected export statement ${expected}, found ${actual}",
            VAR, LET, CONST, INTERFACE, CLASS, FUNCTION
        );

        return stat;
      }
    }

    stat.setExported(exported);
    return stat;
  }

  Statement emptyStatement() {
    Location location = expect(SEMICOLON).location();
    EmptyStatement statement = new EmptyStatement();
    statement.setStart(location);
    return statement;
  }

  Block blockStatement() {
    var start = expect(CURLY_START);
    Block block = new Block(start.location());

    while (!matches(CURLY_CLOSE)) {
      notEof("inside block");

      Statement statement = statement();
      block.getStatements().add(statement);
    }

    expect(CURLY_CLOSE);
    return block;
  }

  Statement exprStatement() {
    var location = peek().location();
    Expression expr = expr();

    if (matches(SEMICOLON)) {
      next();
    }

    return new ExprStatement(location, expr);
  }

  Statement debuggerStatement() {
    Location start = expect(DEBUGGER).location();

    DebuggerStatement stat = new DebuggerStatement();
    stat.setStart(start);

    if (matches(STRING_LITERAL) && peek().location().line() == start.line()) {
      StringLiteral lit = stringLiteral();
      stat.setMessage(lit);
    }

    if (matches(SEMICOLON)) {
      next();
    }

    return stat;
  }

  Statement throwStatement() {
    Location start = expect(THROW).location();

    ThrowStatement stat = new ThrowStatement();
    stat.setStart(start);
    stat.setThrowValue(execEndExpr(start));

    return stat;
  }

  Statement returnStatement() {
    Location start = expect(RETURN).location();

    ReturnStatement stat = new ReturnStatement();
    stat.setStart(start);
    stat.setReturnValue(execEndExpr(start));

    return stat;
  }

  private Expression execEndExpr(Location start) {
    Token peek = peek();
    Location l = peek.location();

    if (peek.is(SEMICOLON) || l.line() != start.line()) {
      return null;
    }

    var expr = expr();

    if (matches(SEMICOLON)) {
      next();
    }

    return expr;
  }

  private ClassDeclaration classDeclaration() {
    Token token = expect(INTERFACE, CLASS, ABSTRACT);

    if (token.is(ABSTRACT)) {
      expect(CLASS);
    }

    Location start = token.location();
    Identifier name = id();

    boolean isInterface = token.is(INTERFACE);

    ClassDeclaration decl = new ClassDeclaration();
    decl.setName(name);
    decl.setStart(start);

    decl.setClassType(switch (token.type()) {
      case INTERFACE -> ClassType.INTERFACE;
      case CLASS -> ClassType.REGULAR;
      default -> ClassType.ABSTRACT;
    });

    if (matches(LT)) {
      typeParameters(decl.getTypeParameters());
    }

    if (matches(EXTENDS)) {
      next();

      Type extendsName = parseType();
      decl.setSuperClass(extendsName);
    }

    if (identifierMatches("implements")) {
      next();

      while (!matches(CURLY_START)) {
        Type type = parseType();
        decl.getImplementations().add(type);
        expectEndOrComma(CURLY_START);
      }
    }

    expect(CURLY_START);

    while (!matches(CURLY_CLOSE)) {
      notEof("inside class");

      ClassComponent component = classComponent(isInterface);
      decl.getComponents().add(component);

      if (matches(SEMICOLON)) {
        next();
      }
    }

    expect(CURLY_CLOSE);

    ObjectType type = types.object(decl);
    decl.setType(type);

    return decl;
  }

  ClassComponent classComponent(boolean isInterface) {
    Location loc = peek().location();
    boolean isAbstract = false;

    if (matches(ABSTRACT)) {
      next();
      isAbstract = true;
    }

    AccessLevel level = accessLevel();

    if (matches(ABSTRACT)) {
      if (isAbstract) {
        errors.error(peek().location(),
            "Duplicate declaration of 'abstract' keyword in class component"
        );
      }

      isAbstract = true;
      next();
    }

    if (isInterface && (level == AccessLevel.PRIVATE || level == AccessLevel.PROTECTED)) {
      errors.error(loc, "Invalid access level for interface declaration");
    }

    Identifier id = id();
    ClassComponent component;

    if (isAbstract) {
      component = classMethod(false);
    } else if (matches(PAREN_START, LT)) {
      component = classMethod(!isInterface);
    } else {
      component = classField(!isInterface);
    }

    component.setStart(loc);
    component.setName(id);
    component.setAccessLevel(level);

    return component;
  }

  FieldComponent classField(boolean valueAllowed) {
    FieldComponent c = new FieldComponent();
    c.setType(optionalType());

    if (!valueAllowed) {
      if (matches(ASSIGN)) {
        errors.error(peek().location(), "Interface values cannot have assigned values");
      }
    } else if (matches(ASSIGN)) {
      next();
      c.setValue(assignExpr());
    }

    return c;
  }

  FuncComponent classMethod(boolean bodyAllowed) {
    FuncComponent c = new FuncComponent();

    FunctionSignature sign = signature();
    c.setSignature(sign);

    if (!bodyAllowed) {
      if (matches(CURLY_START)) {
        errors.error(peek().location(), "Interface methods cannot have a body");
      }
    } else {
      Block body = blockStatement();
      c.setBody(body);
    }

    return c;
  }

  AccessLevel accessLevel() {
    Token peek = peek();

    if (peek.is(HASHTAG)) {
      next();
      return AccessLevel.PRIVATE;
    }

    if (!peek.is(ID)) {
      return AccessLevel.PUBLIC;
    }

    String value = peek.value();

    return switch (value) {
      case "private" -> {
        next();
        yield AccessLevel.PRIVATE;
      }

      case "public" -> {
        next();
        yield AccessLevel.PUBLIC;
      }

      case "protected" -> {
        next();
        yield AccessLevel.PROTECTED;
      }

      default -> AccessLevel.PUBLIC;
    };
  }

  FunctionSignature signature() {
    Location start = peek().location();
    FunctionSignature signature = new FunctionSignature();
    signature.setStart(start);

    if (matches(LT)) {
      typeParameters(signature.getTypeParameters());
    }

    parameterList(signature.getParameters());
    signature.setReturnType(optionalType());

    return signature;
  }

  void parameterList(List<ParameterStatement> params) {
    expect(PAREN_START);

    while (!matches(PAREN_CLOSE)) {
      notEof("inside function parameter list");

      ParameterStatement param = parameter();
      params.add(param);

      expectEndOrComma(PAREN_CLOSE);
    }

    expect(PAREN_CLOSE);
  }

  ParameterStatement parameter() {
    boolean varArgs;
    Location start;

    if (matches(ELLIPSES)) {
      varArgs = true;
      start = next().location();
    } else {
      varArgs = false;
      start = peek().location();
    }

    Identifier id = id();

    ParameterStatement node = new ParameterStatement();
    node.setStart(start);
    node.setVarArgs(varArgs);
    node.setName(id);
    node.setType(optionalType());

    if (matches(ASSIGN)) {
      next();
      Expression val = assignExpr();
      node.setDefaultValue(val);
    }

    return node;
  }

  Type optionalType() {
    if (matches(COLON)) {
      next();
      return parseType();
    }

    return null;
  }

  Type parseType() {
    if (matches(VOID)) {
      next();
      return Types.VOID;
    }

    if (matches(ID)) {
      return regularType();
    }

    if (matches(PAREN_START)) {
      return functionType();
    }

    errors.error(peek().location(), "Invalid type notation token");
    return null;
  }

  Type regularType() {
    Identifier id = id();
    Type type = types.fromName(id.getName());

    // < > parameterized type
    if (matches(LT)) {
      var start = next().location();

      List<Type> params = new ArrayList<>();

      while (!matches(GT)) {
        notEof("inside parameterized type");

        Type paramType = parseType();
        params.add(paramType);

        expectEndOrComma(GT);
      }

      if (params.isEmpty()) {
        errors.error(start, "Empty parameters");
      }

      if (type.getName().equals("Array") && params.size() > 1) {
        errors.error(start, "Array type cannot have more than 1 type parameter");
      }

      // If the type is 'Array' then an array type is returned by the method
      type = types.parameterized(type, params);
    }

    // [ ] array type, these can be repeated, like 'TypeName[][]'
    while (matches(SQUARE_START)) {
      next();
      expect(SQUARE_CLOSE);
      type = types.array(type);
    }

    return type;
  }

  Type functionType() {
    expect(PAREN_START);
    FunctionType funcType = new FunctionType();

    while (!matches(PAREN_CLOSE)) {
      notEof("inside type annotation");

      Identifier id = id();
      expect(COLON);
      Type type = parseType();

      ParameterType param = new ParameterType(id.getName(), type);
      funcType.getParams().add(param);

      expectEndOrComma(PAREN_CLOSE);
    }

    expect(PAREN_CLOSE);
    expect(ARROW);

    Type returnType = parseType();
    funcType.setReturnType(returnType);

    return types.addType(funcType);
  }

  Statement functionDeclaration() {
    Location start = expect(FUNCTION).location();

    FunctionDeclaration decl = new FunctionDeclaration();
    decl.setStart(start);

    Identifier name = id();
    decl.setName(name);

    FunctionExpr expr = functionExpr();
    decl.setExpr(expr);

    return decl;
  }

  void typeParameters(List<TypeParameter> typeParameters) {
    expect(LT);

    while (!matches(GT)) {
      notEof("inside type parameters declaration");

      Identifier id = id();
      TypeParameter param = new TypeParameter();
      param.setStart(id.getStart());
      param.setName(id);

      if (matches(EXTENDS)) {
        next();

        Type type = parseType();
        param.setSuperType(type);
      }

      typeParameters.add(param);
      expectEndOrComma(GT);
    }

    expect(GT);
  }

  Statement lexicalDeclaration() {
    Token startToken = expect(LET, VAR, CONST);
    Location start = startToken.location();

    LexDeclarationStatement node = new LexDeclarationStatement();
    node.setStart(start);

    node.setKind(
        switch (startToken.type()) {
          case VAR -> Kind.VAR;
          case LET -> Kind.LET;
          default -> Kind.CONST;
        }
    );

    lexicalDeclarationValues(node);

    if (matches(SEMICOLON)) {
      next();
    }

    return node;
  }

  void lexicalDeclarationValues(LexDeclarationStatement node) {
    while (true) {
      notEof("inside variable declarations");

      Location loc = peek().location();
      Identifier id = id();

      SingleDeclaration decl = new SingleDeclaration();
      decl.setStart(loc);
      decl.setIdentifier(id);
      decl.setType(optionalType());

      if (matches(ASSIGN)) {
        next();

        Expression expr = assignExpr();
        decl.setValue(expr);
      }

      node.getDeclarations().add(decl);

      if (insideForIn && (identifierMatches("of") || matches(IN))) {
        break;
      }

      if (matches(COMMA)) {
        next();
        continue;
      }

      break;
    }
  }

  Statement switchStatement() {
    var location = expect(SWITCH).location();
    SwitchStatement stat = new SwitchStatement();
    stat.setStart(location);

    expect(PAREN_START);
    Expression expr = expr();
    stat.setExpression(expr);
    expect(PAREN_CLOSE);

    expect(CURLY_START);

    while (!matches(CURLY_CLOSE)) {
      notEof("inside switch statement");

      var clause = expect(CASE, DEFAULT);
      SwitchCase switchCase;

      if (clause.is(CASE)) {
        ClauseCase clauseCase = new ClauseCase();
        Expression clauseExpr = expr();
        clauseCase.setExpression(clauseExpr);
        switchCase = clauseCase;
      } else {
        switchCase = new DefaultCase();
      }

      switchCase.setStart(clause.location());

      Location blockStart = expect(COLON).location();
      Block block = new Block(blockStart);

      while (!matches(CURLY_CLOSE, CASE, DEFAULT)) {
        notEof(peek(), "inside switch statement case");

        Statement statement = statement();
        block.getStatements().add(statement);
      }

      switchCase.setStatement(block);
      stat.getCases().add(switchCase);
    }

    expect(CURLY_CLOSE);
    return stat;
  }

  Statement doWhileStatement() {
    Token start = expect(DO);
    Location loc = start.location();

    DoWhileStatement statement = new DoWhileStatement();
    statement.setStart(loc);

    Block block = blockStatement();
    statement.setBody(block);

    expect(WHILE);

    expect(PAREN_START);
    Expression condition = expr();
    expect(PAREN_CLOSE);

    if (matches(SEMICOLON)) {
      next();
    }

    statement.setCondition(condition);

    return statement;
  }

  Statement whileStatement() {
    Token start = expect(WHILE);
    Location loc = start.location();

    WhileStatement stat = new WhileStatement();
    stat.setStart(loc);

    expect(PAREN_START);
    Expression expr = expr();
    stat.setCondition(expr);
    expect(PAREN_CLOSE);

    Block body = blockStatement();
    stat.setBody(body);

    return body;
  }

  Statement tryStatement() {
    Location start = expect(TRY).location();

    TryStatement stat = new TryStatement();
    stat.setStart(start);
    stat.setBody(blockStatement());

    while (matches(CATCH)) {
      next();
      notEof("while parsing try/catch statement");

      var catchStart = expect(PAREN_START).location();
      Catch node = new Catch();
      node.setStart(catchStart);

      node.setLabel(id());
      node.setErrorType(optionalType());

      expect(PAREN_CLOSE);

      Block body = blockStatement();
      node.setBody(body);
    }

    if (matches(FINALLY)) {
      next();

      Block body = blockStatement();
      stat.setFinallyBody(body);
    }

    return stat;
  }

  Statement ifStatement() {
    Location start = expect(IF).location();

    IfStatement stat = new IfStatement();
    stat.setStart(start);

    expect(PAREN_START);
    Expression expr = expr();
    stat.setCondition(expr);
    expect(PAREN_CLOSE);

    Statement body = statement();
    stat.setBody(body);

    if (matches(ELSE)) {
      next();

      Statement elsePart = statement();
      stat.setBody(elsePart);
    }

    return stat;
  }

  Statement breakStatement() {
    Location start = expect(BREAK).location();
    var peek = peek();

    BreakStatement stat = new BreakStatement();
    stat.setStart(start);

    if (peek.is(ID) && peek.location().line() == start.line()) {
      stat.setLabel(id());
    }

    if (matches(SEMICOLON)) {
      next();
    }

    return stat;
  }

  Statement continueStatement() {
    Location start = expect(BREAK).location();
    var peek = peek();

    ContinueStatement statement = new ContinueStatement();
    statement.setStart(start);

    if (peek.is(ID) && peek.location().line() == start.line()) {
      statement.setLabel(id());
    }

    if (matches(SEMICOLON)) {
      next();
    }

    return statement;
  }

  Statement forStatement() {
    Location start = expect(FOR).location();
    expect(PAREN_START);

    insideForIn = true;

    LexDeclarationStatement decl = new LexDeclarationStatement();
    Token declKeyword = expect(LET, CONST, VAR);

    LexDeclarationStatement.Kind kind = switch (declKeyword.type()) {
      case LET -> Kind.LET;
      case VAR -> Kind.VAR;
      default -> Kind.CONST;
    };

    decl.setStart(declKeyword.location());
    decl.setKind(kind);

    final int type_unknown = -1;
    final int type_normal = 0;
    final int type_of = 1;
    final int type_in = 2;
    final int type_in_of = 3;

    int type = type_unknown;

    if (matches(ID)) {
      lexicalDeclarationValues(decl);
    } else if (!matches(CURLY_START)) {
      expect(CURLY_START);
    } else {
      ObjectLiteral literal = objectLiteral(false);
      literal.setDestructuring(true);
      decl.getDeclarations().add(literal);

      type = type_in_of;
    }

    if (type == type_unknown) {
      if (matches(IN)) {
        type = type_in;
        next();
      } else if (identifierMatches("of")) {
        type = type_of;
        next();
      } else {
        expect(SEMICOLON, IN);
        type = type_normal;
      }
    } else if (matches(IN)) {
      type = type_in;
      next();
    } else {
      expectIdentifier("of");
      type = type_of;
    }

    if (type == type_normal) {
      ForStatement statement = new ForStatement();
      statement.setStart(start);

      Expression expr1 = expr();
      expect(SEMICOLON);
      Expression expr2 = expr();
      expect(PAREN_CLOSE);

      Statement body = statement();

      statement.setFirst(decl);
      statement.setSecond(expr1);
      statement.setThird(expr2);
      statement.setBody(body);

      insideForIn = false;
      return statement;
    }

    ForInOfStatement forOf = new ForInOfStatement();
    Expression ofObject = assignExpr();
    expect(PAREN_CLOSE);
    Statement body = statement();

    forOf.setStart(start);
    forOf.setOf(type == type_of);
    forOf.setDeclaration(decl);
    forOf.setObject(ofObject);
    forOf.setBody(body);

    insideForIn = false;
    return forOf;
  }

  ImportStatement importStatement() {
    Location start = expect(IMPORT).location();

    ImportStatement stat = new ImportStatement();
    stat.setStart(start);

    if (matches(STRING_LITERAL)) {
      StringLiteral lit = stringLiteral();
      stat.setModuleName(lit);
      return stat;
    }

    // Star import
    if (matches(MUL)) {
      next();
      stat.setStarImport(true);
    } else if (matches(ID)) {
      Identifier id = id();

      ImportedBinding binding = new ImportedBinding();
      binding.setBinding(id);
      stat.getImports().add(binding);
    } else {
      expect(CURLY_START);

      while (!matches(CURLY_CLOSE)) {
        notEof("inside imports");

        ImportedBinding binding = new ImportedBinding();
        binding.setStart(peek().location());

        Identifier label = id();
        binding.setBinding(label);

        if (identifierMatches("as")) {
          next();
          Identifier alias = id();
          binding.setAlias(alias);
        }

        stat.getImports().add(binding);
        expectEndOrComma(CURLY_CLOSE);
      }

      expect(CURLY_CLOSE);
    }

    if (identifierMatches("as")) {
      next();
      Identifier as = id();
      stat.setAlias(as);
    }

    Token fromKeyword = expect(ID);

    if (!fromKeyword.value().equals("from")) {
      errors.error(fromKeyword.location(),
          "Expected 'from' import declaration, found '%s' instead",
          fromKeyword.value()
      );
    }

    StringLiteral lit = stringLiteral();
    stat.setModuleName(lit);

    return stat;
  }

  Expression primaryExpr() {
    return switch (peek().type()) {
      case THIS           -> thisExpr();
      case ID             -> id();

      // Literals
      case STRING_LITERAL -> stringLiteral();

      case NUMBER_LITERAL,
          HEX_LITERAL,
          OCTAL_LITERAL,
          BINARY_LITERAL  -> numberLiteral();

      case SQUARE_START   -> arrayLiteral();
      case CURLY_START    -> objectLiteral();
      case NULL, UNDEFINED-> nullLiteral();
      case TRUE, FALSE    -> booleanLiteral();
      case TEMPLATE_QUOTE -> templateLiteral();

      case FUNCTION -> {
        next();
        yield functionExpr();
      }

      default -> errorExpr("Unknown expression token %s", peek());
    };
  }

  ErrorExpr errorExpr(String msg, Object... args) {
    Location loc = next().location();
    errors.error(loc, msg, args);

    ErrorExpr expr = new ErrorExpr();
    expr.setStart(loc);
    expr.setMessage(msg.formatted(args));
    return expr;
  }

  NumberLiteral numberLiteral() {
    Token token = expect(
        NUMBER_LITERAL, HEX_LITERAL, OCTAL_LITERAL,
        BINARY_LITERAL
    );

    Location loc = token.location();

    String strVal = token.value();
    boolean bigInt = strVal.endsWith("n") || strVal.endsWith("N");

    if (bigInt) {
      strVal = strVal.substring(0, strVal.length() - 1);
    }

    int radix = switch (token.type()) {
      case HEX_LITERAL    -> HEX_RADIX;
      case OCTAL_LITERAL  -> OCT_RADIX;
      case BINARY_LITERAL -> BIN_RADIX;
      default             -> DEF_RADIX;
    };

    Number value;

    if (bigInt) {
      value = new BigInteger(strVal, radix);
    } else if (radix == DEF_RADIX) {
      value = Double.parseDouble(strVal);
    } else {
      value = Long.parseLong(strVal, radix);
    }

    return new NumberLiteral(loc, value);
  }

  StringLiteral stringLiteral() {
    Token token = expect(STRING_LITERAL);
    return new StringLiteral(token.location(), token.value());
  }

  Identifier id() {
    Token token = expect(ID);
    Identifier id = id(token.value());
    id.setStart(token.location());
    return id;
  }

  Identifier id(String value) {
    Name symbol = symbolTable.symbol(value);
    return new Identifier(symbol);
  }

  ThisExpr thisExpr() {
    return new ThisExpr(expect(THIS).location());
  }

  NullLiteral nullLiteral() {
    Token token = expect(NULL, UNDEFINED);
    return new NullLiteral(token.location(), token.is(UNDEFINED));
  }

  BooleanLiteral booleanLiteral() {
    Token token = expect(TRUE, FALSE);
    return new BooleanLiteral(token.location(), token.is(TRUE));
  }

  ArrayLiteral arrayLiteral() {
    Location start = expect(SQUARE_START).location();

    ArrayLiteral literal = new ArrayLiteral();
    literal.setStart(start);

    while (!matches(SQUARE_CLOSE)) {
      notEof("inside array literal");

      var peek = peek();

      // Seeing a comma before reading an element? Empty element!
      if (peek.is(COMMA)) {
        next();

        EmptyExpr expr = new EmptyExpr();
        expr.setStart(peek.location());

        literal.getValues().add(expr);
        continue;
      }

      Expression expr = assignExpr();
      literal.getValues().add(expr);

      expectEndOrComma(SQUARE_CLOSE);
    }

    expect(SQUARE_CLOSE);
    return literal;
  }

  ObjectLiteral objectLiteral() {
    return objectLiteral(true);
  }

  ObjectLiteral objectLiteral(boolean allowValues) {
    Location start = expect(CURLY_START).location();

    ObjectLiteral literal = new ObjectLiteral();
    literal.setStart(start);

    while (!matches(CURLY_CLOSE)) {
      notEof("inside object literal");

      Expression key = switch (peek().type()) {
        case ID -> id();
        case STRING_LITERAL -> stringLiteral();
        case NUMBER_LITERAL, OCTAL_LITERAL, HEX_LITERAL, BINARY_LITERAL -> numberLiteral();
        default -> errorExpr(
            "Invalid property key token, must be an identifier, "
                + "quoted string, or number literal"
        );
      };

      Expression value = objectLiteralValue(allowValues);

      ObjectProperty property = new ObjectProperty();
      property.setStart(key.getStart());
      property.setKey(key);
      property.setValue(value);

      literal.getProperties().add(property);

      expectEndOrComma(CURLY_CLOSE);
    }

    expect(CURLY_CLOSE);
    return literal;
  }

  Expression objectLiteralValue(boolean allowValues) {
    if (matches(COLON)) {
      if (!allowValues) {
        errors.error(peek().location(), "ObjectLiteral values not allowed here");
      }

      next();
      return assignExpr();
    }

    if (matches(PAREN_START, LT)) {
      return functionExpr();
    }

    return null;
  }

  Expression parenthesizedExpr() {
    Location start = expect(PAREN_START).location();
    ParenExpr expr = new ParenExpr();
    expr.setStart(start);

    Expression contained = expr();
    expr.setExpr(contained);

    expect(PAREN_CLOSE);

    return expr;
  }

  StringTemplateExpr templateLiteral() {
    Token token = expect(TEMPLATE_QUOTE);

    StringTemplateExpr expr = new StringTemplateExpr();
    expr.setStart(token.location());

    var reader = lexer.reader;

    while (lexer.insideTemplateString) {
      Location location = reader.location();
      String value = reader.readTemplateSection();

      if (!value.isEmpty()) {
        LiteralPart part = new LiteralPart();
        part.setStart(location);
        part.setValue(value);

        expr.getParts().add(part);
        continue;
      }

      if (reader.peek() == '`') {
        break;
      }

      expect(TEMPLATE_EXPR);
      lexer.insideTemplateString = false;
      Expression partExpr = expr();
      lexer.insideTemplateString = true;
      expect(CURLY_CLOSE);

      ExprPart part = new ExprPart();
      part.setStart(location);
      part.setExpr(partExpr);
      expr.getParts().add(part);
    }

    expect(TEMPLATE_QUOTE);
    return expr;
  }

  Expression ternary() {
    Expression expr = coalesceExpr();

    if (!matches(TERNARY)) {
      return expr;
    }

    Location start = next().location();

    ConditionalExpr cond = new ConditionalExpr();
    cond.setStart(start);
    cond.setCondition(expr);

    Expression onTrue = assignExpr();
    cond.setOnTrue(onTrue);

    expect(COLON);

    Expression onFalse = assignExpr();
    cond.setOnFalse(onFalse);

    return cond;
  }

  Expression assignExpr() {
    Expression ternary = ternary();

    Token peek = peek();
    TokenType peekType = peek.type();

    if (peek.is(ARROW)) {
      return arrowFunction(ternary.getStart().index());
    }

    if (!ASSIGNMENT_TOKENS.contains(peekType)) {
      return ternary;
    }

    next();

    // So... many (._. )
    Operation operation = switch (peek.type()) {
      case ASSIGN_OR            -> Operation.ASSIGN_OR;
      case ASSIGN_AND           -> Operation.ASSIGN_AND;
      case ASSIGN_XOR           -> Operation.ASSIGN_XOR;
      case ASSIGN_SUB           -> Operation.ASSIGN_SUB;
      case ASSIGN_ADD           -> Operation.ASSIGN_ADD;
      case ASSIGN_DIV           -> Operation.ASSIGN_DIV;
      case ASSIGN_MUL           -> Operation.ASSIGN_MUL;
      case ASSIGN_MOD           -> Operation.ASSIGN_MOD;
      case ASSIGN_SHIFT_LEFT    -> Operation.ASSIGN_SHIFT_LEFT;
      case ASSIGN_SHIFT_RIGHT   -> Operation.ASSIGN_SHIFT_RIGHT;
      case ASSIGN_USHIFT_LEFT   -> Operation.ASSIGN_USHIFT_LEFT;
      case ASSIGN_USHIFT_RIGHT  -> Operation.ASSIGN_USHIFT_RIGHT;
      case ASSIGN_COALESCE      -> Operation.ASSIGN_COALESCE;

      default                   -> Operation.ASSIGN;
    };

    Expression rhs = assignExpr();

    BinaryExpr expr = new BinaryExpr();
    expr.setOperation(operation);
    expr.setStart(peek.location());
    expr.setLeft(ternary);
    expr.setRight(rhs);

    return expr;
  }

  Expression arrowFunction(final int parenthesesStart) {
    setCursor(parenthesesStart);

    FunctionExpr arrow = new FunctionExpr();
    arrow.setArrowFunction(true);

    FunctionSignature signature = new FunctionSignature();

    arrow.setStart(peek().location());
    signature.setStart(arrow.getStart());

    arrow.setSignature(signature);

    if (matches(LT)) {
      typeParameters(signature.getTypeParameters());
    }

    if (matches(PAREN_START)) {
      parameterList(signature.getParameters());
    } else {
      ParameterStatement param = parameter();
      signature.getParameters().add(param);
    }

    signature.setReturnType(optionalType());

    expect(ARROW);

    Statement body = statement();
    arrow.setBody(body);

    return arrow;
  }

  private Expression _recursiveExpr(Supplier<Expression> previous,
                                    Operation operation,
                                    TokenType type
  ) {
    Expression expr = previous.get();
    Location start = expr.getStart();

    while (matches(type)) {
      next();
      expr = new BinaryExpr(start, operation, expr, previous.get());
    }

    return expr;
  }

  Expression coalesceExpr() {
    return _recursiveExpr(this::logicOr, Operation.COALESCE, COALESCE);
  }

  Expression logicOr() {
    return _recursiveExpr(this::logicAnd, Operation.OR, OR);
  }

  Expression logicAnd() {
    return _recursiveExpr(this::bitwiseOr, Operation.AND, AND);
  }

  Expression bitwiseOr() {
    return _recursiveExpr(this::bitwiseAnd, Operation.OR, BIT_OR);
  }

  Expression bitwiseAnd() {
    return _recursiveExpr(this::bitwiseXor, Operation.AND, BIT_AND);
  }

  Expression bitwiseXor() {
    return _recursiveExpr(this::equalityExpr, Operation.XOR, XOR);
  }

  Expression equalityExpr() {
    Expression relational = relationalExpr();
    final Location start = relational.getStart();

    while (matches(EQUALS, N_EQUALS, STRICT_EQUALS, STRICT_N_EQUALS)) {
      Operation op = switch (next().type()) {
        case EQUALS -> Operation.EQ;
        case N_EQUALS -> Operation.NEQ;
        case STRICT_EQUALS -> Operation.S_EQ;
        default -> Operation.S_NEQ;
      };

      relational = new BinaryExpr(start, op, relational, relationalExpr());
    }

    return relational;
  }

  Expression relationalExpr() {
    Expression shift = shiftExpr();
    final Location start = shift.getStart();

    while (matches(IN, INSTANCE_OF, LT_EQ, LT, GT, GT_EQ)) {
      Operation op = switch (next().type()) {
        case LT_EQ -> Operation.LTE;
        case GT_EQ -> Operation.GTE;
        case LT -> Operation.LT;
        case GT -> Operation.GT;
        default -> Operation.INSTANCEOF;
      };

      shift = new BinaryExpr(start, op, shift, shiftExpr());
    }

    return shift;
  }

  Expression shiftExpr() {
    Expression add = additiveExpr();
    final Location start = add.getStart();

    while (matches(SHIFT_LEFT, USHIFT_LEFT, SHIFT_RIGHT, USHIFT_RIGHT)) {
      Operation op = switch (next().type()) {
        case SHIFT_LEFT -> Operation.SH_LEFT;
        case SHIFT_RIGHT -> Operation.SH_RIGHT;
        case USHIFT_LEFT -> Operation.USH_LEFT;
        default -> Operation.USH_RIGHT;
      };

      add = new BinaryExpr(start, op, add, additiveExpr());
    }

    return add;
  }

  Expression additiveExpr() {
    Expression mult = multiplicativeExpr();
    final Location start = mult.getStart();

    while (matches(ADD, SUB)) {
      Operation op = next().is(SUB)
          ? Operation.SUB
          : Operation.ADD;

      mult = new BinaryExpr(start, op, mult, multiplicativeExpr());
    }

    return mult;
  }

  Expression multiplicativeExpr() {
    Expression previous = exponentialExpr();
    final Location start = previous.getStart();

    while (matches(DIV, MUL, MOD)) {
      Operation op = switch (next().type()) {
        case DIV -> Operation.DIV;
        case MUL -> Operation.MUL;
        default -> Operation.MOD;
      };

      previous = new BinaryExpr(start, op, previous, exponentialExpr());
    }

    return previous;
  }

  Expression exponentialExpr() {
    return _recursiveExpr(this::unaryExpr, Operation.EXP, EXPONENTIAL);
  }

  Expression unaryExpr() {
    Location start = peek().location();

    return switch (peek().type()) {
      case VOID -> {
        next();
        yield new UnaryExpr(start,  unaryExpr(), UnaryOp.VOID);
      }

      case DELETE -> {
        next();
        yield new UnaryExpr(start,  unaryExpr(), UnaryOp.DELETE);
      }

      case BIT_NOT -> {
        next();
        yield new UnaryExpr(start,  unaryExpr(), UnaryOp.BIT_NOT);
      }

      case NEGATE -> {
        next();
        yield new UnaryExpr(start,  unaryExpr(), UnaryOp.LOG_NOT);
      }

      case INC -> {
        next();
        yield new UpdateExpr(start, unaryExpr(), UpdateOp.PRE_INC);
      }

      case DEC -> {
        next();
        yield new UpdateExpr(start, unaryExpr(), UpdateOp.PRE_DEC);
      }

      case ADD -> {
        next();
        yield new UnaryExpr(start,  unaryExpr(), UnaryOp.POSITIVE);
      }

      case SUB -> {
        next();
        yield new UnaryExpr(start,  unaryExpr(), UnaryOp.NEGATIVE);
      }

      default -> {
        Expression member = memberExpr(true);

        if (!matches(INC, DEC)) {
          yield member;
        }

        UpdateOp op = next().is(INC) ? UpdateOp.POST_INC : UpdateOp.POST_DEC;
        yield new UpdateExpr(start, member, op);
      }
    };
  }

  Expression newExpr() {
    Location start = next().location();
    Expression member = memberExpr(false);

    NewExpr newExpr = new NewExpr();
    newExpr.setStart(start);
    newExpr.setTarget(member);

    if (matches(PAREN_START)) {
      argumentList(newExpr.getArguments());
    }

    if (matches(CURLY_START)) {
      ObjectLiteral literal = objectLiteral();
      newExpr.setInitializer(literal);
    }

    return newExpr;
  }

  Expression callExpr(Expression target) {
    if (!matches(PAREN_START)) {
      expect(PAREN_CLOSE);
    }

    Location start = peek().location();

    CallExpr call = new CallExpr();
    call.setStart(start);
    call.setTarget(target);

    argumentList(call.getArguments());

    return call;
  }

  Expression memberExpr(boolean allowCall) {
    Expression expr;

    if (matches(NEW)) {
      expr = newExpr();
    } else {
      expr = primaryExpr();
    }

    return memberExprTail(allowCall, expr);
  }

  Expression memberExprTail(boolean allowCall, Expression parent) {
    Expression expr = parent;

    outer: while (true) {
      switch (peek().type()) {

        // Property access: '.' expr | '.?' expr | '[' expr ']'
        case DOT, OPTIONAL, SQUARE_START -> {
          expr = propertyAccess(expr);
        }

        // () function call expression
        case PAREN_START -> {
          if (!allowCall) {
            break outer;
          }

          expr = callExpr(expr);
        }

        // Tagged template literal
        case TEMPLATE_QUOTE -> {
          StringTemplateExpr template = templateLiteral();
          TaggedTemplateLiteral tagged = new TaggedTemplateLiteral();
          tagged.setStart(expr.getStart());
          tagged.setExpr(expr);
          tagged.setTemplate(template);

          expr = tagged;
        }

        default -> {
          break outer;
        }
      }
    }

    return expr;
  }

  void argumentList(List<Expression> target) {
    expect(PAREN_START);

    while (!matches(PAREN_CLOSE)) {
      notEof("inside argument/function call arguments");

      Expression expr = assignExpr();
      target.add(expr);

      if (matches(COMMA)) {
        next();
      } else if (!matches(PAREN_CLOSE)) {
        expect(PAREN_CLOSE, COMMA);
      }
    }

    expect(PAREN_CLOSE);
  }

  Expression propertyAccess(Expression parent) {
    Token start = expect(SQUARE_START, OPTIONAL, DOT);

    PropertyAccessExpr expr = new PropertyAccessExpr();
    expr.setTarget(parent);
    expr.setStart(start.location());
    expr.setElementAccess(start.is(SQUARE_START));

    if (start.is(SQUARE_START)) {
      Expression prop = expr();

      expr.setProperty(prop);
      expr.setOptional(false);

      expect(SQUARE_CLOSE);
    } else {
      boolean optional = start.is(OPTIONAL);
      expr.setOptional(optional);

      Identifier property = id();
      expr.setProperty(property);
    }

    return expr;
  }

  Expression expr() {
    Expression expr = assignExpr();

    while (matches(COMMA)) {
      next();

      BinaryExpr bin = new BinaryExpr();
      bin.setStart(expr.getStart());
      bin.setOperation(Operation.COMMA);
      bin.setLeft(expr);
      bin.setRight(assignExpr());

      expr = bin;
    }

    return expr;
  }

  FunctionExpr functionExpr() {
    FunctionSignature signature = signature();
    Block body = blockStatement();

    FunctionExpr expr = new FunctionExpr();
    expr.setStart(signature.getStart());
    expr.setSignature(signature);
    expr.setBody(body);

    return expr;
  }
}