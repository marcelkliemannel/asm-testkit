package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.comperator.TypePathComparator;
import dev.turingcomplete.asmtestkit.assertion.option.AssertOption;
import dev.turingcomplete.asmtestkit.assertion.representation.TypePathRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.TypePath;

import java.util.Comparator;

/**
 * An AssertJ {@link AbstractAssert} for a {@link TypePath} which will use the
 * {@link TypePathComparator} to determine the equality.
 *
 * <p>To override the used {@link TypePathRepresentation} or
 * {@link TypePathComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 *
 * <p>An instance can be created via {@link AsmAssertions#assertThat(TypePath)}.
 */
public class TypePathAssert extends AsmAssert<TypePathAssert, TypePath> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes a {@link TypePathAssert}.
   *
   * <p>There are no direct supported {@link AssertOption}s yet.
   *
   * @param actual the actual {@link TypePath}; may be null.
   * @param assertOptions an array of {@link AssertOption}s; never null.
   */
  public TypePathAssert(TypePath actual, AssertOption... assertOptions) {
    super(actual, TypePathAssert.class, TypePath.class, createSelfDescription(actual), assertOptions);

    info.useRepresentation(TypePathRepresentation.instance());
    //noinspection ResultOfMethodCallIgnored
    usingComparator(TypePathComparator.instance());
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //
  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private static String createSelfDescription(TypePath actual) {
    return "Type path: " + actual;
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
