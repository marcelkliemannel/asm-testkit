package dev.turingcomplete.asmtestkit.assertion.representation._internal;

import dev.turingcomplete.asmtestkit.assertion.LabelNameLookup;
import dev.turingcomplete.asmtestkit.assertion._internal.AsmWritableAssertionInfo;
import dev.turingcomplete.asmtestkit.assertion.representation.AsmRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.WithLabelNamesRepresentation;
import org.assertj.core.api.AssertionInfo;
import org.assertj.core.description.Description;
import org.assertj.core.presentation.Representation;

public final class SelfDescription extends Description {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final String             name;
  private final Object             actual;
  private final AssertionInfo      assertionInfo;

  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  public SelfDescription(String name, Object actual, AssertionInfo assertionInfo) {
    this.name = name;
    this.actual = actual;
    this.assertionInfo = assertionInfo;
  }

  @Override
  public String value() {
    Representation representation = assertionInfo.representation();
    String actualRepresentation;
    if (assertionInfo instanceof AsmWritableAssertionInfo && representation instanceof WithLabelNamesRepresentation) {
      LabelNameLookup labelNameLookup = ((AsmWritableAssertionInfo) assertionInfo).labelNameLookup();
      actualRepresentation = ((WithLabelNamesRepresentation<?>) representation).toSimplifiedStringOf(actual, labelNameLookup);
    }
    else if (representation instanceof AsmRepresentation) {
      actualRepresentation = ((AsmRepresentation<?>) representation).toSimplifiedStringOf(actual);
    }
    else {
      actualRepresentation = representation.toStringOf(actual);
    }

    return name + ": " + actualRepresentation;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
