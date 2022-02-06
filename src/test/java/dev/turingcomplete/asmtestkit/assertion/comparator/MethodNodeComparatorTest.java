package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleAnnotationA;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.util.Arrays;

import static dev.turingcomplete.asmtestkit.assertion.comparator.MethodNodeComparator.INSTANCE;
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
    String myClass = "import dev.turingcomplete.asmtestkit.assertion.__helper.*;" +

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
  void testCompareEqualToIgnoreLineNumbers() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.assertion.__helper.*;" +

                     "class MyClass {" +
                     "  Integer myMethod() {" +
                     "    try {" +
                     "      @VisibleTypeParameterAnnotationA Integer first = 6;" +
                     "      return first;" +
                     "    }" +
                     "    catch (@InvisibleTypeParameterAnnotationA IllegalArgumentException e) {" +
                     "      throw new IllegalStateException(e);" +
                     "    }" +
                     "  }" +
                     "}";

    MethodNode withoutLineNumbers = create()
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass").methods.get(1);

    assertThat(Arrays.stream(withoutLineNumbers.instructions.toArray()).filter(LineNumberNode.class::isInstance).count())
            .isEqualTo(1);

    @Language("Java")
    String myClassCopy = "import dev.turingcomplete.asmtestkit.assertion.__helper.*;" +

                         "class MyClass {\n" +
                         "  Integer myMethod() {\n" +
                         "    try {\n" +
                         "      @VisibleTypeParameterAnnotationA Integer first = 6;\n" +
                         "      return first;\n" +
                         "    }\n" +
                         "    catch (@InvisibleTypeParameterAnnotationA IllegalArgumentException e) {\n" +
                         "      throw new IllegalStateException(e);\n" +
                         "    }\n" +
                         "  }\n" +
                         "}\n";

    MethodNode withLineNumbers = create()
            .addJavaInputSource(myClassCopy)
            .compile()
            .readClassNode("MyClass").methods.get(1);
    assertThat(Arrays.stream(withLineNumbers.instructions.toArray()).filter(LineNumberNode.class::isInstance).count())
              .isEqualTo(4);

    assertThat(MethodNodeComparator.create().ignoreLineNumbers().compare(withoutLineNumbers, withLineNumbers))
              .isEqualTo(0);
  }

  @Test
  void testCompareNotEqualTo() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.assertion.__helper.*;" +

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
    String myClassCopy = "import dev.turingcomplete.asmtestkit.assertion.__helper.*;" +

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
