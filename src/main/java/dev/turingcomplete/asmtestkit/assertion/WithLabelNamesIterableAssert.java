package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.representation.WithLabelNamesRepresentation;
import org.assertj.core.presentation.Representation;
import org.assertj.core.presentation.StandardRepresentation;
import org.objectweb.asm.Label;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * The base class for {@link AsmIterableAssert}s that uses a
 * {@link WithLabelNamesRepresentation}.
 *
 * @param <S> the 'self' type of {@code this} {@link WithLabelNamesIterableAssert}}.
 * @param <E> the single element type of the actual {@link Iterable}.
 * @param <A> the {@link AsmAssert} for {@link E}.
 */
public class WithLabelNamesIterableAssert<S extends WithLabelNamesIterableAssert<S, E, A>, E, A extends AsmAssert<A, E>>
        extends AsmIterableAssert<S, E, A> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final Map<Label, String> labelNames = new HashMap<>();

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public WithLabelNamesIterableAssert(Iterable<? extends E> actual, Function<E, A> createElementAssert) {
    super(actual, WithLabelNamesIterableAssert.class, createElementAssert);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  public S useLabelNames(Map<Label, String> labelNames) {
    this.labelNames.putAll(labelNames);

    //noinspection unchecked
    return (S) this;
  }

  @Override
  public S withRepresentation(Representation representation) {
    if (!(representation instanceof WithLabelNamesRepresentation)) {
      throw new IllegalArgumentException("Representation must be an instance of: " + WithLabelNamesRepresentation.class.getName());
    }

    var withLabelNamesRepresentation = (WithLabelNamesRepresentation<?>) representation;
    //noinspection ResultOfMethodCallIgnored
    super.withRepresentation(new WithLabelNamesRepresentationAdapter(withLabelNamesRepresentation, labelNames));

    //noinspection unchecked
    return (S) this;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  public static class WithLabelNamesRepresentationAdapter extends StandardRepresentation {

    private final WithLabelNamesRepresentation<?> delegate;
    private final Map<Label, String>              labelNamesReference;
    protected WithLabelNamesRepresentationAdapter(WithLabelNamesRepresentation<?> delegate, Map<Label, String> labelNamesReference) {
      this.delegate = delegate;
      this.labelNamesReference = labelNamesReference;
    }

    public static WithLabelNamesRepresentationAdapter create(WithLabelNamesRepresentation<?> delegate, Map<Label, String> labelNames) {
      return new WithLabelNamesRepresentationAdapter(delegate, labelNames);
    }

    @Override
    protected String fallbackToStringOf(Object object) {
      return delegate.toStringOf(object, labelNamesReference);
    }
  }
}
