package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils;
import dev.turingcomplete.asmtestkit.node.AnnotationDefaultNode;
import org.junit.jupiter.api.Test;

import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AnnotationDefaultNodeAssertTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testIsEqualTo() {
    assertThat(AnnotationDefaultNode.create("foo"))
            .isEqualTo(AnnotationDefaultNode.create("foo"));

    assertThatThrownBy(() -> assertThat(AnnotationDefaultNode.create("foo")).isEqualTo(AnnotationDefaultNode.create("bar")))
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Annotation default: \"foo\"] \n" +
                        "expected: \"bar\"\n" +
                        " but was: \"foo\"\n" +
                        "when comparing values using AnnotationDefaultValueComparator");
  }

  @Test
  void testIsEqualToAnnotation() {
    AnnotationDefaultNode first = AnnotationDefaultNode.create(AnnotationNodeUtils.createAnnotationNode(Deprecated.class, "forRemoval", true));
    AnnotationDefaultNode second = AnnotationDefaultNode.create(AnnotationNodeUtils.createAnnotationNode(Deprecated.class, "forRemoval", false));

    assertThat(first)
            .isEqualTo(first);

    assertThatThrownBy(() -> assertThat(first).isEqualTo(second))
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Annotation default: @java.lang.Deprecated(forRemoval=true)] \n" +
                        "expected: @java.lang.Deprecated(forRemoval=false)\n" +
                        " but was: @java.lang.Deprecated(forRemoval=true)\n" +
                        "when comparing values using AnnotationDefaultValueComparator");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
