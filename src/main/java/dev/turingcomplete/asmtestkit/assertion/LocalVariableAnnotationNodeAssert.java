package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.comparator.LocalVariableAnnotationNodeComparator;
import dev.turingcomplete.asmtestkit.assertion.option.AssertOption;
import dev.turingcomplete.asmtestkit.assertion.option.StandardAssertOption;
import dev.turingcomplete.asmtestkit.assertion.representation.LocalVariableAnnotationNodeRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.LocalVariableAnnotationNode;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getListFromObjectElseNull;

/**
 * An AssertJ {@link AbstractAssert} for a {@link LocalVariableAnnotationNode}.
 *
 * <p>An instance can be created via
 * {@link AsmAssertions#assertThat(LocalVariableAnnotationNode)}.
 * Use {@link AsmAssertions#assertThatLocalVariableAnnotations(Iterable)} for
 * multiple {@code LocalVariableAnnotationNode}s.
 *
 * <p>The supported {@link AssertOption} is
 * {@link StandardAssertOption#IGNORE_ANNOTATION_VALUES}.
 *
 * <p>To override the used {@link LocalVariableAnnotationNodeRepresentation} or
 * {@link LocalVariableAnnotationNodeComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 */
public class LocalVariableAnnotationNodeAssert
        extends TypeAnnotationNodeAssert<LocalVariableAnnotationNodeAssert, LocalVariableAnnotationNode> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  private final Map<Label, String> labelNames = new HashMap<>();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes a {@link LocalVariableAnnotationNodeAssert}.
   *
   * @param actual the actual {@link LocalVariableAnnotationNode}; may be null.
   */
  protected LocalVariableAnnotationNodeAssert(LocalVariableAnnotationNode actual) {
    super("Local Variable Annotation",
          actual,
          LocalVariableAnnotationNodeAssert.class,
          LocalVariableAnnotationNodeRepresentation.INSTANCE,
          LocalVariableAnnotationNodeComparator.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  public LocalVariableAnnotationNodeAssert useLabelNames(Map<Label, String> labelNames) {
    this.labelNames.putAll(labelNames);

    return this;
  }

  @Override
  public LocalVariableAnnotationNodeAssert isEqualTo(Object expected) {
    super.isEqualTo(expected);

    areEqualRanges(expected);

    return this;
  }

  protected void areEqualRanges(Object expected) {
    var localVariableAnnotationNodeRepresentation = getLocalVariableAnnotationNodeRepresentation();

    Function<LocalVariableAnnotationNode, List<String>> getRanges = localVariableAnnotationNode -> localVariableAnnotationNodeRepresentation.toRangeStringOf(localVariableAnnotationNode, labelNames);
    List<String> actualRanges = getListFromObjectElseNull(actual, getRanges);
    List<String> expectedRanges = getListFromObjectElseNull(expected, LocalVariableAnnotationNode.class, getRanges);

    Assertions.assertThat(actualRanges)
              .as(createDescription("Are equal ranges"))
              .containsExactlyElementsOf(expectedRanges);
  }

  protected LocalVariableAnnotationNodeRepresentation getLocalVariableAnnotationNodeRepresentation() {
    return info.representation() instanceof LocalVariableAnnotationNodeRepresentation
            ? (LocalVariableAnnotationNodeRepresentation) info.representation()
            : LocalVariableAnnotationNodeRepresentation.INSTANCE;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
