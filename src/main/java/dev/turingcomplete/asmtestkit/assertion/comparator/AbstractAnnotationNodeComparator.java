package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.LabelIndexLookup;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

import static dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils.convertAnnotationNodeValuesToMap;

/**
 * A base class for a comparison function to order {@link AnnotationNode}s or
 * its subtypes.
 */
public abstract class AbstractAnnotationNodeComparator<S extends AbstractAnnotationNodeComparator<S, T>, T extends AnnotationNode>
        extends AbstractWithLabelNamesAsmComparator<T> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AbstractAnnotationNodeComparator(Class<?> selfType, Class<?> elementType) {
    super(selfType, elementType);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  protected int doCompare(T first, T second, LabelIndexLookup labelIndexLookup) {
    int descResult = Comparator.comparing((AnnotationNode annotationNode) -> annotationNode.desc)
                               .compare(first, second);

    if (descResult == 0) {
      Map<Object, Object> firstValues = first != null ? convertAnnotationNodeValuesToMap(first.values) : Map.of();
      Map<Object, Object> secondValues = second != null ? convertAnnotationNodeValuesToMap(second.values) : Map.of();
      if (Objects.deepEquals(firstValues, secondValues)) {
        return 0;
      }
    }

    return asmRepresentations.toStringOf(first).compareTo(asmRepresentations.toStringOf(second));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
