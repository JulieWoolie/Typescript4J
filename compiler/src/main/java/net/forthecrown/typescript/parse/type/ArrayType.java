package net.forthecrown.typescript.parse.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ArrayType implements Type {

  private final Type contentType;

  @Override
  public String getName() {
    return contentType.getName() + "[]";
  }
}