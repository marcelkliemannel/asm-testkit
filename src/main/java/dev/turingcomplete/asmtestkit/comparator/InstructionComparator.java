package dev.turingcomplete.asmtestkit.comparator;

import dev.turingcomplete.asmtestkit.assertion.LabelIndexLookup;
import dev.turingcomplete.asmtestkit.representation.InstructionRepresentation;
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
   * A reusable {@link AccessNodeComparator} instance.
   */
  public static final InstructionComparator INSTANCE = create();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected InstructionComparator() {
    super(InstructionComparator.class, AbstractInsnNode.class);
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
  protected int doCompare(AbstractInsnNode first, AbstractInsnNode second, LabelIndexLookup labelIndexLookup) {
    return asmRepresentations.toStringOf(first, labelIndexLookup)
                             .compareTo(asmRepresentations.toStringOf(second, labelIndexLookup));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
