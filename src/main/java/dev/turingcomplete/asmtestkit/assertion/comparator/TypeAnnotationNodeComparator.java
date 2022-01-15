package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.comparator._internal.IterableComparator;
import dev.turingcomplete.asmtestkit.assertion.representation.TypeAnnotationNodeRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.TypePathRepresentation;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Comparator;
import java.util.Objects;

import static dev.turingcomplete.asmtestkit.assertion.comparator._internal.ComparatorUtils.INTEGER_COMPARATOR;

/**
 * A comparison function to order {@link TypeAnnotationNode}s.
 *
 * <p>Two {@code TypeAnnotationNode}s will be considered as equal if their
 * descriptors, values (order is ignored), type references and type paths are
 * equal. If their values are not equal they will be ordered based on the
 * lexicographical order of their {@link TypeAnnotationNodeRepresentation}s.
 */
public class TypeAnnotationNodeComparator<S extends TypeAnnotationNodeComparator<S, A>, A extends TypeAnnotationNode>
        extends AnnotationNodeComparator<S, A> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link TypeAnnotationNodeComparator} instance.
   */
  public static final TypeAnnotationNodeComparator<?, TypeAnnotationNode> INSTANCE = createForTypeAnnotationNode();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link TypeAnnotationNode}s.
   */
  public static final Comparator<Iterable<? extends TypeAnnotationNode>> ITERABLE_INSTANCE = new IterableComparator<>(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private TypePathComparator typePathComparator = TypePathComparator.INSTANCE;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected TypeAnnotationNodeComparator() {
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link TypeAnnotationNodeComparator} instance.
   *
   * @return a new {@link TypeAnnotationNodeComparator}; never null;
   */
  public static TypeAnnotationNodeComparator<?, TypeAnnotationNode> createForTypeAnnotationNode() {
    return new TypeAnnotationNodeComparator<>();
  }

  /**
   * Sets the used {@link TypePathComparator}.
   *
   * <p>The default value is {@link TypePathComparator#INSTANCE}.
   *
   * @param typePathComparator a {@link TypePathRepresentation}; never null.
   * @return {@code this} {@link TypeAnnotationNodeComparator}; never null.
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
