package dev.turingcomplete.asmtestkit.assertion.representation;

import org.assertj.core.presentation.Representation;

/**
 * An AssertJ {@link Representation} which represents one specific ASM
 * {@link #getObjectClass()}.
 */
public interface AsmRepresentation extends Representation {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Gets the ASM {@link Class} this {@link AsmRepresentation} represents.
   *
   * @return a {@link Class}; never null.
   */
  Class<?> getObjectClass();

  /**
   * Creates a simplified {@link String} representation of the given
   * {@code object}.
   *
   * <p>The default implementation will return the same values as
   * {@link #toStringOf(Object)}.
   *
   * <p>A simplified representation should have a limited length and should
   * not contain line breaks. It should reflect the essence of an object,
   * without meta information.
   *
   * <p>In case the representation is undefined, the implementation should
   * return null.
   *
   * @param object the object to create a simple representation of; may be null.
   * @return the {@link String} representation; may be null.
   */
  default String toSimplifiedStringOf(Object object) {
    return toStringOf(object);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
