package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.representation.InstructionRepresentation;
import org.objectweb.asm.tree.AbstractInsnNode;

import java.util.Comparator;
import java.util.Objects;

/**
 * A comparison function to order {@link AbstractInsnNode}s.
 *
 * <p>Two {@code AbstractInsnNode}s will be considered as equal if their
 * {@link InstructionRepresentation}s are equal. Otherwise, they will be
 * ordered based on the lexicographical order of their
 * {@code InstructionRepresentation}.
 */
public class InstructionComparator implements Comparator<AbstractInsnNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link AccessComparator} instance.
   */
  public static final InstructionComparator INSTANCE = new InstructionComparator();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private InstructionRepresentation instructionRepresentation = InstructionRepresentation.INSTANCE;

  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Sets the used {@link InstructionRepresentation}.
   *
   * <p>The default value is {@link InstructionRepresentation#INSTANCE}.
   *
   * @param instructionRepresentation an {@link InstructionRepresentation};
   *                                  never null.
   * @return {@code this} {@link InstructionComparator}; never null.
   */
  public InstructionComparator useInstructionRepresentation(InstructionRepresentation instructionRepresentation) {
    this.instructionRepresentation = Objects.requireNonNull(instructionRepresentation);

    return this;
  }

  @Override
  public int compare(AbstractInsnNode first, AbstractInsnNode second) {
    return instructionRepresentation.toStringOf(first).compareTo(instructionRepresentation.toStringOf(second));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
