package net.forthecrown.typescript.parse;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import net.forthecrown.typescript.parse.source.Source;
import net.forthecrown.typescript.parse.source.Sources;

public final class CharReaders {
  private CharReaders() {}

  public static CharReader fromPath(Path path) throws IOException {
    return fromSource(Sources.fromPath(path));
  }

  public static CharReader fromString(CharSequence src, String name) throws IOException {
    return fromSource(Sources.direct(src, name));
  }

  public static CharReader fromString(CharSequence src) throws IOException {
    return fromSource(Sources.direct(src));
  }

  public static CharReader fromSource(Source source) throws IOException {
    Objects.requireNonNull(source);

    StringBuffer buf = source.read();
    String name = source.name();

    ErrorFactory factory = ErrorFactories.named(buf, name);
    return new CharReader(buf, factory);
  }
}