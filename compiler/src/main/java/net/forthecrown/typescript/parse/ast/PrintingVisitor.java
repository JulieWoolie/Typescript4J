package net.forthecrown.typescript.parse.ast;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import net.forthecrown.typescript.parse.ast.ClassDeclaration.AccessLevel;
import net.forthecrown.typescript.parse.ast.ClassDeclaration.ClassComponent;
import net.forthecrown.typescript.parse.ast.ClassDeclaration.FieldComponent;
import net.forthecrown.typescript.parse.ast.ClassDeclaration.FuncComponent;
import net.forthecrown.typescript.parse.ast.ImportStatement.ImportedBinding;
import net.forthecrown.typescript.parse.ast.LexDeclarationStatement.SingleDeclaration;
import net.forthecrown.typescript.parse.ast.ObjectLiteral.ObjectProperty;
import net.forthecrown.typescript.parse.ast.PrintingVisitor.Printer;
import net.forthecrown.typescript.parse.ast.StringTemplateExpr.ExprPart;
import net.forthecrown.typescript.parse.ast.StringTemplateExpr.LiteralPart;
import net.forthecrown.typescript.parse.ast.SwitchStatement.ClauseCase;
import net.forthecrown.typescript.parse.ast.SwitchStatement.DefaultCase;
import net.forthecrown.typescript.parse.ast.TryStatement.Catch;
import net.forthecrown.typescript.parse.ast.UpdateExpr.UpdateOp;
import net.forthecrown.typescript.parse.type.Type;

public class PrintingVisitor implements NodeVisitor<Void, Printer> {

  private static final PrintingVisitor VISITOR = new PrintingVisitor();

  @RequiredArgsConstructor
  static class Printer {

    private final StringBuffer out;

    private int indent;

    private String indentString = "  ";

    boolean insideSwitch = false;

    Printer incIndent() {
      indent++;
      return this;
    }

    Printer decIndent() {
      indent--;
      return this;
    }

    Printer appendIndent() {
      if (indent < 1) {
        return this;
      }

      out.append(indentString.repeat(indent));
      return this;
    }

    Printer append(Object o) {
      out.append(String.valueOf(o));
      return this;
    }

    Printer append(char c) {
      out.append(c);
      return this;
    }

    Printer append(int c) {
      out.append(c);
      return this;
    }

    Printer append(boolean c) {
      out.append(c);
      return this;
    }

    Printer nlIndent() {
      return append("\n").appendIndent();
    }
  }

  public static void printTree(Node node, StringBuffer buf) {
    Printer printer = new Printer(buf);
    node.visit(VISITOR, printer);
  }

  void printTypeParams(List<TypeParameter> typeParameters, Printer out) {
    out.append("<");
    visitDelimitedList(typeParameters, ", ", out);
    out.append(">");
  }

  <T> void visitDelimitedList(List<? extends T> list, Object delimiter, Printer out) {
    var it = list.iterator();

    while (it.hasNext()) {
      var n = it.next();
      printObject(n, out);

      if (it.hasNext()) {
        out.append(delimiter);
      }
    }
  }

  void printObject(Object o, Printer out) {
    if (o instanceof Node node) {
      node.visit(this, out);
    } else if (o instanceof Type type) {
      out.append(type.getName());
    } else {
      out.append(Objects.toString(o));
    }
  }

  @Override
  public Void visitRoot(CompilationUnit unit, Printer out) {
    unit.getImports().forEach(importStatement -> {
      out.nlIndent();
      importStatement.visit(this, out);
    });

    unit.getExports().forEach(exportStatement -> {
      out.nlIndent();
      exportStatement.visit(this, out);
    });

    unit.getStatements().forEach(statement -> {
      out.nlIndent();
      statement.visit(this, out);
    });

    return null;
  }

  @Override
  public Void visitIf(IfStatement statement, Printer out) {
    out.append("if (");
    statement.getCondition().visit(this, out);
    out.append(")");
    statement.getBody().visit(this, out);
    return null;
  }

