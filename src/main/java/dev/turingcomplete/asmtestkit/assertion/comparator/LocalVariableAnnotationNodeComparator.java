package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.comparator._internal.ComparatorUtils;
import dev.turingcomplete.asmtestkit.assertion.comparator._internal.IterableComparator;
import dev.turingcomplete.asmtestkit.assertion.representation.TypeAnnotationNodeRepresentation;
import org.objectweb.asm.tree.LocalVariableAnnotationNode;

import java.util.Comparator;
/**
 * A comparison function to order {@link LocalVariableAnnotationNode}s.
 *
 * <p>Two {@code LocalVariableAnnotationNode}s will be considered as equal if
 * their descriptors, values (order is ignored), type references, type paths,
 * start labels, end labels and indices are equal. If their values are not equal
 * they will be ordered based on the lexicographical order of their
 * {@link TypeAnnotationNodeRepresentation}s.
 */
public class LocalVariableAnnotationNodeComparator
        extends TypeAnnotationNodeComparator<LocalVariableAnnotationNodeComparator, LocalVariableAnnotationNode> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link LocalVariableAnnotationNodeComparator} instance.
   */
  public static final LocalVariableAnnotationNodeComparator INSTANCE = createForLocalVariableAnnotationNode();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link LocalVariableAnnotationNode}s.
   */
  public static final Comparator<Iterable<? extends LocalVariableAnnotationNode>> ITERABLE_INSTANCE = new IterableComparator<>(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected LocalVariableAnnotationNodeComparator() {
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link LocalVariableAnnotationNodeComparator} instance.
   *
   * @return a new {@link LocalVariableAnnotationNodeComparator}; never null;
   */
  public static LocalVariableAnnotationNodeComparator createForLocalVariableAnnotationNode() {
    return new LocalVariableAnnotationNodeComparator();
  }

  @Override
  protected int doCompare(LocalVariableAnnotationNode first, LocalVariableAnnotationNode second) {
    int typeAnnotationNodeCompare = super.doCompare(first, second);
    if (typeAnnotationNodeCompare != 0) {
      return typeAnnotationNodeCompare;
    }

    return Comparator.comparing((LocalVariableAnnotationNode localVariableAnnotationNode) -> localVariableAnnotationNode.start, LabelNodeComparator.ITERABLE_INSTANCE)
                     .thenComparing((LocalVariableAnnotationNode localVariableAnnotationNode) -> localVariableAnnotationNode.end, LabelNodeComparator.ITERABLE_INSTANCE)
                     .thenComparing((LocalVariableAnnotationNode localVariableAnnotationNode) -> localVariableAnnotationNode.index, new IterableComparator<>(ComparatorUtils.INTEGER_COMPARATOR))
                     .compare(first, second);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
