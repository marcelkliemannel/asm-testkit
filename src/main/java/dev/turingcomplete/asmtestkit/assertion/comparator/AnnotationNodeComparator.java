package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.comparator._internal.IterableComparator;
import dev.turingcomplete.asmtestkit.assertion.representation.AnnotationNodeRepresentation;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.Comparator;
import java.util.Objects;

/**
 * A comparison function to order {@link AnnotationNode}s.
 *
 * <p>Two {@code AnnotationNode}s will be considered as equal if their
 * descriptors and values (order is ignored) are equal. If their values are not
 * equal they will be ordered based on the lexicographical order of their
 * {@link AnnotationNodeRepresentation}s.
 */
public class AnnotationNodeComparator extends AbstractAnnotationNodeComparator<AnnotationNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link AnnotationNodeComparator} instance.
   */
  public static final AnnotationNodeComparator INSTANCE = new AnnotationNodeComparator();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link AnnotationNode}s.
   */
  public static final Comparator<Iterable<? extends AnnotationNode>> ITERABLE_INSTANCE = new IterableComparator<>(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public AnnotationNodeComparator() {
    super(AnnotationNodeRepresentation.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Sets the used {@link AnnotationNodeRepresentation}.
   *
   * <p>The default value is {@link AnnotationNodeRepresentation#INSTANCE}.
   *
   * @param annotationNodeRepresentation a {@link AnnotationNodeRepresentation}; never null.
   * @return {@code this} {@link AnnotationNodeComparator}; never null.
   */
  public AnnotationNodeComparator useAnnotationNodeRepresentation(AnnotationNodeRepresentation annotationNodeRepresentation) {
    useAbstractAnnotationNodeRepresentation(Objects.requireNonNull(annotationNodeRepresentation));
    return  this;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