  @Override
  public Void visitFor(ForStatement statement, Printer out) {
    out.append("for (");
    statement.getFirst().visit(this, out);
    out.append("; ");
    statement.getSecond().visit(this, out);
    out.append("; ");
    statement.getThird().visit(this, out);
    out.append(") ");
    statement.getBody().visit(this, out);
    return null;
  }

  @Override
  public Void visitForIn(ForInOfStatement statement, Printer out) {
    out.append("for (");
    statement.getDeclaration().visit(this, out);

    if (statement.isOf()) {
      out.append(" of ");
    } else {
      out.append(" in ");
    }

    statement.getObject().visit(this, out);
    out.append(") ");
    statement.getBody().visit(this, out);

    return null;
  }

  @Override
  public Void visitDoWhile(DoWhileStatement statement, Printer out) {
    out.append("do ");
    statement.getBody().visit(this, out);
    out.append(" while (");
    statement.getCondition().visit(this, out);
    out.append(")");
    return null;
  }

  @Override
  public Void visitWhile(WhileStatement statement, Printer out) {
    out.append("while (");
    statement.getCondition().visit(this, out);
    out.append(") ");
    statement.getBody().visit(this, out);
    return null;
  }

  @Override
  public Void visitSwitch(SwitchStatement statement, Printer out) {
    out.append("switch (");
    statement.getExpression().visit(this, out);
    out.append(") {").incIndent();

    out.insideSwitch = true;
    for (var c: statement.getCases()) {
      out.appendIndent();
      c.visit(this, out);
    }
    out.insideSwitch = false;

    out.decIndent().nlIndent().append("}");
    return null;
  }

  @Override
  public Void visitSwitchCase(ClauseCase statement, Printer out) {
    out.append("switch ");
    statement.getExpression().visit(this, out);
    out.append(": ");
    statement.getStatement().visit(this, out);
    out.decIndent();

    return null;
  }

  @Override
  public Void visitDefaultCase(DefaultCase statement, Printer out) {
    out.append("default: ");
    statement.getStatement().visit(this, out);
    out.decIndent();

    return null;
  }

  @Override
  public Void visitTry(TryStatement statement, Printer out) {
    out.append("try ");
    statement.getBody().visit(this, out);

    for (var cat: statement.getCatches()) {
      cat.visit(this, out);
    }

    if (statement.getFinallyBody() != null) {
      out.append("finally ");
      statement.getFinallyBody().visit(this, out);
    }

    return null;
  }

  @Override
  public Void visitCatch(Catch statement, Printer out) {
    out.append("catch (");
    statement.getLabel().visit(this, out);
    out.append(") ");
    statement.getBody().visit(this, out);
    return null;
  }

  @Override
  public Void visitContinue(ContinueStatement statement, Printer out) {
    out.append("continue");

    if (statement.getLabel() != null) {
      out.append(" ");
      statement.getLabel().visit(this, out);
    }

    return null;
  }

  @Override
  public Void visitBreak(BreakStatement statement, Printer out) {
    out.append("break");

    if (statement.getLabel() != null) {
      out.append(" ");
      statement.getLabel().visit(this, out);
    }

    return null;
  }

  @Override
  public Void visitBlock(Block block, Printer out) {
    if (block.getStatements().isEmpty()) {
      return null;
    }

    boolean insideSwitch = out.insideSwitch;
    out.insideSwitch = false;

    if (!insideSwitch) {
      out.append("{");
    }

    out.incIndent();

    for (var stat: block.getStatements()) {
      out.nlIndent();
      stat.visit(this, out);
    }

    out.decIndent()
        .nlIndent();

    if (!insideSwitch) {
      out.append("}");
    }

    out.insideSwitch = insideSwitch;
    return null;
  }

  @Override
  public Void visitLexicalDeclaration(
      LexDeclarationStatement statement,
      Printer out
  ) {
    out.append(statement.getKind().name().toLowerCase()).append(" ");

    var it = statement.getDeclarations().iterator();

    while (it.hasNext()) {
      var decl = it.next();
      decl.visit(this, out);

      if (it.hasNext()) {
        out.append(", ");
      }
    }

    return null;
  }

