package dev.turingcomplete.asmtestkit.assertion.representation;

import org.objectweb.asm.TypePath;

/**
 * Creates a {@link String} representation of a {@link TypePath}.
 */
public class TypePathRepresentation extends AsmRepresentation<TypePath> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link TypePathRepresentation} instance.
   */
  public static final TypePathRepresentation INSTANCE = new TypePathRepresentation();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public TypePathRepresentation() {
    super(TypePath.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  protected String doToStringOf(TypePath typePath) {
    return typePath.toString();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
