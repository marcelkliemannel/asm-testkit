package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.asmutils.AccessKind;
import dev.turingcomplete.asmtestkit.assertion.representation._internal.RepresentationUtils;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldNode;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Creates a {@link String} representation of a {@link FieldNode}.
 */
public class FieldNodeRepresentation extends AsmRepresentation<FieldNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link FieldNodeRepresentation} instance.
   */
  public static final FieldNodeRepresentation INSTANCE = new FieldNodeRepresentation();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private AccessRepresentation             accessRepresentation             = AccessRepresentation.FIELD_INSTANCE;
  private TypeRepresentation               typeRepresentation               = TypeRepresentation.INSTANCE;
  private AttributeRepresentation          attributeRepresentation          = AttributeRepresentation.INSTANCE;
  private AnnotationNodeRepresentation     annotationNodeRepresentation     = AnnotationNodeRepresentation.INSTANCE;
  private TypeAnnotationNodeRepresentation typeAnnotationNodeRepresentation = TypeAnnotationNodeRepresentation.INSTANCE;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public FieldNodeRepresentation() {
    super(FieldNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Sets the used {@link AccessRepresentation} for {@link AccessKind#FIELD}.
   *
   * <p>The default value is {@link AccessRepresentation#FIELD_INSTANCE}.
   *
   * @param accessRepresentation a {@link AccessRepresentation};
   *                             never null.
   * @return {@code this} {@link FieldNodeRepresentation}; never null.
   * @throws IllegalArgumentException if the given {@code accessRepresentation}
   *                                  is not for {@link AccessKind#FIELD}.
   */
  public FieldNodeRepresentation useAccessRepresentation(AccessRepresentation accessRepresentation) {
    Objects.requireNonNull(accessRepresentation);

    if (accessRepresentation.accessKind != AccessKind.FIELD) {
      throw new IllegalArgumentException("Must have " + AccessKind.class.getSimpleName() + ": " + AccessKind.FIELD);
    }

    this.accessRepresentation = accessRepresentation;

    return this;
  }

  /**
   * Sets the used {@link TypeRepresentation}.
   *
   * <p>The default value is {@link TypeRepresentation#INSTANCE}.
   *
   * @param typeRepresentation a {@link TypeRepresentation};
   *                           never null.
   * @return {@code this} {@link FieldNodeRepresentation}; never null.
   */
  public FieldNodeRepresentation useTypeRepresentation(TypeRepresentation typeRepresentation) {
    this.typeRepresentation = Objects.requireNonNull(typeRepresentation);

    return this;
  }

  /**
   * Sets the used {@link AttributeRepresentation}.
   *
   * <p>The default value is {@link AttributeRepresentation#INSTANCE}.
   *
   * @param attributeRepresentation an {@link AttributeRepresentation};
   *                                never null.
   * @return {@code this} {@link FieldNodeRepresentation}; never null.
   */
  public FieldNodeRepresentation useAttributeRepresentation(AttributeRepresentation attributeRepresentation) {
    this.attributeRepresentation = Objects.requireNonNull(attributeRepresentation);

    return this;
  }

  /**
   * Sets the used {@link AnnotationNodeRepresentation}.
   *
   * <p>The default value is {@link AnnotationNodeRepresentation#INSTANCE}.
   *
   * @param annotationNodeRepresentation an {@link AnnotationNodeRepresentation};
   *                                     never null.
   * @return {@code this} {@link FieldNodeRepresentation}; never null.
   */
  public FieldNodeRepresentation useAnnotationNodeRepresentation(AnnotationNodeRepresentation annotationNodeRepresentation) {
    this.annotationNodeRepresentation = Objects.requireNonNull(annotationNodeRepresentation);

    return this;
  }

  /**
   * Sets the used {@link TypeAnnotationNodeRepresentation}.
   *
   * <p>The default value is {@link TypeAnnotationNodeRepresentation#INSTANCE}.
   *
   * @param typeAnnotationNodeRepresentation a {@link TypeAnnotationNodeRepresentation};
   *                                         never null.
   * @return {@code this} {@link FieldNodeRepresentation}; never null.
   */
  public FieldNodeRepresentation useTypeAnnotationNodeRepresentation(TypeAnnotationNodeRepresentation typeAnnotationNodeRepresentation) {
    this.typeAnnotationNodeRepresentation = Objects.requireNonNull(typeAnnotationNodeRepresentation);

    return this;
  }

  @Override
  protected String createSimplifiedRepresentation(FieldNode fieldNode) {
    var representation = new StringJoiner(" ");

    // Access
    representation.add(accessRepresentation.toStringOf(fieldNode.access));

    // Type
    if (fieldNode.desc != null) {
      representation.add(typeRepresentation.createRepresentation(Type.getType(fieldNode.desc)));
    }

    // Name
    representation.add(fieldNode.name);

    return representation.toString();
  }

  @Override
  protected String createRepresentation(FieldNode fieldNode) {
    var representation = new StringBuilder();

    // Attributes
    if (fieldNode.attrs != null) {
      for (Attribute attribute : fieldNode.attrs) {
        representation.append(attributeRepresentation.createRepresentation(attribute)).append(System.lineSeparator());
      }
    }

    // Annotations
    RepresentationUtils.createAnnotationNodesRepresentations(annotationNodeRepresentation, typeAnnotationNodeRepresentation,
                                                             fieldNode.visibleAnnotations, fieldNode.invisibleAnnotations,
                                                             fieldNode.visibleTypeAnnotations, fieldNode.invisibleTypeAnnotations)
                       .forEach(annotationNodeRepresentation -> representation.append(annotationNodeRepresentation).append(System.lineSeparator()));

    // Access, type and name
    representation.append(createSimplifiedRepresentation(fieldNode));

    // Value
    if (fieldNode.value != null) {
      representation.append(" = ").append(fieldNode.value);
    }

    // Signature
    if (fieldNode.signature != null) {
      representation.append(" // signature: ").append(fieldNode.signature);
    }

    return representation.toString();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
