package dev.turingcomplete.asmtestkit.assertion;

import org.assertj.core.api.Assertions;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.io.IOException;

import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;

class TypeAnnotationNodeAssertTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testIsEqualTo() throws IOException {
    @Language("Java")
    String typeParameterAnnotation = "import java.lang.annotation.*;" +
                                     "@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})\n" +
                                     "@Retention(RetentionPolicy.RUNTIME)\n" +
                                     "@interface TypeParameterAnnotation { }";
    @Language("Java")
    String myClass = "class MyClass<@TypeParameterAnnotation S> {" +
                     "  @TypeParameterAnnotation String[] myFieldA;" +
                     "  @TypeParameterAnnotation String myFieldB;" +
                     "}";

    ClassNode myClassNode = create()
            .addJavaInputSource(typeParameterAnnotation)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass");

    TypeAnnotationNode firstClassTypeAnnotationNode = myClassNode.visibleTypeAnnotations.get(0);
    TypeAnnotationNode copyOfFirstClassTypeAnnotationNode = new TypeAnnotationNode(firstClassTypeAnnotationNode.typeRef, firstClassTypeAnnotationNode.typePath, firstClassTypeAnnotationNode.desc);
    TypeAnnotationNode firstFieldTypeAnnotationNode = myClassNode.fields.get(0).visibleTypeAnnotations.get(0);
    TypeAnnotationNode secondFieldTypeAnnotationNode = myClassNode.fields.get(1).visibleTypeAnnotations.get(0);

    // Equal
    AsmAssertions.assertThat(firstClassTypeAnnotationNode)
                 .isEqualTo(copyOfFirstClassTypeAnnotationNode);

    // Different type path
    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThat(firstClassTypeAnnotationNode)
                                                     .isEqualTo(firstFieldTypeAnnotationNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Type Annotation: @TypeParameterAnnotation > Is equal type path] \n" +
                          "expected: [\n" +
                          " but was: null\n" +
                          "when comparing values using TypePathComparator");

    // Different type reference
    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThat(firstClassTypeAnnotationNode)
                                                     .isEqualTo(secondFieldTypeAnnotationNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Type Annotation: @TypeParameterAnnotation > Is equal type reference] \n" +
                          "expected: field\n" +
                          " but was: class_type_parameter=0\n" +
                          "when comparing values using TypeReferenceComparator");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
