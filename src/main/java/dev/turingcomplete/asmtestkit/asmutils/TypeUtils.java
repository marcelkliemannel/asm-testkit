package dev.turingcomplete.asmtestkit.asmutils;

import org.objectweb.asm.Type;

import java.util.Objects;

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
   * @param internalName an internal name as {@link String}; never null.
   * @return a {@link Type} representing the internal name; never null.
   */
  public static Type toType(String internalName) {
    Objects.requireNonNull(internalName);
    return Type.getType("L" + internalName + ";");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
