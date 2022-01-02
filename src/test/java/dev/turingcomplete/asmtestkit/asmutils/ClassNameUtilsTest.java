package dev.turingcomplete.asmtestkit.asmutils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ClassNameUtilsTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @ParameterizedTest
  @CsvSource({"MyClass,''",
              "foo.MyClass,foo",
              "foo.bar.MyClass,foo.bar",
              "'',''"})
  void testGetPackage(String actual, String expected) {
    assertThat(ClassNameUtils.getPackage(actual))
            .isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource({"MyClass,MyClass",
              "foo.MyClass,MyClass",
              "foo.bar.MyClass,MyClass",
              "'',''"})
  void testGetSimpleName(String actual, String expected) {
    assertThat(ClassNameUtils.getSimpleName(actual))
            .isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource({"MyClass,MyClass",
              "foo/MyClass,foo.MyClass",
              "foo/bar/MyClass,foo.bar.MyClass",
              "'',''"})
  void testToClassName(String actual, String expected) {
    assertThat(ClassNameUtils.toClassName(actual))
            .isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource({"MyClass,MyClass",
              "foo.MyClass,foo/MyClass",
              "foo.bar.MyClass,foo/bar/MyClass",
              "'',''"})
  void testToInternalName(String actual, String expected) {
    assertThat(ClassNameUtils.toInternalName(actual))
            .isEqualTo(expected);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
