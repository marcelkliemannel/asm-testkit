package dev.turingcomplete.asmtestkit.assertion.comparator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AsmComparatorTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testNullArguments() {
    var dummyAsmComparator = new DummyAsmComparator();

    //noinspection EqualsWithItself
    assertThat(dummyAsmComparator.compare(null, null))
            .isEqualTo(0);

    assertThat(dummyAsmComparator.compare(null, "Right"))
            .isLessThanOrEqualTo(-1);

    assertThat(dummyAsmComparator.compare("Left", null))
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
