package dev.turingcomplete.asmtestkit.assertion.comparator;

import dev.turingcomplete.asmtestkit.assertion.comparator._internal.IterableComparator;
import dev.turingcomplete.asmtestkit.assertion.representation.TypePathRepresentation;
import org.objectweb.asm.TypePath;

import java.util.Comparator;
import java.util.Objects;

/**
 * A comparison function to order {@link TypePath}s.
 *
 * <p>Two {@code TypePath}s will be considered as equal if their
 * {@link TypePathRepresentation}s are equal. Otherwise, they will be ordered
 * based on the lexicographical order of their {@code TypePathRepresentation}.
 */
public class TypePathComparator extends AsmComparator<TypePath> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link TypePathComparator} instance.
   */
  public static final TypePathComparator INSTANCE = create();

  /**
   * A reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link TypePath}s.
   */
  public static final Comparator<Iterable<? extends TypePath>> ITERABLE_INSTANCE = new IterableComparator<>(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private TypePathRepresentation typePathRepresentation = TypePathRepresentation.INSTANCE;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected TypePathComparator() {
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link TypePathComparator} instance.
   *
   * @return a new {@link TypePathComparator}; never null;
   */
  public static TypePathComparator create() {
    return new TypePathComparator();
  }

  /**
   * Sets the used {@link TypePathRepresentation}.
   *
   * <p>The default value is {@link TypePathRepresentation#INSTANCE}.
   *
   * @param typePathRepresentation a {@link TypePathRepresentation}; never null.
   * @return {@code this} {@link TypePathComparator}; never null.
   */
  public TypePathComparator useTypePathRepresentation(TypePathRepresentation typePathRepresentation) {
    this.typePathRepresentation = Objects.requireNonNull(typePathRepresentation);

    return this;
  }

  @Override
  protected int doCompare(TypePath first, TypePath second) {
    return typePathRepresentation.toStringOf(first).compareTo(typePathRepresentation.toStringOf(second));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
