package net.forthecrown.typescript.parse.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.forthecrown.typescript.parse.CompilerErrors;
import net.forthecrown.typescript.parse.Name;
import net.forthecrown.typescript.parse.ast.ClassDeclaration;

public class Types {

  public static final Type NUMBER  = new NumberType();
  public static final Type STRING  = new StringType();
  public static final Type BOOLEAN = new BooleanType();
  public static final Type VOID    = new VoidType();

  public static final Type ANY = new AnyType();

  private final Map<String, Type> byName = new HashMap<>();
  private final CompilerErrors errors;

  public Types(CompilerErrors errors) {
    this.errors = errors;
  }

  public Type fromName(Name symbol) {
    return switch (symbol.getValue()) {
      case "number"  -> NUMBER;
      case "string"  -> STRING;
      case "boolean" -> BOOLEAN;
      case "any"     -> ANY;
      case "void"    -> VOID;
      default        -> byName.computeIfAbsent(symbol.getValue(), s -> new ObjectType(symbol));
    };
  }

  public Type parameterized(Type base, List<Type> params) {
    if (params.size() == 1 && base.getName().equals("Array")) {
      return array(params.get(0));
    }

    ParameterizedType type = new ParameterizedType(base, params);
    return addType(type);
  }

  public Type array(Type type) {
    ArrayType result = new ArrayType(type);
    return addType(result);
  }

  public ObjectType object(ClassDeclaration decl) {
    ObjectType type = new ObjectType(decl.getName().getName());

    if (decl.getSuperClass() != null) {
      Type superType = fromName(decl.getSuperClass().getName());
      type.setSuperType(superType);
    }

    return addType(type);
  }

  public <T extends Type> T addType(T type) {
    Type existing = byName.get(type.getName());

    if (type.getClass().isInstance(existing)) {
      return (T) existing;
    }

    if (existing != null) {
      errors.error("Duplicate type '%s' (type has matching name but represents different type)",
          type.getName()
      );
    }

    byName.put(type.getName(), type);
    return type;
  }
}