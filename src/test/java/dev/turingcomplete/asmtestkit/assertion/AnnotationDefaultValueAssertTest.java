package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.AnnotationNode;

import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatAnnotationDefaultValue;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AnnotationDefaultValueAssertTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testIsEqualTo() {
    assertThatAnnotationDefaultValue("foo")
            .isEqualTo("foo");

    assertThatThrownBy(() -> assertThatAnnotationDefaultValue("foo").isEqualTo("bar"))
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Annotation default value: foo] \n" +
                        "expected: \"bar\"\n" +
                        " but was: \"foo\"\n" +
                        "when comparing values using AnnotationDefaultValueComparator");
  }

  @Test
  void testIsEqualToAnnotation() {
    AnnotationNode first = AnnotationNodeUtils.createAnnotationNode(Deprecated.class, "forRemoval", true);
    AnnotationNode second = AnnotationNodeUtils.createAnnotationNode(Deprecated.class, "forRemoval", false);

    assertThatAnnotationDefaultValue(first)
            .isEqualTo(first);

    assertThatThrownBy(() -> assertThatAnnotationDefaultValue(first).isEqualTo(second))
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Annotation default value: @java.lang.Deprecated(forRemoval=true)] \n" +
                        "expected: @java.lang.Deprecated(forRemoval=false)\n" +
                        " but was: @java.lang.Deprecated(forRemoval=true)\n" +
                        "when comparing values using AnnotationDefaultValueComparator");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
