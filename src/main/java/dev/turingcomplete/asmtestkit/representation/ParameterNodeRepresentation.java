package dev.turingcomplete.asmtestkit.representation;

import dev.turingcomplete.asmtestkit.node.AccessFlags;
import dev.turingcomplete.asmtestkit.asmutils.AccessKind;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.tree.ParameterNode;

/**
 * An AssertJ {@link Representation} for a {@link ParameterNode}.
 *
 * <p>Example output: {@code (16) final param}.
 *
 * <p>The simplified output just contains the parameter name.
 */
public class ParameterNodeRepresentation extends AbstractAsmRepresentation<ParameterNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link ParameterNodeRepresentation} instance.
   */
  public static final ParameterNodeRepresentation INSTANCE = create();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected ParameterNodeRepresentation() {
    super(ParameterNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link ParameterNodeRepresentation} instance.
   *
   * @return a new {@link ParameterNodeRepresentation}; never null;
   */
  public static ParameterNodeRepresentation create() {
    return new ParameterNodeRepresentation();
  }

  @Override
  protected String doToStringOf(ParameterNode parameterNode) {
    return asmRepresentations.toStringOf(AccessFlags.create(parameterNode.access, AccessKind.PARAMETER)) + " " + parameterNode.name;
  }

  @Override
  protected String doToSimplifiedStringOf(ParameterNode parameterNode) {
    return parameterNode.name;
  }


  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
