package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils;
import dev.turingcomplete.asmtestkit.asmutils.ClassNameUtils;
import dev.turingcomplete.asmtestkit.asmutils.MethodNodeUtils;
import dev.turingcomplete.asmtestkit.assertion.__helper.DummyAttribute;
import dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleAnnotationA;
import dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleAnnotationB;
import dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleTypeParameterAnnotationA;
import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleAnnotationA;
import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleAnnotationB;
import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA;
import dev.turingcomplete.asmtestkit.assertion.option.StandardAssertOption;
import org.assertj.core.api.Assertions;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThat;
import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;

class MethodNodeAssertTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testIsEqualTo() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.assertion.__helper.*;" +

                     "class MyClass<S> {" +
                     "  @InvisibleAnnotationB" +
                     "  @VisibleAnnotationA" +
                     "  <T> T myMethod(String bar, @VisibleAnnotationA Integer foo) {\n" +
                     "    try {" +
                     "      @VisibleTypeParameterAnnotationA Integer first = 6 + foo;\n" +
                     "      @InvisibleTypeParameterAnnotationA Integer second = 6 + foo;\n" +
                     "      return null;\n" +
                     "    }" +
                     "    catch (@InvisibleTypeParameterAnnotationA IllegalArgumentException e) {" +
                     "      throw new IllegalStateException(e);" +
                     "    }" +
                     "  }" +
                     "}";

    MethodNode firstMethod = create()
            .addToClasspath(VisibleTypeParameterAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods
            .get(0);

    MethodNode secondMethod = create()
            .addToClasspath(VisibleTypeParameterAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods
            .get(0);

    assertThat(firstMethod)
            .isEqualTo(secondMethod);
  }

  @Test
  void testIsEqualToName() {
    var firstMethodNode = new MethodNode(0, "first", "()I", null, null);
    var secondMethodNode = new MethodNode(0, "second", "()I", null, null);

    assertThat(firstMethodNode)
            .isEqualTo(MethodNodeUtils.copy(firstMethodNode));

    assertThat(firstMethodNode)
            .addOption(StandardAssertOption.IGNORE_NAME)
            .isEqualTo(secondMethodNode);

    Assertions.assertThatThrownBy(() -> assertThat(firstMethodNode)
                      .isEqualTo(secondMethodNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Method: first()I > Has equal method name] \n" +
                          "expected: \"second\"\n" +
                          " but was: \"first\"");
  }

  @Test
  void testIsEqualToDescriptor() {
    var first1MethodNode = new MethodNode(0, "first", "()I", null, null);
    var first2MethodNode = new MethodNode(0, "first", "()C", null, null);

    assertThat(first1MethodNode)
            .isEqualTo(MethodNodeUtils.copy(first1MethodNode));

    assertThat(first1MethodNode)
            .addOption(StandardAssertOption.IGNORE_DESCRIPTOR)
            .isEqualTo(first2MethodNode);

    Assertions.assertThatThrownBy(() -> assertThat(first1MethodNode)
                      .isEqualTo(first2MethodNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Method: first()I > Has equal method descriptor] \n" +
                          "expected: ()C\n" +
                          " but was: ()I\n" +
                          "when comparing values using TypeComparator");
  }

  @Test
  void testIsEqualToAccess() {
    var first1MethodNode = new MethodNode(Opcodes.ACC_PROTECTED, "first", "()I", null, null);
    var first2MethodNode = new MethodNode(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL, "first", "()I", null, null);

    assertThat(first1MethodNode)
            .isEqualTo(MethodNodeUtils.copy(first1MethodNode));

    assertThat(first2MethodNode)
            .addOption(StandardAssertOption.IGNORE_ACCESS)
            .isEqualTo(first2MethodNode);

    Assertions.assertThatThrownBy(() -> assertThat(first1MethodNode)
                      .isEqualTo(first2MethodNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Method: first()I > Has equal method access > Has equal access values] \n" +
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
  void testIsEqualToSignature() {
    var first1MethodNode = new MethodNode(0, "first", "()Ljava.lang.Object;", "[[TT;", null);
    var first2MethodNode = new MethodNode(0, "first", "()Ljava.lang.Object;", "[T;", null);

    assertThat(first1MethodNode)
            .isEqualTo(MethodNodeUtils.copy(first1MethodNode));

    assertThat(first1MethodNode)
            .addOption(StandardAssertOption.IGNORE_SIGNATURE)
            .isEqualTo(first2MethodNode);

    Assertions.assertThatThrownBy(() -> assertThat(first1MethodNode)
                      .isEqualTo(first2MethodNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Method: first()Ljava.lang.Object; > Has equal method signature] \n" +
                          "expected: \"[T;\"\n" +
                          " but was: \"[[TT;\"");
  }

  @Test
  void testIsEqualToExceptions() {
    var first1MethodNode = new MethodNode(0, "first", "()Ljava.lang.String;", null, new String[]{ClassNameUtils.toInternalName(IOException.class)});
    var first2MethodNode = new MethodNode(0, "first", "()Ljava.lang.String;", null, null);

    assertThat(first1MethodNode)
            .isEqualTo(MethodNodeUtils.copy(first1MethodNode));

    assertThat(first1MethodNode)
            .addOption(StandardAssertOption.IGNORE_EXCEPTIONS)
            .isEqualTo(first2MethodNode);

    Assertions.assertThatThrownBy(() -> assertThat(first1MethodNode)
                      .isEqualTo(first2MethodNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Method: first()Ljava.lang.String; > Has equal exceptions] \n" +
                          "expected: []\n" +
                          " but was: [\"java/io/IOException\"]");
  }

  @Test
  void testIsEqualToVisibleAnnotations() {
    var first1MethodNode = new MethodNode(0, "first", "()Ljava.lang.String;", null, null);
    first1MethodNode.visibleAnnotations = List.of(AnnotationNodeUtils.createAnnotationNode(VisibleAnnotationA.class));

    var first2MethodNode = new MethodNode(0, "first", "()Ljava.lang.String;", null, null);
    first2MethodNode.visibleAnnotations = List.of(AnnotationNodeUtils.createAnnotationNode(VisibleAnnotationA.class),
                                                  AnnotationNodeUtils.createAnnotationNode(VisibleAnnotationB.class));

    assertThat(first1MethodNode)
            .isEqualTo(MethodNodeUtils.copy(first1MethodNode));

    assertThat(first1MethodNode)
            .addOption(StandardAssertOption.IGNORE_VISIBLE_ANNOTATIONS)
            .isEqualTo(first2MethodNode);

    Assertions.assertThatThrownBy(() -> assertThat(first1MethodNode)
                      .isEqualTo(first2MethodNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Method: first()Ljava.lang.String; > Has equal method visible annotations] \n" +
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
  void testIsEqualToInvisibleAnnotations() {
    var first1MethodNode = new MethodNode(0, "first", "()Ljava.lang.String;", null, null);
    first1MethodNode.invisibleAnnotations = List.of(AnnotationNodeUtils.createAnnotationNode(InvisibleAnnotationA.class));

    var first2MethodNode = new MethodNode(0, "first", "()Ljava.lang.String;", null, null);
    first2MethodNode.invisibleAnnotations = List.of(AnnotationNodeUtils.createAnnotationNode(InvisibleAnnotationA.class),
                                                    AnnotationNodeUtils.createAnnotationNode(InvisibleAnnotationB.class));

    assertThat(first1MethodNode)
            .isEqualTo(MethodNodeUtils.copy(first1MethodNode));

    assertThat(first1MethodNode)
            .addOption(StandardAssertOption.IGNORE_INVISIBLE_ANNOTATIONS)
            .isEqualTo(first2MethodNode);

    Assertions.assertThatThrownBy(() -> assertThat(first1MethodNode)
                      .isEqualTo(first2MethodNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Method: first()Ljava.lang.String; > Has equal method invisible annotations] \n" +
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
  void testIsEqualToVisibleTypeAnnotations() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA;" +
                     "class MyClass<T> {" +
                     "  T[]@VisibleTypeParameterAnnotationA[] myMethod1() { return null; }" +
                     "  T@VisibleTypeParameterAnnotationA[][] myMethod2() { return null; }" +
                     "}";

    List<MethodNode> methods = create()
            .addToClasspath(VisibleTypeParameterAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods;

    TypeAnnotationNode firstTypeAnnotation = methods.get(1).visibleTypeAnnotations.get(0);
    TypeAnnotationNode secondTypeAnnotation = methods.get(2).visibleTypeAnnotations.get(0);

    var first1MethodNode = new MethodNode(0, "first", "()Ljava.lang.String;", null, null);
    first1MethodNode.visibleTypeAnnotations = List.of(firstTypeAnnotation);

    MethodNode first2MethodNode = new MethodNode(0, "first", "()Ljava.lang.String;", null, null);
    first2MethodNode.visibleTypeAnnotations = List.of(firstTypeAnnotation, secondTypeAnnotation);

    assertThat(first1MethodNode)
            .isEqualTo(MethodNodeUtils.copy(first1MethodNode));

    assertThat(first1MethodNode)
            .addOption(StandardAssertOption.IGNORE_VISIBLE_TYPE_ANNOTATIONS)
            .isEqualTo(first2MethodNode);

    Assertions.assertThatThrownBy(() -> assertThat(first1MethodNode)
                      .isEqualTo(first2MethodNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Method: first()Ljava.lang.String; > Has equal method visible type annotations] \n" +
                          "Expecting actual:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA // reference: method_return; path: []\n" +
                          "to contain exactly in any order:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA // reference: method_return; path: [,\n" +
                          "    @dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA // reference: method_return; path: null]\n" +
                          "but could not find the following elements:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA // reference: method_return; path: null]\n" +
                          "when comparing values using TypeAnnotationNodeComparator");
  }

  @Test
  void testIsEqualToInvisibleTypeAnnotations() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleTypeParameterAnnotationA;" +
                     "class MyClass<T> {" +
                     "  T[]@InvisibleTypeParameterAnnotationA[] myMethod1() { return null; }" +
                     "  T@InvisibleTypeParameterAnnotationA[][] myMethod2() { return null; }" +
                     "}";

    List<MethodNode> methods = create()
            .addToClasspath(InvisibleTypeParameterAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods;

    TypeAnnotationNode firstTypeAnnotation = Objects.requireNonNull(methods.get(1).invisibleTypeAnnotations.get(0));
    TypeAnnotationNode secondTypeAnnotation = Objects.requireNonNull(methods.get(2).invisibleTypeAnnotations.get(0));

    var first1MethodNode = new MethodNode(0, "first", "()Ljava.lang.String;", null, null);
    first1MethodNode.invisibleTypeAnnotations = List.of(firstTypeAnnotation);

    MethodNode first2MethodNode = new MethodNode(0, "first", "()Ljava.lang.String;", null, null);
    first2MethodNode.invisibleTypeAnnotations = List.of(firstTypeAnnotation, secondTypeAnnotation);

    assertThat(first1MethodNode)
            .isEqualTo(MethodNodeUtils.copy(first1MethodNode));

    assertThat(first1MethodNode)
            .addOption(StandardAssertOption.IGNORE_INVISIBLE_TYPE_ANNOTATIONS)
            .isEqualTo(first2MethodNode);

    Assertions.assertThatThrownBy(() -> assertThat(first1MethodNode)
                      .isEqualTo(first2MethodNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Method: first()Ljava.lang.String; > Has equal method invisible type annotations] \n" +
                          "Expecting actual:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleTypeParameterAnnotationA // reference: method_return; path: []\n" +
                          "to contain exactly in any order:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleTypeParameterAnnotationA // reference: method_return; path: [,\n" +
                          "    @dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleTypeParameterAnnotationA // reference: method_return; path: null]\n" +
                          "but could not find the following elements:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleTypeParameterAnnotationA // reference: method_return; path: null]\n" +
                          "when comparing values using TypeAnnotationNodeComparator");
  }

  @Test
  void testIsEqualToAttributes() {
    var first1MethodNode = new MethodNode(0, "first", "()Ljava.lang.String;", null, null);
    first1MethodNode.attrs = List.of(new DummyAttribute("Foo"));
    var first2MethodNode = new MethodNode(0, "first", "()Ljava.lang.String;", null, null);
    first2MethodNode.attrs = List.of(new DummyAttribute("Foo"), new DummyAttribute("Bar"));

    assertThat(first1MethodNode)
            .isEqualTo(MethodNodeUtils.copy(first1MethodNode));

    assertThat(first1MethodNode)
            .addOption(StandardAssertOption.IGNORE_ATTRIBUTES)
            .isEqualTo(first2MethodNode);

    Assertions.assertThatThrownBy(() -> assertThat(first1MethodNode)
                      .isEqualTo(first2MethodNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Method: first()Ljava.lang.String; > Has equal method attributes] \n" +
                          "Expecting actual:\n" +
                          "  [Foonull]\n" +
                          "to contain exactly in any order:\n" +
                          "  [Foonull, Barnull]\n" +
                          "but could not find the following elements:\n" +
                          "  [Barnull]\n" +
                          "when comparing values using AttributeComparator");
  }

  @Test
  void testIsEqualToTryCatchBlocks() throws IOException {
    @Language("Java")
    String myClass = "" +
                     "class MyClass {" +
                     "  String myMethod1() { " +
                     "    try {" +
                     "      return null; " +
                     "    } catch (Exception e) { throw new IllegalStateException(e); }"+
                     "  }" +
                     "  String myMethod2() { " +
                     "    try {" +
                     "      return null; " +
                     "    } catch (RuntimeException e) { throw new IllegalStateException(e); }"+
                     "  }" +
                     "}";

    List<MethodNode> methods = create()
            .addToClasspath(InvisibleTypeParameterAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods;

    var first1MethodNode = new MethodNode(0, "first", "()Ljava.lang.String;", null, null);
    first1MethodNode.tryCatchBlocks = List.of(Objects.requireNonNull(methods.get(1).tryCatchBlocks.get(0)));
    first1MethodNode.instructions = Objects.requireNonNull(methods.get(1).instructions);
    var first2MethodNode = new MethodNode(0, "first", "()Ljava.lang.String;", null, null);
    first2MethodNode.tryCatchBlocks = List.of(Objects.requireNonNull(methods.get(2).tryCatchBlocks.get(0)));
    first2MethodNode.instructions = Objects.requireNonNull(methods.get(2).instructions);

    assertThat(first1MethodNode)
            .isEqualTo(MethodNodeUtils.copy(first1MethodNode));

    assertThat(first1MethodNode)
            .addOption(StandardAssertOption.IGNORE_TRY_CATCH_BLOCKS)
            .addOption(StandardAssertOption.IGNORE_INSTRUCTIONS) // Instructions are required for the label index lookup
            .isEqualTo(first2MethodNode);

    Assertions.assertThatThrownBy(() -> assertThat(first1MethodNode)
                      .addOption(StandardAssertOption.IGNORE_INSTRUCTIONS) // Instructions are required for the label index lookup
                      .isEqualTo(first2MethodNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Method: first()Ljava.lang.String; > Has equal try-catch blocks] \n" +
                          "Expecting actual:\n" +
                          "  [java.lang.Exception // range: L0-L1; handled in: L2]\n" +
                          "to contain exactly in any order:\n" +
                          "  [java.lang.RuntimeException // range: L0-L1; handled in: L2]\n" +
                          "elements not found:\n" +
                          "  [java.lang.RuntimeException // range: L0-L1; handled in: L2]\n" +
                          "and elements not expected:\n" +
                          "  [java.lang.Exception // range: L0-L1; handled in: L2]\n" +
                          "when comparing values using TryCatchBlockNodeComparator");
  }

  @Test
  void testIsEqualToMaxStack() {
    var first1MethodNode = new MethodNode(0, "first", "()Ljava.lang.String;", null, null);
    first1MethodNode.maxStack = 2;
    var first1MethodNodeCopy = MethodNodeUtils.copy(first1MethodNode);
    first1MethodNodeCopy.maxStack = 2;

    var first2MethodNode = new MethodNode(0, "first", "()Ljava.lang.String;", null, null);
    first2MethodNode.maxStack = 5;

    assertThat(first1MethodNode)
            .isEqualTo(first1MethodNodeCopy);

    assertThat(first1MethodNode)
            .addOption(StandardAssertOption.IGNORE_MAX_STACK)
            .isEqualTo(first2MethodNode);

    Assertions.assertThatThrownBy(() -> assertThat(first1MethodNode)
                      .isEqualTo(first2MethodNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Method: first()Ljava.lang.String; > Has equal max stack] \n" +
                          "expected: 5\n" +
                          " but was: 2");
  }

  @Test
  void testIsEqualToMaxLocals() {
    var first1MethodNode = new MethodNode(0, "first", "()Ljava.lang.String;", null, null);
    first1MethodNode.maxLocals = 2;
    var first1MethodNodeCopy = MethodNodeUtils.copy(first1MethodNode);
    first1MethodNodeCopy.maxLocals = 2;

    var first2MethodNode = new MethodNode(0, "first", "()Ljava.lang.String;", null, null);
    first2MethodNode.maxLocals = 5;

    assertThat(first1MethodNode)
            .isEqualTo(first1MethodNodeCopy);

    assertThat(first1MethodNode)
            .addOption(StandardAssertOption.IGNORE_MAX_LOCALS)
            .isEqualTo(first2MethodNode);

    Assertions.assertThatThrownBy(() -> assertThat(first1MethodNode)
                      .isEqualTo(first2MethodNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Method: first()Ljava.lang.String; > Has equal max locals] \n" +
                          "expected: 5\n" +
                          " but was: 2");
  }

  @Test
  void testIsEqualToLocalVariables() throws IOException {
    @Language("Java")
    String myClass = "" +
                     "class MyClass {" +
                     "  void myMethod1(String foo) { " +
                     "    System.out.println(foo);" +
                     "  }" +
                     "  void myMethod2(int foo) { " +
                     "    System.out.println(foo);" +
                     "  }" +
                     "}";

    List<MethodNode> methods = create()
            .addToClasspath(InvisibleTypeParameterAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods;

    var first1MethodNode = new MethodNode(0, "first", "()Ljava.lang.String;", null, null);
    first1MethodNode.localVariables = Objects.requireNonNull(methods.get(1).localVariables);
    first1MethodNode.instructions = Objects.requireNonNull(methods.get(1).instructions);
    var first2MethodNode = new MethodNode(0, "first", "()Ljava.lang.String;", null, null);
    first2MethodNode.localVariables = Objects.requireNonNull(methods.get(2).localVariables);
    first2MethodNode.instructions = Objects.requireNonNull(methods.get(2).instructions);

    assertThat(first1MethodNode)
            .addOption(StandardAssertOption.IGNORE_INSTRUCTIONS)
            .isEqualTo(MethodNodeUtils.copy(first1MethodNode));

    assertThat(first1MethodNode)
            .addOption(StandardAssertOption.IGNORE_LOCAL_VARIABLES)
            .addOption(StandardAssertOption.IGNORE_INSTRUCTIONS)
            .isEqualTo(first2MethodNode);

    Assertions.assertThatThrownBy(() -> assertThat(first1MethodNode)
                      .addOption(StandardAssertOption.IGNORE_INSTRUCTIONS)
                      .isEqualTo(first2MethodNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Method: first()Ljava.lang.String; > Has equal local variables] \n" +
                          "Expecting actual:\n" +
                          "  [#0 MyClass this // range: L0-L1, #1 java.lang.String foo // range: L0-L1]\n" +
                          "to contain exactly in any order:\n" +
                          "  [#0 MyClass this // range: L0-L1, #1 int foo // range: L0-L1]\n" +
                          "elements not found:\n" +
                          "  [#1 int foo // range: L0-L1]\n" +
                          "and elements not expected:\n" +
                          "  [#1 java.lang.String foo // range: L0-L1]\n" +
                          "when comparing values using LocalVariableNodeComparator");
  }

  @Test
  void testIsEqualVisibleLocalVariableAnnotations() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.assertion.__helper.*;" +
                     "class MyClass {" +
                     "  void myMethod1(String foo) { " +
                     "    @VisibleTypeParameterAnnotationA String bar = foo + \"a\";" +
                     "    System.out.println(bar);" +
                     "  }" +
                     "  void myMethod2(String foo) { " +
                     "    @VisibleTypeParameterAnnotationB String bar = foo + \"a\";" +
                     "    System.out.println(bar);" +
                     "  }" +
                     "}";

    List<MethodNode> methods = create()
            .addToClasspath(VisibleTypeParameterAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods;

    var first1MethodNode = new MethodNode(0, "first", "()Ljava.lang.String;", null, null);
    first1MethodNode.visibleLocalVariableAnnotations = Objects.requireNonNull(methods.get(1).visibleLocalVariableAnnotations);
    first1MethodNode.instructions = Objects.requireNonNull(methods.get(1).instructions);

    var first2MethodNode = new MethodNode(0, "first", "()Ljava.lang.String;", null, null);
    first2MethodNode.visibleLocalVariableAnnotations = Objects.requireNonNull(methods.get(2).visibleLocalVariableAnnotations);
    first2MethodNode.instructions = Objects.requireNonNull(methods.get(2).instructions);

    assertThat(first1MethodNode)
            .addOption(StandardAssertOption.IGNORE_INSTRUCTIONS)
            .isEqualTo(MethodNodeUtils.copy(first1MethodNode));

    assertThat(first1MethodNode)
            .addOption(StandardAssertOption.IGNORE_VISISBLE_LOCAL_VARIABLE_ANNOTATIONS)
            .addOption(StandardAssertOption.IGNORE_INSTRUCTIONS)
            .isEqualTo(first2MethodNode);

    Assertions.assertThatThrownBy(() -> assertThat(first1MethodNode)
                      .addOption(StandardAssertOption.IGNORE_INSTRUCTIONS)
                      .isEqualTo(first2MethodNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Method: first()Ljava.lang.String; > Has equal visible local variable annotations] \n" +
                          "Expecting actual:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA // reference: local_variable; path: null // range: #2 L1-L2]\n" +
                          "to contain exactly in any order:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationB // reference: local_variable; path: null // range: #2 L1-L2]\n" +
                          "elements not found:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationB // reference: local_variable; path: null // range: #2 L1-L2]\n" +
                          "and elements not expected:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA // reference: local_variable; path: null // range: #2 L1-L2]\n" +
                          "when comparing values using LocalVariableAnnotationNodeComparator");
  }

  @Test
  void testIsEqualInvisibleLocalVariableAnnotations() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.assertion.__helper.*;" +
                     "class MyClass {" +
                     "  void myMethod1(String foo) { " +
                     "    @InvisibleTypeParameterAnnotationA String bar = foo + \"a\";" +
                     "    System.out.println(bar);" +
                     "  }" +
                     "  void myMethod2(String foo) { " +
                     "    @InvisibleTypeParameterAnnotationB String bar = foo + \"a\";" +
                     "    System.out.println(bar);" +
                     "  }" +
                     "}";

    List<MethodNode> methods = create()
            .addToClasspath(InvisibleTypeParameterAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods;

    var first1MethodNode = new MethodNode(0, "first", "()Ljava.lang.String;", null, null);
    first1MethodNode.invisibleLocalVariableAnnotations = Objects.requireNonNull(methods.get(1).invisibleLocalVariableAnnotations);
    first1MethodNode.instructions = Objects.requireNonNull(methods.get(1).instructions);

    var first2MethodNode = new MethodNode(0, "first", "()Ljava.lang.String;", null, null);
    first2MethodNode.invisibleLocalVariableAnnotations = Objects.requireNonNull(methods.get(2).invisibleLocalVariableAnnotations);
    first2MethodNode.instructions = Objects.requireNonNull(methods.get(2).instructions);

    assertThat(first1MethodNode)
            .addOption(StandardAssertOption.IGNORE_INSTRUCTIONS)
            .isEqualTo(MethodNodeUtils.copy(first1MethodNode));

    assertThat(first1MethodNode)
            .addOption(StandardAssertOption.IGNORE_INVISISBLE_LOCAL_VARIABLE_ANNOTATIONS)
            .addOption(StandardAssertOption.IGNORE_INSTRUCTIONS)
            .isEqualTo(first2MethodNode);

    Assertions.assertThatThrownBy(() -> assertThat(first1MethodNode)
                      .addOption(StandardAssertOption.IGNORE_INSTRUCTIONS)
                      .isEqualTo(first2MethodNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Method: first()Ljava.lang.String; > Has equal invisible local variable annotations] \n" +
                          "Expecting actual:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleTypeParameterAnnotationA // reference: local_variable; path: null // range: #2 L1-L2]\n" +
                          "to contain exactly in any order:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleTypeParameterAnnotationB // reference: local_variable; path: null // range: #2 L1-L2]\n" +
                          "elements not found:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleTypeParameterAnnotationB // reference: local_variable; path: null // range: #2 L1-L2]\n" +
                          "and elements not expected:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleTypeParameterAnnotationA // reference: local_variable; path: null // range: #2 L1-L2]\n" +
                          "when comparing values using LocalVariableAnnotationNodeComparator");
  }

  @Test
  void testIsEqualAnnotationDefault() {
    var first1MethodNode = new MethodNode(0, "first", "()Ljava.lang.String;", null, null);
    first1MethodNode.annotationDefault = null;

    var first2MethodNode = new MethodNode(0, "first", "()Ljava.lang.String;", null, null);
    first2MethodNode.annotationDefault = 888;

    assertThat(first1MethodNode)
            .isEqualTo(MethodNodeUtils.copy(first1MethodNode));

    assertThat(first1MethodNode)
            .addOption(StandardAssertOption.IGNORE_ANNOTATION_DEFAULT)
            .isEqualTo(first2MethodNode);

    Assertions.assertThatThrownBy(() -> assertThat(first1MethodNode)
                      .isEqualTo(first2MethodNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Method: first()Ljava.lang.String; > Has equal annotation default] \n" +
                          "expected: 888\n" +
                          " but was: null\n" +
                          "when comparing values using AnnotationDefaultValueComparator");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
