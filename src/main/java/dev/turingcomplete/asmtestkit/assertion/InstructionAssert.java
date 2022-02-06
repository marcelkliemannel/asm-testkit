package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.comparator.AttributeComparator;
import dev.turingcomplete.asmtestkit.comparator.InstructionComparator;
import dev.turingcomplete.asmtestkit.representation.InstructionRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;

import java.util.Comparator;

/**
 * An AssertJ {@link AbstractAssert} for an {@link AbstractInsnNode} which will
 * use the {@link InstructionComparator} to determine the equality.
 *
 * <p>An instance can be created via {@link AsmAssertions#assertThat(AbstractInsnNode)}.
 * Use
 * <ul>
 *   <li>{@link AsmAssertions#assertThat(InsnList)},
 *   <li>{@link AsmAssertions#assertThatInstructions(Iterable)},
 *   <li>or {@link AsmAssertions#assertThatInstructionsIgnoreLineNumbers(Iterable)}
 * </ul>
 * for multiple {@code AbstractInsnNode}s.
 *
 * <p>To override the used {@link InstructionRepresentation} or
 * {@link AttributeComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 */
public class InstructionAssert extends AsmAssert<InstructionAssert, AbstractInsnNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes an {@link InstructionAssert}.
   *
   * @param actual the actual {@link AbstractInsnNode}; may be null.
   */
  protected InstructionAssert(AbstractInsnNode actual) {
    super("Instruction", actual, InstructionAssert.class, InstructionRepresentation.INSTANCE, InstructionComparator.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //
  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
