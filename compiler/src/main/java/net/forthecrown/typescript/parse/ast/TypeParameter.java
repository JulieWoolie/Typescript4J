package net.forthecrown.typescript.parse.ast;

import lombok.Getter;
import lombok.Setter;
import net.forthecrown.typescript.parse.type.Type;

@Getter
@Setter
public class TypeParameter extends Statement {

  private Identifier name;
  private Type superType;

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitTypeParameter(this, context);
  }
}