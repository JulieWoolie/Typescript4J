package net.forthecrown.typescript.parse.ast;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TaggedTemplateLiteral extends Expression {

  private Expression expr;
  private StringTemplateExpr template;

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitTaggedTemplate(this, context);
  }
}