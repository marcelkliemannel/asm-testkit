package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.option.AssertOption;
import dev.turingcomplete.asmtestkit.assertion.representation.AnnotationNodeRepresentation;
import org.assertj.core.api.Assertions;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.Optional;

import static dev.turingcomplete.asmtestkit.AnnotationNodeUtils.annotationNodeValuesToMap;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getListFromObjectElseNull;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getStringFromObjectElseNull;

public final class AnnotationNodeAssert extends AsmAssert<AnnotationNodeAssert, AnnotationNode> {
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

    Assertions.assertThat(annotationNodeValuesToMap(actual.values))
              .as(createDescription("Are equal values"))
              .containsExactlyInAnyOrderEntriesOf(annotationNodeValuesToMap(getListFromObjectElseNull(expected, AnnotationNode.class, annotationNode -> annotationNode.values)));

    return this;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private static String createSelfDescription(AnnotationNode actual) {
    return "Annotation: " + Optional.ofNullable(actual).map(_actual -> _actual.desc).orElse(null);
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
