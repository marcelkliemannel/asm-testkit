package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleAnnotationA;
import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA;
import org.assertj.core.api.Assertions;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.FieldNode;

import java.io.IOException;
import java.util.List;

import static dev.turingcomplete.asmtestkit.assertion.representation.FieldNodeRepresentation.INSTANCE;
import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;

class FieldNodeRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testToStringOf() throws IOException {
    @Language("Java")
    String myClass = "" +
                     "import dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleAnnotationA;" +
                     "import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA;" +
                     "class MyClass<T> {" +
                     "  @InvisibleAnnotationA\n" +
                     "  private T[]@VisibleTypeParameterAnnotationA [] myField;" +
                     "  @Deprecated(forRemoval = true)\n" +
                     "  public static final int myField2 = 5;" +
                     "  String myField3;" +
                     "}";

    List<FieldNode> fields = create()
            .addToClasspath(VisibleTypeParameterAnnotationA.class)
            .addToClasspath(InvisibleAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .fields;

    FieldNode field1 = fields.get(0);
    FieldNode field2 = fields.get(1);
    FieldNode field3 = fields.get(2);

    Assertions.assertThat(INSTANCE.toStringOf(field1))
              .isEqualTo("@dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleAnnotationA // invisible\n" +
                         "@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA // reference: field; path: [\n" +
                         "(2) private java.lang.Object[][] myField // signature: [[TT;");
    Assertions.assertThat(INSTANCE.doToSimplifiedStringOf(field1))
              .isEqualTo("(2) private java.lang.Object[][] myField");

    Assertions.assertThat(INSTANCE.toStringOf(field2))
              .isEqualTo("@java.lang.Deprecated(forRemoval=true)\n" +
                         "(131097) public static final deprecated int myField2 = 5");
    Assertions.assertThat(INSTANCE.doToSimplifiedStringOf(field2))
              .isEqualTo("(131097) public static final deprecated int myField2");

    Assertions.assertThat(INSTANCE.toStringOf(field3))
              .isEqualTo("(0) java.lang.String myField3");
    Assertions.assertThat(INSTANCE.doToSimplifiedStringOf(field3))
              .isEqualTo("(0) java.lang.String myField3");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
