package net.forthecrown.typescript.parse.type;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import net.forthecrown.typescript.parse.Name;

@Getter
public class ObjectType implements Type {

  private final Name symbol;

  @Setter
  private Type superType;

  private final List<Type> implementations = new ArrayList<>();

  public ObjectType(Name name) {
    this.symbol = name;
  }

  public String getName() {
    return symbol.getValue();
  }
}