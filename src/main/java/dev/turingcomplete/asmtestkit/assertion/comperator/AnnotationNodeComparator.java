package dev.turingcomplete.asmtestkit.assertion.comperator;

import dev.turingcomplete.asmtestkit.assertion.comperator._internal.IterableComparator;
import dev.turingcomplete.asmtestkit.assertion.representation.AnnotationNodeRepresentation;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

import static dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils.convertAnnotationNodeValuesToMap;

/**
 * A comparison function to order {@link AnnotationNode}s.
 *
 * <p>Two {@code AnnotationNode}s will be considered as equal if their
 * descriptors and values are equal, although the order of the values is not
 * taken into account. Otherwise, they will be ordered based on the
 * lexicographical order of their {@link AnnotationNodeRepresentation}s.
 */
public class AnnotationNodeComparator<T extends AnnotationNode> extends AsmComparator<T> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link AnnotationNodeComparator} instance.
   */
  public static final AnnotationNodeComparator<AnnotationNode>       INSTANCE          = new AnnotationNodeComparator<>();
  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link AnnotationNode}s.
   */
  public static final Comparator<Iterable<? extends AnnotationNode>> ITERABLE_INSTANCE = new IterableComparator<>(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private AnnotationNodeRepresentation annotationNodeRepresentation = AnnotationNodeRepresentation.INSTANCE;

  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Sets the used {@link AnnotationNodeRepresentation}.
   *
   * <p>The default value is {@link AnnotationNodeRepresentation#INSTANCE}.
   *
   * @param annotationNodeRepresentation a {@link AnnotationNodeRepresentation}; never null.
   * @return {@code this} {@link AnnotationNodeComparator}; never null.
   */
  public AnnotationNodeComparator<T> useAnnotationNodeRepresentation(AnnotationNodeRepresentation annotationNodeRepresentation) {
    this.annotationNodeRepresentation = Objects.requireNonNull(annotationNodeRepresentation);

    return this;
  }

  @Override
  protected int doCompare(T first, T second) {
    int descResult = Comparator.comparing((AnnotationNode annotationNode) -> annotationNode.desc)
                               .compare(first, second);

    if (descResult == 0) {
      Map<Object, Object> firstValues = first != null ? convertAnnotationNodeValuesToMap(first.values) : Map.of();
      Map<Object, Object> secondValues = second != null ? convertAnnotationNodeValuesToMap(second.values) : Map.of();
      if (Objects.deepEquals(firstValues, secondValues)) {
        return 0;
      }
    }

    return annotationNodeRepresentation.toStringOf(first).compareTo(annotationNodeRepresentation.toStringOf(second));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
