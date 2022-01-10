package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA;
import org.assertj.core.api.Assertions;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.TypeReference;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;

import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;

class TypeReferenceAssertTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testIsEqualTo() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA;" +
                     "class MyClass<@VisibleTypeParameterAnnotationA S> {" +
                     "  @VisibleTypeParameterAnnotationA String myField;" +
                     "}";

    ClassNode myClassNode = create()
            .addToClasspath(VisibleTypeParameterAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass");

    var classTypeReference = new TypeReference(myClassNode.visibleTypeAnnotations.get(0).typeRef);
    var copyOfClassTypeReference = new TypeReference(classTypeReference.getValue());
    var fieldTypeReference = new TypeReference(myClassNode.fields.get(0).visibleTypeAnnotations.get(0).typeRef);

    // Equal
    AsmAssertions.assertThat(classTypeReference)
                 .isEqualTo(copyOfClassTypeReference);

    // Different type path
    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThat(classTypeReference)
                                                     .isEqualTo(fieldTypeReference))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Type reference: class_type_parameter=0] \n" +
                          "expected: field\n" +
                          " but was: class_type_parameter=0\n" +
                          "when comparing values using TypeReferenceComparator");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
