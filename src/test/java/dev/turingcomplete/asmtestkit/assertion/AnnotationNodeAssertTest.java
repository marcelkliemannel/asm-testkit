package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.option.StandardAssertOption;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils.createAnnotationNode;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThat;

class AnnotationNodeAssertTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testIsEqualTo() {
    // Equal
    assertThat(createAnnotationNode(Deprecated.class, "foo", false, "bar", 2))
            .isEqualTo(createAnnotationNode(Deprecated.class, "foo", false, "bar", 2));

    assertThat(createAnnotationNode(Deprecated.class))
            .isEqualTo(createAnnotationNode(Deprecated.class));
  }

  @Test
  void testIsEqualToDifferentDescriptor() {
    Assertions.assertThatThrownBy(() -> assertThat(createAnnotationNode(Deprecated.class))
                      .isEqualTo(createAnnotationNode(SuppressWarnings.class)))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Annotation: @java.lang.Deprecated > Has equal descriptor] \n" +
                          "expected: \"Ljava/lang/SuppressWarnings;\"\n" +
                          " but was: \"Ljava/lang/Deprecated;\"");
  }

  @Test
  void testIsEqualToDifferentValues() {
    Assertions.assertThatThrownBy(() -> assertThat(createAnnotationNode(Deprecated.class, "foo", false, "bar", 1))
                      .isEqualTo(createAnnotationNode(Deprecated.class, "foo", false, "bar", 2)))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Annotation: @java.lang.Deprecated > Has equal values] \n" +
                          "Expecting map:\n" +
                          "  {\"bar\"=1, \"foo\"=false}\n" +
                          "to contain only:\n" +
                          "  [\"bar\"=2, \"foo\"=false]\n" +
                          "map entries not found:\n" +
                          "  [\"bar\"=2]\n" +
                          "and map entries not expected:\n" +
                          "  [\"bar\"=1]\n");
  }

  @Test
  void testIsEqualToIgnoreValues() {
    assertThat(createAnnotationNode(Deprecated.class, "foo", false, "bar", 1))
            .addOption(StandardAssertOption.IGNORE_ANNOTATION_VALUES)
            .isEqualTo(createAnnotationNode(Deprecated.class, "foo", false, "bar", 2));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
