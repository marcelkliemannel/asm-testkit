package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.comperator.AnnotationNodeComparator;
import dev.turingcomplete.asmtestkit.assertion.comperator.AttributeComparator;
import dev.turingcomplete.asmtestkit.assertion.comperator.TypeComparator;
import dev.turingcomplete.asmtestkit.assertion.comperator.TypePathComparator;
import dev.turingcomplete.asmtestkit.assertion.representation.AnnotationNodeRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.AttributeRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.TypePathRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.TypeRepresentation;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.IterableAssert;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.Comparator;

public final class AsmAssertions {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  private AsmAssertions() {
    throw new UnsupportedOperationException();
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  // ---- Objects ----------------------------------------------------------- //

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
   * Creates an {@link AnnotationNode}.
   *
   * @param annotationNode an {@link AnnotationNode}; may be null.
   * @return a new {@link AttributeAssert}; never null.
   */
  public static AnnotationNodeAssert assertThat(AnnotationNode annotationNode) {
    return new AnnotationNodeAssert(annotationNode);
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

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
