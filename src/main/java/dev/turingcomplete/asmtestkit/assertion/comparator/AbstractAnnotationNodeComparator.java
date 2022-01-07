package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.representation.AbstractAnnotationNodeRepresentation;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

import static dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils.convertAnnotationNodeValuesToMap;

abstract class AbstractAnnotationNodeComparator<A extends AnnotationNode> extends AsmComparator<A> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private AbstractAnnotationNodeRepresentation<?, ?> abstractAnnotationNodeRepresentation;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public AbstractAnnotationNodeComparator(AbstractAnnotationNodeRepresentation<?, ?> abstractAnnotationNodeRepresentation) {
    this.abstractAnnotationNodeRepresentation = Objects.requireNonNull(abstractAnnotationNodeRepresentation);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Sets the used {@link AbstractAnnotationNodeRepresentation}.
   *
   * @param abstractAnnotationNodeRepresentation a {@link AbstractAnnotationNodeRepresentation};
   *                                             never null.
   */
  protected void useAbstractAnnotationNodeRepresentation(AbstractAnnotationNodeRepresentation<?, ?> abstractAnnotationNodeRepresentation) {
    this.abstractAnnotationNodeRepresentation = Objects.requireNonNull(abstractAnnotationNodeRepresentation);
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

    return abstractAnnotationNodeRepresentation.toStringOf(first)
                                               .compareTo(abstractAnnotationNodeRepresentation.toStringOf(second));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
