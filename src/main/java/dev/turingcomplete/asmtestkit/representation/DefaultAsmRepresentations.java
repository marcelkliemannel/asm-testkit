package dev.turingcomplete.asmtestkit.representation;

import dev.turingcomplete.asmtestkit.node.AccessNode;
import dev.turingcomplete.asmtestkit.asmutils.AccessKind;
import dev.turingcomplete.asmtestkit.common.LabelIndexLookup;
import dev.turingcomplete.asmtestkit.node.AnnotationDefaultNode;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.presentation.Representation;
import org.assertj.core.presentation.StandardRepresentation;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.TypeReference;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InnerClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LocalVariableAnnotationNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ParameterNode;
import org.objectweb.asm.tree.TryCatchBlockNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import static java.lang.Integer.toHexString;

/**
 * Combines various {@link AsmRepresentation} into one {@link Representation}.
 *
 * <p>This {@code Representation} can be set via
 * {@link AbstractAssert#setCustomRepresentation(Representation)} to all AssertJ
 * assertions in order to get a proper representation of ASM objects.
 *
 * <p>Note that {@link AccessNodeRepresentation} is not part of this representation
 * because it is not clear which {@link AccessKind} to use.
 */
public final class DefaultAsmRepresentations extends StandardRepresentation implements AsmRepresentations {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link DefaultAsmRepresentations} instance.
   */
  public static final DefaultAsmRepresentations INSTANCE = create();

  private static final Map<Class<?>, Container<?>> ASM_REPRESENTATIONS = new HashMap<>();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  static {
    registerAsmRepresentation(Attribute.class, () -> AttributeRepresentation.INSTANCE);
    registerAsmRepresentation(AnnotationNode.class, () -> AnnotationNodeRepresentation.INSTANCE);
    registerAsmRepresentation(TypeAnnotationNode.class, () -> TypeAnnotationNodeRepresentation.INSTANCE);
    registerAsmRepresentation(LocalVariableAnnotationNode.class, () -> LocalVariableAnnotationNodeRepresentation.INSTANCE);
    registerAsmRepresentation(TypePath.class, () -> TypePathRepresentation.INSTANCE);
    registerAsmRepresentation(Type.class, () -> TypeRepresentation.INSTANCE);
    registerAsmRepresentation(TypeReference.class, () -> TypeReferenceRepresentation.INSTANCE);
    registerAsmRepresentation(FieldNode.class, () -> FieldNodeRepresentation.INSTANCE);
    registerAsmRepresentation(AbstractInsnNode.class, () -> InstructionRepresentation.INSTANCE);
    registerAsmRepresentation(LabelNode.class, () -> LabelNodeRepresentation.INSTANCE);
    registerAsmRepresentation(InsnList.class, () -> InsnListRepresentation.INSTANCE);
    registerAsmRepresentation(MethodNode.class, () -> MethodNodeRepresentation.INSTANCE);
    registerAsmRepresentation(LocalVariableNode.class, () -> LocalVariableNodeRepresentation.INSTANCE);
    registerAsmRepresentation(ParameterNode.class, () -> ParameterNodeRepresentation.INSTANCE);
    registerAsmRepresentation(TryCatchBlockNode.class, () -> TryCatchBlockNodeRepresentation.INSTANCE);
    registerAsmRepresentation(AccessNode.class, () -> AccessNodeRepresentation.INSTANCE);
    registerAsmRepresentation(AnnotationDefaultNode.class, () -> AnnotationDefaultValueRepresentation.INSTANCE);
    registerAsmRepresentation(InnerClassNode.class, () -> InnerClassNodeRepresentation.INSTANCE);
    registerAsmRepresentation(ClassNode.class, () -> ClassNodeRepresentation.INSTANCE);

    // An 'InsnList' is an 'Iterable' and would be handled in the 'toStringOf'
    registerFormatterForType(InsnList.class, insnList -> ASM_REPRESENTATIONS.get(InsnList.class).get().toStringOf(insnList));
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link DefaultAsmRepresentations} instance.
   *
   * @return a new {@link DefaultAsmRepresentations}; never null;
   */
  public static DefaultAsmRepresentations create() {
    return new DefaultAsmRepresentations();
  }

  /**
   * Registers a custom {@link AsmRepresentation} which gets lazily
   * initialized.
   *
   * <p>If there is already a registered representation for the
   * {@code objectClass}, it will be replaced.
   *
   * @param objectClass       the {@link Class} the given {@link AsmRepresentation}
   *                          is supposed to handle; never null.
   * @param asmRepresentation a {@link Supplier} which provides a custom
   *                          {@link AsmRepresentation}; never null.
   */
  public static <T> void registerAsmRepresentation(Class<T> objectClass, Supplier<AsmRepresentation<T>> asmRepresentation) {
    ASM_REPRESENTATIONS.put(Objects.requireNonNull(objectClass),
                            new Container<>(Objects.requireNonNull(asmRepresentation)));
  }

  @Override
  public <T> AsmRepresentation<T> getAsmRepresentation(Class<T> elementClass) {
    if (!ASM_REPRESENTATIONS.containsKey(elementClass)) {
      throw new IllegalArgumentException("No representation for: " + elementClass.getName());
    }

    //noinspection unchecked
    return (AsmRepresentation<T>) ASM_REPRESENTATIONS.get(elementClass).get();
  }

  // ---- toStringOf -------------------------------------------------------- //

  @Override
  protected String fallbackToStringOf(Object object) {
    return findSingleAsmRepresentation(object)
            .map(singleAsmRepresentation -> singleAsmRepresentation.toStringOf(object))
            .orElseGet(() -> super.fallbackToStringOf(object));
  }

  @Override
  public String unambiguousToStringOf(Object object) {
    return toStringOf(object) + " (" + toHexString(System.identityHashCode(object)) + ")";
  }

  @Override
  public String toStringOf(Object object, LabelIndexLookup labelIndexLookup) {
    Objects.requireNonNull(labelIndexLookup);

    if (object == null) {
      return null;
    }

    return findSingleAsmRepresentation(object)
            .map(singleAsmRepresentation -> {
              if (singleAsmRepresentation instanceof WithLabelIndexAsmRepresentation) {
                return ((WithLabelIndexAsmRepresentation) singleAsmRepresentation).toStringOf(object, labelIndexLookup);
              }
              else {
                return singleAsmRepresentation.toStringOf(object);
              }
            })
            .orElseGet(() -> toStringOf(object));
  }

  // ---- toSimplifiedStringOf ---------------------------------------------- //

  @Override
  public String toSimplifiedStringOf(Object object) {
    if (object == null) {
      return null;
    }

    return findSingleAsmRepresentation(object)
            .map(singleAsmRepresentation -> singleAsmRepresentation.toSimplifiedStringOf(object))
            .orElseGet(() -> toStringOf(object));
  }

  @Override
  public String toSimplifiedStringOf(Object object, LabelIndexLookup labelIndexLookup) {
    Objects.requireNonNull(labelIndexLookup);

    if (object == null) {
      return null;
    }

    return findSingleAsmRepresentation(object)
            .map(singleAsmRepresentation -> {
              if (singleAsmRepresentation instanceof WithLabelIndexAsmRepresentation) {
                return ((WithLabelIndexAsmRepresentation) singleAsmRepresentation).toSimplifiedStringOf(object, labelIndexLookup);
              }
              else {
                return singleAsmRepresentation.toSimplifiedStringOf(object);
              }
            })
            .orElseGet(() -> toStringOf(object));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private Optional<? extends AsmRepresentation<?>> findSingleAsmRepresentation(Object object) {
    assert object != null;

    // Fast path
    if (ASM_REPRESENTATIONS.containsKey(object.getClass())) {
      return Optional.of(ASM_REPRESENTATIONS.get(object.getClass()).get());
    }

    // Check for subtypes
    return ASM_REPRESENTATIONS.entrySet().stream()
                              .filter(entry -> entry.getKey().isAssignableFrom(object.getClass()))
                              .map(entry -> entry.getValue().get())
                              .findFirst();
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  private static class Container<T> {

    AsmRepresentation<T>           asmRepresentation;
    Supplier<AsmRepresentation<T>> asmRepresentationSupplier;

    Container(Supplier<AsmRepresentation<T>> asmRepresentationSupplier) {
      this.asmRepresentationSupplier = asmRepresentationSupplier;
    }

    AsmRepresentation<?> get() {
      if (asmRepresentation == null) {
        asmRepresentation = Objects.requireNonNull(asmRepresentationSupplier.get());
      }

      return asmRepresentation;
    }
  }
}
