package dev.turingcomplete.asmtestkit.assertion.representation;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.util.TraceAnnotationVisitor;

import java.util.Objects;

/**
 * Creates a {@link String} representation of an {@link AnnotationNode}.
 */
public class AnnotationNodeRepresentation extends AsmRepresentation<AnnotationNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link AnnotationNodeRepresentation} instance.
   */
  public static final AnnotationNodeRepresentation INSTANCE = new AnnotationNodeRepresentation();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private TypeRepresentation typeRepresentation = TypeRepresentation.INSTANCE;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public AnnotationNodeRepresentation() {
    super(AnnotationNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Sets the used {@link TypeRepresentation}.
   *
   * <p>The default value is {@link TypeRepresentation#INSTANCE}.
   *
   * @param typeRepresentation a {@link TypeRepresentation}; never null.
   * @return {@code this} {@link AnnotationNodeRepresentation}; never null.
   */
  public AnnotationNodeRepresentation useTypePathRepresentation(TypeRepresentation typeRepresentation) {
    this.typeRepresentation = Objects.requireNonNull(typeRepresentation);

    return this;
  }

  @Override
  protected String toStringRepresentation(AnnotationNode annotationNode) {
    String className = annotationNode.desc != null ? typeRepresentation.toStringRepresentation(Type.getType(annotationNode.desc)) : null;
    String result = "@" + className;

    String textifiedValues = getAsmTextifierRepresentation(textifier -> annotationNode.accept(new TraceAnnotationVisitor(textifier))).trim();
    if (!textifiedValues.isBlank()) {
      result += "(" + textifiedValues + ")";
    }

    return result;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
