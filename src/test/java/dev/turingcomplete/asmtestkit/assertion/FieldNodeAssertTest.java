package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils;
import dev.turingcomplete.asmtestkit.assertion.__helper.DummyAttribute;
import dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleAnnotationA;
import dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleAnnotationB;
import dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleTypeParameterAnnotation;
import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleAnnotationA;
import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleAnnotationB;
import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA;
import dev.turingcomplete.asmtestkit.assertion.option.StandardAssertOption;
import org.assertj.core.api.Assertions;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.io.IOException;
import java.util.List;

import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;

class FieldNodeAssertTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testIsEqual() {

  }

  @Test
  void testIsEqualName() {
    FieldNode firstFieldNode = new FieldNode(0, "first", "I", null, null);
    var secondFieldNode = new FieldNode(0, "second", "I", null, null);

    AsmAssertions.assertThat(firstFieldNode)
                 .isEqualTo(firstFieldNode);

    AsmAssertions.assertThat(firstFieldNode)
                 .addOption(StandardAssertOption.IGNORE_NAME)
                 .isEqualTo(secondFieldNode);

    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThat(firstFieldNode)
                                                     .isEqualTo(secondFieldNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Field: first > Is equal field name] \n" +
                          "expected: \"second\"\n" +
                          " but was: \"first\"");
  }

  @Test
  void testIsEqualDescriptor() {
    var first1FieldNode = new FieldNode(0, "first", "I", null, null);
    var first2FieldNode = new FieldNode(0, "first", "C", null, null);

    AsmAssertions.assertThat(first1FieldNode)
                 .isEqualTo(first1FieldNode);

    AsmAssertions.assertThat(first1FieldNode)
                 .addOption(StandardAssertOption.IGNORE_DESCRIPTOR)
                 .isEqualTo(first2FieldNode);

    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThat(first1FieldNode)
                                                     .isEqualTo(first2FieldNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Field: first > Is equal field descriptor] \n" +
                          "expected: char\n" +
                          " but was: int\n" +
                          "when comparing values using TypeComparator");
  }

  @Test
  void testIsEqualAccess() {
    var first1FieldNode = new FieldNode(Opcodes.ACC_PROTECTED, "first", "I", null, null);
    FieldNode first2FieldNode = new FieldNode(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL, "first", "I", null, null);

    AsmAssertions.assertThat(first1FieldNode)
                 .isEqualTo(first1FieldNode);

    AsmAssertions.assertThat(first2FieldNode)
                 .addOption(StandardAssertOption.IGNORE_ACCESS)
                 .isEqualTo(first2FieldNode);

    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThat(first1FieldNode)
                                                     .isEqualTo(first2FieldNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Field: first > Is equal field access > Field: first > Is equal field access > Is equal access values] \n" +
                          "Expecting actual:\n" +
                          "  [\"protected\"]\n" +
                          "to contain exactly in any order:\n" +
                          "  [\"public\", \"final\"]\n" +
                          "elements not found:\n" +
                          "  [\"public\", \"final\"]\n" +
                          "and elements not expected:\n" +
                          "  [\"protected\"]\n");
  }

  @Test
  void testIsEqualSignature() {
    var first1FieldNode = new FieldNode(0, "first", "Ljava.lang.Object;", "[[TT;", null);
    var first2FieldNode = new FieldNode(0, "first", "Ljava.lang.Object;", "[T;", null);

    AsmAssertions.assertThat(first1FieldNode)
                 .isEqualTo(first1FieldNode);

    AsmAssertions.assertThat(first1FieldNode)
                 .addOption(StandardAssertOption.IGNORE_SIGNATURE)
                 .isEqualTo(first2FieldNode);

    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThat(first1FieldNode)
                                                     .isEqualTo(first2FieldNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Field: first > Is equal field signature] \n" +
                          "expected: \"[T;\"\n" +
                          " but was: \"[[TT;\"");
  }

  @Test
  void testIsEqualValue() {
    var first1FieldNode = new FieldNode(0, "first", "Ljava.lang.String;", null, "Foo");
    var first2FieldNode = new FieldNode(0, "first", "Ljava.lang.String;", null, "Bar");

    AsmAssertions.assertThat(first1FieldNode)
                 .isEqualTo(first1FieldNode);

    AsmAssertions.assertThat(first1FieldNode)
                 .addOption(StandardAssertOption.IGNORE_VALUE)
                 .isEqualTo(first2FieldNode);

    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThat(first1FieldNode)
                                                     .isEqualTo(first2FieldNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Field: first > Is equal field value] \n" +
                          "expected: \"Bar\"\n" +
                          " but was: \"Foo\"");
  }

  @Test
  void testIsEqualVisibleAnnotations() {
    var first1FieldNode = new FieldNode(0, "first", "Ljava.lang.String;", null, null);
    first1FieldNode.visibleAnnotations = List.of(AnnotationNodeUtils.createAnnotationNode(VisibleAnnotationA.class));
    
    var first2FieldNode = new FieldNode(0, "first", "Ljava.lang.String;", null, null);
    first2FieldNode.visibleAnnotations = List.of(AnnotationNodeUtils.createAnnotationNode(VisibleAnnotationA.class),
                                                 AnnotationNodeUtils.createAnnotationNode(VisibleAnnotationB.class));

    AsmAssertions.assertThat(first1FieldNode)
                 .isEqualTo(first1FieldNode);

    AsmAssertions.assertThat(first1FieldNode)
                 .addOption(StandardAssertOption.IGNORE_VISIBLE_ANNOTATIONS)
                 .isEqualTo(first2FieldNode);

    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThat(first1FieldNode)
                                                     .isEqualTo(first2FieldNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Field: first > Is equal field visible annotations] \n" +
                          "Expecting actual:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleAnnotationA]\n" +
                          "to contain exactly in any order:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleAnnotationA,\n" +
                          "    @dev.turingcomplete.asmtestkit.assertion.__helper.VisibleAnnotationB]\n" +
                          "but could not find the following elements:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleAnnotationB]\n" +
                          "when comparing values using AnnotationNodeComparator");
  }

  @Test
  void testIsEqualInvisibleAnnotations() {
    var first1FieldNode = new FieldNode(0, "first", "Ljava.lang.String;", null, null);
    first1FieldNode.invisibleAnnotations = List.of(AnnotationNodeUtils.createAnnotationNode(InvisibleAnnotationA.class));

    var first2FieldNode = new FieldNode(0, "first", "Ljava.lang.String;", null, null);
    first2FieldNode.invisibleAnnotations = List.of(AnnotationNodeUtils.createAnnotationNode(InvisibleAnnotationA.class),
                                                 AnnotationNodeUtils.createAnnotationNode(InvisibleAnnotationB.class));

    AsmAssertions.assertThat(first1FieldNode)
                 .isEqualTo(first1FieldNode);

    AsmAssertions.assertThat(first1FieldNode)
                 .addOption(StandardAssertOption.IGNORE_INVISIBLE_ANNOTATIONS)
                 .isEqualTo(first2FieldNode);

    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThat(first1FieldNode)
                                                     .isEqualTo(first2FieldNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Field: first > Is equal field invisible annotations] \n" +
                          "Expecting actual:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleAnnotationA]\n" +
                          "to contain exactly in any order:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleAnnotationA,\n" +
                          "    @dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleAnnotationB]\n" +
                          "but could not find the following elements:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleAnnotationB]\n" +
                          "when comparing values using AnnotationNodeComparator");
  }

  @Test
  void testIsEqualVisibleTypeAnnotations() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA;" +
                     "abstract class MyClass<T> extends @VisibleTypeParameterAnnotationA Thread {" +
                     "  T[]@VisibleTypeParameterAnnotationA[] myField1;" +
                     "  T@VisibleTypeParameterAnnotationA[][] myField2;" +
                     "}";

    List<FieldNode> fields = create()
            .addToClasspath(VisibleTypeParameterAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .fields;
    
    TypeAnnotationNode firstTypeAnnotation = fields.get(0).visibleTypeAnnotations.get(0);
    TypeAnnotationNode secondTypeAnnotation = fields.get(1).visibleTypeAnnotations.get(0);

    var first1FieldNode = new FieldNode(0, "first", "Ljava.lang.String;", null, null);
    first1FieldNode.visibleTypeAnnotations = List.of(firstTypeAnnotation);

    FieldNode first2FieldNode = new FieldNode(0, "first", "Ljava.lang.String;", null, null);
    first1FieldNode.visibleTypeAnnotations = List.of(firstTypeAnnotation, secondTypeAnnotation);

    AsmAssertions.assertThat(first1FieldNode)
                 .isEqualTo(first1FieldNode);

    AsmAssertions.assertThat(first1FieldNode)
                 .addOption(StandardAssertOption.IGNORE_VISIBLE_TYPE_ANNOTATIONS)
                 .isEqualTo(first2FieldNode);

    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThat(first1FieldNode)
                                                     .isEqualTo(first2FieldNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Field: first > Is equal field visible type annotations] \n" +
                          "Expecting actual:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA // reference: field; path: [,\n" +
                          "    @dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA // reference: field; path: null]\n" +
                          "to contain exactly in any order:\n" +
                          "  []\n" +
                          "but the following elements were unexpected:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA // reference: field; path: [,\n" +
                          "    @dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA // reference: field; path: null]\n" +
                          "when comparing values using TypeAnnotationNodeComparator");
  }

  @Test
  void testIsEqualInvisibleTypeAnnotations() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleTypeParameterAnnotation;" +
                     "abstract class MyClass<T> extends @InvisibleTypeParameterAnnotation Thread {" +
                     "  T[]@InvisibleTypeParameterAnnotation[] myField1;" +
                     "  T@InvisibleTypeParameterAnnotation[][] myField2;" +
                     "}";

    List<FieldNode> fields = create()
            .addToClasspath(InvisibleTypeParameterAnnotation.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .fields;

    TypeAnnotationNode firstTypeAnnotation = fields.get(0).invisibleTypeAnnotations.get(0);
    TypeAnnotationNode secondTypeAnnotation = fields.get(1).invisibleTypeAnnotations.get(0);

    var first1FieldNode = new FieldNode(0, "first", "Ljava.lang.String;", null, null);
    first1FieldNode.invisibleTypeAnnotations = List.of(firstTypeAnnotation);

    FieldNode first2FieldNode = new FieldNode(0, "first", "Ljava.lang.String;", null, null);
    first1FieldNode.invisibleTypeAnnotations = List.of(firstTypeAnnotation, secondTypeAnnotation);

    AsmAssertions.assertThat(first1FieldNode)
                 .isEqualTo(first1FieldNode);

    AsmAssertions.assertThat(first1FieldNode)
                 .addOption(StandardAssertOption.IGNORE_INVISIBLE_TYPE_ANNOTATIONS)
                 .isEqualTo(first2FieldNode);

    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThat(first1FieldNode)
                                                     .isEqualTo(first2FieldNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Field: first > Is equal field invisible type annotations] \n" +
                          "Expecting actual:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleTypeParameterAnnotation // reference: field; path: [,\n" +
                          "    @dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleTypeParameterAnnotation // reference: field; path: null]\n" +
                          "to contain exactly in any order:\n" +
                          "  []\n" +
                          "but the following elements were unexpected:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleTypeParameterAnnotation // reference: field; path: [,\n" +
                          "    @dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleTypeParameterAnnotation // reference: field; path: null]\n" +
                          "when comparing values using TypeAnnotationNodeComparator");
  }

  @Test
  void testIsEqualAttributes() {
    var first1FieldNode = new FieldNode(0, "first", "Ljava.lang.String;", null, null);
    first1FieldNode.attrs = List.of(new DummyAttribute("Foo"));
    var first2FieldNode = new FieldNode(0, "first", "Ljava.lang.String;", null, null);
    first2FieldNode.attrs = List.of(new DummyAttribute("Foo"), new DummyAttribute("Bar"));

    AsmAssertions.assertThat(first1FieldNode)
                 .isEqualTo(first1FieldNode);

    AsmAssertions.assertThat(first1FieldNode)
                 .addOption(StandardAssertOption.IGNORE_ATTRIBUTES)
                 .isEqualTo(first2FieldNode);

    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThat(first1FieldNode)
                                                     .isEqualTo(first2FieldNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Field: first > Is equal field attributes] \n" +
                          "Expecting actual:\n" +
                          "  [Foonull]\n" +
                          "to contain exactly in any order:\n" +
                          "  [Foonull, Barnull]\n" +
                          "but could not find the following elements:\n" +
                          "  [Barnull]\n" +
                          "when comparing values using AttributeComparator");
  }
  
  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
