package dev.turingcomplete.asmtestkit.asmutils._internal;

import dev.turingcomplete.asmtestkit.assertion.LabelNameLookup;
import org.objectweb.asm.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class CombinedLabelNamesLookup implements LabelNameLookup {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final List<LabelNameLookup> labelNameLookup = new ArrayList<>();

  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public Optional<String> find(Label label) {
    for (LabelNameLookup nameLookup : labelNameLookup) {
      Optional<String> result = nameLookup.find(label);
      if (result.isPresent()) {
        return result;
      }
    }

    return Optional.empty();
  }

  @Override
  public LabelNameLookup merge(LabelNameLookup labelNameLookup) {
    this.labelNameLookup.add(labelNameLookup);

    return this;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
