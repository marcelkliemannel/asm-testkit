package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.AnnotationNode;

import static org.assertj.core.api.Assertions.assertThat;

class AbstractAnnotationNodeRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testHideValuesCreateRepresentation() {
    assertThat(new DummyAbstractAnnotationNodeRepresentation().hideValues().toStringOf(AnnotationNodeUtils.createAnnotationNode(Deprecated.class, "since", "1.2", "forRemoval", true)))
            .isEqualTo("@java.lang.Deprecated");
  }

  @Test
  void testToSimplifiedRepresentation() {
    assertThat(new DummyAbstractAnnotationNodeRepresentation().toSimplifiedRepresentation(AnnotationNodeUtils.createAnnotationNode(Deprecated.class, "since", "1.2", "forRemoval", true)))
            .isEqualTo("@java.lang.Deprecated");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  private static class DummyAbstractAnnotationNodeRepresentation
          extends AbstractAnnotationNodeRepresentation<DummyAbstractAnnotationNodeRepresentation, AnnotationNode> {

    protected DummyAbstractAnnotationNodeRepresentation() {
      super(AnnotationNode.class);
    }
  }
}
