package dev.turingcomplete.asmtestkit.assertion.representation;

import org.assertj.core.presentation.Representation;
import org.assertj.core.presentation.StandardRepresentation;

import java.util.Objects;

/**
 * A base class for an AssertJ {@link Representation} for ASM objects.
 */
public abstract class AsmRepresentation<T> extends StandardRepresentation {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  protected final Class<T> objectClass;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AsmRepresentation(Class<T> objectClass) {
    this.objectClass = Objects.requireNonNull(objectClass);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a {@link String} representation of the given {@code object}.
   *
   * <p>In case the representation is undefined, the implementation should
   * return null.
   *
   * @param object the object of type {@link T} to create a representation of;
   *               never null.
   * @return the {@link String} representation; may be null.
   */
  protected abstract String doToStringOf(T object);

  /**
   * Creates a simplified {@link String} representation of the given
   * {@code object}.
   *
   * <p>Subtypes should override {@link #doToSimplifiedStringOf(Object)}.
   * Otherwise, the same value as {@link #doToStringOf(Object)} will be
   * returned.
   *
   * <p>A simplified representation should have a limited length and should
   * not contain line breaks. It should reflect the essence of an object,
   * without meta information.
   *
   * <p>In case the representation is undefined, the implementation should
   * return null.
   *
   * @param object the object of type {@link T} to create a simple representation
   *               of; may be null.
   * @return the {@link String} representation; may be null.
   */
  public final String toSimplifiedStringOf(Object object) {
    return object != null && (objectClass.equals(object) || objectClass.isAssignableFrom(objectClass))
            ? doToSimplifiedStringOf(objectClass.cast(object))
            : null;
  }

  /**
   * Creates a simplified {@link String} representation of the given
   * {@code object}.
   *
   * <p>The default implementation will return the same values as
   * {@link #doToStringOf(Object)}.
   *
   * <p>A simplified representation should have a limited length and should
   * not contain line breaks. It should reflect the essence of an object,
   * without meta information.
   *
   * <p>In case the representation is undefined, the implementation should
   * return null.
   *
   * @param object the object of type {@link T} to create a simple representation
   *               of; never null.
   * @return the {@link String} representation; may be null.
   */
  protected String doToSimplifiedStringOf(T object) {
    return doToStringOf(object);
  }

  @Override
  protected final String fallbackToStringOf(Object object) {
    if (objectClass.isInstance(object)) {
      return doToStringOf(objectClass.cast(object));
    }

    return super.fallbackToStringOf(object);
  }

  protected boolean isApplicable(Class<?> objectClass) {
    return this.objectClass.equals(objectClass) || this.objectClass.isAssignableFrom(objectClass);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
