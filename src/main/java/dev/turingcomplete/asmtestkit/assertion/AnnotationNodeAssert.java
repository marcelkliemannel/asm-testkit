package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.option.AssertOption;
import dev.turingcomplete.asmtestkit.assertion.representation.AnnotationNodeRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.tree.AnnotationNode;

/**
 * An AssertJ {@link AbstractAssert} for an {@link AnnotationNode}.
 *
 * <p>To override the used {@link AnnotationNodeRepresentation} call
 * {@link #withRepresentation(Representation)}.
 *
 * <p>An instance can be created via {@link AsmAssertions#assertThat(AnnotationNode)}.
 */
public class AnnotationNodeAssert extends AbstractAnnotationNodeAssert<AnnotationNodeAssert, AnnotationNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes an {@link AnnotationNodeAssert}.
   *
   * <p>There are no direct supported {@link AssertOption}s yet.
   *
   * @param actual the actual {@link AnnotationNode}; may be null.
   * @param assertOptions an array of {@link AssertOption}s; never null.
   */
  public AnnotationNodeAssert(AnnotationNode actual, AssertOption... assertOptions) {
    super(actual, AnnotationNodeAssert.class, AnnotationNode.class, createSelfDescription(actual), assertOptions);

    info.useRepresentation(AnnotationNodeRepresentation.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //
  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private static String createSelfDescription(AnnotationNode actual) {
    return "Annotation: " + AnnotationNodeRepresentation.INSTANCE.toStringOf(actual);
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
