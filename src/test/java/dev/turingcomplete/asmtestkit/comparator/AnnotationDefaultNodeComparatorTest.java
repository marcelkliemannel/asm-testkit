package dev.turingcomplete.asmtestkit.comparator;

import dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils;
import dev.turingcomplete.asmtestkit.node.AnnotationDefaultNode;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.AnnotationNode;

import static dev.turingcomplete.asmtestkit.comparator.AnnotationDefaultValueComparator.INSTANCE;
import static org.assertj.core.api.Assertions.assertThat;

class AnnotationDefaultNodeComparatorTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCompare() {
    assertThat(INSTANCE.compare(AnnotationDefaultNode.create(5), AnnotationDefaultNode.create(5)))
            .isEqualTo(0);

    assertThat(INSTANCE.compare(AnnotationDefaultNode.create(5), AnnotationDefaultNode.create(6)))
            .isLessThan(0);

    assertThat(INSTANCE.compare(AnnotationDefaultNode.create(6), AnnotationDefaultNode.create(5)))
            .isGreaterThan(0);
  }

  @Test
  void testCompareAnnotationNode() {
    AnnotationNode first = AnnotationNodeUtils.createAnnotationNode(Deprecated.class, "forRemoval", true);
    AnnotationNode second = AnnotationNodeUtils.createAnnotationNode(Deprecated.class, "forRemoval", false);

    //noinspection EqualsWithItself
    assertThat(INSTANCE.compare(AnnotationDefaultNode.create(first), AnnotationDefaultNode.create(first)))
            .isEqualTo(AnnotationNodeComparator.INSTANCE.compare(first, first));

    assertThat(INSTANCE.compare(AnnotationDefaultNode.create(first), AnnotationDefaultNode.create(second)))
            .isEqualTo(AnnotationNodeComparator.INSTANCE.compare(first, second));

    assertThat(INSTANCE.compare(AnnotationDefaultNode.create(second), AnnotationDefaultNode.create(first)))
            .isEqualTo(AnnotationNodeComparator.INSTANCE.compare(second, first));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
