package dev.turingcomplete.asmtestkit.representation;

import dev.turingcomplete.asmtestkit.__helper.InvisibleAnnotationA;
import dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.objectweb.asm.tree.FieldNode;

import java.io.IOException;
import java.util.stream.Stream;

import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;
import static dev.turingcomplete.asmtestkit.representation.FieldNodeRepresentation.INSTANCE;

class FieldNodeRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @ParameterizedTest
  @MethodSource("testToStringOfArguments")
  void testToStringOf(String methodSource, String expected) throws IOException {
    FieldNode fieldNode = create()
            .addToClasspath(VisibleTypeParameterAnnotationA.class)
            .addToClasspath(InvisibleAnnotationA.class)
            .addJavaInputSource( "import dev.turingcomplete.asmtestkit.__helper.InvisibleAnnotationA;" +
                                 "import dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA;" +
                                 "class MyClass<T> {" + methodSource  + "}")
            .compile()
            .readClassNode("MyClass")
            .fields.get(0);

    Assertions.assertThat(INSTANCE.toStringOf(fieldNode))
              .isEqualTo(expected);
  }

  private static Stream<Arguments> testToStringOfArguments() {
    return Stream.of(Arguments.of("@InvisibleAnnotationA\nprivate T[]@VisibleTypeParameterAnnotationA [] myField;",
                                  "@dev.turingcomplete.asmtestkit.__helper.InvisibleAnnotationA // invisible\n" +
                                  "@dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA // reference: field; path: [\n" +
                                  "[2: private] java.lang.Object[][] myField // signature: [[TT;",
                                  "[2: private] java.lang.Object[][] myField"),
                     Arguments.of("@Deprecated(forRemoval = true)\npublic static final int myField2 = 5;",
                                  "@java.lang.Deprecated(forRemoval=true)\n" +
                                  "[131097: public, static, final, deprecated] int myField2 = 5",
                                  "[131097: public. static, final, deprecated] int myField2"),
                     Arguments.of("String myField3;",
                                  "[0] java.lang.String myField3",
                                  "[0] java.lang.String myField3"));
  }

  @ParameterizedTest
  @MethodSource("testToSimplifiedStringOfArguments")
  void testToSimplifiedStringOf(String methodSource, String expected) throws IOException {
    FieldNode fieldNode = create()
            .addToClasspath(VisibleTypeParameterAnnotationA.class)
            .addToClasspath(InvisibleAnnotationA.class)
            .addJavaInputSource( "import dev.turingcomplete.asmtestkit.__helper.InvisibleAnnotationA;" +
                                 "import dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA;" +
                                 "class MyClass<T> {" + methodSource  + "}")
            .compile()
            .readClassNode("MyClass")
            .fields.get(0);

    Assertions.assertThat(INSTANCE.toSimplifiedStringOf(fieldNode))
              .isEqualTo(expected);
  }

  private static Stream<Arguments> testToSimplifiedStringOfArguments() {
    return Stream.of(Arguments.of("@InvisibleAnnotationA\nprivate T[]@VisibleTypeParameterAnnotationA [] myField;",
                                  "[2: private] java.lang.Object[][] myField"),
                     Arguments.of("@Deprecated(forRemoval = true)\npublic static final int myField2 = 5;",
                                  "[131097: public, static, final, deprecated] int myField2"),
                     Arguments.of("String myField3;",
                                  "[0] java.lang.String myField3"));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
