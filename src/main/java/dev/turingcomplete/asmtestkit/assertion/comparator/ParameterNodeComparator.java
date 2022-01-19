package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.comparator._internal.ComparatorUtils;
import dev.turingcomplete.asmtestkit.assertion.comparator._internal.IterableComparator;
import org.objectweb.asm.tree.ParameterNode;

import java.util.Comparator;
import java.util.Objects;

/**
 * A comparison function to order {@link ParameterNode}s.
 *
 * <p>Two {@code ParameterNode}s will be considered as equal if their
 * names and access flags are equal.
 */
public class ParameterNodeComparator extends AsmComparator<ParameterNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link ParameterNodeComparator} instance.
   */
  public static final ParameterNodeComparator INSTANCE = new ParameterNodeComparator();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link ParameterNode}s.
   */
  public static final Comparator<Iterable<? extends ParameterNode>> ITERABLE_INSTANCE = new IterableComparator<>(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private AccessComparator accessComparator = AccessComparator.INSTANCE;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected ParameterNodeComparator() {
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link ParameterNodeComparator} instance.
   *
   * @return a new {@link ParameterNodeComparator}; never null;
   */
  public static ParameterNodeComparator create() {
    return new ParameterNodeComparator();
  }

  /**
   * Sets the used {@link AccessComparator}.
   *
   * <p>The default value is {@link AccessComparator#INSTANCE}.
   *
   * @param accessComparator an {@link AccessComparator}; never null.
   * @return {@code this} {@link ParameterNodeComparator}; never null.
   */
  public ParameterNodeComparator useAccessComparator(AccessComparator accessComparator) {
    this.accessComparator = Objects.requireNonNull(accessComparator);

    return this;
  }

  @Override
  protected int doCompare(ParameterNode first, ParameterNode second) {
    return Comparator.comparing((ParameterNode parameterNode) -> parameterNode.access, accessComparator)
                     .thenComparing((ParameterNode parameterNode) -> parameterNode.name, ComparatorUtils.STRING_COMPARATOR)
                     .compare(first, second);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
