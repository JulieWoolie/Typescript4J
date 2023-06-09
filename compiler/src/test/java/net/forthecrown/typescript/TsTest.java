package net.forthecrown.typescript;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.google.common.io.Resources;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import net.forthecrown.typescript.parse.Parsers;
import net.forthecrown.typescript.parse.ast.CompilationUnit;
import net.forthecrown.typescript.parse.ast.PrintingVisitor;
import net.forthecrown.typescript.parse.source.Source;
import net.forthecrown.typescript.parse.source.Sources;
import org.junit.jupiter.api.Test;

public class TsTest {

  @Test
  void main() {
    URL testFile = Resources.getResource("ts_test_file.ts");
    Source source = Sources.fromUrl(testFile, "test_file");

    CompilationUnit unit = assertDoesNotThrow(() -> {
      return Parsers.parse(source);
    });

    StringBuffer buf = new StringBuffer();
    PrintingVisitor.printTree(unit, buf);

    assertDoesNotThrow(() -> {
      Files.writeString(
          Path.of("test_output.ts"),
          buf.toString()
      );
    });
  }
}