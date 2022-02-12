package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.compile.CompilationResult;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.InsnList;

import java.io.IOException;

import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InsnListAssertTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testAssertThatInstructions() throws IOException {
    @Language("Java")
    String firstMyClass = "class FirstMyClass {" +
                          "  void myMethod() {" +
                          "    System.out.println(1);" +
                          "  }" +
                          "}";
    @Language("Java")
    String secondMyClass = "class SecondMyClass {" +
                           "  void myMethod() {" +
                           "    System.out.println(1);" +
                           "  }" +
                           "}";
    @Language("Java")
    String thirdMyClass = "class ThirdMyClass {" +
                          "  void myMethod() {" +
                          "    throw new IllegalArgumentException();" +
                          "  }" +
                          "}";
    CompilationResult result = create()
            .addJavaInputSource(firstMyClass)
            .addJavaInputSource(secondMyClass)
            .addJavaInputSource(thirdMyClass)
            .compile();

    InsnList firstInstructions = result.readClassNode("FirstMyClass").methods.get(1).instructions;
    InsnList secondInstructions = result.readClassNode("SecondMyClass").methods.get(1).instructions;
    InsnList thirdInstructions = result.readClassNode("ThirdMyClass").methods.get(1).instructions;

    AsmAssertions.assertThatInstructions(firstInstructions)
                 .isEqualTo(secondInstructions);

    assertThatThrownBy(() -> AsmAssertions.assertThatInstructions(firstInstructions)
                                          .isEqualTo(thirdInstructions))
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Instructions] \n" +
                        "expected: \n" +
                        "  L0\n" +
                        "    LINENUMBER 1 L0\n" +
                        "    NEW java/lang/IllegalArgumentException // opcode: 187\n" +
                        "    DUP // opcode: 89\n" +
                        "    INVOKESPECIAL java/lang/IllegalArgumentException.<init> ()V // opcode: 183\n" +
                        "    ATHROW // opcode: 191\n" +
                        "  L1\n" +
                        " but was: \n" +
                        "  L0\n" +
                        "    LINENUMBER 1 L0\n" +
                        "    GETSTATIC java/lang/System.out : Ljava/io/PrintStream; // opcode: 178\n" +
                        "    ICONST_1 // opcode: 4\n" +
                        "    INVOKEVIRTUAL java/io/PrintStream.println (I)V // opcode: 182\n" +
                        "    RETURN // opcode: 177\n" +
                        "  L1\n" +
                        "when comparing values using InsnListComparator");
  }

  @Test
  void testAssertThatInstructions_ignoreLineNumbers() throws IOException {
    @Language("Java")
    String firstMyClass = "class FirstMyClass {" +
                          "  void myMethod() {" +
                          "    System.out.println(1);\n" +
                          "  }" +
                          "}";
    @Language("Java")
    String secondMyClass = "class SecondMyClass {" +
                           "  void myMethod() {" +
                           "    System.out.println(1);" +
                           "  }" +
                           "}";
    @Language("Java")
    String thirdMyClass = "class ThirdMyClass {" +
                          "  void myMethod() {" +
                          "    System.out.println(2);" +
                          "  }" +
                          "}";
    CompilationResult result = create()
            .addJavaInputSource(firstMyClass)
            .addJavaInputSource(secondMyClass)
            .addJavaInputSource(thirdMyClass)
            .compile();

    InsnList firstInstructions = result.readClassNode("FirstMyClass").methods.get(1).instructions;
    InsnList secondInstructions = result.readClassNode("SecondMyClass").methods.get(1).instructions;
    InsnList thirdInstructions = result.readClassNode("ThirdMyClass").methods.get(1).instructions;

    AsmAssertions.assertThatInstructions(firstInstructions)
                 .ignoreLineNumbers()
                 .isEqualTo(secondInstructions);

    assertThatThrownBy(() -> AsmAssertions.assertThatInstructions(secondInstructions)
                                          .ignoreLineNumbers()
                                          .isEqualTo(thirdInstructions))
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Instructions] \n" +
                        "expected: \n" +
                        "  L0\n" +
                        "    LINENUMBER 1 L0\n" +
                        "    GETSTATIC java/lang/System.out : Ljava/io/PrintStream; // opcode: 178\n" +
                        "    ICONST_2 // opcode: 5\n" +
                        "    INVOKEVIRTUAL java/io/PrintStream.println (I)V // opcode: 182\n" +
                        "    RETURN // opcode: 177\n" +
                        "  L1\n" +
                        " but was: \n" +
                        "  L0\n" +
                        "    LINENUMBER 1 L0\n" +
                        "    GETSTATIC java/lang/System.out : Ljava/io/PrintStream; // opcode: 178\n" +
                        "    ICONST_1 // opcode: 4\n" +
                        "    INVOKEVIRTUAL java/io/PrintStream.println (I)V // opcode: 182\n" +
                        "    RETURN // opcode: 177\n" +
                        "  L1\n" +
                        "when comparing values using InsnListComparator");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
