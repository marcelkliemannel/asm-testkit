package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils;
import dev.turingcomplete.asmtestkit.node.AnnotationDefaultValue;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.AnnotationNode;

import static dev.turingcomplete.asmtestkit.assertion.comparator.AnnotationDefaultValueComparator.INSTANCE;
import static org.assertj.core.api.Assertions.assertThat;

class AnnotationDefaultValueComparatorTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCompare() {
    //noinspection EqualsWithItself
    assertThat(INSTANCE.compare(AnnotationDefaultValue.create(5), AnnotationDefaultValue.create(5)))
            .isEqualTo(0);

    assertThat(INSTANCE.compare(AnnotationDefaultValue.create(5), AnnotationDefaultValue.create(6)))
            .isLessThan(0);

    assertThat(INSTANCE.compare(AnnotationDefaultValue.create(6), AnnotationDefaultValue.create(5)))
            .isGreaterThan(0);
  }

  @Test
  void testCompareAnnotationNode() {
    AnnotationNode first = AnnotationNodeUtils.createAnnotationNode(Deprecated.class, "forRemoval", true);
    AnnotationNode second = AnnotationNodeUtils.createAnnotationNode(Deprecated.class, "forRemoval", false);

    //noinspection EqualsWithItself
    assertThat(INSTANCE.compare(AnnotationDefaultValue.create(first), AnnotationDefaultValue.create(first)))
            .isEqualTo(AnnotationNodeComparator.INSTANCE.compare(first, first));

    assertThat(INSTANCE.compare(AnnotationDefaultValue.create(first), AnnotationDefaultValue.create(second)))
            .isEqualTo(AnnotationNodeComparator.INSTANCE.compare(first, second));

    assertThat(INSTANCE.compare(AnnotationDefaultValue.create(second), AnnotationDefaultValue.create(first)))
            .isEqualTo(AnnotationNodeComparator.INSTANCE.compare(second, first));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
