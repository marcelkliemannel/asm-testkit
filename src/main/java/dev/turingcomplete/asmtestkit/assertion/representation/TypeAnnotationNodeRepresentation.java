package dev.turingcomplete.asmtestkit.assertion.representation;

import org.objectweb.asm.TypeReference;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Objects;

/**
 * Creates a {@link String} representation of an {@link TypeAnnotationNode}.
 */
public class TypeAnnotationNodeRepresentation extends AbstractAnnotationNodeRepresentation<TypeAnnotationNodeRepresentation, TypeAnnotationNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link TypeAnnotationNodeRepresentation} instance.
   */
  public static final TypeAnnotationNodeRepresentation INSTANCE = new TypeAnnotationNodeRepresentation();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private TypeReferenceRepresentation typeReferenceRepresentation = TypeReferenceRepresentation.INSTANCE;
  private TypePathRepresentation      typePathRepresentation      = TypePathRepresentation.INSTANCE;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public TypeAnnotationNodeRepresentation() {
    super(TypeAnnotationNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Sets the used {@link TypeReferenceRepresentation}.
   *
   * <p>The default value is {@link TypeReferenceRepresentation#INSTANCE}.
   *
   * @param typeReferenceRepresentation a {@link TypeReferenceRepresentation}; never null.
   * @return {@code this} {@link TypeAnnotationNodeRepresentation}; never null.
   */
  public TypeAnnotationNodeRepresentation useTypeReferenceRepresentation(TypeReferenceRepresentation typeReferenceRepresentation) {
    this.typeReferenceRepresentation = Objects.requireNonNull(typeReferenceRepresentation);

    return this;
  }

  /**
   * Sets the used {@link TypePathRepresentation}.
   *
   * <p>The default value is {@link TypePathRepresentation#INSTANCE}.
   *
   * @param typePathRepresentation a {@link TypePathRepresentation}; never null.
   * @return {@code this} {@link TypeAnnotationNodeRepresentation}; never null.
   */
  public TypeAnnotationNodeRepresentation useTypePathRepresentation(TypePathRepresentation typePathRepresentation) {
    this.typePathRepresentation = Objects.requireNonNull(typePathRepresentation);

    return this;
  }

  @Override
  protected String toStringRepresentation(TypeAnnotationNode annotationNode) {
    String annotationNodeRepresentation = super.toStringRepresentation(annotationNode);

    return annotationNodeRepresentation +
           " {reference: " + typeReferenceRepresentation.toStringOf(new TypeReference(annotationNode.typeRef)) +
           "; path: " + typePathRepresentation.toStringOf(annotationNode.typePath) + "}";
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
