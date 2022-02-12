package dev.turingcomplete.asmtestkit.representation;

import dev.turingcomplete.asmtestkit.asmutils.Access;
import dev.turingcomplete.asmtestkit.asmutils.AccessKind;
import dev.turingcomplete.asmtestkit.common.DefaultLabelIndexLookup;
import dev.turingcomplete.asmtestkit.common.LabelIndexLookup;
import dev.turingcomplete.asmtestkit.node.AccessNode;
import dev.turingcomplete.asmtestkit.node.AnnotationDefaultNode;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.LocalVariableAnnotationNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ParameterNode;
import org.objectweb.asm.tree.TryCatchBlockNode;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static dev.turingcomplete.asmtestkit.asmutils.MethodNodeUtils.extractLabelIndices;
import static dev.turingcomplete.asmtestkit.asmutils.TypeUtils.nameToTypeElseNull;
import static dev.turingcomplete.asmtestkit.representation._internal.RepresentationUtils.createAnnotationNodesRepresentations;
import static dev.turingcomplete.asmtestkit.representation._internal.RepresentationUtils.createAttributesRepresentations;
import static dev.turingcomplete.asmtestkit.representation._internal.RepresentationUtils.createTypeAnnotationNodesRepresentations;
import static dev.turingcomplete.asmtestkit.representation._internal.RepresentationUtils.prependToFirstLine;

/**
 * An AssertJ {@link Representation} for a {@link MethodNode}.
 *
 * <p>This representation may produce a multiline {@link String}.
 *
 * <p>Example output:
 * <pre>{@code
 * // Attribute: NameContent
 * @Annotation
 * (0) java.lang.Object myMethod(java.lang.String first) // signature: <T:Ljava/lang/Object;>(Ljava/lang/String;Ljava/lang/Integer;)TT;
 *    L0
 *      LINENUMBER 2 L0
 *      INVOKEVIRTUAL java/lang/Integer.intValue ()I // opcode: 182
 *      IADD // opcode: 96
 *      INVOKESTATIC java/lang/Integer.valueOf (I)Ljava/lang/Integer; // opcode: 184
 *      ASTORE 2 // opcode: 58
 *      ...
 *    L6
 *  // Local variable: #0 MyClass this // range: L0-L6 // signature: LMyClass<TS;>;
 *  // Local variable: #1 java.lang.String first // range: L0-L6
 *  // Local variable Annotation: @TypeParameterAnnotationA // reference: local_variable; path: null // range: #3 L3-L2
 *  // Try catch block: @TypeParameterAnnotation // reference: exception_parameter=0; path: null // invisible
 *                      java.lang.IllegalArgumentException // range: L0-L1; handled in: L2
 *  // Max locals: 5
 *  // Max stack: 3
 * }</pre>
 *
 * <p>The simplified output concatenates the method name and descriptor.
 */
