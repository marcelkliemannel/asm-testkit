package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.option.AssertOption;
import dev.turingcomplete.asmtestkit.assertion.representation.AsmRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.description.Description;
import org.assertj.core.description.TextDescription;
import org.assertj.core.presentation.Representation;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The base class for all ASM related assertions.
 *
 * <p>An implementation should always set a {@link Comparator} regardless of
 * whether individual {@link AbstractAssert} methods were overridden. This
 * ensures that the {@code AbstractAssert} methods that are not overridden work
 * correctly.
 *
 * @param <S> the 'self' type of {@code this} {@link AbstractAssert}}.
 * @param <A> the type of the actual object.
 */
public abstract class AsmAssert<S extends AbstractAssert<S, A>, A>
        extends AbstractAssert<S, A> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  protected final String            name;
  protected final Set<AssertOption> options = new HashSet<>();

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes an {@link AsmAssert}.
   *
   * @param name                  a short, generic {@link String} name; never null.
   * @param actual                the actual {@link A}; may be null.
   * @param selfType              the {@link Class} of {@code this}; never null.
   * @param defaultRepresentation the default {@link Representation}; may be null.
   * @param defaultComparator     the default {@link Comparator}; may be null.
   */
  protected AsmAssert(String name,
                      A actual,
                      Class<?> selfType,
                      Representation defaultRepresentation,
                      Comparator<A> defaultComparator) {

    super(actual, Objects.requireNonNull(selfType));

    this.name = name;

    if (defaultRepresentation != null) {
      info.useRepresentation(defaultRepresentation);
    }
    info.description(createSelfDescription(actual));

    if (defaultComparator != null) {
      //noinspection ResultOfMethodCallIgnored
      usingComparator(defaultComparator);
    }
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Adds the given {@link AssertOption}.
   *
   * @param option an {@link AssertOption} to add; never null.
   * @return {@code this} {@link AsmAssert}; never null.
   */
  public AsmAssert<S, A> addOption(AssertOption option) {
    this.options.add(Objects.requireNonNull(option));
    return this;
  }

  /**
   * Adds the given {@link AssertOption}s.
   *
   * @param options a {@link Collection} of {@link AssertOption}s to add;
   *                never null.
   * @return {@code this} {@link AsmAssert}; never null.
   */
  public AsmAssert<S, A> addOptions(Collection<AssertOption> options) {
    this.options.addAll(Objects.requireNonNull(options));
    return this;
  }

  /**
   * Check whether the given {@link AssertOption} was set.
   *
   * @param option the {@link AssertOption} to check; never null.
   * @return if the {@link AssertOption} was set.
   */
  public boolean hasOption(AssertOption option) {
    return options.contains(Objects.requireNonNull(option));
  }

  protected Description createDescription(Description description) {
    return createDescription(description.value());
  }

  protected Description createDescription() {
    return createDescription(null, new Object[0]);
  }

  protected String createSelfDescription(A actual) {
    Representation representation = info.representation();
    String representationOfActual = representation instanceof AsmRepresentation
            ? ((AsmRepresentation<?>) representation).toSimplifiedStringOf(actual)
            : representation.toStringOf(actual);
    return name + ": " + representationOfActual;
  }

  protected Description createDescription(String description, Object... args) {
    String selfDescription = descriptionText();
    if (!selfDescription.equals(createSelfDescription(actual))) {
      selfDescription = selfDescription + " > " + selfDescription;
    }

    return description != null
            ? new TextDescription("%s > %s", selfDescription, String.format(description, args))
            : new TextDescription(selfDescription);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
