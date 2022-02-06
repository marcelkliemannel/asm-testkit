package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils;
import dev.turingcomplete.asmtestkit.node.AnnotationDefault;
import org.junit.jupiter.api.Test;

import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AnnotationDefaultAssertTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testIsEqualTo() {
    assertThat(AnnotationDefault.create("foo"))
            .isEqualTo(AnnotationDefault.create("foo"));

    assertThatThrownBy(() -> assertThat(AnnotationDefault.create("foo")).isEqualTo(AnnotationDefault.create("bar")))
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Annotation default: \"foo\"] \n" +
                        "expected: \"bar\"\n" +
                        " but was: \"foo\"\n" +
                        "when comparing values using AnnotationDefaultValueComparator");
  }

  @Test
  void testIsEqualToAnnotation() {
    AnnotationDefault first = AnnotationDefault.create(AnnotationNodeUtils.createAnnotationNode(Deprecated.class, "forRemoval", true));
    AnnotationDefault second = AnnotationDefault.create(AnnotationNodeUtils.createAnnotationNode(Deprecated.class, "forRemoval", false));

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
