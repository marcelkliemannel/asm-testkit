package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.comparator.AnnotationDefaultValueComparator;
import dev.turingcomplete.asmtestkit.assertion.option.AssertOption;
import dev.turingcomplete.asmtestkit.assertion.representation.AnnotationDefaultValueRepresentation;
import dev.turingcomplete.asmtestkit.node.AnnotationDefaultValue;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.presentation.Representation;

import java.util.Comparator;

/**
 * An AssertJ {@link AbstractAssert} for an annotation default value.
 *
 * <p>An instance can be created via
 * {@link AsmAssertions#assertThatAnnotationDefaultValue(Object)}.
 * Use {@link AsmAssertions#assertThatAnnotationDefaultValues(Iterable)} for
 * multiple annotation default values.
 *
 * <p>There are no direct supported {@link AssertOption}s yet.
 *
 * <p>To override the used {@link AnnotationDefaultValueRepresentation} or
 * {@link AnnotationDefaultValueComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 */
public class AnnotationDefaultValueAssert extends AsmAssert<AnnotationDefaultValueAssert, AnnotationDefaultValue> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes an {@link AnnotationDefaultValueAssert}.
   *
   * @param actual the actual annotation default {@link Object} value; may be null.
   */
  public AnnotationDefaultValueAssert(AnnotationDefaultValue actual) {
    super("Annotation default value",
          actual,
          AnnotationDefaultValueAssert.class,
          AnnotationDefaultValueRepresentation.INSTANCE,
          AnnotationDefaultValueComparator.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //
  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
