package dev.turingcomplete.asmtestkit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class AnnotationNodeUtilsTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testValuesToMap() {
    Assertions.assertThat(AnnotationNodeUtils.annotationNodeValuesToMap(null))
              .containsExactlyInAnyOrderEntriesOf(Map.of());

    Assertions.assertThat(AnnotationNodeUtils.annotationNodeValuesToMap(List.of()))
              .containsExactlyInAnyOrderEntriesOf(Map.of());

    Assertions.assertThat(AnnotationNodeUtils.annotationNodeValuesToMap(List.of("foo", true, "bar", 1)))
              .containsExactlyInAnyOrderEntriesOf(Map.of("foo", true, "bar", 1));

    Assertions.assertThatThrownBy(() -> AnnotationNodeUtils.annotationNodeValuesToMap(List.of("foo")))
              .isInstanceOf(IllegalArgumentException.class)
              .hasMessage("There must be an even number of values.");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
