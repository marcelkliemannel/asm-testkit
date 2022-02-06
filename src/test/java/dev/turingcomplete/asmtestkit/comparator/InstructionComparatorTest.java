package dev.turingcomplete.asmtestkit.comparator;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;

import static dev.turingcomplete.asmtestkit.comparator.InstructionComparator.INSTANCE;
import static org.assertj.core.api.Assertions.assertThat;

class InstructionComparatorTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCompare() {
    assertThat(INSTANCE.compare(new IntInsnNode(Opcodes.BIPUSH, 5), new IntInsnNode(Opcodes.BIPUSH, 5)))
            .isEqualTo(0);

    assertThat(INSTANCE.compare(new IntInsnNode(Opcodes.BIPUSH, 5), new LdcInsnNode("foo")))
            .isLessThanOrEqualTo(-1);

    assertThat(INSTANCE.compare(new LdcInsnNode("foo"), new IntInsnNode(Opcodes.BIPUSH, 5)))
            .isGreaterThanOrEqualTo(1);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
