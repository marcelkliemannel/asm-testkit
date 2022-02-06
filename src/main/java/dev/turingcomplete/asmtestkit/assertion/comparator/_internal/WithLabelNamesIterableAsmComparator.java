package dev.turingcomplete.asmtestkit.assertion.comparator._internal;

import dev.turingcomplete.asmtestkit.assertion.LabelIndexLookup;
import dev.turingcomplete.asmtestkit.assertion.comparator.WithLabelNamesAsmComparator;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static dev.turingcomplete.asmtestkit.assertion.comparator._internal.ComparatorUtils.compareNullCheck;

public class WithLabelNamesIterableAsmComparator<T> extends IterableComparator<T> implements WithLabelNamesAsmComparator<Iterable<? extends T>> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected WithLabelNamesIterableAsmComparator(Comparator<T> elementsComparator) {
    super(elementsComparator);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link WithLabelNamesIterableAsmComparator} instance.
   *
   * @return a new {@link WithLabelNamesIterableAsmComparator}; never null;
   */
  public static <T> WithLabelNamesIterableAsmComparator<T> create(Comparator<T> elementsComparator) {
    return new WithLabelNamesIterableAsmComparator<>(elementsComparator);
  }
  
  @Override
  public int compare(Iterable<? extends T> first, Iterable<? extends T> second, LabelIndexLookup labelIndexLookup) {
    Objects.requireNonNull(labelIndexLookup);

    Integer nullCheckResult = compareNullCheck(first, second);
    if (nullCheckResult != null) {
      return nullCheckResult;
    }

    List<T> firstSorted = StreamSupport.stream(first.spliterator(), false).sorted(elementsComparator).collect(Collectors.toList());
    List<T> secondSorted = StreamSupport.stream(second.spliterator(), false).sorted(elementsComparator).collect(Collectors.toList());

    if (firstSorted.size() != secondSorted.size()) {
      return firstSorted.size() - secondSorted.size();
    }

    Iterator<T> secondSortedIterator = secondSorted.iterator();
    for (T firstElement : firstSorted) {
      T selectElement = secondSortedIterator.next();
      int result = elementsComparator instanceof WithLabelNamesAsmComparator
              ? ((WithLabelNamesAsmComparator<T>) elementsComparator).compare(firstElement, selectElement, labelIndexLookup)
              : elementsComparator.compare(firstElement, selectElement);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