  @Override
  public Void visitSingleLexDeclaration(SingleDeclaration statement, Printer out) {
    statement.getIdentifier().visit(this, out);

    if (statement.getType() != null) {
      out.append(": ").append(statement.getType().getName());
    }

    if (statement.getValue() != null) {
      out.append(" = ");
      statement.getValue().visit(this, out);
    }

    return null;
  }

  @Override
  public Void visitClassDeclaration(ClassDeclaration statement, Printer out) {
    switch (statement.getClassType()) {
      case REGULAR -> out.append("class");
      case ABSTRACT -> out.append("abstract class");
      case INTERFACE -> out.append("interface");
    }

    out.append(" ");

    statement.getName().visit(this, out);

    if (!statement.getTypeParameters().isEmpty()) {
      printTypeParams(statement.getTypeParameters(), out);
    }

    if (statement.getSuperClass() != null) {
      out.append(" extends ").append(statement.getSuperClass().getName());
    }

    if (!statement.getImplementations().isEmpty()) {
      out.append(" implements ");
      visitDelimitedList(statement.getImplementations(), ", ", out);
    }

    out.append("{").incIndent();

    for (var n : statement.getComponents()) {
      out.nlIndent();
      n.visit(this, out);
    }

    out.decIndent().nlIndent().append("}");
    return null;
  }

  @Override
  public Void visitClassField(FieldComponent statement, Printer out) {
    componentPrefix(statement, out);

    if (statement.getType() != null) {
      out.append(": ");
      out.append(statement.getType().getName());
    }

    if (statement.getValue() != null) {
      out.append(" = ");
      statement.getValue().visit(this, out);
    }

    return null;
  }

  @Override
  public Void visitClassMethod(FuncComponent statement, Printer out) {
    componentPrefix(statement, out);
    statement.getSignature().visit(this, out);

    if (statement.getBody() != null) {
      out.append(" ");
      statement.getBody().visit(this, out);
    }

    return null;
  }

  private void componentPrefix(ClassComponent component, Printer out) {
    if (component.getAccessLevel() != AccessLevel.PUBLIC) {
      out.append(component.getAccessLevel().name().toLowerCase());
      out.append(" ");
    }

    component.getName().visit(this, out);
  }

  @Override
  public Void visitFunction(FunctionDeclaration statement, Printer out) {
    out.append("function ");
    statement.getName().visit(this, out);
    statement.getExpr().visit(this, out);
    return null;
  }

  @Override
  public Void visitFunctionParam(ParameterStatement param, Printer out) {
    if (param.isVarArgs()) {
      out.append("...");
    }

    param.getName().visit(this, out);
    if (param.getType() != null) {
      out.append(": ");
      out.append(param.getType().getName());
    }

    if (param.getDefaultValue() != null) {
      out.append(" = ");
      param.getDefaultValue().visit(this, out);
    }

    return null;
  }

  @Override
  public Void visitImport(ImportStatement statement, Printer out) {
    out.append("import ");
    boolean needsFrom = false;

    if (statement.isStarImport()) {
      out.append("* ");
      needsFrom = true;
    } else if (!statement.getImports().isEmpty()) {
      out.append("{");
      visitDelimitedList(statement.getImports(), ", ", out);
      out.append("} ");
      needsFrom = true;
    }

    if (statement.getAlias() != null) {
      out.append("as ");
      statement.getAlias().visit(this, out);
      needsFrom = true;
    }

    if (needsFrom) {
      out.append("from ");
    }

    statement.getModuleName().visit(this, out);
    return null;
  }

  @Override
  public Void visitBindingImport(ImportedBinding statement, Printer out) {
    statement.getBinding().visit(this, out);

    if (statement.getAlias() != null) {
      out.append(" as ");
      statement.getAlias().visit(this, out);
    }

    return null;
  }

  @Override
  public Void visitLabelled(LabelledStatement statement, Printer out) {
    statement.getLabel().visit(this, out);
    out.append(": ");
    statement.getStatement().visit(this, out);
    return null;
  }

