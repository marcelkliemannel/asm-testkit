package dev.turingcomplete.asmtestkit.assertion;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.TypePath;

import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TypePathAssertTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testIsEqualTo() {
    assertThat(TypePath.fromString("*"))
            .isEqualTo(TypePath.fromString("*"));

    assertThatThrownBy(() -> assertThat(TypePath.fromString("*")).isEqualTo(TypePath.fromString("[1;")))
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Type path: *] \n" +
                        "expected: [1;\n" +
                        " but was: *\n" +
                        "when comparing values using TypePathComparator");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
