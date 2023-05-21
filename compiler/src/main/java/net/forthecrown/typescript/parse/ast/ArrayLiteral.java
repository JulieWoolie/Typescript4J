package net.forthecrown.typescript.parse.ast;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ArrayLiteral extends Expression implements Destructuring {

  private final List<Expression> values = new ArrayList<>();

  private boolean destructuring;

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitArrayLiteral(this, context);
  }
}