  @Override
  public Void visitExprStatement(ExprStatement statement, Printer out) {
    return statement.getExpr().visit(this, out);
  }

  @Override
  public Void visitEmptyStatement(EmptyStatement statement, Printer out) {
    out.append(";");
    return null;
  }

  @Override
  public Void visitReturn(ReturnStatement statement, Printer out) {
    out.append("return");

    if (statement.getReturnValue() != null) {
      out.append(" ");
      statement.getReturnValue().visit(this, out);
    }

    return null;
  }

  @Override
  public Void visitThrow(ThrowStatement statement, Printer out) {
    out.append("throw ");
    statement.getThrowValue().visit(this, out);
    return null;
  }

  @Override
  public Void visitDebugger(DebuggerStatement statement, Printer out) {
    out.append("debugger");

    if (statement.getMessage() != null) {
      out.append(" ");
      statement.getMessage().visit(this, out);
    }

    out.append(";");
    return null;
  }

  @Override
  public Void visitTypeParameter(TypeParameter statement, Printer out) {
    statement.getName().visit(this, out);

    if (statement.getSuperType() != null) {
      out.append(" extends ");
      out.append(statement.getSuperType().getName());
    }

    return null;
  }

  @Override
  public Void visitSignature(FunctionSignature signature, Printer out) {
    if (!signature.getTypeParameters().isEmpty()) {
      printTypeParams(signature.getTypeParameters(), out);
    }

    out.append("(");
    visitDelimitedList(signature.getParameters(), ", ", out);
    out.append(")");

    if (signature.getReturnType() != null) {
      out.append(": ");
      out.append(signature.getReturnType().getName());
    }

    return null;
  }

  @Override
  public Void visitExport(ExportStatement statement, Printer out) {
    out.append("export ");
    statement.getExported().visit(this, out);
    return null;
  }

  @Override
  public Void visitIdentifier(Identifier expr, Printer out) {
    out.append(expr.getName());
    return null;
  }

  @Override
  public Void visitArrayLiteral(ArrayLiteral expr, Printer out) {
    out.append("[");
    visitDelimitedList(expr.getValues(), ", ", out);
    out.append("]");
    return null;
  }

  @Override
  public Void visitStringLiteral(StringLiteral expr, Printer out) {
    out.append('"')
        .append(expr.getValue())
        .append('"');

    return null;
  }

  @Override
  public Void visitNullLiteral(NullLiteral expr, Printer out) {
    out.append("null");
    return null;
  }

  @Override
  public Void visitNumberLiteral(NumberLiteral expr, Printer out) {
    out.append(expr.getValue());
    return null;
  }

  @Override
  public Void visitBooleanLiteral(BooleanLiteral expr, Printer out) {
    out.append(expr.value());
    return null;
  }

  @Override
  public Void visitObjectLiteral(ObjectLiteral expr, Printer out) {
    boolean isMultiLine = !expr.isDestructuring();
    out.append("{");

    if (isMultiLine) {
      out.incIndent();
    }

    var it = expr.getProperties().iterator();
    while (it.hasNext()) {
      out.nlIndent();

      var prop = it.next();
      prop.visit(this, out);

      if (it.hasNext()) {
        out.append(",");
      }
    }

    if (isMultiLine) {
      out.decIndent().nlIndent();
    }

    out.append("}");
    return null;
  }

  @Override
  public Void visitObjectProperty(ObjectProperty expr, Printer out) {
    expr.getKey().visit(this, out);

    if (expr.getValue() == null) {
      return null;
    }

    if (!(expr.getValue() instanceof FunctionExpr)) {
      out.append(": ");
    }

    expr.getValue().visit(this, out);
    return null;
  }

  @Override
  public Void visitThisExpr(ThisExpr expr, Printer out) {
    out.append("this");
    return null;
  }

  @Override
  public Void visitEmptyExpr(EmptyExpr expr, Printer out) {
    return null;
  }

  @Override
  public Void visitBinaryExpr(BinaryExpr expr, Printer out) {
    expr.getLeft().visit(this, out);
    out.append(" ").append(expr.getOperation().toString()).append(" ");
    expr.getRight().visit(this, out);
    return null;
  }

