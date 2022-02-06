package dev.turingcomplete.asmtestkit.comparator;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.Arrays;

import static dev.turingcomplete.asmtestkit.comparator.AnnotationNodeComparator.INSTANCE;
import static org.assertj.core.api.Assertions.assertThat;

class AnnotationNodeComparatorTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCompareDescriptor() {
    assertThat(INSTANCE.compare(new AnnotationNode("LA;"), new AnnotationNode("LA;")))
            .isEqualTo(0);

    assertThat(INSTANCE.compare(new AnnotationNode("LA;"), new AnnotationNode("LB;")))
            .isLessThanOrEqualTo(-1);

    assertThat(INSTANCE.compare(new AnnotationNode("LB;"), new AnnotationNode("LA;")))
            .isGreaterThanOrEqualTo(1);
  }

  @Test
  void testCompareValues() {
    assertThat(INSTANCE.compare(createAnnotationNode("foo", true, "bar", false), createAnnotationNode("foo", true, "bar", false)))
            .isEqualTo(0);

    assertThat(INSTANCE.compare(createAnnotationNode("A", true), createAnnotationNode("B", true)))
            .isLessThanOrEqualTo(-1);

    assertThat(INSTANCE.compare(createAnnotationNode("B", true), createAnnotationNode("A", true)))
            .isGreaterThanOrEqualTo(1);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private AnnotationNode createAnnotationNode(Object ...values) {
    var annotationNode = new AnnotationNode("LA;");
    annotationNode.values = Arrays.asList(values);
    return annotationNode;
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
