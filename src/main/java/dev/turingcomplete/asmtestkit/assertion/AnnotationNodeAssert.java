package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.comparator.AnnotationNodeComparator;
import dev.turingcomplete.asmtestkit.assertion.representation.AnnotationNodeRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.Comparator;

/**
 * An AssertJ {@link AbstractAssert} for an {@link AnnotationNode}.
 *
 * <p>An instance can be created via {@link AsmAssertions#assertThat(AnnotationNode)}.
 * Use {@link AsmAssertions#assertThatAnnotations(Iterable)} for multiple
 * {@code AnnotationNode}s.
 *
 * <p>To override the used {@link AnnotationNodeRepresentation} or
 * {@link AnnotationNodeComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 */
public class AnnotationNodeAssert extends AbstractAnnotationNodeAssert<AnnotationNodeAssert, AnnotationNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes an {@link AnnotationNodeAssert}.
   *
   * @param actual the actual {@link AnnotationNode}; may be null.
   */
  protected AnnotationNodeAssert(AnnotationNode actual) {
    super("Annotation",
          actual,
          AnnotationNodeAssert.class,
          AnnotationNodeRepresentation.INSTANCE,
          AnnotationNodeComparator.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //
  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
