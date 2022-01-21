package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.comparator._internal.ComparatorUtils;
import dev.turingcomplete.asmtestkit.assertion.comparator._internal.IterableComparator;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.Comparator;
import java.util.Objects;

public class AnnotationDefaultValueComparator extends AsmComparator<Object> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link AnnotationDefaultValueComparator} instance.
   */
  public static final AnnotationDefaultValueComparator INSTANCE = new AnnotationDefaultValueComparator();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link Object}s.
   */
  public static final Comparator<Iterable<?>> ITERABLE_INSTANCE = new IterableComparator<>(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private AnnotationNodeComparator annotationNodeComparator = AnnotationNodeComparator.INSTANCE;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AnnotationDefaultValueComparator() {
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Sets the used {@link AnnotationNodeComparator}.
   *
   * <p>The default value is {@link AnnotationNodeComparator#INSTANCE}.
   *
   * @param annotationNodeComparator an {@link AnnotationNodeComparator}; never null.
   * @return {@code this} {@link AnnotationDefaultValueComparator}; never null.
   */
  public AnnotationDefaultValueComparator useAnnotationNodeComparator(AnnotationNodeComparator annotationNodeComparator) {
    this.annotationNodeComparator = Objects.requireNonNull(annotationNodeComparator);

    return this;
  }

  /**
   * Creates a new {@link AnnotationDefaultValueComparator} instance.
   *
   * @return a new {@link AnnotationDefaultValueComparator}; never null;
   */
  public static AnnotationDefaultValueComparator create() {
    return new AnnotationDefaultValueComparator();
  }

  @Override
  protected int doCompare(Object first, Object second) {
    if (first instanceof AnnotationNode && second instanceof AnnotationNode) {
      return annotationNodeComparator.compare((AnnotationNode) first, (AnnotationNode) second);
    }

    return ComparatorUtils.OBJECT_COMPARATOR.compare(first, second);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
