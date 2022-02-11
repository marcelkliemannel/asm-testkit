package dev.turingcomplete.asmtestkit.comparator;

import dev.turingcomplete.asmtestkit.asmutils.InsnListUtils;
import dev.turingcomplete.asmtestkit.asmutils.MethodNodeUtils;
import dev.turingcomplete.asmtestkit.asmutils.TypeUtils;
import dev.turingcomplete.asmtestkit.assertion.LabelIndexLookup;
import dev.turingcomplete.asmtestkit.common.IgnoreLineNumbersCapable;
import dev.turingcomplete.asmtestkit.comparator._internal.ComparatorUtils;
import dev.turingcomplete.asmtestkit.comparator._internal.IterableComparator;
import dev.turingcomplete.asmtestkit.node.AccessNode;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InnerClassNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A comparison function to order {@link ClassNode}s.
 *
 * <p>Two {@code ClassNode}s will be considered as equal if all the
 * {@code public} {@link ClassNode} fields are equal. Otherwise, they will
 * be ordered by the comparison of the first non-matching field.
 */
public class ClassNodeComparator
        extends AbstractWithLabelIndexAsmComparator<ClassNode>
        implements IgnoreLineNumbersCapable<ClassNodeComparator> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link ClassNodeComparator} instance.
   */
  public static final ClassNodeComparator INSTANCE = create();

  /**
   * A reusable {@link ClassNodeComparator} instance, which excludes line numbers
   * from the comparison.
   */
  public static final ClassNodeComparator INSTANCE_IGNORE_LINE_NUMBERS = create().ignoreLineNumbers();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link ClassNode}s.
   */
  public static final Comparator<Iterable<? extends ClassNode>> ITERABLE_INSTANCE = new IterableComparator<>(INSTANCE);

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link ClassNode}s, which excludes line numbers from the comparison.
   */
  public static final Comparator<Iterable<? extends ClassNode>> ITERABLE_INSTANCE_IGNORE_LINE_NUMBERS = new IterableComparator<>(INSTANCE_IGNORE_LINE_NUMBERS);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private boolean ignoreLineNumbers = false;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected ClassNodeComparator() {
    super(ClassNodeComparator.class, ClassNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link ClassNodeComparator} instance.
   *
   * @return a new {@link ClassNodeComparator}; never null;
   */
  public static ClassNodeComparator create() {
    return new ClassNodeComparator();
  }

  /**
   * Exclude {@link LineNumberNode}s (and its associated {@link LabelNode}s)
   * from the comparison.
   *
   * @return {@code this} {@link ClassNodeComparator}; never null.
   */
  @Override
  public ClassNodeComparator ignoreLineNumbers() {
    this.ignoreLineNumbers = true;

    return this;
  }

  @Override
  protected int doCompare(ClassNode first, ClassNode second, LabelIndexLookup labelIndexLookup) {
    var classNodeComparator = Comparator.comparing((ClassNode classNode) -> classNode.version, ComparatorUtils.INTEGER_COMPARATOR)
                                        .thenComparing((ClassNode classNode) -> AccessNode.forClass(classNode.access), asmComparators.elementComparator(AccessNode.class))
                                        .thenComparing((ClassNode classNode) -> TypeUtils.nameToTypeElseNull(classNode.name), asmComparators.elementComparator(Type.class))
                                        .thenComparing((ClassNode classNode) -> classNode.signature, ComparatorUtils.STRING_COMPARATOR)
                                        .thenComparing((ClassNode classNode) -> TypeUtils.nameToTypeElseNull(classNode.superName), asmComparators.elementComparator(Type.class))
                                        .thenComparing((ClassNode classNode) -> TypeUtils.namesToTypes(classNode.interfaces), asmComparators.iterableComparator(Type.class))
                                        .thenComparing((ClassNode classNode) -> classNode.sourceFile, ComparatorUtils.STRING_COMPARATOR)
                                        .thenComparing((ClassNode classNode) -> classNode.sourceDebug, ComparatorUtils.STRING_COMPARATOR)
                                        .thenComparing((ClassNode classNode) -> TypeUtils.nameToTypeElseNull(classNode.outerClass), asmComparators.elementComparator(Type.class))
                                        .thenComparing((ClassNode classNode) -> classNode.outerMethod, ComparatorUtils.STRING_COMPARATOR)
                                        .thenComparing((ClassNode classNode) -> classNode.outerMethodDesc, ComparatorUtils.STRING_COMPARATOR)
                                        .thenComparing((ClassNode classNode) -> classNode.visibleAnnotations, asmComparators.iterableComparator(AnnotationNode.class))
                                        .thenComparing((ClassNode classNode) -> classNode.invisibleAnnotations, asmComparators.iterableComparator(AnnotationNode.class))
                                        .thenComparing((ClassNode classNode) -> classNode.visibleTypeAnnotations, asmComparators.iterableComparator(TypeAnnotationNode.class))
                                        .thenComparing((ClassNode classNode) -> classNode.invisibleTypeAnnotations, asmComparators.iterableComparator(TypeAnnotationNode.class))
                                        .thenComparing((ClassNode classNode) -> classNode.attrs, asmComparators.iterableComparator(Attribute.class))
                                        .thenComparing((ClassNode classNode) -> classNode.innerClasses, asmComparators.iterableComparator(InnerClassNode.class))
                                        .thenComparing((ClassNode classNode) -> TypeUtils.nameToTypeElseNull(classNode.nestHostClass), asmComparators.elementComparator(Type.class))
                                        .thenComparing((ClassNode classNode) -> TypeUtils.namesToTypes(classNode.nestMembers), asmComparators.iterableComparator(Type.class))
                                        .thenComparing((ClassNode classNode) -> TypeUtils.namesToTypes(classNode.permittedSubclasses), asmComparators.iterableComparator(Type.class))
                                        .thenComparing((ClassNode classNode) -> classNode.fields, asmComparators.iterableComparator(FieldNode.class));

    Comparator<? super Iterable<? extends MethodNode>> methodNodeComparator = asmComparators.iterableComparator(MethodNode.class);
    if (ignoreLineNumbers) {
      classNodeComparator.thenComparing(new MethodNodesComparatorWithFilteredLineNumbers(labelIndexLookup, methodNodeComparator));
    }
    else {
      classNodeComparator.thenComparing((ClassNode classNode) -> classNode.methods, methodNodeComparator);
    }

    return classNodeComparator.compare(first, second);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  private static class MethodNodesComparatorWithFilteredLineNumbers implements Comparator<ClassNode> {

    private final LabelIndexLookup                                   labelIndexLookup;
    private final Comparator<? super Iterable<? extends MethodNode>> methodNodeComparator;

    MethodNodesComparatorWithFilteredLineNumbers(LabelIndexLookup labelIndexLookup,
                                                 Comparator<? super Iterable<? extends MethodNode>> methodNodeComparator) {
      this.labelIndexLookup = labelIndexLookup;
      this.methodNodeComparator = methodNodeComparator;
    }

    @Override
    public int compare(ClassNode first, ClassNode second) {
      List<MethodNode> firstPrepared = first.methods.stream().map(getMethodNodeMethodNodeFunction()).collect(Collectors.toList());
      List<MethodNode> secondPrepared = second.methods.stream().map(getMethodNodeMethodNodeFunction()).collect(Collectors.toList());
      return methodNodeComparator.compare(firstPrepared, secondPrepared);
    }

    private Function<MethodNode, MethodNode> getMethodNodeMethodNodeFunction() {
      return methodNode -> {
        // Filter line numbers
        MethodNode methodNodePrepared = InsnListUtils.copyWithFilteredLineNumbers(methodNode);

        // Collect label indices
        // Because of the line number filtering, we may have to overwrite existing
        // indices here as they may have changed.
        labelIndexLookup.putAll(MethodNodeUtils.extractLabelIndices(methodNodePrepared));
        return methodNodePrepared;
      };
    }
  }
}
