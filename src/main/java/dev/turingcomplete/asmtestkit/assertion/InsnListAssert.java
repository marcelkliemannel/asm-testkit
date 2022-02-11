package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.comparator.InsnListComparator;
import dev.turingcomplete.asmtestkit.comparator.MethodNodeComparator;
import dev.turingcomplete.asmtestkit.representation.InsnListRepresentation;
import org.assertj.core.api.AbstractIterableAssert;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;

import java.util.Comparator;

/**
 * An AssertJ {@link AbstractIterableAssert} for an {@link Iterable} of
 * {@link AbstractInsnNode}s (e.g., {@link InsnList}) which will use the
 * {@link MethodNodeComparator} to determine the equality.
 *
 * <p>An instance can be created via {@link AsmAssertions#assertThat(FieldNode)}
 * or {@link AsmAssertions#assertThatInstructions(Iterable)}.
 *
 * <p>To override the used {@link InsnListRepresentation} or
 * {@link InsnListComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 */
public class InsnListAssert extends AsmIterableAssert<InsnListAssert, AbstractInsnNode, InstructionAssert> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes an {@link InsnListAssert}.
   *
   * @param actual the actual {@link Iterable} of {@link AbstractInsnNode}s; may
   *               be null.
   */
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
