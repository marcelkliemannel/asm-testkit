package dev.turingcomplete.asmtestkit.assertion.representation;

import org.objectweb.asm.Label;
import org.objectweb.asm.tree.LocalVariableAnnotationNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Creates a {@link String} representation of a {@link LocalVariableAnnotationNode}.
 *
 * <p>Example output:
 * {@code @TypeParameterAnnotation // reference: local_variable=; path: null // range: L0-L1-1}.
 */
public class LocalVariableAnnotationNodeRepresentation
        extends TypeAnnotationNodeRepresentation<LocalVariableAnnotationNodeRepresentation, LocalVariableAnnotationNode> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link LocalVariableAnnotationNodeRepresentation} instance.
   */
  public static final LocalVariableAnnotationNodeRepresentation INSTANCE = new LocalVariableAnnotationNodeRepresentation();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private LabelNodeRepresentation labelNodeRepresentation = LabelNodeRepresentation.INSTANCE;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public LocalVariableAnnotationNodeRepresentation() {
    super(LocalVariableAnnotationNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Sets the used {@link LabelNodeRepresentation}.
   *
   * <p>The default value is {@link LabelNodeRepresentation#INSTANCE}.
   *
   * @param labelNodeRepresentation a {@link LabelNodeRepresentation};
   *                                never null.
   * @return {@code this} {@link LocalVariableAnnotationNodeRepresentation};
   * never null.
   */
  public LocalVariableAnnotationNodeRepresentation useLabelNodeRepresentation(LabelNodeRepresentation labelNodeRepresentation) {
    this.labelNodeRepresentation = Objects.requireNonNull(labelNodeRepresentation);

    return this;
  }

  @Override
  protected String doToStringOf(LocalVariableAnnotationNode annotationNode) {
    return toStringOf(annotationNode, Map.of());
  }

  public String toStringOf(LocalVariableAnnotationNode annotationNode, Map<Label, String> labelNames) {
    String representation = super.doToStringOf(annotationNode);

    representation += toRangeStringOf(annotationNode, labelNames).stream().collect(Collectors.joining("; ", " // range: ", ""));

    return representation;
  }

  public List<String> toRangeStringOf(LocalVariableAnnotationNode annotationNode, Map<Label, String> labelNames) {
    List<String> ranges = new ArrayList<>();

    for (int i = 0; i < annotationNode.start.size(); i++) {
      String range = labelNodeRepresentation.toStringOf(annotationNode.start.get(i), labelNames);
      range += "-";
      range += labelNodeRepresentation.toStringOf(annotationNode.end.get(i), labelNames);
      range += "-";
      range += annotationNode.index.get(i);
      ranges.add(range);
    }

    return ranges;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
