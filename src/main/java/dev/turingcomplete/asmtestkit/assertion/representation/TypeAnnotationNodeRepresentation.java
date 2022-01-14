package dev.turingcomplete.asmtestkit.assertion.representation;

import org.objectweb.asm.TypeReference;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Objects;

/**
 * Creates a {@link String} representation of a {@link TypeAnnotationNode}.
 *
 * <p>Example output: {@code @TypeParameterAnnotation // reference: class_extends=-1; path: null}.
 */
public class TypeAnnotationNodeRepresentation<S, A extends TypeAnnotationNode>
        extends AnnotationNodeRepresentation<S, A> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link TypeAnnotationNodeRepresentation} instance.
   */
  public static final TypeAnnotationNodeRepresentation<?, TypeAnnotationNode> INSTANCE = new TypeAnnotationNodeRepresentation<>(TypeAnnotationNode.class);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private TypeReferenceRepresentation typeReferenceRepresentation = TypeReferenceRepresentation.INSTANCE;
  private TypePathRepresentation      typePathRepresentation      = TypePathRepresentation.INSTANCE;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public TypeAnnotationNodeRepresentation(Class<A> typeAnnotationClass) {
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
