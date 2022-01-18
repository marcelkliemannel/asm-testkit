package dev.turingcomplete.asmtestkit.assertion.comparator;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.TryCatchBlockNode;

import static dev.turingcomplete.asmtestkit.assertion.comparator.TryCatchBlockNodeComparator.INSTANCE;
import static org.assertj.core.api.Assertions.assertThat;

class TryCatchBlockNodeComparatorTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCompare() {
    var firstTryCatchBlockNode = new TryCatchBlockNode(new LabelNode(new Label()), new LabelNode(new Label()), new LabelNode(new Label()), "Ljava/lang/IllegalArgumentException;");
    var secondTryCatchBlockNode = new TryCatchBlockNode(new LabelNode(new Label()), new LabelNode(new Label()), new LabelNode(new Label()), null);

    //noinspection EqualsWithItself
    assertThat(INSTANCE.compare(firstTryCatchBlockNode, firstTryCatchBlockNode))
            .isEqualTo(0);

    assertThat(INSTANCE.compare(firstTryCatchBlockNode, secondTryCatchBlockNode))
            .isLessThan(0);

    assertThat(INSTANCE.compare(secondTryCatchBlockNode, firstTryCatchBlockNode))
            .isGreaterThan(0);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
