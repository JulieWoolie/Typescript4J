package net.forthecrown.typescript.parse.ast;


import lombok.Getter;
import lombok.Setter;
import net.forthecrown.typescript.parse.Location;

/**
 * Abstract syntax tree node
 */
@Getter @Setter
public abstract class Node {

  /**
   * Starting location of the node
   */
  private Location start;

  public Node(Location start) {
    this.start = start;
  }

  public Node() {
  }

  public abstract <R, C> R visit(NodeVisitor<R, C> visitor, C context);
}