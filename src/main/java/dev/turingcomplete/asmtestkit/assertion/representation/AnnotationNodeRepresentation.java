package dev.turingcomplete.asmtestkit.assertion.representation;

import org.objectweb.asm.tree.AnnotationNode;

/**
 * Creates a {@link String} representation of an {@link AnnotationNode}.
 */
public class AnnotationNodeRepresentation extends AbstractAnnotationNodeRepresentation<AnnotationNodeRepresentation, AnnotationNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link AnnotationNodeRepresentation} instance.
   */
  public static final AnnotationNodeRepresentation INSTANCE = new AnnotationNodeRepresentation();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public AnnotationNodeRepresentation() {
    super(AnnotationNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //
  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
