package dev.turingcomplete.asmtestkit.assertion.representation;

import org.assertj.core.presentation.StandardRepresentation;

import java.util.Objects;

public abstract class AsmRepresentation<T> extends StandardRepresentation {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final Class<T> objectClass;

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
  protected abstract String createRepresentation(T object);

  /**
   * Creates a simplified {@link String} representation of the given
   * {@code object}.
   *
   * <p>Subtypes should override {@link #createSimplifiedRepresentation(Object)}.
   * Otherwise, the same value as {@link #createRepresentation(Object)} will be
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
  public final String toSimplifiedRepresentation(T object) {
    return object != null ? createSimplifiedRepresentation(object) : null;
  }

  /**
   * Creates a simplified {@link String} representation of the given
   * {@code object}.
   *
   * <p>The default implementation will return the same values as
   * {@link #createRepresentation(Object)}.
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
  protected String createSimplifiedRepresentation(T object) {
    return createRepresentation(object);
  }

  @Override
  protected final String fallbackToStringOf(Object object) {
    if (objectClass.isInstance(object)) {
      return createRepresentation(objectClass.cast(object));
    }

    return super.fallbackToStringOf(object);
  }

  protected boolean isApplicable(Class<?> objectClass) {
    return this.objectClass.equals(objectClass) || this.objectClass.isAssignableFrom(objectClass);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
