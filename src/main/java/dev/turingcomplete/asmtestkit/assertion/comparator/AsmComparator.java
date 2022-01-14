package dev.turingcomplete.asmtestkit.assertion.comparator;

import java.util.Comparator;

public abstract class AsmComparator<T> implements Comparator<T> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AsmComparator() {
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public final int compare(T first, T second) {
    // Instance null check
    if (first != null && second == null) {
      return 1;
    }
    else if (first == null && second != null) {
      return -1;
    }
    else if (first == null) { // Both null
      return 0;
    }

    return doCompare(first, second);
  }

  /**
   * Compares its two non-null arguments.
   *
   * <p>The same rules to determine the result as defined for the
   * {@link Comparator} apply.
   *
   * @param first  first object to be compared; never null.
   * @param second object to be compared; never null.
   * @return the comparison result.
   */
  protected abstract int doCompare(T first, T second);

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
