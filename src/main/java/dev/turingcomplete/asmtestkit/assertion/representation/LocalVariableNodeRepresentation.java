package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.assertion.DefaultLabelIndexLookup;
import dev.turingcomplete.asmtestkit.assertion.LabelIndexLookup;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.LocalVariableNode;

/**
 * An AssertJ {@link Representation} for a {@link LocalVariableNode}.
 *
 * <p>Example output:
 * {@code @TypeParameterAnnotation // reference: local_variable=; path: null // range: L0-L1-1}.
 */
public class LocalVariableNodeRepresentation extends AbstractWithLabelNamesAsmRepresentation<LocalVariableNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link LocalVariableNodeRepresentation} instance.
   */
  public static final LocalVariableNodeRepresentation INSTANCE = create();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
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

  @Override
  protected String doToSimplifiedStringOf(LocalVariableNode localVariableNode) {
    return localVariableNode.name;
  }

  @Override
  protected String doToStringOf(LocalVariableNode localVariableNode) {
    return toStringOf(localVariableNode, DefaultLabelIndexLookup.create());
  }

  @Override
  public String doToStringOf(LocalVariableNode localVariableNode, LabelIndexLookup labelIndexLookup) {
    var representation = new StringBuilder();

    representation.append("#").append(localVariableNode.index).append(" ")
                  .append(asmRepresentations.toStringOf(Type.getType(localVariableNode.desc)))
                  .append(" ")
                  .append(localVariableNode.name)
                  .append(" // range: ").append(asmRepresentations.toStringOf(localVariableNode.start, labelIndexLookup))
                  .append("-")
                  .append(asmRepresentations.toStringOf(localVariableNode.end, labelIndexLookup));

    if (localVariableNode.signature != null) {
      representation.append(" // signature: ").append(localVariableNode.signature);
    }

    return representation.toString();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
