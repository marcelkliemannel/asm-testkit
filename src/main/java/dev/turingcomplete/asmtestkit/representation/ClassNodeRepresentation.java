package dev.turingcomplete.asmtestkit.representation;

import dev.turingcomplete.asmtestkit.asmutils.Access;
import dev.turingcomplete.asmtestkit.asmutils.TypeUtils;
import dev.turingcomplete.asmtestkit.node.AccessNode;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InnerClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.stream.Collectors;

import static dev.turingcomplete.asmtestkit.representation._internal.RepresentationUtils.createAnnotationNodesRepresentations;
import static dev.turingcomplete.asmtestkit.representation._internal.RepresentationUtils.createAttributesRepresentations;
import static dev.turingcomplete.asmtestkit.representation._internal.RepresentationUtils.createTypeAnnotationNodesRepresentations;
import static dev.turingcomplete.asmtestkit.representation._internal.RepresentationUtils.prependToFirstLine;

/**
 * An AssertJ {@link Representation} for a {@link ClassNode}.
 * <p>{@code
 * // Class version: 55
 * // Attribute: NameContent
 *
 * @VisibleAnnotationA [1057: public, super, abstract] class MyClass extends java.lang.Thread implements java.util.function.Supplier // signature: ...
 * <p>
 * [2: private] int field1
 * [2: private] java.lang.Object field2 // signature: TT;
 * <p>
 * [1: public] <init>()
 * L0
 * LINENUMBER 3 L0
 * ALOAD 0 // opcode: 25
 * INVOKESPECIAL java/lang/Thread.<init> ()V // opcode: 183
 * ALOAD 0 // opcode: 25
 * ICONST_4 // opcode: 7
 * PUTFIELD MyClass.field1 : I // opcode: 181
 * RETURN // opcode: 177
 * L1
 * // Local variable: #0 MyClass this // range: L0-L1 // signature: LMyClass<TT;>;
 * // Max locals: 1
 * // Max stack: 2
 * <p>
 * // Source file: MyClass.java
 * // Inner classes: [1: public] MyClass$MyClassInner // outer name: MyClass // inner name: MyClassInner
 * [16409: public, final, enum] MyClass$InnerEnum // outer name: MyClass // inner name: InnerEnum
 * // Nest members: MyClass$MyClassInner
 * MyClass$InnerEnum
 * }</pre>
 *
 * <p>The simplified output contains the class kind and the name, for example:
 * {@code interface foo.bar.MyInterface}
 */
