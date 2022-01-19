package dev.turingcomplete.asmtestkit.assertion;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.ParameterNode;

class ParameterNodeAssertTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testIsEqualTo() {
    var first = new ParameterNode("a", 16);
    var second = new ParameterNode("a", 16);

    AsmAssertions.assertThat(first).isEqualTo(second);
  }

  @Test
  void testIsEqualToName() {
    var first = new ParameterNode("a", 16);
    var second = new ParameterNode("b", 16);

    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThat(first).isEqualTo(second))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Parameter: a > Has equal name] \n" +
                          "expected: \"b\"\n" +
                          " but was: \"a\"");
  }

  @Test
  void testIsEqualToAccess() {
    var first = new ParameterNode("a", 16);
    var second = new ParameterNode("a", 4112);

    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThat(first).isEqualTo(second))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Parameter: a > Has equal access > Has equal access values] \n" +
                          "Expecting actual:\n" +
                          "  [\"final\"]\n" +
                          "to contain exactly in any order:\n" +
                          "  [\"final\", \"synthetic\"]\n" +
                          "but could not find the following elements:\n" +
                          "  [\"synthetic\"]\n");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
