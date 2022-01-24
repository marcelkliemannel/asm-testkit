package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.node.AnnotationDefaultValue;
import org.assertj.core.presentation.Representation;

/**
 * An AssertJ {@link Representation} for an annotation default value.
 */
public class AnnotationDefaultValueRepresentation extends AbstractAsmRepresentation<AnnotationDefaultValue> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link AnnotationDefaultValueRepresentation} instance.
   */
  public static final AnnotationDefaultValueRepresentation INSTANCE = create();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AnnotationDefaultValueRepresentation() {
    super(AnnotationDefaultValue.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link AnnotationDefaultValueRepresentation} instance.
   *
   * @return a new {@link AnnotationDefaultValueRepresentation}; never null;
   */
  public static AnnotationDefaultValueRepresentation create() {
    return new AnnotationDefaultValueRepresentation();
  }

  @Override
  protected String doToStringOf(AnnotationDefaultValue annotationDefaultValue) {
    return asmRepresentations.toStringOf(annotationDefaultValue.defaultValue());
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
