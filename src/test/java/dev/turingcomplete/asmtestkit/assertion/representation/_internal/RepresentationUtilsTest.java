package dev.turingcomplete.asmtestkit.assertion.representation._internal;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class RepresentationUtilsTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @ParameterizedTest
  @CsvSource({
          "'foo\nbar\nbaz',test,'testfoo\n    bar\n    baz'",
          "foo,test,testfoo",
          "'',test,test"
  })
  void testPrependToFirstLine(String text, String toPrepend, String expected) {
    Assertions.assertThat(RepresentationUtils.prependToFirstLine(text, toPrepend))
              .isEqualTo(expected);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
