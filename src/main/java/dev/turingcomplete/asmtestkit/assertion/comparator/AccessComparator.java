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
  public static final AccessComparator INSTANCE = new AccessComparator();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  protected int doCompare(Integer first, Integer second) {
    return first.compareTo(second);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
