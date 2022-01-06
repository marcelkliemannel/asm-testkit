package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.__helper.DummyAttribute;
import org.junit.jupiter.api.Test;

import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AttributeAssertTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testIsEqualTo() {
    assertThat(new DummyAttribute("A"))
            .isEqualTo(new DummyAttribute("A"));

    assertThat(new DummyAttribute("A", "1"))
            .isEqualTo(new DummyAttribute("A", "1"));

    assertThatThrownBy(() -> assertThat(new DummyAttribute("A", "1")).isEqualTo(new DummyAttribute("B", "2")))
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Attribute: A1] \n" +
                        "expected: B2\n" +
                        " but was: A1\n" +
                        "when comparing values using AttributeComparator");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
