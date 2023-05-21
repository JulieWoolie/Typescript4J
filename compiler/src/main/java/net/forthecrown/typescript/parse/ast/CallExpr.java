package net.forthecrown.typescript.parse.ast;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CallExpr extends Expression {

  private Expression target;

  private final List<Expression> arguments = new ArrayList<>();

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitFunctionCall(this, context);
  }
}