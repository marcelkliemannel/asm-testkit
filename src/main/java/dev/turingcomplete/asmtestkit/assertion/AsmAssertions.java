package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.comperator.AnnotationNodeComparator;
import dev.turingcomplete.asmtestkit.assertion.comperator.AttributeComparator;
import dev.turingcomplete.asmtestkit.assertion.comperator.TypePathComparator;
import dev.turingcomplete.asmtestkit.assertion.representation.AnnotationNodeRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.AttributeRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.TypePathRepresentation;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.IterableAssert;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Attribute;
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

  // ---- Iterable ---------------------------------------------------------- //

  /**
   * Creates an {@link IterableAssert} for {@link Attribute}s which uses
   * {@link AttributeRepresentation#instance()} for the representation and
   * for equality {@link AttributeComparator#instance()} and
   * {@link AttributeComparator#iterableInstance()}.
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
                     .withRepresentation(AttributeRepresentation.instance())
                     .usingElementComparator(AttributeComparator.instance())
                     .usingComparator(AttributeComparator.iterableInstance());
  }

  /**
   * Creates an {@link IterableAssert} for {@link AnnotationNode}s which uses
   * {@link AnnotationNodeRepresentation#instance()} for the representation and
   * for equality {@link AnnotationNodeComparator#instance()} and
   * {@link AnnotationNodeComparator#iterableInstance()}.
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
                     .withRepresentation(AnnotationNodeRepresentation.instance())
                     .usingElementComparator(AnnotationNodeComparator.instance())
                     .usingComparator(AnnotationNodeComparator.iterableInstance());
  }

  /**
   * Creates an {@link IterableAssert} for {@link TypePath}s which uses
   * {@link TypePathRepresentation#instance()} for the representation and for
   * equality {@link TypePathComparator#instance()} and
   * {@link TypePathComparator#iterableInstance()}.
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
                     .withRepresentation(TypePathRepresentation.instance())
                     .usingElementComparator(TypePathComparator.instance())
                     .usingComparator(TypePathComparator.iterableInstance());
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
