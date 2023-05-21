package net.forthecrown.typescript.parse.ast;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SwitchStatement extends Statement {

  private Expression expression;

  private final List<SwitchCase> cases = new ArrayList<>();

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitSwitch(this, context);
  }

  @Getter @Setter
  public abstract static class SwitchCase extends Statement {
    private Statement statement;
  }

  public static class DefaultCase extends SwitchCase {

    @Override
    public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
      return visitor.visitDefaultCase(this, context);
    }
  }

  @Getter @Setter
  public static class ClauseCase extends SwitchCase {
    private Expression expression;

    @Override
    public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
      return visitor.visitSwitchCase(this, context);
    }
  }
}