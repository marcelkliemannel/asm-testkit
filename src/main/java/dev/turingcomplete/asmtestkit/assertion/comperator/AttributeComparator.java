package dev.turingcomplete.asmtestkit.assertion.comperator;

import dev.turingcomplete.asmtestkit.assertion.comperator._internal.IterableComparator;
import dev.turingcomplete.asmtestkit.assertion.representation.AttributeRepresentation;
import org.objectweb.asm.Attribute;

import java.util.Comparator;
import java.util.Objects;

/**
 * A comparison function to order {@link Attribute}s.
 *
 * <p>Two {@code Attribute}s will be considered as equal if their
 * {@link AttributeRepresentation} are equal. Otherwise, they will be ordered
 * based on the lexicographical order of their {@code AttributeRepresentation}.
 */
public class AttributeComparator extends AsmComparator<Attribute> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  private static final AttributeComparator                       INSTANCE          = new AttributeComparator();
  private static final Comparator<Iterable<? extends Attribute>> ITERABLE_INSTANCE = new IterableComparator<>(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private AttributeRepresentation attributeRepresentation = AttributeRepresentation.instance();

  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Gets a reusable {@link AttributeComparator} instance.
   *
   * @return an {@link AttributeComparator} instance; never null.
   */
  public static AttributeComparator instance() {
    return INSTANCE;
  }

  /**
   * Gets a reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link Attribute}s.
   *
   * @return a {@link IterableComparator} instance; never null.
   */
  public static Comparator<Iterable<? extends Attribute>> iterableInstance() {
    return ITERABLE_INSTANCE;
  }

  /**
   * Sets the used {@link AttributeRepresentation}.
   *
   * <p>The default value is {@link AttributeRepresentation#instance()}.
   *
   * @param attributeRepresentation a {@link AttributeRepresentation}; never null.
   */
  public void setAttributeRepresentation(AttributeRepresentation attributeRepresentation) {
    this.attributeRepresentation = Objects.requireNonNull(attributeRepresentation);
  }

  @Override
  public int doCompare(Attribute first, Attribute second) {
    return attributeRepresentation.toStringOf(first).compareTo(attributeRepresentation.toStringOf(second));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
