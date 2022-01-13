package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.asmutils._internal.TextifierUtils;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.util.TraceMethodVisitor;

/**
 * Creates a {@link String} representation of a {@link AbstractInsnNode}.
 *
 * <p>Example output:
 * <pre>{@code BIPUSH 42 (Opcode: 16)}</pre>
 * <pre>{@code LOOKUPSWITCH (Opcode: 171)
 *  1: L0
 *  2: L1
 *  default: L2}</pre>
 *
 * <p>The simplified output will not have the appended opcode information.
 */
public class InstructionRepresentation extends AsmRepresentation<AbstractInsnNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link InstructionRepresentation} instance.
   */
  public static final InstructionRepresentation INSTANCE = new InstructionRepresentation();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public InstructionRepresentation() {
    super(AbstractInsnNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  protected String createSimplifiedRepresentation(AbstractInsnNode abstractInsnNode) {
    return TextifierUtils.toString(textifier -> {
      textifier.setTab2(0);
      textifier.setTab3(1);
      abstractInsnNode.accept(new TraceMethodVisitor(textifier));
    }).replaceAll("[\n\r]$", "");
  }

  @Override
  protected String createRepresentation(AbstractInsnNode abstractInsnNode) {
    String textifiedInstruction = createSimplifiedRepresentation(abstractInsnNode);
    return appendOpcode(abstractInsnNode.getOpcode(), textifiedInstruction);
  }

  static String appendOpcode(int opcode, String textifiedInstruction) {
    String result = textifiedInstruction;
    if (opcode >= 0) {
      String opcodeRepresentation = " (Opcode: " + opcode + ")";

      int indexOfFirstNewLine = result.indexOf("\n");
      if (indexOfFirstNewLine >= 0) {
        // In case the instruction has multiple line, the opcode will be
        // appended to the first one.
        result = result.replaceFirst("\n", opcodeRepresentation + "\n");
      }
      else {
        result += opcodeRepresentation;
      }
    }

    return result;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
