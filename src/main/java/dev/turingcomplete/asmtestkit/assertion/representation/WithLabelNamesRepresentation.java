package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.assertion.LabelNameLookup;

public abstract class WithLabelNamesRepresentation<T> extends AsmRepresentation<T> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public WithLabelNamesRepresentation(Class<T> objectClass) {
    super(objectClass);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  public final String toSimplifiedStringOf(Object object, LabelNameLookup labelNameLookup) {
    return getObjectClass().isInstance(object)
            ? doToSimplifiedStringOf(getObjectClass().cast(object), labelNameLookup)
            : null;
  }

  protected String doToSimplifiedStringOf(T object, LabelNameLookup labelNameLookup) {
    return doToSimplifiedStringOf(object);
  }

  public final String toStringOf(Object object, LabelNameLookup labelNameLookup) {
    if (getObjectClass().isInstance(object)) {
      return doToStringOf(getObjectClass().cast(object), labelNameLookup);
    }

    return null;
  }

  protected abstract String doToStringOf(T object, LabelNameLookup labelNameLookup);

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
