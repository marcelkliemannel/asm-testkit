package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.comparator._internal.IterableComparator;
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
 * descriptors and values (order is ignored) are equal. If their values are not
 * equal they will be ordered based on the lexicographical order of their
 * {@link AnnotationNodeRepresentation}s.
 */
public class AnnotationNodeComparator<S extends AnnotationNodeComparator<S, A>, A extends AnnotationNode>
        extends AsmComparator<A> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link AnnotationNodeComparator} instance.
   */
  public static final AnnotationNodeComparator<?, AnnotationNode> INSTANCE = createForAnnotationNode();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link AnnotationNode}s.
   */
  public static final Comparator<Iterable<? extends AnnotationNode>> ITERABLE_INSTANCE = new IterableComparator<>(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private AnnotationNodeRepresentation<?, AnnotationNode> annotationNodeRepresentation = AnnotationNodeRepresentation.INSTANCE;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AnnotationNodeComparator() {
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link AnnotationNodeComparator} instance.
   *
   * @return a new {@link AnnotationNodeComparator}; never null;
   */
  public static AnnotationNodeComparator<?, AnnotationNode> createForAnnotationNode() {
    return new AnnotationNodeComparator<>();
  }

  /**
   * Sets the used {@link AnnotationNodeRepresentation}.
   *
   * <p>The default value is {@link AnnotationNodeRepresentation#INSTANCE}.
   *
   * @param annotationNodeRepresentation an {@link AnnotationNodeRepresentation};
   *                                     never null.
   * @return {@code this} {@link S}; never null.
   */
  protected S useAnnotationNodeRepresentation(AnnotationNodeRepresentation<?, AnnotationNode> annotationNodeRepresentation) {
    this.annotationNodeRepresentation = Objects.requireNonNull(annotationNodeRepresentation);

    //noinspection unchecked
    return (S) this;
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

    return annotationNodeRepresentation.toStringOf(first)
                                       .compareTo(annotationNodeRepresentation.toStringOf(second));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
