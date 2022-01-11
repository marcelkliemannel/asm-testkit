package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.assertion.__helper.DummyAttribute;
import org.junit.jupiter.api.Test;

import static dev.turingcomplete.asmtestkit.assertion.representation.AttributeRepresentation.INSTANCE;
import static org.assertj.core.api.Assertions.assertThat;

class AttributeRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCreateRepresentation() {
    assertThat(INSTANCE.toStringOf(new DummyAttribute("Name", "Content")))
            .isEqualTo("NameContent");
  }

  @Test
  void testCreateSimplifiedRepresentation() {
    assertThat(INSTANCE.createSimplifiedRepresentation(new DummyAttribute("Name", "Content")))
            .isEqualTo("Name");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
