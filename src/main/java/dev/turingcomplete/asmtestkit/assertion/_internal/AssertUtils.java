package dev.turingcomplete.asmtestkit.assertion._internal;

import java.util.List;
import java.util.function.Function;

public final class AssertUtils {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  private AssertUtils() {
    throw new UnsupportedOperationException();
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  public static <T> String getStringFromObjectElseNull(T object, Function<T, String> provide) {
    return object != null ? provide.apply(object) : null;
  }

  public static <T> String getStringFromObjectElseNull(Object object, Class<T> objectType, Function<T, String> provide) {
    return getFromObjectElseNull(object, objectType, provide);
  }

  // ----------

  public static <T> Integer getIntegerFromObjectElseNull(T object, Function<T, Integer> provide) {
    return object != null ? provide.apply(object) : null;
  }

  public static <T> Integer getIntegerFromObjectElseNull(Object object, Class<T> objectType, Function<T, Integer> provide) {
    return getFromObjectElseNull(object, objectType, provide);
  }

  // ----------

  public static <T, U> U getFromObjectElseNull(Object object, Class<T> objectType, Function<T, U> provide) {
    return objectType.isInstance(object) ? provide.apply(objectType.cast(object)) : null;
  }

  public static <T, U> U getFromObjectElse(Object object, Class<T> objectType, Function<T, U> provide, U defaultObject) {
    return objectType.isInstance(object) ? provide.apply(objectType.cast(object)) : defaultObject;
  }

  // ----------

  public static <S, T> Iterable<T> getIterableFromObjectElseNull(S object, Function<S, Iterable<T>> provide) {
    return object != null ? provide.apply(object) : null;
  }

  public static <S, T> Iterable<T> getIterableFromObjectElseNull(Object object, Class<S> objectType, Function<S, Iterable<T>> provide) {
    return objectType.isInstance(object) ? provide.apply(objectType.cast(object)) : null;
  }

  public static <S, T> List<T> getListFromObjectElseNull(Object object, Class<S> objectType, Function<S, List<T>> provide) {
    return objectType.isInstance(object) ? provide.apply(objectType.cast(object)) : null;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
