package dev.turingcomplete.asmtestkit.assertion.representation;

import org.objectweb.asm.Type;

/**
 * Creates a {@link String} representation of a {@link Type}.
 */
public class TypeRepresentation extends AsmRepresentation<Type> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  private static final TypeRepresentation INSTANCE = new TypeRepresentation();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private ObjectMode objectNameMode = ObjectMode.CLASS_NAME;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public TypeRepresentation() {
    super(Type.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Gets a reusable {@link TypeRepresentation} instance.
   *
   * @return a {@link TypeRepresentation} instance; never null.
   */
  public static TypeRepresentation instance() {
    return INSTANCE;
  }

  /**
   * Use the fully qualified class name representation, e.g.,
   * {@code java.lang.String}.
   *
   * @return {@code this} {@link TypeRepresentation}; never null.
   */
  public TypeRepresentation useClassName() {
    objectNameMode = ObjectMode.CLASS_NAME;

    return this;
  }

  /**
   * Use the internal name representation, e.g., {@code java/lang/String}.
   *
   * @return {@code this} {@link TypeRepresentation}; never null.
   */
  public TypeRepresentation useInternalName() {
    objectNameMode = ObjectMode.INTERNAL_NAME;

    return this;
  }

  /**
   * Use the descriptor representation, e.g., {@code Ljava/lang/String;}.
   *
   * @return {@code this} {@link TypeRepresentation}; never null.
   */
  public TypeRepresentation useDescriptor() {
    objectNameMode = ObjectMode.DESCRIPTOR;

    return this;
  }

  @Override
  protected String toStringRepresentation(Type type) {
    switch (type.getSort()) {
      case Type.ARRAY:
        return toStringRepresentation(type.getElementType()) + "[]".repeat(type.getDimensions());
      case Type.OBJECT:
        // Fall through
      case 12: // INTERNAL
        return getObjectName(type);
      default:
        return type.getClassName();
    }
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private String getObjectName(Type type) {
    String className = type.getClassName();
    switch (objectNameMode) {
      case DESCRIPTOR:
        return type.getDescriptor();
      case CLASS_NAME:
        return className;
      case INTERNAL_NAME:
        return className.replace('.', '/');
      default:
        throw new IllegalStateException("Unknown " + objectNameMode.getClass() + ": " + objectNameMode);
    }
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  private enum ObjectMode {DESCRIPTOR, INTERNAL_NAME, CLASS_NAME}
}
