package dev.turingcomplete.asmtestkit.asmutils;

import org.objectweb.asm.Type;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class TypeUtils {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  private TypeUtils() {
    throw new UnsupportedOperationException();
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a {@link Type} from the given internal or class name (e.g.,
   * {@code java/lang/String} or {@code java.lang.String}).
   *
   * @param name a class or internal name as {@link String}; may be null.
   * @return a {@link Type}; or null if the {@code name} is null.
   */
  public static Type nameToTypeElseNull(String name) {
    if (name == null) {
      return null;
    }

    return Type.getType("L" + ClassNameUtils.toInternalName(name) + ";");
  }

  /**
   * Creates a {@link List} of {@link Type}s from the given {@code List} of
   * internal or class names (e.g., {@code java/lang/String} or
   * {@code java.lang.String}).
   *
   * @param names a {@link List} of class or internal name as {@link String};
   *              may be null.
   * @return a {@link List} of {@link Type}s; never null.
   */
  public static List<Type> namesToTypes(List<String> names) {
    return Optional.ofNullable(names)
                   .stream()
                   .flatMap(Collection::stream)
                   .map(TypeUtils::nameToTypeElseNull)
                   .collect(Collectors.toList());
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
