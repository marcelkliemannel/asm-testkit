package dev.turingcomplete.asmtestkit.assertion.representation;

import org.assertj.core.presentation.Representation;

/**
 * An AssertJ {@link Representation} which can handle the representations of
 * various ASM objects.
 */
public interface AsmRepresentations extends Representation, WithLabelNamesAsmRepresentation {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

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
  String toSimplifiedStringOf(Object object);

  /**
   * Gets an {@link AsmRepresentation} for the given ASM {@code elementClass}.
   *
   * @param elementClass an ASM element {@link Class}; never null.
   * @param <T>          the type of the ASM element.
   * @return the matching {@link AsmRepresentation}; never null.
   * @throws IllegalArgumentException if there is no representation for the
   *                                  given {@code elementClass}.
   */
  <T> AsmRepresentation<T> getAsmRepresentation(Class<T> elementClass);

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
