package dev.turingcomplete.asmtestkit.assertion.representation;

import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.LocalVariableAnnotationNode;
import org.objectweb.asm.tree.LocalVariableNode;

import java.util.Map;
import java.util.Objects;

/**
 * Creates am AssertJ {@link Representation} a {@link LocalVariableAnnotationNode}.
 *
 * <p>Example output:
 * {@code @TypeParameterAnnotation // reference: local_variable=; path: null // range: L0-L1-1}.
 */
public class LocalVariableNodeRepresentation extends AsmRepresentation<LocalVariableNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link LocalVariableNodeRepresentation} instance.
   */
  public static final LocalVariableNodeRepresentation INSTANCE = new LocalVariableNodeRepresentation();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private TypeRepresentation      typeRepresentation      = TypeRepresentation.INSTANCE;
  private LabelNodeRepresentation labelNodeRepresentation = LabelNodeRepresentation.INSTANCE;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected LocalVariableNodeRepresentation() {
    super(LocalVariableNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Sets the used {@link TypeRepresentation}.
   *
   * <p>The default value is {@link TypeRepresentation#INSTANCE}.
   *
   * @param typeRepresentation a {@link TypeRepresentation};
   *                           never null.
   * @return {@code this} {@link LocalVariableNodeRepresentation}; never null.
   */
  public LocalVariableNodeRepresentation useTypeRepresentation(TypeRepresentation typeRepresentation) {
    this.typeRepresentation = Objects.requireNonNull(typeRepresentation);

    return this;
  }

  /**
   * Sets the used {@link LabelNodeRepresentation}.
   *
   * <p>The default value is {@link LabelNodeRepresentation#INSTANCE}.
   *
   * @param labelNodeRepresentation a {@link LabelNodeRepresentation};
   *                                never null.
   * @return {@code this} {@link LocalVariableNodeRepresentation}; never null.
   */
  public LocalVariableNodeRepresentation useLabelNodeRepresentation(LabelNodeRepresentation labelNodeRepresentation) {
    this.labelNodeRepresentation = Objects.requireNonNull(labelNodeRepresentation);

    return this;
  }

  @Override
  protected String doToSimplifiedStringOf(LocalVariableNode localVariableNode) {
    return localVariableNode.name;
  }

  @Override
  protected String doToStringOf(LocalVariableNode localVariableNode) {
    return toStringOf(localVariableNode, Map.of());
  }

  public String toStringOf(LocalVariableNode localVariableNode, Map<Label, String> labelNames) {
    var representation = new StringBuilder();

    representation.append("#").append(localVariableNode.index).append(" ")
                  .append(typeRepresentation.toStringOf(Type.getType(localVariableNode.desc)))
                  .append(" ")
                  .append(localVariableNode.name)
                  .append(" (").append(labelNodeRepresentation.toStringOf(localVariableNode.start, labelNames))
                  .append("-")
                  .append(labelNodeRepresentation.toStringOf(localVariableNode.end, labelNames)).append(")");

    if (localVariableNode.signature != null) {
      representation.append(" // signature: ").append(localVariableNode.signature);
    }

    return representation.toString();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
