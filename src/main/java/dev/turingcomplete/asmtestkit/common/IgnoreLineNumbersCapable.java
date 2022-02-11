package dev.turingcomplete.asmtestkit.common;

import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;

/**
 * Describes an entity which can exclude {@link LineNumberNode} from its tasks.
 *
 * @param <S> the type of {@code this}.
 */
public interface IgnoreLineNumbersCapable<S> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Exclude {@link LineNumberNode}s (and its associated {@link LabelNode}s)
   * from the comparison.
   *
   * @return {@code this} {@link S}; never null.
   */
  S ignoreLineNumbers();

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
