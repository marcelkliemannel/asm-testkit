package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.asmutils.ClassNameUtils;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Type;

import java.util.Objects;

/**
 * An AssertJ {@link Representation} for a {@link Type}.
 */
public class TypeRepresentation extends AbstractAsmRepresentation<Type> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link TypeRepresentation} instance.
   */
  public static final TypeRepresentation INSTANCE = create();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private ObjectMode objectNameMode = ObjectMode.CLASS_NAME;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected TypeRepresentation() {
    super(Type.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link TypeRepresentation} instance.
   *
   * @return a new {@link TypeRepresentation}; never null;
   */
  public static TypeRepresentation create() {
    return new TypeRepresentation();
  }

  /**
   * Use the fully qualified class name (for example {@code java.lang.String})
   * for object names.
   *
   * @return {@code this} {@link TypeRepresentation}; never null.
   */
  public TypeRepresentation useClassName() {
    objectNameMode = ObjectMode.CLASS_NAME;

    return this;
  }

  /**
   * Use the internal name (for example {@code java/lang/String}) for object
   * names.
   *
   * @return {@code this} {@link TypeRepresentation}; never null.
   */
  public TypeRepresentation useInternalName() {
    objectNameMode = ObjectMode.INTERNAL_NAME;

    return this;
  }

  /**
   * Use the descriptor (for example {@code Ljava/lang/String;}) for object
   * names.
   *
   * @return {@code this} {@link TypeRepresentation}; never null.
   */
  public TypeRepresentation useDescriptor() {
    objectNameMode = ObjectMode.DESCRIPTOR;

    return this;
  }

  /**
   * Transforms the given internal name to an object name with the current
   * setting.
   *
   * @param internalName an internal name as {@link String}; never null.
   * @return the transformed {@code internalName}; never null.
   */
  public String transformInternalName(String internalName) {
    Objects.requireNonNull(internalName);

    switch (objectNameMode) {
      case DESCRIPTOR:
        return "L" + internalName + ";";
      case CLASS_NAME:
        return ClassNameUtils.toClassName(internalName);
      case INTERNAL_NAME:
        return internalName;
      default:
        throw new IllegalStateException("Unknown " + objectNameMode.getClass() + ": " + objectNameMode + ". Please report this as a bug.");
    }
  }

  @Override
  protected String doToStringOf(Type type) {
    switch (type.getSort()) {
      case Type.METHOD:
        return type.toString();
      case Type.ARRAY:
        return doToStringOf(type.getElementType()) + "[]".repeat(type.getDimensions());
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
        throw new IllegalStateException("Unknown " + objectNameMode.getClass() + ": " + objectNameMode + ". Please report this as a bug.");
    }
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  private enum ObjectMode {DESCRIPTOR, INTERNAL_NAME, CLASS_NAME}
}
