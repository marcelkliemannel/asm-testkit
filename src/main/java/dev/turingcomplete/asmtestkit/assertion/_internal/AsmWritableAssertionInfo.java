package dev.turingcomplete.asmtestkit.assertion._internal;

import dev.turingcomplete.asmtestkit.assertion.representation.WithLabelNamesRepresentation;
import org.assertj.core.api.WritableAssertionInfo;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Label;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class AsmWritableAssertionInfo extends WritableAssertionInfo {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final Map<Label, String> labelNames = new HashMap<>();

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public AsmWritableAssertionInfo(Representation defaultRepresentation) {
    if (defaultRepresentation != null) {
      useRepresentation(defaultRepresentation);
    }
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  public void useLabelNames(Map<Label, String> labelNames) {
    this.labelNames.clear();
    this.labelNames.putAll(labelNames);
  }

  public Map<Label, String> labelNames() {
    return Collections.unmodifiableMap(labelNames);
  }

  @Override
  public void useRepresentation(Representation newRepresentation) {
    if (newRepresentation instanceof WithLabelNamesRepresentation) {
      var withLabelNamesRepresentation = (WithLabelNamesRepresentation<?>) newRepresentation;
      newRepresentation = new WithLabelNamesRepresentationAdapter<>(withLabelNamesRepresentation, labelNames);
    }

    super.useRepresentation(newRepresentation);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  private static class WithLabelNamesRepresentationAdapter<T> extends WithLabelNamesRepresentation<T> {

    private final WithLabelNamesRepresentation<T> delegate;
    private final Map<Label, String>              labelNamesReference;

    private WithLabelNamesRepresentationAdapter(WithLabelNamesRepresentation<T> delegate, Map<Label, String> labelNamesReference) {
      super(delegate.getObjectClass());

      this.delegate = delegate;
      this.labelNamesReference = labelNamesReference;
    }

    @Override
    protected String doToSimplifiedStringOf(T object) {
      return delegate.toSimplifiedStringOf(object, labelNamesReference);
    }

    @Override
    protected String doToStringOf(T object) {
      return delegate.toStringOf(object, labelNamesReference);
    }

    @Override
    protected String doToSimplifiedStringOf(T object, Map<Label, String> labelNames) {
      return delegate.toSimplifiedStringOf(object, labelNames);
    }

    @Override
    protected String doToStringOf(T object, Map<Label, String> labelNames) {
      return delegate.toStringOf(object, labelNames);
    }
  }
}
