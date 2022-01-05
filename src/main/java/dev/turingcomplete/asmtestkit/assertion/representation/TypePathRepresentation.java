package dev.turingcomplete.asmtestkit.assertion.representation;

import org.objectweb.asm.TypePath;

/**
 * Creates a {@link String} representation of a {@link TypePath}.
 */
public class TypePathRepresentation extends AsmRepresentation<TypePath> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  private static final TypePathRepresentation INSTANCE = new TypePathRepresentation();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public TypePathRepresentation() {
    super(TypePath.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Gets a reusable {@link TypePathRepresentation} instance.
   *
   * @return a {@link TypePathRepresentation} instance; never null.
   */
  public static TypePathRepresentation instance() {
    return INSTANCE;
  }

  @Override
  protected String toStringRepresentation(TypePath typePath) {
    return typePath.toString();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
