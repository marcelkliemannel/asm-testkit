package dev.turingcomplete.asmtestkit.assertion.representation._internal;

import dev.turingcomplete.asmtestkit.assertion.LabelIndexLookup;
import dev.turingcomplete.asmtestkit.assertion.representation.AbstractWithLabelNamesAsmRepresentation;

import java.util.Objects;

public class WithLabelNamesRepresentationAdapter<T> extends AbstractWithLabelNamesAsmRepresentation<T> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final AbstractWithLabelNamesAsmRepresentation<T> delegate;
  private final LabelIndexLookup                           labelIndexLookup;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public WithLabelNamesRepresentationAdapter(AbstractWithLabelNamesAsmRepresentation<T> delegate, LabelIndexLookup labelIndexLookup) {
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
