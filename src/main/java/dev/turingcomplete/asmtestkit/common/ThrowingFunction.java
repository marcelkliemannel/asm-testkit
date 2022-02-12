package dev.turingcomplete.asmtestkit.common;

import java.util.function.Function;

/**
 * A {@link extends} that deals with checked exceptions by rethrowing them as
 * {@link RuntimeException}.
 *
 * @param <T> the type of the input parameter;
 * @param <R> the type of the returned value;
 */
@FunctionalInterface
public interface ThrowingFunction<T, R> extends Function<T, R> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  default R apply(T input) {
    try {
      return applyThrows(input);
    }
    catch (final RuntimeException | AssertionError e) {
      throw e;
    }
    catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }


  R applyThrows(T input) throws Throwable;

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
