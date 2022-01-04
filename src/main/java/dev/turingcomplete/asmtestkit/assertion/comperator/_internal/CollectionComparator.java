package dev.turingcomplete.asmtestkit.assertion.comperator._internal;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public final class CollectionComparator<T> implements Comparator<Collection<T>> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final Comparator<T> elementComparator;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public CollectionComparator(Comparator<T> elementComparator) {
    this.elementComparator = elementComparator;
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public int compare(Collection<T> first, Collection<T> second) {
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

    if (first.size() != second.size()) {
      return first.size() - second.size();
    }

    List<T> firstSorted = first.stream().sorted(elementComparator).collect(Collectors.toList());
    List<T> secondSorted = second.stream().sorted(elementComparator).collect(Collectors.toList());

    Iterator<T> secondSortedIterator = secondSorted.iterator();
    for (T firstElement : firstSorted) {
      T selectElement = secondSortedIterator.next();
      int result = elementComparator.compare(firstElement, selectElement);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
