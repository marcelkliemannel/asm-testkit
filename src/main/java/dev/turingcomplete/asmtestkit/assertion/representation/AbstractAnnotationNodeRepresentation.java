package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.asmutils._internal.TextifierUtils;
import dev.turingcomplete.asmtestkit.assertion.AsmAssert;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.util.TraceAnnotationVisitor;

import java.util.Map;
import java.util.Objects;

/**
 * Base class for an AssertJ {@link Representation} for an {@link AnnotationNode}
 * or its subtypes.
 *
 * @param <S> the 'self' type of {@code this} {@link AsmAssert}.
 * @param <A> an {@link AnnotationNode} or a subtype of the actual object.
 */
public abstract class AbstractAnnotationNodeRepresentation<S, A extends AnnotationNode> extends WithLabelNamesRepresentation<A> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private TypeRepresentation typeRepresentation = TypeRepresentation.INSTANCE;
  private boolean            hideValues         = false;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AbstractAnnotationNodeRepresentation(Class<A> annotationClass) {
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

  @Override
  public String doToStringOf(A object, Map<Label, String> labelNames) {
    return doToStringOf(object);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
