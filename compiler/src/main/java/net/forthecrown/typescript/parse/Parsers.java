package net.forthecrown.typescript.parse;

import java.io.IOException;
import net.forthecrown.typescript.parse.ast.CompilationUnit;
import net.forthecrown.typescript.parse.source.Source;
import net.forthecrown.typescript.parse.type.Types;

public final class Parsers {
  private Parsers() {}

  public static CompilationUnit parse(Source source) throws IOException {
    CharReader reader = CharReaders.fromSource(source);
    Lexer lexer = new Lexer(reader);

    CompilerErrors errors = new CompilerErrors(reader.factory());
    Types types = new Types(errors);

    NameTable table = new NameTable();

    TypescriptParser parser = new TypescriptParser(lexer, errors, types, table);
    return parser.parse();
  }
}