package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.assertion.DefaultLabelIndexLookup;
import dev.turingcomplete.asmtestkit.assertion.LabelIndexLookup;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.tree.LocalVariableAnnotationNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An AssertJ {@link Representation} for a {@link LocalVariableAnnotationNode}.
 *
 * <p>Example output:
 * {@code @TypeParameterAnnotation // reference: local_variable=; path: null // range: L0-L1-1}.
 */
public class LocalVariableAnnotationNodeRepresentation
        extends AbstractTypeAnnotationNodeRepresentation<LocalVariableAnnotationNodeRepresentation, LocalVariableAnnotationNode> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link LocalVariableAnnotationNodeRepresentation} instance.
   */
  public static final LocalVariableAnnotationNodeRepresentation INSTANCE = create();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected LocalVariableAnnotationNodeRepresentation() {
    super(LocalVariableAnnotationNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link LocalVariableAnnotationNodeRepresentation} instance.
   *
   * @return a new {@link LocalVariableAnnotationNodeRepresentation}; never null;
   */
  public static LocalVariableAnnotationNodeRepresentation create() {
    return new LocalVariableAnnotationNodeRepresentation();
  }

  @Override
  protected String doToStringOf(LocalVariableAnnotationNode annotationNode) {
    return toStringOf(annotationNode, DefaultLabelIndexLookup.create());
  }

  @Override
  public String doToStringOf(LocalVariableAnnotationNode annotationNode, LabelIndexLookup labelIndexLookup) {
    String representation = super.doToStringOf(annotationNode);

    representation += toRangeStringOf(annotationNode, labelIndexLookup).stream().collect(Collectors.joining("; ", " // range: ", ""));

    return representation;
  }

  public List<String> toRangeStringOf(LocalVariableAnnotationNode annotationNode, LabelIndexLookup labelIndexLookup) {
    List<String> ranges = new ArrayList<>();

    for (int i = 0; i < annotationNode.start.size(); i++) {
      String range = "#" + annotationNode.index.get(i) + " ";
      range += asmRepresentations.toStringOf(annotationNode.start.get(i), labelIndexLookup);
      range += "-";
      range += asmRepresentations.toStringOf(annotationNode.end.get(i), labelIndexLookup);
      ranges.add(range);
    }

    return ranges;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
