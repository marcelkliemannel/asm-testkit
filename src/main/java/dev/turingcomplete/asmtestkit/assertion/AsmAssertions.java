package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.asmutils.InsnListUtils;
import dev.turingcomplete.asmtestkit.assertion.comparator.*;
import dev.turingcomplete.asmtestkit.assertion.representation.*;
import dev.turingcomplete.asmtestkit.node.AccessFlags;
import dev.turingcomplete.asmtestkit.node.AnnotationDefault;
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
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ParameterNode;
import org.objectweb.asm.tree.TryCatchBlockNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Comparator;

public final class AsmAssertions {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  private AsmAssertions() {
    throw new UnsupportedOperationException();
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  // ---- ASM Objects ------------------------------------------------------- //

  /**
   * Creates an {@link AccessFlagsAssert}.
   *
   * @param actual an {@link AccessFlags}; may be null.
   * @return a new {@link AccessFlagsAssert}; never null.
   */
  public static AccessFlagsAssert assertThat(AccessFlags actual) {
    return new AccessFlagsAssert(actual);
  }

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
   * Creates an {@link AsmIterableAssert} for {@link AbstractInsnNode}s which uses
   * {@link InsnListRepresentation#INSTANCE} for the representation and for
   * equality {@link InsnListComparator#INSTANCE}.
   *
   * <p>To exclude {@link LineNumberNode}s from the comparison use
   * {@link #assertThatInstructionsIgnoreLineNumbers(Iterable)}.
   *
   * <p>To override the representation or comparator call
   * {@link AsmIterableAssert#usingComparator(Comparator)} or
   * {@link AsmIterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link InsnList}; may be null.
   * @return a new {@link AsmIterableAssert}; never null.
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

  /**
   * Creates a {@link TryCatchBlockNodeAssert}.
   *
   * @param actual a {@link TryCatchBlockNode}; may be null.
   * @return a new {@link TryCatchBlockNodeAssert}; never null.
   */
  public static TryCatchBlockNodeAssert assertThat(TryCatchBlockNode actual) {
    return new TryCatchBlockNodeAssert(actual);
  }

  /**
   * Creates a {@link ParameterNodeAssert}.
   *
   * @param actual a {@link ParameterNode}; may be null.
   * @return a new {@link ParameterNodeAssert}; never null.
   */
  public static ParameterNodeAssert assertThat(ParameterNode actual) {
    return new ParameterNodeAssert(actual);
  }

  /**
   * Creates an {@link AnnotationDefaultValueAssert}.
   *
   * @param actual an {@link AnnotationDefault}; may be null.
   * @return a new {@link AnnotationDefaultValueAssert}; never null.
   */
  public static AnnotationDefaultValueAssert assertThat(AnnotationDefault actual) {
    return new AnnotationDefaultValueAssert(actual);
  }

  /**
   * Creates an {@link MethodNodeAssert}.
   *
   * @param actual an {@link MethodNode}; may be null.
   * @return a new {@link MethodNodeAssert}; never null.
   */
  public static MethodNodeAssert assertThat(MethodNode actual) {
    return new MethodNodeAssert(actual);
  }

  // ---- Iterable ---------------------------------------------------------- //

  /**
   * Creates an {@link AsmIterableAssert} for {@link Attribute}s which uses
   * {@link AttributeRepresentation#INSTANCE} for the representation and
   * for equality {@link AttributeComparator#INSTANCE} and
   * {@link AttributeComparator#ITERABLE_INSTANCE}.
   *
   * <p>The returned {@code AsmIterableAssert} should be used in conjunction with
   * {@link AsmIterableAssert#containsExactlyInAnyOrderElementsOf}.
   *
   * <p>To override the representation or comparator call
   * {@link AsmIterableAssert#usingComparator(Comparator)} or
   * {@link AsmIterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link Attribute}s; may be null.
   * @return a new {@link AsmIterableAssert}; never null.
   */
  public static AsmIterableAssert<?, Attribute, AttributeAssert> assertThatAttributes(Iterable<Attribute> actual) {
    return new AsmIterableAssert<>(actual, AsmAssertions::assertThat)
            .as("Attributes")
            .withRepresentation(AttributeRepresentation.INSTANCE)
            .usingElementComparator(AttributeComparator.INSTANCE)
            .usingComparator(AttributeComparator.ITERABLE_INSTANCE);
  }

  /**
   * Creates an {@link AsmIterableAssert} for {@link AnnotationNode}s which uses
   * {@link AnnotationNodeRepresentation#INSTANCE} for the representation and
   * for equality {@link AnnotationNodeComparator#INSTANCE} and
   * {@link AnnotationNodeComparator#ITERABLE_INSTANCE}.
   *
   * <p>The returned {@code AsmIterableAssert} should be used in conjunction with
   * {@link AsmIterableAssert#containsExactlyInAnyOrderElementsOf}.
   *
   * <p>To override the representation or comparator call
   * {@link AsmIterableAssert#usingComparator(Comparator)} or
   * {@link AsmIterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link AnnotationNode}s; may
   *               be null.
   * @return a new {@link AsmIterableAssert}; never null.
   */
  public static AsmIterableAssert<?, AnnotationNode, AnnotationNodeAssert> assertThatAnnotations(Iterable<AnnotationNode> actual) {
    return new AsmIterableAssert<>(actual, AsmAssertions::assertThat)
            .as("Annotations")
            .withRepresentation(AnnotationNodeRepresentation.INSTANCE)
            .usingElementComparator(AnnotationNodeComparator.INSTANCE)
            .usingComparator(AnnotationNodeComparator.ITERABLE_INSTANCE);
  }

  /**
   * Creates an {@link AsmIterableAssert} for {@link TypeAnnotationNode}s which
   * uses {@link TypeAnnotationNodeRepresentation#INSTANCE} for the representation
   * and for equality {@link TypeAnnotationNodeComparator#INSTANCE} and
   * {@link TypeAnnotationNodeComparator#ITERABLE_INSTANCE}.
   *
   * <p>The returned {@code AsmIterableAssert} should be used in conjunction with
   * {@link AsmIterableAssert#containsExactlyInAnyOrderElementsOf}.
   *
   * <p>To override the representation or comparator call
   * {@link AsmIterableAssert#usingComparator(Comparator)} or
   * {@link AsmIterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link TypeAnnotationNode}s;
   *               may be null.
   * @return a new {@link AsmIterableAssert}; never null.
   */
  public static AsmIterableAssert<?, TypeAnnotationNode, TypeAnnotationNodeAssert> assertThatTypeAnnotations(Iterable<TypeAnnotationNode> actual) {
    return new AsmIterableAssert<>(actual, AsmAssertions::assertThat)
            .as("Type Annotations")
            .withRepresentation(TypeAnnotationNodeRepresentation.INSTANCE)
            .usingElementComparator(TypeAnnotationNodeComparator.INSTANCE)
            .usingComparator(TypeAnnotationNodeComparator.ITERABLE_INSTANCE);
  }

  /**
   * Creates an {@link AsmIterableAssert} for {@link LocalVariableAnnotationNode}s which
   * uses {@link LocalVariableAnnotationNodeRepresentation#INSTANCE} for the
   * representation and for equality {@link LocalVariableAnnotationNodeComparator#INSTANCE}
   * and {@link LocalVariableAnnotationNodeComparator#ITERABLE_INSTANCE}.
   *
   * <p>The returned {@code AsmIterableAssert} should be used in conjunction with
   * {@link AsmIterableAssert#containsExactlyInAnyOrderElementsOf}.
   *
   * <p>To override the representation or comparator call
   * {@link AsmIterableAssert#usingComparator(Comparator)} or
   * {@link AsmIterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link LocalVariableAnnotationNode}s;
   *               may be null.
   * @return a new {@link AsmIterableAssert}; never null.
   */
  public static AsmIterableAssert<?, LocalVariableAnnotationNode, LocalVariableAnnotationNodeAssert> assertThatLocalVariableAnnotations(Iterable<LocalVariableAnnotationNode> actual) {
    return new AsmIterableAssert<>(actual, AsmAssertions::assertThat)
            .as("Local Variable Annotations")
            .withRepresentation(LocalVariableAnnotationNodeRepresentation.INSTANCE)
            .usingElementComparator(LocalVariableAnnotationNodeComparator.INSTANCE)
            .usingComparator(LocalVariableAnnotationNodeComparator.ITERABLE_INSTANCE);
  }

  /**
   * Creates an {@link AsmIterableAssert} for {@link TypePath}s which uses
   * {@link TypePathRepresentation#INSTANCE} for the representation and for
   * equality {@link TypePathComparator#INSTANCE} and
   * {@link TypePathComparator#ITERABLE_INSTANCE}.
   *
   * <p>The returned {@code AsmIterableAssert} should be used in conjunction with
   * {@link AsmIterableAssert#containsExactlyInAnyOrderElementsOf}.
   *
   * <p>To override the representation or comparator call
   * {@link AsmIterableAssert#usingComparator(Comparator)} or
   * {@link AsmIterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link TypePath}s; may be null.
   * @return a new {@link AsmIterableAssert}; never null.
   */
  public static AsmIterableAssert<?, TypePath, TypePathAssert> assertThatTypePaths(Iterable<TypePath> actual) {
    return new AsmIterableAssert<>(actual, AsmAssertions::assertThat)
            .as("Type Paths")
            .withRepresentation(TypePathRepresentation.INSTANCE)
            .usingElementComparator(TypePathComparator.INSTANCE)
            .usingComparator(TypePathComparator.ITERABLE_INSTANCE);
  }

  /**
   * Creates an {@link AsmIterableAssert} for {@link Type}s which uses
   * {@link TypeRepresentation#INSTANCE} for the representation and for
   * equality {@link TypeComparator#INSTANCE} and
   * {@link TypeComparator#ITERABLE_INSTANCE}.
   *
   * <p>The returned {@code AsmIterableAssert} should be used in conjunction with
   * {@link AsmIterableAssert#containsExactlyInAnyOrderElementsOf}.
   *
   * <p>To override the representation or comparator call
   * {@link AsmIterableAssert#usingComparator(Comparator)} or
   * {@link AsmIterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link Type}s; may be null.
   * @return a new {@link AsmIterableAssert}; never null.
   */
  public static AsmIterableAssert<?, Type, TypeAssert> assertThatTypes(Iterable<Type> actual) {
    return new AsmIterableAssert<>(actual, AsmAssertions::assertThat)
            .as("Types")
            .withRepresentation(TypeRepresentation.INSTANCE)
            .usingElementComparator(TypeComparator.INSTANCE)
            .usingComparator(TypeComparator.ITERABLE_INSTANCE);
  }

  /**
   * Creates an {@link AsmIterableAssert} for {@link TypeReference}s which uses
   * {@link TypeReferenceRepresentation#INSTANCE} for the representation and for
   * equality {@link TypeReferenceComparator#INSTANCE} and
   * {@link TypeReferenceComparator#ITERABLE_INSTANCE}.
   *
   * <p>The returned {@code AsmIterableAssert} should be used in conjunction with
   * {@link AsmIterableAssert#containsExactlyInAnyOrderElementsOf}.
   *
   * <p>To override the representation or comparator call
   * {@link AsmIterableAssert#usingComparator(Comparator)} or
   * {@link AsmIterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link TypeReference}s;
   *               may be null.
   * @return a new {@link AsmIterableAssert}; never null.
   */
  public static AsmIterableAssert<?, TypeReference, TypeReferenceAssert> assertThatTypeReferences(Iterable<TypeReference> actual) {
    return new AsmIterableAssert<>(actual, AsmAssertions::assertThat)
            .as("Type References")
            .withRepresentation(TypeReferenceRepresentation.INSTANCE)
            .usingElementComparator(TypeReferenceComparator.INSTANCE)
            .usingComparator(TypeReferenceComparator.ITERABLE_INSTANCE);
  }

  /**
   * Creates an {@link AsmIterableAssert} for {@link AbstractInsnNode}s which uses
   * {@link InsnListRepresentation#INSTANCE} for the representation and for
   * equality {@link InsnListComparator#INSTANCE}.
   *
   * <p>To exclude {@link LineNumberNode}s from the comparison use
   * {@link #assertThatInstructionsIgnoreLineNumbers(Iterable)}.
   *
   * <p>The returned {@code AsmIterableAssert} should be used in conjunction with
   * {@link AsmIterableAssert#isEqualTo(Object)}.
   *
   * <p>To override the representation or comparator call
   * {@link AsmIterableAssert#usingComparator(Comparator)} or
   * {@link AsmIterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link AbstractInsnNode}s;
   *               may be null.
   * @return a new {@link AsmIterableAssert}; never null.
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
   * Creates an {@link AsmIterableAssert} for {@link AbstractInsnNode}s which uses
   * {@link InsnListRepresentation#INSTANCE} for the representation and for
   * equality {@link InsnListComparator#INSTANCE_IGNORE_LINE_NUMBERS}. Any
   * {@link LineNumberNode}s will be excluded form the comparison.
   *
   * <p>The returned {@code AsmIterableAssert} should be used in conjunction with
   * {@link AsmIterableAssert#isEqualTo(Object)}.
   *
   * <p>To override the representation or comparator call
   * {@link AsmIterableAssert#usingComparator(Comparator)} or
   * {@link AsmIterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link AbstractInsnNode}s;
   *               may be null.
   * @return a new {@link AsmIterableAssert}; never null.
   */
  public static AsmIterableAssert<?, AbstractInsnNode, InstructionAssert> assertThatInstructionsIgnoreLineNumbers(Iterable<AbstractInsnNode> actual) {
    return new AsmIterableAssert<>(InsnListUtils.toInsnList(actual), AsmAssertions::assertThat)
            .as("Instructions - ignore line numbers")
            .withRepresentation(InsnListRepresentation.INSTANCE)
            .usingComparator(InsnListComparator.INSTANCE_IGNORE_LINE_NUMBERS);
  }

  /**
   * Creates an {@link AsmIterableAssert} for {@link FieldNode}s which
   * uses {@link FieldNodeRepresentation#INSTANCE} for the representation
   * and for equality {@link FieldNodeComparator#INSTANCE} and
   * {@link FieldNodeComparator#ITERABLE_INSTANCE}.
   *
   * <p>The returned {@code AsmIterableAssert} should be used in conjunction with
   * {@link AsmIterableAssert#containsExactlyInAnyOrderElementsOf}.
   *
   * <p>To override the representation or comparator call
   * {@link AsmIterableAssert#usingComparator(Comparator)} or
   * {@link AsmIterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link LabelNode}s; may be null.
   * @return a new {@link AsmIterableAssert}; never null.
   */
  public static AsmIterableAssert<?, FieldNode, FieldNodeAssert> assertThatFields(Iterable<FieldNode> actual) {
    return new AsmIterableAssert<>(actual, AsmAssertions::assertThat)
            .as("Labels")
            .withRepresentation(FieldNodeRepresentation.INSTANCE)
            .usingElementComparator(FieldNodeComparator.INSTANCE)
            .usingComparator(FieldNodeComparator.ITERABLE_INSTANCE);
  }

  /**
   * Creates an {@link AsmIterableAssert} for {@link LabelNode}s which
   * uses {@link TypeAnnotationNodeRepresentation#INSTANCE} for the representation
   * and for equality {@link LabelNodeComparator#INSTANCE} and
   * {@link LabelNodeComparator#ITERABLE_INSTANCE}.
   *
   * <p>The returned {@code AsmIterableAssert} should be used in conjunction with
   * {@link AsmIterableAssert#containsExactlyInAnyOrderElementsOf}.
   *
   * <p>To override the representation or comparator call
   * {@link AsmIterableAssert#usingComparator(Comparator)} or
   * {@link AsmIterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link LabelNode}s; may be null.
   * @return a new {@link AsmIterableAssert}; never null.
   */
  public static AsmIterableAssert<?, LabelNode, LabelNodeAssert> assertThatLabels(Iterable<LabelNode> actual) {
    return new AsmIterableAssert<>(actual, AsmAssertions::assertThat)
            .as("Labels")
            .withRepresentation(LabelNodeRepresentation.INSTANCE)
            .usingElementComparator(LabelNodeComparator.INSTANCE)
            .usingComparator(LabelNodeComparator.ITERABLE_INSTANCE);
  }

  /**
   * Creates an {@link AsmIterableAssert} for {@link LabelNode}s which
   * uses {@link TypeAnnotationNodeRepresentation#INSTANCE} for the representation
   * and for equality {@link LabelNodeComparator#INSTANCE} and
   * {@link LabelNodeComparator#ITERABLE_INSTANCE}.
   *
   * <p>The returned {@code AsmIterableAssert} should be used in conjunction with
   * {@link AsmIterableAssert#containsExactlyInAnyOrderElementsOf}.
   *
   * <p>To override the representation or comparator call
   * {@link AsmIterableAssert#usingComparator(Comparator)} or
   * {@link AsmIterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link LabelNode}s; may be null.
   * @return a new {@link AsmIterableAssert}; never null.
   */
  public static AsmIterableAssert<?, LocalVariableNode, LocalVariableNodeAssert> assertThatLocalVariables(Iterable<LocalVariableNode> actual) {
    return new AsmIterableAssert<>(actual, AsmAssertions::assertThat)
            .as("Local Variables")
            .withRepresentation(LocalVariableNodeRepresentation.INSTANCE)
            .usingElementComparator(LocalVariableNodeComparator.INSTANCE)
            .usingComparator(LocalVariableNodeComparator.ITERABLE_INSTANCE);
  }

  /**
   * Creates an {@link AsmIterableAssert} for {@link TryCatchBlockNode}s which
   * uses {@link TryCatchBlockNodeRepresentation#INSTANCE} for the representation
   * and for equality {@link TryCatchBlockNodeComparator#INSTANCE} and
   * {@link TryCatchBlockNodeComparator#ITERABLE_INSTANCE}.
   *
   * <p>The returned {@code AsmIterableAssert} should be used in conjunction with
   * {@link AsmIterableAssert#containsExactlyInAnyOrderElementsOf}.
   *
   * <p>To override the representation or comparator call
   * {@link AsmIterableAssert#usingComparator(Comparator)} or
   * {@link AsmIterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link TryCatchBlockNode}s; may be null.
   * @return a new {@link AsmIterableAssert}; never null.
   */
  public static AsmIterableAssert<?, TryCatchBlockNode, TryCatchBlockNodeAssert> assertThatTryCatchBlocks(Iterable<TryCatchBlockNode> actual) {
    return new AsmIterableAssert<>(actual, AsmAssertions::assertThat)
            .as("Try Catch Blocks")
            .withRepresentation(TryCatchBlockNodeRepresentation.INSTANCE)
            .usingElementComparator(TryCatchBlockNodeComparator.INSTANCE)
            .usingComparator(TryCatchBlockNodeComparator.ITERABLE_INSTANCE);
  }

  /**
   * Creates an {@link AsmIterableAssert} for {@link ParameterNode}s which
   * uses {@link ParameterNodeRepresentation#INSTANCE} for the representation
   * and for equality {@link ParameterNodeComparator#INSTANCE} and
   * {@link ParameterNodeComparator#ITERABLE_INSTANCE}.
   *
   * <p>The returned {@code AsmIterableAssert} should be used in conjunction with
   * {@link AsmIterableAssert#containsExactlyInAnyOrderElementsOf}.
   *
   * <p>To override the representation or comparator call
   * {@link AsmIterableAssert#usingComparator(Comparator)} or
   * {@link AsmIterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link ParameterNode}s; may be null.
   * @return a new {@link AsmIterableAssert}; never null.
   */
  public static AsmIterableAssert<?, ParameterNode, ParameterNodeAssert> assertThatParameters(Iterable<ParameterNode> actual) {
    return new AsmIterableAssert<>(actual, AsmAssertions::assertThat)
            .as("Parameters")
            .withRepresentation(ParameterNodeRepresentation.INSTANCE)
            .usingElementComparator(ParameterNodeComparator.INSTANCE)
            .usingComparator(ParameterNodeComparator.ITERABLE_INSTANCE);
  }

  /**
   * Creates an {@link AsmIterableAssert} for {@link AnnotationDefault}s
   * which uses {@link AnnotationDefaultValueRepresentation#INSTANCE} for the
   * representation and for equality {@link AnnotationDefaultValueComparator#INSTANCE}
   * and {@link AnnotationDefaultValueComparator#ITERABLE_INSTANCE}.
   *
   * <p>The returned {@code AsmIterableAssert} should be used in conjunction with
   * {@link AsmIterableAssert#containsExactlyInAnyOrderElementsOf}.
   *
   * <p>To override the representation or comparator call
   * {@link AsmIterableAssert#usingComparator(Comparator)} or
   * {@link AsmIterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link AnnotationDefault}s;
   *               may be null.
   * @return a new {@link AsmIterableAssert}; never null.
   */
  public static AsmIterableAssert<?, AnnotationDefault, AnnotationDefaultValueAssert> assertThatAnnotationDefaulls(Iterable<AnnotationDefault> actual) {
    return new AsmIterableAssert<>(actual, AsmAssertions::assertThat)
            .as("Annotation Defaults")
            .withRepresentation(AnnotationDefaultValueRepresentation.INSTANCE)
            .usingElementComparator(AnnotationDefaultValueComparator.INSTANCE)
            .usingComparator(AnnotationDefaultValueComparator.ITERABLE_INSTANCE);
  }

  /**
   * Creates an {@link AsmIterableAssert} for {@link AccessFlags}s which
   * uses {@link AccessFlagsRepresentation#INSTANCE} for the representation
   * and for equality {@link AccessFlagsComparator#INSTANCE} and
   * {@link AccessFlagsComparator#ITERABLE_INSTANCE}.
   *
   * <p>The returned {@code AsmIterableAssert} should be used in conjunction with
   * {@link AsmIterableAssert#containsExactlyInAnyOrderElementsOf}.
   *
   * <p>To override the representation or comparator call
   * {@link AsmIterableAssert#usingComparator(Comparator)} or
   * {@link AsmIterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link AccessFlags}s; may be null.
   * @return a new {@link AsmIterableAssert}; never null.
   */
  public static AsmIterableAssert<?, AccessFlags, AccessFlagsAssert> assertThatAccessFlags(Iterable<AccessFlags> actual) {
    return new AsmIterableAssert<>(actual, AsmAssertions::assertThat)
            .as("Access Flags")
            .withRepresentation(AccessFlagsRepresentation.INSTANCE)
            .usingElementComparator(AccessFlagsComparator.INSTANCE)
            .usingComparator(AccessFlagsComparator.ITERABLE_INSTANCE);
  }

  /**
   * Creates an {@link AsmIterableAssert} for {@link MethodNode}s which
   * uses {@link MethodNodeRepresentation#INSTANCE} for the representation
   * and for equality {@link MethodNodeComparator#INSTANCE} and
   * {@link MethodNodeComparator#ITERABLE_INSTANCE}.
   *
   * <p>The returned {@code AsmIterableAssert} should be used in conjunction with
   * {@link AsmIterableAssert#containsExactlyInAnyOrderElementsOf}.
   *
   * <p>To override the representation or comparator call
   * {@link AsmIterableAssert#usingComparator(Comparator)} or
   * {@link AsmIterableAssert#withRepresentation(Representation)}.
   *
   * @param actual an {@link Iterable} of {@link MethodNode}s; may be null.
   * @return a new {@link AsmIterableAssert}; never null.
   */
  public static AsmIterableAssert<?, MethodNode, MethodNodeAssert> assertThatMethods(Iterable<MethodNode> actual) {
    return new AsmIterableAssert<>(actual, AsmAssertions::assertThat)
            .as("Methods")
            .withRepresentation(MethodNodeRepresentation.INSTANCE)
            .usingElementComparator(MethodNodeComparator.INSTANCE)
            .usingComparator(MethodNodeComparator.ITERABLE_INSTANCE);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
