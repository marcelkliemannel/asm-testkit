package dev.turingcomplete.asmtestkit.asmutils;

import org.objectweb.asm.Type;

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
   * @param name an internal name as {@link String}; may be null.
   * @return a {@link Type} representing the internal name; or null if the
   * {@code name} is null.
   */
  public static Type nameToTypeElseNull(String name) {
    if (name == null) {
      return null;
    }

    return Type.getType("L" + ClassNameUtils.toInternalName(name) + ";");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
