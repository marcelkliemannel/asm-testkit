package dev.turingcomplete.asmtestkit.asmutils;

import java.util.EnumSet;
import java.util.Locale;

import static dev.turingcomplete.asmtestkit.asmutils.Access.*;

/**
 * A group of {@link Access} flags.
 *
 * <p>This is necessary because certain {@code Access} opcodes are reused for
 * different purposes.
 */
public enum AccessKind {
  // -- Values ------------------------------------------------------------------------------------------------------ //

  CLASS(EnumSet.of(PUBLIC, PRIVATE, PROTECTED, FINAL, SUPER, INTERFACE, ABSTRACT, SYNTHETIC, ANNOTATION, RECORD, DEPRECATED, ENUM, Access.MODULE)),
  FIELD(EnumSet.of(PUBLIC, PRIVATE, PROTECTED, STATIC, FINAL, VOLATILE, TRANSIENT, SYNTHETIC, DEPRECATED, ENUM)),
  METHOD(EnumSet.of(PUBLIC, PRIVATE, PROTECTED, STATIC, FINAL, SYNCHRONIZED, BRIDGE, VARARGS, NATIVE, ABSTRACT, STRICT, SYNTHETIC, DEPRECATED)),
  PARAMETER(EnumSet.of(FINAL, SYNTHETIC, MANDATED)),
  MODULE(EnumSet.of(OPEN, SYNTHETIC, MANDATED)),
  MODULE_REQUIRES(EnumSet.of(TRANSITIVE, STATIC_PHASE, SYNTHETIC, MANDATED)),
  MODULE_EXPORTS(EnumSet.of(SYNTHETIC, MANDATED)),
  MODULE_OPENS(EnumSet.of(SYNTHETIC, MANDATED));

  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final EnumSet<Access> accesses;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  AccessKind(EnumSet<Access> accesses) {
    this.accesses = accesses;
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public String toString() {
    return name().toLowerCase(Locale.ENGLISH);
  }

  /**
   * Gets all {@link Access} in this {@link AccessKind}.
   *
   * @return an {@link EnumSet} of {@link Access}s; never null.
   */
  public EnumSet<Access> getAccesses() {
    return EnumSet.copyOf(accesses);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
