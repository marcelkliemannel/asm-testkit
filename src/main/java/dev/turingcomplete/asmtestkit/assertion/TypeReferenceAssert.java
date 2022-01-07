package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.comparator.TypePathComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.TypeReferenceComparator;
import dev.turingcomplete.asmtestkit.assertion.option.AssertOption;
import dev.turingcomplete.asmtestkit.assertion.representation.TypePathRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.TypeReferenceRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.TypeReference;

import java.util.Comparator;

/**
 * An AssertJ {@link AbstractAssert} for a {@link TypeReference} which will use
 * the {@link TypeReferenceComparator} to determine the equality.
 *
 * <p>To override the used {@link TypePathRepresentation} or
 * {@link TypePathComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 *
 * <p>An instance can be created via {@link AsmAssertions#assertThat(TypePath)}.
 */
public class TypeReferenceAssert extends AsmAssert<TypeReferenceAssert, TypeReference> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes a {@link TypeReferenceAssert}.
   *
   * <p>There are no direct supported {@link AssertOption}s yet.
   *
   * @param actual the actual {@link TypeReference}; may be null.
   * @param assertOptions an array of {@link AssertOption}s; never null.
   */
  public TypeReferenceAssert(TypeReference actual, AssertOption... assertOptions) {
    super(actual, TypeReferenceAssert.class, TypeReference.class, createSelfDescription(actual), assertOptions);

    info.useRepresentation(TypeReferenceRepresentation.INSTANCE);
    //noinspection ResultOfMethodCallIgnored
    usingComparator(TypeReferenceComparator.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //
  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private static String createSelfDescription(TypeReference actual) {
    return "Type reference: " + TypeReferenceRepresentation.INSTANCE.toStringOf(actual);
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
