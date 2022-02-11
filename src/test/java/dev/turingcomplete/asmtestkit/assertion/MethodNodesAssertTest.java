package dev.turingcomplete.asmtestkit.assertion;

import org.assertj.core.api.ThrowableAssert;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.util.List;

import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatMethods;
import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MethodNodesAssertTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testAssertThatMethods() throws IOException {
    @Language("Java")
    String myClass = "class MyClass {" +
                     "   void myMethod1() {" +
                     "     System.out.println(1);" +
                     "   }" +
                     "   int myMethod2() {" +
                     "     System.out.println(2);" +
                     "     return 1;" +
                     "   }" +
                     "   String myMethod3() {" +
                     "     System.out.println(3);" +
                     "     return null;" +
                     "   }" +
                     " }";

    List<MethodNode> methods = create()
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods;

    MethodNode firstMethodNode = methods.get(0);
    MethodNode secondMethodNode = methods.get(1);
    MethodNode thirdMethodNode = methods.get(2);

    // Positive
    assertThatMethods(List.of(firstMethodNode, secondMethodNode, thirdMethodNode))
            .containsExactlyInAnyOrderElementsOf(List.of(firstMethodNode, secondMethodNode, thirdMethodNode));

    // Negative
    ThrowableAssert.ThrowingCallable throwingCallable = () -> assertThatMethods(List.of(firstMethodNode, secondMethodNode))
            .containsExactlyInAnyOrderElementsOf(List.of(secondMethodNode, thirdMethodNode));
    assertThatThrownBy(throwingCallable)
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Methods] \n" +
                        "Expecting actual:\n" +
                        "  [[0] <init>()\n" +
                        "    L0\n" +
                        "      LINENUMBER 1 L0\n" +
                        "      ALOAD 0 // opcode: 25\n" +
                        "      INVOKESPECIAL java/lang/Object.<init> ()V // opcode: 183\n" +
                        "      RETURN // opcode: 177\n" +
                        "    L1\n" +
                        "  // Local variable: #0 MyClass this // range: L0-L1\n" +
                        "  // Max locals: 1\n" +
                        "  // Max stack: 1,\n" +
                        "    [0] void myMethod1()\n" +
                        "    L0\n" +
                        "      LINENUMBER 1 L0\n" +
                        "      GETSTATIC java/lang/System.out : Ljava/io/PrintStream; // opcode: 178\n" +
                        "      ICONST_1 // opcode: 4\n" +
                        "      INVOKEVIRTUAL java/io/PrintStream.println (I)V // opcode: 182\n" +
                        "      RETURN // opcode: 177\n" +
                        "    L1\n" +
                        "  // Local variable: #0 MyClass this // range: L0-L1\n" +
                        "  // Max locals: 1\n" +
                        "  // Max stack: 2]\n" +
                        "to contain exactly in any order:\n" +
                        "  [[0] void myMethod1()\n" +
                        "    L0\n" +
                        "      LINENUMBER 1 L0\n" +
                        "      GETSTATIC java/lang/System.out : Ljava/io/PrintStream; // opcode: 178\n" +
                        "      ICONST_1 // opcode: 4\n" +
                        "      INVOKEVIRTUAL java/io/PrintStream.println (I)V // opcode: 182\n" +
                        "      RETURN // opcode: 177\n" +
                        "    L1\n" +
                        "  // Local variable: #0 MyClass this // range: L0-L1\n" +
                        "  // Max locals: 1\n" +
                        "  // Max stack: 2,\n" +
                        "    [0] int myMethod2()\n" +
                        "    L0\n" +
                        "      LINENUMBER 1 L0\n" +
                        "      GETSTATIC java/lang/System.out : Ljava/io/PrintStream; // opcode: 178\n" +
                        "      ICONST_2 // opcode: 5\n" +
                        "      INVOKEVIRTUAL java/io/PrintStream.println (I)V // opcode: 182\n" +
                        "      ICONST_1 // opcode: 4\n" +
                        "      IRETURN // opcode: 172\n" +
                        "    L1\n" +
                        "  // Local variable: #0 MyClass this // range: L0-L1\n" +
                        "  // Max locals: 1\n" +
                        "  // Max stack: 2]\n" +
                        "elements not found:\n" +
                        "  [[0] int myMethod2()\n" +
                        "    L0\n" +
                        "      LINENUMBER 1 L0\n" +
                        "      GETSTATIC java/lang/System.out : Ljava/io/PrintStream; // opcode: 178\n" +
                        "      ICONST_2 // opcode: 5\n" +
                        "      INVOKEVIRTUAL java/io/PrintStream.println (I)V // opcode: 182\n" +
                        "      ICONST_1 // opcode: 4\n" +
                        "      IRETURN // opcode: 172\n" +
                        "    L1\n" +
                        "  // Local variable: #0 MyClass this // range: L0-L1\n" +
                        "  // Max locals: 1\n" +
                        "  // Max stack: 2]\n" +
                        "and elements not expected:\n" +
                        "  [[0] <init>()\n" +
                        "    L0\n" +
                        "      LINENUMBER 1 L0\n" +
                        "      ALOAD 0 // opcode: 25\n" +
                        "      INVOKESPECIAL java/lang/Object.<init> ()V // opcode: 183\n" +
                        "      RETURN // opcode: 177\n" +
                        "    L1\n" +
                        "  // Local variable: #0 MyClass this // range: L0-L1\n" +
                        "  // Max locals: 1\n" +
                        "  // Max stack: 1]\n" +
                        "when comparing values using MethodNodeComparator");
  }


  @Test
  void testAssertThatMethods_ignoreLineNumbers() throws IOException {
    @Language("Java")
    String myClassWithoutLineNumbers = "class MyClass {" +
                                       "   void myMethod1() {" +
                                       "     System.out.println(1);" +
                                       "   }" +
                                       "   void myMethod2() {" +
                                       "     System.out.println(1);" +
                                       "   }" +
                                       " }";

    List<MethodNode> methodsWithoutLineNumbers = create()
            .addJavaInputSource(myClassWithoutLineNumbers)
            .compile()
            .readClassNode("MyClass")
            .methods;

    @Language("Java")
    String myClassWithLineNumbers = "class MyClass {\n" +
                                       "   void myMethod1() {\n" +
                                       "     System.out.println(1)\n;\n" +
                                       "   }\n" +
                                       "   void myMethod2() {\n" +
                                       "     System.out.println(1)\n\n;\n" +
                                       "   }\n" +
                                       " }\n";

    List<MethodNode> methodsWithLineNumbers = create()
            .addJavaInputSource(myClassWithLineNumbers)
            .compile()
            .readClassNode("MyClass")
            .methods;

    assertThatMethods(methodsWithoutLineNumbers)
            .ignoreLineNumbers()
            .containsExactlyInAnyOrderCompareOneByOneElementsOf(methodsWithLineNumbers);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
