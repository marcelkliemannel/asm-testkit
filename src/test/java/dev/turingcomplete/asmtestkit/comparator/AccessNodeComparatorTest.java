package dev.turingcomplete.asmtestkit.comparator;

import dev.turingcomplete.asmtestkit.node.AccessNode;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Opcodes;

import static dev.turingcomplete.asmtestkit.comparator.AccessNodeComparator.INSTANCE;
import static org.assertj.core.api.Assertions.assertThat;

class AccessNodeComparatorTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCompare() {
    assertThat(INSTANCE.compare(AccessNode.forField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL), AccessNode.forField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL)))
            .isEqualTo(0);

    assertThat(INSTANCE.compare(AccessNode.forField(Opcodes.ACC_PUBLIC), AccessNode.forField(Opcodes.ACC_FINAL)))
            .isLessThanOrEqualTo(-1);

    assertThat(INSTANCE.compare(AccessNode.forField(Opcodes.ACC_FINAL), AccessNode.forField(Opcodes.ACC_PUBLIC)))
            .isGreaterThanOrEqualTo(1);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
