package dev.turingcomplete.asmtestkit.assertion.representation;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.TypePath;

import static dev.turingcomplete.asmtestkit.assertion.representation.TypePathRepresentation.INSTANCE;
import static org.assertj.core.api.Assertions.assertThat;

class TypePathRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCreateRepresentation() {
    assertThat(INSTANCE.toStringOf(TypePath.fromString("*")))
            .isEqualTo("*");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
