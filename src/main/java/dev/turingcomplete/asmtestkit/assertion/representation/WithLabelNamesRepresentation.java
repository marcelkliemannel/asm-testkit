package dev.turingcomplete.asmtestkit.assertion.representation;

import org.objectweb.asm.Label;

import java.util.Map;

public abstract class WithLabelNamesRepresentation<T> extends AsmRepresentation<T> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public WithLabelNamesRepresentation(Class<T> objectClass) {
    super(objectClass);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  public final String toSimplifiedStringOf(Object object, Map<Label, String> labelNames) {
    return getObjectClass().isInstance(object)
            ? doToSimplifiedStringOf(getObjectClass().cast(object), labelNames)
            : null;
  }

  protected String doToSimplifiedStringOf(T object, Map<Label, String> labelNames) {
    return doToSimplifiedStringOf(object);
  }

  public final String toStringOf(Object object, Map<Label, String> labelNames) {
    if (getObjectClass().isInstance(object)) {
      return doToStringOf(getObjectClass().cast(object), labelNames);
    }

    return null;
  }

  protected abstract String doToStringOf(T object, Map<Label, String> labelNames);

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
