package dev.turingcomplete.asmtestkit.node;

import java.util.Objects;

/**
 * A container object to hold an annotation default value.
 */
public final class AnnotationDefault {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final Object defaultValue;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  private AnnotationDefault(Object defaultValue) {
    this.defaultValue = defaultValue;
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link AnnotationDefault}.
   *
   * @param defaultValue an {@link Object}; never null.
   * @return a new {@link AnnotationDefault}; never null.
   */
  public static AnnotationDefault create(Object defaultValue) {
    return new AnnotationDefault(Objects.requireNonNull(defaultValue));
  }

  /**
   * Creates a new {@link AnnotationDefault} if the given {@code defaultValue}
   * is not null.
   *
   * @param defaultValue an {@link Object}; may be null.
   * @return a new {@link AnnotationDefault}; may be null.
   */
  public static AnnotationDefault createOrNull(Object defaultValue) {
    if (defaultValue == null) {
      return null;
    }

    return new AnnotationDefault(defaultValue);
  }

  /**
   * Gets the annotation default value.
   *
   * @return the default value {@link Object}; never null.
   */
  public Object defaultValue() {
    return defaultValue;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
