package net.forthecrown.typescript.parse.ast;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ObjectLiteral extends Expression implements Destructuring {

  private final List<ObjectProperty> properties = new ArrayList();

  private boolean destructuring;

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitObjectLiteral(this, context);
  }

  @Getter @Setter
  public static class ObjectProperty extends Expression {

    private Expression key;
    private Expression value;

    @Override
    public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
      return visitor.visitObjectProperty(this, context);
    }
  }
}