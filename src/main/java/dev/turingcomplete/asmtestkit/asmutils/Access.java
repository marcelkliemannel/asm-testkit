package dev.turingcomplete.asmtestkit.asmutils;

import org.objectweb.asm.Opcodes;

import java.util.Locale;

import static org.objectweb.asm.Opcodes.*;

/**
 * An access flag.
 */
public enum Access {
  // -- Values ------------------------------------------------------------------------------------------------------ //

  PUBLIC(ACC_PUBLIC),
  PRIVATE(ACC_PRIVATE),
  PROTECTED(ACC_PROTECTED),
  STATIC(ACC_STATIC),
  FINAL(ACC_FINAL),
  SUPER(ACC_SUPER),
  SYNCHRONIZED(ACC_SYNCHRONIZED),
  OPEN(ACC_OPEN),
  TRANSITIVE(ACC_TRANSITIVE),
  VOLATILE(ACC_VOLATILE),
  BRIDGE(ACC_BRIDGE),
  STATIC_PHASE(ACC_STATIC_PHASE),
  VARARGS(ACC_VARARGS),
  TRANSIENT(ACC_TRANSIENT),
  NATIVE(ACC_NATIVE),
  INTERFACE(ACC_INTERFACE),
  ABSTRACT(ACC_ABSTRACT),
  STRICT(ACC_STRICT),
  SYNTHETIC(ACC_SYNTHETIC),
  ANNOTATION(ACC_ANNOTATION),
  ENUM(ACC_ENUM),
  MANDATED(ACC_MANDATED),
  MODULE(ACC_MODULE),
  RECORD(ACC_RECORD),
  DEPRECATED(ACC_DEPRECATED);

  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final int opcode;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  Access(int opcode) {
    this.opcode = opcode;
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Checks whether this {@link Access} flag is included in the combined
   * representation of the given {@code access} flags.
   *
   * @param access an {@code int} of access flags.
   * @return true if this {@link Access} is included, false otherwise.
   */
  public boolean check(int access) {
    return (opcode & access) != 0;
  }

  /**
   * Gets the opcode of this {@link Access}.
   *
   * @return the {@code int} opcode.
   * @see Opcodes
   */
  public int getOpcode() {
    return opcode;
  }

  /**
   * Gets a readable, like in the Java source code, representation of this
   * {@link Access}.
   *
   * @return a readable {@link String} representation; never null.
   */
  public String toJavaSourceCodeRepresentation() {
    return name().toLowerCase(Locale.ENGLISH);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
