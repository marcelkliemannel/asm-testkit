package dev.turingcomplete.asmtestkit.assertion.representation;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.LabelNode;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LabelNodeRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testToStringOf() {
    var label = new Label();
    assertThat(LabelNodeRepresentation.INSTANCE.doToStringOf(new LabelNode(label)))
            .isEqualTo("L" + label.hashCode());
  }

  @Test
  void testToStringOfWithNames() {
    var label = new Label();
    assertThat(LabelNodeRepresentation.INSTANCE.toStringOf(new LabelNode(label), Map.of(label, "L1")))
            .isEqualTo("L1");

    var unknownLabel = new Label();
    assertThat(LabelNodeRepresentation.INSTANCE.toStringOf(new LabelNode(unknownLabel), Map.of(label, "L1")))
            .isEqualTo("L" + unknownLabel.hashCode());
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
