package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.LabelNameLookup;
import dev.turingcomplete.asmtestkit.assertion.comparator._internal.ComparatorUtils;
import dev.turingcomplete.asmtestkit.assertion.comparator._internal.WithLabelNamesIterableComparator;
import org.objectweb.asm.tree.TryCatchBlockNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Comparator;
import java.util.Objects;

/**
 * A comparison function to order {@link TryCatchBlockNode}s.
 *
 * <p>Two {@code TryCatchBlockNode}s will be considered as equal if their start,
 * end and handler labels, type, and (in)visible type annotations are equal.
 */
public class TryCatchBlockNodeComparator extends AbstractWithLabelNamesAsmComparator<TryCatchBlockNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link TryCatchBlockNodeComparator} instance.
   */
  public static final TryCatchBlockNodeComparator INSTANCE = new TryCatchBlockNodeComparator();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link TryCatchBlockNode}s.
   */
  public static final Comparator<Iterable<? extends TryCatchBlockNode>> ITERABLE_INSTANCE = new WithLabelNamesIterableComparator<>(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private LabelNodeComparator                                labelNodeComparator           = LabelNodeComparator.INSTANCE;
  private Comparator<Iterable<? extends TypeAnnotationNode>> typeAnnotationNodesComparator = TypeAnnotationNodeComparator.ITERABLE_INSTANCE;

  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Sets the used {@link LabelNodeComparator}.
   *
   * <p>The default value is {@link LabelNodeComparator#INSTANCE}.
   *
   * @param labelNodeComparator a {@link LabelNodeComparator}; never null.
   * @return {@code this} {@link TryCatchBlockNodeComparator}; never null.
   */
  public TryCatchBlockNodeComparator useLabelNodeComparator(LabelNodeComparator labelNodeComparator) {
    this.labelNodeComparator = Objects.requireNonNull(labelNodeComparator);

    return this;
  }

  /**
   * Sets the used {@link Comparator} for an {@link Iterable} of
   * {@link TypeAnnotationNode}s
   *
   * <p>The default value is {@link TypeAnnotationNodeComparator#ITERABLE_INSTANCE}.
   *
   * @param typeAnnotationNodesComparator a {@link Comparator} for an {@link Iterable}
   *                                      of {@link TypeAnnotationNode}s; never null.
   * @return {@code this} {@link TryCatchBlockNodeComparator}; never null.
   */
  public TryCatchBlockNodeComparator useTypeAnnotationNodesComparator(Comparator<Iterable<? extends TypeAnnotationNode>> typeAnnotationNodesComparator) {
    this.typeAnnotationNodesComparator = Objects.requireNonNull(typeAnnotationNodesComparator);

    return this;
  }

  @Override
  protected int doCompare(TryCatchBlockNode first, TryCatchBlockNode second, LabelNameLookup labelNameLookup) {
    return WithLabelNamesAsmComparator.comparing((TryCatchBlockNode tryCatchBlockNode) -> tryCatchBlockNode.start, labelNodeComparator, labelNameLookup)
                                      .thenComparing((TryCatchBlockNode tryCatchBlockNode) -> tryCatchBlockNode.end, labelNodeComparator)
                                      .thenComparing((TryCatchBlockNode tryCatchBlockNode) -> tryCatchBlockNode.handler, labelNodeComparator)
                                      .thenComparing((TryCatchBlockNode tryCatchBlockNode) -> tryCatchBlockNode.type, ComparatorUtils.STRING_COMPARATOR)
                                      .thenComparing((TryCatchBlockNode tryCatchBlockNode) -> tryCatchBlockNode.visibleTypeAnnotations, typeAnnotationNodesComparator)
                                      .thenComparing((TryCatchBlockNode tryCatchBlockNode) -> tryCatchBlockNode.invisibleTypeAnnotations, typeAnnotationNodesComparator)
                                      .compare(first, second);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
