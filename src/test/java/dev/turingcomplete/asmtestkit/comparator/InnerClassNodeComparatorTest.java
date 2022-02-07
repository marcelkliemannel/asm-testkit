package dev.turingcomplete.asmtestkit.comparator;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InnerClassNode;

import static dev.turingcomplete.asmtestkit.comparator.InnerClassNodeComparator.INSTANCE;
import static org.assertj.core.api.Assertions.assertThat;

public class InnerClassNodeComparatorTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCompare() {
    var first = new InnerClassNode("Outer$Inner1", "Outer", "Inner1", Opcodes.ACC_STATIC);
    var second = new InnerClassNode("Outer$Inner2", "Outer", "Inner2", Opcodes.ACC_STATIC);
    var third = new InnerClassNode("Outer$Inner3", "Outer", "Inner3", Opcodes.ACC_STATIC);

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

  @Test
  void testCompareWithNullValues() {
    var first = new InnerClassNode(null, null, null, 0);

    assertThat(INSTANCE.doCompare(first, first))
            .isEqualTo(0);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
