package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.comparator.AttributeComparator;
import dev.turingcomplete.asmtestkit.assertion.representation.AttributeRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Attribute;

import java.util.Comparator;

/**
 * An AssertJ {@link AbstractAssert} for an {@link Attribute} which will use the
 * {@link AttributeComparator} to determine the equality.
 *
 * <p>An instance can be created via {@link AsmAssertions#assertThat(Attribute)}.
 * Use {@link AsmAssertions#assertThatAttributes(Iterable)} for multiple
 * {@code Attribute}s.
 *
 * <p>To override the used {@link AttributeRepresentation} or
 * {@link AttributeComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 */
public class AttributeAssert extends AsmAssert<AttributeAssert, Attribute> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes an {@link AttributeAssert}.
   *
   * @param actual the actual {@link Attribute}; may be null.
   */
  protected AttributeAssert(Attribute actual) {
    super("Attribute", actual, AttributeAssert.class, AttributeRepresentation.INSTANCE, AttributeComparator.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //
  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
