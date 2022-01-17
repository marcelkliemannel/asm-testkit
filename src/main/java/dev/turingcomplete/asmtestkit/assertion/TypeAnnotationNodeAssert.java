package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.comparator.TypeAnnotationNodeComparator;
import dev.turingcomplete.asmtestkit.assertion.option.AssertOption;
import dev.turingcomplete.asmtestkit.assertion.option.StandardAssertOption;
import dev.turingcomplete.asmtestkit.assertion.representation.TypeAnnotationNodeRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Comparator;

/**
 * An AssertJ {@link AbstractAssert} for a {@link TypeAnnotationNode}.
 *
 * <p>An instance can be created via {@link AsmAssertions#assertThat(TypeAnnotationNode)}.
 * Use {@link AsmAssertions#assertThatTypeAnnotations(Iterable)} for multiple
 * {@code TypeAnnotationNode}s.
 *
 * <p>The supported {@link AssertOption} is
 * {@link StandardAssertOption#IGNORE_ANNOTATION_VALUES}.
 *
 * <p>To override the used {@link TypeAnnotationNodeRepresentation} or
 * {@link TypeAnnotationNodeComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 */
public class TypeAnnotationNodeAssert
        extends AbstractTypeAnnotationNodeAssert<TypeAnnotationNodeAssert, TypeAnnotationNode> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes a {@link TypeAnnotationNodeAssert}.
   *
   * @param actual the actual {@link TypeAnnotationNode}; may be null.
   */
  protected TypeAnnotationNodeAssert(TypeAnnotationNode actual) {
    super("Type Annotation",
          actual,
          TypeAnnotationNodeAssert.class,
          TypeAnnotationNodeRepresentation.INSTANCE,
          TypeAnnotationNodeComparator.INSTANCE);
  }


  // -- Exposed Methods --------------------------------------------------------------------------------------------- //
  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
