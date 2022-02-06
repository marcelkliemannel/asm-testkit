package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.comparator.TryCatchBlockNodeComparator;
import dev.turingcomplete.asmtestkit.representation.TryCatchBlockNodeRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.TryCatchBlockNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Comparator;
import java.util.function.Function;

import static dev.turingcomplete.asmtestkit.asmutils.TypeUtils.nameToTypeElseNull;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getFromObjectElseNull;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getListFromObjectElseNull;

/**
 * An AssertJ {@link AbstractAssert} for a {@link TryCatchBlockNode}.
 *
 * <p>An instance can be created via {@link AsmAssertions#assertThat(TryCatchBlockNode)}.
 * Use {@link AsmAssertions#assertThatTryCatchBlocks(Iterable)} for multiple
 * {@code TryCatchBlockNode}s.
 *
 * <p>To override the used {@link TryCatchBlockNodeRepresentation} or
 * {@link TryCatchBlockNodeComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 */
public class TryCatchBlockNodeAssert extends AsmAssert<TryCatchBlockNodeAssert, TryCatchBlockNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes a {@link TryCatchBlockNodeAssert}.
   *
   * @param actual the actual {@link TryCatchBlockNode}; may be null.
   */
  protected TryCatchBlockNodeAssert(TryCatchBlockNode actual) {
    super("Try Catch Block",
          actual,
          TryCatchBlockNodeAssert.class,
          TryCatchBlockNodeRepresentation.INSTANCE,
          TryCatchBlockNodeComparator.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public TryCatchBlockNodeAssert isEqualTo(Object expected) {
    hasEqualStart(expected);
    hasEqualEnd(expected);
    hasEqualHandler(expected);
    hasEqualType(expected);
    hasEqualVisibleTypeAnnotations(expected);
    hasEqualInvisibleTypeAnnotation(expected);

    return this;
  }

  /**
   * Checks whether the start label of the given expected {@link TryCatchBlockNode}
   * is equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a {@link TryCatchBlockNode};
   *                 may be null.
   */
  protected void hasEqualStart(Object expected) {
    AsmAssertions.assertThat(getFromObjectElseNull(actual, (TryCatchBlockNode tryCatchBlockNode) -> tryCatchBlockNode.start))
                 .as(createCrumbDescription("Has equal start"))
                 .useLabelIndexLookup(labelNameLookup())
                 .isEqualTo(getFromObjectElseNull(expected, TryCatchBlockNode.class, tryCatchBlockNode -> tryCatchBlockNode.start));
  }

  /**
   * Checks whether the end label of the given expected {@link TryCatchBlockNode}
   * is equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a {@link TryCatchBlockNode};
   *                 may be null.
   */
  protected void hasEqualEnd(Object expected) {
    AsmAssertions.assertThat(getFromObjectElseNull(actual, (TryCatchBlockNode tryCatchBlockNode) -> tryCatchBlockNode.end))
                 .as(createCrumbDescription("Has equal end"))
                 .useLabelIndexLookup(labelNameLookup())
                 .isEqualTo(getFromObjectElseNull(expected, TryCatchBlockNode.class, tryCatchBlockNode -> tryCatchBlockNode.end));
  }

  /**
   * Checks whether the handler label of the given expected
   * {@link TryCatchBlockNode} is equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a {@link TryCatchBlockNode};
   *                 may be null.
   */
  protected void hasEqualHandler(Object expected) {
    AsmAssertions.assertThat(getFromObjectElseNull(actual, (TryCatchBlockNode tryCatchBlockNode) -> tryCatchBlockNode.handler))
                 .as(createCrumbDescription("Has equal handler"))
                 .useLabelIndexLookup(labelNameLookup())
                 .isEqualTo(getFromObjectElseNull(expected, TryCatchBlockNode.class, tryCatchBlockNode -> tryCatchBlockNode.handler));
  }

  /**
   * Checks whether the type of the given expected {@link TryCatchBlockNode} is
   * equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a {@link TryCatchBlockNode};
   *                 may be null.
   */
  protected void hasEqualType(Object expected) {
    Function<TryCatchBlockNode, Type> transformTypeName = (TryCatchBlockNode tryCatchBlockNode) -> {
      //noinspection CodeBlock2Expr
      return tryCatchBlockNode.type != null ? nameToTypeElseNull(tryCatchBlockNode.type) : null;
    };
    AsmAssertions.assertThat(getFromObjectElseNull(actual, transformTypeName))
                 .as(createCrumbDescription("Has equal type"))
                 .useLabelIndexLookup(labelNameLookup())
                 .isEqualTo(getFromObjectElseNull(expected, TryCatchBlockNode.class, transformTypeName));
  }

  /**
   * Checks whether the visible {@link TypeAnnotationNode}s of the given
   * expected {@link TryCatchBlockNode} are equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a {@link TryCatchBlockNode};
   *                 may be null.
   */
  protected void hasEqualVisibleTypeAnnotations(Object expected) {
    AsmAssertions.assertThatTypeAnnotations(getListFromObjectElseNull(actual, (TryCatchBlockNode tryCatchBlockNode) -> tryCatchBlockNode.visibleTypeAnnotations))
                 .as(createCrumbDescription("Has equal visible type annotations"))
                 .useLabelNameLookup(labelNameLookup())
                 .containsExactlyInAnyOrderElementsOf(getListFromObjectElseNull(expected, TryCatchBlockNode.class, (TryCatchBlockNode tryCatchBlockNode) -> tryCatchBlockNode.visibleTypeAnnotations));
  }

  /**
   * Checks whether the invisible {@link TypeAnnotationNode}s of the given
   * expected {@link TryCatchBlockNode} are equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a {@link TryCatchBlockNode};
   *                 may be null.
   */
  protected void hasEqualInvisibleTypeAnnotation(Object expected) {
    AsmAssertions.assertThatTypeAnnotations(getListFromObjectElseNull(actual, (TryCatchBlockNode tryCatchBlockNode) -> tryCatchBlockNode.invisibleTypeAnnotations))
                 .as(createCrumbDescription("Has equal visible type annotations"))
                 .useLabelNameLookup(labelNameLookup())
                 .containsExactlyInAnyOrderElementsOf(getListFromObjectElseNull(expected, TryCatchBlockNode.class, (TryCatchBlockNode tryCatchBlockNode) -> tryCatchBlockNode.invisibleTypeAnnotations));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
