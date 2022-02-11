package dev.turingcomplete.asmtestkit.comparator;

import dev.turingcomplete.asmtestkit.__helper.VisibleAnnotationA;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.util.Arrays;

import static dev.turingcomplete.asmtestkit.comparator.MethodNodeComparator.INSTANCE;
import static dev.turingcomplete.asmtestkit.comparator.MethodNodeComparator.INSTANCE_IGNORE_LINE_NUMBERS;
import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;
import static org.assertj.core.api.Assertions.assertThat;

class MethodNodeComparatorTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCompareEqualTo() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.__helper.InvisibleAnnotationB;import dev.turingcomplete.asmtestkit.__helper.InvisibleTypeParameterAnnotationA;import dev.turingcomplete.asmtestkit.__helper.VisibleAnnotationA;import dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA;import dev.turingcomplete.asmtestkit.__helper.*;" +

                     "class MyClass<S> {" +
                     "  @InvisibleAnnotationB" +
                     "  @VisibleAnnotationA" +
                     "  <T> T myMethod(String bar, @VisibleAnnotationA Integer foo) {\n" +
                     "    try {" +
                     "      @VisibleTypeParameterAnnotationA Integer first = 6 + foo;\n" +
                     "      @InvisibleTypeParameterAnnotationA Integer second = 6 + foo;\n" +
                     "      return null;\n" +
                     "    }" +
                     "    catch (@InvisibleTypeParameterAnnotationA IllegalArgumentException e) {" +
                     "      throw new IllegalStateException(e);" +
                     "    }" +
                     "  }" +
                     "}";

    MethodNode firstMethod = create()
            .addToClasspath(VisibleAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods.get(1);

    MethodNode secondMethod = create()
            .addToClasspath(VisibleAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods.get(1);

    assertThat(INSTANCE.compare(firstMethod, secondMethod))
            .isEqualTo(0);
  }

  @Test
  void testCompareEqualTo_ignoreLineNumbers() throws IOException {
    @Language("Java")
    String myClassWithoutLineNumbers = "import dev.turingcomplete.asmtestkit.__helper.InvisibleTypeParameterAnnotationA;import dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA;import dev.turingcomplete.asmtestkit.__helper.*;" +

                                       "class MyClass {" +
                                       "  void myMethod() {" +
                                       "    System.out.println(1);" +
                                       "    System.out.println(2);" +
                                       "  }" +
                                       "}";

    MethodNode withoutLineNumbers = create()
            .addJavaInputSource(myClassWithoutLineNumbers)
            .compile()
            .readClassNode("MyClass").methods.get(1);

    assertThat(Arrays.stream(withoutLineNumbers.instructions.toArray()).filter(LineNumberNode.class::isInstance).count())
            .isEqualTo(1);

    @Language("Java")
    String myClassWithLineNumbers = "import dev.turingcomplete.asmtestkit.__helper.InvisibleTypeParameterAnnotationA;import dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA;import dev.turingcomplete.asmtestkit.__helper.*;" +

                                    "class MyClass {\n" +
                                    "  void myMethod() {\n" +
                                    "    System\n.out\n.println(1)\n;\n" +
                                    "    System\n.out\n.println(2)\n;\n" +
                                    "  }\n" +
                                    "}\n";

    MethodNode withLineNumbers = create()
            .addJavaInputSource(myClassWithLineNumbers)
            .compile()
            .readClassNode("MyClass").methods.get(1);

    assertThat(Arrays.stream(withLineNumbers.instructions.toArray()).filter(LineNumberNode.class::isInstance).count())
            .isEqualTo(5);

    assertThat(INSTANCE_IGNORE_LINE_NUMBERS.compare(withoutLineNumbers, withLineNumbers))
            .isEqualTo(0);
  }

  @Test
  void testCompareNotEqualTo() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.__helper.InvisibleAnnotationB;import dev.turingcomplete.asmtestkit.__helper.InvisibleTypeParameterAnnotationA;import dev.turingcomplete.asmtestkit.__helper.VisibleAnnotationA;import dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA;import dev.turingcomplete.asmtestkit.__helper.*;" +

                     "class MyClass<S> {" +
                     "  @InvisibleAnnotationB" +
                     "  @VisibleAnnotationA" +
                     "  <T> T myMethod(String bar, @VisibleAnnotationA Integer foo) {\n" +
                     "    try {" +
                     "      @VisibleTypeParameterAnnotationA Integer first = 6 + foo;\n" +
                     "      @InvisibleTypeParameterAnnotationA Integer second = 6 + foo;\n" +
                     "      return null;\n" +
                     "    }" +
                     "    catch (@InvisibleTypeParameterAnnotationA IllegalArgumentException e) {" +
                     "      throw new IllegalStateException(e);" +
                     "    }" +
                     "  }" +
                     "}";

    MethodNode firstMethod = create()
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods.get(1);

    @Language("Java")
    String myClassCopy = "import dev.turingcomplete.asmtestkit.__helper.InvisibleAnnotationB;import dev.turingcomplete.asmtestkit.__helper.InvisibleTypeParameterAnnotationA;import dev.turingcomplete.asmtestkit.__helper.VisibleAnnotationA;import dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA;import dev.turingcomplete.asmtestkit.__helper.*;" +

                         "class MyClass<S> {" +
                         "  @InvisibleAnnotationB" +
                         "  @VisibleAnnotationA" +
                         "  <T> T myMethod(String bar, @VisibleAnnotationA Integer foo) {\n" +
                         "    try {" +
                         "      @VisibleTypeParameterAnnotationA Integer first = 6 + foo;\n" +
                         "      @InvisibleTypeParameterAnnotationA Integer second = 6 + foo;\n" +
                         "      return null;\n" +
                         "    }" +
                         "    catch (@VisibleTypeParameterAnnotationA IllegalArgumentException e) {" + // different annotation
                         "      throw new IllegalStateException(e);" +
                         "    }" +
                         "  }" +
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
