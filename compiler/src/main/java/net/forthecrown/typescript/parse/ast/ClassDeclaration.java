package net.forthecrown.typescript.parse.ast;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import net.forthecrown.typescript.parse.type.Type;

@Getter @Setter
public class ClassDeclaration extends Statement {

  private boolean interfaceDeclaration;

  private Identifier name;
  private Identifier superClass;

  private final List<TypeParameter> typeParameters = new ArrayList<>();

  private final List<ClassComponent> components = new ArrayList<>();

  private Type type;

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitClassDeclaration(this, context);
  }

  @Getter @Setter
  public static abstract class ClassComponent extends Statement {

    private Identifier name;
    private boolean privateComponent;
  }

  @Getter @Setter
  public static class FuncComponent extends ClassComponent {

    private FunctionSignature signature;
    private Block body;

    @Override
    public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
      return visitor.visitClassMethod(this, context);
    }
  }

  @Getter @Setter
  public static class FieldComponent extends ClassComponent {

    private Expression value;
    private Type type;

    @Override
    public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
      return visitor.visitClassField(this, context);
    }
  }
}