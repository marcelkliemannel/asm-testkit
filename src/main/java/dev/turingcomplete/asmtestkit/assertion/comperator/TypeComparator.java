package dev.turingcomplete.asmtestkit.assertion.comperator;

import dev.turingcomplete.asmtestkit.assertion.comperator._internal.IterableComparator;
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

  private static final TypeComparator                       INSTANCE          = new TypeComparator();
  private static final Comparator<Iterable<? extends Type>> ITERABLE_INSTANCE = new IterableComparator<>(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private TypeRepresentation typeRepresentation = TypeRepresentation.instance();

  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Gets a reusable {@link TypeComparator} instance.
   *
   * @return a {@link TypeComparator} instance; never null.
   */
  public static TypeComparator instance() {
    return INSTANCE;
  }

  /**
   * Gets a reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link Type}s.
   *
   * @return a {@link Comparator} instance; never null.
   */
  public static Comparator<Iterable<? extends Type>> iterableInstance() {
    return ITERABLE_INSTANCE;
  }

  /**
   * Sets the used {@link TypeRepresentation}.
   *
   * <p>The default value is {@link TypeRepresentation#instance()}.
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
