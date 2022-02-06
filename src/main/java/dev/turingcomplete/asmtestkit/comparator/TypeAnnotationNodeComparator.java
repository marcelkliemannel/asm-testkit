package dev.turingcomplete.asmtestkit.comparator;

import dev.turingcomplete.asmtestkit.comparator._internal.WithLabelNamesIterableAsmComparator;
import dev.turingcomplete.asmtestkit.representation.AbstractTypeAnnotationNodeRepresentation;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Comparator;

/**
 * A comparison function to order {@link TypeAnnotationNode}s.
 *
 * <p>Two {@code TypeAnnotationNode}s will be considered as equal if their
 * descriptors, values (order is ignored), type references, and type paths are
 * equal. If their values are not equal they will be ordered based on the
 * lexicographical order of their {@link AbstractTypeAnnotationNodeRepresentation}s.
 */
public class TypeAnnotationNodeComparator
        extends AbstractTypeAnnotationNodeComparator<TypeAnnotationNodeComparator, TypeAnnotationNode> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link TypeAnnotationNodeComparator} instance.
   */
  public static final TypeAnnotationNodeComparator INSTANCE = new TypeAnnotationNodeComparator();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link TypeAnnotationNode}s.
   */
  public static final Comparator<Iterable<? extends TypeAnnotationNode>> ITERABLE_INSTANCE = WithLabelNamesIterableAsmComparator.create(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected TypeAnnotationNodeComparator() {
    super(TypeAnnotationNodeComparator.class, TypeAnnotationNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link TypeAnnotationNodeComparator} instance.
   *
   * @return a new {@link TypeAnnotationNodeComparator}; never null;
   */
  public static TypeAnnotationNodeComparator create() {
    return new TypeAnnotationNodeComparator();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
