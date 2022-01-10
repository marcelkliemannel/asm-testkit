package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.FieldNode;

import java.io.IOException;

import static dev.turingcomplete.asmtestkit.assertion.comparator.FieldNodeComparator.INSTANCE;
import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;
import static org.assertj.core.api.Assertions.assertThat;

class FieldNodeComparatorTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCompareEqual() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA;" +
                     "class MyClass<T extends Number> { " +
                     "  @Deprecated" +
                     "  @VisibleTypeParameterAnnotationA" +
                     "  public T myField = null;" +
                     "}";

    FieldNode myField1 = create()
            .addToClasspath(VisibleTypeParameterAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .fields.get(0);

    FieldNode myField2 = create()
            .addToClasspath(VisibleTypeParameterAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .fields.get(0);

    assertThat(INSTANCE.compare(myField1, myField2))
            .isEqualTo(0);
  }

  @Test
  void testCompareNotEqual() throws IOException {
    @Language("Java")
    String myClass1 = "" +
                     "class MyClass { " +
                     "  public static final int myField = 1;" +
                     "}";

    FieldNode myField1 = create()
            .addToClasspath(VisibleTypeParameterAnnotationA.class)
            .addJavaInputSource(myClass1)
            .compile()
            .readClassNode("MyClass")
            .fields.get(0);

    @Language("Java")
    String myClass2 = "" +
                      "class MyClass { " +
                      "  public static final int myField = 2;" +
                      "}";

    FieldNode myField2 = create()
            .addToClasspath(VisibleTypeParameterAnnotationA.class)
            .addJavaInputSource(myClass2)
            .compile()
            .readClassNode("MyClass")
            .fields.get(0);

    assertThat(INSTANCE.compare(myField1, myField2))
            .isNotEqualTo(0);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
