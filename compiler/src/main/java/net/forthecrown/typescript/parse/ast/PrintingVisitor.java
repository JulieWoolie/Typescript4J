package net.forthecrown.typescript.parse.ast;

import java.math.BigInteger;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.forthecrown.typescript.parse.ast.ClassDeclaration.ClassComponent;
import net.forthecrown.typescript.parse.ast.ClassDeclaration.FieldComponent;
import net.forthecrown.typescript.parse.ast.ClassDeclaration.FuncComponent;
import net.forthecrown.typescript.parse.ast.ImportStatement.ImportedBinding;
import net.forthecrown.typescript.parse.ast.LexDeclarationStatement.SingleDeclaration;
import net.forthecrown.typescript.parse.ast.ObjectLiteral.ObjectProperty;
import net.forthecrown.typescript.parse.ast.SwitchStatement.ClauseCase;
import net.forthecrown.typescript.parse.ast.SwitchStatement.DefaultCase;
import net.forthecrown.typescript.parse.ast.SwitchStatement.SwitchCase;
import net.forthecrown.typescript.parse.ast.TryStatement.Catch;
import net.forthecrown.typescript.parse.ast.UpdateExpr.UpdateOp;
import net.forthecrown.typescript.parse.type.Type;

@RequiredArgsConstructor
public class PrintingVisitor implements NodeVisitor<Void, Void> {

  private int indent = 0;
  private final StringBuffer out;

  private void indent() {
    out.append("  ".repeat(indent));
  }

  private void nlIndent() {
    out.append("\n");
    indent();
  }

  private void incIndent() {
    indent++;
  }

  private void decIndent() {
    indent--;
  }

  private void optionalType(Type type) {
    if (type == null) {
      return;
    }

    out.append(": ").append(type.getName());
  }

  @Override
  public Void visitRoot(CompilationUnit unit, Void unused) {
    unit.getStatements().forEach(statement -> {
      statement.visit(this, unused);
      nlIndent();
    });

    return null;
  }

  @Override
  public Void visitIf(IfStatement statement, Void unused) {
    out.append("if (");
    statement.getExpression().visit(this, unused);
    out.append(")");

    statement.getStatement().visit(this, unused);

    if (statement.getElseStatement() != null) {
      statement.getElseStatement().visit(this, unused);
    }

    return null;
  }

  @Override
  public Void visitFor(ForStatement statement, Void unused) {
    out.append("for (");
    statement.getFirst().visit(this, unused);
    out.append("; ");
    statement.getSecond().visit(this, unused);
    out.append("; ");
    statement.getThird().visit(this, unused);
    out.append(")");

    statement.getStatement().visit(this, unused);
    return null;
  }

  @Override
  public Void visitForIn(ForInOfStatement statement, Void unused) {
    out.append("for (");
    statement.getDeclaration().visit(this, unused);
    out.append(" in ");
    statement.getObject().visit(this, unused);
    out.append(")");

    statement.getBody().visit(this, unused);
    return null;
  }

  @Override
  public Void visitDoWhile(DoWhileStatement statement, Void unused) {
    out.append("do ");
    statement.getBody().visit(this, unused);
    out.append(" while (");
    statement.getExpression().visit(this, unused);
    out.append(")");

    return null;
  }

  @Override
  public Void visitWhile(WhileStatement statement, Void unused) {
    out.append("while (");
    statement.getCondition().visit(this, unused);
    out.append(")");
    statement.getBody().visit(this, unused);

    return null;
  }

  @Override
  public Void visitSwitch(SwitchStatement statement, Void unused) {
    out.append("switch (");
    statement.getExpression().visit(this, unused);
    out.append(") {");
    incIndent();

    for (var c: statement.getCases()) {
      nlIndent();
      c.visit(this, unused);
    }

    decIndent();
    nlIndent();
    out.append("}");

    return null;
  }

  @Override
  public Void visitSwitchCase(ClauseCase statement, Void unused) {
    out.append("case ");
    statement.getExpression().visit(this, unused);
    defCase(statement);
    return null;
  }

  @Override
  public Void visitDefaultCase(DefaultCase statement, Void unused) {
    out.append("default");
    defCase(statement);
    return null;
  }

  private void defCase(SwitchCase switchCase) {
    out.append(": ");

    incIndent();
    nlIndent();

    switchCase.getStatement().visit(this, null);

    decIndent();
    nlIndent();

  }

  @Override
  public Void visitTry(TryStatement statement, Void unused) {
    out.append("try ");
    statement.getBody().visit(this, unused);

    statement.getCatches().forEach(aCatch -> {
      aCatch.visit(this, unused);
    });

    if (statement.getFinallyBody() != null) {
      out.append("finally ");
      statement.getFinallyBody().visit(this, unused);
    }

    return null;
  }

