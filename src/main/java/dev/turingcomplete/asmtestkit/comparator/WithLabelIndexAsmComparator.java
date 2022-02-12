package dev.turingcomplete.asmtestkit.comparator;

import dev.turingcomplete.asmtestkit.common.LabelIndexLookup;
import dev.turingcomplete.asmtestkit.comparator._internal.WithLabelIndexAsmComparatorAdapter;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

/**
 * A comparison function, that offers additional ordering functionally to take
 * label indices into account.
 *
 * @param <T> the type of objects that may be compared by this comparator.
 */
public interface WithLabelIndexAsmComparator<T> extends Comparator<T> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  int compare(T first, T second, LabelIndexLookup labelIndexLookup);

  /**
   * Accepts a function that extracts a sort key from a type {@code T}, and
   * returns a {@code Comparator<T>} that compares by that sort key using
   * the specified {@link Comparator}.
   *
   * @param <T>              the type of element to be compared
   * @param <U>              the type of the sort key
   * @param keyExtractor     the function used to extract the sort key; never null.
   * @param keyComparator    the {@link Comparator} used to compare the sort key;
   *                         never null.
   * @param labelIndexLookup the {@link LabelIndexLookup} to use; never null.
   * @return a comparator that compares by an extracted key using the
   * specified {@link Comparator}; never null.
   */
  static <T, U> WithLabelIndexAsmComparator<T> comparing(Function<? super T, ? extends U> keyExtractor,
                                                         Comparator<U> keyComparator,
                                                         LabelIndexLookup labelIndexLookup) {
    Objects.requireNonNull(keyExtractor);
    Objects.requireNonNull(keyComparator);

    // The keyComparator must always be wrapped regardless if it is a
    // WithLabelIndexAsmComparator or not. Otherwise, a subsequent call to
    // thenComparing would lose the labelIndexLookup capability.
    var keyExtractingComparator = new WithLabelIndexAsmComparator<T>() {

      final Comparator<U> _keyComparator = WithLabelIndexAsmComparatorAdapter.wrap(keyComparator, labelIndexLookup);

      @Override
      public int compare(T first, T second) {
        return compare(first, second, labelIndexLookup);
      }

      @Override
      public int compare(T first, T second, LabelIndexLookup labelIndexLookup) {
        if (_keyComparator instanceof WithLabelIndexAsmComparator) {
          return ((WithLabelIndexAsmComparator<U>) _keyComparator).compare(keyExtractor.apply(first), keyExtractor.apply(second), labelIndexLookup);
        }
        else {
          return _keyComparator.compare(keyExtractor.apply(first), keyExtractor.apply(second));
        }
      }
    };
    return WithLabelIndexAsmComparatorAdapter.wrap(keyExtractingComparator, labelIndexLookup);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
