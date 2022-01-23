package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.comparator._internal.WithLabelNamesIterableAsmComparator;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Comparator;

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
