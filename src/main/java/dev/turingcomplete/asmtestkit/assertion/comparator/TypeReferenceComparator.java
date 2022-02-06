package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.comparator._internal.IterableComparator;
import org.objectweb.asm.TypeReference;

import java.util.Comparator;

/**
 * A comparison function to order {@link TypeReference}s.
 *
 * <p>Two {@code TypeReference}s will be compared based on their
 * {@link TypeReference#getValue()}s.
 */
public class TypeReferenceComparator extends AsmComparator<TypeReference> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link TypeReferenceComparator} instance.
   */
  public static final TypeReferenceComparator INSTANCE = create();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link TypeReference}s.
   */
  public static final Comparator<Iterable<? extends TypeReference>> ITERABLE_INSTANCE = new IterableComparator<>(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected TypeReferenceComparator() {
    super(TypeReferenceComparator.class, TypeReference.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link TypeReferenceComparator} instance.
   *
   * @return a new {@link TypeReferenceComparator}; never null;
   */
  public static TypeReferenceComparator create() {
    return new TypeReferenceComparator();
  }

  @Override
  protected int doCompare(TypeReference first, TypeReference second) {
    return Integer.compare(first.getValue(), second.getValue());
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
