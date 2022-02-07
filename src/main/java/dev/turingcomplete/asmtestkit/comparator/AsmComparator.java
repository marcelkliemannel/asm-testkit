package dev.turingcomplete.asmtestkit.comparator;

import dev.turingcomplete.asmtestkit.representation.AsmRepresentations;
import dev.turingcomplete.asmtestkit.representation.DefaultAsmRepresentations;
import dev.turingcomplete.asmtestkit.comparator._internal.ComparatorUtils;
import org.assertj.core.api.Assert;
import org.assertj.core.internal.DescribableComparator;

import java.util.Comparator;
import java.util.Objects;

/**
 * Base class for an ASM nodes {@link Comparator}.
 *
 * <p>Implementations should implement {@link #doCompare(Object, Object)}.
 *
 * @param <T> the type of the ASM node.
 */
public abstract class AsmComparator<T> extends DescribableComparator<T> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  @SuppressWarnings("FieldCanBeLocal")
  private final Class<?> selfType;
  private final Class<T> elementType;

  protected AsmRepresentations asmRepresentations = DefaultAsmRepresentations.INSTANCE;
  protected AsmComparators     asmComparators     = DefaultAsmComparators.INSTANCE;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AsmComparator(Class<?> selfType, Class<?> elementType) {
    // TODO selfType.cast(this);
    this.selfType = Objects.requireNonNull(selfType);
    //noinspection unchecked
    this.elementType = (Class<T>) Objects.requireNonNull(elementType);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  public Class<T> elementType() {
    return elementType;
  }

  /**
   * Determines the order of the given {@link Object}s.
   *
   * <p>The original method from the {@link Comparator} interface uses the
   * type parameter {@link T}, with which the compiler would prevent wrong input
   * type usages. However, if this {@code Comparator} gets used inside an
   * AssertJ {@link Assert}, e.g., {@link Assert#isEqualTo(Object)}, it would
   * pass any {@code Object} to this method regardless if it is of the type
   * {@link T}. This would then lead to a {@link ClassCastException} during the
   * subsequent call to {@link #doCompare(Object, Object)}.
   *
   * @param first  an object expected to be of type {@link T}; may be null
   * @param second an object expected to be of type {@link T}; may be null
   * @return an {@code int} indicating the order of {@code first} and
   * {@code second}.
   */
  @Override
  public final int compare(Object first, Object second) {
    Integer nullCheckResult = ComparatorUtils.compareNullCheck(first, second);
    if (nullCheckResult != null) {
      return nullCheckResult;
    }

    if (!elementType.isInstance(first) || !elementType.isInstance(second)) {
      return -1;
    }

    return doCompare(elementType.cast(first), elementType.cast(second));
  }

  /**
   * Determines the order of the given {@link T}s.
   *
   * <p>The same rules to determine the result as defined for the
   * {@link Comparator} apply.
   *
   * @param first  first object to be compared; never null.
   * @param second object to be compared; never null.
   * @return an {@code int} indicating the order of {@code first} and
   * {@code second}.
   */
  protected abstract int doCompare(T first, T second);

  @Override
  public String description() {
    return getClass().getSimpleName();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
