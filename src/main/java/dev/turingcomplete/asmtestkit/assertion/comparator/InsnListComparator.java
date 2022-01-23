package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.asmutils.InsnListUtils;
import dev.turingcomplete.asmtestkit.assertion.LabelNameLookup;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;

import java.util.Iterator;
import java.util.Objects;

import static dev.turingcomplete.asmtestkit.asmutils.InsnListUtils.numerateLabels;
import static org.assertj.core.util.IterableUtil.sizeOf;

/**
 * A comparison function to order an {@link InsnList}.
 *
 * <p>Two {@code InsnList}s will be considered as equal if they have the same
 * size and equal {@link AbstractInsnNode}s (using {@link InstructionComparator}
 * for comparison).
 */
public class InsnListComparator implements WithLabelNamesAsmComparator<Iterable<? extends AbstractInsnNode>> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link InsnListComparator} instance.
   */
  public static final InsnListComparator INSTANCE = create();

  /**
   * A reusable {@link InsnListComparator} instance, which excludes line numbers
   * from the comparison.
   */
  public static final InsnListComparator INSTANCE_IGNORE_LINE_NUMBERS = new InsnListComparator().ignoreLineNumbers();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private InstructionComparator instructionComparator = InstructionComparator.INSTANCE;
  private boolean               ignoreLineNumbers     = false;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected InsnListComparator() {
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link InsnListComparator} instance.
   *
   * @return a new {@link InsnListComparator}; never null;
   */
  public static InsnListComparator create() {
    return new InsnListComparator();
  }

  /**
   * Sets the used {@link InstructionComparator}.
   *
   * <p>The default value is {@link InstructionComparator#INSTANCE}.
   *
   * @param instructionComparator an {@link InstructionComparator}; never null.
   * @return {@code this} {@link InsnListComparator}; never null.
   */
  public InsnListComparator useInstructionComparator(InstructionComparator instructionComparator) {
    this.instructionComparator = Objects.requireNonNull(instructionComparator);

    return this;
  }

  /**
   * Exclude {@link LineNumberNode}s (and its associated {@link LabelNode}s)
   * from the comparison.
   *
   * @return {@code this} {@link InsnListComparator}; never null.
   */
  public InsnListComparator ignoreLineNumbers() {
    this.ignoreLineNumbers = true;

    return this;
  }

  @Override
  public int compare(Iterable<? extends AbstractInsnNode> first, Iterable<? extends AbstractInsnNode> second) {
    return compare(first, second, LabelNameLookup.EMPTY);
  }

  @Override
  public int compare(Iterable<? extends AbstractInsnNode> first,
                     Iterable<? extends AbstractInsnNode> second,
                     LabelNameLookup labelNameLookup) {

    // Clear line numbers
    if (ignoreLineNumbers) {
      first = InsnListUtils.filterLineNumbers(first);
      second = InsnListUtils.filterLineNumbers(second);
    }

    // Compare sizes
    int firstSize = first instanceof InsnList ? ((InsnList) first).size() : sizeOf(first);
    int secondSize = second instanceof InsnList ? ((InsnList) second).size() : sizeOf(second);
    if (firstSize != secondSize) {
      return firstSize - secondSize;
    }

    LabelNameLookup _labelNameLookup = labelNameLookup.merge(createLabelNameLookup(first, second));

    // Compare each instruction
    Iterator<? extends AbstractInsnNode> secondIterator = second.iterator();
    for (AbstractInsnNode firstInstruction : first) {
      AbstractInsnNode secondInstruction = secondIterator.next();
      int instructionCompare = instructionComparator.compare(firstInstruction, secondInstruction, _labelNameLookup);
      if (instructionCompare != 0) {
        return instructionCompare;
      }
    }

    return 0;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private LabelNameLookup createLabelNameLookup(Iterable<? extends AbstractInsnNode> first,
                                                Iterable<? extends AbstractInsnNode> second) {

    return LabelNameLookup.create(numerateLabels(first)).merge(LabelNameLookup.create(numerateLabels(second)));
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
