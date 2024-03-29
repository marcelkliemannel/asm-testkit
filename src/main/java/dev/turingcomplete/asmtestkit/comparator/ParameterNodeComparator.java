package dev.turingcomplete.asmtestkit.comparator;

import dev.turingcomplete.asmtestkit.asmutils.AccessKind;
import dev.turingcomplete.asmtestkit.comparator._internal.ComparatorUtils;
import dev.turingcomplete.asmtestkit.comparator._internal.IterableComparator;
import dev.turingcomplete.asmtestkit.node.AccessNode;
import org.objectweb.asm.tree.ParameterNode;

import java.util.Comparator;

/**
 * A comparison function to order {@link ParameterNode}s.
 *
 * <p>Two {@code ParameterNode}s will be considered as equal if all the
 * {@code public} {@link ParameterNode} fields are equal. Otherwise, they will
 * be ordered by the comparison of the first non-matching field.
 */
public class ParameterNodeComparator extends AsmComparator<ParameterNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link ParameterNodeComparator} instance.
   */
  public static final ParameterNodeComparator INSTANCE = create();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link ParameterNode}s.
   */
  public static final Comparator<Iterable<? extends ParameterNode>> ITERABLE_INSTANCE = new IterableComparator<>(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected ParameterNodeComparator() {
    super(ParameterNodeComparator.class, ParameterNode.class);
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
    return Comparator.comparing((ParameterNode parameterNode) -> AccessNode.create(parameterNode.access, AccessKind.PARAMETER),
                                asmComparators.elementComparator(AccessNode.class))
                     .thenComparing((ParameterNode parameterNode) -> parameterNode.name, ComparatorUtils.STRING_COMPARATOR)
                     .compare(first, second);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
