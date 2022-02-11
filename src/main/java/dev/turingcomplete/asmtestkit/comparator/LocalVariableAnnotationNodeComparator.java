package dev.turingcomplete.asmtestkit.comparator;

import dev.turingcomplete.asmtestkit.assertion.DefaultLabelIndexLookup;
import dev.turingcomplete.asmtestkit.assertion.LabelIndexLookup;
import dev.turingcomplete.asmtestkit.comparator._internal.ComparatorUtils;
import dev.turingcomplete.asmtestkit.comparator._internal.IterableComparator;
import dev.turingcomplete.asmtestkit.comparator._internal.WithLabelIndexIterableAsmComparator;
import dev.turingcomplete.asmtestkit.representation.AbstractTypeAnnotationNodeRepresentation;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LocalVariableAnnotationNode;

import java.util.Comparator;

/**
 * A comparison function to order {@link LocalVariableAnnotationNode}s.
 *
 * <p>Two {@code LocalVariableAnnotationNode}s will be considered as equal if
 * their descriptors, values (order is ignored), type references, type paths,
 * start labels, end labels and indices are equal. If their values are not equal
 * they will be ordered based on the lexicographical order of their
 * {@link AbstractTypeAnnotationNodeRepresentation}s.
 */
public class LocalVariableAnnotationNodeComparator
        extends AbstractTypeAnnotationNodeComparator<LocalVariableAnnotationNodeComparator, LocalVariableAnnotationNode>
        implements WithLabelIndexAsmComparator<LocalVariableAnnotationNode> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link LocalVariableAnnotationNodeComparator} instance.
   */
  public static final LocalVariableAnnotationNodeComparator INSTANCE = create();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link LocalVariableAnnotationNode}s.
   */
  public static final Comparator<Iterable<? extends LocalVariableAnnotationNode>> ITERABLE_INSTANCE = WithLabelIndexIterableAsmComparator.create(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected LocalVariableAnnotationNodeComparator() {
    super(LocalVariableAnnotationNodeComparator.class, LocalVariableAnnotationNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link LocalVariableAnnotationNodeComparator} instance.
   *
   * @return a new {@link LocalVariableAnnotationNodeComparator}; never null;
   */
  public static LocalVariableAnnotationNodeComparator create() {
    return new LocalVariableAnnotationNodeComparator();
  }

  @Override
  protected int doCompare(LocalVariableAnnotationNode first, LocalVariableAnnotationNode second) {
    return doCompare(first, second, DefaultLabelIndexLookup.create());
  }

  @Override
  protected int doCompare(LocalVariableAnnotationNode first, LocalVariableAnnotationNode second, LabelIndexLookup labelIndexLookup) {
    int typeAnnotationNodeCompare = super.doCompare(first, second, labelIndexLookup);
    if (typeAnnotationNodeCompare != 0) {
      return typeAnnotationNodeCompare;
    }

    return WithLabelIndexAsmComparator.comparing((LocalVariableAnnotationNode localVariableAnnotationNode) -> localVariableAnnotationNode.start, asmComparators.iterableComparator(LabelNode.class), labelIndexLookup)
                                      .thenComparing((LocalVariableAnnotationNode localVariableAnnotationNode) -> localVariableAnnotationNode.end, asmComparators.iterableComparator(LabelNode.class))
                                      .thenComparing((LocalVariableAnnotationNode localVariableAnnotationNode) -> localVariableAnnotationNode.index, new IterableComparator<>(ComparatorUtils.INTEGER_COMPARATOR))
                                      .compare(first, second);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
