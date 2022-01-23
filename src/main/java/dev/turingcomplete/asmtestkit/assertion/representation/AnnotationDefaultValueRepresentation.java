package dev.turingcomplete.asmtestkit.assertion.representation;

import org.assertj.core.presentation.Representation;
import org.objectweb.asm.tree.AnnotationNode;

/**
 * An AssertJ {@link Representation} for an annotation default value.
 */
public class AnnotationDefaultValueRepresentation extends AbstractAsmRepresentation<Object> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link AnnotationDefaultValueRepresentation} instance.
   */
  public static final AnnotationDefaultValueRepresentation INSTANCE = create();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AnnotationDefaultValueRepresentation() {
    super(Object.class);
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
  protected String doToStringOf(Object object) {
    return object instanceof AnnotationNode ? asmRepresentations.toStringOf(object) : object.toString();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
