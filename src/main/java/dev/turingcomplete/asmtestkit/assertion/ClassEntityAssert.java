package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.asmutils.AccessKind;
import dev.turingcomplete.asmtestkit.assertion.option.AssertOption;
import dev.turingcomplete.asmtestkit.assertion.option.StandardAssertOption;
import org.assertj.core.api.AbstractAssert;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.List;

import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatAccess;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatAnnotationNodes;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatAttributes;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatTypeAnnotationNodes;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getIntegerFromObjectElseNull;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getListFromObjectElse;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getStringFromObjectElseNull;
import static org.assertj.core.api.Assertions.assertThat;

abstract class ClassEntityAssert<S extends AbstractAssert<S, A>, A> extends AsmAssert<S, A> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final String entityName;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected ClassEntityAssert(String entityName,
                              A actual,
                              Class<?> selfType,
                              Class<A> objectType,
                              String selfDescription,
                              AssertOption... assertOptions) {

    super(actual, selfType, objectType, selfDescription, assertOptions);

    this.entityName = entityName;
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public S isEqualTo(Object expected) {
    isEqualName(expected);
    isEqualAccess(expected);
    isEqualSignature(expected);
    isEqualVisibleAnnotations(expected);
    isEqualInvisibleAnnotations(expected);
    isEqualVisibleTypeAnnotations(expected);
    isEqualInvisibleTypeAnnotationNodes(expected);
    isEqualAttributes(expected);

    //noinspection unchecked
    return (S) this;
  }

  /**
   * Checks whether the name of the given expected {@link A} is equal to the
   * actual one.
   *
   * @param expected an {@link Object} expected to be a {@link A}; may be null.
   */
  protected void isEqualName(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_NAME)) {
      return;
    }

    assertThat(getStringFromObjectElseNull(actual, this::getName))
            .as(createDescription("Is equal " + entityName + " name"))
            .isEqualTo(getStringFromObjectElseNull(expected, objectType, this::getName));
  }

  /**
   * Gets the name of the given {@code entity}.
   *
   * @param entity the {@link A}; never null.
   * @return the name as {@link String}; may be null.
   */
  protected abstract String getName(A entity);

  /**
   * Checks whether the access flags of the given expected {@link A} is equal to
   * the actual one.
   *
   * @param expected an {@link Object} expected to be a {@link A}; may be null.
   */
  protected void isEqualAccess(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_ACCESS)) {
      return;
    }

    assertThatAccess(getIntegerFromObjectElseNull(actual, this::getAccess), getAccessKind())
            .addOptions(options)
            .as(createDescription("Is equal " + entityName + " access"))
            .isEqualTo(getIntegerFromObjectElseNull(expected, objectType, this::getAccess));
  }

  /**
   * Gets the access of the given {@code entity}.
   *
   * @param entity the {@link A} entity; never null.
   * @return the access as {@link Integer}; may be null.
   */
  protected abstract Integer getAccess(A entity);

  /**
   * Gets the {@link AccessKind} of {@link #getAccess(Object)}.
   *
   * @return the {@link AccessKind}; never null.
   */
  protected abstract AccessKind getAccessKind();

  /**
   * Checks whether the signature of the given expected {@link A} is
   * equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a {@link A}; may be null.
   */
  protected void isEqualSignature(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_SIGNATURE)) {
      return;
    }

    assertThat(getStringFromObjectElseNull(actual, this::getSignature))
            .as(createDescription("Is equal " + entityName + " signature"))
            .isEqualTo(getStringFromObjectElseNull(expected, objectType, this::getSignature));
  }

  /**
   * Gets the signature of the given {@code entity}.
   *
   * @param entity the {@link A} entity; never null.
   * @return the signature as {@link String}; may be null.
   */
  protected abstract String getSignature(A entity);

  /**
   * Checks whether the visible {@link AnnotationNode}s of the given expected
   * {@link A} are equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a {@link A}; may be null.
   */
  protected void isEqualVisibleAnnotations(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_VISIBLE_ANNOTATIONS)) {
      return;
    }

    assertThatAnnotationNodes(getListFromObjectElse(actual, this::getVisibleAnnotations, List.of()))
            .as(createDescription("Is equal " + entityName + " visible annotations"))
            .containsExactlyInAnyOrderElementsOf(getListFromObjectElse(expected, objectType, this::getVisibleAnnotations, List.of()));
  }

  /**
   * Gets the visible annotations of the given {@code entity}.
   *
   * @param entity the {@link A} entity; never null.
   * @return a {@link List} of {@link AnnotationNode}s; may be null.
   */
  protected abstract List<AnnotationNode> getVisibleAnnotations(A entity);

  /**
   * Checks whether the invisible {@link AnnotationNode}s of the given expected
   * {@link A} are equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a {@link A}; may be null.
   */
  protected void isEqualInvisibleAnnotations(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_INVISIBLE_ANNOTATIONS)) {
      return;
    }

    assertThatAnnotationNodes(getListFromObjectElse(actual, this::getInvisibleAnnotations, List.of()))
            .as(createDescription("Is equal " + entityName + " invisible annotations"))
            .containsExactlyInAnyOrderElementsOf(getListFromObjectElse(expected, objectType, this::getInvisibleAnnotations, List.of()));
  }

  /**
   * Gets the invisible annotations of the given {@code entity}.
   *
   * @param entity the {@link A} entity; never null.
   * @return a {@link List} of {@link AnnotationNode}s; may be null.
   */
  protected abstract List<AnnotationNode> getInvisibleAnnotations(A entity);

  /**
   * Checks whether the visible {@link TypeAnnotationNode}s of the given
   * expected {@link A} are equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a {@link A}; may be null.
   */
  protected void isEqualVisibleTypeAnnotations(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_VISIBLE_TYPE_ANNOTATIONS)) {
      return;
    }

    assertThatTypeAnnotationNodes(getListFromObjectElse(actual, this::getVisibleTypeAnnotations, List.of()))
            .as(createDescription("Is equal " + entityName + " visible type annotations"))
            .containsExactlyInAnyOrderElementsOf(getListFromObjectElse(expected, objectType, this::getVisibleTypeAnnotations, List.of()));
  }

  /**
   * Gets the visible type annotations of the given {@code entity}.
   *
   * @param entity the {@link A} entity; never null.
   * @return a {@link List} of {@link TypeAnnotationNode}s; may be null.
   */
  protected abstract List<TypeAnnotationNode> getVisibleTypeAnnotations(A entity);

  /**
   * Checks whether the invisible {@link TypeAnnotationNode}s of the given
   * expected {@link A} are equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a {@link A}; may be null.
   */
  protected void isEqualInvisibleTypeAnnotationNodes(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_INVISIBLE_TYPE_ANNOTATIONS)) {
      return;
    }

    assertThatTypeAnnotationNodes(getListFromObjectElse(actual, this::getInvisibleTypeAnnotations, List.of()))
            .as(createDescription("Is equal " + entityName + " invisible type annotations"))
            .containsExactlyInAnyOrderElementsOf(getListFromObjectElse(expected, objectType, this::getInvisibleTypeAnnotations, List.of()));
  }

  /**
   * Gets the invisible type annotations of the given {@code entity}.
   *
   * @param entity the {@link A} entity; never null.
   * @return a {@link List} of {@link TypeAnnotationNode}s; may be null.
   */
  protected abstract List<TypeAnnotationNode> getInvisibleTypeAnnotations(A entity);

  /**
   * Checks whether the {@link Attribute}s of the given expected
   * {@link A} are equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a {@link A}; may be null.
   */
  protected void isEqualAttributes(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_ATTRIBUTES)) {
      return;
    }

    assertThatAttributes(getListFromObjectElse(actual, this::getAttributes, List.of()))
            .as(createDescription("Is equal " + entityName + " attributes"))
            .containsExactlyInAnyOrderElementsOf(getListFromObjectElse(expected, objectType, this::getAttributes, List.of()));
  }

  /**
   * Gets the attributes of the given {@code entity}.
   *
   * @param entity the {@link A} entity; never null.
   * @return a {@link List} of {@link Attribute}s; may be null.
   */
  protected abstract List<Attribute> getAttributes(A entity);

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