  @Override
  public Void visitUnary(UnaryExpr expr, Printer out) {
    switch (expr.getOp()) {
      case POSITIVE -> out.append("+");
      case NEGATIVE -> out.append("-");
      case VOID -> out.append("void ");
      case DELETE -> out.append("delete ");
      case TYPEOF -> out.append("typeof ");
      case BIT_NOT -> out.append("~");
      case LOG_NOT -> out.append("!");
    }

    expr.getExpr().visit(this, out);
    return null;
  }

  @Override
  public Void visitUpdate(UpdateExpr expr, Printer out) {
    UpdateOp op = expr.getOperation();

    if (op == UpdateOp.PRE_INC) {
      out.append("++");
    } else if (op == UpdateOp.PRE_DEC) {
      out.append("--");
    }

    expr.getExpr().visit(this, out);

    if (op == UpdateOp.POST_INC) {
      out.append("++");
    } else if (op == UpdateOp.POST_DEC) {
      out.append("--");
    }

    return null;
  }

  @Override
  public Void visitNew(NewExpr expr, Printer out) {
    out.append("new ");
    expr.getTarget().visit(this, out);

    if (!expr.getArguments().isEmpty()) {
      argumentList(expr.getArguments(), out);
    }

    if (expr.getInitializer() != null) {
      expr.getInitializer().visit(this, out);
    }

    return null;
  }

  @Override
  public Void visitPropertyAccess(PropertyAccessExpr expr, Printer out) {
    expr.getTarget().visit(this, out);

    if (expr.isOptional()) {
      out.append('?');
    }

    out.append('.');

    if (expr.isElementAccess()) {
      out.append("[");
    }

    expr.getProperty().visit(this, out);

    if (expr.isElementAccess()) {
      out.append("]");
    }

    return null;
  }

  @Override
  public Void visitFunctionCall(CallExpr expr, Printer out) {
    expr.getTarget().visit(this, out);
    argumentList(expr.getArguments(), out);
    return null;
  }

  void argumentList(List<Expression> arguments, Printer out) {
    out.append("(");
    var it = arguments.iterator();

    while (it.hasNext()) {
      var n = it.next();
      n.visit(this, out);

      if (it.hasNext()) {
        out.append(", ");
      }
    }

    out.append(")");
  }

  @Override
  public Void visitErrorExpr(ErrorExpr expr, Printer out) {
    out.append("/*").append(expr.getMessage()).append("*/");
    return null;
  }

  @Override
  public Void visitConditional(ConditionalExpr expr, Printer out) {
    expr.getCondition().visit(this, out);
    out.append(" ? ");
    expr.getOnTrue().visit(this, out);
    out.append(" : ");
    expr.getOnFalse().visit(this, out);

    return null;
  }

  @Override
  public Void visitFunction(FunctionExpr expr, Printer out) {
    expr.getSignature().visit(this, out);

    if (expr.isArrowFunction()) {
      out.append(" => ");
    }

    return expr.getBody().visit(this, out);
  }

  @Override
  public Void visitParenthisized(ParenExpr expr, Printer out) {
    out.append("(");
    expr.getExpr().visit(this, out);
    out.append(")");
    return null;
  }

  @Override
  public Void visitTemplateExpr(ExprPart expr, Printer out) {
    out.append("${");
    expr.getExpr().visit(this, out);
    out.append("}");

    return null;
  }

  @Override
  public Void visitTemplateLiteral(LiteralPart expr, Printer out) {
    out.append(expr.getValue());
    return null;
  }

  @Override
  public Void visitTemplate(StringTemplateExpr expr, Printer out) {
    out.append("`");
    expr.getParts().forEach(templatePart -> templatePart.visit(this, out));
    out.append("`");
    return null;
  }

  @Override
  public Void visitTaggedTemplate(TaggedTemplateLiteral expr, Printer out) {
    expr.getExpr().visit(this, out);
    expr.getTemplate().visit(this, out);

    return null;
  }
}