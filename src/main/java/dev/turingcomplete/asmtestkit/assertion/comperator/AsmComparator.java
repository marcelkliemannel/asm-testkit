package dev.turingcomplete.asmtestkit.assertion.comperator;

import java.util.Comparator;

public abstract class AsmComparator<T> implements Comparator<T> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public final int compare(T first, T second) {
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

    return doCompare(first, second);
  }

  protected abstract int doCompare(T first, T second);

  protected final int stringCompare(String first, String second) {
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

    return first.compareTo(second);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
