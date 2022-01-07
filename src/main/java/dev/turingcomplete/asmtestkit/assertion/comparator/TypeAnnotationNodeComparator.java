package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.comparator._internal.IterableComparator;
import dev.turingcomplete.asmtestkit.assertion.representation.TypeAnnotationNodeRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.TypePathRepresentation;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Comparator;
import java.util.Objects;

/**
 * A comparison function to order {@link TypeAnnotationNode}s.
 *
 * <p>Two {@code AnnotationNode}s will be considered as equal if their
 * descriptors, values (order is ignored), type references and type paths are
 * equal. If their values are not equal they will be ordered based on the
 * lexicographical order of their {@link TypeAnnotationNodeRepresentation}s.
 */
public class TypeAnnotationNodeComparator extends AbstractAnnotationNodeComparator<TypeAnnotationNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link TypeAnnotationNodeComparator} instance.
   */
  public static final TypeAnnotationNodeComparator INSTANCE = new TypeAnnotationNodeComparator();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link TypeAnnotationNode}s.
   */
  public static final Comparator<Iterable<? extends TypeAnnotationNode>> ITERABLE_INSTANCE = new IterableComparator<>(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private TypePathComparator typePathComparator = TypePathComparator.INSTANCE;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public TypeAnnotationNodeComparator() {
    super(TypeAnnotationNodeRepresentation.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Sets the used {@link TypeAnnotationNodeRepresentation}.
   *
   * <p>The default value is {@link TypeAnnotationNodeRepresentation#INSTANCE}.
   *
   * @param typeAnnotationNodeRepresentation a {@link TypeAnnotationNodeRepresentation};
   *                                        never null.
   * @return {@code this} {@link TypeAnnotationNodeComparator}; never null.
   */
  public TypeAnnotationNodeComparator useAnnotationNodeRepresentation(TypeAnnotationNodeRepresentation typeAnnotationNodeRepresentation) {
    useAbstractAnnotationNodeRepresentation(Objects.requireNonNull(typeAnnotationNodeRepresentation));

    return this;
  }

  /**
   * Sets the used {@link TypePathComparator}.
   *
   * <p>The default value is {@link TypePathComparator#INSTANCE}.
   *
   * @param typePathComparator a {@link TypePathRepresentation}; never null.
   * @return {@code this} {@link TypeAnnotationNodeComparator}; never null.
   */
  public TypeAnnotationNodeComparator useTypePathComparator(TypePathComparator typePathComparator) {
    this.typePathComparator = Objects.requireNonNull(typePathComparator);

    return this;
  }

  @Override
  protected int doCompare(TypeAnnotationNode first, TypeAnnotationNode second) {
    int annotationNodeCompare = super.doCompare(first, second);
    if (annotationNodeCompare != 0) {
      return annotationNodeCompare;
    }

    return Comparator.comparing((TypeAnnotationNode typeAnnotationNode) -> typeAnnotationNode.typeRef, integerComparator())
                     .thenComparing((TypeAnnotationNode typeAnnotationNode) -> typeAnnotationNode.typePath, typePathComparator)
                     .compare(first, second);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
