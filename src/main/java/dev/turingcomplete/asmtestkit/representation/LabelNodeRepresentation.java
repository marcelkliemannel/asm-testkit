package dev.turingcomplete.asmtestkit.representation;

import dev.turingcomplete.asmtestkit.assertion.DefaultLabelIndexLookup;
import dev.turingcomplete.asmtestkit.assertion.LabelIndexLookup;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.LabelNode;

/**
 * An AssertJ {@link Representation} for a {@link LabelNode}.
 *
 * The {@link #toStringOf(Object)} implementation will output a {@code L}
 * followed by the hash code of the {@link LabelNode#getLabel()}. To map the
 * {@link Label} to a consistent name use
 * {@link #doToStringOf(LabelNode, LabelIndexLookup)}.
 */
public class LabelNodeRepresentation extends AbstractWithLabelNamesAsmRepresentation<LabelNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  /**
   * A reusable {@link LabelNodeRepresentation} instance.
   */
  public static final LabelNodeRepresentation INSTANCE = create();

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
    return doToStringOf(labelNode, DefaultLabelIndexLookup.create());
  }

  @Override
  public String doToStringOf(LabelNode labelNode, LabelIndexLookup labelIndexLookup) {
    return "L" + labelIndexLookup.find(labelNode.getLabel()).orElse(labelNode.getLabel().hashCode());
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
