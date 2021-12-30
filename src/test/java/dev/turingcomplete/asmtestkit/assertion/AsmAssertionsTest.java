package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.__helper.DummyAttribute;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class AsmAssertionsTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testAssertThatAttributes() {
    // Test comparator
    AsmAssertions.assertThatAttributes(List.of(new DummyAttribute("A"), new DummyAttribute("A")))
                 .containsExactlyInAnyOrderElementsOf(List.of(new DummyAttribute("A"), new DummyAttribute("A")));

    // Test representation
    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThatAttributes(List.of(new DummyAttribute("A"), new DummyAttribute("A")))
                                                     .containsExactlyInAnyOrderElementsOf(List.of(new DummyAttribute("C"), new DummyAttribute("D"))))
              .isInstanceOf(AssertionError.class)
              .hasMessage("\nExpecting actual:\n" +
                          "  [Anull, Anull]\n" +
                          "to contain exactly in any order:\n" +
                          "  [Cnull, Dnull]\n" +
                          "elements not found:\n" +
                          "  [Cnull, Dnull]\n" +
                          "and elements not expected:\n" +
                          "  [Anull, Anull]\n" +
                          "when comparing values using AttributeComparator");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
