package dev.turingcomplete.asmtestkit.assertion.comperator;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.TypePath;

import static dev.turingcomplete.asmtestkit.assertion.comperator.TypePathComparator.instance;
import static org.assertj.core.api.Assertions.assertThat;

class TypePathComparatorTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCompare() {
    assertThat(instance().compare(TypePath.fromString("*"), TypePath.fromString("*")))
            .isEqualTo(0);

    assertThat(instance().compare(TypePath.fromString("*"), TypePath.fromString("[1;")))
            .isLessThanOrEqualTo(-1);

    assertThat(instance().compare(TypePath.fromString("[1;"), TypePath.fromString("*")))
            .isGreaterThanOrEqualTo(1);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
