package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.asmutils.AccessKind;
import dev.turingcomplete.asmtestkit.asmutils.InsnListUtils;
import dev.turingcomplete.asmtestkit.assertion.comparator.AnnotationNodeComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.AttributeComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.FieldNodeComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.InsnListComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.LabelNodeComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.TypeAnnotationNodeComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.TypeComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.TypePathComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.TypeReferenceComparator;
import dev.turingcomplete.asmtestkit.assertion.representation.AnnotationNodeRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.AttributeRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.FieldNodeRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.InsnListRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.LabelNodeRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.TypeAnnotationNodeRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.TypePathRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.TypeReferenceRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.TypeRepresentation;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.IterableAssert;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.TypeReference;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Comparator;
import java.util.Objects;

public final class AsmAssertions {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  private AsmAssertions() {
    throw new UnsupportedOperationException();
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  // --- Access

  /**
   * Creates an {@link AccessAssert} for class accesses.
   *
   * @param access an {@link Integer} of class access flags; may be null.
   * @return a new {@link AccessAssert}; never null.
   */
  public static AccessAssert assertThatClassAccess(Integer access) {
    return new AccessAssert(access, AccessKind.CLASS);
  }

  /**
   * Creates an {@link AccessAssert} for field accesses.
   *
   * @param access an {@link Integer} of field access flags; may be null.
   * @return a new {@link AccessAssert}; never null.
   */
  public static AccessAssert assertThatFieldAccess(Integer access) {
    return new AccessAssert(access, AccessKind.FIELD);
  }

  /**
   * Creates an {@link AccessAssert} for method accesses.
   *
   * @param access an {@link Integer} of method access flags; may be null.
   * @return a new {@link AccessAssert}; never null.
   */
  public static AccessAssert assertThatMethodAccess(Integer access) {
    return new AccessAssert(access, AccessKind.METHOD);
  }

  /**
   * Creates an {@link AccessAssert} for parameter accesses.
   *
   * @param access an {@link Integer} of parameter access flags; may be null.
   * @return a new {@link AccessAssert}; never null.
   */
  public static AccessAssert assertThatParameterAccess(Integer access) {
    return new AccessAssert(access, AccessKind.PARAMETER);
  }

  /**
   * Creates an {@link AccessAssert} for module accesses.
   *
   * @param access an {@link Integer} of module access flags; may be null.
   * @return a new {@link AccessAssert}; never null.
   */
  public static AccessAssert assertThatModuleAccess(Integer access) {
    return new AccessAssert(access, AccessKind.MODULE);
  }

  /**
   * Creates an {@link AccessAssert} for module requires accesses.
   *
   * @param access an {@link Integer} of module requires access flags; may be
   *               null.
   * @return a new {@link AccessAssert}; never null.
   */
  public static AccessAssert assertThatModuleRequiresAccess(Integer access) {
    return new AccessAssert(access, AccessKind.MODULE_REQUIRES);
  }

  /**
   * Creates an {@link AccessAssert} for module exports accesses.
   *
   * @param access an {@link Integer} of module exports access flags; may be
   *               null.
   * @return a new {@link AccessAssert}; never null.
   */
  public static AccessAssert assertThatModuleExportsAccess(Integer access) {
    return new AccessAssert(access, AccessKind.MODULE_EXPORTS);
  }

  /**
   * Creates an {@link AccessAssert} for module opens accesses.
   *
   * @param access an {@link Integer} of module opens access flags; may be null.
   * @return a new {@link AccessAssert}; never null.
   */
  public static AccessAssert assertThatModuleOpensAccess(Integer access) {
    return new AccessAssert(access, AccessKind.MODULE_OPENS);
  }

  /**
   * Creates an {@link AccessAssert} for the given {@code accessKind}.
   *
   * @param access     an {@link Integer} of class access flags; may be null.
   * @param accessKind the {@link AccessKind} the {@code access} parameter;
   *                   not null.
   * @return a new {@link AccessAssert}; never null.
   */
  public static AccessAssert assertThatAccess(Integer access, AccessKind accessKind) {
    return new AccessAssert(access, Objects.requireNonNull(accessKind));
  }

  // ---- ASM Objects ------------------------------------------------------- //

  /**
   * Creates an {@link AttributeAssert}.
   *
   * @param actual an {@link Attribute}; may be null.
   * @return a new {@link AttributeAssert}; never null.
   */
  public static AttributeAssert assertThat(Attribute actual) {
    return new AttributeAssert(actual);
  }

