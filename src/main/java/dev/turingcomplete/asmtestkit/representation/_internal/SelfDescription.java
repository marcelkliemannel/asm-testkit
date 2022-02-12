package dev.turingcomplete.asmtestkit.representation._internal;

import dev.turingcomplete.asmtestkit.common.LabelIndexLookup;
import dev.turingcomplete.asmtestkit.assertion._internal.AsmWritableAssertionInfo;
import dev.turingcomplete.asmtestkit.representation.AbstractAsmRepresentation;
import dev.turingcomplete.asmtestkit.representation.AbstractWithLabelIndexAsmRepresentation;
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
    if (assertionInfo instanceof AsmWritableAssertionInfo && representation instanceof AbstractWithLabelIndexAsmRepresentation) {
      LabelIndexLookup labelIndexLookup = ((AsmWritableAssertionInfo) assertionInfo).labelIndexLookup();
      actualRepresentation = ((AbstractWithLabelIndexAsmRepresentation<?>) representation).toSimplifiedStringOf(actual, labelIndexLookup);
    }
    else if (representation instanceof AbstractAsmRepresentation) {
      actualRepresentation = ((AbstractAsmRepresentation<?>) representation).toSimplifiedStringOf(actual);
    }
    else {
      actualRepresentation = representation.toStringOf(actual);
    }

    return name + ": " + actualRepresentation;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
