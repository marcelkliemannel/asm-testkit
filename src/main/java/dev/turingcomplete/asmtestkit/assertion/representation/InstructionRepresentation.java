package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.asmutils._internal.TextifierUtils;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.util.TraceMethodVisitor;

import static dev.turingcomplete.asmtestkit.assertion.representation._internal.RepresentationUtils.appendToFirstLine;

/**
 * An AssertJ {@link Representation} for an {@link AbstractInsnNode}.
 *
 * <p>Example output:
 * <pre>{@code BIPUSH 42 // opcode: 16}</pre>
 * <pre>{@code LOOKUPSWITCH // opcode: 171
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

  protected InstructionRepresentation() {
    super(AbstractInsnNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link InstructionRepresentation} instance.
   *
   * @return a new {@link InstructionRepresentation}; never null;
   */
  public static InstructionRepresentation create() {
    return new InstructionRepresentation();
  }

  @Override
  protected String doToSimplifiedStringOf(AbstractInsnNode abstractInsnNode) {
    return TextifierUtils.toString(textifier -> {
      textifier.setTab2(0);
      textifier.setTab3(1);
      abstractInsnNode.accept(new TraceMethodVisitor(textifier));
    }).replaceAll("[\n\r]$", "");
  }

  @Override
  protected String doToStringOf(AbstractInsnNode abstractInsnNode) {
    String textifiedInstruction = doToSimplifiedStringOf(abstractInsnNode);
    return appendOpcode(abstractInsnNode.getOpcode(), textifiedInstruction);
  }

  static String appendOpcode(int opcode, String textifiedInstruction) {
    if (opcode >= 0) {
      return appendToFirstLine(textifiedInstruction, " // opcode: " + opcode);
    }
    else {
      return textifiedInstruction;
    }
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
