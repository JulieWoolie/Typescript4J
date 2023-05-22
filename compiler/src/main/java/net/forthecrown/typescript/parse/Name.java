package net.forthecrown.typescript.parse;

import java.util.Objects;
import java.util.stream.IntStream;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Name implements CharSequence {

  String value;

  @Setter(AccessLevel.PACKAGE)
  private NameTable table;

  public Name(String value) {
    this.value = value;
  }

  public void setValue(String value) {
    Objects.requireNonNull(value);

    if (table == null) {
      this.value = value;
      return;
    }

    table.rename(this, value);
    this.value = value;
  }

  @Override
  public int length() {
    return value.length();
  }

  @Override
  public char charAt(int index) {
    return value.charAt(index);
  }

  @Override
  public String subSequence(int start, int end) {
    return value.substring(start, end);
  }

  @Override
  public boolean isEmpty() {
    return value.isEmpty();
  }

  @Override
  public IntStream chars() {
    return value.chars();
  }

  @Override
  public IntStream codePoints() {
    return value.codePoints();
  }

  @Override
  public String toString() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Name symbol)) {
      return false;
    }
    return getValue().equals(symbol.getValue());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getValue());
  }
}