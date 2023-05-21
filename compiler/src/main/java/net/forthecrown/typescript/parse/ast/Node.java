package net.forthecrown.typescript.parse.ast;


import net.forthecrown.typescript.parse.Location;

/**
 * Abstract syntax tree node
 */
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

  public final Location getStart() {
    return start;
  }

  public final void setStart(Location start) {
    this.start = start;
  }
}