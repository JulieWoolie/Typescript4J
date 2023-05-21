package net.forthecrown.typescript.parse.ast;

import lombok.Getter;
import lombok.Setter;
import net.forthecrown.typescript.parse.type.Type;

@Getter @Setter
public class ParameterNode extends Statement {

  private Identifier name;
  private Expression defaultValue;
  private Type type;

  private boolean varArgs;

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitFunctionParam(this, context);
  }
}