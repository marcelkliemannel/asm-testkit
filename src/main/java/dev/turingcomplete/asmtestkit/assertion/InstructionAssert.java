package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.comparator.AttributeComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.InstructionComparator;
import dev.turingcomplete.asmtestkit.assertion.option.AssertOption;
import dev.turingcomplete.asmtestkit.assertion.representation.InstructionRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.tree.AbstractInsnNode;

import java.util.Comparator;

/**
 * An AssertJ {@link AbstractAssert} for an {@link AbstractInsnNode} which will
 * use the {@link InstructionRepresentation} to determine the equality.
 *
 * <p>To override the used {@link InstructionRepresentation} or
 * {@link AttributeComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 *
 * <p>An instance can be created via {@link AsmAssertions#assertThat(AbstractInsnNode)}.
 */
public class InstructionAssert extends AsmAssert<InstructionAssert, AbstractInsnNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes an {@link InstructionAssert}.
   *
   * <p>There are no direct supported {@link AssertOption}s yet.
   *
   * @param actual the actual {@link AbstractInsnNode}; may be null.
   * @param assertOptions an array of {@link AssertOption}s; never null.
   */
  public InstructionAssert(AbstractInsnNode actual, AssertOption... assertOptions) {
    super(actual, InstructionAssert.class, AbstractInsnNode.class, createSelfDescription(actual), assertOptions);

    info.useRepresentation(InstructionRepresentation.INSTANCE);
    //noinspection ResultOfMethodCallIgnored
    usingComparator(InstructionComparator.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //
  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private static String createSelfDescription(AbstractInsnNode actual) {
    return "Instruction: " + InstructionRepresentation.INSTANCE.toSimplifiedRepresentation(actual);
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
