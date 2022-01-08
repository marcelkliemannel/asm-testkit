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
   * @param object the {@link T} object whose representation is to be created;
   *               never null.
   * @return the {@link String} representation; never null.
   */
  protected abstract String toRepresentation(T object);

  @Override
  protected final String fallbackToStringOf(Object object) {
    if (objectClass.isInstance(object)) {
      return toRepresentation(objectClass.cast(object));
    }

    return super.fallbackToStringOf(object);
  }

  protected boolean isApplicable(Class<?> objectClass) {
    return this.objectClass.equals(objectClass) || this.objectClass.isAssignableFrom(objectClass);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
