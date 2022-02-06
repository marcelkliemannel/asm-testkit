package dev.turingcomplete.asmtestkit.representation;

import dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils;
import dev.turingcomplete.asmtestkit.node.AnnotationDefault;
import dev.turingcomplete.asmtestkit.representation.AnnotationNodeRepresentation;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.AnnotationNode;

import static dev.turingcomplete.asmtestkit.representation.AnnotationDefaultValueRepresentation.INSTANCE;
import static org.assertj.core.api.Assertions.assertThat;

class AnnotationDefaultRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testToStringOf() {
    assertThat(INSTANCE.toStringOf(AnnotationDefault.create(5)))
              .isEqualTo("5");

    AnnotationNode annotationNode = AnnotationNodeUtils.createAnnotationNode(Deprecated.class);
    AnnotationDefault annotationDefault = AnnotationDefault.create(annotationNode);

    assertThat(INSTANCE.toStringOf(annotationDefault))
            .isEqualTo(AnnotationNodeRepresentation.INSTANCE.toStringOf(annotationNode));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
