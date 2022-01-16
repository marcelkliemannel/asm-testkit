package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.asmutils.AccessKind;
import dev.turingcomplete.asmtestkit.asmutils.InsnListUtils;
import dev.turingcomplete.asmtestkit.assertion.comparator.AnnotationNodeComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.AttributeComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.FieldNodeComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.InsnListComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.LabelNodeComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.LocalVariableAnnotationNodeComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.LocalVariableNodeComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.TypeAnnotationNodeComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.TypeComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.TypePathComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.TypeReferenceComparator;
import dev.turingcomplete.asmtestkit.assertion.representation.AnnotationNodeRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.AttributeRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.FieldNodeRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.InsnListRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.LabelNodeRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.LocalVariableAnnotationNodeRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.LocalVariableNodeRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.TypeAnnotationNodeRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.TypePathRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.TypeReferenceRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.TypeRepresentation;
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
import org.objectweb.asm.tree.LocalVariableAnnotationNode;
import org.objectweb.asm.tree.LocalVariableNode;
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
   * @param actual a {@link TypeAnnotationNode}; may be null.
   * @return a new {@link TypeAnnotationNodeAssert}; never null.
   */
  public static TypeAnnotationNodeAssert assertThat(TypeAnnotationNode actual) {
    return new TypeAnnotationNodeAssert(actual);
  }

  /**
   * Creates an {@link LocalVariableAnnotationNodeAssert}.
   *
   * @param actual a {@link LocalVariableAnnotationNode}; may be null.
   * @return a new {@link LocalVariableAnnotationNodeAssert}; never null.
   */
  public static LocalVariableAnnotationNodeAssert assertThat(LocalVariableAnnotationNode actual) {
    return new LocalVariableAnnotationNodeAssert(actual);
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
  public static AsmIterableAssert<?, AbstractInsnNode, InstructionAssert> assertThat(InsnList actual) {
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

  /**
   * Creates a {@link LocalVariableNodeAssert}.
   *
   * @param actual a {@link LocalVariableNode}; may be null.
   * @return a new {@link LocalVariableNodeAssert}; never null.
   */
  public static LocalVariableNodeAssert assertThat(LocalVariableNode actual) {
    return new LocalVariableNodeAssert(actual);
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
  public static AsmIterableAssert<?, Attribute, AttributeAssert> assertThatAttributes(Iterable<Attribute> actual) {
    return new AsmIterableAssert<>(actual, AsmAssertions::assertThat)
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
  public static WithLabelNamesIterableAssert<?, AnnotationNode, AnnotationNodeAssert> assertThatAnnotations(Iterable<AnnotationNode> actual) {
    return new WithLabelNamesIterableAssert<>(actual, AsmAssertions::assertThat)
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
  public static WithLabelNamesIterableAssert<?, TypeAnnotationNode, TypeAnnotationNodeAssert> assertThatTypeAnnotations(Iterable<TypeAnnotationNode> actual) {
    return new WithLabelNamesIterableAssert<>(actual, AsmAssertions::assertThat)
            .as("Type Annotations")
            .withRepresentation(TypeAnnotationNodeRepresentation.INSTANCE)
            .usingElementComparator(TypeAnnotationNodeComparator.INSTANCE)
            .usingComparator(TypeAnnotationNodeComparator.ITERABLE_INSTANCE);
  }

  /**
   * Creates an {@link IterableAssert} for {@link LocalVariableAnnotationNode}s which
   * uses {@link LocalVariableAnnotationNodeRepresentation#INSTANCE} for the
   * representation and for equality {@link LocalVariableAnnotationNodeComparator#INSTANCE}
   * and {@link LocalVariableAnnotationNodeComparator#ITERABLE_INSTANCE}.
   *
   * <p>To override the representation or comparator call
   * {@link IterableAssert#usingComparator(Comparator)} or
   * {@link IterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link LocalVariableAnnotationNode}s;
   *               may be null.
   * @return a new {@link IterableAssert}; never null.
   */
  public static WithLabelNamesIterableAssert<?, LocalVariableAnnotationNode, LocalVariableAnnotationNodeAssert> assertThatLocalVariableAnnotations(Iterable<LocalVariableAnnotationNode> actual) {
    return new WithLabelNamesIterableAssert<>(actual, AsmAssertions::assertThat)
            .as("Local Variable Annotations")
            .withRepresentation(LocalVariableAnnotationNodeRepresentation.INSTANCE)
            .usingElementComparator(LocalVariableAnnotationNodeComparator.INSTANCE)
            .usingComparator(LocalVariableAnnotationNodeComparator.ITERABLE_INSTANCE);
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
  public static AsmIterableAssert<?, TypePath, TypePathAssert> assertThatTypePaths(Iterable<TypePath> actual) {
    return new AsmIterableAssert<>(actual, AsmAssertions::assertThat)
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
  public static AsmIterableAssert<?, Type, TypeAssert> assertThatTypes(Iterable<Type> actual) {
    return new AsmIterableAssert<>(actual, AsmAssertions::assertThat)
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
  public static AsmIterableAssert<?, TypeReference, TypeReferenceAssert> assertThatTypeReferences(Iterable<TypeReference> actual) {
    return new AsmIterableAssert<>(actual, AsmAssertions::assertThat)
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
  public static AsmIterableAssert<?, AbstractInsnNode, InstructionAssert> assertThatInstructions(Iterable<AbstractInsnNode> actual) {
    return new AsmIterableAssert<>(InsnListUtils.toInsnList(actual), AsmAssertions::assertThat)
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
  public static AsmIterableAssert<?, AbstractInsnNode, InstructionAssert> assertThatInstructionsIgnoreLineNumbers(Iterable<AbstractInsnNode> actual) {
    return new AsmIterableAssert<>(InsnListUtils.toInsnList(actual), AsmAssertions::assertThat)
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
  public static AsmIterableAssert<?, FieldNode, FieldNodeAssert> assertThatFields(Iterable<FieldNode> actual) {
    return new AsmIterableAssert<>(actual, AsmAssertions::assertThat)
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
  public static WithLabelNamesIterableAssert<?, LabelNode, LabelNodeAssert> assertThatLabels(Iterable<LabelNode> actual) {
    return new WithLabelNamesIterableAssert<>(actual, AsmAssertions::assertThat)
            .as("Labels")
            .withRepresentation(LabelNodeRepresentation.INSTANCE)
            .usingElementComparator(LabelNodeComparator.INSTANCE)
            .usingComparator(LabelNodeComparator.ITERABLE_INSTANCE);
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
  public static WithLabelNamesIterableAssert<?, LocalVariableNode, LocalVariableNodeAssert> assertThatLocalVariables(Iterable<LocalVariableNode> actual) {
    return new WithLabelNamesIterableAssert<>(actual, AsmAssertions::assertThat)
            .as("Local Variables")
            .withRepresentation(LocalVariableNodeRepresentation.INSTANCE)
            .usingElementComparator(LocalVariableNodeComparator.INSTANCE)
            .usingComparator(LocalVariableNodeComparator.ITERABLE_INSTANCE);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
