package dev.turingcomplete.asmtestkit.representation;

import dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils;
import dev.turingcomplete.asmtestkit.representation.AnnotationNodeRepresentation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AnnotationNodeRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testToStringOf() {
    assertThat(AnnotationNodeRepresentation.INSTANCE.toStringOf(AnnotationNodeUtils.createAnnotationNode(Deprecated.class, "since", "1.2", "forRemoval", true)))
            .isEqualTo("@java.lang.Deprecated(since=\"1.2\", forRemoval=true)");

    assertThat(AnnotationNodeRepresentation.INSTANCE.toStringOf(AnnotationNodeUtils.createAnnotationNode(Deprecated.class)))
            .isEqualTo("@java.lang.Deprecated");
  }

  @Test
  void testHideValuesToStringOf() {
    assertThat(AnnotationNodeRepresentation.create().hideValues().toStringOf(AnnotationNodeUtils.createAnnotationNode(Deprecated.class, "since", "1.2", "forRemoval", true)))
            .isEqualTo("@java.lang.Deprecated");
  }

  @Test
  void testToSimplifiedRepresentation() {
    assertThat(AnnotationNodeRepresentation.create().toSimplifiedStringOf(AnnotationNodeUtils.createAnnotationNode(Deprecated.class, "since", "1.2", "forRemoval", true)))
            .isEqualTo("@java.lang.Deprecated");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
