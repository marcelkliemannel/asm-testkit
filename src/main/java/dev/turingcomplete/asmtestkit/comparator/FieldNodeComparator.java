package dev.turingcomplete.asmtestkit.comparator;

import dev.turingcomplete.asmtestkit.comparator._internal.IterableComparator;
import dev.turingcomplete.asmtestkit.node.AccessFlags;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Comparator;

import static dev.turingcomplete.asmtestkit.comparator._internal.ComparatorUtils.OBJECT_COMPARATOR;
import static dev.turingcomplete.asmtestkit.comparator._internal.ComparatorUtils.STRING_COMPARATOR;
import static java.util.Comparator.comparing;

/**
 * A comparison function to order {@link FieldNode}s.
 *
 * <p>Two {@code FieldNode}s will be considered as equal if all the
 * {@code public} {@link FieldNode} fields are equal. Otherwise, they will be
 * ordered by the comparison of the first non-matching field.
 */
public class FieldNodeComparator extends AsmComparator<FieldNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link FieldNodeComparator} instance.
   */
  public static final FieldNodeComparator INSTANCE = create();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link FieldNode}s.
   */
  public static final Comparator<Iterable<? extends FieldNode>> ITERABLE_INSTANCE = new IterableComparator<>(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected FieldNodeComparator() {
    super(FieldNodeComparator.class, FieldNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link FieldNodeComparator} instance.
   *
   * @return a new {@link FieldNodeComparator}; never null;
   */
  public static FieldNodeComparator create() {
    return new FieldNodeComparator();
  }

  @Override
  public int doCompare(FieldNode first, FieldNode second) {
    return comparing((FieldNode fieldNode) -> fieldNode.name, STRING_COMPARATOR)
            .thenComparing((FieldNode fieldNode) -> fieldNode.desc, STRING_COMPARATOR)
            .thenComparing((FieldNode fieldNode) -> AccessFlags.forField(fieldNode.access), asmComparators.elementComparator(AccessFlags.class))
            .thenComparing((FieldNode fieldNode) -> fieldNode.signature, STRING_COMPARATOR)
            .thenComparing((FieldNode fieldNode) -> fieldNode.value, OBJECT_COMPARATOR)
            .thenComparing((FieldNode fieldNode) -> fieldNode.visibleAnnotations, asmComparators.iterableComparator(AnnotationNode.class))
            .thenComparing((FieldNode fieldNode) -> fieldNode.invisibleAnnotations, asmComparators.iterableComparator(AnnotationNode.class))
            .thenComparing((FieldNode fieldNode) -> fieldNode.visibleTypeAnnotations, asmComparators.iterableComparator(TypeAnnotationNode.class))
            .thenComparing((FieldNode fieldNode) -> fieldNode.invisibleTypeAnnotations, asmComparators.iterableComparator(TypeAnnotationNode.class))
            .thenComparing((FieldNode fieldNode) -> fieldNode.attrs, asmComparators.iterableComparator(Attribute.class))
            .compare(first, second);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
