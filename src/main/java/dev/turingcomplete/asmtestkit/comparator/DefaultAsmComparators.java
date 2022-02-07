package dev.turingcomplete.asmtestkit.comparator;

import dev.turingcomplete.asmtestkit.node.AccessFlags;
import dev.turingcomplete.asmtestkit.node.AnnotationDefault;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.TypeReference;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InnerClassNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LocalVariableAnnotationNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ParameterNode;
import org.objectweb.asm.tree.TryCatchBlockNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public final class DefaultAsmComparators implements AsmComparators {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link DefaultAsmComparators} instance.
   */
  public static final DefaultAsmComparators INSTANCE = create();

  private static final Map<Class<?>, Container<?>> ASM_COMPARATORS = new HashMap<>();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  static {
    registerAsmComparator(Attribute.class, () -> AttributeComparator.INSTANCE, () -> AttributeComparator.ITERABLE_INSTANCE);
    registerAsmComparator(AnnotationNode.class, () -> AnnotationNodeComparator.INSTANCE, () -> AnnotationNodeComparator.ITERABLE_INSTANCE);
    registerAsmComparator(TypeAnnotationNode.class, () -> TypeAnnotationNodeComparator.INSTANCE, () -> TypeAnnotationNodeComparator.ITERABLE_INSTANCE);
    registerAsmComparator(LocalVariableAnnotationNode.class, () -> LocalVariableAnnotationNodeComparator.INSTANCE, () -> LocalVariableAnnotationNodeComparator.ITERABLE_INSTANCE);
    registerAsmComparator(TypePath.class, () -> TypePathComparator.INSTANCE, () -> TypePathComparator.ITERABLE_INSTANCE);
    registerAsmComparator(Type.class, () -> TypeComparator.INSTANCE, () -> TypeComparator.ITERABLE_INSTANCE);
    registerAsmComparator(TypeReference.class, () -> TypeReferenceComparator.INSTANCE, () -> TypeReferenceComparator.ITERABLE_INSTANCE);
    registerAsmComparator(FieldNode.class, () -> FieldNodeComparator.INSTANCE, () -> FieldNodeComparator.ITERABLE_INSTANCE);
    registerAsmComparator(AbstractInsnNode.class, () -> InstructionComparator.INSTANCE, () -> InsnListComparator.INSTANCE);
    registerAsmComparator(LabelNode.class, () -> LabelNodeComparator.INSTANCE, () -> LabelNodeComparator.ITERABLE_INSTANCE);
    registerAsmComparator(MethodNode.class, () -> MethodNodeComparator.INSTANCE, () -> MethodNodeComparator.ITERABLE_INSTANCE);
    registerAsmComparator(LocalVariableNode.class, () -> LocalVariableNodeComparator.INSTANCE, () -> LocalVariableNodeComparator.ITERABLE_INSTANCE);
    registerAsmComparator(ParameterNode.class, () -> ParameterNodeComparator.INSTANCE, () -> ParameterNodeComparator.ITERABLE_INSTANCE);
    registerAsmComparator(TryCatchBlockNode.class, () -> TryCatchBlockNodeComparator.INSTANCE, () -> TryCatchBlockNodeComparator.ITERABLE_INSTANCE);
    registerAsmComparator(AccessFlags.class, () -> AccessFlagsComparator.INSTANCE, () -> AccessFlagsComparator.ITERABLE_INSTANCE);
    registerAsmComparator(AnnotationDefault.class, () -> AnnotationDefaultValueComparator.INSTANCE, () -> AnnotationDefaultValueComparator.ITERABLE_INSTANCE);
    registerAsmComparator(MethodNode.class, () -> MethodNodeComparator.INSTANCE, () -> MethodNodeComparator.ITERABLE_INSTANCE);
    registerAsmComparator(InnerClassNode.class, () -> InnerClassNodeComparator.INSTANCE, () -> InnerClassNodeComparator.ITERABLE_INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link DefaultAsmComparators} instance.
   *
   * @return a new {@link DefaultAsmComparators}; never null;
   */
  public static DefaultAsmComparators create() {
    return new DefaultAsmComparators();
  }

  public <T> Comparator<T> elementComparator(Class<T> elementClass) {
    Objects.requireNonNull(elementClass);

    if (!ASM_COMPARATORS.containsKey(elementClass)) {
      throw new IllegalArgumentException("No element comparator for: " + elementClass.getName());
    }

    //noinspection unchecked
    return (Comparator<T>) ASM_COMPARATORS.get(elementClass).elementComparator();
  }

  @Override
  public <T> Comparator<? extends Iterable<T>> iterableComparator(Class<T> elementClass) {
    Objects.requireNonNull(elementClass);

    if (!ASM_COMPARATORS.containsKey(elementClass)) {
      throw new IllegalArgumentException("No iterable comparator for: " + elementClass.getName());
    }

    //noinspection unchecked
    return (Comparator<? extends Iterable<T>>) ASM_COMPARATORS.get(elementClass).iterableComparator();
  }

  public static <T> void registerAsmComparator(Class<? extends T> elementClass,
                                               Supplier<Comparator<? extends T>> comparator,
                                               Supplier<Comparator<Iterable<? extends T>>> iterableComparator) {

    Objects.requireNonNull(elementClass);
    Objects.requireNonNull(comparator);
    Objects.requireNonNull(iterableComparator);

    ASM_COMPARATORS.put(elementClass, new Container<>(comparator, iterableComparator));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  private static class Container<T> {

    private       Comparator<? extends T>           elementComparator;
    private final Supplier<Comparator<? extends T>> elementComparatorSupplier;

    private       Comparator<Iterable<? extends T>>           iterableComparator;
    private final Supplier<Comparator<Iterable<? extends T>>> iterableComparatorSupplier;

    Container(Supplier<Comparator<? extends T>> elementComparatorSupplier,
              Supplier<Comparator<Iterable<? extends T>>> iterableComparatorSupplier) {

      this.elementComparatorSupplier = elementComparatorSupplier;
      this.iterableComparatorSupplier = iterableComparatorSupplier;
    }

    public Comparator<? extends T> elementComparator() {
      if (elementComparator == null) {
        elementComparator = Objects.requireNonNull(elementComparatorSupplier.get());
      }

      return elementComparator;
    }

    public Comparator<? extends Iterable<? extends T>> iterableComparator() {
      if (iterableComparator == null) {
        iterableComparator = Objects.requireNonNull(iterableComparatorSupplier.get());
      }

      return iterableComparator;
    }
  }
}
