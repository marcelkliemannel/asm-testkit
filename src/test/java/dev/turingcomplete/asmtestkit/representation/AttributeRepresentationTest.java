package dev.turingcomplete.asmtestkit.representation;

import dev.turingcomplete.asmtestkit.__helper.DummyAttribute;
import org.junit.jupiter.api.Test;

import static dev.turingcomplete.asmtestkit.representation.AttributeRepresentation.INSTANCE;
import static org.assertj.core.api.Assertions.assertThat;

class AttributeRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testToStringOf() {
    assertThat(INSTANCE.toStringOf(new DummyAttribute("Name", "Content")))
            .isEqualTo("NameContent");
  }

  @Test
  void testToSimplifiedStringOf() {
    assertThat(INSTANCE.doToSimplifiedStringOf(new DummyAttribute("Name", "Content")))
            .isEqualTo("Name");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
