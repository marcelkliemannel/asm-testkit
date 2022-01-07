package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.asmutils._internal.TextifierUtils;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.util.TraceAnnotationVisitor;

import java.util.Objects;

public abstract class AbstractAnnotationNodeRepresentation<SELF extends AbstractAnnotationNodeRepresentation<SELF, ANNO>, ANNO extends AnnotationNode>
        extends AsmRepresentation<ANNO> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private TypeRepresentation typeRepresentation = TypeRepresentation.INSTANCE;
  private boolean            hideValues         = false;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public AbstractAnnotationNodeRepresentation(Class<ANNO> annotationNodeClass) {
    super(annotationNodeClass);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Sets the used {@link TypeRepresentation}.
   *
   * <p>The default value is {@link TypeRepresentation#INSTANCE}.
   *
   * @param typeRepresentation a {@link TypeRepresentation}; never null.
   * @return {@code this} {@link SELF}; never null.
   */
  public SELF useTypePathRepresentation(TypeRepresentation typeRepresentation) {
    this.typeRepresentation = Objects.requireNonNull(typeRepresentation);

    //noinspection unchecked
    return (SELF) this;
  }

  /**
   * Hides the values of the annotation in the representation.
   *
   * @return {@code this} {@link SELF}; never null.
   */
  public SELF hideValues() {
    this.hideValues = true;

    //noinspection unchecked
    return (SELF) this;
  }

  @Override
  protected String toStringRepresentation(ANNO annotationNode) {
    String className = annotationNode.desc != null ? typeRepresentation.toStringRepresentation(Type.getType(annotationNode.desc)) : null;
    String result = "@" + className;

    if (!hideValues) {
      String textifiedValues = TextifierUtils.textify(textifier -> annotationNode.accept(new TraceAnnotationVisitor(textifier))).trim();
      if (!textifiedValues.isBlank()) {
        result += "(" + textifiedValues + ")";
      }
    }

    return result;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
