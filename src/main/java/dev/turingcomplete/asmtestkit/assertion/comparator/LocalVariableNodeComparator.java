package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.LabelNameLookup;
import dev.turingcomplete.asmtestkit.assertion.comparator._internal.ComparatorUtils;
import dev.turingcomplete.asmtestkit.assertion.comparator._internal.WithLabelNamesIterableComparator;
import org.objectweb.asm.tree.LocalVariableNode;

import java.util.Comparator;

/**
 * A comparison function to order {@link LocalVariableNode}s.
 *
 * <p>Two {@code LocalVariableNode}s will be considered as equal if their index,
 * name, descriptor, signature, start and end label are equal.
 */
public class LocalVariableNodeComparator extends AbstractWithLabelNamesAsmComparator<LocalVariableNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link LabelNodeComparator} instance.
   */
  public static final LocalVariableNodeComparator INSTANCE = new LocalVariableNodeComparator();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link LocalVariableNode}s.
   */
  public static final Comparator<Iterable<? extends LocalVariableNode>> ITERABLE_INSTANCE = new WithLabelNamesIterableComparator<>(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private LabelNodeComparator labelNodeComparator = LabelNodeComparator.INSTANCE;

  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Sets the used {@link LabelNodeComparator}.
   *
   * <p>The default value is {@link LabelNodeComparator#INSTANCE}.
   *
   * @param labelNodeComparator an {@link LabelNodeComparator}; never null.
   * @return {@code this} {@link LocalVariableNodeComparator}; never null.
   */
  public LocalVariableNodeComparator useLabelNodeComparator(LabelNodeComparator labelNodeComparator) {
    this.labelNodeComparator = labelNodeComparator;

    return this;
  }

  @Override
  protected int doCompare(LocalVariableNode first, LocalVariableNode second, LabelNameLookup labelNameLookup) {
    return WithLabelNamesAsmComparator.comparing((LocalVariableNode localVariableNode) -> localVariableNode.index, ComparatorUtils.INTEGER_COMPARATOR, labelNameLookup)
                                      .thenComparing((LocalVariableNode localVariableNode) -> localVariableNode.name, ComparatorUtils.STRING_COMPARATOR)
                                      .thenComparing((LocalVariableNode localVariableNode) -> localVariableNode.desc, ComparatorUtils.STRING_COMPARATOR)
                                      .thenComparing((LocalVariableNode localVariableNode) -> localVariableNode.signature, ComparatorUtils.STRING_COMPARATOR)
                                      .thenComparing((LocalVariableNode localVariableNode) -> localVariableNode.start, labelNodeComparator)
                                      .thenComparing((LocalVariableNode localVariableNode) -> localVariableNode.end, labelNodeComparator)
                                      .compare(first, second);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
