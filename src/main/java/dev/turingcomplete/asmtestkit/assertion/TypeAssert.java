package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.comperator.TypeComparator;
import dev.turingcomplete.asmtestkit.assertion.option.AssertOption;
import dev.turingcomplete.asmtestkit.assertion.representation.TypeRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Type;

import java.util.Comparator;

/**
 * An AssertJ {@link AbstractAssert} for a {@link Type} which will use the
 * {@link TypeComparator} to determine the equality.
 *
 * <p>To override the used {@link TypeRepresentation} or
 * {@link TypeComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 *
 * <p>An instance can be created via {@link AsmAssertions#assertThat(Attribute)}.
 */
public class TypeAssert extends AsmAssert<TypeAssert, Type> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes a {@link TypeAssert}.
   *
   * <p>There are no direct supported {@link AssertOption}s yet.
   *
   * @param actual the actual {@link Type}; may be null.
   * @param assertOptions an array of {@link AssertOption}s; never null.
   */
  public TypeAssert(Type actual, AssertOption... assertOptions) {
    super(actual, TypeAssert.class, Type.class, createSelfDescription(actual), assertOptions);

    info.useRepresentation(TypeRepresentation.instance());
    //noinspection ResultOfMethodCallIgnored
    usingComparator(TypeComparator.instance());
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //
  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private static String createSelfDescription(Type actual) {
    return "Type: " + TypeRepresentation.instance().toStringOf(actual);
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
