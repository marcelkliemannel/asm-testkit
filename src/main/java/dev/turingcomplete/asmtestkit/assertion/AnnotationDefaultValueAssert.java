package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.comparator.AnnotationDefaultValueComparator;
import dev.turingcomplete.asmtestkit.representation.AnnotationDefaultValueRepresentation;
import dev.turingcomplete.asmtestkit.node.AnnotationDefaultNode;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.presentation.Representation;

import java.util.Comparator;

/**
 * An AssertJ {@link AbstractAssert} for an {@link AnnotationDefaultNode}.
 *
 * <p>An instance can be created via
 * {@link AsmAssertions#assertThat(AnnotationDefaultNode)}.
 * Use {@link AsmAssertions#assertThatAnnotationDefaulls(Iterable)} for
 * multiple {@code AnnotationDefault}s.
 *
 * <p>To override the used {@link AnnotationDefaultValueRepresentation} or
 * {@link AnnotationDefaultValueComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 */
public class AnnotationDefaultValueAssert extends AsmAssert<AnnotationDefaultValueAssert, AnnotationDefaultNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes an {@link AnnotationDefaultValueAssert}.
   *
   * @param actual the actual annotation default {@link Object} value; may be null.
   */
  public AnnotationDefaultValueAssert(AnnotationDefaultNode actual) {
    super("Annotation default",
          actual,
          AnnotationDefaultValueAssert.class,
          AnnotationDefaultValueRepresentation.INSTANCE,
          AnnotationDefaultValueComparator.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //
  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
