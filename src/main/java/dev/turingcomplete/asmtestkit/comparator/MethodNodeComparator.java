package dev.turingcomplete.asmtestkit.comparator;

import dev.turingcomplete.asmtestkit.asmutils.InsnListUtils;
import dev.turingcomplete.asmtestkit.common.DefaultLabelIndexLookup;
import dev.turingcomplete.asmtestkit.common.LabelIndexLookup;
import dev.turingcomplete.asmtestkit.common.IgnoreLineNumbersCapable;
import dev.turingcomplete.asmtestkit.comparator._internal.IterableComparator;
import dev.turingcomplete.asmtestkit.comparator._internal.WithLabelIndexIterableAsmComparator;
import dev.turingcomplete.asmtestkit.node.AccessNode;
import dev.turingcomplete.asmtestkit.node.AnnotationDefaultNode;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.LocalVariableAnnotationNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ParameterNode;
import org.objectweb.asm.tree.TryCatchBlockNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Comparator;

import static dev.turingcomplete.asmtestkit.asmutils.MethodNodeUtils.extractLabelIndices;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getFromObjectElseNull;
import static dev.turingcomplete.asmtestkit.comparator._internal.ComparatorUtils.INTEGER_COMPARATOR;
import static dev.turingcomplete.asmtestkit.comparator._internal.ComparatorUtils.STRING_COMPARATOR;
import static org.assertj.core.util.Lists.newArrayList;

/**
 * A comparison function to order {@link MethodNode}s.
 *
 * <p>Two {@code MethodNode}s will be considered as equal if all the
 * {@code public} {@link MethodNode} fields are equal. Otherwise, they will
 * be ordered by the comparison of the first non-matching field.
 */
