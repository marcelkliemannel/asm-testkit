package dev.turingcomplete.asmtestkit.comparator;

import dev.turingcomplete.asmtestkit.common.DefaultLabelIndexLookup;
import dev.turingcomplete.asmtestkit.common.LabelIndexLookup;
import dev.turingcomplete.asmtestkit.common.IgnoreLineNumbersCapable;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;

import java.util.Comparator;
import java.util.Iterator;

import static dev.turingcomplete.asmtestkit.asmutils.InsnListUtils.extractLabelIndices;
import static dev.turingcomplete.asmtestkit.asmutils.InsnListUtils.filterLineNumbers;
import static org.assertj.core.util.IterableUtil.sizeOf;

/**
 * A comparison function to order an {@link InsnList}.
 *
 * <p>Two {@code InsnList}s will be considered as equal if they have the same
 * size and equal {@link AbstractInsnNode}s (using {@link InstructionComparator}
 * for comparison).
 */
public class InsnListComparator
        extends AbstractWithLabelIndexAsmComparator<Iterable<? extends AbstractInsnNode>>
        implements IgnoreLineNumbersCapable<InsnListComparator> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link InsnListComparator} instance.
   */
  public static final InsnListComparator INSTANCE = create();

  /**
   * A reusable {@link InsnListComparator} instance, which excludes line numbers
   * from the comparison.
   */
  public static final InsnListComparator INSTANCE_IGNORE_LINE_NUMBERS = create().ignoreLineNumbers();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private boolean ignoreLineNumbers = false;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected InsnListComparator() {
    super(InsnListComparator.class, Iterable.class);
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
   * Exclude {@link LineNumberNode}s (and its associated {@link LabelNode}s)
   * from the comparison.
   *
   * @return {@code this} {@link InsnListComparator}; never null.
   */
  @Override
  public InsnListComparator ignoreLineNumbers() {
    this.ignoreLineNumbers = true;

    return this;
  }

  @Override
  protected int doCompare(Iterable<? extends AbstractInsnNode> first, Iterable<? extends AbstractInsnNode> second) {
    DefaultLabelIndexLookup labelIndexLookup = DefaultLabelIndexLookup.create(extractLabelIndices(first));
    labelIndexLookup.putAll(extractLabelIndices(second));
    return doCompare(first, second, labelIndexLookup);
  }

  @Override
  protected int doCompare(Iterable<? extends AbstractInsnNode> first,
                          Iterable<? extends AbstractInsnNode> second,
                          LabelIndexLookup labelIndexLookup) {

    // Clear line numbers
    if (ignoreLineNumbers) {
      first = filterLineNumbers(first);
      second = filterLineNumbers(second);
    }

    // Compare sizes
    int firstSize = first instanceof InsnList ? ((InsnList) first).size() : sizeOf(first);
    int secondSize = second instanceof InsnList ? ((InsnList) second).size() : sizeOf(second);
    if (firstSize != secondSize) {
      return firstSize - secondSize;
    }

    extractLabelIndices(first).forEach(labelIndexLookup::putIfUnknown);
    extractLabelIndices(second).forEach(labelIndexLookup::putIfUnknown);

    // Compare each instruction
    Comparator<AbstractInsnNode> instructionComparator = asmComparators.elementComparator(AbstractInsnNode.class);
    Iterator<? extends AbstractInsnNode> secondIterator = second.iterator();
    for (AbstractInsnNode firstInstruction : first) {
      AbstractInsnNode secondInstruction = secondIterator.next();
      int instructionCompare = compareInstructions(instructionComparator, firstInstruction, secondInstruction, labelIndexLookup);
      if (instructionCompare != 0) {
        return instructionCompare;
      }
    }

    return 0;
  }

  private int compareInstructions(Comparator<AbstractInsnNode> instructionComparator,
                                  AbstractInsnNode firstInstruction,
                                  AbstractInsnNode secondInstruction,
                                  LabelIndexLookup _labelIndexLookup) {

    if (instructionComparator instanceof WithLabelIndexAsmComparator) {
      return ((WithLabelIndexAsmComparator<AbstractInsnNode>) instructionComparator)
              .compare(firstInstruction, secondInstruction, _labelIndexLookup);
    }
    else {
      return instructionComparator.compare(firstInstruction, secondInstruction);
    }
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
