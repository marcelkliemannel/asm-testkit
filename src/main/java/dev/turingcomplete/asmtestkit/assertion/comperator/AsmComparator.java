package dev.turingcomplete.asmtestkit.assertion.comperator;

import java.util.Comparator;

public abstract class AsmComparator<T> implements Comparator<T> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  private static final Comparator<String>  NULL_STRING_COMPARATOR_INSTANCE  = Comparator.nullsFirst(Comparator.naturalOrder());
  private static final Comparator<Integer> NULL_INTEGER_COMPARATOR_INSTANCE = Comparator.nullsFirst(Comparator.naturalOrder());

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
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

  protected final Comparator<String> nullStringComparator() {
    return NULL_STRING_COMPARATOR_INSTANCE;
  }

  protected final Comparator<Integer> nullIntStringComparator() {
    return NULL_INTEGER_COMPARATOR_INSTANCE;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
