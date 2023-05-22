package net.forthecrown.typescript.parse;

import static net.forthecrown.typescript.parse.TokenType.ADD;
import static net.forthecrown.typescript.parse.TokenType.AND;
import static net.forthecrown.typescript.parse.TokenType.ARROW_FUNC;
import static net.forthecrown.typescript.parse.TokenType.ASSIGN;
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
import static net.forthecrown.typescript.parse.TokenType.TEMPLATE_STRING;
import static net.forthecrown.typescript.parse.TokenType.TERNARY;
import static net.forthecrown.typescript.parse.TokenType.THIS;
import static net.forthecrown.typescript.parse.TokenType.THROW;
import static net.forthecrown.typescript.parse.TokenType.TRUE;
import static net.forthecrown.typescript.parse.TokenType.TRY;
import static net.forthecrown.typescript.parse.TokenType.USHIFT_LEFT;
import static net.forthecrown.typescript.parse.TokenType.USHIFT_RIGHT;
import static net.forthecrown.typescript.parse.TokenType.VAR;
import static net.forthecrown.typescript.parse.TokenType.WHILE;
import static net.forthecrown.typescript.parse.TokenType.XOR;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.forthecrown.typescript.parse.ast.ArrayLiteral;
import net.forthecrown.typescript.parse.ast.BinaryExpr;
import net.forthecrown.typescript.parse.ast.BinaryExpr.Operation;
import net.forthecrown.typescript.parse.ast.Block;
import net.forthecrown.typescript.parse.ast.BooleanLiteral;
import net.forthecrown.typescript.parse.ast.BreakStatement;
import net.forthecrown.typescript.parse.ast.CallExpr;
import net.forthecrown.typescript.parse.ast.ClassDeclaration;
import net.forthecrown.typescript.parse.ast.ClassDeclaration.ClassComponent;
import net.forthecrown.typescript.parse.ast.ClassDeclaration.FieldComponent;
import net.forthecrown.typescript.parse.ast.ClassDeclaration.FuncComponent;
import net.forthecrown.typescript.parse.ast.ContinueStatement;
import net.forthecrown.typescript.parse.ast.DebuggerStatement;
import net.forthecrown.typescript.parse.ast.DoWhileStatement;
import net.forthecrown.typescript.parse.ast.EmptyExpr;
import net.forthecrown.typescript.parse.ast.EmptyStatement;
import net.forthecrown.typescript.parse.ast.ErrorExpr;
import net.forthecrown.typescript.parse.ast.ExprStatement;
import net.forthecrown.typescript.parse.ast.Expression;
import net.forthecrown.typescript.parse.ast.ForStatement;
import net.forthecrown.typescript.parse.ast.FunctionDeclaration;
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
import net.forthecrown.typescript.parse.ast.ParameterNode;
import net.forthecrown.typescript.parse.ast.ParenExpr;
import net.forthecrown.typescript.parse.ast.PropertyAccessExpr;
import net.forthecrown.typescript.parse.ast.ReturnStatement;
import net.forthecrown.typescript.parse.ast.Statement;
import net.forthecrown.typescript.parse.ast.StringLiteral;
import net.forthecrown.typescript.parse.ast.SwitchStatement;
import net.forthecrown.typescript.parse.ast.SwitchStatement.ClauseCase;
import net.forthecrown.typescript.parse.ast.SwitchStatement.DefaultCase;
import net.forthecrown.typescript.parse.ast.SwitchStatement.SwitchCase;
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

public class TypescriptParser {

  private final Lexer lexer;
  private final CompilerErrors errors;
  private final Types types;

  private final NameTable symbolTable;

  private final List<Token> tokenbuf = new ArrayList<>();

  private int cursor = 0;

