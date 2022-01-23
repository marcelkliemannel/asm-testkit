package dev.turingcomplete.asmtestkit.assertion.representation;

import org.assertj.core.presentation.Representation;
import org.objectweb.asm.TypeReference;

import static org.objectweb.asm.TypeReference.*;

/**
 * An AssertJ {@link Representation} for a {@link TypeReference}.
 *
 * <p>Example output: {@code class_type_parameter=1}
 */
public class TypeReferenceRepresentation extends AbstractSingleAsmRepresentation<TypeReference> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link TypeRepresentation} instance.
   */
  public static final TypeReferenceRepresentation INSTANCE = create();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected TypeReferenceRepresentation() {
    super(TypeReference.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link TypeReferenceRepresentation} instance.
   *
   * @return a new {@link TypeReferenceRepresentation}; never null;
   */
  public static TypeReferenceRepresentation create() {
    return new TypeReferenceRepresentation();
  }

  @Override
  protected String doToStringOf(TypeReference typeReference) {
    switch (typeReference.getSort()) {
      case CLASS_TYPE_PARAMETER:
        return "class_type_parameter=" + typeReference.getTypeParameterIndex();
      case METHOD_TYPE_PARAMETER:
        return "method_type_parameter=" + typeReference.getTypeParameterIndex();
      case CLASS_EXTENDS:
        return "class_extends=" + typeReference.getSuperTypeIndex();
      case CLASS_TYPE_PARAMETER_BOUND:
        return "class_type_parameter_bound=" + typeReference.getTypeParameterIndex() + "," + typeReference.getTypeParameterBoundIndex();
      case METHOD_TYPE_PARAMETER_BOUND:
        return "method_type_parameter_bound=" + typeReference.getTypeParameterIndex() + "," + typeReference.getTypeParameterBoundIndex();
      case FIELD:
        return "field";
      case METHOD_RETURN:
        return "method_return";
      case METHOD_RECEIVER:
        return "method_receiver";
      case METHOD_FORMAL_PARAMETER:
        return "method_formal_parameter=" + typeReference.getFormalParameterIndex();
      case THROWS:
        return "throws=" + typeReference.getExceptionIndex();
      case LOCAL_VARIABLE:
        return "local_variable";
      case RESOURCE_VARIABLE:
        return "resource_variable";
      case EXCEPTION_PARAMETER:
        return "exception_parameter=" + typeReference.getTryCatchBlockIndex();
      case INSTANCEOF:
        return "instanceof";
      case NEW:
        return "new";
      case CONSTRUCTOR_REFERENCE:
        return "constructor_reference";
      case METHOD_REFERENCE:
        return "method_reference";
      case CAST:
        return "cast=" + typeReference.getTypeArgumentIndex();
      case CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT:
        return "constructor_invocation_type_argument=" + typeReference.getTypeArgumentIndex();
      case METHOD_INVOCATION_TYPE_ARGUMENT:
        return "method_invocation_type_argument=" + typeReference.getTypeArgumentIndex();
      case CONSTRUCTOR_REFERENCE_TYPE_ARGUMENT:
        return "constructor_reference_type_argument= " + typeReference.getTypeArgumentIndex();
      case METHOD_REFERENCE_TYPE_ARGUMENT:
        return "method_reference_type_argument=" + typeReference.getTypeArgumentIndex();
      default:
        throw new IllegalStateException("Unknown " + TypeReference.class.getSimpleName() + "value: " + typeReference.getSort() + ". Please report this as a bug.");
    }
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
