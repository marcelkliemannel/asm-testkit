package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.asmutils.AccessKind;
import dev.turingcomplete.asmtestkit.assertion.comparator.AnnotationNodeComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.AttributeComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.TypeAnnotationNodeComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.TypeComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.TypePathComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.TypeReferenceComparator;
import dev.turingcomplete.asmtestkit.assertion.representation.AnnotationNodeRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.AttributeRepresentation;
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
import org.objectweb.asm.tree.AnnotationNode;
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

  // ---- ASM Objects ------------------------------------------------------- //

  /**
   * Creates an {@link AttributeAssert}.
   *
   * @param attribute an {@link Attribute}; may be null.
   * @return a new {@link AttributeAssert}; never null.
   */
  public static AttributeAssert assertThat(Attribute attribute) {
    return new AttributeAssert(attribute);
  }

  /**
   * Creates an {@link AnnotationNodeAssert}.
   *
   * @param annotationNode an {@link AnnotationNode}; may be null.
   * @return a new {@link AnnotationNodeAssert}; never null.
   */
  public static AnnotationNodeAssert assertThat(AnnotationNode annotationNode) {
    return new AnnotationNodeAssert(annotationNode);
  }

  /**
   * Creates an {@link TypeAnnotationNodeAssert}.
   *
   * @param typeAnnotationNode an {@link TypeAnnotationNode}; may be null.
   * @return a new {@link TypeAnnotationNodeAssert}; never null.
   */
  public static TypeAnnotationNodeAssert assertThat(TypeAnnotationNode typeAnnotationNode) {
    return new TypeAnnotationNodeAssert(typeAnnotationNode);
  }

  /**
   * Creates a {@link TypePathAssert}.
   *
   * @param typePath a {@link TypePath}; may be null.
   * @return a new {@link TypePathAssert}; never null.
   */
  public static TypePathAssert assertThat(TypePath typePath) {
    return new TypePathAssert(typePath);
  }

  /**
   * Creates a {@link TypeAssert}.
   *
   * @param type a {@link Type}; may be null.
   * @return a new {@link TypeAssert}; never null.
   */
  public static TypeAssert assertThat(Type type) {
    return new TypeAssert(type);
  }

  /**
   * Creates a {@link TypeReferenceAssert}.
   *
   * @param typeReference a {@link TypeReference}; may be null.
   * @return a new {@link TypeReferenceAssert}; never null.
   */
  public static TypeReferenceAssert assertThat(TypeReference typeReference) {
    return new TypeReferenceAssert(typeReference);
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
   * @param attributes an {@link Iterable} of {@link Attribute}s; may be null.
   * @return a new {@link IterableAssert}; never null.
   */
  public static IterableAssert<Attribute> assertThatAttributes(Iterable<Attribute> attributes) {
    return Assertions.assertThat(attributes)
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
   * @param annotationNodes an {@link Iterable} of {@link AnnotationNode}s; may
   *                        be null.
   * @return a new {@link IterableAssert}; never null.
   */
  public static IterableAssert<AnnotationNode> assertThatAnnotationNodes(Iterable<AnnotationNode> annotationNodes) {
    return Assertions.assertThat(annotationNodes)
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
   * @param typeAnnotationNodes an {@link Iterable} of {@link TypeAnnotationNode}s;
   *                            may be null.
   * @return a new {@link IterableAssert}; never null.
   */
  public static IterableAssert<TypeAnnotationNode> assertThatTypeAnnotationNodes(Iterable<TypeAnnotationNode> typeAnnotationNodes) {
    return Assertions.assertThat(typeAnnotationNodes)
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
   * @param typePaths an {@link Iterable} of {@link TypePath}s; may be null.
   * @return a new {@link IterableAssert}; never null.
   */
  public static IterableAssert<TypePath> assertThatTypePaths(Iterable<TypePath> typePaths) {
    return Assertions.assertThat(typePaths)
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
   * @param types an {@link Iterable} of {@link Type}s; may be null.
   * @return a new {@link IterableAssert}; never null.
   */
  public static IterableAssert<Type> assertThatTypes(Iterable<Type> types) {
    return Assertions.assertThat(types)
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
   * @param typeReferences an {@link Iterable} of {@link TypeReference}s;
   *                       may be null.
   * @return a new {@link IterableAssert}; never null.
   */
  public static IterableAssert<TypeReference> assertThatTypeReferences(Iterable<TypeReference> typeReferences) {
    return Assertions.assertThat(typeReferences)
                     .withRepresentation(TypeReferenceRepresentation.INSTANCE)
                     .usingElementComparator(TypeReferenceComparator.INSTANCE)
                     .usingComparator(TypeReferenceComparator.ITERABLE_INSTANCE);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
