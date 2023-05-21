package net.forthecrown.typescript.parse.source;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Objects;

public final class Sources {
  private Sources() {}

  public static final Charset CHARSET = StandardCharsets.UTF_8;

  public static Source fromPath(Path path) {
    return fromPath(path, null);
  }

  public static Source fromPath(Path path, Path scriptDirectory) {
    Objects.requireNonNull(path, "Null path");

    String name;

    if (scriptDirectory == null) {
      name = path.toString();
    } else {
      Path relative = scriptDirectory.relativize(path);
      name = relative.toString();
    }

    return new PathSource(path, name);
  }

  public static Source fromUrl(String url) throws MalformedURLException {
    Objects.requireNonNull(url, "Null url");
    URL urlObject = new URL(url);
    return fromUrl(urlObject);
  }

  public static Source fromUrl(URL url, String name) {
    Objects.requireNonNull(url, "Null url");
    name = Objects.requireNonNullElse(name, url.toString());
    return new UrlSource(url, name);
  }

  public static Source fromUrl(URL url) {
    return fromUrl(url, null);
  }

  public static Source direct(CharSequence src) {
    return direct(src, null);
  }

  public static Source direct(CharSequence src, String name) {
    Objects.requireNonNull(src, "Null source");

    name = Objects.requireNonNullElse(name, "<eval>");
    return new DirectSource(src, name);
  }
}