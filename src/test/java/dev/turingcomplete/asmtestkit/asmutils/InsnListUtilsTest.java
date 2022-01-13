package dev.turingcomplete.asmtestkit.asmutils;

import dev.turingcomplete.asmtestkit.assertion.AsmAssertions;
import dev.turingcomplete.asmtestkit.compile.CompilationEnvironment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;

import java.io.IOException;
import java.util.Arrays;

class InsnListUtilsTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Check that {@link InsnListUtils#filterLineNumbers(Iterable)} does not remove
   * labels used by a line number but also by a {@link JumpInsnNode}.
   */
  @Test
  void filterLineNumbersJumpInsnNode() throws IOException {
    InsnList actual = CompilationEnvironment
            .create()
            .addJavaInputSource("class MyClass {\n" +
                                "  void myMethod(int param) {\n" +
                                "    foo: while (param == 1) { continue foo; }\n" +
                                "  }\n" +
                                "}\n")
            .compile()
            .readClassNode("MyClass").methods.get(1).instructions;

    InsnList expected = CompilationEnvironment
            .create()
            .addJavaInputSource("class MyClass {\n" +
                                "  void myMethod(int param) {\n" +
                                "    foo: while (param == 1) { continue foo; }" +
                                "  }\n" +
                                "}\n")
            .disableDebuggingInformation() // No line numbers
            .compile()
            .readClassNode("MyClass").methods.get(1).instructions;

    // Check that actual contains line numbers and expected none
    Assertions.assertThat(Arrays.stream(actual.toArray()).filter(LineNumberNode.class::isInstance).count())
              .isEqualTo(2);
    Assertions.assertThat(Arrays.stream(expected.toArray()).filter(LineNumberNode.class::isInstance).count())
              .isEqualTo(0);

    // Ensure presents of JumpInsnNode
    Assertions.assertThat(actual).anyMatch(input -> input instanceof JumpInsnNode);
    Assertions.assertThat(expected).anyMatch(input -> input instanceof JumpInsnNode);

    // Don't use 'assertThatInstructionsIgnoreLineNumbers', because it is already cleaned
    AsmAssertions.assertThatInstructions(InsnListUtils.filterLineNumbers(actual))
                 .isEqualTo(InsnListUtils.filterLineNumbers(expected));
  }

  /**
   * Check that {@link InsnListUtils#filterLineNumbers(Iterable)} does not remove
   * labels used by a line number but also by a {@link LookupSwitchInsnNode}.
   */
  @Test
  void filterLineNumbersLookupSwitch() throws IOException {
    InsnList actual = CompilationEnvironment
            .create()
            .addJavaInputSource("class MyClass {\n" +
                                "  void myMethod(int param) {\n" +
                                "    switch(param) {\n" +
                                "      case 0: param = 2;\n" +
                                "      default: param = 4;\n" +
                                "    }\n" +
                                "  }\n" +
                                "}\n")
            .compile()
            .readClassNode("MyClass").methods.get(1).instructions;

    InsnList expected = CompilationEnvironment
            .create()
            .addJavaInputSource("class MyClass {\n" +
                                "  void myMethod(int param) {\n" +
                                "    switch(param) {" +
                                "      case 0: param = 2;" +
                                "      default: param = 4;" +
                                "    }\n" +
                                "  }\n" +
                                "}\n")
            .disableDebuggingInformation() // No line numbers
            .compile()
            .readClassNode("MyClass").methods.get(1).instructions;

    // Check that actual contains line numbers and expected none
    Assertions.assertThat(Arrays.stream(actual.toArray()).filter(LineNumberNode.class::isInstance).count())
              .isEqualTo(4);
    Assertions.assertThat(Arrays.stream(expected.toArray()).filter(LineNumberNode.class::isInstance).count())
              .isEqualTo(0);

    // Ensure presents of LookupSwitchInsnNode
    Assertions.assertThat(actual).anyMatch(input -> input instanceof LookupSwitchInsnNode);
    Assertions.assertThat(expected).anyMatch(input -> input instanceof LookupSwitchInsnNode);

    // Don't use 'assertThatInstructionsIgnoreLineNumbers', because it is already cleaned
    AsmAssertions.assertThatInstructions(InsnListUtils.filterLineNumbers(actual))
                 .isEqualTo(InsnListUtils.filterLineNumbers(expected));
  }

  /**
   * Check that {@link InsnListUtils#filterLineNumbers(Iterable)} does not remove
   * labels used by a line number but also by a {@link TableSwitchInsnNode}.
   */
  @Test
  void filterLineNumbersTableSwitch() throws IOException {
    InsnList actual = CompilationEnvironment
            .create()
            .addJavaInputSource("class MyClass {\n" +
                                "  void myMethod(int param) {\n" +
                                "    switch(param) {\n" +
                                "      case 0: param = 2;\n" +
                                "      case 1: param = 2;\n" +
                                "      case 2: param = 2;\n" +
                                "      case 3: param = 2;\n" +
                                "      case 4: param = 2;\n" +
                                "      default: param = 4;\n" +
                                "    }\n" +
                                "  }\n" +
                                "}\n")
            .compile()
            .readClassNode("MyClass").methods.get(1).instructions;

    InsnList expected = CompilationEnvironment
            .create()
            .addJavaInputSource("class MyClass {" +
                                "  void myMethod(int param) {" +
                                "    switch(param) {" +
                                "      case 0: param = 2;" +
                                "      case 1: param = 2;" +
                                "      case 2: param = 2;" +
                                "      case 3: param = 2;" +
                                "      case 4: param = 2;" +
                                "      default: param = 4;" +
                                "    }" +
                                "  }" +
                                "}")
            .disableDebuggingInformation() // No line numbers
            .compile()
            .readClassNode("MyClass").methods.get(1).instructions;

    // Check that actual contains line numbers and expected none
    Assertions.assertThat(Arrays.stream(actual.toArray()).filter(LineNumberNode.class::isInstance).count())
              .isEqualTo(8);
    Assertions.assertThat(Arrays.stream(expected.toArray()).filter(LineNumberNode.class::isInstance).count())
              .isEqualTo(0);

    // Ensure presents of TableSwitchInsnNode
    Assertions.assertThat(actual).anyMatch(input -> input instanceof TableSwitchInsnNode);
    Assertions.assertThat(expected).anyMatch(input -> input instanceof TableSwitchInsnNode);

    // Don't use 'assertThatInstructionsIgnoreLineNumbers', because it is already cleaned
    AsmAssertions.assertThatInstructions(InsnListUtils.filterLineNumbers(actual))
                 .isEqualTo(expected);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
