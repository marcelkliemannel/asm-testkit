package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.asmutils.InsnListUtils;
import dev.turingcomplete.asmtestkit.assertion.DefaultLabelIndexLookup;
import dev.turingcomplete.asmtestkit.assertion.LabelIndexLookup;
import dev.turingcomplete.asmtestkit.assertion.comparator._internal.IterableComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator._internal.WithLabelNamesIterableAsmComparator;
import dev.turingcomplete.asmtestkit.node.AccessFlags;
import dev.turingcomplete.asmtestkit.node.AnnotationDefault;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.LocalVariableAnnotationNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ParameterNode;
import org.objectweb.asm.tree.TryCatchBlockNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Comparator;
import java.util.function.Function;

import static dev.turingcomplete.asmtestkit.asmutils.MethodNodeUtils.extractLabelIndices;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getFromObjectElseNull;
import static dev.turingcomplete.asmtestkit.assertion.comparator._internal.ComparatorUtils.INTEGER_COMPARATOR;
import static dev.turingcomplete.asmtestkit.assertion.comparator._internal.ComparatorUtils.STRING_COMPARATOR;
import static org.assertj.core.util.Lists.newArrayList;

/**
 * A comparison function to order {@link MethodNode}s.
 *
 * <p>Two {@code ParameterNode}s will be considered as equal if all the
 * {@code public} {@link MethodNode} fields are equal. Otherwise, they will
 * be ordered by the comparison of the first non-matching field.
 */
public class MethodNodeComparator extends AbstractWithLabelNamesAsmComparator<MethodNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link MethodNodeComparator} instance.
   */
  public static final MethodNodeComparator INSTANCE = create();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link MethodNode}s.
   */
  public static final Comparator<Iterable<? extends MethodNode>> ITERABLE_INSTANCE = WithLabelNamesIterableAsmComparator.create(INSTANCE);

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
    extractLabelIndices(first, second).forEach(labelIndexLookup::putIfUnknown);

    return WithLabelNamesAsmComparator.comparing((MethodNode methodNode) -> methodNode.name, STRING_COMPARATOR, labelIndexLookup)
                                      .thenComparing((MethodNode methodNode) -> getFromObjectElseNull(methodNode.desc, Type::getMethodType), asmComparators.elementComparator(Type.class))
                                      .thenComparing((MethodNode methodNode) -> AccessFlags.forField(methodNode.access), asmComparators.elementComparator(AccessFlags.class))
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
                                      .thenComparing(gerInstructions(), asmComparators.iterableComparator(AbstractInsnNode.class))
                                      .thenComparing((MethodNode methodNode) -> methodNode.tryCatchBlocks, asmComparators.iterableComparator(TryCatchBlockNode.class))
                                      .thenComparing((MethodNode methodNode) -> methodNode.maxLocals, INTEGER_COMPARATOR)
                                      .thenComparing((MethodNode methodNode) -> methodNode.maxStack, INTEGER_COMPARATOR)
                                      .thenComparing((MethodNode methodNode) -> methodNode.localVariables, asmComparators.iterableComparator(LocalVariableNode.class))
                                      .thenComparing((MethodNode methodNode) -> methodNode.visibleLocalVariableAnnotations, asmComparators.iterableComparator(LocalVariableAnnotationNode.class))
                                      .thenComparing((MethodNode methodNode) -> methodNode.invisibleLocalVariableAnnotations, asmComparators.iterableComparator(LocalVariableAnnotationNode.class))
                                      .thenComparing((MethodNode methodNode) -> AnnotationDefault.createOrNull(methodNode.annotationDefault), asmComparators.elementComparator(AnnotationDefault.class))
                                      .compare(first, second);
  }

  private Function<MethodNode, InsnList> gerInstructions() {
    return methodNode -> {
      if (ignoreLineNumbers) {
        return InsnListUtils.filterLineNumbers2(methodNode);
      }
      else {
        return methodNode.instructions;
      }
    };
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
