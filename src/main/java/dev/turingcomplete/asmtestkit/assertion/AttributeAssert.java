package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.comparator.AttributeComparator;
import dev.turingcomplete.asmtestkit.assertion.option.AssertOption;
import dev.turingcomplete.asmtestkit.assertion.representation.AttributeRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Attribute;

import java.util.Comparator;

/**
 * An AssertJ {@link AbstractAssert} for an {@link Attribute} which will use the
 * {@link AttributeComparator} to determine the equality.
 *
 * <p>To override the used {@link AttributeRepresentation} or
 * {@link AttributeComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 *
 * <p>An instance can be created via {@link AsmAssertions#assertThat(Attribute)}.
 */
public class AttributeAssert extends AsmAssert<AttributeAssert, Attribute> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes an {@link AttributeAssert}.
   *
   * <p>There are no direct supported {@link AssertOption}s yet.
   *
   * @param actual the actual {@link Attribute}; may be null.
   * @param assertOptions an array of {@link AssertOption}s; never null.
   */
  public AttributeAssert(Attribute actual, AssertOption... assertOptions) {
    super(actual, AttributeAssert.class, Attribute.class, createSelfDescription(actual), assertOptions);

    info.useRepresentation(AttributeRepresentation.INSTANCE);
    //noinspection ResultOfMethodCallIgnored
    usingComparator(AttributeComparator.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //
  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private static String createSelfDescription(Attribute actual) {
    return "Attribute: " + AttributeRepresentation.INSTANCE.toStringOf(actual);
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
