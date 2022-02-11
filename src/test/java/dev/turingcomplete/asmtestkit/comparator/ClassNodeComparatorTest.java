package dev.turingcomplete.asmtestkit.comparator;

import dev.turingcomplete.asmtestkit.__helper.AsmNodeTestUtils;
import dev.turingcomplete.asmtestkit.__helper.VisibleAnnotationA;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;

import static dev.turingcomplete.asmtestkit.comparator.ClassNodeComparator.INSTANCE;
import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;
import static org.assertj.core.api.Assertions.assertThat;

class ClassNodeComparatorTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCompareEqualTo() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.__helper.*;" +
                     "@VisibleTypeParameterAnnotationA\n" +
                     "@VisibleAnnotationA\n" +
                     "class MyClass<T extends Number> {" +

                     "  public final int myField1 = 2;" +

                     "  public void myMethod() {}" +

                     "  public enum Foo { FOO, BAR } " +
                     "}";

    ClassNode firstClass = create()
            .addToClasspath(VisibleAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass");

    ClassNode secondClass = create()
            .addToClasspath(VisibleAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass");

    assertThat(INSTANCE.compare(firstClass, secondClass))
            .isEqualTo(0);
  }

  @Test
  void testCompareEqualTo_ignoreLineNumbers() throws IOException {
    @Language("Java")
    String myClassWithoutLineNumbers = "import dev.turingcomplete.asmtestkit.__helper.*;" +
                                       "class MyClass<T extends Number> {" +
                                       "  public final int myField1 = 2;" +
                                       "  void myMethod() { System.out.println(222); } " +
                                       "}";

    ClassNode withoutLineNumbers = create()
            .addJavaInputSource(myClassWithoutLineNumbers)
            .compile()
            .readClassNode("MyClass");

    assertThat(AsmNodeTestUtils.countLineNumbers(withoutLineNumbers.methods.get(1).instructions))
            .isEqualTo(1);

    @Language("Java")
    String myClassWithLineNumbers = "import dev.turingcomplete.asmtestkit.__helper.*;\n" +
                                    "class MyClass<T extends Number> {\n\n" +
                                    "  public final int myField1 = 2\n;\n\n" +
                                    "  void myMethod() {\n System\n.out\n.println(222)\n;\n } " +
                                    "}";

    ClassNode withLineNumbers = create()
            .addJavaInputSource(myClassWithLineNumbers)
            .compile()
            .readClassNode("MyClass");

    assertThat(AsmNodeTestUtils.countLineNumbers(withLineNumbers.methods.get(1).instructions))
            .isEqualTo(3);

    assertThat(ClassNodeComparator.INSTANCE_IGNORE_LINE_NUMBERS.compare(withoutLineNumbers, withLineNumbers))
            .isEqualTo(0);
  }

  @Test
  void testCompareNotEqualTo() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.__helper.*;" +
                     "@VisibleTypeParameterAnnotationA\n" +
                     "@VisibleAnnotationA\n" +
                     "class MyClass<T extends Number> {" +

                     "  public final int myField1 = 2;" +

                     "  public void myMethod1() {}" +

                     "  public enum Foo { FOO, BAR } " +
                     "}";

    MethodNode firstMethod = create()
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods.get(1);

    @Language("Java")
    String myClassCopy = "import dev.turingcomplete.asmtestkit.__helper.*;" +
                         "@VisibleTypeParameterAnnotationA\n" +
                         "@VisibleAnnotationA\n" +
                         "class MyClass<T extends Number> {" +

                         "  public final int myField1 = 2;" +

                         "  public void myMethod2() {}" + // Different method name

                         "  public enum Foo { FOO, BAR } " +
                         "}";

    MethodNode secondMethod = create()
            .addJavaInputSource(myClassCopy)
            .compile()
            .readClassNode("MyClass")
            .methods.get(1);

    assertThat(INSTANCE.compare(firstMethod, secondMethod))
            .isNotEqualTo(0);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
