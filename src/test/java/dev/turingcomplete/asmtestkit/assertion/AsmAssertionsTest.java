package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.__helper.DummyAttribute;
import org.assertj.core.api.Assertions;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.TypeReference;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.io.IOException;
import java.util.List;

import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;

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
              .hasMessage("\nExpecting actual:\n" +
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
              .hasMessage("\nExpecting actual:\n" +
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
    String typeParameterAnnotation = "import java.lang.annotation.*;" +
                                     "@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})\n" +
                                     "@Retention(RetentionPolicy.RUNTIME)\n" +
                                     "@interface TypeParameterAnnotation { }";
    @Language("Java")
    String myClass = "class MyClass<@TypeParameterAnnotation S, @TypeParameterAnnotation T, @TypeParameterAnnotation U> { }";

    List<TypeAnnotationNode> visibleTypeAnnotations = create()
            .addJavaInputSource(typeParameterAnnotation)
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
              .hasMessage("\nExpecting actual:\n" +
                          "  [@TypeParameterAnnotation {reference: class_type_parameter=0; path: null},\n" +
                          "    @TypeParameterAnnotation {reference: class_type_parameter=1; path: null}]\n" +
                          "to contain exactly in any order:\n" +
                          "  [@TypeParameterAnnotation {reference: class_type_parameter=1; path: null},\n" +
                          "    @TypeParameterAnnotation {reference: class_type_parameter=2; path: null}]\n" +
                          "elements not found:\n" +
                          "  [@TypeParameterAnnotation {reference: class_type_parameter=2; path: null}]\n" +
                          "and elements not expected:\n" +
                          "  [@TypeParameterAnnotation {reference: class_type_parameter=0; path: null}]\n" +
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
              .hasMessage("\nExpecting actual:\n" +
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
              .hasMessage("\nExpecting actual:\n" +
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
    String typeParameterAnnotationA = "import java.lang.annotation.*;" +
                                      "@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})\n" +
                                      "@Retention(RetentionPolicy.RUNTIME)\n" +
                                      "@interface TypeParameterAnnotationA { }";
    @Language("Java")
    String typeParameterAnnotationB = "import java.lang.annotation.*;" +
                                      "@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})\n" +
                                      "@Retention(RetentionPolicy.RUNTIME)\n" +
                                      "@interface TypeParameterAnnotationB { }";
    @Language("Java")
    String myClass = "class MyClass<@TypeParameterAnnotationA @TypeParameterAnnotationB S, @TypeParameterAnnotationA T> { }";

    List<TypeAnnotationNode> visibleTypeAnnotations = create()
            .addJavaInputSource(typeParameterAnnotationA)
            .addJavaInputSource(typeParameterAnnotationB)
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
              .hasMessage("\nExpecting actual:\n" +
                          "  [class_type_parameter=0, class_type_parameter=0]\n" +
                          "to contain exactly in any order:\n" +
                          "  [class_type_parameter=0, class_type_parameter=1]\n" +
                          "elements not found:\n" +
                          "  [class_type_parameter=1]\n" +
                          "and elements not expected:\n" +
                          "  [class_type_parameter=0]\n" +
                          "when comparing values using TypeReferenceComparator");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
