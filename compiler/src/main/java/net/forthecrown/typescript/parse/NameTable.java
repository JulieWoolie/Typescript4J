package net.forthecrown.typescript.parse;

import java.util.HashMap;
import java.util.Map;

public class NameTable {

  private final Map<String, Name> symbols = new HashMap<>();

  public Name symbol(String label) {
    return symbols.computeIfAbsent(label, Name::new);
  }

  void rename(Name symbol, String newName) {
    String oldName = symbol.getValue();

    symbols.remove(oldName);

    Name existing = symbols.get(newName);

    if (existing == null) {
      symbols.put(newName, symbol);
      return;
    }

    throw new IllegalStateException(
        "Attempted to rename '%s' to already existing symbol '%s'"
            .formatted(oldName, newName)
    );
  }
}