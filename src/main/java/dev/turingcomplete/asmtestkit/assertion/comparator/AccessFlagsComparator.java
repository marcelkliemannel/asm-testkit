package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.node.AccessFlags;
import dev.turingcomplete.asmtestkit.assertion.comparator._internal.IterableComparator;

import java.util.Comparator;

/**
 * A comparison function to order {@link AccessFlags}s.
 *
 * <p>Two access flags will be considered as equal if their values are equal.
 */
public class AccessFlagsComparator extends AsmComparator<AccessFlags> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link AccessFlagsComparator} instance.
   */
  public static final AccessFlagsComparator INSTANCE = create();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link AccessFlags}s.
   */
  public static final Comparator<Iterable<? extends AccessFlags>> ITERABLE_INSTANCE = new IterableComparator<>(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AccessFlagsComparator() {
    super(AccessFlagsComparator.class, AccessFlags.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link AccessFlagsComparator} instance.
   *
   * @return a new {@link AccessFlagsComparator}; never null;
   */
  public static AccessFlagsComparator create() {
    return new AccessFlagsComparator();
  }

  @Override
  protected int doCompare(AccessFlags first, AccessFlags second) {
    return Integer.compare(first.access(), second.access());
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
