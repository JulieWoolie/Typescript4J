package net.forthecrown.typescript.parse.type;

public class JavaObjectType implements Type {

  private final Class<?> javaClass;
  private final String name;

  public JavaObjectType(Class<?> javaClass) {
    this(javaClass, javaClass.getSimpleName());
  }

  public JavaObjectType(Class<?> javaClass, String name) {
    this.javaClass = javaClass;
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }
}