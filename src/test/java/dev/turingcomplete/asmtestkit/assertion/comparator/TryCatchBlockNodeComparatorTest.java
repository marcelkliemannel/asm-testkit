package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.LabelNameLookup;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.util.Map;

import static dev.turingcomplete.asmtestkit.asmutils.MethodNodeUtils.extractLabelNames;
import static dev.turingcomplete.asmtestkit.assertion.comparator.TryCatchBlockNodeComparator.INSTANCE;
import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;
import static org.assertj.core.api.Assertions.assertThat;

class TryCatchBlockNodeComparatorTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCompareIsEqual() throws IOException {
    @Language("Java")
    String myClass = "class MyClass {" +
                     "  void myMethod() {" +
                     "    try {" +
                     "      System.out.println(44);" +
                     "    }" +
                     "    catch (IllegalArgumentException e) {" +
                     "      throw new IllegalStateException(e);" +
                     "    }" +
                     "  }" +
                     "}";

    MethodNode firstMethod = create()
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods.get(1);

    MethodNode secondMethod = create()
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods.get(1);

    Map<Label, String> labelNames = extractLabelNames(firstMethod, secondMethod);
    assertThat(INSTANCE.compare(firstMethod.tryCatchBlocks.get(0), secondMethod.tryCatchBlocks.get(0), LabelNameLookup.create(labelNames)))
            .isEqualTo(0);
  }

  @Test
  void testCompareIsNotEqual() throws IOException {
    @Language("Java")
    String myClass1 = "class MyClass1 {" +
                      "  void myMethod() {" +
                      "    try {" +
                      "      System.out.println(44);" +
                      "    }" +
                      "    catch (IllegalArgumentException e) {" +
                      "      throw new IllegalStateException(e);" +
                      "    }" +
                      "  }" +
                      "}";

    MethodNode firstMethod = create()
            .addJavaInputSource(myClass1)
            .compile()
            .readClassNode("MyClass1")
            .methods.get(1);

    @Language("Java")
    String myClass2 = "class MyClass2 {" +
                      "  void myMethod() {" +
                      "    try {" +
                      "      System.out.println(44);" +
                      "    }" +
                      "    catch (IllegalStateException e) {" +
                      "      throw new RuntimeException(e);" +
                      "    }" +
                      "  }" +
                      "}";

    MethodNode secondMethod = create()
            .addJavaInputSource(myClass2)
            .compile()
            .readClassNode("MyClass2")
            .methods.get(1);

    Map<Label, String> labelNames = extractLabelNames(firstMethod, secondMethod);

    assertThat(INSTANCE.compare(firstMethod.tryCatchBlocks.get(0), secondMethod.tryCatchBlocks.get(0), LabelNameLookup.create(labelNames)))
            .isNotEqualTo(0);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
