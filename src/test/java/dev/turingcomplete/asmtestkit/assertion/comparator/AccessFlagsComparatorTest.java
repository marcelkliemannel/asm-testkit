package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.node.AccessFlags;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Opcodes;

import static dev.turingcomplete.asmtestkit.assertion.comparator.AccessFlagsComparator.INSTANCE;
import static org.assertj.core.api.Assertions.assertThat;

class AccessFlagsComparatorTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCompare() {
    assertThat(INSTANCE.compare(AccessFlags.forField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL), AccessFlags.forField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL)))
            .isEqualTo(0);

    assertThat(INSTANCE.compare(AccessFlags.forField(Opcodes.ACC_PUBLIC), AccessFlags.forField(Opcodes.ACC_FINAL)))
            .isLessThanOrEqualTo(-1);

    assertThat(INSTANCE.compare(AccessFlags.forField(Opcodes.ACC_FINAL), AccessFlags.forField(Opcodes.ACC_PUBLIC)))
            .isGreaterThanOrEqualTo(1);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
