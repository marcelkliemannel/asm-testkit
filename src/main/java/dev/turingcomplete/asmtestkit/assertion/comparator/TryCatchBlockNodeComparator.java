package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.asmutils.TypeUtils;
import dev.turingcomplete.asmtestkit.assertion.LabelNameLookup;
import dev.turingcomplete.asmtestkit.assertion.comparator._internal.WithLabelNamesIterableAsmComparator;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.TryCatchBlockNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Comparator;

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
  public static final Comparator<Iterable<? extends TryCatchBlockNode>> ITERABLE_INSTANCE = WithLabelNamesIterableAsmComparator.create(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  protected int doCompare(TryCatchBlockNode first, TryCatchBlockNode second, LabelNameLookup labelNameLookup) {
    return WithLabelNamesAsmComparator.comparing((TryCatchBlockNode tryCatchBlockNode) -> tryCatchBlockNode.start, asmComparators.elementComparator(LabelNode.class), labelNameLookup)
                                      .thenComparing((TryCatchBlockNode tryCatchBlockNode) -> tryCatchBlockNode.end, asmComparators.elementComparator(LabelNode.class))
                                      .thenComparing((TryCatchBlockNode tryCatchBlockNode) -> tryCatchBlockNode.handler, asmComparators.elementComparator(LabelNode.class))
                                      .thenComparing((TryCatchBlockNode tryCatchBlockNode) -> TypeUtils.toTypeElseNull(tryCatchBlockNode.type), asmComparators.elementComparator(Type.class))
                                      .thenComparing((TryCatchBlockNode tryCatchBlockNode) -> tryCatchBlockNode.visibleTypeAnnotations, asmComparators.iterableComparator(TypeAnnotationNode.class))
                                      .thenComparing((TryCatchBlockNode tryCatchBlockNode) -> tryCatchBlockNode.invisibleTypeAnnotations, asmComparators.iterableComparator(TypeAnnotationNode.class))
                                      .compare(first, second);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
