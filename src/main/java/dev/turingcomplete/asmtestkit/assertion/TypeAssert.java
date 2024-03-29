package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.comparator.TypeComparator;
import dev.turingcomplete.asmtestkit.representation.TypeRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Type;

import java.util.Comparator;

/**
 * An AssertJ {@link AbstractAssert} for a {@link Type} which will use the
 * {@link TypeComparator} to determine the equality.
 *
 * <p>An instance can be created via {@link AsmAssertions#assertThat(Type)}.
 * Use {@link AsmAssertions#assertThatTypes(Iterable)} for multiple {@code Type}s.
 *
 * <p>To override the used {@link TypeRepresentation} or
 * {@link TypeComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 */
public class TypeAssert extends AsmAssert<TypeAssert, Type> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes a {@link TypeAssert}.
   *
   * @param actual the actual {@link Type}; may be null.
   */
  protected TypeAssert(Type actual) {
    super("Type", actual, TypeAssert.class, TypeRepresentation.INSTANCE, TypeComparator.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //
  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
