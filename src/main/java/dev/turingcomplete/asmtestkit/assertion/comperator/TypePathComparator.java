package dev.turingcomplete.asmtestkit.assertion.comperator;

import dev.turingcomplete.asmtestkit.assertion.comperator._internal.IterableComparator;
import dev.turingcomplete.asmtestkit.assertion.representation.TypePathRepresentation;
import org.objectweb.asm.TypePath;

import java.util.Comparator;
import java.util.Objects;

/**
 * A comparison function to order {@link TypePath}s.
 *
 * <p>Two {@code TypePath}s will be considered as equal if their
 * {@link TypePathRepresentation} are equal. Otherwise, they will be ordered
 * based on the lexicographical order of their {@code TypePathRepresentation}.
 */
public class TypePathComparator extends AsmComparator<TypePath> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  private static final TypePathComparator                       INSTANCE          = new TypePathComparator();
  private static final Comparator<Iterable<? extends TypePath>> ITERABLE_INSTANCE = new IterableComparator<>(INSTANCE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private TypePathRepresentation typePathRepresentation = TypePathRepresentation.instance();

  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Gets a reusable {@link TypePathComparator} instance.
   *
   * @return a {@link TypePathComparator} instance; never null.
   */
  public static TypePathComparator instance() {
    return INSTANCE;
  }

  /**
   * Gets a reusable {@link Comparator} instance for an {@link Iterable} of
   * {@link TypePath}s.
   *
   * @return a {@link IterableComparator} instance; never null.
   */
  public static Comparator<Iterable<? extends TypePath>> iterableInstance() {
    return ITERABLE_INSTANCE;
  }

  /**
   * Sets the used {@link TypePathRepresentation}.
   *
   * <p>The default value is {@link TypePathRepresentation#instance()}.
   *
   * @param typePathRepresentation a {@link TypePathRepresentation}; never null.
   */
  public void setTypePathRepresentation(TypePathRepresentation typePathRepresentation) {
    this.typePathRepresentation = Objects.requireNonNull(typePathRepresentation);
  }

  @Override
  protected int doCompare(TypePath first, TypePath second) {
    return typePathRepresentation.toStringOf(first).compareTo(typePathRepresentation.toStringOf(second));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
