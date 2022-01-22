package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.assertion.LabelNameLookup;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.LocalVariableNode;

import java.util.Objects;

/**
 * An AssertJ {@link Representation} for a {@link LocalVariableNode}.
 *
 * <p>Example output:
 * {@code @TypeParameterAnnotation // reference: local_variable=; path: null // range: L0-L1-1}.
 */
public class LocalVariableNodeRepresentation extends WithLabelNamesRepresentation<LocalVariableNode> {
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
   * Creates a new {@link LocalVariableNodeRepresentation} instance.
   *
   * @return a new {@link LocalVariableNodeRepresentation}; never null;
   */
  public static LocalVariableNodeRepresentation create() {
    return new LocalVariableNodeRepresentation();
  }

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
    return toStringOf(localVariableNode, LabelNameLookup.EMPTY);
  }

  @Override
  public String doToStringOf(LocalVariableNode localVariableNode, LabelNameLookup labelNameLookup) {
    var representation = new StringBuilder();

    representation.append("#").append(localVariableNode.index).append(" ")
                  .append(typeRepresentation.toStringOf(Type.getType(localVariableNode.desc)))
                  .append(" ")
                  .append(localVariableNode.name)
                  .append(" // range: ").append(labelNodeRepresentation.doToStringOf(localVariableNode.start, labelNameLookup))
                  .append("-")
                  .append(labelNodeRepresentation.doToStringOf(localVariableNode.end, labelNameLookup));

    if (localVariableNode.signature != null) {
      representation.append(" // signature: ").append(localVariableNode.signature);
    }

    return representation.toString();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
