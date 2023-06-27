package net.forthecrown.typescript.parse.ast;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import net.forthecrown.typescript.parse.type.Type;

@Getter @Setter
public class FunctionSignature extends Statement {

  private final List<ParameterStatement> parameters = new ArrayList<>();
  private final List<TypeParameter> typeParameters = new ArrayList<>();

  private Type returnType;

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitSignature(this, context);
  }
}