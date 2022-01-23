package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.asmutils.AccessKind;
import dev.turingcomplete.asmtestkit.assertion.LabelNameLookup;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.presentation.Representation;
import org.assertj.core.presentation.StandardRepresentation;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.TypeReference;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.FieldNode;
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
 * Combines various {@link SingleAsmRepresentation} into one {@link Representation}.
 *
 * <p>This {@code Representation} can be set via
 * {@link AbstractAssert#setCustomRepresentation(Representation)} to all AssertJ
 * assertions in order to get a proper representation of ASM objects.
 *
 * <p>Note that {@link AccessRepresentation} is not part of this representation
 * because it is not clear which {@link AccessKind} to use.
 */
public final class DefaultAsmRepresentations extends StandardRepresentation implements AsmRepresentations {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link DefaultAsmRepresentations} instance.
   */
  public static final DefaultAsmRepresentations INSTANCE = create();

  private static final Map<Class<?>, Supplier<SingleAsmRepresentation>> SINGLE_ASM_REPRESENTATIONS = new HashMap<>();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  static {
    registerSingleAsmRepresentation(Attribute.class, () -> AttributeRepresentation.INSTANCE);
    registerSingleAsmRepresentation(AnnotationNode.class, () -> AnnotationNodeRepresentation.INSTANCE);
    registerSingleAsmRepresentation(TypeAnnotationNode.class, () -> TypeAnnotationNodeRepresentation.INSTANCE);
    registerSingleAsmRepresentation(LocalVariableAnnotationNode.class, () -> LocalVariableAnnotationNodeRepresentation.INSTANCE);
    registerSingleAsmRepresentation(TypePath.class, () -> TypePathRepresentation.INSTANCE);
    registerSingleAsmRepresentation(Type.class, () -> TypeRepresentation.INSTANCE);
    registerSingleAsmRepresentation(TypeReference.class, () -> TypeReferenceRepresentation.INSTANCE);
    registerSingleAsmRepresentation(FieldNode.class, () -> FieldNodeRepresentation.INSTANCE);
    registerSingleAsmRepresentation(AbstractInsnNode.class, () -> InstructionRepresentation.INSTANCE);
    registerSingleAsmRepresentation(LabelNode.class, () -> LabelNodeRepresentation.INSTANCE);
    registerSingleAsmRepresentation(InsnList.class, () -> InsnListRepresentation.INSTANCE);
    registerSingleAsmRepresentation(MethodNode.class, () -> MethodNodeRepresentation.INSTANCE);
    registerSingleAsmRepresentation(LocalVariableNode.class, () -> LocalVariableNodeRepresentation.INSTANCE);
    registerSingleAsmRepresentation(ParameterNode.class, () -> ParameterNodeRepresentation.INSTANCE);
    registerSingleAsmRepresentation(TryCatchBlockNode.class, () -> TryCatchBlockNodeRepresentation.INSTANCE);

    // An 'InsnList' is an 'Iterable' and would be handled in the 'toStringOf'
    registerFormatterForType(InsnList.class, insnList -> SINGLE_ASM_REPRESENTATIONS.get(InsnList.class).get().toStringOf(insnList));
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
   * Registers a custom {@link SingleAsmRepresentation} which gets lazily
   * initialized.
   *
   * <p>If there is already a registered representation for the
   * {@code objectClass}, it will be replaced.
   *
   * @param objectClass             the {@link Class} the given
   *                                {@link SingleAsmRepresentation} is supposed
   *                                to handle; never null.
   * @param singleAsmRepresentation a {@link Supplier} which provides a custom
   *                                {@link SingleAsmRepresentation}; never null.
   */
  public static void registerSingleAsmRepresentation(Class<?> objectClass, Supplier<SingleAsmRepresentation> singleAsmRepresentation) {
    SINGLE_ASM_REPRESENTATIONS.put(Objects.requireNonNull(objectClass),
                                   Objects.requireNonNull(singleAsmRepresentation));
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
  public String toStringOf(int access, AccessKind accessKind) {
    return AccessRepresentation.instance(Objects.requireNonNull(accessKind)).toStringOf(access);
  }

  @Override
  public String toStringOf(Object object, LabelNameLookup labelNameLookup) {
    Objects.requireNonNull(labelNameLookup);

    if (object == null) {
      return null;
    }

    return findSingleAsmRepresentation(object)
            .map(singleAsmRepresentation -> {
              if (singleAsmRepresentation instanceof WithLabelNamesAsmRepresentation) {
                return ((WithLabelNamesAsmRepresentation) singleAsmRepresentation).toStringOf(object, labelNameLookup);
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
  public String toSimplifiedStringOf(Object object, LabelNameLookup labelNameLookup) {
    Objects.requireNonNull(labelNameLookup);

    if (object == null) {
      return null;
    }

    return findSingleAsmRepresentation(object)
            .map(singleAsmRepresentation -> {
              if (singleAsmRepresentation instanceof WithLabelNamesAsmRepresentation) {
                return ((WithLabelNamesAsmRepresentation) singleAsmRepresentation).toSimplifiedStringOf(object, labelNameLookup);
              }
              else {
                return singleAsmRepresentation.toSimplifiedStringOf(object);
              }
            })
            .orElseGet(() -> toStringOf(object));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private Optional<SingleAsmRepresentation> findSingleAsmRepresentation(Object object) {
    assert object != null;

    // Fast path
    if (SINGLE_ASM_REPRESENTATIONS.containsKey(object.getClass())) {
      return Optional.of(SINGLE_ASM_REPRESENTATIONS.get(object.getClass()).get());
    }

    for (Map.Entry<Class<?>, Supplier<SingleAsmRepresentation>> entry : SINGLE_ASM_REPRESENTATIONS.entrySet()) {
      if (entry.getKey().isAssignableFrom(object.getClass())) {
        return Optional.of(entry.getValue().get());
      }
    }

    return Optional.empty();
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
