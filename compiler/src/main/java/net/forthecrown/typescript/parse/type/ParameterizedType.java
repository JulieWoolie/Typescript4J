package net.forthecrown.typescript.parse.type;

import java.util.List;
import java.util.StringJoiner;

public class ParameterizedType implements Type {

  private final Type base;
  private final List<Type> params;

  public ParameterizedType(Type base, List<Type> params) {
    this.base = base;
    this.params = params;
  }

  @Override
  public String getName() {
    StringJoiner joiner = new StringJoiner(",", base.getName() + "<", ">");

    for (int i = 0; i < params.size(); i++) {
      joiner.add(params.get(i).getName());
    }

    return joiner.toString();
  }
}