package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.node.AccessNode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThat;
import static org.objectweb.asm.Opcodes.ACC_ABSTRACT;
import static org.objectweb.asm.Opcodes.ACC_FINAL;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;

class AccessNodeAssertTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testisEqualTo() {
    assertThat(AccessNode.forClass(ACC_PUBLIC + ACC_ABSTRACT))
            .isEqualTo(AccessNode.forClass(ACC_PUBLIC + ACC_ABSTRACT));

    Assertions.assertThatThrownBy(() -> assertThat(AccessNode.forClass(ACC_PUBLIC + ACC_ABSTRACT))
                      .isEqualTo(AccessNode.forClass(ACC_ABSTRACT + ACC_FINAL)))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Access: [1025: public, abstract] > Has equal access values] \n" +
                          "Expecting actual:\n" +
                          "  [\"public\", \"abstract\"]\n" +
                          "to contain exactly in any order:\n" +
                          "  [\"final\", \"abstract\"]\n" +
                          "elements not found:\n" +
                          "  [\"final\"]\n" +
                          "and elements not expected:\n" +
                          "  [\"public\"]\n");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
