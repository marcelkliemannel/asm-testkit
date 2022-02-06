package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.representation.AbstractAsmRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.TypeReference;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Comparator;

import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getFromObjectElseNull;

/**
 * A base class for an AssertJ {@link AbstractAssert} for a
 * {@link TypeAnnotationNode} or its subtypes.
 *
 * @param <S> the 'self' type of {@code this} {@link AsmAssert}.
 * @param <A> a {@link TypeAnnotationNode} or a subtype of the actual object.
 */
public abstract class AbstractTypeAnnotationNodeAssert<S extends AbstractTypeAnnotationNodeAssert<S, A>, A extends TypeAnnotationNode>
        extends AbstractAnnotationNodeAssert<S, A> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes a {@link AbstractTypeAnnotationNodeAssert}.
   *
   * @param name                  a short, generic {@link String} name; never null.
   * @param actual                the actual {@link A}; may be null.
   * @param selfType              the {@link Class} of {@code this}; never null.
   * @param defaultRepresentation the default {@link Representation}; may be null.
   * @param defaultComparator     the default {@link Comparator}; may be null.
   */
  protected AbstractTypeAnnotationNodeAssert(String name,
                                             A actual,
                                             Class<?> selfType,
                                             AbstractAsmRepresentation<A> defaultRepresentation,
                                             Comparator<A> defaultComparator) {

    super(name, actual, selfType, defaultRepresentation, defaultComparator);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public S isEqualTo(Object expected) {
    super.isEqualTo(expected);

    hasEqualTypePath(expected);
    hasEqualTypeReference(expected);

    //noinspection unchecked
    return (S) this;
  }

  /**
   * Checks whether the {@link TypePath} of the given expected
   * {@link TypeAnnotationNode} is equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a
   *                 {@link TypeAnnotationNode}; may be null.
   */
  protected void hasEqualTypePath(Object expected) {
    AsmAssertions.assertThat(getFromObjectElseNull(actual, (TypeAnnotationNode typeAnnotationNode) -> typeAnnotationNode.typePath))
                 .addOptions(options)
                 .as(createCrumbDescription("Has equal type path"))
                 .isEqualTo(getFromObjectElseNull(expected, TypeAnnotationNode.class, typeAnnotationNode -> typeAnnotationNode.typePath));
  }

  /**
   * Checks whether the {@link TypeReference} of the given expected
   * {@link TypeAnnotationNode} is equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a
   *                 {@link TypeAnnotationNode}; may be null.
   */
  protected void hasEqualTypeReference(Object expected) {
    AsmAssertions.assertThat(getFromObjectElseNull(actual, (TypeAnnotationNode typeAnnotationNode) -> new TypeReference(actual.typeRef)))
                 .addOptions(options)
                 .as(createCrumbDescription("Has equal type reference"))
                 .isEqualTo(getFromObjectElseNull(expected, TypeAnnotationNode.class, typeAnnotationNode -> new TypeReference(typeAnnotationNode.typeRef)));

  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
