package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.comparator.MethodNodeComparator;
import dev.turingcomplete.asmtestkit.representation.MethodNodeRepresentation;
import org.assertj.core.api.AbstractIterableAssert;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Comparator;

/**
 * An AssertJ {@link AbstractIterableAssert} for an {@link Iterable} of
 * {@link MethodNode}s which will use the {@link MethodNodeComparator} to
 * determine the equality.
 *
 * <p>An instance can be created via {@link AsmAssertions#assertThatMethods(Iterable)}.
 *
 * <p>To override the used {@link MethodNodeRepresentation} or
 * {@link MethodNodeComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 */
public class MethodNodesAssert extends AsmIterableAssert<MethodNodesAssert, MethodNode, MethodNodeAssert> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes an {@link MethodNodesAssert}.
   *
   * @param actual the actual {@link Iterable} of {@link MethodNode}s; may be
   *               null.
   */
  @SuppressWarnings("ResultOfMethodCallIgnored")
  protected MethodNodesAssert(Iterable<? extends MethodNode> actual) {
    super(actual, MethodNodesAssert.class, AsmAssertions::assertThat);

    as("Methods");
    withRepresentation(MethodNodeRepresentation.INSTANCE);
    setComparators(false);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Exclude {@link LineNumberNode}s (and its associated {@link LabelNode}s)
   * from the comparison.
   *
   * <p>This method will override the previously set {@link Comparator}s.
   *
   * @return {@code this} {@link MethodNodesAssert}; never null.
   */
  public MethodNodesAssert ignoreLineNumbers() {
    setComparators(true);
    setElementAssertCreator(methodNode -> AsmAssertions.assertThat(methodNode).ignoreLineNumbers());

    return this;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  @SuppressWarnings("ResultOfMethodCallIgnored")
  private void setComparators(boolean ignoreLineNumbers) {
    if (!ignoreLineNumbers) {
      usingElementComparator(MethodNodeComparator.INSTANCE);
      usingComparator(MethodNodeComparator.ITERABLE_INSTANCE);
    }
    else {
      usingElementComparator(MethodNodeComparator.INSTANCE_IGNORE_LINE_NUMBERS);
      usingComparator(MethodNodeComparator.ITERABLE_INSTANCE_IGNORE_LINE_NUMBERS);
    }
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
