package net.forthecrown.typescript.parse.ast;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
public class ImportStatement extends Statement {

  private StringLiteral moduleName;
  private final List<ImportedBinding> imports = new ArrayList<>();
  private boolean starImport;
  private Identifier alias;

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitImport(this, context);
  }

  @Getter @Setter
  public static class ImportedBinding extends Statement {
    private Identifier binding;
    private Identifier alias;

    @Override
    public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
      return visitor.visitBindingImport(this, context);
    }
  }
}