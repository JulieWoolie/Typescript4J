package net.forthecrown.typescript.parse.ast;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
public class StringTemplateExpr extends Expression {

  private final List<TemplatePart> parts = new ArrayList<>();

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitTemplate(this, context);
  }

  public static abstract class TemplatePart extends Expression {

  }

  @Getter @Setter
  public static class LiteralPart extends TemplatePart {

    private String value;

    @Override
    public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
      return visitor.visitTemplateLiteral(this, context);
    }
  }

  @Getter @Setter
  public static class ExprPart extends TemplatePart {

    private Expression expr;

    @Override
    public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
      return visitor.visitTemplateExpr(this, context);
    }
  }
}