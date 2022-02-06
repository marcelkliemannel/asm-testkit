package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion._internal.AsmWritableAssertionInfo;
import dev.turingcomplete.asmtestkit.assertion.comparator._internal.WithLabelNamesAsmComparatorAdapter;
import org.assertj.core.api.AbstractIterableAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.internal.Failures;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

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

  private final Failures failures = Failures.instance();

  private final Function<E, A> createElementAssert;

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
   */
  @Override
  protected S containsExactlyInAnyOrderForProxy(E[] expected) {
    if (actual == null && expected == null) {
      //noinspection unchecked
      return (S) this;
    }

    if (actual != null) {
      Assertions.assertThat(expected)
                .as(descriptionText())
                .withFailMessage(String.format("%nExpecting expected value not to be null if actual value is not null"))
                .isNotNull();
    }

    return super.containsExactlyInAnyOrderForProxy(expected);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
