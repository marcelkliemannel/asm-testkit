package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils;
import dev.turingcomplete.asmtestkit.node.AnnotationDefaultValue;
import org.junit.jupiter.api.Test;

import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AnnotationDefaultValueAssertTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testIsEqualTo() {
    assertThat(AnnotationDefaultValue.create("foo"))
            .isEqualTo(AnnotationDefaultValue.create("foo"));

    assertThatThrownBy(() -> assertThat(AnnotationDefaultValue.create("foo")).isEqualTo(AnnotationDefaultValue.create("bar")))
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Annotation default value: \"foo\"] \n" +
                        "expected: \"bar\"\n" +
                        " but was: \"foo\"\n" +
                        "when comparing values using AnnotationDefaultValueComparator");
  }

  @Test
  void testIsEqualToAnnotation() {
    AnnotationDefaultValue first = AnnotationDefaultValue.create(AnnotationNodeUtils.createAnnotationNode(Deprecated.class, "forRemoval", true));
    AnnotationDefaultValue second = AnnotationDefaultValue.create(AnnotationNodeUtils.createAnnotationNode(Deprecated.class, "forRemoval", false));

    assertThat(first)
            .isEqualTo(first);

    assertThatThrownBy(() -> assertThat(first).isEqualTo(second))
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Annotation default value: @java.lang.Deprecated(forRemoval=true)] \n" +
                        "expected: @java.lang.Deprecated(forRemoval=false)\n" +
                        " but was: @java.lang.Deprecated(forRemoval=true)\n" +
                        "when comparing values using AnnotationDefaultValueComparator");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
