package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.asmutils.Access;
import dev.turingcomplete.asmtestkit.asmutils.AccessKind;
import org.assertj.core.presentation.Representation;
import org.assertj.core.presentation.StandardRepresentation;

import java.util.Comparator;
import java.util.Objects;

import static dev.turingcomplete.asmtestkit.asmutils.AccessKind.*;

/**
 * An AssertJ {@link Representation} for access flags.
 *
 * Example output: {@code (513) public interface}.
 */
public class AccessRepresentation extends StandardRepresentation {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link AccessRepresentation} instance for class accesses.
   */
  public static final AccessRepresentation CLASS_INSTANCE = new AccessRepresentation(CLASS);

  /**
   * A reusable {@link AccessRepresentation} instance for method accesses.
   */
  public static final AccessRepresentation METHOD_INSTANCE = new AccessRepresentation(METHOD);

  /**
   * A reusable {@link AccessRepresentation} instance for field accesses.
   */
  public static final AccessRepresentation FIELD_INSTANCE = new AccessRepresentation(FIELD);

  /**
   * A reusable {@link AccessRepresentation} instance for parameter accesses.
   */
  public static final AccessRepresentation PARAMETER_INSTANCE = new AccessRepresentation(PARAMETER);

  /**
   * A reusable {@link AccessRepresentation} instance for module descriptor
   * accesses.
   */
  public static final AccessRepresentation MODULE_INSTANCE = new AccessRepresentation(MODULE);

  /**
   * A reusable {@link AccessRepresentation} instance for module requires
   * statements accesses.
   */
  public static final AccessRepresentation MODULE_REQUIRES_INSTANCE = new AccessRepresentation(MODULE_REQUIRES);

  /**
   * A reusable {@link AccessRepresentation} instance for module exports
   * statements accesses.
   */
  public static final AccessRepresentation MODULE_EXPORTS_INSTANCE = new AccessRepresentation(MODULE_EXPORTS);

  /**
   * A reusable {@link AccessRepresentation} instance for module opens
   * statements accesses.
   */
  public static final AccessRepresentation MODULE_OPENS_INSTANCE = new AccessRepresentation(MODULE_OPENS);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  protected final AccessKind accessKind;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AccessRepresentation(AccessKind accessKind) {
    this.accessKind = Objects.requireNonNull(accessKind);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link AnnotationNodeRepresentation} instance for the given
   * {@link AccessKind}.
   *
   * @param accessKind an {@link AccessKind}; never null.
   * @return a new {@link AnnotationNodeRepresentation}; never null;
   */
  public static AccessRepresentation create(AccessKind accessKind) {
    return new AccessRepresentation(accessKind);
  }

  /**
   * Gets a reusable {@link AccessRepresentation} instance for the given
   * {@link AccessKind}.
   *
   * @param accessKind an {@link AccessKind}; never null.
   * @return an {@link AccessRepresentation}; never null.
   */
  public static AccessRepresentation instance(AccessKind accessKind) {
    switch (Objects.requireNonNull(accessKind)) {
      case CLASS:
        return CLASS_INSTANCE;
      case FIELD:
        return FIELD_INSTANCE;
      case METHOD:
        return METHOD_INSTANCE;
      case PARAMETER:
        return PARAMETER_INSTANCE;
      case MODULE:
        return MODULE_INSTANCE;
      case MODULE_REQUIRES:
        return MODULE_REQUIRES_INSTANCE;
      case MODULE_EXPORTS:
        return MODULE_EXPORTS_INSTANCE;
      case MODULE_OPENS:
        return MODULE_OPENS_INSTANCE;
      default:
        throw new IllegalStateException("Unknown " + AccessKind.class.getSimpleName() + ": " + accessKind + ". Please report this as a bug.");
    }
  }

  @Override
  protected String toStringOf(Number number) {
    if (number instanceof Integer) {
      int access = number.intValue();
      String textifiedAccess = "(" + access + ")";
      if (access > 0) {
        textifiedAccess += " " + toJavaSourceCodeRepresentation(access);
      }
      return textifiedAccess;
    }

    return super.toStringOf(number);
  }

  /**
   * Creates an array of readable access representations, like in the Java
   * source code, from the given {@code access} flags;
   *
   * @param access the access flags as a combined {@link Integer}; may be null.
   * @return an array of {@link String}s with Java source code like
   * representation of the access flags; never null. An empty array if
   * {@code access} is null.
   */
  public String[] toJavaSourceCodeRepresentations(Integer access) {
    if (access == null) {
      return new String[0];
    }

    return accessKind.getAccesses().stream()
                     .filter(_access -> _access.check(access))
                     .sorted(Comparator.comparingInt(Access::getOpcode))
                     .map(Access::toJavaSourceCodeRepresentation)
                     .toArray(String[]::new);
  }

  /**
   * Creates a combined readable access representation, like in the Java
   * source code, from the given {@code access} flags;
   *
   * @param access the access flags as a combined {@link Integer}; may be null.
   * @return a {@link String} representing a Java source code like representation
   * of the access flags; may be null if {@code access} is null.
   */
  public String toJavaSourceCodeRepresentation(Integer access) {
    if (access == null) {
      return null;
    }

    return String.join(" ", toJavaSourceCodeRepresentations(access));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
