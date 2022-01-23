package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.LabelNameLookup;
import dev.turingcomplete.asmtestkit.assertion.representation.InstructionRepresentation;
import org.objectweb.asm.tree.AbstractInsnNode;

import java.util.Comparator;

/**
 * A comparison function to order {@link AbstractInsnNode}s.
 *
 * <p>Two {@code AbstractInsnNode}s will be considered as equal if their
 * {@link InstructionRepresentation}s are equal. Otherwise, they will be
 * ordered based on the lexicographical order of their
 * {@code InstructionRepresentation}.
 *
 * <p>Use {@link InsnListComparator} for a {@link Comparator} of an
 * {@link Iterable} of {@link AbstractInsnNode}s.
 */
public class InstructionComparator extends AbstractWithLabelNamesAsmComparator<AbstractInsnNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link AccessFlagsComparator} instance.
   */
  public static final InstructionComparator INSTANCE = create();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected InstructionComparator() {
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link InstructionComparator} instance.
   *
   * @return a new {@link InstructionComparator}; never null;
   */
  public static InstructionComparator create() {
    return new InstructionComparator();
  }

  @Override
  protected int doCompare(AbstractInsnNode first, AbstractInsnNode second, LabelNameLookup labelNameLookup) {
    return asmRepresentations.toStringOf(first, labelNameLookup)
                             .compareTo(asmRepresentations.toStringOf(second, labelNameLookup));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
