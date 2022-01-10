package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.option.AssertOption;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.description.Description;
import org.assertj.core.description.TextDescription;
import org.objectweb.asm.Opcodes;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class AsmAssert<S extends AbstractAssert<S, A>, A>
        extends AbstractAssert<S, A> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  protected final String            selfDescription;
  protected final Class<A>          objectType;
  protected final Set<AssertOption> options = new HashSet<>();
  protected       int               asmApi  = Opcodes.ASM9;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public AsmAssert(A actual,
                   Class<?> selfType,
                   Class<A> objectType,
                   String selfDescription,
                   AssertOption... assertOptions) {

    super(actual, selfType);

    this.objectType = objectType;
    this.selfDescription = selfDescription;
    info.description(this.selfDescription);
    this.options.addAll(Arrays.asList(assertOptions));
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  public AsmAssert<S, A> useAsmApi(int asmApi) {
    this.asmApi = asmApi;

    return this;
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
   *               never null.
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

  protected Description createDescription(String description, Object... args) {
    String selfDescription = descriptionText();
    if (!selfDescription.equals(this.selfDescription)) {
      selfDescription = selfDescription + " > " + selfDescription;
    }

    return description != null
            ? new TextDescription("%s > %s", selfDescription, String.format(description, args))
            : new TextDescription(selfDescription);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
