package dev.turingcomplete.asmtestkit.comparator;

import dev.turingcomplete.asmtestkit.comparator._internal.ComparatorUtils;
import dev.turingcomplete.asmtestkit.comparator._internal.IterableComparator;
import dev.turingcomplete.asmtestkit.node.AnnotationDefaultNode;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.Comparator;

/**
 * A comparison function to order {@link AnnotationDefaultNode}s.
 *
 * <p>Two {@code AnnotationDefaultValue}s will be considered as equal if:
 * <ul>
 *   <li>In case of an {@link AnnotationNode}: delegates the comparison to
 *   {@link AnnotationNodeComparator};
 *   <li>Otherwise: If they have the same {@link Object#hashCode()} value.
 * </ul>
 */
public class AnnotationDefaultValueComparator extends AsmComparator<AnnotationDefaultNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link AnnotationDefaultValueComparator} instance.
   */
  public static final AnnotationDefaultValueComparator INSTANCE = new AnnotationDefaultValueComparator();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link Object}s.
   */
  public static final Comparator<Iterable<? extends AnnotationDefaultNode>> ITERABLE_INSTANCE = new IterableComparator<>(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AnnotationDefaultValueComparator() {
    super(AnnotationDefaultValueComparator.class, AnnotationDefaultNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link AnnotationDefaultValueComparator} instance.
   *
   * @return a new {@link AnnotationDefaultValueComparator}; never null;
   */
  public static AnnotationDefaultValueComparator create() {
    return new AnnotationDefaultValueComparator();
  }

  @Override
  protected int doCompare(AnnotationDefaultNode first, AnnotationDefaultNode second) {
    Object firstValue = first.value();
    Object secondValue = second.value();

    if (firstValue instanceof AnnotationNode && secondValue instanceof AnnotationNode) {
      return asmComparators.elementComparator(AnnotationNode.class)
                           .compare((AnnotationNode) firstValue, (AnnotationNode) secondValue);
    }

    return ComparatorUtils.OBJECT_COMPARATOR.compare(firstValue, secondValue);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
