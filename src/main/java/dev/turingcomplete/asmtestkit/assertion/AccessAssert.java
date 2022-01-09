package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.asmutils.AccessKind;
import dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils;
import dev.turingcomplete.asmtestkit.assertion.option.AssertOption;
import dev.turingcomplete.asmtestkit.assertion.representation.AccessRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.function.Function;

/**
 * An AssertJ {@link AbstractAssert} for {@link Integer} access flags.
 *
 * <p>To override the used {@link AccessRepresentation} call
 * {@link #withRepresentation(Representation)}.
 *
 * <p>An instance can be created via {@link AsmAssertions#assertThat(AnnotationNode)}.
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
   * @param actual the actual {@link Integer} access flags; may be null.
   * @param assertOptions an array of {@link AssertOption}s; never null.
   */
  public AccessAssert(Integer actual, AccessKind accessKind, AssertOption... assertOptions) {
    super(actual, AccessAssert.class, Integer.class, createSelfDescription(actual, accessKind), assertOptions);

    this.accessKind = accessKind;

    info.useRepresentation(AccessRepresentation.instance(accessKind));
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public AccessAssert isEqualTo(Object expected) {
    Function<Integer, String[]> getAccessRepresentation = access -> AccessRepresentation.instance(accessKind).toJavaSourceCodeRepresentations(access);
    String[] actualRepresentations = AssertUtils.getFromObjectElse(actual, Integer.class, getAccessRepresentation, new String[0]);
    String[] expectedRepresentations = AssertUtils.getFromObjectElse(expected, Integer.class, getAccessRepresentation, new String[0]);

    Assertions.assertThat(actualRepresentations)
              .containsExactlyInAnyOrder(expectedRepresentations);

    return this;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private static String createSelfDescription(Integer actual, AccessKind accessKind) {
    return "Access: " + AccessRepresentation.instance(accessKind).toJavaSourceCodeRepresentation(actual);
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
