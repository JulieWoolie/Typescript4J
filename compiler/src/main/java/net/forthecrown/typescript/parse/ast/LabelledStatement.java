package net.forthecrown.typescript.parse.ast;

import lombok.Getter;
import lombok.Setter;

public class LabelledStatement extends Statement {

  private Identifier label;
  private Statement statement;

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitLabelled(this, context);
  }

  public Identifier getLabel() {
    return label;
  }

  public void setLabel(Identifier label) {
    this.label = label;
  }

  public Statement getStatement() {
    return statement;
  }

  public void setStatement(Statement statement) {
    this.statement = statement;
  }
}