package dev.turingcomplete.asmtestkit.comparator;

import java.util.Comparator;

public interface AsmComparators {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Gets a {@link Comparator} which can order elements of type
   * {@code elementClass}.
   *
   * @param elementClass the {@link Class} of elements to compare; never null.
   * @param <T>          the type of elements to compare.
   * @return a {@link Comparator} to order {@code T}s; never null.
   */
  <T> Comparator<T> elementComparator(Class<T> elementClass);

  /**
   * Gets a {@link Comparator} which can order {@link Iterable}s with elements
   * of type {@code elementClass}.
   *
   * @param <T>          the type of elements to compare.
   * @param elementClass the {@link Class} of elements to compare; never null.
   * @return a {@link Comparator} to order {@link Iterable} of {@code T}; never
   * null.
   */
  <T> Comparator<? super Iterable<? extends T>> iterableComparator(Class<T> elementClass);

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
