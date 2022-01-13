package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.compile.CompilationResult;
import org.assertj.core.api.Assertions;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LineNumberNode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static dev.turingcomplete.asmtestkit.assertion.comparator.InsnListComparator.INSTANCE;
import static dev.turingcomplete.asmtestkit.assertion.comparator.InsnListComparator.INSTANCE_IGNORE_LINE_NUMBERS;
import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;
import static org.assertj.core.api.Assertions.assertThat;

class InsnListComparatorTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCompare() {
    //noinspection EqualsWithItself
    assertThat(INSTANCE.compare(List.of(), List.of()))
            .isEqualTo(0);

    assertThat(INSTANCE.compare(List.of(new InsnNode(Opcodes.RETURN)), List.of(new InsnNode(Opcodes.RETURN), new InsnNode(Opcodes.IADD))))
            .isLessThanOrEqualTo(-1);

    assertThat(INSTANCE.compare(List.of(new InsnNode(Opcodes.RETURN), new InsnNode(Opcodes.IADD)), List.of(new InsnNode(Opcodes.RETURN))))
            .isGreaterThanOrEqualTo(1);
  }

  @Test
  void testCompareIgnoreLineNumbers() throws IOException {
    @Language("Java")
    String firstMyClass = "class FirstMyClass {" +
                          "  void myMethod() {\n" +
                          "    System.out.println(1);\n" +
                          "  }" +
                          "}";
    @Language("Java")
    String secondMyClass = "class SecondMyClass {" +
                           "  void myMethod() {" +
                           "    System.out.println(1);" +
                           "  }" +
                           "}";
    CompilationResult result = create()
            .addJavaInputSource(firstMyClass)
            .addJavaInputSource(secondMyClass)
            .compile();

    InsnList firstInstructions = result.readClassNode("FirstMyClass").methods.get(1).instructions;
    InsnList secondInstructions = result.readClassNode("SecondMyClass").methods.get(1).instructions;

    // Check that first contains more line numbers than second
    Assertions.assertThat(Arrays.stream(firstInstructions.toArray()).filter(LineNumberNode.class::isInstance).count())
              .isGreaterThan(Arrays.stream(secondInstructions.toArray()).filter(LineNumberNode.class::isInstance).count());

    assertThat(INSTANCE_IGNORE_LINE_NUMBERS.compare(firstInstructions, secondInstructions))
            .isEqualTo(0);
  }

  @Test
  void testCompareIncludeLineNumbers() throws IOException {
    @Language("Java")
    String firstMyClass = "class MyClass {" +
                          "  void myMethod() {\n" +
                          "    System.out.println(1);\n" +
                          "  }" +
                          "}";
    InsnList firstInstructions = create()
            .addJavaInputSource(firstMyClass)
            .compile()
            .readClassNode("MyClass")
            .methods.get(1).instructions;

    @Language("Java")
    String secondMyClass = "class MyClass {" +
                           "  void myMethod() {" +
                           "    System.out.println(1);" +
                           "  }" +
                           "}";
    InsnList secondInstructions = create()
            .addJavaInputSource(secondMyClass)
            .disableDebuggingInformation()
            .compile()
            .readClassNode("MyClass")
            .methods.get(1).instructions;

    // Check that first contains line numbers and second none
    Assertions.assertThat(Arrays.stream(firstInstructions.toArray()).filter(LineNumberNode.class::isInstance).count())
              .isEqualTo(2);
    Assertions.assertThat(Arrays.stream(secondInstructions.toArray()).filter(LineNumberNode.class::isInstance).count())
              .isEqualTo(0);

    assertThat(INSTANCE.compare(firstInstructions, secondInstructions))
            .isGreaterThan(0);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
