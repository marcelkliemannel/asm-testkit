package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.LabelNameLookup;
import dev.turingcomplete.asmtestkit.assertion.comparator._internal.ComparatorUtils;
import dev.turingcomplete.asmtestkit.assertion.comparator._internal.WithLabelNamesIterableAsmComparator;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LocalVariableAnnotationNode;

import java.util.Comparator;
import java.util.Objects;

/**
 * A comparison function to order {@link LabelNode}s.
 *
 * <p>Two {@code Attribute}s will be considered as equal if their
 * {@link LabelNode#getLabel()}s are equal.
 */
public class LabelNodeComparator extends AbstractWithLabelNamesAsmComparator<LabelNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link LabelNodeComparator} instance.
   */
  public static final LabelNodeComparator INSTANCE = create();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link LabelNode}s.
   */
  public static final Comparator<Iterable<? extends LabelNode>> ITERABLE_INSTANCE = WithLabelNamesIterableAsmComparator.create(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected LabelNodeComparator() {
    super(LabelNodeComparator.class, LabelNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link LabelNodeComparator} instance.
   *
   * @return a new {@link LabelNodeComparator}; never null;
   */
  public static LabelNodeComparator create() {
    return new LabelNodeComparator();
  }

  @Override
  protected int doCompare(LabelNode first, LabelNode second) {
    return ComparatorUtils.OBJECT_COMPARATOR.compare(first.getLabel(), second.getLabel());
  }

  @Override
  protected int doCompare(LabelNode first, LabelNode second, LabelNameLookup labelNameLookup) {
    Objects.requireNonNull(labelNameLookup);

    var firstName = labelNameLookup.find(first.getLabel()).orElse(null);
    var secondName = labelNameLookup.find(second.getLabel()).orElse(null);
    return ComparatorUtils.OBJECT_COMPARATOR.compare(firstName != null ? firstName : first.getLabel(),
                                                     secondName != null ? secondName : second.getLabel());
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
