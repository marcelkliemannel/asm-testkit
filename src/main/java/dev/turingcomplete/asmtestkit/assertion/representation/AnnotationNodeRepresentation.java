package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.asmutils._internal.TextifierUtils;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.util.TraceAnnotationVisitor;

import java.util.Objects;

/**
 * Creates a {@link String} representation of an {@link AnnotationNode}.
 */
public class AnnotationNodeRepresentation<S, A extends AnnotationNode> extends AsmRepresentation<A> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link AnnotationNodeRepresentation} instance.
   */
  public static final AnnotationNodeRepresentation<?, AnnotationNode> INSTANCE = new AnnotationNodeRepresentation<>(AnnotationNode.class);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private TypeRepresentation typeRepresentation = TypeRepresentation.INSTANCE;
  private boolean            hideValues         = false;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public static AnnotationNodeRepresentation<AnnotationNodeRepresentation<?, AnnotationNode>, AnnotationNode> create() {
    return new AnnotationNodeRepresentation<>(AnnotationNode.class);
  }

  public AnnotationNodeRepresentation(Class<A> annotationClass) {
    super(annotationClass);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Sets the used {@link TypeRepresentation}.
   *
   * <p>The default value is {@link TypeRepresentation#INSTANCE}.
   *
   * @param typeRepresentation a {@link TypeRepresentation}; never null.
   * @return {@code this} {@link S}; never null.
   */
  public S useTypePathRepresentation(TypeRepresentation typeRepresentation) {
    this.typeRepresentation = Objects.requireNonNull(typeRepresentation);

    //noinspection unchecked
    return (S) this;
  }

  /**
   * Hides the values of the annotation in the representation.
   *
   * @return {@code this} {@link S}; never null.
   */
  public S hideValues() {
    this.hideValues = true;

    //noinspection unchecked
    return (S) this;
  }

  /**
   * Creates a simple representation of the given {@code annotationNode} with
   * just the class name but without any additional information (e.g., no values).
   *
   * <p>For example, would generate for the annotation {@link Deprecated} the
   * representation {@code @java.lang.Deprecated}.
   *
   * @param annotationNode an annotation of type {@link A}.
   * @return a simple representation of {@code annotationNode}.
   */
  @Override
  public String doToSimplifiedStringOf(A annotationNode) {
    String className = annotationNode.desc != null ? typeRepresentation.doToStringOf(Type.getType(annotationNode.desc)) : null;
    return "@" + className;
  }

  @Override
  protected String doToStringOf(A annotationNode) {
    String representation = doToSimplifiedStringOf(annotationNode);

    if (!hideValues) {
      String textifiedValues = TextifierUtils.toString(textifier -> annotationNode.accept(new TraceAnnotationVisitor(textifier))).trim();
      if (!textifiedValues.isBlank()) {
        representation += "(" + textifiedValues + ")";
      }
    }

    return representation;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
