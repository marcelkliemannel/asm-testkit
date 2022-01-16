package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.assertion.AsmAssert;
import org.objectweb.asm.TypeReference;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Objects;

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

  private TypeReferenceRepresentation typeReferenceRepresentation = TypeReferenceRepresentation.INSTANCE;
  private TypePathRepresentation      typePathRepresentation      = TypePathRepresentation.INSTANCE;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public AbstractTypeAnnotationNodeRepresentation(Class<A> typeAnnotationClass) {
    super(typeAnnotationClass);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Sets the used {@link TypeReferenceRepresentation}.
   *
   * <p>The default value is {@link TypeReferenceRepresentation#INSTANCE}.
   *
   * @param typeReferenceRepresentation a {@link TypeReferenceRepresentation}; never null.
   * @return {@code this} {@link S}; never null.
   */
  public S useTypeReferenceRepresentation(TypeReferenceRepresentation typeReferenceRepresentation) {
    this.typeReferenceRepresentation = Objects.requireNonNull(typeReferenceRepresentation);

    //noinspection unchecked
    return (S) this;
  }

  /**
   * Sets the used {@link TypePathRepresentation}.
   *
   * <p>The default value is {@link TypePathRepresentation#INSTANCE}.
   *
   * @param typePathRepresentation a {@link TypePathRepresentation}; never null.
   * @return {@code this} {@link S}; never null.
   */
  public S useTypePathRepresentation(TypePathRepresentation typePathRepresentation) {
    this.typePathRepresentation = Objects.requireNonNull(typePathRepresentation);

    //noinspection unchecked
    return (S) this;
  }

  @Override
  protected String doToStringOf(A annotationNode) {
    String annotationNodeRepresentation = super.doToStringOf(annotationNode);

    return annotationNodeRepresentation +
           " // reference: " + typeReferenceRepresentation.toStringOf(new TypeReference(annotationNode.typeRef)) +
           "; path: " + typePathRepresentation.toStringOf(annotationNode.typePath);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
