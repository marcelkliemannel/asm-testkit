package dev.turingcomplete.asmtestkit.representation;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.TypePath;

import static dev.turingcomplete.asmtestkit.representation.TypePathRepresentation.INSTANCE;
import static org.assertj.core.api.Assertions.assertThat;

class TypePathRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testToStringOf() {
    assertThat(INSTANCE.toStringOf(TypePath.fromString("*")))
            .isEqualTo("*");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
