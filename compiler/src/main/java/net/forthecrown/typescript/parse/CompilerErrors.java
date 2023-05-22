package net.forthecrown.typescript.parse;

import java.util.ArrayList;
import java.util.List;
import javax.tools.Diagnostic;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompilerErrors {

  private final List<Diagnostic> diagnostics = new ArrayList<>();
  private final ErrorFactory factory;

  private boolean errorsAreFatal = true;
  private boolean errorThrown;

  public CompilerErrors(ErrorFactory factory) {
    this.factory = factory;
  }

  public void error(String format, Object... args) {
    error(null, format, args);
  }

  public void error(Location location, String format, Object... args) {
    Diagnostic diagnostic = new Diagnostic(format.formatted(args), location, ErrorLevel.ERROR);
    addDiagnostic(diagnostic);
  }

  public void warn(String format, Object... args) {
    warn(null, format, args);
  }

  public void warn(Location location, String format, Object... args) {
    Diagnostic diagnostic = new Diagnostic(format.formatted(args), location, ErrorLevel.WARN);
    addDiagnostic(diagnostic);
  }

  public void addDiagnostic(Diagnostic diagnostic) {
    diagnostics.add(diagnostic);

    if (diagnostic.level() == ErrorLevel.ERROR) {
      errorThrown = true;

      if (errorsAreFatal) {
        throw factory.create(diagnostic.location, diagnostic.message);
      }
    }
  }

  public List<Diagnostic> getDiagnostics() {
    return diagnostics;
  }

  public ErrorFactory getFactory() {
    return factory;
  }

  public record Diagnostic(String message, Location location, ErrorLevel level) {
  }

  public enum ErrorLevel {
    ERROR,
    WARN
  }
}