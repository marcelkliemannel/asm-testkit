package dev.turingcomplete.asmtestkit.assertion;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.LabelNode;

import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LabelNodeAssertTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testIsEqualTo() {
    var firstLabelNode = new LabelNode();

    var label = new Label();
    var secondLabelNode = new LabelNode(label);
    var thirdLabelNode = new LabelNode(label);

    assertThat(secondLabelNode)
            .isEqualTo(thirdLabelNode);

    assertThatThrownBy(() -> assertThat(firstLabelNode).isEqualTo(secondLabelNode))
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Label] \n" +
                        "expected: L" + secondLabelNode.getLabel().hashCode() + "\n" +
                        " but was: L" + firstLabelNode.getLabel().hashCode() + "\n" +
                        "when comparing values using LabelNodeComparator");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
