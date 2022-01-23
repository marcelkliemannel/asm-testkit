package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.asmutils._internal.TextifierUtils;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Attribute;

/**
 * An AssertJ {@link Representation} for an {@link Attribute}.
 *
 * <p>Example output: {@code NameContent}.
 */
public class AttributeRepresentation extends AbstractSingleAsmRepresentation<Attribute> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link AttributeRepresentation} instance.
   */
  public static final AttributeRepresentation INSTANCE = create();

  private static final String ASM_TEXTIFIER_PREFIX = "ATTRIBUTE ";

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AttributeRepresentation() {
    super(Attribute.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link AttributeRepresentation} instance.
   *
   * @return a new {@link AttributeRepresentation}; never null;
   */
  public static AttributeRepresentation create() {
    return new AttributeRepresentation();
  }

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
