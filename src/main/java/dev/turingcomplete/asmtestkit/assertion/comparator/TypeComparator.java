package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.comparator._internal.IterableComparator;
import dev.turingcomplete.asmtestkit.assertion.representation.TypeRepresentation;
import org.objectweb.asm.Type;

import java.util.Comparator;
import java.util.Objects;

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

  private TypeRepresentation typeRepresentation = TypeRepresentation.INSTANCE;

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

  /**
   * Sets the used {@link TypeRepresentation}.
   *
   * <p>The default value is {@link TypeRepresentation#INSTANCE}.
   *
   * @param typeRepresentation a {@link TypeRepresentation}; never null.
   * @return {@code this} {@link TypeComparator}; never null.
   */
  public TypeComparator useTypeRepresentation(TypeRepresentation typeRepresentation) {
    this.typeRepresentation = Objects.requireNonNull(typeRepresentation);

    return this;
  }

  @Override
  protected int doCompare(Type first, Type second) {
    return typeRepresentation.toStringOf(first).compareTo(typeRepresentation.toStringOf(second));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
