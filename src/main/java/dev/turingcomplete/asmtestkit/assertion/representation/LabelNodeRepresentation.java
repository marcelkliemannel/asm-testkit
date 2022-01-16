package dev.turingcomplete.asmtestkit.assertion.representation;

import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.LabelNode;

import java.util.Map;

/**
 * An AssertJ {@link Representation} for a {@link LabelNode}.
 *
 * The {@link #toStringOf(String)} implementation will output a {@code L}
 * followed by the hash code of the {@link LabelNode#getLabel()}. To map the
 * {@link Label} to a consistent name use
 * {@link #doToStringOf(LabelNode, Map)}.
 */
public class LabelNodeRepresentation extends WithLabelNamesRepresentation<LabelNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  /**
   * A reusable {@link LabelNodeRepresentation} instance.
   */
  public static final LabelNodeRepresentation INSTANCE = new LabelNodeRepresentation();


  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected LabelNodeRepresentation() {
    super(LabelNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link LabelNodeRepresentation} instance.
   *
   * @return a new {@link LabelNodeRepresentation}; never null;
   */
  public static LabelNodeRepresentation create() {
    return new LabelNodeRepresentation();
  }

  @Override
  protected String doToStringOf(LabelNode labelNode) {
    return doToStringOf(labelNode, Map.of());
  }

  @Override
  public String doToStringOf(LabelNode labelNode, Map<Label, String> labelNames) {
    return labelNames.getOrDefault(labelNode.getLabel(), "L" + labelNode.getLabel().hashCode());
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
