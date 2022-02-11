package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.__helper.AsmNodeTestUtils;
import dev.turingcomplete.asmtestkit.compile.CompilationResult;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.util.List;

import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatClasses;
import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ClassNodesAssertTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testAssertThatClasses() throws IOException {
    @Language("Java")
    String myClassA = "class MyClassA {" +
                      "   int field = 1;" +
                      " }";

    @Language("Java")
    String myClassB = "class MyClassB {" +
                      "   int field = 1;" +
                      " }";

    @Language("Java")
    String myClassC = "class MyClassC {" +
                      "   int field = 1;" +
                      " }";

    CompilationResult result = create()
            .addJavaInputSource(myClassA)
            .addJavaInputSource(myClassB)
            .addJavaInputSource(myClassC)
            .compile();

    ClassNode classA = result.readClassNode("MyClassA");
    ClassNode classB = result.readClassNode("MyClassB");
    ClassNode classC = result.readClassNode("MyClassC");

    // Positive
    assertThatClasses(List.of(classA, classB, classC))
            .containsExactlyInAnyOrderElementsOf(List.of(classA, classB, classC));

    // Negative
    ThrowableAssert.ThrowingCallable throwingCallable = () -> assertThatClasses(List.of(classA, classB))
            .containsExactlyInAnyOrderElementsOf(List.of(classB, classC));
    assertThatThrownBy(throwingCallable)
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Classes] \n" +
                        "Expecting actual:\n" +
                        "  [// Class version: 55\n" +
                        "[32: super] class MyClassA extends java.lang.Object\n" +
                        "\n" +
                        "    [0] int field\n" +
                        "\n" +
                        "    [0] <init>()\n" +
                        "        L0\n" +
                        "          LINENUMBER 1 L0\n" +
                        "          ALOAD 0 // opcode: 25\n" +
                        "          INVOKESPECIAL java/lang/Object.<init> ()V // opcode: 183\n" +
                        "          ALOAD 0 // opcode: 25\n" +
                        "          ICONST_1 // opcode: 4\n" +
                        "          PUTFIELD MyClassA.field : I // opcode: 181\n" +
                        "          RETURN // opcode: 177\n" +
                        "        L1\n" +
                        "      // Local variable: #0 MyClassA this // range: L0-L1\n" +
                        "      // Max locals: 1\n" +
                        "      // Max stack: 2\n" +
                        "\n" +
                        "  // Source file: MyClassA.java\n" +
                        ",\n" +
                        "    // Class version: 55\n" +
                        "[32: super] class MyClassB extends java.lang.Object\n" +
                        "\n" +
                        "    [0] int field\n" +
                        "\n" +
                        "    [0] <init>()\n" +
                        "        L0\n" +
                        "          LINENUMBER 1 L0\n" +
                        "          ALOAD 0 // opcode: 25\n" +
                        "          INVOKESPECIAL java/lang/Object.<init> ()V // opcode: 183\n" +
                        "          ALOAD 0 // opcode: 25\n" +
                        "          ICONST_1 // opcode: 4\n" +
                        "          PUTFIELD MyClassB.field : I // opcode: 181\n" +
                        "          RETURN // opcode: 177\n" +
                        "        L1\n" +
                        "      // Local variable: #0 MyClassB this // range: L0-L1\n" +
                        "      // Max locals: 1\n" +
                        "      // Max stack: 2\n" +
                        "\n" +
                        "  // Source file: MyClassB.java\n" +
                        "]\n" +
                        "to contain exactly in any order:\n" +
                        "  [// Class version: 55\n" +
                        "[32: super] class MyClassB extends java.lang.Object\n" +
                        "\n" +
                        "    [0] int field\n" +
                        "\n" +
                        "    [0] <init>()\n" +
                        "        L0\n" +
                        "          LINENUMBER 1 L0\n" +
                        "          ALOAD 0 // opcode: 25\n" +
                        "          INVOKESPECIAL java/lang/Object.<init> ()V // opcode: 183\n" +
                        "          ALOAD 0 // opcode: 25\n" +
                        "          ICONST_1 // opcode: 4\n" +
                        "          PUTFIELD MyClassB.field : I // opcode: 181\n" +
                        "          RETURN // opcode: 177\n" +
                        "        L1\n" +
                        "      // Local variable: #0 MyClassB this // range: L0-L1\n" +
                        "      // Max locals: 1\n" +
                        "      // Max stack: 2\n" +
                        "\n" +
                        "  // Source file: MyClassB.java\n" +
                        ",\n" +
                        "    // Class version: 55\n" +
                        "[32: super] class MyClassC extends java.lang.Object\n" +
                        "\n" +
                        "    [0] int field\n" +
                        "\n" +
                        "    [0] <init>()\n" +
                        "        L0\n" +
                        "          LINENUMBER 1 L0\n" +
                        "          ALOAD 0 // opcode: 25\n" +
                        "          INVOKESPECIAL java/lang/Object.<init> ()V // opcode: 183\n" +
                        "          ALOAD 0 // opcode: 25\n" +
                        "          ICONST_1 // opcode: 4\n" +
                        "          PUTFIELD MyClassC.field : I // opcode: 181\n" +
                        "          RETURN // opcode: 177\n" +
                        "        L1\n" +
                        "      // Local variable: #0 MyClassC this // range: L0-L1\n" +
                        "      // Max locals: 1\n" +
                        "      // Max stack: 2\n" +
                        "\n" +
                        "  // Source file: MyClassC.java\n" +
                        "]\n" +
                        "elements not found:\n" +
                        "  [// Class version: 55\n" +
                        "[32: super] class MyClassC extends java.lang.Object\n" +
                        "\n" +
                        "    [0] int field\n" +
                        "\n" +
                        "    [0] <init>()\n" +
                        "        L0\n" +
                        "          LINENUMBER 1 L0\n" +
                        "          ALOAD 0 // opcode: 25\n" +
                        "          INVOKESPECIAL java/lang/Object.<init> ()V // opcode: 183\n" +
                        "          ALOAD 0 // opcode: 25\n" +
                        "          ICONST_1 // opcode: 4\n" +
                        "          PUTFIELD MyClassC.field : I // opcode: 181\n" +
                        "          RETURN // opcode: 177\n" +
                        "        L1\n" +
                        "      // Local variable: #0 MyClassC this // range: L0-L1\n" +
                        "      // Max locals: 1\n" +
                        "      // Max stack: 2\n" +
                        "\n" +
                        "  // Source file: MyClassC.java\n" +
                        "]\n" +
                        "and elements not expected:\n" +
                        "  [// Class version: 55\n" +
                        "[32: super] class MyClassA extends java.lang.Object\n" +
                        "\n" +
                        "    [0] int field\n" +
                        "\n" +
                        "    [0] <init>()\n" +
                        "        L0\n" +
                        "          LINENUMBER 1 L0\n" +
                        "          ALOAD 0 // opcode: 25\n" +
                        "          INVOKESPECIAL java/lang/Object.<init> ()V // opcode: 183\n" +
                        "          ALOAD 0 // opcode: 25\n" +
                        "          ICONST_1 // opcode: 4\n" +
                        "          PUTFIELD MyClassA.field : I // opcode: 181\n" +
                        "          RETURN // opcode: 177\n" +
                        "        L1\n" +
                        "      // Local variable: #0 MyClassA this // range: L0-L1\n" +
                        "      // Max locals: 1\n" +
                        "      // Max stack: 2\n" +
                        "\n" +
                        "  // Source file: MyClassA.java\n" +
                        "]\n" +
                        "when comparing values using ClassNodeComparator");
  }


  @Test
  void testAssertThatMethods_ignoreLineNumbers() throws IOException {
    @Language("Java")
    String myClassWithoutLineNumbers = "class MyClass {" +
                                       "   void myMethod() {" +
                                       "     System.out.println(1);" +
                                       "   }" +
                                       " }";

    ClassNode classWithoutLineNumbers = create()
            .addJavaInputSource(myClassWithoutLineNumbers)
            .compile()
            .readClassNode("MyClass");

    Assertions.assertThat(AsmNodeTestUtils.countLineNumbers(classWithoutLineNumbers.methods.get(1).instructions))
              .isEqualTo(1);

    @Language("Java")
    String myClassWithLineNumbers = "class MyClass {\n" +
                                    "   void myMethod() {\n" +
                                    "     System.out.println(1);\n" +
                                    "   }\n" +
                                    " }\n";

    Assertions.assertThat(AsmNodeTestUtils.countLineNumbers(classWithoutLineNumbers.methods.get(1).instructions))
              .isEqualTo(1);

    ClassNode classWithLineNumbers = create()
            .addJavaInputSource(myClassWithLineNumbers)
            .compile()
            .readClassNode("MyClass");

    @Language("Java")
    String myClassWithMoreLineNumbers = "class MyClass {\n" +
                                        "   void myMethod() {\n" +
                                        "     System\n.out\n.println(1)\n;\n" +
                                        "   }\n" +
                                        " }\n";

    ClassNode classWithMoreLineNumbers = create()
            .addJavaInputSource(myClassWithMoreLineNumbers)
            .compile()
            .readClassNode("MyClass");

    Assertions.assertThat(AsmNodeTestUtils.countLineNumbers(classWithMoreLineNumbers.methods.get(1).instructions))
              .isEqualTo(3);

    assertThatClasses(List.of(classWithoutLineNumbers, classWithLineNumbers))
            .ignoreLineNumbers()
            .containsExactlyInAnyOrderCompareOneByOneElementsOf(List.of(classWithLineNumbers, classWithMoreLineNumbers));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
