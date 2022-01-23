package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion._internal.AsmWritableAssertionInfo;
import dev.turingcomplete.asmtestkit.assertion.comparator._internal.WithLabelNamesAsmComparatorAdapter;
import org.assertj.core.api.AbstractIterableAssert;

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
   * Sets the given {@link LabelNameLookup} to look up known label names.
   *
   * @param labelNameLookup a {@link LabelNameLookup} to set; never null.
   * @return {@code this} {@link S}; never null.
   * @see #labelNameLookup()
   */
  public S useLabelNameLookup(LabelNameLookup labelNameLookup) {
    Objects.requireNonNull(labelNameLookup);
    getWritableAssertionInfo().useLabelNameLookup(labelNameLookup);

    //noinspection unchecked
    return (S) this;
  }

  /**
   * Gets the current {@link LabelNameLookup} to look up known label names.
   *
   * @return the current {@link LabelNameLookup}; never null.
   * @see #labelNameLookup()
   */
  public LabelNameLookup labelNameLookup() {
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

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
