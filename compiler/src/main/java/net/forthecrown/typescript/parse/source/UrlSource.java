package net.forthecrown.typescript.parse.source;

import static net.forthecrown.typescript.parse.source.Sources.CHARSET;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;

public record UrlSource(URL url, String name) implements Source {

  @Override
  public StringBuffer read() throws IOException {
    InputStream stream = url.openStream();
    InputStreamReader reader = new InputStreamReader(stream, CHARSET);
    StringWriter strWriter = new StringWriter();

    reader.transferTo(strWriter);
    StringBuffer buf = strWriter.getBuffer();
    stream.close();

    return buf;
  }
}