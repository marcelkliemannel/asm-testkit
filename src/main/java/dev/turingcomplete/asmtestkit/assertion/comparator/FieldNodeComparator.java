package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.representation.AttributeRepresentation;
import org.objectweb.asm.tree.FieldNode;

import static dev.turingcomplete.asmtestkit.assertion.comparator._internal.ComparatorUtils.OBJECT_COMPARATOR;
import static dev.turingcomplete.asmtestkit.assertion.comparator._internal.ComparatorUtils.STRING_COMPARATOR;
import static java.util.Comparator.comparing;

/**
 * A comparison function to order {@link FieldNode}s.
 *
 * <p>Two {@code FieldNode}s will be considered as equal if their
 * {@link AttributeRepresentation} are equal. Otherwise, they will be ordered
 * based on the lexicographical order of their {@code AttributeRepresentation}.
 */
public class FieldNodeComparator extends AsmComparator<FieldNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link FieldNodeComparator} instance.
   */
  public static final FieldNodeComparator INSTANCE = new FieldNodeComparator();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public int doCompare(FieldNode first, FieldNode second) {
    return comparing((FieldNode fieldNode) -> fieldNode.name, STRING_COMPARATOR)
            .thenComparing((FieldNode fieldNode) -> fieldNode.desc, STRING_COMPARATOR)
            .thenComparing((FieldNode fieldNode) -> fieldNode.access, AccessComparator.INSTANCE)
            .thenComparing((FieldNode fieldNode) -> fieldNode.signature, STRING_COMPARATOR)
            .thenComparing((FieldNode fieldNode) -> fieldNode.value, OBJECT_COMPARATOR)
            .thenComparing((FieldNode fieldNode) -> fieldNode.visibleAnnotations, AnnotationNodeComparator.ITERABLE_INSTANCE)
            .thenComparing((FieldNode fieldNode) -> fieldNode.invisibleAnnotations, AnnotationNodeComparator.ITERABLE_INSTANCE)
            .thenComparing((FieldNode fieldNode) -> fieldNode.visibleTypeAnnotations, TypeAnnotationNodeComparator.ITERABLE_INSTANCE)
            .thenComparing((FieldNode fieldNode) -> fieldNode.invisibleTypeAnnotations, TypeAnnotationNodeComparator.ITERABLE_INSTANCE)
            .thenComparing((FieldNode fieldNode) -> fieldNode.attrs, AttributeComparator.ITERABLE_INSTANCE)
            .compare(first, second);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
