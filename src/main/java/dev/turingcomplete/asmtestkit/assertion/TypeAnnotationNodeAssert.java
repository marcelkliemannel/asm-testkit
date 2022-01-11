package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.comparator.TypeAnnotationNodeComparator;
import dev.turingcomplete.asmtestkit.assertion.option.AssertOption;
import dev.turingcomplete.asmtestkit.assertion.representation.AnnotationNodeRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.TypeAnnotationNodeRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.TypeReference;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Comparator;

import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getFromObjectElseNull;

/**
 * An AssertJ {@link AbstractAssert} for a {@link TypeAnnotationNode}.
 *
 * <p>To override the used {@link TypeAnnotationNodeRepresentation} or
 * {@link TypeAnnotationNodeComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 *
 * <p>An instance can be created via
 * {@link AsmAssertions#assertThat(TypeAnnotationNode)}.
 */
public class TypeAnnotationNodeAssert extends AbstractAnnotationNodeAssert<TypeAnnotationNodeAssert, TypeAnnotationNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes a {@link TypeAnnotationNodeAssert}.
   *
   * <p>There are no direct supported {@link AssertOption}s yet.
   *
   * @param actual        the actual {@link TypeAnnotationNode}; may be null.
   * @param assertOptions an array of {@link AssertOption}s; never null.
   */
  public TypeAnnotationNodeAssert(TypeAnnotationNode actual, AssertOption... assertOptions) {
    super(actual, TypeAnnotationNodeAssert.class, TypeAnnotationNode.class, createSelfDescription(actual), assertOptions);

    info.useRepresentation(TypeAnnotationNodeRepresentation.INSTANCE);
    //noinspection ResultOfMethodCallIgnored
    usingComparator(TypeAnnotationNodeComparator.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public TypeAnnotationNodeAssert isEqualTo(Object expected) {
    super.isEqualTo(expected);

    isEqualTypePath(expected);
    isEqualTypeReference(expected);

    return this;
  }

  /**
   * Checks whether the {@link TypePath} of the given expected
   * {@link TypeAnnotationNode} is equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a
   *                 {@link TypeAnnotationNode}; may be null.
   */
  protected void isEqualTypePath(Object expected) {
    AsmAssertions.assertThat(actual.typePath)
                 .addOptions(options)
                 .as(createDescription("Is equal type path"))
                 .isEqualTo(getFromObjectElseNull(expected, TypeAnnotationNode.class, typeAnnotationNode -> typeAnnotationNode.typePath));
  }

  /**
   * Checks whether the {@link TypeReference} of the given expected
   * {@link TypeAnnotationNode} is equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a
   *                 {@link TypeAnnotationNode}; may be null.
   */
  protected void isEqualTypeReference(Object expected) {
    AsmAssertions.assertThat(new TypeReference(actual.typeRef))
                 .addOptions(options)
                 .as(createDescription("Is equal type reference"))
                 .isEqualTo(getFromObjectElseNull(expected, TypeAnnotationNode.class, typeAnnotationNode -> new TypeReference(typeAnnotationNode.typeRef)));

  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private static String createSelfDescription(AnnotationNode actual) {
    return "Type Annotation: " + AnnotationNodeRepresentation.INSTANCE.toStringOf(actual);
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
