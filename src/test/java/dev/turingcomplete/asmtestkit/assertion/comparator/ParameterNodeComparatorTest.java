package dev.turingcomplete.asmtestkit.assertion.comparator;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.ParameterNode;

import static dev.turingcomplete.asmtestkit.assertion.comparator.ParameterNodeComparator.INSTANCE;
import static org.assertj.core.api.Assertions.assertThat;

class ParameterNodeComparatorTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCompare() {
    ParameterNode first = new ParameterNode("a", 789);
    ParameterNode second = new ParameterNode("b", 789);
    ParameterNode third = new ParameterNode("b", 99999);

    assertThat(INSTANCE.doCompare(first, first))
              .isEqualTo(0);

    assertThat(INSTANCE.doCompare(first, second))
            .isEqualTo(-1);

    assertThat(INSTANCE.doCompare(second, third))
            .isEqualTo(-1);

    assertThat(INSTANCE.doCompare(second, first))
            .isEqualTo(1);

    assertThat(INSTANCE.doCompare(third, second))
            .isEqualTo(1);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
