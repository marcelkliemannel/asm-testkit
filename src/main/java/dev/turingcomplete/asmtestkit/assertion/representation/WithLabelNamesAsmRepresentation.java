package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.assertion.LabelNameLookup;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Label;

/**
 * An AssertJ {@link Representation} which handles representations of ASM
 * objects that are supporting {@link LabelNameLookup}.
 */
public interface WithLabelNamesAsmRepresentation extends Representation {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a simplified {@link String} representation of the given
   * {@code object} respecting names of {@link Label}s.
   *
   * <p>The default implementation will return the same values as
   * {@link #toStringOf(Object, LabelNameLookup)}.
   *
   * <p>A simplified representation should have a limited length and should
   * not contain line breaks. It should reflect the essence of an object,
   * without meta information.
   *
   * <p>In case the representation is undefined, the implementation should
   * return null.
   *
   * @param object          the object to create a simple representation of;
   *                        may be null.
   * @param labelNameLookup a {@link LabelNameLookup} to look up names of
   *                        {@link Label}s; never null.
   * @return the {@link String} representation; may be null.
   */
  default String toSimplifiedStringOf(Object object, LabelNameLookup labelNameLookup) {
    return toStringOf(object, labelNameLookup);
  }

  /**
   * Creates a {@link String} representation of the given {@code object}
   * respecting names of {@link Label}s.
   *
   * <p>In case the representation is undefined, the implementation should
   * return null.
   *
   * @param object          the object to create a representation of; may be
   *                        null.
   * @param labelNameLookup a {@link LabelNameLookup} to look up names of
   *                        {@link Label}s; never null.
   * @return the {@link String} representation; may be null.
   */
  String toStringOf(Object object, LabelNameLookup labelNameLookup);

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
