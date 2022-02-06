package dev.turingcomplete.asmtestkit.asmutils;

import java.util.Objects;

public final class ClassNameUtils {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  private ClassNameUtils() {
    throw new UnsupportedOperationException();
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Gets the package part of the given class name.
   *
   * <p>For example, returns {@code foo} for the input {@code foo.MyClass}.
   *
   * @param className a {@link String} with the class name (in fully qualified
   *                  or integral format); never null.
   * @return the package part of the given class name or an empty {@link String}
   * if the class name does not have a package; never null.
   */
  public static String getPackage(String className) {
    className = toClassName(Objects.requireNonNull(className));
    int lastSeparatorIndex = className.lastIndexOf('.');
    return lastSeparatorIndex >= 0 ? className.substring(0, lastSeparatorIndex) : "";
  }

  /**
   * Gets the simple name of the given class name.
   *
   * <p>For example, returns {@code MyClass} for the input {@code foo.MyClass}.
   *
   * @param className a {@link String} with the class name (in fully qualified
   *                  or internal format); never null.
   * @return the simple name of the given class name; never null.
   */
  public static String getSimpleName(String className) {
    className = toClassName(Objects.requireNonNull(className));
    int lastSeparatorIndex = className.lastIndexOf('.');
    return lastSeparatorIndex >= 0 ? className.substring(lastSeparatorIndex + 1) : className;
  }

  /**
   * Converts the given fully qualified class name ('dot' format) to an internal
   * name ('slash' format).
   *
   * <p>For example, returns {@code foo/bar/MyClass} for the input
   * {@code foo.bar.MyClass}.
   *
   * @param className a {@link String} with the class name; never null.
   * @return the internal name of the given class name; never null.
   */
  public static String toInternalName(String className) {
    return Objects.requireNonNull(className).replace('.', '/');
  }

  /**
   * Gets the internal name of the given {@link Class}.
   *
   * <p>For example, returns {@code java/lang/String} for {@link Class}.
   *
   * @param aClass a {@link Class}; never null.
   * @return the internal name of the given class; never null.
   */
  public static String toInternalName(Class<?> aClass) {
    return Objects.requireNonNull(aClass).getName().replace('.', '/');
  }

  /**
   * Converts the given internal name ('slash' format) to a fully qualified
   * name ('dot' format).
   *
   * <p>For example, returns {@code foo.bar.MyClass} for the input
   * {@code foo/bar/MyClass}.
   *
   * @param internalName a {@link String} with the internal name; never null.
   * @return the class name of the given internal name; never null.
   */
  public static String toClassName(String internalName) {
    return Objects.requireNonNull(internalName).replace('/', '.');
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
