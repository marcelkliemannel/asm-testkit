package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.node.AccessFlags;
import dev.turingcomplete.asmtestkit.assertion.option.StandardAssertOption;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatAnnotations;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatAttributes;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatTypeAnnotations;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getFromObjectElseNull;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getListFromObjectElse;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getStringFromObjectElseNull;
import static org.assertj.core.api.Assertions.assertThat;

abstract class ClassEntityAssert<S extends AbstractAssert<S, A>, A> extends AsmAssert<S, A> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final Class<A> objectType;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes a {@link ClassEntityAssert}.
   *
   * @param name                  a short, generic {@link String} name; never null.
   * @param actual                the actual {@link A}; may be null.
   * @param selfType              the {@link Class} of {@code this}; never null.
   * @param objectType            the {@link Class} of {@code actual); never null.
   * @param defaultRepresentation the default {@link Representation}; may be null.
   * @param defaultComparator     the default {@link Comparator} for {@link A};
   *                              may be null.
   */
  protected ClassEntityAssert(String name,
                              A actual,
                              Class<?> selfType,
                              Class<A> objectType,
                              Representation defaultRepresentation,
                              Comparator<A> defaultComparator) {

    super(name, actual, selfType, defaultRepresentation, defaultComparator);

    this.objectType = Objects.requireNonNull(objectType);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public S isEqualTo(Object expected) {
    hasEqualName(expected);
    hasEqualAccess(expected);
    hasEqualSignature(expected);
    hasEqualVisibleAnnotations(expected);
    hasEqualInvisibleAnnotations(expected);
    hasEqualVisibleTypeAnnotations(expected);
    hasEqualInvisibleTypeAnnotation(expected);
    hasEqualAttributes(expected);

    //noinspection unchecked
    return (S) this;
  }

  /**
   * Checks whether the name of the given expected {@link A} is equal to the
   * actual one.
   *
   * @param expected an {@link Object} expected to be a {@link A}; may be null.
   */
  protected void hasEqualName(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_NAME)) {
      return;
    }

    assertThat(getStringFromObjectElseNull(actual, this::getName))
            .as(createCrumbDescription("Has equal " + name.toLowerCase(Locale.ROOT) + " name"))
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
  protected void hasEqualAccess(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_ACCESS)) {
      return;
    }

    AsmAssertions.assertThat(getFromObjectElseNull(actual, this::getAccessFlags))
                 .addOptions(options)
                 .as(createCrumbDescription("Has equal " + name.toLowerCase(Locale.ROOT) + " access"))
                 .isEqualTo(getFromObjectElseNull(expected, objectType, this::getAccessFlags));
  }

  /**
   * Gets the {@link AccessFlags} of the given {@code entity}.
   *
   * @param entity the {@link A} entity; never null.
   * @return the access as {@link Integer}; may be null.
   */
  protected abstract AccessFlags getAccessFlags(A entity);

  /**
   * Checks whether the signature of the given expected {@link A} is
   * equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a {@link A}; may be null.
   */
  protected void hasEqualSignature(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_SIGNATURE)) {
      return;
    }

    assertThat(getStringFromObjectElseNull(actual, this::getSignature))
            .as(createCrumbDescription("Has equal " + name.toLowerCase(Locale.ROOT) + " signature"))
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
  protected void hasEqualVisibleAnnotations(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_VISIBLE_ANNOTATIONS)) {
      return;
    }

    assertThatAnnotations(getListFromObjectElse(actual, this::getVisibleAnnotations, List.of()))
            .as(createCrumbDescription("Has equal " + name.toLowerCase(Locale.ROOT) + " visible annotations"))
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
  protected void hasEqualInvisibleAnnotations(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_INVISIBLE_ANNOTATIONS)) {
      return;
    }

    assertThatAnnotations(getListFromObjectElse(actual, this::getInvisibleAnnotations, List.of()))
            .as(createCrumbDescription("Has equal " + name.toLowerCase(Locale.ROOT) + " invisible annotations"))
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
  protected void hasEqualVisibleTypeAnnotations(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_VISIBLE_TYPE_ANNOTATIONS)) {
      return;
    }

    assertThatTypeAnnotations(getListFromObjectElse(actual, this::getVisibleTypeAnnotations, List.of()))
            .as(createCrumbDescription("Has equal " + name.toLowerCase(Locale.ROOT) + " visible type annotations"))
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
  protected void hasEqualInvisibleTypeAnnotation(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_INVISIBLE_TYPE_ANNOTATIONS)) {
      return;
    }

    assertThatTypeAnnotations(getListFromObjectElse(actual, this::getInvisibleTypeAnnotations, List.of()))
            .as(createCrumbDescription("Has equal " + name.toLowerCase(Locale.ROOT) + " invisible type annotations"))
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
  protected void hasEqualAttributes(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_ATTRIBUTES)) {
      return;
    }

    assertThatAttributes(getListFromObjectElse(actual, this::getAttributes, List.of()))
            .as(createCrumbDescription("Has equal " + name.toLowerCase(Locale.ROOT) + " attributes"))
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
