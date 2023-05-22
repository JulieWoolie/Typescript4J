package net.forthecrown.typescript.parse.ast;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import net.forthecrown.typescript.parse.type.Type;

@Getter @Setter
public class FunctionSignature {

  private final List<ParameterNode> parameters = new ArrayList<>();
  private final List<TypeParameter> typeParameters = new ArrayList<>();

  private Type returnType;
}