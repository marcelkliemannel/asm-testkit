package dev.turingcomplete.asmtestkit.common;

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
  private List<LabelIndexLookup> children;

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
    if (label == null) {
      return Optional.empty();
    }

    if (labelIndices != null && labelIndices.containsKey(label)) {
      return Optional.ofNullable(labelIndices.get(label));
    }

    if (children != null) {
      for (LabelIndexLookup _additionalLabelIndexLookup : children) {
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
    Objects.requireNonNull(label);

    if (find(label).isEmpty()) {
      initLabelIndices();

      this.labelIndices.put(label, index);
    }
  }

  @Override
  public void clearLabelIndices() {
    labelIndices = null;
  }

  @Override
  public Map<Label, Integer> getAllLabelIndices() {
    return labelIndices == null ? Map.of() : labelIndices;
  }

  @Override
  public void addChild(LabelIndexLookup childLabelIndexLookup) {
    Objects.requireNonNull(childLabelIndexLookup);

    initChildren();
    children.add(childLabelIndexLookup);
  }

  @Override
  public List<LabelIndexLookup> getChildren() {
    return children == null ? List.of() : children;
  }

  @Override
  public void clearChildren() {
    children = null;
  }

  @Override
  public void mergeWith(LabelIndexLookup labelIndexLookup) {
    Objects.requireNonNull(labelIndexLookup);

    Map<Label, Integer> labelIndices = labelIndexLookup.getAllLabelIndices();
    if (!labelIndices.isEmpty()) {
      initLabelIndices();
      this.labelIndices.putAll(labelIndices);
    }

    List<LabelIndexLookup> children = labelIndexLookup.getChildren();
    if (!children.isEmpty()) {
      initChildren();
      this.children.addAll(children);
    }
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private void initLabelIndices() {
    if (this.labelIndices == null) {
      this.labelIndices = new HashMap<>();
    }
  }

  private void initChildren() {
    if (this.children == null) {
      this.children = new ArrayList<>();
    }
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
