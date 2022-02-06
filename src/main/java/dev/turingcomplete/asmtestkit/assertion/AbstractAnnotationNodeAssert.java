package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.option.StandardAssertOption;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.Comparator;

import static dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils.convertAnnotationNodeValuesToMap;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getListFromObjectElseNull;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getStringFromObjectElseNull;

/**
 * The base class for an AssertJ {@link AbstractAssert} for an
 * {@link AnnotationNode} or its subtypes.
 *
 * @param <S> the 'self' type of {@code this} {@link AsmAssert}.
 * @param <A> an {@link AnnotationNode} or a subtype of the actual object.
 */
public abstract class AbstractAnnotationNodeAssert<S extends AsmAssert<S, A>, A extends AnnotationNode>
        extends AsmAssert<S, A> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AbstractAnnotationNodeAssert(String name,
                                         A actual,
                                         Class<?> selfType,
                                         Representation defaultRepresentation,
                                         Comparator<A> defaultComparator) {

    super(name, actual, selfType, defaultRepresentation, defaultComparator);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

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
              .as(createCrumbDescription("Has equal descriptor"))
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
              .as(createCrumbDescription("Has equal values"))
              .containsExactlyInAnyOrderEntriesOf(convertAnnotationNodeValuesToMap(getListFromObjectElseNull(expected, AnnotationNode.class, annotationNode -> annotationNode.values)));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
