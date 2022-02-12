package dev.turingcomplete.asmtestkit.common;

import org.objectweb.asm.Label;

import java.util.List;
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
   * @param label a {@link Label}; may be null.
   * @return an {@link Optional} describing the index of {@code label};
   * never null.
   */
  Optional<Integer> find(Label label);

  /**
   * Adds or replaces all given {@link Label} to index mappings.
   *
   * @param labelIndices a {@link Map} with a mapping of {@link Label} to
   *                     an {@link Integer} index; never null.
   */
  void putAll(Map<Label, Integer> labelIndices);

  /**
   * Adds the giben {@link Label} to index lookup if there is not already a
   * mapping for the given {@link Label}.
   *
   * @param label a {@link Label}; never null.
   * @param index the index of the given label; may be null.
   */
  void putIfUnknown(Label label, Integer index);

  /**
   * Clears all known {@link Label} indicies.
   */
  void clearLabelIndices();

  /**
   * Gets all known {@link Label} to index mappings.
   *
   * @return a {@link Map} with a mapping of {@link Label} to an {@link Integer}
   * index; never null.
   */
  Map<Label, Integer> getAllLabelIndices();

  /**
   * Adds the given {@link LabelIndexLookup} as a child lookup.
   *
   * @param childLabelIndexLookup a {@link LabelIndexLookup}; never null.
   * @see #mergeWith(LabelIndexLookup)
   */
  void addChild(LabelIndexLookup childLabelIndexLookup);

  /**
   * Gets all child {@link LabelIndexLookup}s.
   *
   * @return a {@link List} of {@link LabelIndexLookup}s; never null.
   */
  List<LabelIndexLookup> getChildren();

  /**
   * Clears all child {@link LabelIndexLookup}s.
   */
  void clearChildren();

  /**
   * Merges the given {@link LabelIndexLookup} with this.
   *
   * @param labelIndexLookup a {@link LabelIndexLookup}; never null.
   * @see #addChild(LabelIndexLookup)
   */
  void mergeWith(LabelIndexLookup labelIndexLookup);

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
