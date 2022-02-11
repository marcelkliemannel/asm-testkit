package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.comparator.InsnListComparator;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;

import java.util.Comparator;

public class InsnListAssert extends AsmIterableAssert<InsnListAssert, AbstractInsnNode, InstructionAssert> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected InsnListAssert(Iterable<? extends AbstractInsnNode> actual) {
    super(actual, InsnListAssert.class, AsmAssertions::assertThat);

    //noinspection ResultOfMethodCallIgnored
    as("Instructions");
    setComparators(false);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Exclude {@link LineNumberNode}s (and its associated {@link LabelNode}s)
   * from the comparison.
   *
   * <p>This method will override the previously set {@link Comparator}s.
   *
   * @return {@code this} {@link InsnListAssert}; never null.
   */
  public InsnListAssert ignoreLineNumbers() {
    setComparators(true);

    return this;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  @SuppressWarnings("ResultOfMethodCallIgnored")
  private void setComparators(boolean ignoreLineNumbers) {
    if (!ignoreLineNumbers) {
      usingComparator(InsnListComparator.INSTANCE);
    }
    else {
      usingComparator(InsnListComparator.INSTANCE_IGNORE_LINE_NUMBERS);
    }
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
