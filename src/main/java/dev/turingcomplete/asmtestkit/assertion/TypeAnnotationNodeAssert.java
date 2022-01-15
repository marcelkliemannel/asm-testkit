package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.comparator.TypeAnnotationNodeComparator;
import dev.turingcomplete.asmtestkit.assertion.option.AssertOption;
import dev.turingcomplete.asmtestkit.assertion.option.StandardAssertOption;
import dev.turingcomplete.asmtestkit.assertion.representation.AsmRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.TypeAnnotationNodeRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.TypeReference;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Comparator;

import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getFromObjectElseNull;

/**
 * An AssertJ {@link AbstractAssert} for a {@link TypeAnnotationNode}.
 *
 * <p>An instance can be created via
 * {@link AsmAssertions#assertThat(TypeAnnotationNode)} or
 * {@link #createForTypeAnnotationNode(TypeAnnotationNode)}.
 * Use {@link AsmAssertions#assertThatTypeAnnotationNodes(Iterable)} for multiple
 * {@code TypeAnnotationNode}s.
 *
 * <p>The supported {@link AssertOption} is
 * {@link StandardAssertOption#IGNORE_ANNOTATION_VALUES}.
 *
 * <p>To override the used {@link TypeAnnotationNodeRepresentation} or
 * {@link TypeAnnotationNodeComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 */
public class TypeAnnotationNodeAssert<S extends TypeAnnotationNodeAssert<S, A>, A extends TypeAnnotationNode>
        extends AnnotationNodeAssert<S, A> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes a {@link TypeAnnotationNodeAssert}.
   *
   * @param name                  a short, generic {@link String} name; never null.
   * @param actual                the actual {@link A}; may be null.
   * @param selfType              the {@link Class} of {@code this); never null.
   * @param defaultRepresentation the default {@link Representation}; may be null.
   * @param defaultComparator     the default {@link Comparator}; may be null.
   */
  protected TypeAnnotationNodeAssert(String name,
                                     A actual,
                                     Class<?> selfType,
                                     AsmRepresentation<A> defaultRepresentation,
                                     Comparator<A> defaultComparator) {

    super(name, actual, selfType, defaultRepresentation, defaultComparator);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link TypeAnnotationNodeAssert} for a {@link TypeAnnotationNode}.
   *
   * @param actual the actual {@link TypeAnnotationNode}; may be null.
   * @return a new {@link TypeAnnotationNodeAssert}; never null.
   */
  public static TypeAnnotationNodeAssert<?, TypeAnnotationNode> createForTypeAnnotationNode(TypeAnnotationNode actual) {
    return new TypeAnnotationNodeAssert<>("Type Annotation",
                                          actual,
                                          TypeAnnotationNodeAssert.class,
                                          TypeAnnotationNodeRepresentation.INSTANCE,
                                          TypeAnnotationNodeComparator.INSTANCE);
  }

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
                 .as(createDescription("Has equal type path"))
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
                 .as(createDescription("Has equal type reference"))
                 .isEqualTo(getFromObjectElseNull(expected, TypeAnnotationNode.class, typeAnnotationNode -> new TypeReference(typeAnnotationNode.typeRef)));

  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
