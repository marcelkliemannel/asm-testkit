package dev.turingcomplete.asmtestkit.comparator._internal;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static dev.turingcomplete.asmtestkit.comparator._internal.ComparatorUtils.compareNullCheck;

/**
 * A comparison function to order {@link Iterable}s of {@link T}s.
 *
 * <p>The order of the elements are not taking into account. This gets archives
 * by ordering the {@link Iterable}s before comparing their elements.
 */
public class IterableComparator<T> implements Comparator<Iterable<? extends T>> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  protected final Comparator<T> elementsComparator;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public IterableComparator(Comparator<T> elementsComparator) {
    this.elementsComparator = elementsComparator;
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public int compare(Iterable<? extends T> first, Iterable<? extends T> second) {
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
