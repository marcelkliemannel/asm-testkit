package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.__helper.DummyAttribute;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AttributeAssertTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testIsEqualTo() {
    AsmAssertions.assertThat(new DummyAttribute("A"))
                 .isEqualTo(new DummyAttribute("A"));

    AsmAssertions.assertThat(new DummyAttribute("A", "1"))
                 .isEqualTo(new DummyAttribute("A", "1"));

    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThat(new DummyAttribute("A", "1")).isEqualTo(new DummyAttribute("B", "2")))
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Attribute: A] \n" +
                        "expected: B2\n" +
                        " but was: A1\n" +
                        "when comparing values using AttributeComparator");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
