package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.comparator.LabelNodeComparator;
import dev.turingcomplete.asmtestkit.assertion.option.AssertOption;
import dev.turingcomplete.asmtestkit.assertion.representation.LabelNodeRepresentation;
import org.objectweb.asm.tree.LabelNode;

public class LabelNodeAssert extends AsmAssert<LabelNodeAssert, LabelNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public LabelNodeAssert(LabelNode actual, AssertOption... assertOptions) {
    super(actual, LabelNodeAssert.class, LabelNode.class, "Label", assertOptions);

    info.useRepresentation(LabelNodeRepresentation.INSTANCE);
    //noinspection ResultOfMethodCallIgnored
    usingComparator(LabelNodeComparator.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //
  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
