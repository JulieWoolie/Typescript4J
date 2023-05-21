package net.forthecrown.typescript.parse.source;

import java.io.IOException;

/**
 * A source for a stream of characters
 */
public interface Source {

  /**
   * Reads the source's content
   * @return Source content
   * @throws IOException If an IO Error occurred
   */
  StringBuffer read() throws IOException;

  /**
   * Gets the source's name
   * @return Source's name
   */
  String name();
}