  @Override
  public Void visitCatch(Catch statement, Void unused) {
    out.append("catch (");
    statement.getLabel().visit(this, unused);
    optionalType(statement.getErrorType());

    out.append(")");
    statement.getBody().visit(this, unused);

    return null;
  }

  @Override
  public Void visitContinue(ContinueStatement statement, Void unused) {
    out.append("continue");

    if (statement.getLabel() != null) {
      out.append(' ');
      statement.getLabel().visit(this, unused);
    }

    out.append(";");
    return null;
  }

  @Override
  public Void visitBreak(BreakStatement statement, Void unused) {
    out.append("break");

    if (statement.getLabel() != null) {
      out.append(' ');
      statement.getLabel().visit(this, unused);
    }

    out.append(";");
    return null;
  }

  @Override
  public Void visitBlock(Block block, Void unused) {
    out.append("{");

    if (block.getStatements().isEmpty()) {
      out.append("}");
    }

    incIndent();

    block.getStatements().forEach(statement -> {
      nlIndent();
      statement.visit(this, unused);
    });

    decIndent();
    nlIndent();
    out.append("}");

    return null;
  }

  @Override
  public Void visitLexicalDeclaration(LexDeclarationStatement statement, Void unused) {
    String type = statement.getKind().name().toLowerCase();
    out.append(type);
    out.append(" ");

    var it = statement.getDeclarations().iterator();

    while (it.hasNext()) {
      var n = it.next();

      n.visit(this, unused);

      if (it.hasNext()) {
        out.append(", ");
      }
    }

    return null;
  }

  @Override
  public Void visitSingleLexDeclaration(SingleDeclaration statement, Void unused) {
    statement.getIdentifier().visit(this, unused);
    optionalType(statement.getType());

    if (statement.getValue() != null) {
      out.append(" = ");
      statement.getValue().visit(this, unused);
    }

    return null;
  }

  @Override
  public Void visitClassDeclaration(ClassDeclaration statement, Void unused) {
    out.append("class ");
    statement.getName().visit(this, unused);

    if (!statement.getTypeParameters().isEmpty()) {
      typeParams(statement.getTypeParameters());
    }

    if (statement.getSuperClass() != null) {
      out.append(" extends ");
      statement.getSuperClass().visit(this, unused);
    }

    out.append("{");

    if (statement.getComponents().isEmpty()) {
      out.append("}");
      return null;
    }

    incIndent();

    statement.getComponents().forEach(classComponent -> {
      nlIndent();
      nlIndent();
      classComponent.visit(this, unused);
    });

    decIndent();
    nlIndent();
    out.append("}");

    return null;
  }

  @Override
  public Void visitClassField(FieldComponent statement, Void unused) {
    visitComponent(statement);
    optionalType(statement.getType());

    if (statement.getValue() != null) {
      statement.getValue().visit(this, unused);
    }

    out.append(";");
    return null;
  }

  private void visitComponent(ClassComponent c) {
    if (c.isPrivateComponent()) {
      out.append("private ");
    }

    c.getName().visit(this, null);
  }

  @Override
  public Void visitClassMethod(FuncComponent statement, Void unused) {
    if (statement.isPrivateComponent()) {
      out.append("private ");
    }

    if (!statement.getSignature().getTypeParameters().isEmpty()) {
      typeParams(statement.getSignature().getTypeParameters());
    }

    statement.getName().visit(this, unused);
    visitSignature(false, statement.getSignature());

    statement.getBody().visit(this, unused);

    return null;
  }

  private void parameterList(List<ParameterNode> params) {
    out.append("(");
    var it = params.iterator();

    while (it.hasNext()) {
      var n = it.next();

      n.visit(this, null);

      if (it.hasNext()) {
        out.append(", ");
      }
    }

    out.append(")");
  }

  private void typeParams(List<TypeParameter> params) {
    out.append("<");

    var it = params.iterator();

    while (it.hasNext()) {
      var n = it.next();

      n.visit(this, null);

      if (it.hasNext()) {
        out.append(", ");
      }
    }

    out.append(">");
  }

  private void visitSignature(boolean writeTypeParams, FunctionSignature signature) {
    if (writeTypeParams && !signature.getTypeParameters().isEmpty()) {
      typeParams(signature.getTypeParameters());
    }

    parameterList(signature.getParameters());
    optionalType(signature.getReturnType());
    out.append(" ");
  }

