package dev.turingcomplete.asmtestkit.assertion.comperator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AsmComparatorTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testNullArguments() {
    //noinspection EqualsWithItself
    assertThat(new DummyAsmComparator().compare(null, null))
            .isEqualTo(0);

    assertThat(new DummyAsmComparator().compare(null, "Right"))
            .isLessThanOrEqualTo(-1);

    assertThat(new DummyAsmComparator().compare("Left", null))
            .isGreaterThanOrEqualTo(1);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  private static class DummyAsmComparator extends AsmComparator<Object> {

    @Override
    protected int doCompare(Object first, Object second) {
      return 0;
    }
  }
}
