package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.__helper.DummyAttribute;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.AnnotationNode;

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

  @Test
  void testAssertThatAnnotationNodes() {
    // Test comparator
    AsmAssertions.assertThatAnnotationNodes(List.of(new AnnotationNode("LA;"), new AnnotationNode("LA;")))
                 .containsExactlyInAnyOrderElementsOf(List.of(new AnnotationNode("LA;"), new AnnotationNode("LA;")));

    // Test representation
    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThatAnnotationNodes(List.of(new AnnotationNode("LA;"), new AnnotationNode("LB;")))
                                                     .containsExactlyInAnyOrderElementsOf(List.of(new AnnotationNode("LB;"), new AnnotationNode("LC;"))))
              .isInstanceOf(AssertionError.class)
              .hasMessage("\nExpecting actual:\n" +
                          "  [@A, @B]\n" +
                          "to contain exactly in any order:\n" +
                          "  [@B, @C]\n" +
                          "elements not found:\n" +
                          "  [@C]\n" +
                          "and elements not expected:\n" +
                          "  [@A]\n" +
                          "when comparing values using AnnotationNodeComparator");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
