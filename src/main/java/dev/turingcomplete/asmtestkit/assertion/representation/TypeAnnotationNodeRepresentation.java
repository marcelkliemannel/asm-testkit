package dev.turingcomplete.asmtestkit.assertion.representation;

import org.assertj.core.presentation.Representation;
import org.objectweb.asm.tree.TypeAnnotationNode;

/**
 * An AssertJ {@link Representation} for a {@link TypeAnnotationNode}.
 *
 * <p>Example output: {@code @TypeParameterAnnotation // reference: class_extends=-1; path: null}.
 *
 * <p>The simplified output omits any values, reference and path.
 */
public class TypeAnnotationNodeRepresentation
        extends AbstractTypeAnnotationNodeRepresentation<TypeAnnotationNodeRepresentation, TypeAnnotationNode> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link TypeAnnotationNodeRepresentation} instance.
   */
  public static final TypeAnnotationNodeRepresentation INSTANCE = new TypeAnnotationNodeRepresentation();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected TypeAnnotationNodeRepresentation() {
    super(TypeAnnotationNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link TypeAnnotationNodeRepresentation} instance.
   *
   * @return a new {@link TypeAnnotationNodeRepresentation}; never null;
   */
  public static TypeAnnotationNodeRepresentation create() {
    return new TypeAnnotationNodeRepresentation();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
