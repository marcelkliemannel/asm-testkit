package dev.turingcomplete.asmtestkit.representation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.objectweb.asm.tree.InnerClassNode;

import java.io.IOException;
import java.util.stream.Stream;

import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;

public class InnerClassNodeRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @ParameterizedTest
  @MethodSource("testToStringOfArguments")
  void testToStringOf(String innerSource, String expected) throws IOException {
    InnerClassNode innerClassNode = create()
            .addJavaInputSource("class MyClass { " + innerSource + " }")
            .compile()
            .readClassNode("MyClass")
            .innerClasses.get(0);

    Assertions.assertThat(InnerClassNodeRepresentation.INSTANCE.toStringOf(innerClassNode))
              .isEqualTo(expected);
  }

  private static Stream<Arguments> testToStringOfArguments() {
    return Stream.of(Arguments.of("private class InnerClass {}", "[2: private] MyClass$InnerClass // outer name: MyClass // inner name: InnerClass"),
                     Arguments.of("void myMethod() { class AnonymousClass {} }", "[0] MyClass$1AnonymousClass // outer name: null // inner name: AnonymousClass"));
  }

  @Test
  void testToStringOfNullValues() {
    var first = new InnerClassNode(null, null, null, 0);

    Assertions.assertThat(InnerClassNodeRepresentation.INSTANCE.toStringOf(first))
              .isEqualTo("[0] null // outer name: null // inner name: null");
  }

  @ParameterizedTest
  @MethodSource("testToSimplifiedStringOfArguments")
  void testToSimplifiedStringOf(String innerSource, String expected) throws IOException {
    InnerClassNode innerClassNode = create()
            .addJavaInputSource("class MyClass { " + innerSource + " }")
            .compile()
            .readClassNode("MyClass")
            .innerClasses.get(0);

    Assertions.assertThat(InnerClassNodeRepresentation.INSTANCE.toSimplifiedStringOf(innerClassNode))
              .isEqualTo(expected);
  }

  private static Stream<Arguments> testToSimplifiedStringOfArguments() {
    return Stream.of(Arguments.of("private class InnerClass {}", "MyClass$InnerClass"),
                     Arguments.of("void myMethod() { class AnonymousClass {} }", "MyClass$1AnonymousClass"));
  }

  @Test
  void testToSimplifiedStringOfNullValues() {
    var first = new InnerClassNode(null, null, null, 0);

    Assertions.assertThat(InnerClassNodeRepresentation.INSTANCE.toSimplifiedStringOf(first))
              .isEqualTo(null);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