public class ClassNodeRepresentation extends AbstractAsmRepresentation<ClassNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  public static final String FIELDS_INDENT           = " ".repeat(4);
  public static final String METHODS_INDENT          = " ".repeat(4);
  public static final String META_INFORMATION_INDENT = " ".repeat(2) + "// ";

  /**
   * A reusable {@link ClassNodeRepresentation} instance.
   */
  public static final ClassNodeRepresentation INSTANCE = create();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  private ClassNodeRepresentation() {
    super(ClassNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link ClassNodeRepresentation} instance.
   *
   * @return a new {@link ClassNodeRepresentation}; never null;
   */
  public static ClassNodeRepresentation create() {
    return new ClassNodeRepresentation();
  }

  @Override
  protected String doToSimplifiedStringOf(ClassNode classNode) {
    // Kind
    String kind = ((AccessNodeRepresentation) asmRepresentations.getAsmRepresentation(AccessNode.class))
            .toJavaSourceCodeClassKindRepresentation(AccessNode.forClass(classNode.access));

    // Name
    String name = asmRepresentations.toStringOf(TypeUtils.nameToTypeElseNull(classNode.name));

    return kind + " " + name;
  }

  @Override
  protected String doToStringOf(ClassNode classNode) {
    AsmRepresentation<Type> typeRepresentation = asmRepresentations.getAsmRepresentation(Type.class);

    var representation = new StringBuilder();

    // Version
    representation.append("// Class version: ").append(classNode.version).append(System.lineSeparator());

    // Attributes
    createAttributesRepresentations(asmRepresentations, classNode.attrs)
            .forEach(attributeRepresentation -> representation.append("// Attribute: ")
                                                              .append(attributeRepresentation)
                                                              .append(System.lineSeparator()));

    // Annotations
    createAnnotationNodesRepresentations(asmRepresentations, classNode.visibleAnnotations, classNode.invisibleAnnotations)
            .forEach(annotationNodeRepresentation -> representation.append(annotationNodeRepresentation)
                                                                   .append(System.lineSeparator()));

    // Type annotations
    createTypeAnnotationNodesRepresentations(asmRepresentations, classNode.visibleTypeAnnotations, classNode.invisibleTypeAnnotations)
            .forEach(typeAnnotationNodeRepresentation -> representation.append(typeAnnotationNodeRepresentation)
                                                                       .append(System.lineSeparator()));

    // Declaration
    representation.append(createClassDeclaration(classNode)).append(System.lineSeparator()).append(System.lineSeparator());

    // Fields
    if (classNode.fields != null && !classNode.fields.isEmpty()) {
      for (FieldNode fieldNode : classNode.fields) {
        asmRepresentations.getAsmRepresentation(FieldNode.class)
                          .toStringOf(fieldNode).lines()
                          .forEach(line -> representation.append(FIELDS_INDENT).append(line).append(System.lineSeparator()));
      }
      representation.append(System.lineSeparator());
    }

    // Methods
    if (classNode.methods != null && !classNode.methods.isEmpty()) {
      for (MethodNode methodNode : classNode.methods) {
        asmRepresentations.getAsmRepresentation(MethodNode.class)
                          .toStringOf(methodNode).lines()
                          .forEach(line -> representation.append(METHODS_INDENT).append(line)
                                                         .append(System.lineSeparator()));
        representation.append(System.lineSeparator());
      }
    }

    // Source file
    if (classNode.sourceFile != null) {
      representation.append(META_INFORMATION_INDENT).append("Source file: ")
                    .append(classNode.sourceFile)
                    .append(System.lineSeparator());
    }

    // Source debug
    if (classNode.sourceDebug != null) {
      representation.append(META_INFORMATION_INDENT).append("Source debug: ")
                    .append(classNode.sourceDebug)
                    .append(System.lineSeparator());
    }

    // Outer class
    if (classNode.outerClass != null) {
      representation.append(META_INFORMATION_INDENT).append("Outer class: ")
                    .append(typeRepresentation.toStringOf(TypeUtils.nameToTypeElseNull(classNode.outerClass)))
                    .append(System.lineSeparator());
    }

    // Outer method
    if (classNode.outerMethod != null || classNode.outerMethodDesc != null) {
      representation.append(META_INFORMATION_INDENT).append("Outer method: ")
                    .append(classNode.outerMethod != null ? classNode.outerMethod : "null");
      if (classNode.outerMethodDesc != null) {
        representation.append(classNode.outerMethodDesc);
      }
      representation.append(System.lineSeparator());
    }

    // Inner classes
    if (classNode.innerClasses != null && !classNode.innerClasses.isEmpty()) {
      AsmRepresentation<InnerClassNode> innerClassNodeRepresentation = asmRepresentations.getAsmRepresentation(InnerClassNode.class);
      String innerClasses = classNode.innerClasses.stream()
                                                  .map(innerClassNodeRepresentation::toStringOf)
                                                  .collect(Collectors.joining(System.lineSeparator()));
      representation.append(prependToFirstLine(META_INFORMATION_INDENT + "Inner classes: ", innerClasses))
                    .append(System.lineSeparator());
    }

    // Nest host class
    if (classNode.nestHostClass != null) {
      representation.append(META_INFORMATION_INDENT).append("Nest host class: ")
                    .append(typeRepresentation.toStringOf(TypeUtils.nameToTypeElseNull(classNode.nestHostClass)))
                    .append(System.lineSeparator());
    }

    // Nest members
    if (classNode.nestMembers != null && !classNode.nestMembers.isEmpty()) {
      String nestMembers = TypeUtils.namesToTypes(classNode.nestMembers).stream()
                                    .map(typeRepresentation::toStringOf)
                                    .collect(Collectors.joining(System.lineSeparator()));
      representation.append(prependToFirstLine(META_INFORMATION_INDENT + "Nest members: ", nestMembers))
                    .append(System.lineSeparator());
    }

    // Permitted subclasses
    if (classNode.permittedSubclasses != null) {
      String permittedSubclasses = TypeUtils.namesToTypes(classNode.permittedSubclasses).stream()
                                            .map(typeRepresentation::toStringOf)
                                            .collect(Collectors.joining(System.lineSeparator()));
      representation.append(prependToFirstLine(META_INFORMATION_INDENT + "Permitted subclasses: ", permittedSubclasses))
                    .append(System.lineSeparator());
    }

    return representation.toString();
  }

  /**
   * Creates the class declaration as one single line, including: access,
   * name, hierarchy and signature.
   *
   * @param classNode the {@link ClassNode}; never null
   * @return a {@link String} representation of {@code classNode}'s
   * declaration; never null.
   */
  public String createClassDeclaration(ClassNode classNode) {
    var representation = new StringBuilder();

    // Access
    AccessNode accessNode = AccessNode.forClass(classNode.access);
    if (classNode.access >= 0) {
      representation.append(asmRepresentations.toStringOf(accessNode)).append(" ");
    }

    // Kind
    representation.append(((AccessNodeRepresentation) asmRepresentations.getAsmRepresentation(AccessNode.class))
                                  .toJavaSourceCodeClassKindRepresentation(accessNode));

    // Name
    representation.append(" ").append(asmRepresentations.toStringOf(TypeUtils.nameToTypeElseNull(classNode.name)));

    // Hierarchy
    String interfaces = null;
    if (classNode.interfaces != null && !classNode.interfaces.isEmpty()) {
      interfaces = classNode.interfaces.stream()
                                       .map(_interface -> asmRepresentations.toStringOf(TypeUtils.nameToTypeElseNull(_interface)))
                                       .collect(Collectors.joining(" ,"));
    }

    if (Access.INTERFACE.check(classNode.access)) {
      if (interfaces != null) {
        representation.append(" extends ").append(interfaces);
      }
    }
    else {
      if (classNode.superName != null) {
        representation.append(" extends ").append(asmRepresentations.toStringOf(Type.getObjectType(classNode.superName)));
      }
      if (classNode.interfaces != null && !classNode.interfaces.isEmpty()) {
        representation.append(" implements ").append(interfaces);
      }
    }

    // Signature
    if (classNode.signature != null) {
      representation.append(" // signature: ").append(classNode.signature);
    }

    return representation.toString();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
