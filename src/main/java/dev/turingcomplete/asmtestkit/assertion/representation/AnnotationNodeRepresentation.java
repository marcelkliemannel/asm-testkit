package dev.turingcomplete.asmtestkit.assertion.representation;

import org.assertj.core.presentation.Representation;
import org.objectweb.asm.tree.AnnotationNode;

/**
 * An AssertJ {@link Representation} for an {@link AnnotationNode}.
 *
 * <p>Example output: {@code @Annotation(param = 1)}.
 *
 * <p>The simplified output omits any values.
 */
public class AnnotationNodeRepresentation
        extends AbstractAnnotationNodeRepresentation<AnnotationNodeRepresentation, AnnotationNode> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link AnnotationNodeRepresentation} instance.
   */
  public static final AnnotationNodeRepresentation INSTANCE = new AnnotationNodeRepresentation();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AnnotationNodeRepresentation() {
    super(AnnotationNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link AnnotationNodeRepresentation} instance.
   *
   * @return a new {@link AnnotationNodeRepresentation}; never null;
   */
  public static AnnotationNodeRepresentation create() {
    return new AnnotationNodeRepresentation();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
