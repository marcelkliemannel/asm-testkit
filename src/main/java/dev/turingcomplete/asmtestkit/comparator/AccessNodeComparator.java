package dev.turingcomplete.asmtestkit.comparator;

import dev.turingcomplete.asmtestkit.comparator._internal.IterableComparator;
import dev.turingcomplete.asmtestkit.node.AccessNode;

import java.util.Comparator;

/**
 * A comparison function to order {@link AccessNode}s.
 *
 * <p>Two access flags will be considered as equal if their values are equal.
 */
public class AccessNodeComparator extends AsmComparator<AccessNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link AccessNodeComparator} instance.
   */
  public static final AccessNodeComparator INSTANCE = create();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link AccessNode}s.
   */
  public static final Comparator<Iterable<? extends AccessNode>> ITERABLE_INSTANCE = new IterableComparator<>(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AccessNodeComparator() {
    super(AccessNodeComparator.class, AccessNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link AccessNodeComparator} instance.
   *
   * @return a new {@link AccessNodeComparator}; never null;
   */
  public static AccessNodeComparator create() {
    return new AccessNodeComparator();
  }

  @Override
  protected int doCompare(AccessNode first, AccessNode second) {
    return Integer.compare(first.access(), second.access());
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
