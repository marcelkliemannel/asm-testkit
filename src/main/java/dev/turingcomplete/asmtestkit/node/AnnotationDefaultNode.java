package dev.turingcomplete.asmtestkit.node;

import java.util.Objects;

/**
 * A container object to hold an annotation default value.
 */
public final class AnnotationDefaultNode {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final Object value;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  private AnnotationDefaultNode(Object value) {
    this.value = value;
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link AnnotationDefaultNode}.
   *
   * @param value an {@link Object}; never null.
   * @return a new {@link AnnotationDefaultNode}; never null.
   */
  public static AnnotationDefaultNode create(Object value) {
    return new AnnotationDefaultNode(Objects.requireNonNull(value));
  }

  /**
   * Creates a new {@link AnnotationDefaultNode} if the given {@code defaultValue}
   * is not null.
   *
   * @param value an {@link Object}; may be null.
   * @return a new {@link AnnotationDefaultNode}; may be null.
   */
  public static AnnotationDefaultNode createOrNull(Object value) {
    if (value == null) {
      return null;
    }

    return new AnnotationDefaultNode(value);
  }

  /**
   * Gets the annotation default value.
   *
   * @return the default value {@link Object}; never null.
   */
  public Object value() {
    return value;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
