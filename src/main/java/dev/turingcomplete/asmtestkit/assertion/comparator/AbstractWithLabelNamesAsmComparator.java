package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.LabelNameLookup;

import java.util.Comparator;
import java.util.Objects;

public abstract class AbstractWithLabelNamesAsmComparator<T> extends AsmComparator<T> implements WithLabelNamesAsmComparator<T> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AbstractWithLabelNamesAsmComparator(Class<?> selfType, Class<?> elementType) {
    super(selfType, elementType);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  public final int compare(T first, T second, LabelNameLookup labelNameLookup) {
    Objects.requireNonNull(labelNameLookup);

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

    return doCompare(first, second, labelNameLookup);
  }

  @Override
  protected int doCompare(T first, T second) {
    return doCompare(first, second, LabelNameLookup.EMPTY);
  }

  /**
   * Compares its two non-null arguments.
   *
   * <p>The same rules to determine the result as defined for the
   * {@link Comparator} apply.
   *
   * @param first           first object to be compared; never null.
   * @param second          object to be compared; never null.
   * @param labelNameLookup the {@link LabelNameLookup}; never null.
   * @return the comparison result.
   */
  protected abstract int doCompare(T first, T second, LabelNameLookup labelNameLookup);

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
