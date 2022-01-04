package dev.turingcomplete.asmtestkit.assertion.comperator;

import dev.turingcomplete.asmtestkit.assertion.representation.AttributeRepresentation;
import org.objectweb.asm.Attribute;

/**
 * A comparison function to order {@link Attribute}s.
 *
 * <p>Two {@code Attribute}s will be considered as equal if their
 * {@link AttributeRepresentation} are equal. Otherwise, they will be ordered
 * based on the lexicographical order of their {@code AttributeRepresentation}.
 */
public final class AttributeComparator extends AsmComparator<Attribute> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  private static final AttributeComparator INSTANCE = new AttributeComparator();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Gets a reusable {@link AttributeComparator} instance.
   *
   * @return a {@link AttributeComparator} instance; never null.
   */
  public static AttributeComparator instance() {
    return INSTANCE;
  }

  @Override
  public int doCompare(Attribute first, Attribute second) {
    return AttributeRepresentation.instance().toStringOf(first)
                                  .compareTo(AttributeRepresentation.instance().toStringOf(second));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
