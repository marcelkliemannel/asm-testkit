package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.comparator.TypePathComparator;
import dev.turingcomplete.asmtestkit.representation.TypePathRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.TypePath;

import java.util.Comparator;

/**
 * An AssertJ {@link AbstractAssert} for a {@link TypePath} which will use the
 * {@link TypePathComparator} to determine the equality.
 *
 * <p>An instance can be created via {@link AsmAssertions#assertThat(TypePath)}.
 * Use {@link AsmAssertions#assertThatTypePaths(Iterable)} for multiple
 * {@code TypePath}s.
 *
 * <p>To override the used {@link TypePathRepresentation} or
 * {@link TypePathComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 */
public class TypePathAssert extends AsmAssert<TypePathAssert, TypePath> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes a {@link TypePathAssert}.
   *
   * @param actual the actual {@link TypePath}; may be null.
   */
  protected TypePathAssert(TypePath actual) {
    super("Type Path", actual, TypePathAssert.class, TypePathRepresentation.INSTANCE, TypePathComparator.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //
  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
