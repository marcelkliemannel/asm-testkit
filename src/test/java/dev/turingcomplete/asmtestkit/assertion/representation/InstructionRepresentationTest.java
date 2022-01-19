package dev.turingcomplete.asmtestkit.assertion.representation;

import org.assertj.core.api.Assertions;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LookupSwitchInsnNode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;

class InstructionRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  @Language("Java")
  private static final String MY_CLASS = "import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA;" +
                                         "class MyClass {" +
                                         "  void myMethod() {" +
                                         "    System.out.println(42);" +
                                         "  }" +
                                         "}";

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testToStringOf() throws IOException {
    InsnList instructions = create()
            .addJavaInputSource(MY_CLASS)
            .compile()
            .readClassNode("MyClass")
            .methods.get(1).instructions;

    List<String> expectedRepresentations = List.of("L0",
                                                   "LINENUMBER 1 L0",
                                                   "GETSTATIC java/lang/System.out : Ljava/io/PrintStream; // opcode: 178",
                                                   "BIPUSH 42 // opcode: 16",
                                                   "INVOKEVIRTUAL java/io/PrintStream.println (I)V // opcode: 182",
                                                   "RETURN // opcode: 177",
                                                   "L0");

    Assertions.assertThat(Arrays.stream(instructions.toArray()).map(InstructionRepresentation.INSTANCE::toStringOf))
              .containsExactlyElementsOf(expectedRepresentations);
  }

  @Test
  void testSwitchIndentToStringOf() throws IOException {
    @Language("Java")
    String myClass = "class MyClass {" +
                        "  void myMethod(int foo) {" +
                        "    switch(foo) {" +
                        "      case 1: System.out.println(1); break;" +
                        "      case 2: System.out.println(2); break;" +
                        "    }" +
                        "  }" +
                        "}";
    InsnList instructions = create()
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods.get(1).instructions;

    Optional<AbstractInsnNode> lookupSwitch = Arrays.stream(instructions.toArray()).filter(instruction -> instruction instanceof LookupSwitchInsnNode).findFirst();
    Assertions.assertThat(lookupSwitch).isPresent();

    Assertions.assertThat(InstructionRepresentation.INSTANCE.toStringOf(lookupSwitch.get()))
              .isEqualTo("LOOKUPSWITCH // opcode: 171\n" +
                         " 1: L0\n" +
                         " 2: L1\n" +
                         " default: L2");
  }

  @Test
  void testToSimplifiedStringOf() throws IOException {
    InsnList instructions = create()
            .addJavaInputSource(MY_CLASS)
            .compile()
            .readClassNode("MyClass")
            .methods.get(1).instructions;

    List<String> expectedRepresentations = List.of("L0",
                                                   "LINENUMBER 1 L0",
                                                   "GETSTATIC java/lang/System.out : Ljava/io/PrintStream;",
                                                   "BIPUSH 42",
                                                   "INVOKEVIRTUAL java/io/PrintStream.println (I)V",
                                                   "RETURN",
                                                   "L0");

    Assertions.assertThat(Arrays.stream(instructions.toArray()).map(InstructionRepresentation.INSTANCE::doToSimplifiedStringOf))
              .containsExactlyElementsOf(expectedRepresentations);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
