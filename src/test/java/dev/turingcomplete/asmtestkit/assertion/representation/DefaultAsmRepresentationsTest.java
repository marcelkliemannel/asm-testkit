package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils;
import dev.turingcomplete.asmtestkit.assertion.__helper.DummyAttribute;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.stream.Stream;

import static dev.turingcomplete.asmtestkit.assertion.representation.DefaultAsmRepresentations.INSTANCE;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test that all {@link SingleAsmRepresentation} are registered.
 */
class DefaultAsmRepresentationsTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @ParameterizedTest
  @MethodSource("testFallbackToStringOfArguments")
  void testToStringOf(Object object, String expectedRepresentation) {
    assertThat(INSTANCE.toStringOf(object))
            .isEqualTo(expectedRepresentation);
  }

  @Test
  void testUnambiguousToStringOf() {
    AnnotationNode first = AnnotationNodeUtils.createAnnotationNode(Deprecated.class);
    AnnotationNode second = AnnotationNodeUtils.createAnnotationNode(Deprecated.class);

    assertThat(INSTANCE.unambiguousToStringOf(first))
            .isEqualTo(INSTANCE.unambiguousToStringOf(first));

    assertThat(INSTANCE.unambiguousToStringOf(first))
            .isNotEqualTo(INSTANCE.unambiguousToStringOf(second));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private static Stream<Arguments> testFallbackToStringOfArguments() {
    return Stream.of(
            Arguments.of(new AnnotationNode("Lfoo.A;"), "@foo.A"),
            Arguments.of(new DummyAttribute("A", "Content"), "AContent")
    );
    // todo add all asm
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  private static class CustomRepresentation extends AbstractSingleAsmRepresentation<Integer> {

    protected CustomRepresentation() {
      super(Integer.class);
    }

    @Override
    protected String doToStringOf(Integer object) {
      return "Foo" + object;
    }
  }
}
