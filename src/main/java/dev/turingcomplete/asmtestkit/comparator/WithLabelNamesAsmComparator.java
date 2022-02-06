package dev.turingcomplete.asmtestkit.comparator;

import dev.turingcomplete.asmtestkit.assertion.LabelIndexLookup;
import dev.turingcomplete.asmtestkit.comparator._internal.WithLabelNamesAsmComparatorAdapter;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

/**
 * A comparison function, that offers additional ordering functionally to take
 * label names into account.
 *
 * @param <T> the type of objects that may be compared by this comparator.
 */
public interface WithLabelNamesAsmComparator<T> extends Comparator<T> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  int compare(T first, T second, LabelIndexLookup labelIndexLookup);

  /**
   * Accepts a function that extracts a sort key from a type {@code T}, and
   * returns a {@code Comparator<T>} that compares by that sort key using
   * the specified {@link Comparator}.
   *
   * @param <T>           the type of element to be compared
   * @param <U>           the type of the sort key
   * @param keyExtractor  the function used to extract the sort key; never null.
   * @param keyComparator the {@link Comparator} used to compare the sort key;
   *                      never null.
   * @return a comparator that compares by an extracted key using the
   * specified {@link Comparator}; never null.
   */
  static <T, U> WithLabelNamesAsmComparator<T> comparing(Function<? super T, ? extends U> keyExtractor,
                                                         Comparator<U> keyComparator,
                                                         LabelIndexLookup labelIndexLookup) {
    Objects.requireNonNull(keyExtractor);
    Objects.requireNonNull(keyComparator);

    // The keyComparator must always be wrapped regardless if it is a
    // WithLabelNamesAsmComparator or not. Otherwise, a subsequent call to
    // thenComparing would lose the labelNameLookup capability.
    var keyExtractingComparator = new WithLabelNamesAsmComparator<T>() {
      final Comparator<U> _keyComparator = WithLabelNamesAsmComparatorAdapter.wrap(keyComparator, labelIndexLookup);

      @Override
      public int compare(T first, T second) {
        return compare(first, second, labelIndexLookup);
      }

      @Override
      public int compare(T first, T second, LabelIndexLookup labelNameLookup) {
        if (_keyComparator instanceof WithLabelNamesAsmComparator) {
          return ((WithLabelNamesAsmComparator<U>) _keyComparator).compare(keyExtractor.apply(first), keyExtractor.apply(second), labelNameLookup);
        }
        else {
          return _keyComparator.compare(keyExtractor.apply(first), keyExtractor.apply(second));
        }
      }
    };
    return WithLabelNamesAsmComparatorAdapter.wrap(keyExtractingComparator, labelIndexLookup);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
