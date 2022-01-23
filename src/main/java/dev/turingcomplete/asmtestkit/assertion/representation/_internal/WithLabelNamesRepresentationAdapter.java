package dev.turingcomplete.asmtestkit.assertion.representation._internal;

import dev.turingcomplete.asmtestkit.assertion.LabelNameLookup;
import dev.turingcomplete.asmtestkit.assertion.representation.AbstractWithLabelNamesAsmRepresentation;

import java.util.Objects;

public class WithLabelNamesRepresentationAdapter<T> extends AbstractWithLabelNamesAsmRepresentation<T> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final AbstractWithLabelNamesAsmRepresentation<T> delegate;
  private final LabelNameLookup                            labelNameLookup;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public WithLabelNamesRepresentationAdapter(AbstractWithLabelNamesAsmRepresentation<T> delegate, LabelNameLookup labelNameLookup) {
    super(Objects.requireNonNull(delegate).getObjectClass());

    this.delegate = delegate;
    this.labelNameLookup = Objects.requireNonNull(labelNameLookup);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  protected String doToSimplifiedStringOf(T object) {
    return delegate.toSimplifiedStringOf(object, labelNameLookup);
  }

  @Override
  protected String doToStringOf(T object) {
    return delegate.toStringOf(object, labelNameLookup);
  }

  @Override
  protected String doToSimplifiedStringOf(T object, LabelNameLookup labelNameLookup) {
    return delegate.toSimplifiedStringOf(object, labelNameLookup);
  }

  @Override
  protected String doToStringOf(T object, LabelNameLookup labelNameLookup) {
    return delegate.toStringOf(object, labelNameLookup);
  }

  @Override
  public String toString() {
    return delegate.getClass().getSimpleName();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
