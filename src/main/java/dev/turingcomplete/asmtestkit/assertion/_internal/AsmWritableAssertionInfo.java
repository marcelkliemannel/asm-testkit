package dev.turingcomplete.asmtestkit.assertion._internal;

import dev.turingcomplete.asmtestkit.assertion.DefaultLabelIndexLookup;
import dev.turingcomplete.asmtestkit.assertion.LabelIndexLookup;
import dev.turingcomplete.asmtestkit.representation.AbstractWithLabelIndexAsmRepresentation;
import dev.turingcomplete.asmtestkit.representation._internal.WithLabelIndexRepresentationAdapter;
import org.assertj.core.api.WritableAssertionInfo;
import org.assertj.core.presentation.Representation;

public final class AsmWritableAssertionInfo extends WritableAssertionInfo {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final LabelIndexLookup labelIndexLookup = DefaultLabelIndexLookup.create();

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public AsmWritableAssertionInfo(Representation defaultRepresentation) {
    if (defaultRepresentation != null) {
      useRepresentation(defaultRepresentation);
    }
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  public void useLabelIndexLookup(LabelIndexLookup labelIndexLookup) {
    this.labelIndexLookup.add(labelIndexLookup);
  }

  public LabelIndexLookup labelIndexLookup() {
    return labelIndexLookup;
  }

  @Override
  public void useRepresentation(Representation newRepresentation) {
    if (newRepresentation instanceof AbstractWithLabelIndexAsmRepresentation) {
      var withLabelIndexRepresentation = (AbstractWithLabelIndexAsmRepresentation<?>) newRepresentation;
      newRepresentation = new WithLabelIndexRepresentationAdapter<>(withLabelIndexRepresentation, labelIndexLookup);
    }

    super.useRepresentation(newRepresentation);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
