package dev.turingcomplete.asmtestkit.assertion;

import org.objectweb.asm.Label;

import java.util.Map;
import java.util.Optional;

public final class IgnoringLabelIndexLookup implements LabelIndexLookup {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  public static final IgnoringLabelIndexLookup INSTANCE = create();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final Integer constantIndex;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  private IgnoringLabelIndexLookup(Integer constantIndex) {
    this.constantIndex = constantIndex;
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  public static IgnoringLabelIndexLookup create() {
    return new IgnoringLabelIndexLookup(-1);
  }

  public static IgnoringLabelIndexLookup create(int constantIndex) {
    return new IgnoringLabelIndexLookup(constantIndex);
  }

  @Override
  public Optional<Integer> find(Label label) {
    return Optional.of(constantIndex);
  }

  @Override
  public void putAll(Map<Label, Integer> labelIndices) {
    // Nothing to do
  }

  @Override
  public void putIfUnknown(Label label, Integer index) {
    // Nothing to do
  }

  @Override
  public void add(LabelIndexLookup labelIndexLookup) {
    // Nothing to do
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
