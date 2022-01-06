package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.option.AssertOption;
import dev.turingcomplete.asmtestkit.assertion.representation.AnnotationNodeRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.tree.AnnotationNode;

import static dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils.convertAnnotationNodeValuesToMap;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getListFromObjectElseNull;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getStringFromObjectElseNull;

/**
 * An AssertJ {@link AbstractAssert} for an {@link AnnotationNode}.
 *
 * <p>To override the used {@link AnnotationNodeRepresentation} call
 * {@link #withRepresentation(Representation)}.
 *
 * <p>An instance can be created via {@link AsmAssertions#assertThat(AnnotationNode)}.
 */
public class AnnotationNodeAssert extends AsmAssert<AnnotationNodeAssert, AnnotationNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public AnnotationNodeAssert(AnnotationNode actual, AssertOption... assertOptions) {
    super(actual, AnnotationNodeAssert.class, AnnotationNode.class, createSelfDescription(actual), assertOptions);

    info.useRepresentation(AnnotationNodeRepresentation.instance());
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public AnnotationNodeAssert isEqualTo(Object expected) {
    Assertions.assertThat(getStringFromObjectElseNull(actual, AnnotationNode.class, annotationNode -> annotationNode.desc))
            .as(createDescription("Is equal descriptor"))
            .isEqualTo(getStringFromObjectElseNull(expected, AnnotationNode.class, annotationNode -> annotationNode.desc));

    Assertions.assertThat(convertAnnotationNodeValuesToMap(actual.values))
              .as(createDescription("Are equal values"))
              .containsExactlyInAnyOrderEntriesOf(convertAnnotationNodeValuesToMap(getListFromObjectElseNull(expected, AnnotationNode.class, annotationNode -> annotationNode.values)));

    return this;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private static String createSelfDescription(AnnotationNode actual) {
    return "Annotation: " + AnnotationNodeRepresentation.instance().toStringOf(actual);
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
