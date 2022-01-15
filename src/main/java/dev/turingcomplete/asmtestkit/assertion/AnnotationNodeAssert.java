package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.comparator.AnnotationNodeComparator;
import dev.turingcomplete.asmtestkit.assertion.option.AssertOption;
import dev.turingcomplete.asmtestkit.assertion.option.StandardAssertOption;
import dev.turingcomplete.asmtestkit.assertion.representation.AnnotationNodeRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.Comparator;

import static dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils.convertAnnotationNodeValuesToMap;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getListFromObjectElseNull;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getStringFromObjectElseNull;

/**
 * An AssertJ {@link AbstractAssert} for an {@link AnnotationNode}.
 *
 * <p>An instance can be created via {@link AsmAssertions#assertThat(AnnotationNode)}
 * or {@link #createForAnnotationNode(AnnotationNode)}.
 *
 * <p>The supported {@link AssertOption} is:
 * {@link StandardAssertOption#IGNORE_ANNOTATION_VALUES}.
 *
 * <p>To override the used {@link AnnotationNodeRepresentation} or
 * {@link AnnotationNodeComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 *
 * @param <S> the 'self' type of {@code this) {@link AbstractAssert}}.
 * @param <A> the type of the actual object.
 */
public class AnnotationNodeAssert<S extends AnnotationNodeAssert<S, A>, A extends AnnotationNode>
        extends AsmAssert<S, A> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes an {@link AnnotationNodeAssert}.
   *
   * @param name                  a short, generic {@link String} name; never null.
   * @param actual                the actual {@link A}; may be null.
   * @param selfType              the {@link Class} of {@code this); never null.
   * @param defaultRepresentation the default {@link Representation}; may be null.
   * @param defaultComparator     the default {@link Comparator}; may be null.
   */
  protected AnnotationNodeAssert(String name,
                                 A actual,
                                 Class<?> selfType,
                                 Representation defaultRepresentation,
                                 Comparator<A> defaultComparator) {

    super(name, actual, selfType, defaultRepresentation, defaultComparator);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link AnnotationNodeAssert} for an {@link AnnotationNode}.
   *
   * @param actual the actual {@link AnnotationNode}; may be null.
   * @return a new {@link AnnotationNodeAssert}; never null.
   */
  public static AnnotationNodeAssert<?, AnnotationNode> createForAnnotationNode(AnnotationNode actual) {
    return new AnnotationNodeAssert<>("Annotation",
                                      actual,
                                      AnnotationNodeAssert.class,
                                      AnnotationNodeRepresentation.INSTANCE,
                                      AnnotationNodeComparator.INSTANCE);
  }

  @Override
  public S isEqualTo(Object expected) {
    hasEqualDescriptor(expected);

    if (!hasOption(StandardAssertOption.IGNORE_ANNOTATION_VALUES)) {
      hasEqualValues(expected);
    }

    //noinspection unchecked
    return (S) this;
  }

  /**
   * Checks whether the {@link AnnotationNode#desc} of the given expected
   * {@link AnnotationNode} is equal to the actual one.
   *
   * @param expected the expected {@link Object}; may be null.
   */
  protected void hasEqualDescriptor(Object expected) {
    Assertions.assertThat(getStringFromObjectElseNull(actual, AnnotationNode.class, annotationNode -> annotationNode.desc))
              .as(createDescription("Has equal descriptor"))
              .isEqualTo(getStringFromObjectElseNull(expected, AnnotationNode.class, annotationNode -> annotationNode.desc));
  }

  /**
   * Checks whether the {@link AnnotationNode#values} of the given expected
   * {@link AnnotationNode} is equal to the actual one.
   *
   * @param expected the expected {@link Object}; may be null.
   */
  protected void hasEqualValues(Object expected) {
    Assertions.assertThat(convertAnnotationNodeValuesToMap(getListFromObjectElseNull(actual, (AnnotationNode annotationNode) -> annotationNode.values)))
              .as(createDescription("Has equal values"))
              .containsExactlyInAnyOrderEntriesOf(convertAnnotationNodeValuesToMap(getListFromObjectElseNull(expected, AnnotationNode.class, annotationNode -> annotationNode.values)));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
