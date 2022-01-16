package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.comparator.LocalVariableNodeComparator;
import dev.turingcomplete.asmtestkit.assertion.option.AssertOption;
import dev.turingcomplete.asmtestkit.assertion.representation.LocalVariableNodeRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LocalVariableNode;

import java.util.Comparator;

import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getFromObjectElseNull;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getIntegerFromObjectElseNull;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getStringFromObjectElseNull;

/**
 * An AssertJ {@link AbstractAssert} for a {@link LocalVariableNode}.
 *
 * <p>An instance can be created via
 * {@link AsmAssertions#assertThat(LocalVariableNode)}. Use
 * {@link AsmAssertions#assertThatLocalVariables(Iterable)} for multiple
 * {@code LocalVariableNode}s.
 *
 * <p>There are no direct supported {@link AssertOption}s yet.
 *
 * <p>To override the used {@link LocalVariableNodeRepresentation} or
 * {@link LocalVariableNodeComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 */
public class LocalVariableNodeAssert extends AsmAssert<LocalVariableNodeAssert, LocalVariableNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes an {@link LocalVariableNodeAssert}.
   *
   * @param actual the actual {@link LocalVariableNode}; may be null.
   */
  protected LocalVariableNodeAssert(LocalVariableNode actual) {
    super("Local Variable",
          actual,
          LocalVariableNodeAssert.class,
          LocalVariableNodeRepresentation.INSTANCE,
          LocalVariableNodeComparator.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public LocalVariableNodeAssert isEqualTo(Object expected) {
    hasEqualIndex(expected);
    hasEqualName(expected);
    hasEqualDescriptor(expected);
    hasEqualSignature(expected);
    hasEqualStartLabel(expected);
    hasEqualEndLabel(expected);

    return this;
  }

  /**
   * Checks whether the index of the given expected {@link LocalVariableNode} is
   * equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a
   *                 {@link LocalVariableNode}; may be null.
   */
  protected void hasEqualIndex(Object expected) {
    Assertions.assertThat(getIntegerFromObjectElseNull(actual, (LocalVariableNode localVariableNode) -> localVariableNode.index))
              .as(createDescription("Has equal index"))
              .isEqualTo(getFromObjectElseNull(expected, LocalVariableNode.class, localVariableNode -> localVariableNode.index));
  }

  /**
   * Checks whether the name of the given expected {@link LocalVariableNode} is
   * equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a
   *                 {@link LocalVariableNode}; may be null.
   */
  protected void hasEqualName(Object expected) {
    Assertions.assertThat(getStringFromObjectElseNull(actual, (LocalVariableNode localVariableNode) -> localVariableNode.name))
              .as(createDescription("Has equal name"))
              .isEqualTo(getStringFromObjectElseNull(expected, LocalVariableNode.class, localVariableNode -> localVariableNode.name));
  }

  /**
   * Checks whether the descriptor of the given expected {@link LocalVariableNode}
   * is equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a
   *                 {@link LocalVariableNode}; may be null.
   */
  protected void hasEqualDescriptor(Object expected) {
    Assertions.assertThat(getStringFromObjectElseNull(actual, (LocalVariableNode localVariableNode) -> localVariableNode.desc))
              .as(createDescription("Has equal descriptor"))
              .isEqualTo(getStringFromObjectElseNull(expected, LocalVariableNode.class, localVariableNode -> localVariableNode.desc));
  }

  /**
   * Checks whether the signature of the given expected {@link LocalVariableNode}
   * is equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a
   *                 {@link LocalVariableNode}; may be null.
   */
  protected void hasEqualSignature(Object expected) {
    Assertions.assertThat(getStringFromObjectElseNull(actual, (LocalVariableNode localVariableNode) -> localVariableNode.signature))
              .as(createDescription("Has equal signature"))
              .isEqualTo(getStringFromObjectElseNull(expected, LocalVariableNode.class, localVariableNode -> localVariableNode.signature));
  }

  /**
   * Checks whether the start {@link LabelNode} of the given expected
   * {@link LocalVariableNode} is equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a
   *                 {@link LocalVariableNode}; may be null.
   */
  protected void hasEqualStartLabel(Object expected) {
    AsmAssertions.assertThat(getFromObjectElseNull(actual, (LocalVariableNode localVariableNode) -> localVariableNode.start))
                 .as(createDescription("Has equal start label"))
                 .isEqualTo(getFromObjectElseNull(expected, LocalVariableNode.class, localVariableNode -> localVariableNode.start));
  }

  /**
   * Checks whether the end {@link LabelNode} of the given expected
   * {@link LocalVariableNode} is equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a
   *                 {@link LocalVariableNode}; may be null.
   */
  protected void hasEqualEndLabel(Object expected) {
    AsmAssertions.assertThat(getFromObjectElseNull(actual, (LocalVariableNode localVariableNode) -> localVariableNode.end))
                 .as(createDescription("Has equal end label"))
                 .isEqualTo(getFromObjectElseNull(expected, LocalVariableNode.class, localVariableNode -> localVariableNode.end));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
