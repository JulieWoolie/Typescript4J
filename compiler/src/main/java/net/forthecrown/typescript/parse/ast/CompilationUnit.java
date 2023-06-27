package net.forthecrown.typescript.parse.ast;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class CompilationUnit extends Node {

  private final List<ExportStatement> exports = new ArrayList<>();

  private final List<ImportStatement> imports = new ArrayList<>();

  private final List<Statement> statements = new ArrayList<>();

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitRoot(this, context);
  }
}