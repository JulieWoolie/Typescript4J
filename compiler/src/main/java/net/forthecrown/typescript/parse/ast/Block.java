package net.forthecrown.typescript.parse.ast;

import java.util.ArrayList;
import java.util.List;
import net.forthecrown.typescript.parse.Location;

public class Block extends Statement {

  private final List<Statement> statements = new ArrayList<>();

  public Block(Location start) {
    super(start);
  }

  @Override
  public <R, C> R visit(NodeVisitor<R, C> visitor, C context) {
    return visitor.visitBlock(this, context);
  }

  public List<Statement> getStatements() {
    return statements;
  }
}