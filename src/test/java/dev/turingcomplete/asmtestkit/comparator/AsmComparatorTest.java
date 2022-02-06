package dev.turingcomplete.asmtestkit.comparator;

import dev.turingcomplete.asmtestkit.comparator.AsmComparator;
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
            .isLessThan(0);

    assertThat(dummyAsmComparator.compare("Left", null))
            .isGreaterThan(0);
  }

  @Test
  void testWrongElementType() {
    var dummyAsmComparator = new DummyAsmComparator();

    //noinspection EqualsWithItself
    assertThat(dummyAsmComparator.compare(1, 1)) // Same value but wrong type
            .isLessThan(0);

    //noinspection EqualsWithItself
    assertThat(dummyAsmComparator.compare("foo", "foo")) // Same value and correct type
            .isEqualTo(0);

    assertThat(dummyAsmComparator.compare("Left", 1))
            .isLessThan(0);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  private static class DummyAsmComparator extends AsmComparator<String> {

    protected DummyAsmComparator() {
      super(DummyAsmComparator.class, String.class);
    }

    @Override
    protected int doCompare(String first, String second) {
      return 0;
    }
  }
}
