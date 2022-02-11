package dev.turingcomplete.asmtestkit.comparator;

import dev.turingcomplete.asmtestkit.assertion.LabelIndexLookup;
import dev.turingcomplete.asmtestkit.comparator._internal.WithLabelIndexIterableAsmComparator;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.TryCatchBlockNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Comparator;

import static dev.turingcomplete.asmtestkit.asmutils.TypeUtils.nameToTypeElseNull;

/**
 * A comparison function to order {@link TryCatchBlockNode}s.
 *
 * <p>Two {@code TryCatchBlockNode}s will be considered as equal if if all the
 * {@code public} {@link TryCatchBlockNode} fields are equal. Otherwise, they
 * will be ordered by the comparison of the first non-matching field.
 */
public class TryCatchBlockNodeComparator extends AbstractWithLabelIndexAsmComparator<TryCatchBlockNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link TryCatchBlockNodeComparator} instance.
   */
  public static final TryCatchBlockNodeComparator INSTANCE = create();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link TryCatchBlockNode}s.
   */
  public static final Comparator<Iterable<? extends TryCatchBlockNode>> ITERABLE_INSTANCE = WithLabelIndexIterableAsmComparator.create(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected TryCatchBlockNodeComparator() {
    super(TryCatchBlockNodeComparator.class, TryCatchBlockNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link TryCatchBlockNodeComparator} instance.
   *
   * @return a new {@link TryCatchBlockNodeComparator}; never null;
   */
  public static TryCatchBlockNodeComparator create() {
    return new TryCatchBlockNodeComparator();
  }

  @Override
  protected int doCompare(TryCatchBlockNode first, TryCatchBlockNode second, LabelIndexLookup labelIndexLookup) {
    return WithLabelIndexAsmComparator.comparing((TryCatchBlockNode tryCatchBlockNode) -> tryCatchBlockNode.start, asmComparators.elementComparator(LabelNode.class), labelIndexLookup)
                                      .thenComparing((TryCatchBlockNode tryCatchBlockNode) -> tryCatchBlockNode.end, asmComparators.elementComparator(LabelNode.class))
                                      .thenComparing((TryCatchBlockNode tryCatchBlockNode) -> tryCatchBlockNode.handler, asmComparators.elementComparator(LabelNode.class))
                                      .thenComparing((TryCatchBlockNode tryCatchBlockNode) -> nameToTypeElseNull(tryCatchBlockNode.type), asmComparators.elementComparator(Type.class))
                                      .thenComparing((TryCatchBlockNode tryCatchBlockNode) -> tryCatchBlockNode.visibleTypeAnnotations, asmComparators.iterableComparator(TypeAnnotationNode.class))
                                      .thenComparing((TryCatchBlockNode tryCatchBlockNode) -> tryCatchBlockNode.invisibleTypeAnnotations, asmComparators.iterableComparator(TypeAnnotationNode.class))
                                      .compare(first, second);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
