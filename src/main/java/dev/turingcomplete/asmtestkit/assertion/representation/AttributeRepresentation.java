package dev.turingcomplete.asmtestkit.assertion.representation;

import org.objectweb.asm.Attribute;

/**
 * Creates a {@link String} representation of an {@link Attribute}.
 */
public class AttributeRepresentation extends AsmRepresentation<Attribute> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link AttributeRepresentation} instance.
   */
  public static final AttributeRepresentation INSTANCE = new AttributeRepresentation();

  private static final String ASM_TEXTIFIER_PREFIX = "ATTRIBUTE ";

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public AttributeRepresentation() {
    super(Attribute.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  protected String toStringRepresentation(Attribute attribute) {
    return getAsmTextifierRepresentation(textifier -> textifier.visitAttribute(attribute))
            .substring(ASM_TEXTIFIER_PREFIX.length()) // Remove prefix
            .trim();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
