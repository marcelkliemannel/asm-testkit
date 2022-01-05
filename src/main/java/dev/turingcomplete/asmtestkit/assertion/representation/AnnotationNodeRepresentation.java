package dev.turingcomplete.asmtestkit.assertion.representation;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.util.TraceAnnotationVisitor;

/**
 * Creates a {@link String} representation of an {@link AnnotationNode}.
 */
public class AnnotationNodeRepresentation extends AsmRepresentation<AnnotationNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  private static final AnnotationNodeRepresentation INSTANCE = new AnnotationNodeRepresentation();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public AnnotationNodeRepresentation() {
    super(AnnotationNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Gets a reusable {@link AnnotationNodeRepresentation} instance.
   *
   * @return an {@link AnnotationNodeRepresentation} instance; never null.
   */
  public static AnnotationNodeRepresentation instance() {
    return INSTANCE;
  }

  @Override
  protected String toStringRepresentation(AnnotationNode annotationNode) {
    String result = "@" + Type.getType(annotationNode.desc).getClassName();

    String textifiedValues = getAsmTextifierRepresentation(textifier -> annotationNode.accept(new TraceAnnotationVisitor(textifier))).trim();
    if (!textifiedValues.isBlank()) {
      result += "(" + textifiedValues + ")";
    }

    return result;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
