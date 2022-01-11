package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.asmutils.AccessKind;
import dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils;
import dev.turingcomplete.asmtestkit.assertion.comparator.AccessComparator;
import dev.turingcomplete.asmtestkit.assertion.option.AssertOption;
import dev.turingcomplete.asmtestkit.assertion.representation.AccessRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.presentation.Representation;

import java.util.Comparator;
import java.util.function.Function;

/**
 * An AssertJ {@link AbstractAssert} for {@link Integer} access flags.
 *
 * <p>To override the used {@link AccessRepresentation} or
 * {@link AccessComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 *
 * <p>An instance can be created via:
 * <ul>
 *   <li>{@link AsmAssertions#assertThatClassAccess(Integer)}
 *   <li>{@link AsmAssertions#assertThatFieldAccess(Integer)}
 *   <li>{@link AsmAssertions#assertThatMethodAccess(Integer)}
 *   <li>{@link AsmAssertions#assertThatParameterAccess(Integer)}
 *   <li>{@link AsmAssertions#assertThatModuleAccess(Integer)}
 *   <li>{@link AsmAssertions#assertThatModuleRequiresAccess(Integer)}
 *   <li>{@link AsmAssertions#assertThatModuleRequiresAccess(Integer)}
 *   <li>{@link AsmAssertions#assertThatModuleOpensAccess(Integer)}
 *   <li>{@link AsmAssertions#assertThatAccess(Integer, AccessKind)}
 * </ul>
 */
public class AccessAssert extends AsmAssert<AccessAssert, Integer> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final AccessKind accessKind;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes an {@link AccessAssert}.
   *
   * <p>There are no direct supported {@link AssertOption}s yet.
   *
   * @param actual        the actual {@link Integer} access flags; may be null.
   * @param assertOptions an array of {@link AssertOption}s; never null.
   */
  public AccessAssert(Integer actual, AccessKind accessKind, AssertOption... assertOptions) {
    super(actual, AccessAssert.class, Integer.class, createSelfDescription(actual, accessKind), assertOptions);

    this.accessKind = accessKind;

    info.useRepresentation(AccessRepresentation.instance(accessKind));
    //noinspection ResultOfMethodCallIgnored
    usingComparator(AccessComparator.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public AccessAssert isEqualTo(Object expected) {
    Function<Integer, String[]> getAccessRepresentation = access -> AccessRepresentation.instance(accessKind).toJavaSourceCodeRepresentations(access);
    String[] actualRepresentations = AssertUtils.getFromObjectElse(actual, Integer.class, getAccessRepresentation, new String[0]);
    String[] expectedRepresentations = AssertUtils.getFromObjectElse(expected, Integer.class, getAccessRepresentation, new String[0]);

    Assertions.assertThat(actualRepresentations)
              .as(createDescription("Is equal access values"))
              .containsExactlyInAnyOrder(expectedRepresentations);

    return this;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private static String createSelfDescription(Integer actual, AccessKind accessKind) {
    return "Access: " + AccessRepresentation.instance(accessKind).toJavaSourceCodeRepresentation(actual);
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
