package dev.turingcomplete.asmtestkit.representation;

import dev.turingcomplete.asmtestkit.assertion.DefaultLabelIndexLookup;
import dev.turingcomplete.asmtestkit.assertion.LabelIndexLookup;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.tree.TryCatchBlockNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import static dev.turingcomplete.asmtestkit.asmutils.TypeUtils.toTypeElseNull;

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
public class TryCatchBlockNodeRepresentation extends AbstractWithLabelNamesAsmRepresentation<TryCatchBlockNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link TryCatchBlockNodeRepresentation} instance.
   */
  public static final TryCatchBlockNodeRepresentation INSTANCE = create();

  private static final String INVISIBLE_POSTFIX = " // invisible";

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
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

  @Override
  protected String doToSimplifiedStringOf(TryCatchBlockNode tryCatchBlockNode, LabelIndexLookup labelIndexLookup) {
    var representation = new StringBuilder();

    // Type
    if (tryCatchBlockNode.type != null) {
      representation.append(asmRepresentations.toStringOf(toTypeElseNull(tryCatchBlockNode.type)));
    }
    else {
      representation.append("finally");
    }

    // Range
    representation.append(" // range: ")
                  .append(asmRepresentations.toStringOf(tryCatchBlockNode.start, labelIndexLookup))
                  .append("-")
                  .append(asmRepresentations.toStringOf(tryCatchBlockNode.end, labelIndexLookup))
                  .append("; handled in: ")
                  .append(asmRepresentations.toStringOf(tryCatchBlockNode.handler, labelIndexLookup));

    return representation.toString();
  }

  @Override
  protected String doToStringOf(TryCatchBlockNode tryCatchBlockNode) {
    return doToStringOf(tryCatchBlockNode, DefaultLabelIndexLookup.create());
  }

  @Override
  public String doToStringOf(TryCatchBlockNode tryCatchBlockNode, LabelIndexLookup labelIndexLookup) {
    var representation = new StringBuilder();

    // Annotations
    if (tryCatchBlockNode.visibleTypeAnnotations != null) {
      for (TypeAnnotationNode typeAnnotationNode : tryCatchBlockNode.visibleTypeAnnotations) {
        representation.append(asmRepresentations.toStringOf(typeAnnotationNode)).append(System.lineSeparator());
      }
    }
    if (tryCatchBlockNode.invisibleTypeAnnotations != null) {
      for (TypeAnnotationNode typeAnnotationNode : tryCatchBlockNode.invisibleTypeAnnotations) {
        representation.append(asmRepresentations.toStringOf(typeAnnotationNode)).append(INVISIBLE_POSTFIX).append(System.lineSeparator());
      }
    }

    representation.append(doToSimplifiedStringOf(tryCatchBlockNode, labelIndexLookup));

    return representation.toString();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
