package dev.turingcomplete.asmtestkit.assertion.representation;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class AsmRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testNullArgument() {
    assertThat(new DummyAsmRepresentation().toStringOf(null))
            .isEqualTo(null);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  private static class DummyAsmRepresentation extends AbstractAsmRepresentation<Object> {

    protected DummyAsmRepresentation() {
      super(Object.class);
    }

    @Override
    protected String doToStringOf(Object object) {
      return Objects.toString(object);
    }
  }
}
