package dev.turingcomplete.asmtestkit.assertion.representation;

import org.objectweb.asm.Label;
import org.objectweb.asm.tree.LabelNode;

import java.util.Map;

/**
 * Creates a {@link String} representation of a {@link LabelNode}.
 *
 * The {@link #toStringOf(String)} implementation will output an {@code L}
 * followed by the hash code of the {@link LabelNode#getLabel()}. To map the
 * {@link Label} to a consistent name use
 * {@link #toStringOf(LabelNode, Map)}.
 */
public class LabelNodeRepresentation extends AsmRepresentation<LabelNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  /**
   * A reusable {@link LabelNodeRepresentation} instance.
   */
  public static final LabelNodeRepresentation INSTANCE = new LabelNodeRepresentation();


  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public LabelNodeRepresentation() {
    super(LabelNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  protected String doToStringOf(LabelNode labelNode) {
    return toStringOf(labelNode, Map.of());
  }

  public String toStringOf(LabelNode labelNode, Map<Label, String> labelNames) {
    return labelNames.getOrDefault(labelNode.getLabel(), "L" + labelNode.getLabel().hashCode());
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
