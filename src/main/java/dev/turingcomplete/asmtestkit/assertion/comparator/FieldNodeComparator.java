package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.comparator._internal.IterableComparator;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Comparator;
import java.util.Objects;

import static dev.turingcomplete.asmtestkit.assertion.comparator._internal.ComparatorUtils.OBJECT_COMPARATOR;
import static dev.turingcomplete.asmtestkit.assertion.comparator._internal.ComparatorUtils.STRING_COMPARATOR;
import static java.util.Comparator.comparing;

/**
 * A comparison function to order {@link FieldNode}s.
 *
 * <p>Two {@code FieldNode}s will be considered as equal if their
 * name, descriptor, access, signature, value, (in)visible (type) annotations
 * and attributes are equal. Otherwise, they are ordered in the order of the
 * previous listing. For example, if they have different names in the
 * lexicographic order of their names; if they have the same name in the
 * lexicographic order of their descriptor; etc.
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

  private AccessComparator                                   accessComparator             = AccessComparator.INSTANCE;
  private Comparator<Iterable<? extends AnnotationNode>>     annotationNodeComparator     = AnnotationNodeComparator.ITERABLE_INSTANCE;
  private Comparator<Iterable<? extends TypeAnnotationNode>> typeAnnotationNodeComparator = TypeAnnotationNodeComparator.ITERABLE_INSTANCE;
  private Comparator<Iterable<? extends Attribute>>          attributesComparator         = AttributeComparator.ITERABLE_INSTANCE;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected FieldNodeComparator() {
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

  /**
   * Sets the used {@link AccessComparator}.
   *
   * <p>The default value is {@link AccessComparator#INSTANCE}.
   *
   * @param accessComparator an {@link AccessComparator}; never null.
   * @return {@code this} {@link FieldNodeComparator}; never null.
   */
  public FieldNodeComparator useAccessComparator(AccessComparator accessComparator) {
    this.accessComparator = Objects.requireNonNull(accessComparator);

    return this;
  }

  /**
   * Sets the used {@link Comparator} for an {@link Iterable} of
   * {@link AnnotationNode}s
   *
   * <p>The default value is {@link AnnotationNodeComparator#ITERABLE_INSTANCE}.
   *
   * @param annotationNodeComparator a {@link Comparator} for an {@link Iterable}
   *                                 of {@link AnnotationNode}s; never null.
   * @return {@code this} {@link FieldNodeComparator}; never null.
   */
  public FieldNodeComparator useAnnotationNodeComparator(Comparator<Iterable<? extends AnnotationNode>> annotationNodeComparator) {
    this.annotationNodeComparator = Objects.requireNonNull(annotationNodeComparator);

    return this;
  }

  /**
   * Sets the used {@link Comparator} for an {@link Iterable} of
   * {@link TypeAnnotationNode}s
   *
   * <p>The default value is {@link TypeAnnotationNodeComparator#ITERABLE_INSTANCE}.
   *
   * @param typeAnnotationNodeComparator a {@link Comparator} for an {@link Iterable}
   *                                     of {@link TypeAnnotationNode}s; never null.
   * @return {@code this} {@link FieldNodeComparator}; never null.
   */
  public FieldNodeComparator useTypeAnnotationNodeComparator(Comparator<Iterable<? extends TypeAnnotationNode>> typeAnnotationNodeComparator) {
    this.typeAnnotationNodeComparator = Objects.requireNonNull(typeAnnotationNodeComparator);

    return this;
  }

  /**
   * Sets the used {@link Comparator} for an {@link Iterable} of
   * {@link Attribute}s
   *
   * <p>The default value is {@link AttributeComparator#ITERABLE_INSTANCE}.
   *
   * @param attributesComparator a {@link Comparator} for an {@link Iterable}
   *                             of {@link Attribute}s; never null.
   * @return {@code this} {@link FieldNodeComparator}; never null.
   */
  public FieldNodeComparator useAttributeComparator(Comparator<Iterable<? extends Attribute>> attributesComparator) {
    this.attributesComparator = Objects.requireNonNull(attributesComparator);

    return this;
  }

  @Override
  public int doCompare(FieldNode first, FieldNode second) {
    return comparing((FieldNode fieldNode) -> fieldNode.name, STRING_COMPARATOR)
            .thenComparing((FieldNode fieldNode) -> fieldNode.desc, STRING_COMPARATOR)
            .thenComparing((FieldNode fieldNode) -> fieldNode.access, accessComparator)
            .thenComparing((FieldNode fieldNode) -> fieldNode.signature, STRING_COMPARATOR)
            .thenComparing((FieldNode fieldNode) -> fieldNode.value, OBJECT_COMPARATOR)
            .thenComparing((FieldNode fieldNode) -> fieldNode.visibleAnnotations, annotationNodeComparator)
            .thenComparing((FieldNode fieldNode) -> fieldNode.invisibleAnnotations, annotationNodeComparator)
            .thenComparing((FieldNode fieldNode) -> fieldNode.visibleTypeAnnotations, typeAnnotationNodeComparator)
            .thenComparing((FieldNode fieldNode) -> fieldNode.invisibleTypeAnnotations, typeAnnotationNodeComparator)
            .thenComparing((FieldNode fieldNode) -> fieldNode.attrs, attributesComparator)
            .compare(first, second);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
