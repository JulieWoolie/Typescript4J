package net.forthecrown.typescript.parse.ast;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ImportStatement extends Statement {

  private StringLiteral moduleName;
  private final List<Identifier> imports = new ArrayList<>();
  private Identifier alias;

  public boolean isStarImport() {
    return imports.size() == 1 && imports.get(0).getName().getValue().equals("*");
  }

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitImport(this, context);
  }
}