package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.comparator.AccessNodeComparator;
import dev.turingcomplete.asmtestkit.representation.AccessNodeRepresentation;
import dev.turingcomplete.asmtestkit.node.AccessNode;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.presentation.Representation;

import java.util.Comparator;

import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getFromObjectElse;

/**
 * An AssertJ {@link AbstractAssert} for {@link AccessNode}s.
 *
 * <p>An instance can be created via {@link AsmAssertions#assertThat(AccessNode)}.
 * Use {@link AsmAssertions#assertThatAccesses(Iterable)} for
 * multiple {@code AccessNode}s.
 *
 * <p>To override the used {@link AccessNodeRepresentation} or {@link AccessNodeComparator}
 * use {@link #withRepresentation(Representation)} or {@link #usingComparator(Comparator)}.
 */
public class AccessNodeAssert extends AsmAssert<AccessNodeAssert, AccessNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes an {@link AccessNodeAssert}.
   *
   * @param actual the actual {@link AccessNode}; may be null.
   */
  protected AccessNodeAssert(AccessNode actual) {
    super("Access", actual, AccessNodeAssert.class, AccessNodeRepresentation.INSTANCE, AccessNodeComparator.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public AccessNodeAssert isEqualTo(Object expected) {
    AccessNodeRepresentation accessNodeRepresentation = getAccessNodeRepresentation();

    String[] actualRepresentations = accessNodeRepresentation.toJavaSourceCodeRepresentations(actual);
    String[] expectedRepresentations = getFromObjectElse(expected, AccessNode.class, accessNodeRepresentation::toJavaSourceCodeRepresentations, new String[0]);

    Assertions.assertThat(actualRepresentations)
              .as(createCrumbDescription("Has equal access values"))
              .containsExactlyInAnyOrder(expectedRepresentations);

    return this;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private AccessNodeRepresentation getAccessNodeRepresentation() {
    Representation representation = getWritableAssertionInfo().representation();
    if (representation instanceof AccessNodeRepresentation) {
      return (AccessNodeRepresentation) representation;
    }

    return  (AccessNodeRepresentation) asmRepresentations.getAsmRepresentation(AccessNode.class);
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
