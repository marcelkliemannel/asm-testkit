package dev.turingcomplete.asmtestkit.assertion.comparator._internal;

import dev.turingcomplete.asmtestkit.assertion.LabelNameLookup;
import dev.turingcomplete.asmtestkit.assertion.comparator.WithLabelNamesAsmComparator;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static dev.turingcomplete.asmtestkit.assertion.comparator._internal.ComparatorUtils.compareNullCheck;

public class WithLabelNamesIterableComparator<T> extends IterableComparator<T> implements WithLabelNamesAsmComparator<Iterable<? extends T>> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public WithLabelNamesIterableComparator(Comparator<T> elementsComparator) {
    super(elementsComparator);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public int compare(Iterable<? extends T> first, Iterable<? extends T> second, LabelNameLookup labelNameLookup) {
    Objects.requireNonNull(labelNameLookup);

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
              ? ((WithLabelNamesAsmComparator<T>) elementsComparator).compare(firstElement, selectElement, labelNameLookup)
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
