package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.option.AssertOption;
import org.assertj.core.api.Assertions;
import org.objectweb.asm.tree.AnnotationNode;

import static dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils.convertAnnotationNodeValuesToMap;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getListFromObjectElseNull;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getStringFromObjectElseNull;

abstract class AbstractAnnotationNodeAssert<S extends AbstractAnnotationNodeAssert<S, A>, A extends AnnotationNode>
        extends AsmAssert<S, A> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public AbstractAnnotationNodeAssert(A actual,
                                      Class<S> selfType,
                                      Class<A> objectType,
                                      String selfDescription,
                                      AssertOption... assertOptions) {

    super(actual, selfType, objectType, selfDescription, assertOptions);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public S isEqualTo(Object expected) {
    isEqualDescriptor(expected);
    areEqualValues(expected);

    //noinspection unchecked
    return (S) this;
  }

  /**
   * Checks whether the {@link AnnotationNode#desc} of the given expected
   * {@link AnnotationNode} is equal to the actual one.
   *
   * @param expected the expected {@link Object}; may be null.
   */
  protected void isEqualDescriptor(Object expected) {
    Assertions.assertThat(getStringFromObjectElseNull(actual, AnnotationNode.class, annotationNode -> annotationNode.desc))
              .as(createDescription("Is equal descriptor"))
              .isEqualTo(getStringFromObjectElseNull(expected, AnnotationNode.class, annotationNode -> annotationNode.desc));
  }

  /**
   * Checks whether the {@link AnnotationNode#values} of the given expected
   * {@link AnnotationNode} is equal to the actual one.
   *
   * @param expected the expected {@link Object}; may be null.
   */
  protected void areEqualValues(Object expected) {
    Assertions.assertThat(convertAnnotationNodeValuesToMap(actual.values))
              .as(createDescription("Are equal values"))
              .containsExactlyInAnyOrderEntriesOf(convertAnnotationNodeValuesToMap(getListFromObjectElseNull(expected, AnnotationNode.class, annotationNode -> annotationNode.values)));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
