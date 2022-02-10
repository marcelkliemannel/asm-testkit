package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion._internal.AsmWritableAssertionInfo;
import dev.turingcomplete.asmtestkit.comparator._internal.WithLabelNamesAsmComparatorAdapter;
import dev.turingcomplete.asmtestkit.representation.AsmRepresentation;
import org.assertj.core.api.AbstractIterableAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.presentation.Representation;
import org.assertj.core.util.IterableUtil;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The base class for all ASM {@link Iterable} related assertions.
 *
 * @param <S> the 'self' type of {@code this} {@link AsmIterableAssert}}.
 * @param <E> the single element type of the actual {@link Iterable}.
 * @param <A> the {@link AsmAssert} for {@link E}.
 */
public class AsmIterableAssert<S extends AsmIterableAssert<S, E, A>, E, A extends AsmAssert<A, E>>
        extends AbstractIterableAssert<S, Iterable<? extends E>, E, A> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final Function<E, A> createElementAssert;

  private Function<E, String> compareOneByOneKeyExtractor = __ -> {
    throw new UnsupportedOperationException("No key extractor for one by one comparison set.");
  };

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public AsmIterableAssert(Iterable<? extends E> actual, Function<E, A> createElementAssert) {
    this(actual, AsmIterableAssert.class, createElementAssert);
  }

  protected AsmIterableAssert(Iterable<? extends E> actual, Class<?> selfType, Function<E, A> createElementAssert) {
    super(actual, selfType);

    this.createElementAssert = createElementAssert;
    info = new AsmWritableAssertionInfo(info.representation());
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public AsmWritableAssertionInfo getWritableAssertionInfo() {
    if (!(info instanceof AsmWritableAssertionInfo)) {
      throw new IllegalStateException("Expected info to be of type:" + AsmWritableAssertionInfo.class.getName());
    }

    return (AsmWritableAssertionInfo) info;
  }

  /**
   * Sets the given {@link LabelIndexLookup} to look up known label names.
   *
   * @param labelIndexLookup a {@link LabelIndexLookup} to set; never null.
   * @return {@code this} {@link S}; never null.
   * @see #labelNameLookup()
   */
  public S useLabelNameLookup(LabelIndexLookup labelIndexLookup) {
    Objects.requireNonNull(labelIndexLookup);
    getWritableAssertionInfo().useLabelIndexLookup(labelIndexLookup);

    //noinspection unchecked
    return (S) this;
  }

  /**
   * Gets the current {@link LabelIndexLookup} to look up known label names.
   *
   * @return the current {@link LabelIndexLookup}; never null.
   * @see #labelNameLookup()
   */
  public LabelIndexLookup labelNameLookup() {
    return getWritableAssertionInfo().labelNameLookup();
  }

  @Override
  protected A toAssert(E value, String description) {
    return createElementAssert.apply(value).as(description);
  }

  @Override
  protected S newAbstractIterableAssert(Iterable<? extends E> iterable) {
    //noinspection unchecked
    return (S) new AsmIterableAssert<S, E, A>(iterable, createElementAssert);
  }

  @Override
  public S usingComparator(Comparator<? super Iterable<? extends E>> customComparator) {
    return usingComparator(customComparator, null);
  }

  @Override
  public S usingComparator(Comparator<? super Iterable<? extends E>> customComparator, String customComparatorDescription) {
    return super.usingComparator(WithLabelNamesAsmComparatorAdapter.wrapIfNeeded(customComparator, getWritableAssertionInfo().labelNameLookup()), customComparatorDescription);
  }

  @Override
  public S usingElementComparator(Comparator<? super E> customElementComparator) {
    return super.usingElementComparator(WithLabelNamesAsmComparatorAdapter.wrapIfNeeded(customElementComparator, getWritableAssertionInfo().labelNameLookup()));
  }

  /**
   * The {@link super#containsExactlyInAnyOrderForProxy} fails if {@link #actual} or
   * {@code expected} are null. But in most ASM assertion cases, both to be null
   * is a valid state.
   *
   * <p>In the case {@code expected == null}, the super methods throws a generic
   * {@link NullPointerException}, without pointing out in detail that causes the
   * error. We want to fix that here with a more detailed error message.
   *
   * @return {@code this} {@link S}; never null.
   */
  @Override
  protected S containsExactlyInAnyOrderForProxy(E[] expected) {
    if (actual == null && expected == null) {
      //noinspection unchecked
      return (S) this;
    }

    if (actual != null) {
      assertExpectedNotNullOfActualIsNotNull(expected);
    }
    assert actual != null && expected != null;

    return super.containsExactlyInAnyOrderForProxy(expected);
  }

  /**
   * Compares the given {@link Iterable} of {@link E}s one by one.
   *
   * <p>The AssertJ method {@link #containsExactlyInAnyOrderElementsOf} will
   * output a full list of all non-matching elements which is very difficult to
   * read if the textual representation of the elements spans several lines.
   * This method first searches for a matching actual element (using the search
   * key from {@link #setCompareOneByOneKeyExtractor(Function)}) and then just
   * compares this one element.
   *
   * @param expected an {@link Iterable} of {@link E}s; may be null.
   * @return {@code this} {@link S}; never null.
   * @see #setCompareOneByOneKeyExtractor(Function)
   */
  public S containsExactlyInAnyOrderCompareOneByOneElementsOf(Iterable<E> expected) {
    return containsExactlyInAnyOrderCompareOneByOne(IterableUtil.toArray(expected));
  }

  /**
   * Compares the given array of {@link E}s one by one.
   *
   * <p>The AssertJ method {@link #containsExactlyInAnyOrderElementsOf} will
   * output a full list of all non-matching elements which is very difficult to
   * read if the textual representation of the elements spans several lines.
   * This method first searches for a matching actual element (using the search
   * key from {@link #setCompareOneByOneKeyExtractor(Function)}) and then just
   * compares this one element.
   *
   * @param expected an array of {@link E}s; may be null.
   * @return {@code this} {@link S}; never null.
   * @see #setCompareOneByOneKeyExtractor(Function)
   */
  @SafeVarargs
  public final S containsExactlyInAnyOrderCompareOneByOne(E ...expected) {
    return containsExactlyInAnyOrderCompareOneByOneProxy(expected);
  }

  protected S containsExactlyInAnyOrderCompareOneByOneProxy(E[] expected) {
    if (actual == null && expected == null) {
      //noinspection unchecked
      return (S) this;
    }

    if (actual != null) {
      assertExpectedNotNullOfActualIsNotNull(expected);
    }
    assert actual != null && expected != null;

    Map<String, E> expectedKeyToElement = Arrays.stream(expected).collect(Collectors.toMap(compareOneByOneKeyExtractor, element -> element));
    for (E actualElement : actual) {
      String actualElementKey = compareOneByOneKeyExtractor.apply(actualElement);
      Assertions.assertThat(expectedKeyToElement.keySet())
                .as(descriptionText())
                .contains(actualElementKey);

      toAssert(actualElement, descriptionText())
              .isEqualTo(expectedKeyToElement.get(actualElementKey));
    }

    //noinspection unchecked
    return (S) this;
  }

  @Override
  public S withRepresentation(Representation representation) {
    if (representation instanceof AsmRepresentation) {
      compareOneByOneKeyExtractor = element -> ((AsmRepresentation<?>) representation).toSimplifiedStringOf(element);
    }

    return super.withRepresentation(representation);
  }

  /**
   * Sets the search key extractor which is gets used by:
   * <ul>
   *   <li>{@link #containsExactlyInAnyOrderCompareOneByOneElementsOf(Iterable)};
   *   <li>and {@link #containsExactlyInAnyOrderCompareOneByOne(Object[])}.
   * </ul>
   *
   * <p>If not called, but the {@link Representation} is an
   * {@link AsmRepresentation} the returned value of
   * {@link AsmRepresentation#toSimplifiedStringOf(Object)} will be used.
   *
   * @param compareOneByOneKeyExtractor a {@link Function} mapping an element to
   *                                    a search {@link String} key; never null.
   * @return {@code this} {@link S}; never null.
   */
  public S setCompareOneByOneKeyExtractor(Function<E, String> compareOneByOneKeyExtractor) {
    this.compareOneByOneKeyExtractor = Objects.requireNonNull(compareOneByOneKeyExtractor);

    //noinspection unchecked
    return (S) this;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private void assertExpectedNotNullOfActualIsNotNull(E[] expected) {
    Assertions.assertThat(expected)
              .as(descriptionText())
              .withFailMessage(String.format("%nExpecting expected value not to be null if actual value is not null"))
              .isNotNull();
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
