package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.asmutils._internal.TextifierUtils;
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
  protected String doToSimplifiedStringOf(Attribute attribute) {
    return attribute.type;
  }

  @Override
  protected String doToStringOf(Attribute attribute) {
    return TextifierUtils.toString(textifier -> textifier.visitAttribute(attribute))
            .substring(ASM_TEXTIFIER_PREFIX.length()) // Remove prefix
            .trim();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
