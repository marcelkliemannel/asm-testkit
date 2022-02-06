package dev.turingcomplete.asmtestkit.representation;

import org.assertj.core.presentation.StandardRepresentation;

import java.util.Objects;

/**
 * A skeletal implementation of {@link AsmRepresentation}, which can be
 * used as a base class to create a representation of a specific ASM {@link T}
 * object.
 *
 * @param <T> the type of the ASM object that gets represented.
 */
public abstract class AbstractAsmRepresentation<T> extends StandardRepresentation implements AsmRepresentation<T> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  protected AsmRepresentations asmRepresentations = DefaultAsmRepresentations.INSTANCE;

  private final Class<T> objectClass;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AbstractAsmRepresentation(Class<T> objectClass) {
    this.objectClass = Objects.requireNonNull(objectClass);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Sets the used {@link AsmRepresentations}.
   *
   * <p>The default value is {@link DefaultAsmRepresentations#INSTANCE}.
   *
   * @param asmRepresentations an {@link AsmRepresentations};
   *                                   never null.
   * @return {@code this} {@link AbstractAsmRepresentation}; never null.
   */
  public AbstractAsmRepresentation<T> useAsmRepresentationsCombiner(AsmRepresentations asmRepresentations) {
    this.asmRepresentations = Objects.requireNonNull(asmRepresentations);

    return this;
  }

  @Override
  public final Class<T> getObjectClass() {
    return objectClass;
  }

  @Override
  public final String toStringOf(Object object) {
    return super.toStringOf(object);
  }

  protected final String fallbackToStringOf(Object object) {
    if (objectClass.equals(object) || objectClass.isAssignableFrom(objectClass)) {
      return doToStringOf(objectClass.cast(object));
    }

    return super.fallbackToStringOf(object);
  }

  /**
   * Creates a {@link String} representation of the given {@code object}.
   *
   * <p>In case the representation is undefined, the implementation should
   * return null.
   *
   * @param object the object of type {@link T} to create a representation of;
   *               never null.
   * @return the {@link String} representation; may be null.
   */
  protected abstract String doToStringOf(T object);

  @Override
  public final String toSimplifiedStringOf(Object object) {
    return object != null && (objectClass.equals(object) || objectClass.isAssignableFrom(objectClass))
            ? doToSimplifiedStringOf(objectClass.cast(object))
            : null;
  }

  /**
   * Creates a simplified {@link String} representation of the given
   * {@code object}.
   *
   * <p>The default implementation will return the same values as
   * {@link #doToStringOf(Object)}.
   *
   * <p>A simplified representation should have a limited length and should
   * not contain line breaks. It should reflect the essence of an object,
   * without meta information.
   *
   * <p>In case the representation is undefined, the implementation should
   * return null.
   *
   * @param object the object of type {@link T} to create a simple representation
   *               of; never null.
   * @return the {@link String} representation; may be null.
   */
  protected String doToSimplifiedStringOf(T object) {
    return doToStringOf(object);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
