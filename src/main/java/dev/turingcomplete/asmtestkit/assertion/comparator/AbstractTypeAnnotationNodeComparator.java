package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.LabelNameLookup;
import dev.turingcomplete.asmtestkit.assertion.representation.AbstractTypeAnnotationNodeRepresentation;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Comparator;

import static dev.turingcomplete.asmtestkit.assertion.comparator._internal.ComparatorUtils.INTEGER_COMPARATOR;

/**
 * A comparison function to order {@link TypeAnnotationNode}s.
 *
 * <p>Two {@code TypeAnnotationNode}s will be considered as equal if their
 * descriptors, values (order is ignored), type references, and type paths are
 * equal. If their values are not equal they will be ordered based on the
 * lexicographical order of their {@link AbstractTypeAnnotationNodeRepresentation}s.
 */
public abstract class AbstractTypeAnnotationNodeComparator<S extends AbstractTypeAnnotationNodeComparator<S, T>, T extends TypeAnnotationNode>
        extends AbstractAnnotationNodeComparator<S, T> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AbstractTypeAnnotationNodeComparator(Class<?> selfType, Class<?> elementType) {
    super(selfType, elementType);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  protected int doCompare(T first, T second) {
    return doCompare(first, second, LabelNameLookup.EMPTY);
  }

  @Override
  protected int doCompare(T first, T second, LabelNameLookup labelNameLookup) {
    int annotationNodeCompare = super.doCompare(first, second, labelNameLookup);
    if (annotationNodeCompare != 0) {
      return annotationNodeCompare;
    }

    return Comparator.comparing((TypeAnnotationNode typeAnnotationNode) -> typeAnnotationNode.typeRef, INTEGER_COMPARATOR)
                     .thenComparing((TypeAnnotationNode typeAnnotationNode) -> typeAnnotationNode.typePath, asmComparators.elementComparator(TypePath.class))
                     .compare(first, second);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
