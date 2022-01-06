package dev.turingcomplete.asmtestkit.assertion;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.Type;

import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TypeAssertTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testIsEqualTo() {
    assertThat(Type.getType(String.class))
            .isEqualTo(Type.getType(String.class));

    assertThatThrownBy(() -> assertThat(Type.getType(String.class)).isEqualTo(Type.getType(Integer.class)))
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Type: java.lang.String] \n" +
                        "expected: java.lang.Integer\n" +
                        " but was: java.lang.String\n" +
                        "when comparing values using TypeComparator");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
