package net.forthecrown.typescript.parse.ast;

import net.forthecrown.typescript.parse.ast.ClassDeclaration.FieldComponent;
import net.forthecrown.typescript.parse.ast.ClassDeclaration.FuncComponent;
import net.forthecrown.typescript.parse.ast.ImportStatement.ImportedBinding;
import net.forthecrown.typescript.parse.ast.LexDeclarationStatement.SingleDeclaration;
import net.forthecrown.typescript.parse.ast.ObjectLiteral.ObjectProperty;
import net.forthecrown.typescript.parse.ast.SwitchStatement.ClauseCase;
import net.forthecrown.typescript.parse.ast.SwitchStatement.DefaultCase;
import net.forthecrown.typescript.parse.ast.TryStatement.Catch;

public interface NodeVisitor<R, C> {

  R visitRoot(CompilationUnit unit, C c);

  /* ----------------------------- STATEMENTS ------------------------------ */

  R visitIf(IfStatement statement, C c);

  R visitFor(ForStatement statement, C c);

  R visitForIn(ForInOfStatement statement, C c);

  R visitDoWhile(DoWhileStatement statement, C c);

  R visitWhile(WhileStatement statement, C c);

  R visitSwitch(SwitchStatement statement, C c);

  R visitSwitchCase(ClauseCase statement, C c);

  R visitDefaultCase(DefaultCase statement, C c);

  R visitTry(TryStatement statement, C c);

  R visitCatch(Catch statement, C c);

  R visitContinue(ContinueStatement statement, C c);

  R visitBreak(BreakStatement statement, C c);

  R visitBlock(Block block, C c);

  R visitLexicalDeclaration(LexDeclarationStatement statement, C c);

  R visitSingleLexDeclaration(SingleDeclaration statement, C c);

  R visitClassDeclaration(ClassDeclaration statement, C c);

  R visitClassField(FieldComponent statement, C c);

  R visitClassMethod(FuncComponent statement, C c);

  R visitFunction(FunctionDeclaration statement, C c);

  R visitFunctionParam(ParameterNode param, C c);

  R visitImport(ImportStatement statement, C c);

  R visitBindingImport(ImportedBinding statement, C c);

  R visitLabelled(LabelledStatement statement, C c);

  R visitExprStatement(ExprStatement statement, C c);

  R visitEmptyStatement(EmptyStatement statement, C c);

  R visitReturn(ReturnStatement statement, C c);

  R visitThrow(ThrowStatement statement, C c);

  R visitDebugger(DebuggerStatement statement, C c);

  R visitTypeParameter(TypeParameter statement, C c);

  /* ----------------------------- EXPRESSIONS ------------------------------ */

  R visitIdentifier(Identifier expr, C c);

  R visitArrayLiteral(ArrayLiteral expr, C c);

  R visitStringLiteral(StringLiteral expr, C c);

  R visitNullLiteral(NullLiteral expr, C c);

  R visitNumberLiteral(NumberLiteral expr, C c);

  R visitBooleanLiteral(BooleanLiteral expr, C c);

  R visitObjectLiteral(ObjectLiteral expr, C c);

  R visitObjectProperty(ObjectProperty expr, C c);

  R visitThisExpr(ThisExpr expr, C c);

  R visitEmptyExpr(EmptyExpr expr, C c);

  R visitBinaryExpr(BinaryExpr expr, C c);

  R visitUnary(UnaryExpr expr, C c);

  R visitUpdate(UpdateExpr expr, C c);

  R visitNew(NewExpr expr, C c);

  R visitPropertyAccess(PropertyAccessExpr expr, C c);

  R visitFunctionCall(CallExpr expr, C c);

  R visitErrorExpr(ErrorExpr expr, C c);

  R visitConditional(ConditionalExpr expr, C c);

  R visitArrowFunction(ArrowFunction expr, C c);

  R visitParenthisized(ParenExpr expr, C c);
}