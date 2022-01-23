package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion._internal.AsmWritableAssertionInfo;
import dev.turingcomplete.asmtestkit.assertion.comparator.WithLabelNamesAsmComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator._internal.WithLabelNamesAsmComparatorAdapter;
import dev.turingcomplete.asmtestkit.assertion.option.AssertOption;
import dev.turingcomplete.asmtestkit.assertion.representation.AsmRepresentations;
import dev.turingcomplete.asmtestkit.assertion.representation.DefaultAsmRepresentations;
import dev.turingcomplete.asmtestkit.assertion.representation._internal.CrumbDescription;
import dev.turingcomplete.asmtestkit.assertion.representation._internal.SelfDescription;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.description.Description;
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

  protected final String             name;
  protected final Set<AssertOption>  options            = new HashSet<>();
  protected       AsmRepresentations asmRepresentations = DefaultAsmRepresentations.INSTANCE;

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

    info = new AsmWritableAssertionInfo(defaultRepresentation);
    info.description(new SelfDescription(name, actual, info));

    if (defaultComparator != null) {
      //noinspection ResultOfMethodCallIgnored
      usingComparator(defaultComparator);
    }
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Sets the used {@link AsmRepresentations}.
   *
   * <p>The default value is {@link DefaultAsmRepresentations#INSTANCE}.
   *
   * @param asmRepresentations an {@link AsmRepresentations};
   *                           never null.
   * @return {@code this} {@link S}; never null.
   */
  public S useAsmRepresentations(AsmRepresentations asmRepresentations) {
    this.asmRepresentations = Objects.requireNonNull(asmRepresentations);

    //noinspection unchecked
    return (S) this;
  }

  @Override
  public AsmWritableAssertionInfo getWritableAssertionInfo() {
    if (!(info instanceof AsmWritableAssertionInfo)) {
      throw new IllegalStateException("Expected info to be of type:" + AsmWritableAssertionInfo.class.getName());
    }

    return (AsmWritableAssertionInfo) info;
  }

  /**
   * Sets the given {@link LabelNameLookup} to look up known label names.
   *
   * @param labelNameLookup a {@link LabelNameLookup} to set; never null.
   * @return {@code this} {@link S}; never null.
   * @see #labelNameLookup()
   */
  public S useLabelNameLookup(LabelNameLookup labelNameLookup) {
    getWritableAssertionInfo().useLabelNameLookup(Objects.requireNonNull(labelNameLookup));

    //noinspection unchecked
    return (S) this;
  }

  /**
   * Gets the current {@link LabelNameLookup} to look up known label names.
   *
   * @return the current {@link LabelNameLookup}; never null.
   * @see #labelNameLookup()
   */
  public LabelNameLookup labelNameLookup() {
    return getWritableAssertionInfo().labelNameLookup();
  }

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

  protected final Description createCrumbDescription(String description, Object... args) {
    return new CrumbDescription(info, description, args);
  }

  @Override
  public S usingComparator(Comparator<? super A> customComparator) {
    return usingComparator(customComparator, null);
  }

  @Override
  public S usingComparator(Comparator<? super A> customComparator, String customComparatorDescription) {
    Comparator<? super A> _customComparator = customComparator;
    if (customComparator instanceof WithLabelNamesAsmComparator) {
      _customComparator = WithLabelNamesAsmComparatorAdapter.wrapIfNeeded(customComparator, getWritableAssertionInfo().labelNameLookup());
    }

    return super.usingComparator(_customComparator, customComparatorDescription);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
