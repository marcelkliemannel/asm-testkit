package dev.turingcomplete.asmtestkit.assertion.comparator._internal;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class IterableComparator<T> implements Comparator<Iterable<? extends T>> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final Comparator<T> elementsComparator;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public IterableComparator(Comparator<T> elementsComparator) {
    this.elementsComparator = elementsComparator;
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public int compare(Iterable<? extends T> first, Iterable<? extends T> second) {
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

    List<T> firstSorted = StreamSupport.stream(first.spliterator(), false).sorted(elementsComparator).collect(Collectors.toList());
    List<T> secondSorted = StreamSupport.stream(second.spliterator(), false).sorted(elementsComparator).collect(Collectors.toList());

    if (firstSorted.size() != secondSorted.size()) {
      return firstSorted.size() - secondSorted.size();
    }

    Iterator<T> secondSortedIterator = secondSorted.iterator();
    for (T firstElement : firstSorted) {
      T selectElement = secondSortedIterator.next();
      int result = elementsComparator.compare(firstElement, selectElement);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
