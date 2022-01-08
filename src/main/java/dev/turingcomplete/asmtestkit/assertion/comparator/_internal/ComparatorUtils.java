package dev.turingcomplete.asmtestkit.assertion.comparator._internal;

import java.util.Comparator;

public final class ComparatorUtils {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A {@link Comparator} for {@link String}s which can handle null values by
   * using {@link Comparator#nullsFirst(Comparator)}.
   */
  public static final Comparator<String> STRING_COMPARATOR = Comparator.nullsFirst(Comparator.naturalOrder());

  /**
   * A {@link Comparator} for {@link Integer}s which can handle null values by
   * using {@link Comparator#nullsFirst(Comparator)}.
   */
  public static final Comparator<Integer> INTEGER_COMPARATOR = Comparator.nullsFirst(Comparator.naturalOrder());

  /**
   * A {@link Comparator} for {@link Object}s which can handle null values. In
   * the case that both values are not null, the comparator returns {@code 0} if
   * {@link Object#equals(Object)} returns true, otherwise the returned value
   * will have an undefined value other than {@code 0}.
   */
  public static final Comparator<Object> OBJECT_COMPARATOR = new ObjectComparator();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  private ComparatorUtils() {
    throw new UnsupportedOperationException();
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //
  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  private static class ObjectComparator implements Comparator<Object> {

    @Override
    public int compare(Object first, Object second) {
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

      return first.equals(second) ? 0 : -1;
    }
  }
}