public class MethodNodeRepresentation extends AbstractWithLabelIndexAsmRepresentation<MethodNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  public static final String INSTRUCTIONS_INDENT     = " ".repeat(4);
  public static final String META_INFORMATION_INDENT = " ".repeat(2) + "// ";

  /**
   * A reusable {@link MethodNodeRepresentation} instance.
   */
  public static final MethodNodeRepresentation INSTANCE = create();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected MethodNodeRepresentation() {
    super(MethodNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link MethodNodeRepresentation} instance.
   *
   * @return a new {@link MethodNodeRepresentation}; never null;
   */
  public static MethodNodeRepresentation create() {
    return new MethodNodeRepresentation();
  }

  @Override
  protected String doToSimplifiedStringOf(MethodNode methodNode) {
    return methodNode.name + methodNode.desc;
  }

  @Override
  protected String doToStringOf(MethodNode methodNode) {
    return doToStringOf(methodNode, DefaultLabelIndexLookup.create(extractLabelIndices(methodNode)));
  }

  @Override
  protected String doToStringOf(MethodNode methodNode, LabelIndexLookup labelIndexLookup) {
    var representation = new StringBuilder();

    // Attributes
    createAttributesRepresentations(asmRepresentations, methodNode.attrs)
            .forEach(attributeRepresentation -> representation.append("// Attribute: ")
                                                              .append(attributeRepresentation)
                                                              .append(System.lineSeparator()));

    // Annotations
    createAnnotationNodesRepresentations(asmRepresentations, methodNode.visibleAnnotations, methodNode.invisibleAnnotations)
            .forEach(annotationNodeRepresentation -> representation.append(annotationNodeRepresentation)
                                                                   .append(System.lineSeparator()));

    // Type annotations
    createTypeAnnotationNodesRepresentations(asmRepresentations, methodNode.visibleTypeAnnotations, methodNode.invisibleTypeAnnotations)
            .forEach(typeAnnotationNodeRepresentation -> representation.append(typeAnnotationNodeRepresentation)
                                                                       .append(System.lineSeparator()));

    // Declaration
    representation.append(createMethodDeclaration(methodNode)).append(System.lineSeparator());

    // Instructions
    if (methodNode.instructions != null) {
      asmRepresentations.toStringOf(methodNode.instructions).lines()
                        .forEach(line -> representation.append(INSTRUCTIONS_INDENT).append(line).append(System.lineSeparator()));
    }

    // Annotation default
    if (methodNode.annotationDefault != null) {
      representation.append(META_INFORMATION_INDENT).append("Annotation default: ")
                    .append(asmRepresentations.toStringOf(AnnotationDefaultNode.create(methodNode.annotationDefault)))
                    .append(System.lineSeparator());
    }
    // Parameters
    if (methodNode.parameters != null) {
      for (int i = 0; i < methodNode.parameters.size(); i++) {
        prependToFirstLine("Parameter: ", toStringOfParameter(methodNode.parameters.get(i), i, methodNode.visibleParameterAnnotations, methodNode.invisibleParameterAnnotations))
                .lines().forEach(appendMetaInformationLine(representation));
      }
    }

    // Local Variables
    if (methodNode.localVariables != null) {
      for (LocalVariableNode localVariable : methodNode.localVariables) {
        representation.append(META_INFORMATION_INDENT)
                      .append("Local variable: ")
                      .append(asmRepresentations.toStringOf(localVariable, labelIndexLookup))
                      .append(System.lineSeparator());
      }
    }

    // Local Variable Annotations
    if (methodNode.visibleLocalVariableAnnotations != null) {
      for (LocalVariableAnnotationNode localVariableAnnotation : methodNode.visibleLocalVariableAnnotations) {
        representation.append(META_INFORMATION_INDENT)
                      .append("Local variable annotation: ")
                      .append(asmRepresentations.toStringOf(localVariableAnnotation, labelIndexLookup))
                      .append(System.lineSeparator());
      }
    }
    if (methodNode.invisibleLocalVariableAnnotations != null) {
      for (LocalVariableAnnotationNode localVariableAnnotation : methodNode.invisibleLocalVariableAnnotations) {
        representation.append(META_INFORMATION_INDENT)
                      .append("Local variable annotation: ")
                      .append(asmRepresentations.toStringOf(localVariableAnnotation, labelIndexLookup))
                      .append(" // invisible")
                      .append(System.lineSeparator());
      }
    }

    // Try Catch Blocks
    if (methodNode.tryCatchBlocks != null) {
      for (TryCatchBlockNode tryCatchBlock : methodNode.tryCatchBlocks) {
        prependToFirstLine("Try catch block: ", asmRepresentations.toStringOf(tryCatchBlock, labelIndexLookup))
                .lines().forEach(appendMetaInformationLine(representation));
      }
    }

    // Max Locals and Stack
    representation.append(META_INFORMATION_INDENT).append("Max locals: ")
                  .append(methodNode.maxLocals)
                  .append(System.lineSeparator());
    representation.append(META_INFORMATION_INDENT).append("Max stack: ")
                  .append(methodNode.maxStack);

    return representation.toString();
  }

  /**
   * Creates the method declaration as one single line, including: access,
   * return type, name, parameters, exception and signature.
   *
   * @param methodNode the {@link MethodNode}; never null
   * @return a {@link String} representation of {@code methodNode}'s
   * declaration; never null.
   */
  public String createMethodDeclaration(MethodNode methodNode) {
    var representation = new StringBuilder();

    Type methodType = methodNode.desc != null ? Type.getMethodType(methodNode.desc) : null;

    // Access
    boolean isStatic = Access.STATIC.check(methodNode.access);
    if (methodNode.access >= 0) {
      representation.append(asmRepresentations.toStringOf(AccessNode.forMethod(methodNode.access))).append(" ");
    }

    // Return Type
    if (methodType != null && !Objects.equals("<clinit>", methodNode.name) && !Objects.equals("<init>", methodNode.name)) {
      representation.append(asmRepresentations.toStringOf(methodType.getReturnType())).append(" ");
    }

    // Name
    representation.append(methodNode.name);

    // Parameters
    representation.append("(");
    if (methodType != null) {
      Type[] argumentTypes = methodType.getArgumentTypes();
      for (int i = 0; i < argumentTypes.length; i++) {
        if (i >= 1) {
          representation.append(", ");
        }

        // Access, type and name
        String access = null;
        String type = asmRepresentations.toStringOf(argumentTypes[i]);
        String name = "var" + i;

        if (methodNode.parameters != null && i < methodNode.parameters.size()) {
          // Use name and access from ParameterNode
          ParameterNode parameterNode = methodNode.parameters.get(i);
          if (parameterNode.access != 0) {
            access = asmRepresentations.toStringOf(AccessNode.create(parameterNode.access, AccessKind.PARAMETER));
          }
          name = parameterNode.name;
        }
        else if (methodNode.localVariables != null) {
          // Use name from LocalVariableNodes
          int localVariableIndex = i + (isStatic ? 0 : 1);
          if (localVariableIndex < methodNode.localVariables.size()) {
            name = methodNode.localVariables.get(localVariableIndex).name;
          }
        }

        if (access != null) {
          representation.append(access).append(" ");
        }
        representation.append(type).append(" ").append(name);
      }
    }
    representation.append(")");

    // Exceptions
    if (methodNode.exceptions != null && !methodNode.exceptions.isEmpty()) {
      representation.append(" throws");
      methodNode.exceptions.forEach(exception -> {
        Type exceptionType = nameToTypeElseNull(exception);
        representation.append(" ").append(asmRepresentations.toStringOf(exceptionType));
      });
    }

    // Signature
    if (methodNode.signature != null) {
      representation.append(" // signature: ").append(methodNode.signature);
    }

    return representation.toString();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private Consumer<String> appendMetaInformationLine(StringBuilder representation) {
    return line -> representation.append(META_INFORMATION_INDENT)
                                 .append(line)
                                 .append(System.lineSeparator());
  }

  private String toStringOfParameter(ParameterNode parameter,
                                     int index,
                                     List<AnnotationNode>[] visibleParameterAnnotations,
                                     List<AnnotationNode>[] invisibleParameterAnnotations) {

    var representation = new StringBuilder();

    // Annotations
    List<AnnotationNode> visibleAnnotations = visibleParameterAnnotations != null && index < visibleParameterAnnotations.length
            ? visibleParameterAnnotations[index] : List.of();
    List<AnnotationNode> invisibleAnnotations = invisibleParameterAnnotations != null && index < invisibleParameterAnnotations.length
            ? invisibleParameterAnnotations[index] : List.of();

    createAnnotationNodesRepresentations(asmRepresentations, visibleAnnotations, invisibleAnnotations)
            .forEach(annotationNodeRepresentation -> representation.append(annotationNodeRepresentation).append(System.lineSeparator()));

    // Parameter
    representation.append(asmRepresentations.toStringOf(parameter));

    return representation.toString();
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
