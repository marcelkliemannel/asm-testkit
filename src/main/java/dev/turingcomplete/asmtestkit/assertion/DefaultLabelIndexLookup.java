package dev.turingcomplete.asmtestkit.assertion;

import org.objectweb.asm.Label;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class DefaultLabelIndexLookup implements LabelIndexLookup {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  /**
   * Lazily initialized to reduce the memory footprint for empty
   * {@link DefaultLabelIndexLookup}s.
   */
  private Map<Label, Integer> labelIndices;

  /**
   * Lazily initialized to reduce the memory footprint for empty
   * {@link DefaultLabelIndexLookup}s.
   */
  private List<LabelIndexLookup> additionalLabelIndexLookup;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  private DefaultLabelIndexLookup(Map<Label, Integer> labelIndices) {
    this.labelIndices = labelIndices != null ? new HashMap<>(labelIndices) : null;
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link DefaultLabelIndexLookup}.
   *
   * @return a new {@link DefaultLabelIndexLookup}; never null.
   */
  public static DefaultLabelIndexLookup create() {
    return new DefaultLabelIndexLookup(null);
  }

  /**
   * Creates a new {@link DefaultLabelIndexLookup} with the given initial
   * {@link Label} to index mappings.
   *
   * @param labelIndices a {@link Map} with a mapping of {@link Label} to
   *                     an {@link Integer} index; never null.
   * @return a new {@link DefaultLabelIndexLookup}; never null.
   */
  public static DefaultLabelIndexLookup create(Map<Label, Integer> labelIndices) {
    Objects.requireNonNull(labelIndices);

    return new DefaultLabelIndexLookup(labelIndices);
  }

  @Override
  public Optional<Integer> find(Label label) {
    Objects.requireNonNull(label);

    if (labelIndices != null && labelIndices.containsKey(label)) {
      return Optional.ofNullable(labelIndices.get(label));
    }

    if (additionalLabelIndexLookup != null) {
      for (LabelIndexLookup _additionalLabelIndexLookup : additionalLabelIndexLookup) {
        Optional<Integer> result = _additionalLabelIndexLookup.find(label);
        if (result.isPresent()) {
          return result;
        }
      }
    }

    return Optional.empty();
  }

  @Override
  public void putAll(Map<Label, Integer> labelIndices) {
    Objects.requireNonNull(labelIndices);

    if (this.labelIndices == null) {
      this.labelIndices = new HashMap<>();
    }

    this.labelIndices.putAll(labelIndices);
  }

  @Override
  public void putIfUnknown(Label label, Integer index) {
    if (find(label).isEmpty()) {
      if (this.labelIndices == null) {
        this.labelIndices = new HashMap<>();
      }

      this.labelIndices.put(label, index);
    }
  }

  @Override
  public void add(LabelIndexLookup labelIndexLookup) {
    if (additionalLabelIndexLookup == null) {
      additionalLabelIndexLookup = new ArrayList<>();
    }

    additionalLabelIndexLookup.add(labelIndexLookup);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
