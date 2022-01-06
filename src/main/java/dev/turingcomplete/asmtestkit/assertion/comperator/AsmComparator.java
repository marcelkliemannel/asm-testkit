package dev.turingcomplete.asmtestkit.assertion.comperator;

import java.util.Comparator;

public abstract class AsmComparator<T> implements Comparator<T> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  private static final Comparator<String>  STRING_COMPARATOR_INSTANCE  = Comparator.nullsFirst(Comparator.naturalOrder());
  private static final Comparator<Integer> INTEGER_COMPARATOR_INSTANCE = Comparator.nullsFirst(Comparator.naturalOrder());

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

  /**
   * Gets a {@link Comparator} for {@link String}s which can handle null
   * values by using {@link Comparator#nullsFirst(Comparator)}.
   *
   * @return a {@link Comparator} for {@link String}s; never null.
   */
  protected final Comparator<String> stringComparator() {
    return STRING_COMPARATOR_INSTANCE;
  }

  /**
   * Gets a {@link Comparator} for {@link Integer}s which can handle null
   * values by using {@link Comparator#nullsFirst(Comparator)}.
   *
   * @return a {@link Comparator} for {@link Integer}s; never null.
   */
  protected final Comparator<Integer> integerComparator() {
    return INTEGER_COMPARATOR_INSTANCE;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
