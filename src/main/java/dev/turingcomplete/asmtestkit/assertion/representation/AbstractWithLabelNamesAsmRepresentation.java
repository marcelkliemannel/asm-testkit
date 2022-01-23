package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.assertion.LabelNameLookup;
import org.objectweb.asm.Label;

import java.util.Objects;

/**
 * A skeletal implementation of {@link WithLabelNamesAsmRepresentation}, which
 * can be used as a base class to create a representation of a specific ASM
 * {@link T} object, respecting the names of {@link Label}s.
 *
 * @param <T> the type of the ASM object that gets represented.
 */
public abstract class AbstractWithLabelNamesAsmRepresentation<T>
        extends AbstractAsmRepresentation<T> implements WithLabelNamesAsmRepresentation {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AbstractWithLabelNamesAsmRepresentation(Class<T> objectClass) {
    super(objectClass);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public final String toSimplifiedStringOf(Object object, LabelNameLookup labelNameLookup) {
    Objects.requireNonNull(labelNameLookup);

    return getObjectClass().isInstance(object)
            ? doToSimplifiedStringOf(getObjectClass().cast(object), labelNameLookup)
            : null;
  }

  /**
   * Creates a simplified {@link String} representation of the given
   * {@code object} respecting the names of {@link Label}s.
   *
   * <p>The default implementation will return the same values as
   * {@link #toStringOf(Object, LabelNameLookup)}.
   *
   * <p>A simplified representation should have a limited length and should
   * not contain line breaks. It should reflect the essence of an object,
   * without meta information.
   *
   * <p>In case the representation is undefined, the implementation should
   * return null.
   *
   * @param object          the object of type {@link T} to create a
   *                        representation of; may be null.
   * @param labelNameLookup a {@link LabelNameLookup} to look up names of
   *                        {@link Label}s; never null.
   * @return the {@link String} representation; may be null.
   */
  protected String doToSimplifiedStringOf(T object, LabelNameLookup labelNameLookup) {
    return doToSimplifiedStringOf(object);
  }

  @Override
  public final String toStringOf(Object object, LabelNameLookup labelNameLookup) {
    Objects.requireNonNull(labelNameLookup);

    if (getObjectClass().isInstance(object)) {
      return doToStringOf(getObjectClass().cast(object), labelNameLookup);
    }

    return null;
  }

  /**
   * Creates a {@link String} representation of the given {@code object}
   * respecting the names of {@link Label}s.
   *
   * <p>In case the representation is undefined, the implementation should
   * return null.
   *
   * @param object          the object of type {@link T} to create a
   *                        representation of; may be null.
   * @param labelNameLookup a {@link LabelNameLookup} to look up names of
   *                        {@link Label}s; never null.
   * @return the {@link String} representation; may be null.
   */
  protected abstract String doToStringOf(T object, LabelNameLookup labelNameLookup);

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
