package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.asmutils.AccessKind;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.tree.ParameterNode;

import java.util.Objects;

/**
 * An AssertJ {@link Representation} for a {@link ParameterNode}.
 *
 * <p>Example output: {@code (16) final param}.
 *
 * <p>The simplified output just contains the parameter name.
 */
public class ParameterNodeRepresentation extends AsmRepresentation<ParameterNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link ParameterNodeRepresentation} instance.
   */
  public static final ParameterNodeRepresentation INSTANCE = new ParameterNodeRepresentation();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private AccessRepresentation accessRepresentation = AccessRepresentation.PARAMETER_INSTANCE;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public ParameterNodeRepresentation() {
    super(ParameterNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Sets the used {@link AccessRepresentation} for {@link AccessKind#PARAMETER}.
   *
   * <p>The default value is {@link AccessRepresentation#PARAMETER_INSTANCE}.
   *
   * @param accessRepresentation a {@link AccessRepresentation};
   *                             never null.
   * @return {@code this} {@link ParameterNodeRepresentation}; never null.
   * @throws IllegalArgumentException if the given {@code accessRepresentation}
   *                                  is not for {@link AccessKind#PARAMETER}.
   */
  public ParameterNodeRepresentation useAccessRepresentation(AccessRepresentation accessRepresentation) {
    Objects.requireNonNull(accessRepresentation);

    if (accessRepresentation.accessKind != AccessKind.PARAMETER) {
      throw new IllegalArgumentException("Must have " + AccessKind.class.getSimpleName() + ": " + AccessKind.PARAMETER);
    }

    this.accessRepresentation = accessRepresentation;

    return this;
  }

  @Override
  protected String doToStringOf(ParameterNode parameterNode) {
    return accessRepresentation.toStringOf(parameterNode.access) + " " + parameterNode.name;
  }

  @Override
  protected String doToSimplifiedStringOf(ParameterNode parameterNode) {
    return parameterNode.name;
  }


  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
