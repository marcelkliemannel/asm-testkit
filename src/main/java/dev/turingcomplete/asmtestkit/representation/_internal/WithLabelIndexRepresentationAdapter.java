package dev.turingcomplete.asmtestkit.representation._internal;

import dev.turingcomplete.asmtestkit.common.LabelIndexLookup;
import dev.turingcomplete.asmtestkit.representation.AbstractWithLabelIndexAsmRepresentation;

import java.util.Objects;

public class WithLabelIndexRepresentationAdapter<T> extends AbstractWithLabelIndexAsmRepresentation<T> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final AbstractWithLabelIndexAsmRepresentation<T> delegate;
  private final LabelIndexLookup                           labelIndexLookup;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public WithLabelIndexRepresentationAdapter(AbstractWithLabelIndexAsmRepresentation<T> delegate, LabelIndexLookup labelIndexLookup) {
    super(Objects.requireNonNull(delegate).getObjectClass());

    this.delegate = delegate;
    this.labelIndexLookup = Objects.requireNonNull(labelIndexLookup);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  protected String doToSimplifiedStringOf(T object) {
    return delegate.toSimplifiedStringOf(object, labelIndexLookup);
  }

  @Override
  protected String doToStringOf(T object) {
    return delegate.toStringOf(object, labelIndexLookup);
  }

  @Override
  protected String doToSimplifiedStringOf(T object, LabelIndexLookup labelIndexLookup) {
    return delegate.toSimplifiedStringOf(object, labelIndexLookup);
  }

  @Override
  protected String doToStringOf(T object, LabelIndexLookup labelIndexLookup) {
    return delegate.toStringOf(object, labelIndexLookup);
  }

  @Override
  public String toString() {
    return delegate.getClass().getSimpleName();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
