package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.comperator.AttributeComparator;
import dev.turingcomplete.asmtestkit.assertion.representation.AttributeRepresentation;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.IterableAssert;
import org.objectweb.asm.Attribute;

public final class AsmAssertions {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  private AsmAssertions() {
    throw new UnsupportedOperationException();
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  // ---- Objects ----------------------------------------------------------- //

  /**
   * Creates an {@link AttributeAssert}.
   *
   * @param attribute an {@link Attribute}; may be null.
   * @return a new {@link AttributeAssert}; never null.
   */
  public static AttributeAssert assertThat(Attribute attribute) {
    return new AttributeAssert(attribute);
  }

  // ---- Iterable ---------------------------------------------------------- //

  /**
   * Creates an {@link IterableAssert} for {@link Attribute}s.
   *
   * @param attributes an {@link Iterable} of {@link Attribute}s; may be null.
   * @return a new {@link IterableAssert}; never null.
   */
  public static IterableAssert<Attribute> assertThatAttributes(Iterable<Attribute> attributes) {
    return Assertions.assertThat(attributes)
                     .withRepresentation(AttributeRepresentation.instance())
                     .usingElementComparator(AttributeComparator.instance());
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
