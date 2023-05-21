package net.forthecrown.typescript.parse.ast;

/**
 * Debugger statement which deviates from the ECMA Script specification a bit. FTC's debugger
 * statements also allow for a string literal message
 */
public class DebuggerStatement extends Statement {

  private StringLiteral message;

  public StringLiteral getMessage() {
    return message;
  }

  public void setMessage(StringLiteral message) {
    this.message = message;
  }

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitDebugger(this, context);
  }
}