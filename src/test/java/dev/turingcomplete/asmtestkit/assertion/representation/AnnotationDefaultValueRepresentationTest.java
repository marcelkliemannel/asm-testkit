package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils;
import dev.turingcomplete.asmtestkit.node.AnnotationDefaultValue;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.AnnotationNode;

import static dev.turingcomplete.asmtestkit.assertion.representation.AnnotationDefaultValueRepresentation.INSTANCE;
import static org.assertj.core.api.Assertions.assertThat;

class AnnotationDefaultValueRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testToStringOf() {
    assertThat(INSTANCE.toStringOf(AnnotationDefaultValue.create(5)))
              .isEqualTo("5");

    AnnotationNode annotationNode = AnnotationNodeUtils.createAnnotationNode(Deprecated.class);
    AnnotationDefaultValue annotationDefaultValue = AnnotationDefaultValue.create(annotationNode);

    assertThat(INSTANCE.toStringOf(annotationDefaultValue))
            .isEqualTo(AnnotationNodeRepresentation.INSTANCE.toStringOf(annotationNode));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
