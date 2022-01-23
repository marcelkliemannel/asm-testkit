package dev.turingcomplete.asmtestkit.assertion.representation;

import org.assertj.core.presentation.Representation;
import org.objectweb.asm.TypePath;

/**
 * An AssertJ {@link Representation} for a {@link TypePath}.
 */
public class TypePathRepresentation extends AbstractAsmRepresentation<TypePath> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link TypePathRepresentation} instance.
   */
  public static final TypePathRepresentation INSTANCE = create();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected TypePathRepresentation() {
    super(TypePath.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link TypePathRepresentation} instance.
   *
   * @return a new {@link TypePathRepresentation}; never null;
   */
  public static TypePathRepresentation create() {
    return new TypePathRepresentation();
  }

  @Override
  protected String doToStringOf(TypePath typePath) {
    return typePath.toString();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
