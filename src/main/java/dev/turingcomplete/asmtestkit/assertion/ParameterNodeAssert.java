package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.comparator.ParameterNodeComparator;
import dev.turingcomplete.asmtestkit.representation.ParameterNodeRepresentation;
import dev.turingcomplete.asmtestkit.node.AccessFlags;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.tree.ParameterNode;

import java.util.Comparator;
import java.util.function.Function;

import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getFromObjectElseNull;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getStringFromObjectElseNull;

/**
 * An AssertJ {@link AbstractAssert} for a {@link ParameterNode}.
 *
 * <p>An instance can be created via
 * {@link AsmAssertions#assertThat(ParameterNode)}. Use
 * {@link AsmAssertions#assertThatParameters(Iterable)} for multiple
 * {@code ParameterNode}s.
 *
 * <p>To override the used {@link ParameterNodeRepresentation} or
 * {@link ParameterNodeComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 */
public class ParameterNodeAssert extends AsmAssert<ParameterNodeAssert, ParameterNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected ParameterNodeAssert(ParameterNode actual) {
    super("Parameter", actual, ParameterNodeAssert.class, ParameterNodeRepresentation.INSTANCE, ParameterNodeComparator.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public ParameterNodeAssert isEqualTo(Object expected) {
    hasEqualName(expected);
    hasEqualAccess(expected);

    return this;
  }

  /**
   * Checks whether the name of the given expected {@link ParameterNode} is
   * equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a
   *                 {@link ParameterNode}; may be null.
   */
  protected void hasEqualName(Object expected) {
    Assertions.assertThat(getStringFromObjectElseNull(actual, (ParameterNode parameterNode) -> parameterNode.name))
              .as(createCrumbDescription("Has equal name"))
              .isEqualTo(getFromObjectElseNull(expected, ParameterNode.class, parameterNode -> parameterNode.name));
  }

  /**
   * Checks whether the access flags of the given expected {@link ParameterNode}
   * are equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a
   *                 {@link ParameterNode}; may be null.
   */
  protected void hasEqualAccess(Object expected) {
    Function<ParameterNode, AccessFlags> getParameterAccess = parameterNode -> AccessFlags.forParameter(parameterNode.access);
    AsmAssertions.assertThat(getFromObjectElseNull(actual, getParameterAccess))
                 .as(createCrumbDescription("Has equal access"))
                 .isEqualTo(getFromObjectElseNull(expected, ParameterNode.class, getParameterAccess));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
