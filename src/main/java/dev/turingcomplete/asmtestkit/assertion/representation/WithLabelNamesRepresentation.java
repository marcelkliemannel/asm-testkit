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

  public final String toStringOf(Object object, Map<Label, String> labelNames) {
    if (objectClass.isInstance(object)) {
      return doToStringOf(objectClass.cast(object), labelNames);
    }

    return super.fallbackToStringOf(object);
  }

  public abstract String doToStringOf(T object, Map<Label, String> labelNames);

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
