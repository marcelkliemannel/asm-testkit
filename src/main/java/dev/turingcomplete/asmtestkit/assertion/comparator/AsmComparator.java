package dev.turingcomplete.asmtestkit.assertion.comparator;

import org.assertj.core.internal.DescribableComparator;

import java.util.Comparator;

import static dev.turingcomplete.asmtestkit.assertion.comparator._internal.ComparatorUtils.compareNullCheck;

public abstract class AsmComparator<T> extends DescribableComparator<T> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AsmComparator() {
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public final int compare(T first, T second) {
    Integer nullCheckResult = compareNullCheck(first, second);
    return nullCheckResult != null ? nullCheckResult : doCompare(first, second);
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

  @Override
  public String description() {
    return getClass().getSimpleName();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
