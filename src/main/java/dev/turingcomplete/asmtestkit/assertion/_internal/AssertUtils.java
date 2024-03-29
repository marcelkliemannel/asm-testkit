package dev.turingcomplete.asmtestkit.assertion._internal;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public final class AssertUtils {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  private AssertUtils() {
    throw new UnsupportedOperationException();
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  public static <T> void ifNotNull(T object, Consumer<T> task) {
    if (object != null) {
      task.accept(object);
    }
  }

  public static <T> T getIfIndexExists(List<T> list, int index) {
    if (index < list.size()) {
      return list.get(index);
    }
    else {
      return null;
    }
  }

  // ----------

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

  public static <T, U> U getFromObjectElseNull(T object, Function<T, U> provide) {
    return object != null ? provide.apply(object) : null;
  }

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

  public static <S, T> List<T> getListFromObjectElseNull(S object, Function<S, List<T>> provide) {
    return object != null ? provide.apply(object) : null;
  }

  public static <S, T> List<T> getListFromObjectElse(S object, Function<S, List<T>> provide, List<T> defaultObject) {
    if (object != null) {
      List<T> value = provide.apply(object);
      if (value != null) {
        return value;
      }
    }

    return  defaultObject;
  }

  public static <S, T> List<T> getListFromObjectElseNull(Object object, Class<S> objectType, Function<S, List<T>> provide) {
    return objectType.isInstance(object) ? provide.apply(objectType.cast(object)) : null;
  }

  public static <S, T> List<T> getListFromObjectElse(Object object, Class<S> objectType, Function<S, List<T>> provide, List<T> defaultObject) {
    if (objectType.isInstance(object)) {
      List<T> value = provide.apply(objectType.cast(object));
      if (value != null) {
        return value;
      }
    }

    return  defaultObject;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
