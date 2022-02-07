package dev.turingcomplete.asmtestkit.assertion;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InnerClassNode;

import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InnerClassNodeAssertTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testIsEqualToName() {
    var first = new InnerClassNode("A", null, null, 0);
    var second = new InnerClassNode("B", null, null, 0);

    assertThat(first)
            .isEqualTo(first);

    assertThatThrownBy(() -> assertThat(first).isEqualTo(second))
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Inner class: A > Has equal name] \n" +
                        "expected: B\n" +
                        " but was: A\n" +
                        "when comparing values using TypeComparator");
  }

  @Test
  void testIsEqualToOuterName() {
    var first = new InnerClassNode(null, "A", null, 0);
    var second = new InnerClassNode(null, "B", null, 0);

    assertThat(first)
            .isEqualTo(first);

    assertThatThrownBy(() -> assertThat(first).isEqualTo(second))
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Inner class: null > Has equal outer name] \n" +
                        "expected: B\n" +
                        " but was: A\n" +
                        "when comparing values using TypeComparator");
  }

  @Test
  void testIsEqualToInnerName() {
    var first = new InnerClassNode(null, null, "A", 0);
    var second = new InnerClassNode(null,null, "B", 0);

    assertThat(first)
            .isEqualTo(first);

    assertThatThrownBy(() -> assertThat(first).isEqualTo(second))
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Inner class: null > Has equal inner name] \n" +
                        "expected: \"B\"\n" +
                        " but was: \"A\"");
  }

  @Test
  void testIsEqualToAccess() {
    var first = new InnerClassNode(null, null, null, 0);
    var second = new InnerClassNode(null, null, null, Opcodes.ACC_PUBLIC);

    assertThat(first)
            .isEqualTo(first);

    assertThatThrownBy(() -> assertThat(first).isEqualTo(second))
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Inner class: null > Has equal access > Has equal access values] \n" +
                        "Expecting actual:\n" +
                        "  []\n" +
                        "to contain exactly in any order:\n" +
                        "  [\"public\"]\n" +
                        "but could not find the following elements:\n" +
                        "  [\"public\"]\n");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
