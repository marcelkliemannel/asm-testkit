package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA;
import org.assertj.core.api.Assertions;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;

import static dev.turingcomplete.asmtestkit.assertion.representation.TypeAnnotationNodeRepresentation.INSTANCE;
import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;

class TypeAnnotationNodeRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  @Language("Java")
  private static final String MY_CLASS = "import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA;" +
                                         "abstract class MyClass<T> extends @VisibleTypeParameterAnnotationA Thread {" +
                                         "  T[]@VisibleTypeParameterAnnotationA [] myField;" +
                                         "}";

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testToStringOf() throws IOException {
    ClassNode myClassNode = create()
            .addToClasspath(VisibleTypeParameterAnnotationA.class)
            .addJavaInputSource(MY_CLASS)
            .compile()
            .readClassNode("MyClass");

    Assertions.assertThat(INSTANCE.toStringOf(myClassNode.visibleTypeAnnotations.get(0)))
              .isEqualTo("@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA // reference: class_extends=-1; path: null");

    Assertions.assertThat(INSTANCE.toStringOf(myClassNode.fields.get(0).visibleTypeAnnotations.get(0)))
              .isEqualTo("@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA // reference: field; path: [");
  }

  @Test
  void testToSimplifiedStringOf() throws IOException {
    ClassNode myClassNode = create()
            .addToClasspath(VisibleTypeParameterAnnotationA.class)
            .addJavaInputSource(MY_CLASS)
            .compile()
            .readClassNode("MyClass");

    Assertions.assertThat(INSTANCE.doToSimplifiedStringOf(myClassNode.visibleTypeAnnotations.get(0)))
              .isEqualTo("@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA");

    Assertions.assertThat(INSTANCE.doToSimplifiedStringOf(myClassNode.fields.get(0).visibleTypeAnnotations.get(0)))
              .isEqualTo("@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
