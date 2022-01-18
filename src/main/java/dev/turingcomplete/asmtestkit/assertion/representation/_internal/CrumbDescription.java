package dev.turingcomplete.asmtestkit.assertion.representation._internal;

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
    return assertionInfo.description().value() + " > " + String.format(crumbDescription, crumbArgs);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
