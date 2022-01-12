package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.asmutils._internal.TextifierUtils;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.util.TraceMethodVisitor;

/**
 * Creates a {@link String} representation of a {@link AbstractInsnNode}.
 *
 * <p>Example output:
 * <pre>{@code @java.lang.Deprecated(forRemoval=true)
 * (131073) public deprecated int myField = 5
 * }</pre>
 *
 * <p>Example simplified output: {@code (131073) public deprecated int myField}.
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
    return TextifierUtils.textify(textifier -> {
      textifier.setTab2(0);
      textifier.setTab3(1);
      abstractInsnNode.accept(new TraceMethodVisitor(textifier));
    }).replaceAll("[\n\r]$", "");
  }

  @Override
  protected String createRepresentation(AbstractInsnNode abstractInsnNode) {
    String textifiedInstruction = createSimplifiedRepresentation(abstractInsnNode);
    if (abstractInsnNode.getOpcode() >= 0) {
      String opcodeRepresentation = " (Opcode: " + abstractInsnNode.getOpcode() + ")";

      int indexOfFirstNewLine = textifiedInstruction.indexOf("\n");
      if (indexOfFirstNewLine >= 0) {
        // In case the instruction has multiple line, the opcode will be
        // appended to the first one.
        textifiedInstruction = textifiedInstruction.replaceFirst("\n", opcodeRepresentation + "\n");
      }
      else {
        textifiedInstruction += opcodeRepresentation;
      }
    }
    return textifiedInstruction;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
