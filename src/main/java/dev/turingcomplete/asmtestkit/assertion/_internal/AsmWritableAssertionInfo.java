package dev.turingcomplete.asmtestkit.assertion._internal;

import dev.turingcomplete.asmtestkit.assertion.representation.AsmRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.WithLabelNamesRepresentation;
import org.assertj.core.api.WritableAssertionInfo;
import org.assertj.core.description.Description;
import org.assertj.core.description.TextDescription;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Label;

import java.util.HashMap;
import java.util.Map;

public final class AsmWritableAssertionInfo extends WritableAssertionInfo {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final Map<Label, String> labelNames = new HashMap<>();

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public AsmWritableAssertionInfo(Representation defaultRepresentation) {
    useRepresentation(defaultRepresentation);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  public void useLabelNames(Map<Label, String> labelNames) {
    this.labelNames.clear();
    this.labelNames.putAll(labelNames);
  }

  @Override
  public void useRepresentation(Representation newRepresentation) {
    if (newRepresentation instanceof WithLabelNamesRepresentation) {
      var withLabelNamesRepresentation = (WithLabelNamesRepresentation<?>) newRepresentation;
      newRepresentation = new WithLabelNamesRepresentationAdapter<>(withLabelNamesRepresentation, labelNames);
    }

    super.useRepresentation(newRepresentation);
  }

  public Description createCrumbDescription(String description, Object... args) {
    String selfDescription = descriptionText();
    if (!selfDescription.equals(descriptionText())) {
      selfDescription = selfDescription + " > " + selfDescription;
    }

    return description != null
            ? new TextDescription("%s > %s", selfDescription, String.format(description, args))
            : new TextDescription(selfDescription);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  private static class WithLabelNamesRepresentationAdapter<T> extends AsmRepresentation<T> {

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
  }
}
