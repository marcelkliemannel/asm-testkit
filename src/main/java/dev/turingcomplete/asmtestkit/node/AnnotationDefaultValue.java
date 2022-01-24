package dev.turingcomplete.asmtestkit.node;

public final class AnnotationDefaultValue {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final Object defaultValue;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  private AnnotationDefaultValue(Object defaultValue) {
    this.defaultValue = defaultValue;
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  public static AnnotationDefaultValue create(Object defaultValue) {
    return new AnnotationDefaultValue(defaultValue);
  }

  public Object defaultValue() {
    return defaultValue;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
