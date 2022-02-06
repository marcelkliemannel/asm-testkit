package dev.turingcomplete.asmtestkit.representation;

import dev.turingcomplete.asmtestkit.assertion.AsmAssert;
import org.objectweb.asm.TypeReference;
import org.objectweb.asm.tree.TypeAnnotationNode;

/**
 * Creates a {@link String} representation of a {@link TypeAnnotationNode}.
 *
 * <p>Example output: {@code @TypeParameterAnnotation // reference: class_extends=-1; path: null}.
 *
 * <p>The simplified output omits any values, reference and path.
 *
 * @param <S> the 'self' type of {@code this} {@link AsmAssert}}.
 * @param <A> a {@link TypeAnnotationNode} or a subtype of the actual object.
 */
public abstract class AbstractTypeAnnotationNodeRepresentation<S, A extends TypeAnnotationNode>
        extends AbstractAnnotationNodeRepresentation<S, A> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AbstractTypeAnnotationNodeRepresentation(Class<A> typeAnnotationClass) {
    super(typeAnnotationClass);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  protected String doToStringOf(A annotationNode) {
    String annotationNodeRepresentation = super.doToStringOf(annotationNode);

    return annotationNodeRepresentation +
           " // reference: " + asmRepresentations.toStringOf(new TypeReference(annotationNode.typeRef)) +
           "; path: " + asmRepresentations.toStringOf(annotationNode.typePath);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
