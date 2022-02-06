package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.comparator._internal.ComparatorUtils;
import dev.turingcomplete.asmtestkit.assertion.comparator._internal.IterableComparator;
import dev.turingcomplete.asmtestkit.node.AnnotationDefault;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.Comparator;

/**
 * A comparison function to order {@link AnnotationDefault}s.
 *
 * <p>Two {@code AnnotationDefaultValue}s will be considered as equal if:
 * <ul>
 *   <li>In case of an {@link AnnotationNode}: delegates the comparison to
 *   {@link AnnotationNodeComparator};
 *   <li>Otherwise: If they have the same {@link Object#hashCode()} value.
 * </ul>
 */
public class AnnotationDefaultValueComparator extends AsmComparator<AnnotationDefault> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link AnnotationDefaultValueComparator} instance.
   */
  public static final AnnotationDefaultValueComparator INSTANCE = new AnnotationDefaultValueComparator();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link Object}s.
   */
  public static final Comparator<Iterable<? extends AnnotationDefault>> ITERABLE_INSTANCE = new IterableComparator<>(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AnnotationDefaultValueComparator() {
    super(AnnotationDefaultValueComparator.class, AnnotationDefault.class);
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
  protected int doCompare(AnnotationDefault first, AnnotationDefault second) {
    Object firstValue = first.defaultValue();
    Object secondValue = second.defaultValue();

    if (firstValue instanceof AnnotationNode && secondValue instanceof AnnotationNode) {
      return asmComparators.elementComparator(AnnotationNode.class)
                           .compare((AnnotationNode) firstValue, (AnnotationNode) secondValue);
    }

    return ComparatorUtils.OBJECT_COMPARATOR.compare(firstValue, secondValue);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
