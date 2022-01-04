package dev.turingcomplete.asmtestkit.assertion.comperator;

import dev.turingcomplete.asmtestkit.assertion.representation.AnnotationNodeRepresentation;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

import static dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils.convertAnnotationNodeValuesToMap;

/**
 * A comparison function to order {@link AnnotationNode}s.
 *
 * <p>Two {@code AnnotationNode}s will be considered as equal if their
 * descriptors and values are equal, although the order of the values is not
 * taken into account. Otherwise, they will be ordered based on the
 * lexicographical order of their {@link AnnotationNodeRepresentation}.
 */
public final class AnnotationNodeComparator extends AsmComparator<AnnotationNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  private static final AnnotationNodeComparator INSTANCE = new AnnotationNodeComparator();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Gets a reusable {@link AnnotationNodeComparator} instance.
   *
   * @return a {@link AnnotationNodeComparator} instance; never null.
   */
  public static AnnotationNodeComparator instance() {
    return INSTANCE;
  }

  @Override
  protected int doCompare(AnnotationNode first, AnnotationNode second) {
    int descResult = Comparator.comparing((AnnotationNode annotationNode) -> annotationNode.desc)
                               .compare(first, second);

    if (descResult == 0) {
      Map<Object, Object> firstValues = first != null ? convertAnnotationNodeValuesToMap(first.values) : Map.of();
      Map<Object, Object> secondValues = second != null ? convertAnnotationNodeValuesToMap(second.values) : Map.of();
      if (Objects.deepEquals(firstValues, secondValues)) {
        return 0;
      }
    }

    return AnnotationNodeRepresentation.instance().toStringOf(first)
                                       .compareTo(AnnotationNodeRepresentation.instance().toStringOf(second));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
