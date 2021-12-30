package dev.turingcomplete.asmtestkit.assertion.comperator;

import dev.turingcomplete.asmtestkit.assertion.representation.AttributeRepresentation;
import org.objectweb.asm.Attribute;

import java.util.Comparator;

/**
 * A comparison function to order {@link Attribute}s.
 *
 * <p>Two {@code Attribute}s will be considered as equal if their
 * {@link AttributeRepresentation} are equal. Otherwise, they will be ordered
 * based on the lexicographical order of their {@code AttributeRepresentation}.
 */
public final class AttributeComparator implements Comparator<Attribute> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  private static final AttributeComparator INSTANCE = new AttributeComparator();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  public static AttributeComparator instance() {
    return INSTANCE;
  }

  @Override
  public int compare(Attribute first, Attribute second) {
    // Instance null check
    if (first != null && second == null) {
      return 1;
    }
    else if (first == null && second != null) {
      return -1;
    }
    else if (first == null) { // Both null
      return 0;
    }

    return AttributeRepresentation.instance().toStringOf(first).compareTo(AttributeRepresentation.instance().toStringOf(second));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
