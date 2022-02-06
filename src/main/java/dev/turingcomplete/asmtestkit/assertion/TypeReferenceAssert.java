package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.comparator.TypePathComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.TypeReferenceComparator;
import dev.turingcomplete.asmtestkit.assertion.representation.TypePathRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.TypeReferenceRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.TypeReference;

import java.util.Comparator;

/**
 * An AssertJ {@link AbstractAssert} for a {@link TypeReference} which will use
 * the {@link TypeReferenceComparator} to determine the equality.
 *
 * <p>An instance can be created via {@link AsmAssertions#assertThat(TypeReference)}.
 * Use {@link AsmAssertions#assertThatTypeReferences(Iterable)} for multiple
 * {@code TypeReference}s.
 *
 * <p>To override the used {@link TypePathRepresentation} or
 * {@link TypePathComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 */
public class TypeReferenceAssert extends AsmAssert<TypeReferenceAssert, TypeReference> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes a {@link TypeReferenceAssert}.
   *
   * @param actual the actual {@link TypeReference}; may be null.
   */
  protected TypeReferenceAssert(TypeReference actual) {
    super("Type Reference", actual, TypeReferenceAssert.class, TypeReferenceRepresentation.INSTANCE, TypeReferenceComparator.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //
  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
