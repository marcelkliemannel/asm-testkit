package dev.turingcomplete.asmtestkit.comparator;

import dev.turingcomplete.asmtestkit.comparator._internal.WithLabelIndexIterableAsmComparator;
import dev.turingcomplete.asmtestkit.representation.AnnotationNodeRepresentation;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.Comparator;

/**
 * A comparison function to order {@link AnnotationNode}s.
 *
 * <p>Two {@code AnnotationNode}s will be considered as equal if their
 * descriptors and values (order is ignored) are equal. If their values are not
 * equal they will be ordered based on the lexicographical order of their
 * {@link AnnotationNodeRepresentation}s.
 */
public class AnnotationNodeComparator extends AbstractAnnotationNodeComparator<AnnotationNodeComparator, AnnotationNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link AnnotationNodeComparator} instance.
   */
  public static final AnnotationNodeComparator INSTANCE = new AnnotationNodeComparator();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link AnnotationNode}s.
   */
  public static final Comparator<Iterable<? extends AnnotationNode>> ITERABLE_INSTANCE = WithLabelIndexIterableAsmComparator.create(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AnnotationNodeComparator() {
    super(AnnotationNodeComparator.class, AnnotationNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link AnnotationNodeComparator} instance.
   *
   * @return a new {@link AnnotationNodeComparator}; never null;
   */
  public static AnnotationNodeComparator create() {
    return new AnnotationNodeComparator();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
