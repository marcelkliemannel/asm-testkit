package dev.turingcomplete.asmtestkit.assertion.comparator;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.Opcodes;

import static dev.turingcomplete.asmtestkit.assertion.comparator.AccessComparator.INSTANCE;
import static org.assertj.core.api.Assertions.assertThat;

class AccessComparatorTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCompare() {
    //noinspection EqualsWithItself
    assertThat(INSTANCE.compare(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL, Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL))
            .isEqualTo(0);

    assertThat(INSTANCE.compare(Opcodes.ACC_PUBLIC, Opcodes.ACC_FINAL))
            .isLessThanOrEqualTo(-1);

    assertThat(INSTANCE.compare(Opcodes.ACC_FINAL, Opcodes.ACC_PUBLIC))
            .isGreaterThanOrEqualTo(1);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
