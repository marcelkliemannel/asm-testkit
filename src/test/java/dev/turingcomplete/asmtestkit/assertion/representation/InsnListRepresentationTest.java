package dev.turingcomplete.asmtestkit.assertion.representation;

import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;

import java.io.IOException;

import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;
import static org.assertj.core.api.Assertions.assertThat;

class InsnListRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testToStringOf() throws IOException {
    @Language("Java")
    String myClass = "class MyClass {\n" +
                     "  void myMethod(int foo) {\n" +
                     "    System.out.println(1);\n\n" +
                     "    switch(foo) {\n" +
                     "      case 1: System.out.println(1);\nbreak;\n" +
                     "      case 2: System.out.println(2);\nbreak;\n" +
                     "    }\n" +
                     "  }\n" +
                     "}";
    InsnList instructions = create()
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods.get(1).instructions;

    assertThat(InsnListRepresentation.INSTANCE.doToStringOf(instructions))
            .isEqualTo("L0\n" +
                       "  LINENUMBER 3 L0\n" +
                       "  GETSTATIC java/lang/System.out : Ljava/io/PrintStream; (Opcode: 178)\n" +
                       "  ICONST_1 (Opcode: 4)\n" +
                       "  INVOKEVIRTUAL java/io/PrintStream.println (I)V (Opcode: 182)\n" +
                       "L1\n" +
                       "  LINENUMBER 5 L1\n" +
                       "  ILOAD 1 (Opcode: 21)\n" +
                       "  LOOKUPSWITCH (Opcode: 171)\n" +
                       "    1: L2\n" +
                       "    2: L3\n" +
                       "    default: L4\n" +
                       "L2\n" +
                       "  LINENUMBER 6 L2\n" +
                       "FRAME SAME\n" +
                       "  GETSTATIC java/lang/System.out : Ljava/io/PrintStream; (Opcode: 178)\n" +
                       "  ICONST_1 (Opcode: 4)\n" +
                       "  INVOKEVIRTUAL java/io/PrintStream.println (I)V (Opcode: 182)\n" +
                       "L5\n" +
                       "  LINENUMBER 7 L5\n" +
                       "  GOTO L4 (Opcode: 167)\n" +
                       "L3\n" +
                       "  LINENUMBER 8 L3\n" +
                       "FRAME SAME\n" +
                       "  GETSTATIC java/lang/System.out : Ljava/io/PrintStream; (Opcode: 178)\n" +
                       "  ICONST_2 (Opcode: 5)\n" +
                       "  INVOKEVIRTUAL java/io/PrintStream.println (I)V (Opcode: 182)\n" +
                       "L4\n" +
                       "  LINENUMBER 11 L4\n" +
                       "FRAME SAME\n" +
                       "  RETURN (Opcode: 177)\n" +
                       "L6");
  }


  @Test
  void testHideOpcodeToStringOf() throws IOException {
    @Language("Java")
    String myClass = "class MyClass {" +
                     "  void myMethod() {" +
                     "    System.out.println(1);" +
                     "  }" +
                     "}";
    InsnList instructions = create()
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods.get(1).instructions;

    assertThat(new InsnListRepresentation().hideOpcode().doToStringOf(instructions))
            .isEqualTo("L0\n" +
                       "  LINENUMBER 1 L0\n" +
                       "  GETSTATIC java/lang/System.out : Ljava/io/PrintStream;\n" +
                       "  ICONST_1\n" +
                       "  INVOKEVIRTUAL java/io/PrintStream.println (I)V\n" +
                       "  RETURN\n" +
                       "L1");
  }

  @Test
  void testToSimplifiedStringOf() {
    var instructions = new InsnList();

    assertThat(InsnListRepresentation.INSTANCE.doToSimplifiedStringOf(instructions))
            .isEqualTo("0 instructions");

    instructions.add(new InsnNode(Opcodes.RETURN));
    assertThat(InsnListRepresentation.INSTANCE.doToSimplifiedStringOf(instructions))
            .isEqualTo("1 instruction");

    instructions.clear();
    instructions.add(new LdcInsnNode("foo"));
    instructions.add(new InsnNode(Opcodes.RETURN));
    assertThat(InsnListRepresentation.INSTANCE.doToSimplifiedStringOf(instructions))
            .isEqualTo("2 instructions");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
