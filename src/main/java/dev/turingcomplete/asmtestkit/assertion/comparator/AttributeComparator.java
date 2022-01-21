package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.comparator._internal.IterableComparator;
import dev.turingcomplete.asmtestkit.assertion.representation.AttributeRepresentation;
import org.objectweb.asm.Attribute;

import java.util.Comparator;
import java.util.Objects;

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

  private AttributeRepresentation attributeRepresentation = AttributeRepresentation.INSTANCE;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AttributeComparator() {
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

  /**
   * Sets the used {@link AttributeRepresentation}.
   *
   * <p>The default value is {@link AttributeRepresentation#INSTANCE}.
   *
   * @param attributeRepresentation an {@link AttributeRepresentation}; never null.
   * @return {@code this} {@link AttributeComparator}; never null.
   */
  public AttributeComparator useAttributeRepresentation(AttributeRepresentation attributeRepresentation) {
    this.attributeRepresentation = Objects.requireNonNull(attributeRepresentation);

    return this;
  }

  @Override
  public int doCompare(Attribute first, Attribute second) {
    return attributeRepresentation.toStringOf(first).compareTo(attributeRepresentation.toStringOf(second));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
