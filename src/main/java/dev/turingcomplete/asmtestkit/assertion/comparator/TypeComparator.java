package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.comparator._internal.IterableComparator;
import dev.turingcomplete.asmtestkit.assertion.representation.TypeRepresentation;
import org.objectweb.asm.Type;

import java.util.Comparator;

/**
 * A comparison function to order {@link Type}s.
 *
 * <p>Two {@code TypePath}s will be considered as equal if their
 * {@link TypeRepresentation}s are equal. Otherwise, they will be ordered
 * based on the lexicographical order of their {@code TypeRepresentation}.
 */
public class TypeComparator extends AsmComparator<Type> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link TypeComparator} instance.
   */
  public static final TypeComparator INSTANCE = create();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link Type}s.
   */
  public static final Comparator<Iterable<? extends Type>> ITERABLE_INSTANCE = new IterableComparator<>(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected TypeComparator() {
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link TypeComparator} instance.
   *
   * @return a new {@link TypeComparator}; never null;
   */
  public static TypeComparator create() {
    return new TypeComparator();
  }

  @Override
  protected int doCompare(Type first, Type second) {
    return asmRepresentations.toStringOf(first).compareTo(asmRepresentations.toStringOf(second));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
