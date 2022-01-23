package dev.turingcomplete.asmtestkit.assertion._internal;

import dev.turingcomplete.asmtestkit.asmutils._internal.CombinedLabelNamesLookup;
import dev.turingcomplete.asmtestkit.assertion.LabelNameLookup;
import dev.turingcomplete.asmtestkit.assertion.representation.AbstractWithLabelNamesAsmRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation._internal.WithLabelNamesRepresentationAdapter;
import org.assertj.core.api.WritableAssertionInfo;
import org.assertj.core.presentation.Representation;

public final class AsmWritableAssertionInfo extends WritableAssertionInfo {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final LabelNameLookup labelNameLookup = new CombinedLabelNamesLookup();

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public AsmWritableAssertionInfo(Representation defaultRepresentation) {
    if (defaultRepresentation != null) {
      useRepresentation(defaultRepresentation);
    }
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  public void useLabelNameLookup(LabelNameLookup labelNameLookup) {
    this.labelNameLookup.merge(labelNameLookup);
  }

  public LabelNameLookup labelNameLookup() {
    return labelNameLookup;
  }

  @Override
  public void useRepresentation(Representation newRepresentation) {
    if (newRepresentation instanceof AbstractWithLabelNamesAsmRepresentation) {
      var withLabelNamesRepresentation = (AbstractWithLabelNamesAsmRepresentation<?>) newRepresentation;
      newRepresentation = new WithLabelNamesRepresentationAdapter<>(withLabelNamesRepresentation, labelNameLookup);
    }

    super.useRepresentation(newRepresentation);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
