package dev.turingcomplete.asmtestkit.assertion;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;

import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InstructionAssertTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testIsEqual() {
    assertThat(new IntInsnNode(Opcodes.BIPUSH, 5))
            .isEqualTo(new IntInsnNode(Opcodes.BIPUSH, 5));

    assertThat(new LdcInsnNode("foo"))
            .isEqualTo(new LdcInsnNode("foo"));

    assertThatThrownBy(() -> assertThat(new LdcInsnNode("foo"))
                                       .isEqualTo(new IntInsnNode(Opcodes.BIPUSH, 5)))
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Instruction: LDC \"foo\"] \n" +
                        "expected: BIPUSH 5 // opcode: 16\n" +
                        " but was: LDC \"foo\" // opcode: 18\n" +
                        "when comparing values using InstructionComparator");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
