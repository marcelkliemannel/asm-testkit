package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.assertion.__helper.TypeParameterAnnotation;
import org.assertj.core.api.Assertions;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;

import static dev.turingcomplete.asmtestkit.assertion.representation.TypeAnnotationNodeRepresentation.INSTANCE;
import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;

class TypeAnnotationNodeRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCreateRepresentation() throws IOException {
    @Language("Java")
    String myClass = "import java.util.Comparator;" +
                     "import dev.turingcomplete.asmtestkit.assertion.__helper.TypeParameterAnnotation;" +
                     "abstract class MyClass<T> extends @TypeParameterAnnotation Thread {" +
                     "  T[]@TypeParameterAnnotation [] myField;" +
                     "}";

    ClassNode myClassNode = create()
            .addToClasspath(TypeParameterAnnotation.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass");

    Assertions.assertThat(INSTANCE.toStringOf(myClassNode.visibleTypeAnnotations.get(0)))
              .isEqualTo("@dev.turingcomplete.asmtestkit.assertion.__helper.TypeParameterAnnotation {reference: class_extends=-1; path: null}");

    Assertions.assertThat(INSTANCE.toStringOf(myClassNode.fields.get(0).visibleTypeAnnotations.get(0)))
              .isEqualTo("@dev.turingcomplete.asmtestkit.assertion.__helper.TypeParameterAnnotation {reference: field; path: [}");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
