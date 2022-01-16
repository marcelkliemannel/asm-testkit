package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.asmutils._internal.TextifierUtils;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.util.TraceMethodVisitor;

/**
 * An AssertJ {@link Representation} for an {@link InsnList}.
 *
 * <p>Example output:
 * <pre>{@code
 * L0
 *   LINENUMBER 3 L0
 *   GETSTATIC java/lang/System.out : Ljava/io/PrintStream; (Opcode: 178)
 *   ICONST_1 (Opcode: 4)
 *   INVOKEVIRTUAL java/io/PrintStream.println (I)V (Opcode: 182)
 * L1
 * }</pre>
 *
 * <p>Example simplified output: {@code 2 instructions}.
 */
public class InsnListRepresentation extends AsmRepresentation<InsnList> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link InsnListRepresentation} instance.
   */
  public static final InsnListRepresentation INSTANCE = new InsnListRepresentation();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private boolean hideOpcode = false;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  static {
    // An 'InsnList' is an 'Iterable' and would be handled in the 'toStringOf'
    registerFormatterForType(InsnList.class, InsnListRepresentation.INSTANCE::doToStringOf);
  }

  protected InsnListRepresentation() {
    super(InsnList.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link InsnListRepresentation} instance.
   *
   * @return a new {@link InsnListRepresentation}; never null;
   */
  public static InsnListRepresentation create() {
    return new InsnListRepresentation();
  }

  /**
   * Hides the opcode information for each instruction.
   *
   * @return {@code this} {@link FieldNodeRepresentation}; never null.
   */
  public InsnListRepresentation hideOpcode() {
    hideOpcode = true;

    return this;
  }

  @Override
  protected String doToSimplifiedStringOf(InsnList insnList) {
    return insnList.size() + " instruction" + (insnList.size() != 1 ? "s" : "");
  }

  @Override
  protected String doToStringOf(InsnList insnList) {
    var textifier = !hideOpcode ? new OpcodeAppendingTextifier() : new TextifierUtils.ExtendedTextifier();
    insnList.accept(new TraceMethodVisitor(textifier));
    return TextifierUtils.toString(textifier).replaceAll("[\n\r]$", "");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  private static class OpcodeAppendingTextifier extends TextifierUtils.ExtendedTextifier {

    @Override
    public void visitInsn(int opcode) {
      super.visitInsn(opcode);
      appendOpcode(opcode);
    }

    @Override
    public void visitIntInsn(int opcode, int operand) {
      super.visitIntInsn(opcode, operand);
      appendOpcode(opcode);
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
      super.visitVarInsn(opcode, var);
      appendOpcode(opcode);
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
      super.visitTypeInsn(opcode, type);
      appendOpcode(opcode);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
      super.visitFieldInsn(opcode, owner, name, descriptor);
      appendOpcode(opcode);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
      super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
      appendOpcode(opcode);
    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {
      super.visitJumpInsn(opcode, label);
      appendOpcode(opcode);
    }

    @Override
    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
      super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
      appendOpcode(Opcodes.INVOKEDYNAMIC);
    }

    @Override
    public void visitLdcInsn(Object value) {
      super.visitLdcInsn(value);
      appendOpcode(Opcodes.LDC);
    }

    @Override
    public void visitIincInsn(int var, int increment) {
      super.visitIincInsn(var, increment);
      appendOpcode(Opcodes.IINC);
    }

    @Override
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
      super.visitTableSwitchInsn(min, max, dflt, labels);
      appendOpcode(Opcodes.TABLESWITCH);
    }

    @Override
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
      super.visitLookupSwitchInsn(dflt, keys, labels);
      appendOpcode(Opcodes.LOOKUPSWITCH);
    }

    @Override
    public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
      super.visitMultiANewArrayInsn(descriptor, numDimensions);
      appendOpcode(Opcodes.MULTIANEWARRAY);
    }

    private void appendOpcode(int opcode) {
      int lastIndex = text.size() - 1;
      assert lastIndex >= 0;
      text.set(lastIndex, InstructionRepresentation.appendOpcode(opcode, (String) text.get(lastIndex)));
    }
  }
}
