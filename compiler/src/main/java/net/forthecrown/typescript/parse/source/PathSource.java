package net.forthecrown.typescript.parse.source;

import static net.forthecrown.typescript.parse.source.Sources.CHARSET;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;

record PathSource(Path path, String name) implements Source {

  @Override
  public StringBuffer read() throws IOException {
    BufferedReader reader = Files.newBufferedReader(path, CHARSET);
    StringWriter writer = new StringWriter();
    reader.transferTo(writer);
    reader.close();

    return writer.getBuffer();
  }
}