public class MethodNodeComparator
        extends AbstractWithLabelIndexAsmComparator<MethodNode>
        implements IgnoreLineNumbersCapable<MethodNodeComparator> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link MethodNodeComparator} instance.
   */
  public static final MethodNodeComparator INSTANCE = create();

  /**
   * A reusable {@link MethodNodeComparator} instance, which excludes line numbers
   * from the comparison.
   */
  public static final MethodNodeComparator INSTANCE_IGNORE_LINE_NUMBERS = create().ignoreLineNumbers();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link MethodNode}s.
   */
  public static final Comparator<Iterable<? extends MethodNode>> ITERABLE_INSTANCE = WithLabelIndexIterableAsmComparator.create(INSTANCE);

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link MethodNode}s, which excludes line numbers from the comparison.
   */
  public static final Comparator<Iterable<? extends MethodNode>> ITERABLE_INSTANCE_IGNORE_LINE_NUMBERS = WithLabelIndexIterableAsmComparator.create(INSTANCE_IGNORE_LINE_NUMBERS);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private boolean ignoreLineNumbers = false;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected MethodNodeComparator() {
    super(MethodNodeComparator.class, MethodNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link MethodNodeComparator} instance.
   *
   * @return a new {@link MethodNodeComparator}; never null;
   */
  public static MethodNodeComparator create() {
    return new MethodNodeComparator();
  }

  /**
   * Exclude {@link LineNumberNode}s (and its associated {@link LabelNode}s)
   * from the comparison.
   *
   * @return {@code this} {@link MethodNodeComparator}; never null.
   */
  @Override
  public MethodNodeComparator ignoreLineNumbers() {
    this.ignoreLineNumbers = true;

    return this;
  }

  @Override
  protected int doCompare(MethodNode first, MethodNode second) {
    return doCompare(first, second, DefaultLabelIndexLookup.create());
  }

  @Override
  protected int doCompare(MethodNode first, MethodNode second, LabelIndexLookup labelIndexLookup) {
    // Filter line numbers if needed
    first = filterLineNumbers(first);
    second = filterLineNumbers(second);

    // Collect label indices
    // Because of the line number filtering, we may have to overwrite existing
    // indices here as they may have changed.
    labelIndexLookup.putAll(extractLabelIndices(first, second));

    return WithLabelIndexAsmComparator.comparing((MethodNode methodNode) -> methodNode.name, STRING_COMPARATOR, labelIndexLookup)
                                      .thenComparing((MethodNode methodNode) -> getFromObjectElseNull(methodNode.desc, Type::getMethodType), asmComparators.elementComparator(Type.class))
                                      .thenComparing((MethodNode methodNode) -> AccessNode.forField(methodNode.access), asmComparators.elementComparator(AccessNode.class))
                                      .thenComparing((MethodNode methodNode) -> methodNode.signature, STRING_COMPARATOR)
                                      .thenComparing((MethodNode methodNode) -> methodNode.exceptions, new IterableComparator<>(STRING_COMPARATOR))
                                      .thenComparing((MethodNode methodNode) -> methodNode.parameters, asmComparators.iterableComparator(ParameterNode.class))
                                      .thenComparing((MethodNode methodNode) -> methodNode.visibleAnnotations, asmComparators.iterableComparator(AnnotationNode.class))
                                      .thenComparing((MethodNode methodNode) -> methodNode.invisibleAnnotations, asmComparators.iterableComparator(AnnotationNode.class))
                                      .thenComparing((MethodNode methodNode) -> methodNode.visibleTypeAnnotations, asmComparators.iterableComparator(TypeAnnotationNode.class))
                                      .thenComparing((MethodNode methodNode) -> methodNode.invisibleTypeAnnotations, asmComparators.iterableComparator(TypeAnnotationNode.class))
                                      .thenComparing((MethodNode methodNode) -> methodNode.attrs, asmComparators.iterableComparator(Attribute.class))
                                      .thenComparing((MethodNode methodNode) -> methodNode.visibleAnnotableParameterCount, INTEGER_COMPARATOR)
                                      .thenComparing((MethodNode methodNode) -> newArrayList(methodNode.visibleParameterAnnotations), new IterableComparator<>(asmComparators.iterableComparator(AnnotationNode.class)))
                                      .thenComparing((MethodNode methodNode) -> methodNode.invisibleAnnotableParameterCount, INTEGER_COMPARATOR)
                                      .thenComparing((MethodNode methodNode) -> newArrayList(methodNode.invisibleParameterAnnotations), new IterableComparator<>(asmComparators.iterableComparator(AnnotationNode.class)))
                                      .thenComparing((MethodNode methodNode) -> methodNode.instructions, asmComparators.iterableComparator(AbstractInsnNode.class))
                                      .thenComparing((MethodNode methodNode) -> methodNode.tryCatchBlocks, asmComparators.iterableComparator(TryCatchBlockNode.class))
                                      .thenComparing((MethodNode methodNode) -> methodNode.maxLocals, INTEGER_COMPARATOR)
                                      .thenComparing((MethodNode methodNode) -> methodNode.maxStack, INTEGER_COMPARATOR)
                                      .thenComparing((MethodNode methodNode) -> methodNode.localVariables, asmComparators.iterableComparator(LocalVariableNode.class))
                                      .thenComparing((MethodNode methodNode) -> methodNode.visibleLocalVariableAnnotations, asmComparators.iterableComparator(LocalVariableAnnotationNode.class))
                                      .thenComparing((MethodNode methodNode) -> methodNode.invisibleLocalVariableAnnotations, asmComparators.iterableComparator(LocalVariableAnnotationNode.class))
                                      .thenComparing((MethodNode methodNode) -> AnnotationDefaultNode.createOrNull(methodNode.annotationDefault), asmComparators.elementComparator(AnnotationDefaultNode.class))
                                      .compare(first, second);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private MethodNode filterLineNumbers(MethodNode methodNode) {
    if (ignoreLineNumbers) {
      return InsnListUtils.copyWithFilteredLineNumbers(methodNode);
    }
    else {
      return methodNode;
    }
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
