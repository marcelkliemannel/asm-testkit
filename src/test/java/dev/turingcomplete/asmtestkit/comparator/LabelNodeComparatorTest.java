package dev.turingcomplete.asmtestkit.comparator;

import dev.turingcomplete.asmtestkit.comparator.LabelNodeComparator;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.LabelNode;

import static org.assertj.core.api.Assertions.assertThat;

class LabelNodeComparatorTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCompare() {
    var firstLabelNode = new LabelNode();

    var label = new Label();
    var secondLabelNode = new LabelNode(label);
    var thirdLabelNode = new LabelNode(label);

    assertThat(LabelNodeComparator.INSTANCE.compare(firstLabelNode, secondLabelNode))
            .isNotEqualTo(0);

    assertThat(LabelNodeComparator.INSTANCE.compare(secondLabelNode, thirdLabelNode))
            .isEqualTo(0);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
