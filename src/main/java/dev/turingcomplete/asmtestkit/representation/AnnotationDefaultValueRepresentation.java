package dev.turingcomplete.asmtestkit.representation;

import dev.turingcomplete.asmtestkit.node.AnnotationDefault;
import org.assertj.core.presentation.Representation;

/**
 * An AssertJ {@link Representation} for an annotation default value.
 */
public class AnnotationDefaultValueRepresentation extends AbstractAsmRepresentation<AnnotationDefault> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link AnnotationDefaultValueRepresentation} instance.
   */
  public static final AnnotationDefaultValueRepresentation INSTANCE = create();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AnnotationDefaultValueRepresentation() {
    super(AnnotationDefault.class);
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
  protected String doToStringOf(AnnotationDefault annotationDefault) {
    return asmRepresentations.toStringOf(annotationDefault.defaultValue());
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