  /**
   * Creates an {@link AnnotationNodeAssert}.
   *
   * @param actual an {@link AnnotationNode}; may be null.
   * @return a new {@link AnnotationNodeAssert}; never null.
   */
  public static AnnotationNodeAssert assertThat(AnnotationNode actual) {
    return new AnnotationNodeAssert(actual);
  }

  /**
   * Creates an {@link TypeAnnotationNodeAssert}.
   *
   * @param actual an {@link TypeAnnotationNode}; may be null.
   * @return a new {@link TypeAnnotationNodeAssert}; never null.
   */
  public static TypeAnnotationNodeAssert assertThat(TypeAnnotationNode actual) {
    return new TypeAnnotationNodeAssert(actual);
  }

  /**
   * Creates a {@link TypePathAssert}.
   *
   * @param actual a {@link TypePath}; may be null.
   * @return a new {@link TypePathAssert}; never null.
   */
  public static TypePathAssert assertThat(TypePath actual) {
    return new TypePathAssert(actual);
  }

  /**
   * Creates a {@link TypeAssert}.
   *
   * @param actual a {@link Type}; may be null.
   * @return a new {@link TypeAssert}; never null.
   */
  public static TypeAssert assertThat(Type actual) {
    return new TypeAssert(actual);
  }

  /**
   * Creates a {@link FieldNodeAssert}.
   *
   * @param actual a {@link FieldNode}; may be null.
   * @return a new {@link FieldNodeAssert}; never null.
   */
  public static FieldNodeAssert assertThat(FieldNode actual) {
    return new FieldNodeAssert(actual);
  }

  /**
   * Creates a {@link TypeReferenceAssert}.
   *
   * @param actual a {@link TypeReference}; may be null.
   * @return a new {@link TypeReferenceAssert}; never null.
   */
  public static TypeReferenceAssert assertThat(TypeReference actual) {
    return new TypeReferenceAssert(actual);
  }

  /**
   * Creates a {@link InstructionAssert}.
   *
   * @param actual a {@link AbstractInsnNode}; may be null.
   * @return a new {@link InstructionAssert}; never null.
   */
  public static InstructionAssert assertThat(AbstractInsnNode actual) {
    return new InstructionAssert(actual);
  }

  /**
   * Creates an {@link IterableAssert} for {@link AbstractInsnNode}s which uses
   * {@link InsnListRepresentation#INSTANCE} for the representation and for
   * equality {@link InsnListComparator#INSTANCE}.
   *
   * <p>To exclude {@link LineNumberNode}s from the comparison use
   * {@link #assertThatInstructionsIgnoreLineNumbers(Iterable)}.
   *
   * <p>To override the representation or comparator call
   * {@link IterableAssert#usingComparator(Comparator)} or
   * {@link IterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link InsnList}; may be null.
   * @return a new {@link IterableAssert}; never null.
   * @see #assertThatInstructions(Iterable)
   * @see #assertThatInstructionsIgnoreLineNumbers(Iterable)
   */
  public static IterableAssert<AbstractInsnNode> assertThat(InsnList actual) {
    return assertThatInstructions(actual);
  }

  /**
   * Creates a {@link LabelNodeAssert}.
   *
   * @param actual a {@link LabelNode}; may be null.
   * @return a new {@link LabelNodeAssert}; never null.
   */
  public static LabelNodeAssert assertThat(LabelNode actual) {
    return new LabelNodeAssert(actual);
  }

  // ---- Iterable ---------------------------------------------------------- //

  /**
   * Creates an {@link IterableAssert} for {@link Attribute}s which uses
   * {@link AttributeRepresentation#INSTANCE} for the representation and
   * for equality {@link AttributeComparator#INSTANCE} and
   * {@link AttributeComparator#ITERABLE_INSTANCE}.
   *
   * <p>To override the representation or comparator call
   * {@link IterableAssert#usingComparator(Comparator)} or
   * {@link IterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link Attribute}s; may be null.
   * @return a new {@link IterableAssert}; never null.
   */
  public static IterableAssert<Attribute> assertThatAttributes(Iterable<Attribute> actual) {
    return Assertions.assertThat(actual)
                     .as("Attributes")
                     .withRepresentation(AttributeRepresentation.INSTANCE)
                     .usingElementComparator(AttributeComparator.INSTANCE)
                     .usingComparator(AttributeComparator.ITERABLE_INSTANCE);
  }

