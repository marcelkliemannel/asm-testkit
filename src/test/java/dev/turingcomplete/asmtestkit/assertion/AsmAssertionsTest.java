package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.__helper.DummyAttribute;
import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA;
import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationB;
import dev.turingcomplete.asmtestkit.compile.CompilationResult;
import org.assertj.core.api.Assertions;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.TypeReference;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.io.IOException;
import java.util.List;

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
    AsmAssertions.assertThatAnnotationNodes(List.of(new AnnotationNode("LA;"), new AnnotationNode("LA;")))
                 .containsExactlyInAnyOrderElementsOf(List.of(new AnnotationNode("LA;"), new AnnotationNode("LA;")));

    // Test representation
    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThatAnnotationNodes(List.of(new AnnotationNode("LA;"), new AnnotationNode("LB;")))
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
    String myClass = "import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA;" +
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
    AsmAssertions.assertThatTypeAnnotationNodes(List.of(firstTypeAnnotationNode, copyOfFirstTypeAnnotationNode))
                 .containsExactlyInAnyOrderElementsOf(List.of(firstTypeAnnotationNode, copyOfFirstTypeAnnotationNode));

    // Test representation
    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThatTypeAnnotationNodes(List.of(firstTypeAnnotationNode, secondTypeAnnotationNode))
                                                     .containsExactlyInAnyOrderElementsOf(List.of(secondTypeAnnotationNode, thirdTypeAnnotationNode)))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Type Annotations] \n" +
                          "Expecting actual:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA // reference: class_type_parameter=0; path: null,\n" +
                          "    @dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA // reference: class_type_parameter=1; path: null]\n" +
                          "to contain exactly in any order:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA // reference: class_type_parameter=1; path: null,\n" +
                          "    @dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA // reference: class_type_parameter=2; path: null]\n" +
                          "elements not found:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA // reference: class_type_parameter=2; path: null]\n" +
                          "and elements not expected:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA // reference: class_type_parameter=0; path: null]\n" +
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
    String myClass = "import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA;" +
                     "import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationB;" +
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
                        "  NEW java/lang/IllegalArgumentException (Opcode: 187)\n" +
                        "  DUP (Opcode: 89)\n" +
                        "  INVOKESPECIAL java/lang/IllegalArgumentException.<init> ()V (Opcode: 183)\n" +
                        "  ATHROW (Opcode: 191)\n" +
                        "L1\n" +
                        " but was: L0\n" +
                        "  LINENUMBER 1 L0\n" +
                        "  GETSTATIC java/lang/System.out : Ljava/io/PrintStream; (Opcode: 178)\n" +
                        "  ICONST_1 (Opcode: 4)\n" +
                        "  INVOKEVIRTUAL java/io/PrintStream.println (I)V (Opcode: 182)\n" +
                        "  RETURN (Opcode: 177)\n" +
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
                        "  GETSTATIC java/lang/System.out : Ljava/io/PrintStream; (Opcode: 178)\n" +
                        "  ICONST_2 (Opcode: 5)\n" +
                        "  INVOKEVIRTUAL java/io/PrintStream.println (I)V (Opcode: 182)\n" +
                        "  RETURN (Opcode: 177)\n" +
                        "L1\n" +
                        " but was: L0\n" +
                        "  LINENUMBER 1 L0\n" +
                        "  GETSTATIC java/lang/System.out : Ljava/io/PrintStream; (Opcode: 178)\n" +
                        "  ICONST_1 (Opcode: 4)\n" +
                        "  INVOKEVIRTUAL java/io/PrintStream.println (I)V (Opcode: 182)\n" +
                        "  RETURN (Opcode: 177)\n" +
                        "L1\n" +
                        "when comparing values using InsnListComparator");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
