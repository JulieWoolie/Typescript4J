package net.forthecrown.typescript.parse.ast;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ForStatement extends Statement {

  private Statement statement;

  private Statement  first;
  private Expression second;
  private Expression third;

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitFor(this, context);
  }
}