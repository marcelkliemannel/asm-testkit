package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.asmutils.AccessKind;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldNode;

import java.util.StringJoiner;

import static dev.turingcomplete.asmtestkit.assertion.representation._internal.RepresentationUtils.createAnnotationNodesRepresentations;
import static dev.turingcomplete.asmtestkit.assertion.representation._internal.RepresentationUtils.createAttributesRepresentations;
import static dev.turingcomplete.asmtestkit.assertion.representation._internal.RepresentationUtils.createTypeAnnotationNodesRepresentations;

/**
 * An AssertJ {@link Representation} for a {@link FieldNode}.
 *
 * <p>Example output:
 * <pre>{@code
 * @java.lang.Deprecated(forRemoval=true)
 * (131073) public deprecated int myField = 5
 * }</pre>
 *
 * <p>Example simplified output: {@code (131073) public deprecated int myField}.
 */
public class FieldNodeRepresentation extends AbstractSingleAsmRepresentation<FieldNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link FieldNodeRepresentation} instance.
   */
  public static final FieldNodeRepresentation INSTANCE = create();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected FieldNodeRepresentation() {
    super(FieldNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link FieldNodeRepresentation} instance.
   *
   * @return a new {@link FieldNodeRepresentation}; never null;
   */
  public static FieldNodeRepresentation create() {
    return new FieldNodeRepresentation();
  }

  @Override
  protected String doToSimplifiedStringOf(FieldNode fieldNode) {
    var representation = new StringJoiner(" ");

    // Access
    representation.add(asmRepresentations.toStringOf(fieldNode.access, AccessKind.FIELD));

    // Type
    if (fieldNode.desc != null) {
      representation.add(asmRepresentations.toStringOf(Type.getType(fieldNode.desc)));
    }

    // Name
    representation.add(fieldNode.name);

    return representation.toString();
  }

  @Override
  protected String doToStringOf(FieldNode fieldNode) {
    var representation = new StringBuilder();

    // Attributes
    createAttributesRepresentations(asmRepresentations, fieldNode.attrs)
            .forEach(attributeRepresentation -> representation.append("// attribute: ")
                                                              .append(attributeRepresentation)
                                                              .append(System.lineSeparator()));

    // Annotations
    createAnnotationNodesRepresentations(asmRepresentations, fieldNode.visibleAnnotations, fieldNode.invisibleAnnotations)
            .forEach(annotationNodeRepresentation -> representation.append(annotationNodeRepresentation).append(System.lineSeparator()));

    // Type annotations
    createTypeAnnotationNodesRepresentations(asmRepresentations, fieldNode.visibleTypeAnnotations, fieldNode.invisibleTypeAnnotations)
            .forEach(annotationNodeRepresentation -> representation.append(annotationNodeRepresentation).append(System.lineSeparator()));

    // Access, type and name
    representation.append(doToSimplifiedStringOf(fieldNode));

    // Value
    if (fieldNode.value != null) {
      representation.append(" = ").append(fieldNode.value);
    }

    // signature
    if (fieldNode.signature != null) {
      representation.append(" // signature: ").append(fieldNode.signature);
    }

    return representation.toString();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
