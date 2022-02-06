package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.comparator.AttributeComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.LabelNodeComparator;
import dev.turingcomplete.asmtestkit.assertion.representation.InstructionRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.LabelNodeRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.tree.LabelNode;

import java.util.Comparator;

/**
 * An AssertJ {@link AbstractAssert} for a {@link LabelNode} which will
 * use the {@link LabelNodeComparator} to determine the equality.
 *
 * <p>An instance can be created via {@link AsmAssertions#assertThat(LabelNode)}.
 * Use {@link AsmAssertions#assertThatLabels(Iterable)} for multiple
 * {@code LabelNode}s.
 *
 * <p>To override the used {@link InstructionRepresentation} or
 * {@link AttributeComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 */
public class LabelNodeAssert extends AsmAssert<LabelNodeAssert, LabelNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes a {@link LabelNodeAssert}.
   *
   * @param actual the actual {@link LabelNode}; may be null.
   */
  protected LabelNodeAssert(LabelNode actual) {
    super("Label", actual, LabelNodeAssert.class, LabelNodeRepresentation.INSTANCE, LabelNodeComparator.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //
  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
