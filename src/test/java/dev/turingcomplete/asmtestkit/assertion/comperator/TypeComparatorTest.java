package dev.turingcomplete.asmtestkit.assertion.comperator;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.Type;

import static dev.turingcomplete.asmtestkit.assertion.comperator.TypeComparator.instance;
import static org.assertj.core.api.Assertions.assertThat;

class TypeComparatorTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCompare() {
    //noinspection EqualsWithItself
    assertThat(instance().compare(Type.BOOLEAN_TYPE, Type.BOOLEAN_TYPE))
            .isEqualTo(0);

    assertThat(instance().compare(Type.BOOLEAN_TYPE, Type.CHAR_TYPE))
            .isLessThanOrEqualTo(-1);

    assertThat(instance().compare(Type.CHAR_TYPE, Type.BOOLEAN_TYPE))
            .isGreaterThanOrEqualTo(1);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
