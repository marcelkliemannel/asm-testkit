package dev.turingcomplete.asmtestkit.representation;

import dev.turingcomplete.asmtestkit.asmutils.TypeUtils;
import dev.turingcomplete.asmtestkit.node.AccessFlags;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.InnerClassNode;

/**
 * An AssertJ {@link Representation} for a {@link InnerClassNode}.
 *
 * <p>Example output: {@code [2: private] MyClass$InnerClass // outer name: MyClass // inner name: InnerClass}
 *
 * <p>Example simplified output: {@code MyClass$InnerClass}.
 */
public class InnerClassNodeRepresentation extends AbstractAsmRepresentation<InnerClassNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link InnerClassNodeRepresentation} instance.
   */
  public static final InnerClassNodeRepresentation INSTANCE = create();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected InnerClassNodeRepresentation() {
    super(InnerClassNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link FieldNodeRepresentation} instance.
   *
   * @return a new {@link FieldNodeRepresentation}; never null;
   */
  public static InnerClassNodeRepresentation create() {
    return new InnerClassNodeRepresentation();
  }

  @Override
  protected String doToSimplifiedStringOf(InnerClassNode innerClassNode) {
    return asmRepresentations.getAsmRepresentation(Type.class).toSimplifiedStringOf(TypeUtils.nameToTypeElseNull(innerClassNode.name));
  }

  @Override
  protected String doToStringOf(InnerClassNode innerClassNode) {
    AsmRepresentation<Type> typeRepresentation = asmRepresentations.getAsmRepresentation(Type.class);
    return asmRepresentations.getAsmRepresentation(AccessFlags.class).toStringOf(AccessFlags.forClass(innerClassNode.access)) +
           " " +
           typeRepresentation.toSimplifiedStringOf(TypeUtils.nameToTypeElseNull(innerClassNode.name)) +
           " // outer name: " +
           typeRepresentation.toSimplifiedStringOf(TypeUtils.nameToTypeElseNull(innerClassNode.outerName)) +
           " // inner name: " +
           typeRepresentation.toSimplifiedStringOf(TypeUtils.nameToTypeElseNull(innerClassNode.innerName));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
