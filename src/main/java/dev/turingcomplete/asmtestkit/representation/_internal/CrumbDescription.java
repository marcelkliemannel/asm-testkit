package dev.turingcomplete.asmtestkit.representation._internal;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.description.Description;

public final class CrumbDescription extends Description {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final AssertionInfo assertionInfo;
  private final String        crumbDescription;
  private final Object[]      crumbArgs;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public CrumbDescription(AssertionInfo assertionInfo, String crumbDescription, Object... crumbArgs) {
    this.assertionInfo = assertionInfo;
    this.crumbDescription = crumbDescription;
    this.crumbArgs = crumbArgs;
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public String value() {
    String value = assertionInfo.description().value();

    if (crumbDescription != null) {
      value += " > " + String.format(crumbDescription, crumbArgs);
    }

    return value;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
