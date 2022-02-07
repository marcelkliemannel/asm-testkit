package dev.turingcomplete.asmtestkit.comparator;

import dev.turingcomplete.asmtestkit.asmutils.TypeUtils;
import dev.turingcomplete.asmtestkit.comparator._internal.IterableComparator;
import dev.turingcomplete.asmtestkit.node.AccessFlags;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.InnerClassNode;

import java.util.Comparator;

import static dev.turingcomplete.asmtestkit.comparator._internal.ComparatorUtils.STRING_COMPARATOR;
import static java.util.Comparator.comparing;

/**
 * A comparison function to order {@link InnerClassNode}s.
 *
 * <p>Two {@code InnerClassNode}s will be considered as equal if all the
 * {@code public} {@link InnerClassNode} fields are equal. Otherwise, they will
 * be ordered by the comparison of the first non-matching field.
 */
public class InnerClassNodeComparator extends AsmComparator<InnerClassNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link InnerClassNodeComparator} instance.
   */
  public static final InnerClassNodeComparator INSTANCE = create();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link InnerClassNode}s.
   */
  public static final Comparator<Iterable<? extends InnerClassNode>> ITERABLE_INSTANCE = new IterableComparator<>(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected InnerClassNodeComparator() {
    super(InnerClassNodeComparator.class, InnerClassNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link InnerClassNodeComparator} instance.
   *
   * @return a new {@link InnerClassNodeComparator}; never null;
   */
  public static InnerClassNodeComparator create() {
    return new InnerClassNodeComparator();
  }

  @Override
  protected int doCompare(InnerClassNode first, InnerClassNode second) {
    return comparing((InnerClassNode innerClassNode) -> TypeUtils.nameToTypeElseNull(innerClassNode.name), asmComparators.elementComparator(Type.class))
            .thenComparing((InnerClassNode innerClassNode) -> TypeUtils.nameToTypeElseNull(innerClassNode.outerName), asmComparators.elementComparator(Type.class))
            .thenComparing((InnerClassNode innerClassNode) -> innerClassNode.innerName, STRING_COMPARATOR)
            .thenComparing((InnerClassNode innerClassNode) -> AccessFlags.forClass(innerClassNode.access), asmComparators.elementComparator(AccessFlags.class))
            .compare(first, second);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
