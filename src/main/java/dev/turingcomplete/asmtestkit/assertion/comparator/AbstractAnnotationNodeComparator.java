package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.representation.AnnotationNodeRepresentation;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

import static dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils.convertAnnotationNodeValuesToMap;

/**
 * A base class for a comparison function to order {@link AnnotationNode}s or
 * its subtypes.
 */
public abstract class AbstractAnnotationNodeComparator<S extends AbstractAnnotationNodeComparator<S, A>, A extends AnnotationNode>
        extends AsmComparator<A> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private AnnotationNodeRepresentation annotationNodeRepresentation = AnnotationNodeRepresentation.INSTANCE;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AbstractAnnotationNodeComparator() {
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Sets the used {@link AnnotationNodeRepresentation}.
   *
   * <p>The default value is {@link AnnotationNodeRepresentation#INSTANCE}.
   *
   * @param annotationNodeRepresentation an {@link AnnotationNodeRepresentation};
   *                                     never null.
   * @return {@code this} {@link S}; never null.
   */
  protected S useAnnotationNodeRepresentation(AnnotationNodeRepresentation annotationNodeRepresentation) {
    this.annotationNodeRepresentation = Objects.requireNonNull(annotationNodeRepresentation);

    //noinspection unchecked
    return (S) this;
  }

  @Override
  protected int doCompare(A first, A second) {
    int descResult = Comparator.comparing((AnnotationNode annotationNode) -> annotationNode.desc)
                               .compare(first, second);

    if (descResult == 0) {
      Map<Object, Object> firstValues = first != null ? convertAnnotationNodeValuesToMap(first.values) : Map.of();
      Map<Object, Object> secondValues = second != null ? convertAnnotationNodeValuesToMap(second.values) : Map.of();
      if (Objects.deepEquals(firstValues, secondValues)) {
        return 0;
      }
    }

    return annotationNodeRepresentation.toStringOf(first)
                                       .compareTo(annotationNodeRepresentation.toStringOf(second));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
