package dev.turingcomplete.asmtestkit.assertion.representation;

import org.assertj.core.api.Assertions;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.InsnList;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
  void testCreateRepresentation() throws IOException {
    InsnList instructions = create()
            .addJavaInputSource(MY_CLASS)
            .compile()
            .readClassNode("MyClass")
            .methods.get(1).instructions;

    List<String> expectedRepresentations = List.of("L0",
                                                   "LINENUMBER 1 L0",
                                                   "GETSTATIC java/lang/System.out : Ljava/io/PrintStream; (Opcode: 178)",
                                                   "BIPUSH 42 (Opcode: 16)",
                                                   "INVOKEVIRTUAL java/io/PrintStream.println (I)V (Opcode: 182)",
                                                   "RETURN (Opcode: 177)",
                                                   "L0");

    Assertions.assertThat(Arrays.stream(instructions.toArray()).map(InstructionRepresentation.INSTANCE::toStringOf))
              .containsExactlyElementsOf(expectedRepresentations);
  }

  @Test
  void testCreateSimplifiedRepresentation() throws IOException {
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

    Assertions.assertThat(Arrays.stream(instructions.toArray()).map(InstructionRepresentation.INSTANCE::createSimplifiedRepresentation))
              .containsExactlyElementsOf(expectedRepresentations);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
