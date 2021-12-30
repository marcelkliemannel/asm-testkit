package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.comperator.AttributeComparator;
import dev.turingcomplete.asmtestkit.assertion.option.AssertOption;
import dev.turingcomplete.asmtestkit.assertion.representation.AttributeRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.objectweb.asm.Attribute;

import java.util.Objects;

/**
 * An {@link AbstractAssert} for an {@link Attribute} which will use the
 * {@link AttributeComparator} to determine the equality.
 *
 * <p>An instance can be created via {@link AsmAssertions#assertThat(Attribute)}.
 */
public final class AttributeAssert extends AsmAssert<AttributeAssert, Attribute> {
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

    info.useRepresentation(AttributeRepresentation.instance());
    //noinspection ResultOfMethodCallIgnored
    usingComparator(AttributeComparator.instance());
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //
  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private static String createSelfDescription(Attribute actual) {
    return "Attribute: " + Objects.requireNonNullElse(actual.type, "null");
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
