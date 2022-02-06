package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.assertion.LabelIndexLookup;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Label;

/**
 * An AssertJ {@link Representation} which handles representations of ASM
 * objects that are supporting {@link LabelIndexLookup}.
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
   * {@link #toStringOf(Object, LabelIndexLookup)}.
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
   * @param labelIndexLookup a {@link LabelIndexLookup} to look up names of
   *                        {@link Label}s; never null.
   * @return the {@link String} representation; may be null.
   */
  default String toSimplifiedStringOf(Object object, LabelIndexLookup labelIndexLookup) {
    return toStringOf(object, labelIndexLookup);
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
   * @param labelIndexLookup a {@link LabelIndexLookup} to look up names of
   *                        {@link Label}s; never null.
   * @return the {@link String} representation; may be null.
   */
  String toStringOf(Object object, LabelIndexLookup labelIndexLookup);

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
