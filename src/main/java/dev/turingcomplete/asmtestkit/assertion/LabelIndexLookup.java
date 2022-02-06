package dev.turingcomplete.asmtestkit.assertion;

import org.objectweb.asm.Label;

import java.util.Map;
import java.util.Optional;

/**
 * A lookup function to map {@link Label}s to indices.
 */
public interface LabelIndexLookup {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Looks up the index for the given {@link Label}.
   *
   * @param label a {@link Label}; never null.
   * @return an {@link Optional} describing the index of {@code label}; never null.
   */
  Optional<Integer> find(Label label);

  /**
   * Adds or replaces all given {@link Label} to index mappings.
   *
   * @param labelIndices a {@link Map} with a mapping of {@link Label} to
   *                     an {@link Integer} index; never null.
   */
  void putAll(Map<Label, Integer> labelIndices);

  void putIfUnknown(Label label, Integer index);

  void add(LabelIndexLookup labelIndexLookup);

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
