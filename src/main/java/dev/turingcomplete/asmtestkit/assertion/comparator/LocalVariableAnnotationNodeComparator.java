package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.LabelNameLookup;
import dev.turingcomplete.asmtestkit.assertion.comparator._internal.ComparatorUtils;
import dev.turingcomplete.asmtestkit.assertion.comparator._internal.IterableComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator._internal.WithLabelNamesIterableAsmComparator;
import dev.turingcomplete.asmtestkit.assertion.representation.AbstractTypeAnnotationNodeRepresentation;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LocalVariableAnnotationNode;

import java.util.Comparator;
import java.util.Objects;

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
        implements WithLabelNamesAsmComparator<LocalVariableAnnotationNode> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link LocalVariableAnnotationNodeComparator} instance.
   */
  public static final LocalVariableAnnotationNodeComparator INSTANCE = create();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link LocalVariableAnnotationNode}s.
   */
  public static final Comparator<Iterable<? extends LocalVariableAnnotationNode>> ITERABLE_INSTANCE = WithLabelNamesIterableAsmComparator.create(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private Comparator<Iterable<? extends LabelNode>> labelNodesComparator = LabelNodeComparator.ITERABLE_INSTANCE;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected LocalVariableAnnotationNodeComparator() {
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

  /**
   * Sets the used {@link Comparator} for an {@link Iterable} of
   * {@link LabelNode}s
   *
   * <p>The default value is {@link LabelNodeComparator#ITERABLE_INSTANCE}.
   *
   * @param labelNodesComparator a {@link Comparator} for an {@link Iterable}
   *                             of {@link LabelNode}s; never null.
   * @return {@code this} {@link LocalVariableAnnotationNodeComparator}; never null.
   */
  public LocalVariableAnnotationNodeComparator useLabelNodesComparator(Comparator<Iterable<? extends LabelNode>> labelNodesComparator) {
    this.labelNodesComparator = Objects.requireNonNull(labelNodesComparator);

    return this;
  }

  @Override
  protected int doCompare(LocalVariableAnnotationNode first, LocalVariableAnnotationNode second) {
    return doCompare(first, second, LabelNameLookup.EMPTY);
  }

  @Override
  protected int doCompare(LocalVariableAnnotationNode first, LocalVariableAnnotationNode second, LabelNameLookup labelNameLookup) {
    int typeAnnotationNodeCompare = super.doCompare(first, second, labelNameLookup);
    if (typeAnnotationNodeCompare != 0) {
      return typeAnnotationNodeCompare;
    }

    return WithLabelNamesAsmComparator.comparing((LocalVariableAnnotationNode localVariableAnnotationNode) -> localVariableAnnotationNode.start, labelNodesComparator, labelNameLookup)
                                      .thenComparing((LocalVariableAnnotationNode localVariableAnnotationNode) -> localVariableAnnotationNode.end, labelNodesComparator)
                                      .thenComparing((LocalVariableAnnotationNode localVariableAnnotationNode) -> localVariableAnnotationNode.index, new IterableComparator<>(ComparatorUtils.INTEGER_COMPARATOR))
                                      .compare(first, second);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
