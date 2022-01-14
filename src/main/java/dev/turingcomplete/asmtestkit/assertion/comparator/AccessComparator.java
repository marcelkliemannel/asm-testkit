package dev.turingcomplete.asmtestkit.assertion.comparator;

/**
 * A comparison function to order {@link Integer} access flags.
 *
 * <p>Two access flags will be considered as equal if their values are equal.
 */
public class AccessComparator extends AsmComparator<Integer> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link AccessComparator} instance.
   */
  public static final AccessComparator INSTANCE = create();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AccessComparator() {
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link AccessComparator} instance.
   *
   * @return a new {@link AccessComparator}; never null;
   */
  public static AccessComparator create() {
    return new AccessComparator();
  }

  @Override
  protected int doCompare(Integer first, Integer second) {
    return first.compareTo(second);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
