package dev.turingcomplete.asmtestkit.assertion.representation;

import org.assertj.core.presentation.Representation;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.Objects;

/**
 * An AssertJ {@link Representation} for an annotation default value.
 *
 * <p>For an {@link AnnotationNode} it will use the
 * {@link #annotationNodeRepresentation}, otherwise {@link #toString()}.
 */
public class AnnotationDefaultValueRepresentation extends AsmRepresentation<Object> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link AnnotationDefaultValueRepresentation} instance.
   */
  public static final AnnotationDefaultValueRepresentation INSTANCE = new AnnotationDefaultValueRepresentation();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private AnnotationNodeRepresentation annotationNodeRepresentation = AnnotationNodeRepresentation.INSTANCE;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AnnotationDefaultValueRepresentation() {
    super(Object.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Sets the used {@link AnnotationNodeRepresentation}.
   *
   * <p>The default value is {@link AnnotationNodeRepresentation#INSTANCE}.
   *
   * @param annotationNodeRepresentation an {@link AnnotationNodeRepresentation};
   *                                     never null.
   * @return {@code this} {@link AnnotationDefaultValueRepresentation}; never null.
   */
  public AnnotationDefaultValueRepresentation useAnnotationNodeRepresentation(AnnotationNodeRepresentation annotationNodeRepresentation) {
    this.annotationNodeRepresentation = Objects.requireNonNull(annotationNodeRepresentation);

    return this;
  }

  /**
   * Creates a new {@link AnnotationDefaultValueRepresentation} instance.
   *
   * @return a new {@link AnnotationDefaultValueRepresentation}; never null;
   */
  public static AttributeRepresentation create() {
    return new AttributeRepresentation();
  }

  @Override
  protected String doToStringOf(Object object) {
    return object instanceof AnnotationNode ? annotationNodeRepresentation.toStringOf(object) : object.toString();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
