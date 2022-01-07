package dev.turingcomplete.asmtestkit.assertion.comparator._internal;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CollectionComparatorTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testNullArguments() {
    var collectionComparator = new IterableComparator<String>(Comparator.naturalOrder());

    //noinspection EqualsWithItself
    assertThat(collectionComparator.compare(null, null))
            .isEqualTo(0);

    assertThat(collectionComparator.compare(null, List.of()))
            .isLessThanOrEqualTo(-1);

    assertThat(collectionComparator.compare(List.of(), null))
            .isGreaterThanOrEqualTo(1);
  }

  @Test
  void testDifferentSize() {
    var stringCollectionComparator = new IterableComparator<String>(Comparator.naturalOrder());

    Assertions.assertThat(stringCollectionComparator.compare(List.of("A", "B"), List.of("A")))
              .isGreaterThan(0);

    Assertions.assertThat(stringCollectionComparator.compare(List.of("A"), List.of("A", "B")))
              .isLessThan(0);
  }

  @Test
  void testElementsCompare() {
    var stringCollectionComparator = new IterableComparator<String>(Comparator.naturalOrder());

    //noinspection EqualsWithItself
    Assertions.assertThat(stringCollectionComparator.compare(List.of("A", "B"), List.of("A", "B")))
              .isEqualTo(0);

    //noinspection EqualsWithItself
    Assertions.assertThat(stringCollectionComparator.compare(List.of(), List.of()))
              .isEqualTo(0);

    Assertions.assertThat(stringCollectionComparator.compare(List.of("A", "B"), List.of("C", "D")))
              .isLessThan(0);

    Assertions.assertThat(stringCollectionComparator.compare(List.of("C", "D"), List.of("A", "B")))
              .isGreaterThan(0);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
