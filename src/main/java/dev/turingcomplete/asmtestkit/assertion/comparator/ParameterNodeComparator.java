package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.node.AccessFlags;
import dev.turingcomplete.asmtestkit.asmutils.AccessKind;
import dev.turingcomplete.asmtestkit.assertion.comparator._internal.ComparatorUtils;
import dev.turingcomplete.asmtestkit.assertion.comparator._internal.IterableComparator;
import org.objectweb.asm.tree.ParameterNode;

import java.util.Comparator;

/**
 * A comparison function to order {@link ParameterNode}s.
 *
 * <p>Two {@code ParameterNode}s will be considered as equal if their
 * names and access flags are equal.
 */
public class ParameterNodeComparator extends AsmComparator<ParameterNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link ParameterNodeComparator} instance.
   */
  public static final ParameterNodeComparator INSTANCE = new ParameterNodeComparator();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link ParameterNode}s.
   */
  public static final Comparator<Iterable<? extends ParameterNode>> ITERABLE_INSTANCE = new IterableComparator<>(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected ParameterNodeComparator() {
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link ParameterNodeComparator} instance.
   *
   * @return a new {@link ParameterNodeComparator}; never null;
   */
  public static ParameterNodeComparator create() {
    return new ParameterNodeComparator();
  }

  @Override
  protected int doCompare(ParameterNode first, ParameterNode second) {
    return Comparator.comparing((ParameterNode parameterNode) -> AccessFlags.create(parameterNode.access, AccessKind.PARAMETER),
                                asmComparators.elementComparator(AccessFlags.class))
                     .thenComparing((ParameterNode parameterNode) -> parameterNode.name, ComparatorUtils.STRING_COMPARATOR)
                     .compare(first, second);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
