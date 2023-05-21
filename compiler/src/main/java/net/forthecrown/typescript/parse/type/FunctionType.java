package net.forthecrown.typescript.parse.type;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import net.forthecrown.typescript.parse.Name;

@Getter @Setter
public class FunctionType implements Type {

  private Type returnType;
  private final List<ParameterType> params = new ArrayList<>();

  @Override
  public String getName() {
    StringBuilder builder = new StringBuilder();
    builder.append("(");

    for (int i = 0; i < params.size(); i++) {
      if (i != 0) {
        builder.append(",");
      }

      var param = params.get(i);

      builder.append(param.name.getValue())
          .append(":")
          .append(param.type().getName());
    }

    builder.append(")=>")
        .append(returnType.getName());

    return builder.toString();
  }

  public record ParameterType(Name name, Type type) {
  }
}