  public TypescriptParser(Lexer lexer, CompilerErrors errors, Types types, NameTable table) {
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

  Statement statement() {
    return statement(false);
  }

  Statement statement(boolean topLevel) {
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
      case CLASS            -> classDeclaration();
      case FUNCTION         -> functionDeclaration();

      case IMPORT -> {
        if (!topLevel) {
          errors.error(peek.location(),
              "Import statements are only allowed in the top level scope"
          );
        }

        yield importStatement();
      }

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
    skipLineEnd();
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

    skipLineEnd();
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
    skipLineEnd();
    return expr;
  }

  Statement classDeclaration() {
    var start = expect(CLASS).location();
    var name = id();

    ClassDeclaration decl = new ClassDeclaration();
    decl.setName(name);
    decl.setStart(start);

    if (matches(LT)) {
      typeParameters(decl.getTypeParameters());
    }

    if (matches(EXTENDS)) {
      next();

      Identifier extendsName = id();
      decl.setSuperClass(extendsName);
    }

    expect(CURLY_START);

    while (!peek().is(CURLY_CLOSE)) {
      notEof("inside class");

      boolean isPrivate;

      if (matches(HASHTAG) || identifierMatches("private")) {
        next();
        isPrivate = true;
      } else {
        isPrivate = false;
      }

      Identifier id = id();
      ClassComponent component;
      Location l = peek().location();

      if (matches(PAREN_START)) {
        FuncComponent c = new FuncComponent();
        component = c;

        parameterList(c.getParameters());
        c.setReturnType(optionalType());

        Block body = blockStatement();
        c.setBody(body);
      } else {
        FieldComponent c = new FieldComponent();
        component = c;

        c.setType(optionalType());

        if (matches(ASSIGN)) {
          next();
          c.setValue(assignExpr());
        }
      }

      component.setStart(l);
      component.setName(id);
      component.setPrivateComponent(isPrivate);

      decl.getComponents().add(component);
    }

    expect(CURLY_CLOSE);

    ObjectType type = types.object(decl);
    decl.setType(type);

    return decl;
  }

  void parameterList(List<ParameterNode> params) {
    expect(PAREN_START);

    while (!matches(PAREN_CLOSE)) {
      notEof("inside function parameter list");

      ParameterNode param = parameter();
      params.add(param);

      expectEndOrComma(PAREN_CLOSE);
    }

    expect(PAREN_CLOSE);
  }

  ParameterNode parameter() {
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

    ParameterNode node = new ParameterNode();
    node.setStart(start);
    node.setVarArgs(varArgs);
    node.setName(id);
    node.setType(optionalType());

    if (matches(ASSIGN)) {
      next();
      Expression val = expr();
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
    if (matches(ID)) {
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
    } else if (matches(PAREN_START)) {
      next();
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
      expect(ARROW_FUNC);

      Type returnType = parseType();
      funcType.setReturnType(returnType);

      return types.addType(funcType);
    } else {
      errors.error(peek().location(), "Invalid type notation token");
      return null;
    }
  }

  Statement functionDeclaration() {
    Location start = expect(FUNCTION).location();

    FunctionDeclaration decl = new FunctionDeclaration();
    decl.setStart(start);

    Identifier name = id();
    decl.setName(name);

    if (matches(LT)) {
      typeParameters(decl.getTypeParameters());
    }

    parameterList(decl.getParameters());
    decl.setReturnType(optionalType());

    Block body = blockStatement();
    decl.setBody(body);

    return body;
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

    while (!matches(SEMICOLON) && matches(ID)) {
      notEof("inside variable declarations");

      Location loc = peek().location();
      Identifier id = id();

      SingleDeclaration decl = new SingleDeclaration();
      decl.setStart(loc);
      decl.setIdentifier(id);

      if (matches(ASSIGN)) {
        next();

        Expression expr = assignExpr();
        decl.setValue(expr);
      }

      node.getDeclarations().add(decl);

      expectEndOrComma(SEMICOLON);
    }

    skipLineEnd();
    return node;
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

    statement.setExpression(condition);

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
    stat.setExpression(expr);
    expect(PAREN_CLOSE);

    Statement body = statement();
    stat.setStatement(body);

    if (matches(ELSE)) {
      next();

      Statement elsePart = statement();
      stat.setStatement(elsePart);
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

    skipLineEnd();
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

    skipLineEnd();
    return statement;
  }

  Statement forStatement() {
    Location start = expect(FOR).location();
    expect(PAREN_START);

  }

  Statement importStatement() {
    Location start = expect(IMPORT).location();

    ImportStatement stat = new ImportStatement();
    stat.setStart(start);

    if (matches(STRING_LITERAL)) {
      StringLiteral lit = stringLiteral();
      stat.setModuleName(lit);
      skipLineEnd();
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

    skipLineEnd();
    return stat;
  }

  void skipLineEnd() {
    if (!matches(SEMICOLON)) {
      return;
    }

    next();
  }

  Expression primaryExpr() {
    return switch (peek().type()) {
      case THIS -> thisExpr();
      case ID -> id();

      // Literals
      case STRING_LITERAL -> stringLiteral();
      case NUMBER_LITERAL, HEX_LITERAL, OCTAL_LITERAL, BINARY_LITERAL -> numberLiteral();
      case SQUARE_START -> arrayLiteral();
      case CURLY_START -> objectLiteral();
      case NULL -> nullLiteral();
      case TRUE, FALSE -> booleanLiteral();
      case TEMPLATE_STRING -> templateLiteral();

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
    Token token = expect(NUMBER_LITERAL, HEX_LITERAL, OCTAL_LITERAL, BINARY_LITERAL);
    Location loc = token.location();
    double value;

    if (token.is(HEX_LITERAL)) {
      value = Long.parseLong(token.value(), 16);
    } else if (token.is(OCTAL_LITERAL)) {
      value = Long.parseLong(token.value(), 8);
    } else if (token.is(BINARY_LITERAL)) {
      value = Long.parseLong(token.value(), 2);
    } else {
      value = Double.parseDouble(token.value());
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
    return new NullLiteral(expect(NULL).location());
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
    Location start = expect(CURLY_START).location();

    ObjectLiteral literal = new ObjectLiteral();
    literal.setStart(start);

    while (!matches(CURLY_CLOSE)) {
      notEof("inside object literal");

      Expression key = switch (peek().type()) {
        case ID -> id();
        case STRING_LITERAL -> stringLiteral();
        case NUMBER_LITERAL, OCTAL_LITERAL, HEX_LITERAL, BINARY_LITERAL -> numberLiteral();
        default -> {
          errors.error(peek().location(), "Invalid property key token, must be an identifier, "
              + "quoted string, or number literal"
          );

          yield null;
        }
      };

      expect(
          "Expected property key-value separator ${expected}, found ${actual}",
          COLON
      );

      Expression value = assignExpr();

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

  Expression parenthesizedExpr() {
    Location start = expect(PAREN_START).location();
    ParenExpr expr = new ParenExpr();
    expr.setStart(start);

    Expression contained = expr();
    expr.setExpr(contained);

    expect(PAREN_CLOSE);

    return expr;
  }

  Expression templateLiteral() {
    Token token = expect(TEMPLATE_STRING);
  }

  Expression ternary() {

  }

  Expression assignExpr() {

  }

  private Expression _recursiveExpr(Supplier<Expression> previous,
                                    Operation operation,
                                    TokenType type
  ) {
    Expression expr = previous.get();
    Location start = expr.getStart();

    while (matches(type)) {
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

  Expression updateExpr() {

  }

  Expression lhsExpr() {

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
    Location start = next().location();

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
        case DOT, OPTIONAL, SQUARE_START -> {
          expr = propertyAccess(expr);
        }

        case PAREN_START -> {
          if (!allowCall) {
            break outer;
          }

          expr = callExpr(expr);
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

    if (start.is(SQUARE_START)) {
      Expression prop = expr();

      expr.setProperty(prop);
      expr.setOptional(false);

      expect(SQUARE_CLOSE);
    } else {
      boolean optional = start.is(OPTIONAL);
      expr.setOptional(optional);

      // TODO
      throw new IllegalStateException("TODO");
    }

    return expr;
  }

  Expression expr() {

  }
}