package dev.turingcomplete.asmtestkit.comparator;

import dev.turingcomplete.asmtestkit.assertion.DefaultLabelIndexLookup;
import dev.turingcomplete.asmtestkit.assertion.LabelIndexLookup;

import java.util.Comparator;
import java.util.Objects;

public abstract class AbstractWithLabelIndexAsmComparator<T> extends AsmComparator<T> implements WithLabelIndexAsmComparator<T> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AbstractWithLabelIndexAsmComparator(Class<?> selfType, Class<?> elementType) {
    super(selfType, elementType);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  public final int compare(T first, T second, LabelIndexLookup labelIndexLookup) {
    Objects.requireNonNull(labelIndexLookup);

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

    return doCompare(first, second, labelIndexLookup);
  }

  @Override
  protected int doCompare(T first, T second) {
    return doCompare(first, second, DefaultLabelIndexLookup.create());
  }

  /**
   * Compares its two non-null arguments.
   *
   * <p>The same rules to determine the result as defined for the
   * {@link Comparator} apply.
   *
   * @param first           first object to be compared; never null.
   * @param second          object to be compared; never null.
   * @param labelIndexLookup the {@link LabelIndexLookup}; never null.
   * @return the comparison result.
   */
  protected abstract int doCompare(T first, T second, LabelIndexLookup labelIndexLookup);

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
