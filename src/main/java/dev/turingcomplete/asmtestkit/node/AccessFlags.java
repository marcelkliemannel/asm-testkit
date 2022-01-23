package dev.turingcomplete.asmtestkit.node;

import dev.turingcomplete.asmtestkit.asmutils.AccessKind;

import java.util.Objects;

/**
 * A container object to hold access flags.
 *
 * <p>In ASM access flags are represented by a simple int. By representing these
 * flags by their own type, we simplify the handling at the points where a class
 * gets mapped to asn ASM object type.
 */
public final class AccessFlags {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final int        access;
  private final AccessKind accessKind;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  private AccessFlags(int access, AccessKind accessKind) {
    this.access = access;
    this.accessKind = accessKind;
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link AccessFlags}.
   *
   * @param access     the access flags as a combined {@code int}.
   * @param accessKind the {@link AccessKind} the given {@code access} belongs;
   *                   never null.
   * @return a new {@link AccessFlags}; never null.
   */
  public static AccessFlags create(int access, AccessKind accessKind) {
    return new AccessFlags(access, Objects.requireNonNull(accessKind));
  }

  /**
   * Creates a new {@link AccessFlags} for {@link AccessKind#FIELD}.
   *
   * @param access the access flags as a combined {@code int}.
   * @return a new {@link AccessFlags}; never null.
   */
  public static AccessFlags forField(int access) {
    return create(access, AccessKind.FIELD);
  }

  /**
   * Creates a new {@link AccessFlags} for {@link AccessKind#CLASS}.
   *
   * @param access the access flags as a combined {@code int}.
   * @return a new {@link AccessFlags}; never null.
   */
  public static AccessFlags forClass(int access) {
    return create(access, AccessKind.CLASS);
  }

  /**
   * Creates a new {@link AccessFlags} for {@link AccessKind#METHOD}.
   *
   * @param access the access flags as a combined {@code int}.
   * @return a new {@link AccessFlags}; never null.
   */
  public static AccessFlags forMethod(int access) {
    return create(access, AccessKind.METHOD);
  }

  /**
   * Creates a new {@link AccessFlags} for {@link AccessKind#PARAMETER}.
   *
   * @param access the access flags as a combined {@code int}.
   * @return a new {@link AccessFlags}; never null.
   */
  public static AccessFlags forParameter(int access) {
    return create(access, AccessKind.PARAMETER);
  }

  /**
   * Creates a new {@link AccessFlags} for {@link AccessKind#MODULE}.
   *
   * @param access the access flags as a combined {@code int}.
   * @return a new {@link AccessFlags}; never null.
   */
  public static AccessFlags forModule(int access) {
    return create(access, AccessKind.MODULE);
  }

  /**
   * Creates a new {@link AccessFlags} for {@link AccessKind#MODULE_REQUIRES}.
   *
   * @param access the access flags as a combined {@code int}.
   * @return a new {@link AccessFlags}; never null.
   */
  public static AccessFlags forModuleRequires(int access) {
    return create(access, AccessKind.MODULE_REQUIRES);
  }

  /**
   * Creates a new {@link AccessFlags} for {@link AccessKind#MODULE_EXPORTS}.
   *
   * @param access the access flags as a combined {@code int}.
   * @return a new {@link AccessFlags}; never null.
   */
  public static AccessFlags forModuleExports(int access) {
    return create(access, AccessKind.MODULE_EXPORTS);
  }

  /**
   * Creates a new {@link AccessFlags} for {@link AccessKind#MODULE_OPENS}.
   *
   * @param access the access flags as a combined {@code int}.
   * @return a new {@link AccessFlags}; never null.
   */
  public static AccessFlags forModuleOpens(int access) {
    return create(access, AccessKind.MODULE_OPENS);
  }

  /**
   * Gets the access flags as a combined {@code int}.
   *
   * @return the access flags as an {@code int}.
   */
  public int access() {
    return access;
  }

  /**
   * Gets the {@link AccessKind} this access flags belong.
   *
   * @return the {@link AccessKind}; never null.
   */
  public AccessKind accessKind() {
    return accessKind;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
