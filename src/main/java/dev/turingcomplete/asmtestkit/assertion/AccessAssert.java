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
 *
 * <p>There are no direct supported {@link AssertOption}s yet.
 *
 * <p>To override the used {@link AccessRepresentation} or {@link AccessComparator}
 * use {@link #withRepresentation(Representation)} or {@link #usingComparator(Comparator)}.
 */
public class AccessAssert extends AsmAssert<AccessAssert, Integer> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final AccessKind accessKind;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes an {@link AccessAssert}.
   *
   * @param actual the actual {@link Integer} access flags; may be null.
   * @param accessKind the {@link AccessKind of the given access flags};
   *                  never null.
   */
  protected AccessAssert(Integer actual, AccessKind accessKind) {
    super("Access", actual, AccessAssert.class, AccessRepresentation.instance(accessKind), AccessComparator.INSTANCE);

    this.accessKind = accessKind;
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public AccessAssert isEqualTo(Object expected) {
    Function<Integer, String[]> getAccessRepresentation = access -> AccessRepresentation.instance(accessKind).toJavaSourceCodeRepresentations(access);
    String[] actualRepresentations = AssertUtils.getFromObjectElse(actual, Integer.class, getAccessRepresentation, new String[0]);
    String[] expectedRepresentations = AssertUtils.getFromObjectElse(expected, Integer.class, getAccessRepresentation, new String[0]);

    Assertions.assertThat(actualRepresentations)
              .as(createCrumbDescription("Has equal access values"))
              .containsExactlyInAnyOrder(expectedRepresentations);

    return this;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
