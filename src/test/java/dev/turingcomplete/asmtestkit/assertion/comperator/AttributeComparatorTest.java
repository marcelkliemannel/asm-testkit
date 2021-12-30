package dev.turingcomplete.asmtestkit.assertion.comperator;

import dev.turingcomplete.asmtestkit.assertion.__helper.DummyAttribute;
import org.junit.jupiter.api.Test;

import static dev.turingcomplete.asmtestkit.assertion.comperator.AttributeComparator.instance;
import static org.assertj.core.api.Assertions.assertThat;

class AttributeComparatorTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCompareName() {
    assertThat(instance().compare(new DummyAttribute("A"), new DummyAttribute("A")))
            .isEqualTo(0);

    assertThat(instance().compare(new DummyAttribute("A"), new DummyAttribute("B")))
            .isLessThanOrEqualTo(-1);

    assertThat(instance().compare(new DummyAttribute("B"), new DummyAttribute("A")))
            .isGreaterThanOrEqualTo(1);
  }

  @Test
  void testCompareContent() {
    assertThat(instance().compare(new DummyAttribute("A", "1"), new DummyAttribute("A", "1")))
            .isEqualTo(0);

    assertThat(instance().compare(new DummyAttribute("A", "1"), new DummyAttribute("A", "2")))
            .isLessThanOrEqualTo(-1);

    assertThat(instance().compare(new DummyAttribute("A", "3"), new DummyAttribute("A", "2")))
            .isGreaterThanOrEqualTo(1);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
