package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AnnotationNodeRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testStringRepresentation() {
    assertThat(AnnotationNodeRepresentation.instance().toStringOf(AnnotationNodeUtils.createAnnotationNode(Deprecated.class, "since", "1.2", "forRemoval", true)))
            .isEqualTo("@java.lang.Deprecated(since=\"1.2\", forRemoval=true)");

    assertThat(AnnotationNodeRepresentation.instance().toStringOf(AnnotationNodeUtils.createAnnotationNode(Deprecated.class)))
            .isEqualTo("@java.lang.Deprecated");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
