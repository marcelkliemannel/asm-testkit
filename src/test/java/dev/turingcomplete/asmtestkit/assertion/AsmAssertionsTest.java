package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.__helper.DummyAttribute;
import dev.turingcomplete.asmtestkit.__helper.VisibleAnnotationA;
import dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA;
import dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationB;
import dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils;
import dev.turingcomplete.asmtestkit.asmutils.MethodNodeUtils;
import dev.turingcomplete.asmtestkit.compile.CompilationResult;
import dev.turingcomplete.asmtestkit.node.AccessNode;
import dev.turingcomplete.asmtestkit.node.AnnotationDefaultNode;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.TypeReference;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InnerClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LocalVariableAnnotationNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ParameterNode;
import org.objectweb.asm.tree.TryCatchBlockNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static dev.turingcomplete.asmtestkit.asmutils.MethodNodeUtils.extractLabelIndices;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatAccesses;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatAnnotationDefaulls;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatFields;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatInnerClasses;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatLabels;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatLocalVariableAnnotations;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatLocalVariables;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatMethods;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatParameters;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatTryCatchBlocks;
import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AsmAssertionsTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testAssertThatAttributes() {
    // Test comparator
    AsmAssertions.assertThatAttributes(List.of(new DummyAttribute("A"), new DummyAttribute("A")))
                 .containsExactlyInAnyOrderElementsOf(List.of(new DummyAttribute("A"), new DummyAttribute("A")));

    // Test representation
    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThatAttributes(List.of(new DummyAttribute("A"), new DummyAttribute("A")))
                                                     .containsExactlyInAnyOrderElementsOf(List.of(new DummyAttribute("C"), new DummyAttribute("D"))))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Attributes] \n" +
                          "Expecting actual:\n" +
                          "  [Anull, Anull]\n" +
                          "to contain exactly in any order:\n" +
                          "  [Cnull, Dnull]\n" +
                          "elements not found:\n" +
                          "  [Cnull, Dnull]\n" +
                          "and elements not expected:\n" +
                          "  [Anull, Anull]\n" +
                          "when comparing values using AttributeComparator");
  }

  @Test
  void testAssertThatAnnotationNodes() {
    // Test comparator
    AsmAssertions.assertThatAnnotations(List.of(new AnnotationNode("LA;"), new AnnotationNode("LA;")))
                 .containsExactlyInAnyOrderElementsOf(List.of(new AnnotationNode("LA;"), new AnnotationNode("LA;")));

    // Test representation
    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThatAnnotations(List.of(new AnnotationNode("LA;"), new AnnotationNode("LB;")))
                                                     .containsExactlyInAnyOrderElementsOf(List.of(new AnnotationNode("LB;"), new AnnotationNode("LC;"))))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Annotations] \n" +
                          "Expecting actual:\n" +
                          "  [@A, @B]\n" +
                          "to contain exactly in any order:\n" +
                          "  [@B, @C]\n" +
                          "elements not found:\n" +
                          "  [@C]\n" +
                          "and elements not expected:\n" +
                          "  [@A]\n" +
                          "when comparing values using AnnotationNodeComparator");
  }

  @Test
  void testAssertThatTypeAnnotationNodes() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA;" +
                     "class MyClass<@VisibleTypeParameterAnnotationA S, @VisibleTypeParameterAnnotationA T, @VisibleTypeParameterAnnotationA U> { }";

    List<TypeAnnotationNode> visibleTypeAnnotations = create()
            .addToClasspath(VisibleTypeParameterAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .visibleTypeAnnotations;

    TypeAnnotationNode firstTypeAnnotationNode = visibleTypeAnnotations.get(0);
    TypeAnnotationNode copyOfFirstTypeAnnotationNode = new TypeAnnotationNode(firstTypeAnnotationNode.typeRef, firstTypeAnnotationNode.typePath, firstTypeAnnotationNode.desc);
    TypeAnnotationNode secondTypeAnnotationNode = visibleTypeAnnotations.get(1);
    TypeAnnotationNode thirdTypeAnnotationNode = visibleTypeAnnotations.get(2);

    // Test comparator
    AsmAssertions.assertThatTypeAnnotations(List.of(firstTypeAnnotationNode, copyOfFirstTypeAnnotationNode))
                 .containsExactlyInAnyOrderElementsOf(List.of(firstTypeAnnotationNode, copyOfFirstTypeAnnotationNode));

    // Test representation
    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThatTypeAnnotations(List.of(firstTypeAnnotationNode, secondTypeAnnotationNode))
                                                     .containsExactlyInAnyOrderElementsOf(List.of(secondTypeAnnotationNode, thirdTypeAnnotationNode)))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Type Annotations] \n" +
                          "Expecting actual:\n" +
                          "  [@dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA // reference: class_type_parameter=0; path: null,\n" +
                          "    @dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA // reference: class_type_parameter=1; path: null]\n" +
                          "to contain exactly in any order:\n" +
                          "  [@dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA // reference: class_type_parameter=1; path: null,\n" +
                          "    @dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA // reference: class_type_parameter=2; path: null]\n" +
                          "elements not found:\n" +
                          "  [@dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA // reference: class_type_parameter=2; path: null]\n" +
                          "and elements not expected:\n" +
                          "  [@dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA // reference: class_type_parameter=0; path: null]\n" +
                          "when comparing values using TypeAnnotationNodeComparator");
  }

  @Test
  void testAssertThatTypePaths() {
    // Test comparator
    AsmAssertions.assertThatTypePaths(List.of(TypePath.fromString("*"), TypePath.fromString("*")))
                 .containsExactlyInAnyOrderElementsOf(List.of(TypePath.fromString("*"), TypePath.fromString("*")));

    // Test representation
    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThatTypePaths(List.of(TypePath.fromString("[1;"), TypePath.fromString("[2;")))
                                                     .containsExactlyInAnyOrderElementsOf(List.of(TypePath.fromString("[2;"), TypePath.fromString("[3;"))))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Type Paths] \n" +
                          "Expecting actual:\n" +
                          "  [[1;, [2;]\n" +
                          "to contain exactly in any order:\n" +
                          "  [[2;, [3;]\n" +
                          "elements not found:\n" +
                          "  [[3;]\n" +
                          "and elements not expected:\n" +
                          "  [[1;]\n" +
                          "when comparing values using TypePathComparator");
  }

  @Test
  void testAssertThatTypes() {
    // Test comparator
    AsmAssertions.assertThatTypes(List.of(Type.BOOLEAN_TYPE, Type.CHAR_TYPE))
                 .containsExactlyInAnyOrderElementsOf(List.of(Type.BOOLEAN_TYPE, Type.CHAR_TYPE));

    // Test representation
    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThatTypes(List.of(Type.BOOLEAN_TYPE, Type.CHAR_TYPE))
                                                     .containsExactlyInAnyOrderElementsOf(List.of(Type.CHAR_TYPE, Type.SHORT_TYPE)))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Types] \n" +
                          "Expecting actual:\n" +
                          "  [boolean, char]\n" +
                          "to contain exactly in any order:\n" +
                          "  [char, short]\n" +
                          "elements not found:\n" +
                          "  [short]\n" +
                          "and elements not expected:\n" +
                          "  [boolean]\n" +
                          "when comparing values using TypeComparator");
  }

  @Test
  void testAssertThatTypeReferences() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA;" +
                     "import dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationB;" +
                     "class MyClass<@VisibleTypeParameterAnnotationA @VisibleTypeParameterAnnotationB S, @VisibleTypeParameterAnnotationA T> { }";

    List<TypeAnnotationNode> visibleTypeAnnotations = create()
            .addToClasspath(VisibleTypeParameterAnnotationA.class)
            .addToClasspath(VisibleTypeParameterAnnotationB.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .visibleTypeAnnotations;

    TypeAnnotationNode firstTypeAnnotationNode = visibleTypeAnnotations.get(0);
    TypeAnnotationNode secondTypeAnnotationNode = visibleTypeAnnotations.get(1);
    TypeAnnotationNode thirdTypeAnnotationNode = visibleTypeAnnotations.get(2);

    // Test comparator
    AsmAssertions.assertThatTypeReferences(List.of(new TypeReference(firstTypeAnnotationNode.typeRef), new TypeReference(secondTypeAnnotationNode.typeRef)))
                 .containsExactlyInAnyOrderElementsOf(List.of(new TypeReference(firstTypeAnnotationNode.typeRef), new TypeReference(secondTypeAnnotationNode.typeRef)));

    // Test representation
    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThatTypeReferences(List.of(new TypeReference(firstTypeAnnotationNode.typeRef), new TypeReference(secondTypeAnnotationNode.typeRef)))
                                                     .containsExactlyInAnyOrderElementsOf(List.of(new TypeReference(secondTypeAnnotationNode.typeRef), new TypeReference(thirdTypeAnnotationNode.typeRef))))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Type References] \n" +
                          "Expecting actual:\n" +
                          "  [class_type_parameter=0, class_type_parameter=0]\n" +
                          "to contain exactly in any order:\n" +
                          "  [class_type_parameter=0, class_type_parameter=1]\n" +
                          "elements not found:\n" +
                          "  [class_type_parameter=1]\n" +
                          "and elements not expected:\n" +
                          "  [class_type_parameter=0]\n" +
                          "when comparing values using TypeReferenceComparator");
  }

  @Test
  void testAssertThatInstructions() throws IOException {
    @Language("Java")
    String firstMyClass = "class FirstMyClass {" +
                          "  void myMethod() {" +
                          "    System.out.println(1);" +
                          "  }" +
                          "}";
    @Language("Java")
    String secondMyClass = "class SecondMyClass {" +
                           "  void myMethod() {" +
                           "    System.out.println(1);" +
                           "  }" +
                           "}";
    @Language("Java")
    String thirdMyClass = "class ThirdMyClass {" +
                          "  void myMethod() {" +
                          "    throw new IllegalArgumentException();" +
                          "  }" +
                          "}";
    CompilationResult result = create()
            .addJavaInputSource(firstMyClass)
            .addJavaInputSource(secondMyClass)
            .addJavaInputSource(thirdMyClass)
            .compile();

    InsnList firstInstructions = result.readClassNode("FirstMyClass").methods.get(1).instructions;
    InsnList secondInstructions = result.readClassNode("SecondMyClass").methods.get(1).instructions;
    InsnList thirdInstructions = result.readClassNode("ThirdMyClass").methods.get(1).instructions;

    AsmAssertions.assertThatInstructions(firstInstructions)
                 .isEqualTo(secondInstructions);

    assertThatThrownBy(() -> AsmAssertions.assertThatInstructions(firstInstructions)
                                          .isEqualTo(thirdInstructions))
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Instructions] \n" +
                        "expected: L0\n" +
                        "  LINENUMBER 1 L0\n" +
                        "  NEW java/lang/IllegalArgumentException // opcode: 187\n" +
                        "  DUP // opcode: 89\n" +
                        "  INVOKESPECIAL java/lang/IllegalArgumentException.<init> ()V // opcode: 183\n" +
                        "  ATHROW // opcode: 191\n" +
                        "L1\n" +
                        " but was: L0\n" +
                        "  LINENUMBER 1 L0\n" +
                        "  GETSTATIC java/lang/System.out : Ljava/io/PrintStream; // opcode: 178\n" +
                        "  ICONST_1 // opcode: 4\n" +
                        "  INVOKEVIRTUAL java/io/PrintStream.println (I)V // opcode: 182\n" +
                        "  RETURN // opcode: 177\n" +
                        "L1\n" +
                        "when comparing values using InsnListComparator");
  }

  @Test
  void testAssertThatInstructionsIgnoreLineNumbers() throws IOException {
    @Language("Java")
    String firstMyClass = "class FirstMyClass {" +
                          "  void myMethod() {" +
                          "    System.out.println(1);\n" +
                          "  }" +
                          "}";
    @Language("Java")
    String secondMyClass = "class SecondMyClass {" +
                           "  void myMethod() {" +
                           "    System.out.println(1);" +
                           "  }" +
                           "}";
    @Language("Java")
    String thirdMyClass = "class ThirdMyClass {" +
                          "  void myMethod() {" +
                          "    System.out.println(2);" +
                          "  }" +
                          "}";
    CompilationResult result = create()
            .addJavaInputSource(firstMyClass)
            .addJavaInputSource(secondMyClass)
            .addJavaInputSource(thirdMyClass)
            .compile();

    InsnList firstInstructions = result.readClassNode("FirstMyClass").methods.get(1).instructions;
    InsnList secondInstructions = result.readClassNode("SecondMyClass").methods.get(1).instructions;
    InsnList thirdInstructions = result.readClassNode("ThirdMyClass").methods.get(1).instructions;

    AsmAssertions.assertThatInstructionsIgnoreLineNumbers(firstInstructions)
                 .isEqualTo(secondInstructions);

    assertThatThrownBy(() -> AsmAssertions.assertThatInstructionsIgnoreLineNumbers(secondInstructions)
                                          .isEqualTo(thirdInstructions))
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Instructions - ignore line numbers] \n" +
                        "expected: L0\n" +
                        "  LINENUMBER 1 L0\n" +
                        "  GETSTATIC java/lang/System.out : Ljava/io/PrintStream; // opcode: 178\n" +
                        "  ICONST_2 // opcode: 5\n" +
                        "  INVOKEVIRTUAL java/io/PrintStream.println (I)V // opcode: 182\n" +
                        "  RETURN // opcode: 177\n" +
                        "L1\n" +
                        " but was: L0\n" +
                        "  LINENUMBER 1 L0\n" +
                        "  GETSTATIC java/lang/System.out : Ljava/io/PrintStream; // opcode: 178\n" +
                        "  ICONST_1 // opcode: 4\n" +
                        "  INVOKEVIRTUAL java/io/PrintStream.println (I)V // opcode: 182\n" +
                        "  RETURN // opcode: 177\n" +
                        "L1\n" +
                        "when comparing values using InsnListComparator");
  }

  @Test
  void testAssertThatLabels() {
    var firstLabel = new Label();
    var firstLabelNode = new LabelNode(firstLabel);

    var secondLabel = new Label();
    var secondLabelNode = new LabelNode(secondLabel);
    var thirdLabelNode = new LabelNode(secondLabel);

    var thirdLabel = new Label();
    var fourthLabelNode = new LabelNode(thirdLabel);

    // Positive
    assertThatLabels(List.of(secondLabelNode))
            .containsExactlyInAnyOrderElementsOf(List.of(thirdLabelNode));

    // Negative
    ThrowableAssert.ThrowingCallable throwingCallable = () -> assertThatLabels(List.of(firstLabelNode, secondLabelNode))
            .useLabelNameLookup(DefaultLabelIndexLookup.create(Map.of(firstLabel, 1, secondLabel, 2, thirdLabel, 3)))
            .containsExactlyInAnyOrderElementsOf(List.of(thirdLabelNode, fourthLabelNode));
    assertThatThrownBy(throwingCallable)
            .isInstanceOf(AssertionError.class)
            .hasMessage(String.format("[Labels] \n" +
                                      "Expecting actual:\n" +
                                      "  [%1$s, %2$s]\n" +
                                      "to contain exactly in any order:\n" +
                                      "  [%2$s, %3$s]\n" +
                                      "elements not found:\n" +
                                      "  [%3$s]\n" +
                                      "and elements not expected:\n" +
                                      "  [%1$s]\n" +
                                      "when comparing values using LabelNodeComparator",
                                      "L1", "L2", "L3"));
  }

  @Test
  void testAssertThatFields() {
    var first1FieldNode = new FieldNode(0, "first", "I", null, null);
    var first2FieldNode = new FieldNode(0, "first", "I", null, null);
    var secondFieldNode = new FieldNode(0, "second", "Ljava/lang/String;", null, "foo");
    secondFieldNode.visibleAnnotations = List.of(AnnotationNodeUtils.createAnnotationNode(Deprecated.class));
    var thirdFieldNode = new FieldNode(1, "third", "Ljava/lang/Integer;", null, null);

    // Positive
    assertThatFields(List.of(first1FieldNode))
            .containsExactlyInAnyOrderElementsOf(List.of(first2FieldNode));

    // Negative
    assertThatThrownBy(() -> assertThatFields(List.of(first1FieldNode, secondFieldNode)).containsExactlyInAnyOrderElementsOf(List.of(secondFieldNode, thirdFieldNode)))
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Labels] \n" +
                        "Expecting actual:\n" +
                        "  [[0] int first, @java.lang.Deprecated\n" +
                        "[0] java.lang.String second = foo]\n" +
                        "to contain exactly in any order:\n" +
                        "  [@java.lang.Deprecated\n" +
                        "[0] java.lang.String second = foo,\n" +
                        "    [1: public] java.lang.Integer third]\n" +
                        "elements not found:\n" +
                        "  [[1: public] java.lang.Integer third]\n" +
                        "and elements not expected:\n" +
                        "  [[0] int first]\n" +
                        "when comparing values using FieldNodeComparator");
  }

  @Test
  void testAssertThatLocalVariableAnnotations() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA;import dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationB;import dev.turingcomplete.asmtestkit.__helper.*;" +
                     "class MyClass {" +
                     "   String myMethod(String param) {" +
                     "     String @VisibleTypeParameterAnnotationA [] a = { param + 1 };" +
                     "     @VisibleTypeParameterAnnotationA String b = param + 2;" +
                     "     @VisibleTypeParameterAnnotationB String c = param + 2;" +
                     "     return b + 3;" +
                     "   }" +
                     " }";

    MethodNode methodNode = create()
            .addToClasspath(VisibleAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods.get(1);

    LocalVariableAnnotationNode firstLocalVariableAnnotationNode = methodNode.visibleLocalVariableAnnotations.get(0);
    LocalVariableAnnotationNode secondLocalVariableAnnotationNode = methodNode.visibleLocalVariableAnnotations.get(1);
    LocalVariableAnnotationNode thirdLocalVariableAnnotationNode = methodNode.visibleLocalVariableAnnotations.get(2);

    // Positive
    assertThatLocalVariableAnnotations(List.of(firstLocalVariableAnnotationNode, secondLocalVariableAnnotationNode, thirdLocalVariableAnnotationNode))
            .containsExactlyInAnyOrderElementsOf(List.of(firstLocalVariableAnnotationNode, secondLocalVariableAnnotationNode, thirdLocalVariableAnnotationNode));

    // Negative
    ThrowableAssert.ThrowingCallable throwingCallable = () -> assertThatLocalVariableAnnotations(List.of(firstLocalVariableAnnotationNode, secondLocalVariableAnnotationNode))
            .useLabelNameLookup(DefaultLabelIndexLookup.create(extractLabelIndices(methodNode)))
            .containsExactlyInAnyOrderElementsOf(List.of(secondLocalVariableAnnotationNode, thirdLocalVariableAnnotationNode));
    assertThatThrownBy(throwingCallable)
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Local Variable Annotations] \n" +
                        "Expecting actual:\n" +
                        "  [@dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA // reference: local_variable; path: null // range: #2 L1-L4,\n" +
                        "    @dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA // reference: local_variable; path: null // range: #3 L2-L4]\n" +
                        "to contain exactly in any order:\n" +
                        "  [@dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA // reference: local_variable; path: null // range: #3 L2-L4,\n" +
                        "    @dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationB // reference: local_variable; path: null // range: #4 L3-L4]\n" +
                        "elements not found:\n" +
                        "  [@dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationB // reference: local_variable; path: null // range: #4 L3-L4]\n" +
                        "and elements not expected:\n" +
                        "  [@dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA // reference: local_variable; path: null // range: #2 L1-L4]\n" +
                        "when comparing values using LocalVariableAnnotationNodeComparator");
  }

  @Test
  void testAssertThatLocalVariables() throws IOException {
    @Language("Java")
    String myClass = "class MyClass<T> {" +
                     "   void myMethod() {" +
                     "     String a = \"foo\";" +
                     "     int[] b = new int[0];" +
                     "     T c = null;" +
                     "   }" +
                     " }";

    MethodNode methodNode = create()
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods.get(1);

    LocalVariableNode firstVariable = methodNode.localVariables.get(1);
    LocalVariableNode secondVariable = methodNode.localVariables.get(2);
    LocalVariableNode thirdVariable = methodNode.localVariables.get(3);

    // Positive
    assertThatLocalVariables(List.of(firstVariable, secondVariable))
            .containsExactlyInAnyOrderElementsOf(List.of(secondVariable, firstVariable));

    // Negative
    ThrowableAssert.ThrowingCallable throwingCallable = () -> assertThatLocalVariables(List.of(firstVariable, secondVariable))
            .useLabelNameLookup(DefaultLabelIndexLookup.create(extractLabelIndices(methodNode)))
            .containsExactlyInAnyOrderElementsOf(List.of(secondVariable, thirdVariable));
    assertThatThrownBy(throwingCallable)
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Local Variables] \n" +
                        "Expecting actual:\n" +
                        "  [#1 java.lang.String a // range: L1-L4, #2 int[] b // range: L2-L4]\n" +
                        "to contain exactly in any order:\n" +
                        "  [#2 int[] b // range: L2-L4,\n" +
                        "    #3 java.lang.Object c // range: L3-L4 // signature: TT;]\n" +
                        "elements not found:\n" +
                        "  [#3 java.lang.Object c // range: L3-L4 // signature: TT;]\n" +
                        "and elements not expected:\n" +
                        "  [#1 java.lang.String a // range: L1-L4]\n" +
                        "when comparing values using LocalVariableNodeComparator");
  }

  @Test
  void testAssertThatTryCatchBlocks() throws IOException {
    @Language("Java")
    String myClass = "class MyClass<T> {" +
                     "   void myMethod() {" +
                     "     try {" +
                     "       throw new java.io.IOException();" +
                     "     }" +
                     "     catch (java.io.IOException | java.lang.IllegalArgumentException e) {" +
                     "       System.out.println(2);" +
                     "     }" +
                     "     finally {" +
                     "       System.out.println(4);" +
                     "     }" +
                     "   }" +
                     " }";

    MethodNode methodA = create()
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods.get(1);

    MethodNode methodB = create()
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods.get(1);


    TryCatchBlockNode firstATryCatchBlock = methodA.tryCatchBlocks.get(0);
    TryCatchBlockNode firstBTryCatchBlock = methodB.tryCatchBlocks.get(0);
    TryCatchBlockNode secondATryCatchBlock = methodA.tryCatchBlocks.get(1);
    TryCatchBlockNode secondBTryCatchBlock = methodB.tryCatchBlocks.get(1);
    TryCatchBlockNode thirdATryCatchBlock = methodA.tryCatchBlocks.get(2);
    TryCatchBlockNode thirdBTryCatchBlock = methodB.tryCatchBlocks.get(2);

    LabelIndexLookup labelIndexLookup = DefaultLabelIndexLookup.create(MethodNodeUtils.extractLabelIndices(methodA, methodB));

    // Positive
    assertThatTryCatchBlocks(List.of(firstATryCatchBlock, secondATryCatchBlock, thirdATryCatchBlock))
            .useLabelNameLookup(labelIndexLookup)
            .containsExactlyInAnyOrderElementsOf(List.of(firstBTryCatchBlock, secondBTryCatchBlock, thirdBTryCatchBlock));

    // Negative
    ThrowableAssert.ThrowingCallable throwingCallable = () -> assertThatTryCatchBlocks(List.of(firstATryCatchBlock, secondATryCatchBlock))
            .useLabelNameLookup(labelIndexLookup)
            .containsExactlyInAnyOrderElementsOf(List.of(secondBTryCatchBlock, thirdBTryCatchBlock));
    assertThatThrownBy(throwingCallable)
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Try Catch Blocks] \n" +
                        "Expecting actual:\n" +
                        "  [java.io.IOException // range: L0-L1; handled in: L1,\n" +
                        "    java.lang.IllegalArgumentException // range: L0-L1; handled in: L1]\n" +
                        "to contain exactly in any order:\n" +
                        "  [java.lang.IllegalArgumentException // range: L0-L1; handled in: L1,\n" +
                        "    finally // range: L0-L2; handled in: L3]\n" +
                        "elements not found:\n" +
                        "  [finally // range: L0-L2; handled in: L3]\n" +
                        "and elements not expected:\n" +
                        "  [java.io.IOException // range: L0-L1; handled in: L1]\n" +
                        "when comparing values using TryCatchBlockNodeComparator");
  }

  @Test
  void testAssertThatParameters() {
    var first = new ParameterNode("a", 16);
    var second = new ParameterNode("b", 16);
    var third = new ParameterNode("b", 4112);

    // Positive
    assertThatParameters(List.of(first, second, third))
            .containsExactlyInAnyOrderElementsOf(List.of(first, second, third));

    // Negative
    assertThatThrownBy(() -> assertThatParameters(List.of(first, second)).containsExactlyInAnyOrderElementsOf(List.of(second, third)))
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Parameters] \n" +
                        "Expecting actual:\n" +
                        "  [[16: final] a, [16: final] b]\n" +
                        "to contain exactly in any order:\n" +
                        "  [[16: final] b, [4112: final, synthetic] b]\n" +
                        "elements not found:\n" +
                        "  [[4112: final, synthetic] b]\n" +
                        "and elements not expected:\n" +
                        "  [[16: final] a]\n" +
                        "when comparing values using ParameterNodeComparator");
  }

  @Test
  void testAssertThatAnnotationDefaultValues() {
    AnnotationDefaultNode first = AnnotationDefaultNode.create(AnnotationNodeUtils.createAnnotationNode(Deprecated.class, "since", "1"));
    AnnotationDefaultNode second = AnnotationDefaultNode.create(AnnotationNodeUtils.createAnnotationNode(Deprecated.class, "since", "2"));
    AnnotationDefaultNode third = AnnotationDefaultNode.create(AnnotationNodeUtils.createAnnotationNode(Deprecated.class, "since", "3"));


    // Positive
    assertThatAnnotationDefaulls(List.of(first, second, third))
            .containsExactlyInAnyOrderElementsOf(List.of(first, second, third));

    // Negative
    assertThatThrownBy(() -> assertThatAnnotationDefaulls(List.of(first, second)).containsExactlyInAnyOrderElementsOf(List.of(second, third)))
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Annotation Defaults] \n" +
                        "Expecting actual:\n" +
                        "  [@java.lang.Deprecated(since=\"1\"), @java.lang.Deprecated(since=\"2\")]\n" +
                        "to contain exactly in any order:\n" +
                        "  [@java.lang.Deprecated(since=\"2\"), @java.lang.Deprecated(since=\"3\")]\n" +
                        "elements not found:\n" +
                        "  [@java.lang.Deprecated(since=\"3\")]\n" +
                        "and elements not expected:\n" +
                        "  [@java.lang.Deprecated(since=\"1\")]\n" +
                        "when comparing values using AnnotationDefaultValueComparator");
  }

  @Test
  void testAssertAccesses() {
    var first = AccessNode.forClass(Opcodes.ACC_PUBLIC);
    var second = AccessNode.forClass(Opcodes.ACC_ABSTRACT);
    var third = AccessNode.forClass(Opcodes.ACC_INTERFACE);

    // Positive
    assertThatAccesses(List.of(first, second, third))
            .containsExactlyInAnyOrderElementsOf(List.of(first, second, third));

    // Negative
    assertThatThrownBy(() -> assertThatAccesses(List.of(first, second)).containsExactlyInAnyOrderElementsOf(List.of(second, third)))
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Accesses] \n" +
                        "Expecting actual:\n" +
                        "  [[1: public], [1024: abstract]]\n" +
                        "to contain exactly in any order:\n" +
                        "  [[1024: abstract], [512: interface]]\n" +
                        "elements not found:\n" +
                        "  [[512: interface]]\n" +
                        "and elements not expected:\n" +
                        "  [[1: public]]\n" +
                        "when comparing values using AccessNodeComparator");
  }

  @Test
  void testAssertThatMethods() throws IOException {
    @Language("Java")
    String myClass = "class MyClass {" +
                     "   void myMethod1() {" +
                     "     System.out.println(1);" +
                     "   }" +
                     "   int myMethod2() {" +
                     "     System.out.println(2);" +
                     "     return 1;" +
                     "   }" +
                     "   String myMethod3() {" +
                     "     System.out.println(3);" +
                     "     return null;" +
                     "   }" +
                     " }";

    List<MethodNode> methods = create()
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods;

    MethodNode firstMethodNode = methods.get(0);
    MethodNode secondMethodNode = methods.get(1);
    MethodNode thirdMethodNode = methods.get(2);

    // Positive
    assertThatMethods(List.of(firstMethodNode, secondMethodNode, thirdMethodNode))
            .containsExactlyInAnyOrderElementsOf(List.of(firstMethodNode, secondMethodNode, thirdMethodNode));

    // Negative
    ThrowableAssert.ThrowingCallable throwingCallable = () -> assertThatMethods(List.of(firstMethodNode, secondMethodNode))
            .containsExactlyInAnyOrderElementsOf(List.of(secondMethodNode, thirdMethodNode));
    assertThatThrownBy(throwingCallable)
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Methods] \n" +
                        "Expecting actual:\n" +
                        "  [[0] <init>()\n" +
                        "    L0\n" +
                        "      LINENUMBER 1 L0\n" +
                        "      ALOAD 0 // opcode: 25\n" +
                        "      INVOKESPECIAL java/lang/Object.<init> ()V // opcode: 183\n" +
                        "      RETURN // opcode: 177\n" +
                        "    L1\n" +
                        "  // Local variable: #0 MyClass this // range: L0-L1\n" +
                        "  // Max locals: 1\n" +
                        "  // Max stack: 1,\n" +
                        "    [0] void myMethod1()\n" +
                        "    L0\n" +
                        "      LINENUMBER 1 L0\n" +
                        "      GETSTATIC java/lang/System.out : Ljava/io/PrintStream; // opcode: 178\n" +
                        "      ICONST_1 // opcode: 4\n" +
                        "      INVOKEVIRTUAL java/io/PrintStream.println (I)V // opcode: 182\n" +
                        "      RETURN // opcode: 177\n" +
                        "    L1\n" +
                        "  // Local variable: #0 MyClass this // range: L0-L1\n" +
                        "  // Max locals: 1\n" +
                        "  // Max stack: 2]\n" +
                        "to contain exactly in any order:\n" +
                        "  [[0] void myMethod1()\n" +
                        "    L0\n" +
                        "      LINENUMBER 1 L0\n" +
                        "      GETSTATIC java/lang/System.out : Ljava/io/PrintStream; // opcode: 178\n" +
                        "      ICONST_1 // opcode: 4\n" +
                        "      INVOKEVIRTUAL java/io/PrintStream.println (I)V // opcode: 182\n" +
                        "      RETURN // opcode: 177\n" +
                        "    L1\n" +
                        "  // Local variable: #0 MyClass this // range: L0-L1\n" +
                        "  // Max locals: 1\n" +
                        "  // Max stack: 2,\n" +
                        "    [0] int myMethod2()\n" +
                        "    L0\n" +
                        "      LINENUMBER 1 L0\n" +
                        "      GETSTATIC java/lang/System.out : Ljava/io/PrintStream; // opcode: 178\n" +
                        "      ICONST_2 // opcode: 5\n" +
                        "      INVOKEVIRTUAL java/io/PrintStream.println (I)V // opcode: 182\n" +
                        "      ICONST_1 // opcode: 4\n" +
                        "      IRETURN // opcode: 172\n" +
                        "    L1\n" +
                        "  // Local variable: #0 MyClass this // range: L0-L1\n" +
                        "  // Max locals: 1\n" +
                        "  // Max stack: 2]\n" +
                        "elements not found:\n" +
                        "  [[0] int myMethod2()\n" +
                        "    L0\n" +
                        "      LINENUMBER 1 L0\n" +
                        "      GETSTATIC java/lang/System.out : Ljava/io/PrintStream; // opcode: 178\n" +
                        "      ICONST_2 // opcode: 5\n" +
                        "      INVOKEVIRTUAL java/io/PrintStream.println (I)V // opcode: 182\n" +
                        "      ICONST_1 // opcode: 4\n" +
                        "      IRETURN // opcode: 172\n" +
                        "    L1\n" +
                        "  // Local variable: #0 MyClass this // range: L0-L1\n" +
                        "  // Max locals: 1\n" +
                        "  // Max stack: 2]\n" +
                        "and elements not expected:\n" +
                        "  [[0] <init>()\n" +
                        "    L0\n" +
                        "      LINENUMBER 1 L0\n" +
                        "      ALOAD 0 // opcode: 25\n" +
                        "      INVOKESPECIAL java/lang/Object.<init> ()V // opcode: 183\n" +
                        "      RETURN // opcode: 177\n" +
                        "    L1\n" +
                        "  // Local variable: #0 MyClass this // range: L0-L1\n" +
                        "  // Max locals: 1\n" +
                        "  // Max stack: 1]\n" +
                        "when comparing values using MethodNodeComparator");
  }

  @Test
  void testAssertThatInnerClasses() {
    var first = new InnerClassNode("Outer$Inner1", "Outer", "Inner1", Opcodes.ACC_STATIC);
    var second = new InnerClassNode("Outer$Inner2", "Outer", "Inner2", Opcodes.ACC_PUBLIC);
    var third = new InnerClassNode("Outer$Inner3", "Outer", "Inner3", Opcodes.ACC_ABSTRACT);

    assertThatInnerClasses(List.of(first, second, third))
            .containsExactlyInAnyOrderElementsOf(List.of(first, second, third));

    assertThatThrownBy(() -> assertThatInnerClasses(List.of(first, second)).containsExactlyInAnyOrderElementsOf(List.of(second, third)))
            .isInstanceOf(AssertionError.class)
            .hasMessage("[Inner classes] \n" +
                        "Expecting actual:\n" +
                        "  [[8] Outer$Inner1 // outer name: Outer // inner name: Inner1,\n" +
                        "    [1: public] Outer$Inner2 // outer name: Outer // inner name: Inner2]\n" +
                        "to contain exactly in any order:\n" +
                        "  [[1: public] Outer$Inner2 // outer name: Outer // inner name: Inner2,\n" +
                        "    [1024: abstract] Outer$Inner3 // outer name: Outer // inner name: Inner3]\n" +
                        "elements not found:\n" +
                        "  [[1024: abstract] Outer$Inner3 // outer name: Outer // inner name: Inner3]\n" +
                        "and elements not expected:\n" +
                        "  [[8] Outer$Inner1 // outer name: Outer // inner name: Inner1]\n" +
                        "when comparing values using InnerClassNodeComparator");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