  /**
   * Creates an {@link IterableAssert} for {@link AnnotationNode}s which uses
   * {@link AnnotationNodeRepresentation#INSTANCE} for the representation and
   * for equality {@link AnnotationNodeComparator#INSTANCE} and
   * {@link AnnotationNodeComparator#ITERABLE_INSTANCE}.
   *
   * <p>To override the representation or comparator call
   * {@link IterableAssert#usingComparator(Comparator)} or
   * {@link IterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link AnnotationNode}s; may
   *               be null.
   * @return a new {@link IterableAssert}; never null.
   */
  public static IterableAssert<AnnotationNode> assertThatAnnotationNodes(Iterable<AnnotationNode> actual) {
    return Assertions.assertThat(actual)
                     .as("Annotations")
                     .withRepresentation(AnnotationNodeRepresentation.INSTANCE)
                     .usingElementComparator(AnnotationNodeComparator.INSTANCE)
                     .usingComparator(AnnotationNodeComparator.ITERABLE_INSTANCE);
  }

  /**
   * Creates an {@link IterableAssert} for {@link TypeAnnotationNode}s which
   * uses {@link TypeAnnotationNodeRepresentation#INSTANCE} for the representation
   * and for equality {@link TypeAnnotationNodeComparator#INSTANCE} and
   * {@link TypeAnnotationNodeComparator#ITERABLE_INSTANCE}.
   *
   * <p>To override the representation or comparator call
   * {@link IterableAssert#usingComparator(Comparator)} or
   * {@link IterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link TypeAnnotationNode}s;
   *               may be null.
   * @return a new {@link IterableAssert}; never null.
   */
  public static IterableAssert<TypeAnnotationNode> assertThatTypeAnnotationNodes(Iterable<TypeAnnotationNode> actual) {
    return Assertions.assertThat(actual)
                     .as("Type Annotations")
                     .withRepresentation(TypeAnnotationNodeRepresentation.INSTANCE)
                     .usingElementComparator(TypeAnnotationNodeComparator.INSTANCE)
                     .usingComparator(TypeAnnotationNodeComparator.ITERABLE_INSTANCE);
  }

  /**
   * Creates an {@link IterableAssert} for {@link TypePath}s which uses
   * {@link TypePathRepresentation#INSTANCE} for the representation and for
   * equality {@link TypePathComparator#INSTANCE} and
   * {@link TypePathComparator#ITERABLE_INSTANCE}.
   *
   * <p>To override the representation or comparator call
   * {@link IterableAssert#usingComparator(Comparator)} or
   * {@link IterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link TypePath}s; may be null.
   * @return a new {@link IterableAssert}; never null.
   */
  public static IterableAssert<TypePath> assertThatTypePaths(Iterable<TypePath> actual) {
    return Assertions.assertThat(actual)
                     .as("Type Paths")
                     .withRepresentation(TypePathRepresentation.INSTANCE)
                     .usingElementComparator(TypePathComparator.INSTANCE)
                     .usingComparator(TypePathComparator.ITERABLE_INSTANCE);
  }

  /**
   * Creates an {@link IterableAssert} for {@link Type}s which uses
   * {@link TypeRepresentation#INSTANCE} for the representation and for
   * equality {@link TypeComparator#INSTANCE} and
   * {@link TypeComparator#ITERABLE_INSTANCE}.
   *
   * <p>To override the representation or comparator call
   * {@link IterableAssert#usingComparator(Comparator)} or
   * {@link IterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link Type}s; may be null.
   * @return a new {@link IterableAssert}; never null.
   */
  public static IterableAssert<Type> assertThatTypes(Iterable<Type> actual) {
    return Assertions.assertThat(actual)
                     .as("Types")
                     .withRepresentation(TypeRepresentation.INSTANCE)
                     .usingElementComparator(TypeComparator.INSTANCE)
                     .usingComparator(TypeComparator.ITERABLE_INSTANCE);
  }

  /**
   * Creates an {@link IterableAssert} for {@link TypeReference}s which uses
   * {@link TypeReferenceRepresentation#INSTANCE} for the representation and for
   * equality {@link TypeReferenceComparator#INSTANCE} and
   * {@link TypeReferenceComparator#ITERABLE_INSTANCE}.
   *
   * <p>To override the representation or comparator call
   * {@link IterableAssert#usingComparator(Comparator)} or
   * {@link IterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link TypeReference}s;
   *               may be null.
   * @return a new {@link IterableAssert}; never null.
   */
  public static IterableAssert<TypeReference> assertThatTypeReferences(Iterable<TypeReference> actual) {
    return Assertions.assertThat(actual)
                     .as("Type References")
                     .withRepresentation(TypeReferenceRepresentation.INSTANCE)
                     .usingElementComparator(TypeReferenceComparator.INSTANCE)
                     .usingComparator(TypeReferenceComparator.ITERABLE_INSTANCE);
  }

