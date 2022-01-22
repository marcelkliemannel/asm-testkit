package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.assertion.LabelNameLookup;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.tree.TryCatchBlockNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Objects;

/**
 * An AssertJ {@link Representation} for a {@link TryCatchBlockNode}.
 *
 * <p>This representation may produce a multiline {@link String}.
 *
 * <p>Example output:
 * <pre>{@code
 * @TypeParameterAnnotation // reference: exception_parameter=0; path: null
 * java.io.IOException, from: L0-L1; handled in: L1
 * }</pre>
 *
 * <p>The simplified representation omits the type parameter annotations.
 */
public class TryCatchBlockNodeRepresentation extends WithLabelNamesRepresentation<TryCatchBlockNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link TryCatchBlockNodeRepresentation} instance.
   */
  public static final TryCatchBlockNodeRepresentation INSTANCE = new TryCatchBlockNodeRepresentation();

  private static final String INVISIBLE_POSTFIX = " // invisible";

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  protected TypeRepresentation               typeRepresentation               = TypeRepresentation.INSTANCE;
  protected LabelNodeRepresentation          labelNodeRepresentation          = LabelNodeRepresentation.INSTANCE;
  protected TypeAnnotationNodeRepresentation typeAnnotationNodeRepresentation = TypeAnnotationNodeRepresentation.INSTANCE;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected TryCatchBlockNodeRepresentation() {
    super(TryCatchBlockNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link TryCatchBlockNodeRepresentation} instance.
   *
   * @return a new {@link TryCatchBlockNodeRepresentation}; never null;
   */
  public static TryCatchBlockNodeRepresentation create() {
    return new TryCatchBlockNodeRepresentation();
  }

  /**
   * Sets the used {@link TypeRepresentation}.
   *
   * <p>The default value is {@link TypeRepresentation#INSTANCE}.
   *
   * @param typeRepresentation a {@link TypeRepresentation}; never null.
   * @return {@code this} {@link TryCatchBlockNodeRepresentation}; never null.
   */
  public TryCatchBlockNodeRepresentation useTypeRepresentation(TypeRepresentation typeRepresentation) {
    this.typeRepresentation = Objects.requireNonNull(typeRepresentation);

    return this;
  }

  /**
   * Sets the used {@link LabelNodeRepresentation}.
   *
   * <p>The default value is {@link LabelNodeRepresentation#INSTANCE}.
   *
   * @param labelNodeRepresentation a {@link LabelNodeRepresentation}; never null.
   * @return {@code this} {@link TryCatchBlockNodeRepresentation}; never null.
   */
  public TryCatchBlockNodeRepresentation useLabelNodeRepresentation(LabelNodeRepresentation labelNodeRepresentation) {
    this.labelNodeRepresentation = Objects.requireNonNull(labelNodeRepresentation);

    return this;
  }

  /**
   * Sets the used {@link TypeAnnotationNodeRepresentation}.
   *
   * <p>The default value is {@link TypeAnnotationNodeRepresentation#INSTANCE}.
   *
   * @param typeAnnotationNodeRepresentation a {@link TypeAnnotationNodeRepresentation};
   *                                         never null.
   * @return {@code this} {@link TryCatchBlockNodeRepresentation}; never null.
   */
  public TryCatchBlockNodeRepresentation useTypeAnnotationNodeRepresentation(TypeAnnotationNodeRepresentation typeAnnotationNodeRepresentation) {
    this.typeAnnotationNodeRepresentation = Objects.requireNonNull(typeAnnotationNodeRepresentation);

    return this;
  }

  @Override
  protected String doToSimplifiedStringOf(TryCatchBlockNode tryCatchBlockNode, LabelNameLookup labelNameLookup) {
    var representation = new StringBuilder();

    // Type
    if (tryCatchBlockNode.type != null) {
      representation.append(typeRepresentation.transformInternalName(tryCatchBlockNode.type));
    }
    else {
      representation.append("finally");
    }

    // Range
    representation.append(" // range: ")
                  .append(labelNodeRepresentation.toStringOf(tryCatchBlockNode.start, labelNameLookup))
                  .append("-")
                  .append(labelNodeRepresentation.toStringOf(tryCatchBlockNode.end, labelNameLookup))
                  .append("; handled in: ")
                  .append(labelNodeRepresentation.toStringOf(tryCatchBlockNode.handler, labelNameLookup));

    return representation.toString();
  }

  @Override
  protected String doToStringOf(TryCatchBlockNode tryCatchBlockNode) {
    return doToStringOf(tryCatchBlockNode, LabelNameLookup.EMPTY);
  }

  @Override
  public String doToStringOf(TryCatchBlockNode tryCatchBlockNode, LabelNameLookup labelNameLookup) {
    var representation = new StringBuilder();

    // Annotations
    if (tryCatchBlockNode.visibleTypeAnnotations != null) {
      for (TypeAnnotationNode typeAnnotationNode : tryCatchBlockNode.visibleTypeAnnotations) {
        representation.append(typeAnnotationNodeRepresentation.toStringOf(typeAnnotationNode)).append(System.lineSeparator());
      }
    }
    if (tryCatchBlockNode.invisibleTypeAnnotations != null) {
      for (TypeAnnotationNode typeAnnotationNode : tryCatchBlockNode.invisibleTypeAnnotations) {
        representation.append(typeAnnotationNodeRepresentation.toStringOf(typeAnnotationNode)).append(INVISIBLE_POSTFIX).append(System.lineSeparator());
      }
    }

    representation.append(doToSimplifiedStringOf(tryCatchBlockNode, labelNameLookup));

    return representation.toString();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