  @Override
  public Void visitFunction(FunctionDeclaration statement, Void unused) {
    out.append("function ");

    if (!statement.getFunction().getTypeParameters().isEmpty()) {
      typeParams(statement.getFunction().getTypeParameters());
    }

    statement.getName().visit(this, unused);
    visitSignature(false, statement.getFunction());

    statement.getBody().visit(this, unused);
    return null;
  }

  @Override
  public Void visitFunctionParam(ParameterNode param, Void unused) {
    param.getName().visit(this, unused);
    optionalType(param.getType());

    if (param.getDefaultValue() != null) {
      out.append(" = ");
      param.getDefaultValue().visit(this, unused);
    }

    return null;
  }

  @Override
  public Void visitImport(ImportStatement statement, Void unused) {
    out.append("import ");
    boolean simpleImport = true;

    if (statement.isStarImport()) {
      out.append("* ");
      simpleImport = false;
    } else if (statement.getImports().size() == 1) {
      statement.getImports().get(0).visit(this, unused);
      simpleImport = false;
    } else if (statement.getImports().isEmpty()) {
      simpleImport = true;
    } else {
      out.append("{ ");

      var it = statement.getImports().iterator();

      while (it.hasNext()) {
        var n = it.next();
        n.visit(this, unused);

        if (it.hasNext()) {
          out.append(", ");
        }
      }

      out.append(" }");
      simpleImport = false;
    }

    if (statement.getAlias() != null) {
      out.append(" as ");
      statement.getAlias().visit(this, unused);
      simpleImport = false;
    }

    if (!simpleImport) {
      out.append(" from ");
    }

    statement.getModuleName().visit(this, unused);
    out.append(";");

    return null;
  }

  @Override
  public Void visitBindingImport(ImportedBinding statement, Void unused) {
    statement.getBinding().visit(this, unused);

    if (statement.getAlias() != null) {
      out.append(" as ");
      statement.getAlias().visit(this, unused);
    }

    return null;
  }

  @Override
  public Void visitLabelled(LabelledStatement statement, Void unused) {
    statement.getLabel().visit(this, unused);
    out.append(": ");
    statement.getStatement().visit(this, unused);

    return null;
  }

  @Override
  public Void visitExprStatement(ExprStatement statement, Void unused) {
    return statement.getExpr().visit(this, unused);
  }

  @Override
  public Void visitEmptyStatement(EmptyStatement statement, Void unused) {
    out.append("/* Empty */ ;");
    return null;
  }

  @Override
  public Void visitReturn(ReturnStatement statement, Void unused) {
    out.append("return");

    if (statement.getReturnValue() != null) {
      out.append(" ");
      statement.getReturnValue().visit(this, unused);
    }

    out.append(";");
    return null;
  }

  @Override
  public Void visitThrow(ThrowStatement statement, Void unused) {
    out.append("throw ");
    statement.getThrowValue().visit(this, unused);
    return null;
  }

  @Override
  public Void visitDebugger(DebuggerStatement statement, Void unused) {
    out.append("debugger");

    if (statement.getMessage() != null) {
      out.append(" ");
      statement.getMessage().visit(this, unused);
    }

    out.append(";");
    return null;
  }

  @Override
  public Void visitTypeParameter(TypeParameter statement, Void unused) {
    statement.getName().visit(this, unused);

    if (statement.getSuperType() != null) {
      out.append(" extends ");
      out.append(statement.getSuperType().getName());
    }

    return null;
  }

  @Override
  public Void visitIdentifier(Identifier expr, Void unused) {
    out.append(expr.getName());
    return null;
  }

  @Override
  public Void visitArrayLiteral(ArrayLiteral expr, Void unused) {
    out.append("[");

    if (expr.getValues().isEmpty()) {
      out.append("]");
      return null;
    }

    incIndent();

    var it = expr.getValues().iterator();

    while (it.hasNext()) {
      nlIndent();
      var n = it.next();

      if (it.hasNext()) {
        out.append(",");
      }
    }

    decIndent();
    nlIndent();
    out.append("]");

    return null;
  }

  @Override
  public Void visitStringLiteral(StringLiteral expr, Void unused) {
    out.append('"');
    out.append(expr.getValue());
    out.append('"');
    return null;
  }

  @Override
  public Void visitNullLiteral(NullLiteral expr, Void unused) {
    out.append("null");
    return null;
  }

  @Override
  public Void visitNumberLiteral(NumberLiteral expr, Void unused) {
    Number val = expr.getValue();
    out.append(val);

    if (val instanceof BigInteger) {
      out.append("n");
    }

    return null;
  }

