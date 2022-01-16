package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.representation.AbstractTypeAnnotationNodeRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.TypePathRepresentation;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Comparator;
import java.util.Objects;

import static dev.turingcomplete.asmtestkit.assertion.comparator._internal.ComparatorUtils.INTEGER_COMPARATOR;

/**
 * A comparison function to order {@link TypeAnnotationNode}s.
 *
 * <p>Two {@code TypeAnnotationNode}s will be considered as equal if their
 * descriptors, values (order is ignored), type references, and type paths are
 * equal. If their values are not equal they will be ordered based on the
 * lexicographical order of their {@link AbstractTypeAnnotationNodeRepresentation}s.
 */
public abstract class AbstractTypeAnnotationNodeComparator<S extends AbstractTypeAnnotationNodeComparator<S, A>, A extends TypeAnnotationNode>
        extends AbstractAnnotationNodeComparator<S, A> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private TypePathComparator typePathComparator = TypePathComparator.INSTANCE;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AbstractTypeAnnotationNodeComparator() {
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Sets the used {@link TypePathComparator}.
   *
   * <p>The default value is {@link TypePathComparator#INSTANCE}.
   *
   * @param typePathComparator a {@link TypePathRepresentation}; never null.
   * @return {@code this} {@link S}; never null.
   */
  public S useTypePathComparator(TypePathComparator typePathComparator) {
    this.typePathComparator = Objects.requireNonNull(typePathComparator);

    //noinspection unchecked
    return (S) this;
  }

  @Override
  protected int doCompare(A first, A second) {
    int annotationNodeCompare = super.doCompare(first, second);
    if (annotationNodeCompare != 0) {
      return annotationNodeCompare;
    }

    return Comparator.comparing((TypeAnnotationNode typeAnnotationNode) -> typeAnnotationNode.typeRef, INTEGER_COMPARATOR)
                     .thenComparing((TypeAnnotationNode typeAnnotationNode) -> typeAnnotationNode.typePath, typePathComparator)
                     .compare(first, second);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
