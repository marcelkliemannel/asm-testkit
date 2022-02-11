package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.representation.AbstractAsmRepresentation;
import org.assertj.core.api.Assertions;
import org.assertj.core.presentation.StandardRepresentation;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

class AsmIterableAssertTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  
  private static final Dummy A = new Dummy("A", "AA");
  private static final Dummy B = new Dummy("B", "BB");
  private static final Dummy C = new Dummy("C", "CC");
  
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testContainsExactlyInAnyOrderCompareOneByOneElementsOf_missingKeyExtractor() {
    Assertions.assertThatThrownBy(() -> new DummyAsmIterableAssert(List.of(A, B))
                      .containsExactlyInAnyOrderCompareOneByOneElementsOf(List.of(A, B)))
              .isInstanceOf(UnsupportedOperationException.class)
              .hasMessage("No key extractor for one by one comparison set.");
  }

  @Test
  void testContainsExactlyInAnyOrderCompareOneByOneElementsOf_individualKeyExtractor() {
    new DummyAsmIterableAssert(List.of(A, B))
            .setCompareOneByOneKeyExtractor(element -> element.key)
            .containsExactlyInAnyOrderCompareOneByOneElementsOf(List.of(A, B));
  }

  @Test
  void testContainsExactlyInAnyOrderCompareOneByOneElementsOf_asmRepresentation() {
    new DummyAsmIterableAssert(List.of(A, B))
            .withRepresentation(new AbstractAsmRepresentation<>(Dummy.class) {
              @Override
              protected String doToSimplifiedStringOf(Dummy dummy) {
                return dummy.key;
              }

              @Override
              protected String doToStringOf(Dummy dummy) {
                return dummy.value;
              }
            })
            .containsExactlyInAnyOrderCompareOneByOneElementsOf(List.of(A, B));
  }

  @Test
  void testContainsExactlyInAnyOrderCompareOneByOneElementsOf_missingElement() {
    Assertions.assertThatThrownBy(() -> new DummyAsmIterableAssert(List.of(A, B, C))
                      .as("Missing element test")
                      .setCompareOneByOneKeyExtractor(element -> element.key)
                      .containsExactlyInAnyOrderCompareOneByOneElementsOf(List.of(A, B)))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Missing element test] \n" +
                          "Expecting KeySet:\n" +
                          "  [\"A\", \"B\"]\n" +
                          "to contain:\n" +
                          "  [\"C\"]\n" +
                          "but could not find the following element(s):\n" +
                          "  [\"C\"]\n");
  }

  @Test
  void testContainsExactlyInAnyOrderCompareOneByOneElementsOf_invalidElement() {
    var d1 = new Dummy("D", "DD");
    var d2 = new Dummy("D", "DDDD");

    Assertions.assertThatThrownBy(() -> new DummyAsmIterableAssert(List.of(A, B, C, d1))
                      .as("Invalid element test")
                      .setCompareOneByOneKeyExtractor(element -> element.key)
                      .containsExactlyInAnyOrderCompareOneByOneElementsOf(List.of(A, B, C, d2)))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Invalid element test > Dummy: DD] \n" +
                          "expected: DDDD\n" +
                          " but was: DD\n" +
                          "when comparing values using DummyComparator");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  private static class Dummy {

    private final String key;
    private final String value;

    private Dummy(String key, String value) {
      this.key = key;
      this.value = value;
    }

    @Override
    public String toString() {
      return value;
    }
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  private static class DummyAsmIterableAssert extends AsmIterableAssert<DummyAsmIterableAssert, Dummy, DummyAsmAssert> {

    public DummyAsmIterableAssert(Iterable<Dummy> actual) {
      super(actual, DummyAsmIterableAssert.class, DummyAsmAssert::new);
    }
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  private static class DummyAsmAssert extends AsmAssert<DummyAsmAssert, Dummy> {

    protected DummyAsmAssert(Dummy actual) {
      super("Dummy", actual, DummyAsmAssert.class, StandardRepresentation.STANDARD_REPRESENTATION, new DummyComparator());
    }
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
  
  private static final class DummyComparator implements Comparator<Dummy> {

    @Override 
    public int compare(Dummy o1, Dummy o2) {
      return o1.value.compareTo(o2.value);
    }
  }
}