  @Override
  public Void visitBooleanLiteral(BooleanLiteral expr, Void unused) {
    out.append(expr.value());
    return null;
  }

  @Override
  public Void visitObjectLiteral(ObjectLiteral expr, Void unused) {
    out.append("{");

    if (expr.getProperties().isEmpty()) {
      out.append("}");
      return null;
    }

    incIndent();

    var it = expr.getProperties().iterator();

    while (it.hasNext()) {
      var n = it.next();

      nlIndent();
      n.visit(this, unused);

      if (it.hasNext()) {
        out.append(",");
      }
    }

    decIndent();
    nlIndent();
    out.append("}");

    return null;
  }

  @Override
  public Void visitObjectProperty(ObjectProperty expr, Void unused) {
    expr.getKey().visit(this, unused);

    if (expr.getValue() != null) {
      out.append(": ");
      expr.getValue().visit(this, unused);
    }

    return null;
  }

  @Override
  public Void visitThisExpr(ThisExpr expr, Void unused) {
    out.append("this");
    return null;
  }

  @Override
  public Void visitEmptyExpr(EmptyExpr expr, Void unused) {
    return null;
  }

  @Override
  public Void visitBinaryExpr(BinaryExpr expr, Void unused) {
    expr.getLeft().visit(this, unused);
    out.append(' ');
    out.append(expr.getOperation());
    out.append(' ');
    expr.getRight().visit(this, unused);

    return null;
  }

  @Override
  public Void visitUnary(UnaryExpr expr, Void unused) {
    String strVal = switch (expr.getOp()) {
      case NEGATIVE -> "-";
      case POSITIVE -> "+";
      case BIT_NOT  -> "~";
      case LOG_NOT  -> "!";
      default -> expr.getOp().name().toLowerCase();
    };

    out.append(strVal);
    out.append(' ');
    expr.getExpr().visit(this, unused);

    return null;
  }

  @Override
  public Void visitUpdate(UpdateExpr expr, Void unused) {
    UpdateOp op = expr.getOperation();

    if (op == UpdateOp.PRE_INC) {
      out.append("++");
    } else if (op == UpdateOp.PRE_DEC) {
      out.append("--");
    }

    expr.getExpr().visit(this, unused);

    if (op == UpdateOp.POST_INC) {
      out.append("++");
    } else {
      out.append("--");
    }

    return null;
  }

  @Override
  public Void visitNew(NewExpr expr, Void unused) {
    out.append("new ");
    expr.getTarget().visit(this, unused);

    if (!expr.getArguments().isEmpty()) {
      argumentList(expr.getArguments());
    }

    if (expr.getInitializer() != null) {
      out.append(" ");
      expr.getInitializer().visit(this, unused);
    }

    return null;
  }

  @Override
  public Void visitPropertyAccess(PropertyAccessExpr expr, Void unused) {
    expr.getTarget().visit(this, unused);

    if (expr.isOptional()) {
      out.append("?.");
    } else if (expr.isElementAccess()) {
      out.append("[");
    } else {
      out.append(".");
    }

    expr.getProperty().visit(this, unused);

    if (expr.isElementAccess()) {
      out.append("]");
    }

    return null;
  }

  @Override
  public Void visitFunctionCall(CallExpr expr, Void unused) {
    expr.getTarget().visit(this, unused);
    argumentList(expr.getArguments());
    return null;
  }

  private void argumentList(List<Expression> args) {
    out.append("(");

    var it = args.iterator();

    while (it.hasNext()) {
      var n = it.next();
      n.visit(this, null);

      if (it.hasNext()) {
        out.append(", ");
      }
    }

    out.append(")");
  }

  @Override
  public Void visitErrorExpr(ErrorExpr expr, Void unused) {
    out.append("/* ERROR: ");
    out.append('"');
    out.append(expr.getMessage());
    out.append('"');
    out.append("*/");

    return null;
  }

  @Override
  public Void visitConditional(ConditionalExpr expr, Void unused) {
    expr.getCondition().visit(this, unused);

    out.append(" ? ");
    expr.getOnTrue().visit(this, unused);
    out.append(" : ");
    expr.getOnFalse().visit(this, unused);

    return null;
  }

  @Override
  public Void visitArrowFunction(ArrowFunction expr, Void unused) {
    visitSignature(true, expr.getNode());
    out.append(" => ");
    expr.getBody().visit(this, unused);

    return null;
  }

  @Override
  public Void visitParenthisized(ParenExpr expr, Void unused) {
    out.append("(");
    expr.getExpr().visit(this, unused);
    out.append(")");
    return null;
  }
}