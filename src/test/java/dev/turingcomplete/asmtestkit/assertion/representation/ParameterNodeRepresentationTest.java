package dev.turingcomplete.asmtestkit.assertion.representation;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.ParameterNode;

import static dev.turingcomplete.asmtestkit.assertion.representation.ParameterNodeRepresentation.INSTANCE;
import static org.assertj.core.api.Assertions.assertThat;

class ParameterNodeRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testToStringOf() {
    ParameterNode first = new ParameterNode("a", 16);

    assertThat(INSTANCE.toStringOf(first)).isEqualTo("(16) final a");
  }

  @Test
  void testToSimplifiedStringOf() {
    ParameterNode first = new ParameterNode("a", 16);

    assertThat(INSTANCE.toSimplifiedStringOf(first)).isEqualTo("a");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
