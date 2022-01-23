package dev.turingcomplete.asmtestkit.assertion;

import org.objectweb.asm.Label;

import java.util.Map;
import java.util.Optional;

/**
 * A lookup function to map {@link Label}s to names.
 */
@FunctionalInterface
public interface LabelNameLookup {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  LabelNameLookup EMPTY = __ -> Optional.empty();

  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link LabelNameLookup} that works on the given label names.
   *
   * @param labelNames a {@link Map} with a mapping of {@link Label} to
   *                   {@link String} names; never null.
   * @return a new {@link LabelNameLookup}; never null.
   */
  static LabelNameLookup create(Map<Label, String> labelNames) {
    return label -> labelNames.containsKey(label) ? Optional.of(labelNames.get(label)) : Optional.empty();
  }

  /**
   * Merges the given {@link LabelNameLookup} with this one.
   *
   * @param labelNameLookup a {@link LabelNameLookup} to merge; never null.
   * @return the resulting (possible new) {@link LabelNameLookup}; never null.
   */
  default LabelNameLookup merge(LabelNameLookup labelNameLookup) {
    return label -> find(label).or(() -> labelNameLookup.find(label));
  }

  /**
   * Looks up a label name for the given {@link Label}.
   *
   * @param label a {@link Label}; never null.
   * @return an {@link Optional} describing the name of the label; never null.
   */
  Optional<String> find(Label label);

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
