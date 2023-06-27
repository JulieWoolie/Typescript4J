package net.forthecrown.typescript.parse.ast;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IfStatement extends Statement {

  private Expression condition;
  private Statement body;

  private Statement elseStatement;

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitIf(this, context);
  }
}