package dev.turingcomplete.asmtestkit.assertion.comperator;

import dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils;
import dev.turingcomplete.asmtestkit.assertion.representation.AnnotationNodeRepresentation;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.Map;
import java.util.Objects;

/**
 * A comparison function to order {@link AnnotationNode}s.
 *
 * <p>Two {@code AnnotationNode}s will be considered as equal if their
 * descriptors are equal and have equal values (equal key-value pairs), although
 * the order of the values is not taken into account. Otherwise, they will be
 * ordered based on the lexicographical order of their
 * {@link AnnotationNodeRepresentation}.
 */
public final class AnnotationNodeComparator extends AsmComparator<AnnotationNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  private static final AnnotationNodeComparator INSTANCE = new AnnotationNodeComparator();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  public static AnnotationNodeComparator instance() {
    return INSTANCE;
  }

  @Override
  protected int doCompare(AnnotationNode first, AnnotationNode second) {
    int descResult = stringCompare(first.desc, second.desc);
    if (descResult == 0) {
      Map<Object, Object> firstValues = AnnotationNodeUtils.convertAnnotationNodeValuesToMap(first.values);
      Map<Object, Object> secondValues = AnnotationNodeUtils.convertAnnotationNodeValuesToMap(second.values);
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
