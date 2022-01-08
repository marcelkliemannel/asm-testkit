package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.assertion.__helper.DummyAttribute;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test that all {@link AsmRepresentation} are registered.
 */
class CombinedAsmRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @ParameterizedTest
  @MethodSource("testFallbackToStringOfArguments")
  void testFallbackToStringOf(Object object, String expectedRepresentation) {
    CombinedAsmRepresentation.addAsmRepresentation(new CustomRepresentation());

    assertThat(new CombinedAsmRepresentation().fallbackToStringOf(object))
            .isEqualTo(expectedRepresentation);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private static Stream<Arguments> testFallbackToStringOfArguments() {
    return Stream.of(
            Arguments.of(new AnnotationNode("Lfoo.A;"), "@foo.A"),
            Arguments.of(new DummyAttribute("A", "Content"), "AContent"),
            Arguments.of(42, "Foo42"),
            Arguments.of("foo", "foo") // fallback to StandardRepresentation
    );
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  private static class CustomRepresentation extends AsmRepresentation<Integer> {

    protected CustomRepresentation() {
      super(Integer.class);
    }

    @Override
    protected String createRepresentation(Integer object) {
      return "Foo" + object;
    }
  }
}
