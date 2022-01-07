package dev.turingcomplete.asmtestkit.assertion.representation;

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
  void testStringRepresentation() throws IOException {
    @Language("Java")
    String typeParameterAnnotation = "import java.lang.annotation.*;" +
                                     "@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})\n" +
                                     "@Retention(RetentionPolicy.RUNTIME)\n" +
                                     "@interface TypeParameterAnnotation { }";
    @Language("Java")
    String myClass = "import java.util.Comparator;" +
                     "abstract class MyClass<T> extends @TypeParameterAnnotation Thread {" +
                     "  T[]@TypeParameterAnnotation [] myField;" +
                     "}";

    ClassNode myClassNode = create()
            .addJavaInputSource(typeParameterAnnotation)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass");

    Assertions.assertThat(INSTANCE.toStringOf(myClassNode.visibleTypeAnnotations.get(0)))
              .isEqualTo("@TypeParameterAnnotation {reference: class_extends=-1; path: null}");

    Assertions.assertThat(INSTANCE.toStringOf(myClassNode.fields.get(0).visibleTypeAnnotations.get(0)))
              .isEqualTo("@TypeParameterAnnotation {reference: field; path: [}");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
