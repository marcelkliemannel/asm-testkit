package dev.turingcomplete.asmtestkit.representation;

import dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils;
import dev.turingcomplete.asmtestkit.node.AnnotationDefaultNode;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.AnnotationNode;

import static dev.turingcomplete.asmtestkit.representation.AnnotationDefaultValueRepresentation.INSTANCE;
import static org.assertj.core.api.Assertions.assertThat;

class AnnotationDefaultNodeRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testToStringOf() {
    assertThat(INSTANCE.toStringOf(AnnotationDefaultNode.create(5)))
              .isEqualTo("5");

    AnnotationNode annotationNode = AnnotationNodeUtils.createAnnotationNode(Deprecated.class);
    AnnotationDefaultNode annotationDefaultNode = AnnotationDefaultNode.create(annotationNode);

    assertThat(INSTANCE.toStringOf(annotationDefaultNode))
            .isEqualTo(AnnotationNodeRepresentation.INSTANCE.toStringOf(annotationNode));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
