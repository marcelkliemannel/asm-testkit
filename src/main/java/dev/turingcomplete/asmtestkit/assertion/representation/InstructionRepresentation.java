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
    return TextifierUtils.textify(textifier -> abstractInsnNode.accept(new TraceMethodVisitor(textifier))).trim();
  }

  @Override
  protected String createRepresentation(AbstractInsnNode abstractInsnNode) {
    String textifiedInstruction = createSimplifiedRepresentation(abstractInsnNode);
    if (abstractInsnNode.getOpcode() >= 0) {
      textifiedInstruction += " (Opcode: " + abstractInsnNode.getOpcode() + ")";
    }
    return textifiedInstruction;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
