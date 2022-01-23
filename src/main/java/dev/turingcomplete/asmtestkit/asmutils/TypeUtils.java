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
   * Creates a {@link Type} from the given internal name (e.g.,
   * {@code java/lang/String}).
   *
   * @param internalName an internal name as {@link String}; may be null.
   * @return a {@link Type} representing the internal name; or null if the
   * {@code internalName} is null.
   */
  public static Type toTypeElseNull(String internalName) {
    if (internalName == null) {
      return null;
    }

    return Type.getType("L" + internalName + ";");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
