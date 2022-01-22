package dev.turingcomplete.asmtestkit.assertion._internal;

import dev.turingcomplete.asmtestkit.assertion.LabelNameLookup;
import dev.turingcomplete.asmtestkit.assertion.representation.WithLabelNamesRepresentation;
import org.assertj.core.api.WritableAssertionInfo;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Label;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class AsmWritableAssertionInfo extends WritableAssertionInfo {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final Map<Label, String> labelNames      = new HashMap<>();
  private final LabelNameLookup    labelNameLookup = LabelNameLookup.create(labelNames);

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public AsmWritableAssertionInfo(Representation defaultRepresentation) {
    if (defaultRepresentation != null) {
      useRepresentation(defaultRepresentation);
    }
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  public void useLabelNames(Map<Label, String> labelNames) {
    this.labelNames.clear();
    this.labelNames.putAll(labelNames);
  }

  public Map<Label, String> labelNames() {
    return Collections.unmodifiableMap(labelNames);
  }

  public LabelNameLookup labelNameLookup() {
    return labelNameLookup;
  }

  @Override
  public void useRepresentation(Representation newRepresentation) {
    if (newRepresentation instanceof WithLabelNamesRepresentation) {
      var withLabelNamesRepresentation = (WithLabelNamesRepresentation<?>) newRepresentation;
      newRepresentation = new WithLabelNamesRepresentationAdapter<>(withLabelNamesRepresentation, labelNameLookup);
    }

    super.useRepresentation(newRepresentation);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
