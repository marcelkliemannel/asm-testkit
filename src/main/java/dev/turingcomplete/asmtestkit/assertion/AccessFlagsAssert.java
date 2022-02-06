package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.comparator.AccessFlagsComparator;
import dev.turingcomplete.asmtestkit.representation.AccessFlagsRepresentation;
import dev.turingcomplete.asmtestkit.node.AccessFlags;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.presentation.Representation;

import java.util.Comparator;

import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getFromObjectElse;

/**
 * An AssertJ {@link AbstractAssert} for {@link AccessFlags}s.
 *
 * <p>An instance can be created via {@link AsmAssertions#assertThat(AccessFlags)}.
 * Use {@link AsmAssertions#assertThatAccessFlags(Iterable)} for
 * multiple {@code AccessFlags}.
 *
 * <p>To override the used {@link AccessFlagsRepresentation} or {@link AccessFlagsComparator}
 * use {@link #withRepresentation(Representation)} or {@link #usingComparator(Comparator)}.
 */
public class AccessFlagsAssert extends AsmAssert<AccessFlagsAssert, AccessFlags> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes an {@link AccessFlagsAssert}.
   *
   * @param actual the actual {@link AccessFlags}; may be null.
   */
  protected AccessFlagsAssert(AccessFlags actual) {
    super("Access", actual, AccessFlagsAssert.class, AccessFlagsRepresentation.INSTANCE, AccessFlagsComparator.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public AccessFlagsAssert isEqualTo(Object expected) {
    AccessFlagsRepresentation accessFlagsRepresentation = getAccessFlagsRepresentation();

    String[] actualRepresentations = accessFlagsRepresentation.toJavaSourceCodeRepresentations(actual);
    String[] expectedRepresentations = getFromObjectElse(expected, AccessFlags.class, accessFlagsRepresentation::toJavaSourceCodeRepresentations, new String[0]);

    Assertions.assertThat(actualRepresentations)
              .as(createCrumbDescription("Has equal access values"))
              .containsExactlyInAnyOrder(expectedRepresentations);

    return this;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private AccessFlagsRepresentation getAccessFlagsRepresentation() {
    Representation representation = getWritableAssertionInfo().representation();
    if (representation instanceof AccessFlagsRepresentation) {
      return (AccessFlagsRepresentation) representation;
    }

    return  (AccessFlagsRepresentation) asmRepresentations.getAsmRepresentation(AccessFlags.class);
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
