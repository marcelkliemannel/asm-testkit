package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.comparator._internal.IterableComparator;
import dev.turingcomplete.asmtestkit.assertion.representation.AttributeRepresentation;
import org.objectweb.asm.Attribute;

import java.util.Comparator;

/**
 * A comparison function to order {@link Attribute}s.
 *
 * <p>Two {@code Attribute}s will be considered as equal if their
 * {@link AttributeRepresentation}s are equal. Otherwise, they will be ordered
 * based on the lexicographical order of their {@code AttributeRepresentation}.
 */
public class AttributeComparator extends AsmComparator<Attribute> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link AttributeComparator} instance.
   */
  public static final AttributeComparator INSTANCE = create();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link Attribute}s.
   */
  public static final Comparator<Iterable<? extends Attribute>> ITERABLE_INSTANCE = new IterableComparator<>(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AttributeComparator() {
    super(AttributeComparator.class, Attribute.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link AttributeComparator} instance.
   *
   * @return a new {@link AttributeComparator}; never null;
   */
  public static AttributeComparator create() {
    return new AttributeComparator();
  }

  @Override
  public int doCompare(Attribute first, Attribute second) {
    return asmRepresentations.toStringOf(first).compareTo(asmRepresentations.toStringOf(second));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