  /**
   * Creates an {@link IterableAssert} for {@link AbstractInsnNode}s which uses
   * {@link InsnListRepresentation#INSTANCE} for the representation and for
   * equality {@link InsnListComparator#INSTANCE}.
   *
   * <p>To exclude {@link LineNumberNode}s from the comparison use
   * {@link #assertThatInstructionsIgnoreLineNumbers(Iterable)}.
   *
   * <p>To override the representation or comparator call
   * {@link IterableAssert#usingComparator(Comparator)} or
   * {@link IterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link AbstractInsnNode}s;
   *               may be null.
   * @return a new {@link IterableAssert}; never null.
   * @see #assertThat(InsnList)
   * @see #assertThatInstructionsIgnoreLineNumbers(Iterable)
   */
  public static IterableAssert<AbstractInsnNode> assertThatInstructions(Iterable<AbstractInsnNode> actual) {
    return Assertions.assertThat(InsnListUtils.toInsnList(actual))
                     .as("Instructions")
                     .withRepresentation(InsnListRepresentation.INSTANCE)
                     .usingComparator(InsnListComparator.INSTANCE);
  }

  /**
   * Creates an {@link IterableAssert} for {@link AbstractInsnNode}s which uses
   * {@link InsnListRepresentation#INSTANCE} for the representation and for
   * equality {@link InsnListComparator#INSTANCE_IGNORE_LINE_NUMBERS}. Any
   * {@link LineNumberNode}s will be excluded form the comparison.
   *
   * <p>To override the representation or comparator call
   * {@link IterableAssert#usingComparator(Comparator)} or
   * {@link IterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link AbstractInsnNode}s;
   *               may be null.
   * @return a new {@link IterableAssert}; never null.
   */
  public static IterableAssert<AbstractInsnNode> assertThatInstructionsIgnoreLineNumbers(Iterable<AbstractInsnNode> actual) {
    return Assertions.assertThat(InsnListUtils.toInsnList(actual))
                     .as("Instructions - ignore line numbers")
                     .withRepresentation(InsnListRepresentation.INSTANCE)
                     .usingComparator(InsnListComparator.INSTANCE_IGNORE_LINE_NUMBERS);
  }

  /**
   * Creates an {@link IterableAssert} for {@link FieldNode}s which
   * uses {@link FieldNodeRepresentation#INSTANCE} for the representation
   * and for equality {@link FieldNodeComparator#INSTANCE} and
   * {@link FieldNodeComparator#ITERABLE_INSTANCE}.
   *
   * <p>To override the representation or comparator call
   * {@link IterableAssert#usingComparator(Comparator)} or
   * {@link IterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link LabelNode}s; may be null.
   * @return a new {@link IterableAssert}; never null.
   */
  public static IterableAssert<FieldNode> asserThatFields(Iterable<FieldNode> actual) {
    return Assertions.assertThat(actual)
                     .as("Labels")
                     .withRepresentation(FieldNodeRepresentation.INSTANCE)
                     .usingElementComparator(FieldNodeComparator.INSTANCE)
                     .usingComparator(FieldNodeComparator.ITERABLE_INSTANCE);
  }

  /**
   * Creates an {@link IterableAssert} for {@link LabelNode}s which
   * uses {@link TypeAnnotationNodeRepresentation#INSTANCE} for the representation
   * and for equality {@link LabelNodeComparator#INSTANCE} and
   * {@link LabelNodeComparator#ITERABLE_INSTANCE}.
   *
   * <p>To override the representation or comparator call
   * {@link IterableAssert#usingComparator(Comparator)} or
   * {@link IterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link LabelNode}s; may be null.
   * @return a new {@link IterableAssert}; never null.
   */
  public static IterableAssert<LabelNode> assertThatLabels(Iterable<LabelNode> actual) {
    return Assertions.assertThat(actual)
                     .as("Labels")
                     .withRepresentation(LabelNodeRepresentation.INSTANCE)
                     .usingElementComparator(LabelNodeComparator.INSTANCE)
                     .usingComparator(LabelNodeComparator.ITERABLE_INSTANCE);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
