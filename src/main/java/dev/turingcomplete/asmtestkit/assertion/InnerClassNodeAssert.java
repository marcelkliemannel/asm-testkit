package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.asmutils.TypeUtils;
import dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils;
import dev.turingcomplete.asmtestkit.assertion.option.StandardAssertOption;
import dev.turingcomplete.asmtestkit.comparator.InnerClassNodeComparator;
import dev.turingcomplete.asmtestkit.node.AccessFlags;
import dev.turingcomplete.asmtestkit.representation.InnerClassNodeRepresentation;
import org.assertj.core.api.Assertions;
import org.objectweb.asm.tree.InnerClassNode;

import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThat;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getFromObjectElseNull;

public class InnerClassNodeAssert extends AsmAssert<InnerClassNodeAssert, InnerClassNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes an {@link InnerClassNodeAssert}.
   *
   * @param actual the actual {@link InnerClassNode}; may be null.
   */
  protected InnerClassNodeAssert(InnerClassNode actual) {
    super("Inner class", actual, InnerClassNodeAssert.class, InnerClassNodeRepresentation.INSTANCE, InnerClassNodeComparator.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public InnerClassNodeAssert isEqualTo(Object expected) {
    hasEqualName(expected);
    hasEqualOuterName(expected);
    hasEqualInnerName(expected);
    hasEqualAccess(expected);

    return this;
  }

  /**
   * Checks whether the name of the given expected {@link InnerClassNode} is
   * equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a {@link InnerClassNode};
   *                 may be null.
   */
  protected void hasEqualName(Object expected) {
    assertThat(AssertUtils.getFromObjectElseNull(actual, (InnerClassNode innerClassNode) -> TypeUtils.nameToTypeElseNull(innerClassNode.name)))
            .as(createCrumbDescription("Has equal name"))
            .addOptions(options)
            .isEqualTo(AssertUtils.getFromObjectElseNull(expected, InnerClassNode.class, (InnerClassNode innerClassNode) -> TypeUtils.nameToTypeElseNull(innerClassNode.name)));
  }

  /**
   * Checks whether the outer name of the given expected {@link InnerClassNode}
   * is equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a {@link InnerClassNode};
   *                 may be null.
   */
  protected void hasEqualOuterName(Object expected) {
    assertThat(AssertUtils.getFromObjectElseNull(actual, (InnerClassNode innerClassNode) -> TypeUtils.nameToTypeElseNull(innerClassNode.outerName)))
            .as(createCrumbDescription("Has equal outer name"))
            .addOptions(options)
            .isEqualTo(AssertUtils.getFromObjectElseNull(expected, InnerClassNode.class, (InnerClassNode innerClassNode) -> TypeUtils.nameToTypeElseNull(innerClassNode.outerName)));
  }

  /**
   * Checks whether the inner name of the given expected {@link InnerClassNode}
   * is equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a {@link InnerClassNode};
   *                 may be null.
   */
  protected void hasEqualInnerName(Object expected) {
    Assertions.assertThat(AssertUtils.getFromObjectElseNull(actual, (InnerClassNode innerClassNode) -> innerClassNode.innerName))
              .as(createCrumbDescription("Has equal inner name"))
              .isEqualTo(AssertUtils.getFromObjectElseNull(expected, InnerClassNode.class, (InnerClassNode innerClassNode) -> innerClassNode.innerName));
  }


  /**
   * Checks whether the access flags of the given expected {@link InnerClassNode}
   * are equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a {@link InnerClassNode};
   *                 may be null.
   */
  protected void hasEqualAccess(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_ACCESS)) {
      return;
    }

    assertThat(getFromObjectElseNull(actual, (InnerClassNode innerClassNode) -> AccessFlags.forClass(innerClassNode.access)))
            .addOptions(options)
            .as(createCrumbDescription("Has equal access"))
            .isEqualTo(getFromObjectElseNull(expected, InnerClassNode.class, (InnerClassNode innerClassNode) -> AccessFlags.forClass(innerClassNode.access)));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
