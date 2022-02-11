package dev.turingcomplete.asmtestkit.assertion.option;

import java.util.Collection;

public interface AssertOptionCapable<S> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Adds the given {@link AssertOption}.
   *
   * @param option an {@link AssertOption} to add; never null.
   * @return {@code this} {@link S}; never null.
   */
  S addOption(AssertOption option);

  /**
   * Adds the given {@link AssertOption}s.
   *
   * @param options a {@link Collection} of {@link AssertOption}s to add;
   *                never null.
   * @return {@code this} {@link S}; never null.
   */
  S addOptions(Collection<AssertOption> options);

  /**
   * Check whether the given {@link AssertOption} was set.
   *
   * @param option the {@link AssertOption} to check; never null.
   * @return if the {@link AssertOption} was set.
   */
  boolean hasOption(AssertOption option);

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
