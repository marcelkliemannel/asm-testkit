package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.comparator._internal.ComparatorUtils;
import dev.turingcomplete.asmtestkit.assertion.comparator._internal.IterableComparator;
import org.objectweb.asm.tree.LabelNode;

import java.util.Comparator;

/**
 * A comparison function to order {@link LabelNode}s.
 *
 * <p>Two {@code Attribute}s will be considered as equal if their
 * {@link LabelNode#getLabel()}s are equal.
 */
public class LabelNodeComparator extends AsmComparator<LabelNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link LabelNodeComparator} instance.
   */
  public static final LabelNodeComparator INSTANCE = create();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link LabelNode}s.
   */
  public static final Comparator<Iterable<? extends LabelNode>> ITERABLE_INSTANCE = new IterableComparator<>(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected LabelNodeComparator() {
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link LabelNodeComparator} instance.
   *
   * @return a new {@link LabelNodeComparator}; never null;
   */
  public static LabelNodeComparator create() {
    return new LabelNodeComparator();
  }

  @Override
  protected int doCompare(LabelNode first, LabelNode second) {
    return ComparatorUtils.OBJECT_COMPARATOR.compare(first.getLabel(), second.getLabel());
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
