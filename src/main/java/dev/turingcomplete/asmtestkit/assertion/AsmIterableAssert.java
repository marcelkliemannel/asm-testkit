package dev.turingcomplete.asmtestkit.assertion;

import org.assertj.core.api.AbstractIterableAssert;

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
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  protected A toAssert(E value, String description) {
    return createElementAssert.apply(value).as(description);
  }

  @Override
  protected S newAbstractIterableAssert(Iterable<? extends E> iterable) {
    //noinspection unchecked
    return (S) new AsmIterableAssert<S, E, A>(iterable, createElementAssert);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
