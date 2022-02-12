package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.__helper.DummyAttribute;
import dev.turingcomplete.asmtestkit.__helper.InvisibleAnnotationA;
import dev.turingcomplete.asmtestkit.__helper.InvisibleAnnotationB;
import dev.turingcomplete.asmtestkit.__helper.VisibleAnnotationA;
import dev.turingcomplete.asmtestkit.__helper.VisibleAnnotationB;
import dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA;
import dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils;
import dev.turingcomplete.asmtestkit.asmutils.ClassNodeUtils;
import dev.turingcomplete.asmtestkit.assertion.option.StandardAssertOption;
import org.assertj.core.api.Assertions;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InnerClassNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.io.IOException;
import java.util.List;

import static dev.turingcomplete.asmtestkit.asmutils.ClassNameUtils.toInternalName;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThat;
import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;

public class ClassNodeAssertTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testIsEqualTo() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.__helper.*;" +
                     "@VisibleTypeParameterAnnotationA\n" +
                     "@VisibleAnnotationA\n" +
                     "class MyClass<T extends Number> {" +

                     "  public final int myField1 = 2;" +

                     "  public enum Foo { FOO, BAR } " +
                     "}";

    ClassNode firstClass = create()
            .addToClasspath(VisibleTypeParameterAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass");
    firstClass.attrs = List.of(new DummyAttribute("Foo"));

    ClassNode secondClass = create()
            .addToClasspath(VisibleTypeParameterAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass");
    secondClass.attrs = List.of(new DummyAttribute("Foo"));

    assertThat(firstClass)
            .isEqualTo(secondClass);
  }

  @Test
  void testIsEqualToVersion() {
    var firstClassNode = new ClassNode();
    firstClassNode.version = 12;
    var secondClassNode = new ClassNode();
    secondClassNode.version = 42;

    assertThat(firstClassNode)
            .isEqualTo(ClassNodeUtils.copy(firstClassNode));

    assertThat(firstClassNode)
            .addOption(StandardAssertOption.IGNORE_VERSION)
            .isEqualTo(secondClassNode);

    Assertions.assertThatThrownBy(() -> assertThat(firstClassNode)
                      .isEqualTo(secondClassNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Class: class null > Has equal version] \n" +
                          "expected: 42\n" +
                          " but was: 12");
  }

  @Test
  void testIsEqualToName() {
    var firstClassNode = new ClassNode();
    firstClassNode.name = "A";
    var secondClassNode = new ClassNode();
    secondClassNode.name = "B";

    assertThat(firstClassNode)
            .isEqualTo(ClassNodeUtils.copy(firstClassNode));

    assertThat(firstClassNode)
            .addOption(StandardAssertOption.IGNORE_NAME)
            .isEqualTo(secondClassNode);

    Assertions.assertThatThrownBy(() -> assertThat(firstClassNode)
                      .isEqualTo(secondClassNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Class: class A > Has equal class name] \n" +
                          "expected: \"B\"\n" +
                          " but was: \"A\"");
  }

  @Test
  void testIsEqualToAccess() {
    var firstClassNode = new ClassNode();
    firstClassNode.access = Opcodes.ACC_ABSTRACT + Opcodes.ACC_PUBLIC;
    var secondClassNode = new ClassNode();
    secondClassNode.access = Opcodes.ACC_FINAL;

    assertThat(firstClassNode)
            .isEqualTo(ClassNodeUtils.copy(firstClassNode));

    assertThat(firstClassNode)
            .addOption(StandardAssertOption.IGNORE_ACCESS)
            .isEqualTo(secondClassNode);

    Assertions.assertThatThrownBy(() -> assertThat(firstClassNode)
                      .isEqualTo(secondClassNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Class: class null > Has equal class access > Has equal access values] \n" +
                          "Expecting actual:\n" +
                          "  [\"public\", \"abstract\"]\n" +
                          "to contain exactly in any order:\n" +
                          "  [\"final\"]\n" +
                          "elements not found:\n" +
                          "  [\"final\"]\n" +
                          "and elements not expected:\n" +
                          "  [\"public\", \"abstract\"]\n");
  }

  @Test
  void testIsEqualToSignature() {
    var firstClassNode = new ClassNode();
    firstClassNode.signature = "[T;";
    var secondClassNode = new ClassNode();
    secondClassNode.signature = "[[TT;";

    assertThat(firstClassNode)
            .isEqualTo(ClassNodeUtils.copy(firstClassNode));

    assertThat(firstClassNode)
            .addOption(StandardAssertOption.IGNORE_SIGNATURE)
            .isEqualTo(secondClassNode);

    Assertions.assertThatThrownBy(() -> assertThat(firstClassNode)
                      .isEqualTo(secondClassNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Class: class null > Has equal class signature] \n" +
                          "expected: \"[[TT;\"\n" +
                          " but was: \"[T;\"");
  }


  @Test
  void testIsEqualToVisibleAnnotations() {
    var firstClassNode = new ClassNode();
    firstClassNode.visibleAnnotations = List.of(AnnotationNodeUtils.createAnnotationNode(VisibleAnnotationA.class));
    var secondClassNode = new ClassNode();
    secondClassNode.visibleAnnotations = List.of(AnnotationNodeUtils.createAnnotationNode(VisibleAnnotationA.class),
                                                 AnnotationNodeUtils.createAnnotationNode(VisibleAnnotationB.class));

    assertThat(firstClassNode)
            .isEqualTo(ClassNodeUtils.copy(firstClassNode));

    assertThat(firstClassNode)
            .addOption(StandardAssertOption.IGNORE_VISIBLE_ANNOTATIONS)
            .isEqualTo(secondClassNode);

    Assertions.assertThatThrownBy(() -> assertThat(firstClassNode)
                      .isEqualTo(secondClassNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Class: class null > Has equal class visible annotations] \n" +
                          "Expecting actual:\n" +
                          "  [@dev.turingcomplete.asmtestkit.__helper.VisibleAnnotationA]\n" +
                          "to contain exactly in any order:\n" +
                          "  [@dev.turingcomplete.asmtestkit.__helper.VisibleAnnotationA,\n" +
                          "    @dev.turingcomplete.asmtestkit.__helper.VisibleAnnotationB]\n" +
                          "but could not find the following elements:\n" +
                          "  [@dev.turingcomplete.asmtestkit.__helper.VisibleAnnotationB]\n" +
                          "when comparing values using AnnotationNodeComparator");
  }

  @Test
  void testIsEqualToInvisibleAnnotations() {
    var firstClassNode = new ClassNode();
    firstClassNode.invisibleAnnotations = List.of(AnnotationNodeUtils.createAnnotationNode(InvisibleAnnotationA.class));
    var secondClassNode = new ClassNode();
    secondClassNode.invisibleAnnotations = List.of(AnnotationNodeUtils.createAnnotationNode(InvisibleAnnotationA.class),
                                                   AnnotationNodeUtils.createAnnotationNode(InvisibleAnnotationB.class));

    assertThat(firstClassNode)
            .isEqualTo(ClassNodeUtils.copy(firstClassNode));

    assertThat(firstClassNode)
            .addOption(StandardAssertOption.IGNORE_INVISIBLE_ANNOTATIONS)
            .isEqualTo(secondClassNode);

    Assertions.assertThatThrownBy(() -> assertThat(firstClassNode)
                      .isEqualTo(secondClassNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Class: class null > Has equal class invisible annotations] \n" +
                          "Expecting actual:\n" +
                          "  [@dev.turingcomplete.asmtestkit.__helper.InvisibleAnnotationA]\n" +
                          "to contain exactly in any order:\n" +
                          "  [@dev.turingcomplete.asmtestkit.__helper.InvisibleAnnotationA,\n" +
                          "    @dev.turingcomplete.asmtestkit.__helper.InvisibleAnnotationB]\n" +
                          "but could not find the following elements:\n" +
                          "  [@dev.turingcomplete.asmtestkit.__helper.InvisibleAnnotationB]\n" +
                          "when comparing values using AnnotationNodeComparator");
  }

  @Test
  void testIsEqualToVisibleTypeAnnotations() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.__helper.*;" +
                     "class MyClass<@VisibleTypeParameterAnnotationA T, @VisibleTypeParameterAnnotationB S> {" +
                     "}";

    ClassNode classNode = create()
            .addToClasspath(VisibleTypeParameterAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass");

    TypeAnnotationNode firstTypeAnnotation = classNode.visibleTypeAnnotations.get(0);
    TypeAnnotationNode secondTypeAnnotation = classNode.visibleTypeAnnotations.get(1);

    var firstClassNode = new ClassNode();
    firstClassNode.visibleTypeAnnotations = List.of(firstTypeAnnotation);

    ClassNode secondClassNode = new ClassNode();
    secondClassNode.visibleTypeAnnotations = List.of(firstTypeAnnotation, secondTypeAnnotation);

    assertThat(firstClassNode)
            .isEqualTo(ClassNodeUtils.copy(firstClassNode));

    assertThat(firstClassNode)
            .addOption(StandardAssertOption.IGNORE_VISIBLE_TYPE_ANNOTATIONS)
            .isEqualTo(secondClassNode);

    Assertions.assertThatThrownBy(() -> assertThat(firstClassNode)
                      .isEqualTo(secondClassNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Class: class null > Has equal class visible type annotations] \n" +
                          "Expecting actual:\n" +
                          "  [@dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA // reference: class_type_parameter=0; path: null]\n" +
                          "to contain exactly in any order:\n" +
                          "  [@dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA // reference: class_type_parameter=0; path: null,\n" +
                          "    @dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationB // reference: class_type_parameter=1; path: null]\n" +
                          "but could not find the following elements:\n" +
                          "  [@dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationB // reference: class_type_parameter=1; path: null]\n" +
                          "when comparing values using TypeAnnotationNodeComparator");
  }

  @Test
  void testIsEqualToInvisibleTypeAnnotations() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.__helper.*;" +
                     "class MyClass<@InvisibleTypeParameterAnnotationA T, @InvisibleTypeParameterAnnotationB S> {" +
                     "}";

    ClassNode classNode = create()
            .addToClasspath(VisibleTypeParameterAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass");

    TypeAnnotationNode firstTypeAnnotation = classNode.invisibleTypeAnnotations.get(0);
    TypeAnnotationNode secondTypeAnnotation = classNode.invisibleTypeAnnotations.get(1);

    var firstClassNode = new ClassNode();
    firstClassNode.invisibleTypeAnnotations = List.of(firstTypeAnnotation);

    ClassNode secondClassNode = new ClassNode();
    secondClassNode.invisibleTypeAnnotations = List.of(firstTypeAnnotation, secondTypeAnnotation);

    assertThat(firstClassNode)
            .isEqualTo(ClassNodeUtils.copy(firstClassNode));

    assertThat(firstClassNode)
            .addOption(StandardAssertOption.IGNORE_INVISIBLE_TYPE_ANNOTATIONS)
            .isEqualTo(secondClassNode);

    Assertions.assertThatThrownBy(() -> assertThat(firstClassNode)
                      .isEqualTo(secondClassNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Class: class null > Has equal class invisible type annotations] \n" +
                          "Expecting actual:\n" +
                          "  [@dev.turingcomplete.asmtestkit.__helper.InvisibleTypeParameterAnnotationA // reference: class_type_parameter=0; path: null]\n" +
                          "to contain exactly in any order:\n" +
                          "  [@dev.turingcomplete.asmtestkit.__helper.InvisibleTypeParameterAnnotationA // reference: class_type_parameter=0; path: null,\n" +
                          "    @dev.turingcomplete.asmtestkit.__helper.InvisibleTypeParameterAnnotationB // reference: class_type_parameter=1; path: null]\n" +
                          "but could not find the following elements:\n" +
                          "  [@dev.turingcomplete.asmtestkit.__helper.InvisibleTypeParameterAnnotationB // reference: class_type_parameter=1; path: null]\n" +
                          "when comparing values using TypeAnnotationNodeComparator");
  }

  @Test
  void testIsEqualToAttributes() {
    var firstClassNode = new ClassNode();
    firstClassNode.attrs = List.of(new DummyAttribute("Foo"));
    var secondClassNode = new ClassNode();
    secondClassNode.attrs = List.of(new DummyAttribute("Foo"), new DummyAttribute("Bar"));

    assertThat(firstClassNode)
            .isEqualTo(ClassNodeUtils.copy(firstClassNode));

    assertThat(firstClassNode)
            .addOption(StandardAssertOption.IGNORE_ATTRIBUTES)
            .isEqualTo(secondClassNode);

    Assertions.assertThatThrownBy(() -> assertThat(firstClassNode)
                      .isEqualTo(secondClassNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Class: class null > Has equal class attributes] \n" +
                          "Expecting actual:\n" +
                          "  [Foonull]\n" +
                          "to contain exactly in any order:\n" +
                          "  [Foonull, Barnull]\n" +
                          "but could not find the following elements:\n" +
                          "  [Barnull]\n" +
                          "when comparing values using AttributeComparator");
  }

  @Test
  void testIsEqualToSuperName() {
    var firstClassNode = new ClassNode();
    firstClassNode.superName = toInternalName(Thread.class);
    var secondClassNode = new ClassNode();
    secondClassNode.superName = toInternalName(Number.class);

    assertThat(firstClassNode)
            .isEqualTo(ClassNodeUtils.copy(firstClassNode));

    assertThat(firstClassNode)
            .addOption(StandardAssertOption.IGNORE_SUPER_NAME)
            .isEqualTo(secondClassNode);

    Assertions.assertThatThrownBy(() -> assertThat(firstClassNode)
                      .isEqualTo(secondClassNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Class: class null > Has equal super name] \n" +
                          "expected: java.lang.Number\n" +
                          " but was: java.lang.Thread\n" +
                          "when comparing values using TypeComparator");
  }

  @Test
  void testIsEqualToSourceFile() {
    var firstClassNode = new ClassNode();
    firstClassNode.sourceFile = "Foo.java";
    var secondClassNode = new ClassNode();
    secondClassNode.sourceFile = "Bar.java";

    assertThat(firstClassNode)
            .isEqualTo(ClassNodeUtils.copy(firstClassNode));

    assertThat(firstClassNode)
            .addOption(StandardAssertOption.IGNORE_SOURCE_FILE)
            .isEqualTo(secondClassNode);

    Assertions.assertThatThrownBy(() -> assertThat(firstClassNode)
                      .isEqualTo(secondClassNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Class: class null > Has equal source file] \n" +
                          "expected: \"Bar.java\"\n" +
                          " but was: \"Foo.java\"");
  }

  @Test
  void testIsEqualToSourceDebug() {
    var firstClassNode = new ClassNode();
    firstClassNode.sourceDebug = "Foo";
    var secondClassNode = new ClassNode();
    secondClassNode.sourceDebug = "Bar";

    assertThat(firstClassNode)
            .isEqualTo(ClassNodeUtils.copy(firstClassNode));

    assertThat(firstClassNode)
            .addOption(StandardAssertOption.IGNORE_SOURCE_DEBUG)
            .isEqualTo(secondClassNode);

    Assertions.assertThatThrownBy(() -> assertThat(firstClassNode)
                      .isEqualTo(secondClassNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Class: class null > Has equal source debug] \n" +
                          "expected: \"Bar\"\n" +
                          " but was: \"Foo\"");
  }

  @Test
  void testIsEqualToOuterClass() {
    var firstClassNode = new ClassNode();
    firstClassNode.outerClass = toInternalName(Thread.class);
    var secondClassNode = new ClassNode();
    secondClassNode.outerClass = toInternalName(Number.class);

    assertThat(firstClassNode)
            .isEqualTo(ClassNodeUtils.copy(firstClassNode));

    assertThat(firstClassNode)
            .addOption(StandardAssertOption.IGNORE_OUTER_CLASS)
            .isEqualTo(secondClassNode);

    Assertions.assertThatThrownBy(() -> assertThat(firstClassNode)
                      .isEqualTo(secondClassNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Class: class null > Has equal outer class] \n" +
                          "expected: java.lang.Number\n" +
                          " but was: java.lang.Thread\n" +
                          "when comparing values using TypeComparator");
  }

  @Test
  void testIsEqualToOuterMethod() {
    var firstClassNode = new ClassNode();
    firstClassNode.outerClass = toInternalName(Thread.class);
    firstClassNode.outerMethod = "myFirstMethod";
    firstClassNode.outerMethodDesc = "()V";

    var secondClassNode = new ClassNode();
    secondClassNode.outerClass = toInternalName(Thread.class);
    secondClassNode.outerMethod = "mySecondMethod";
    secondClassNode.outerMethodDesc = "()V";

    assertThat(firstClassNode)
            .isEqualTo(ClassNodeUtils.copy(firstClassNode));

    assertThat(firstClassNode)
            .addOption(StandardAssertOption.IGNORE_OUTER_METHOD)
            .isEqualTo(secondClassNode);

    Assertions.assertThatThrownBy(() -> assertThat(firstClassNode)
                      .isEqualTo(secondClassNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Class: class null > Has equal outer method] \n" +
                          "expected: \"mySecondMethod\"\n" +
                          " but was: \"myFirstMethod\"");
  }

  @Test
  void testIsEqualToOuterMethodDesc() {
    var firstClassNode = new ClassNode();
    firstClassNode.outerClass = toInternalName(Thread.class);
    firstClassNode.outerMethod = "myMethod";
    firstClassNode.outerMethodDesc = "()V";

    var secondClassNode = new ClassNode();
    secondClassNode.outerClass = toInternalName(Thread.class);
    secondClassNode.outerMethod = "myMethod";
    secondClassNode.outerMethodDesc = "()I";

    assertThat(firstClassNode)
            .isEqualTo(ClassNodeUtils.copy(firstClassNode));

    assertThat(firstClassNode)
            .addOption(StandardAssertOption.IGNORE_OUTER_METHOD_DESCRIPTOR)
            .isEqualTo(secondClassNode);

    Assertions.assertThatThrownBy(() -> assertThat(firstClassNode)
                      .isEqualTo(secondClassNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Class: class null > Has equal outer method descriptor] \n" +
                          "expected: \"()I\"\n" +
                          " but was: \"()V\"");
  }

  @Test
  void testIsEqualToInnerClasses() {
    var firstClassNode = new ClassNode();
    firstClassNode.innerClasses = List.of(new InnerClassNode("First$Inner", "First", "Inner", 0));

    var secondClassNode = new ClassNode();
    secondClassNode.innerClasses = List.of(new InnerClassNode("Second$Inner", "Second", "Inner", 0));

    assertThat(firstClassNode)
            .isEqualTo(ClassNodeUtils.copy(firstClassNode));

    assertThat(firstClassNode)
            .addOption(StandardAssertOption.IGNORE_INNER_CLASSES)
            .isEqualTo(secondClassNode);

    Assertions.assertThatThrownBy(() -> assertThat(firstClassNode)
                      .isEqualTo(secondClassNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Class: class null > Has equal outer class] \n" +
                          "Expecting actual:\n" +
                          "  [[0] First$Inner // outer name: First // inner name: Inner]\n" +
                          "to contain exactly in any order:\n" +
                          "  [[0] Second$Inner // outer name: Second // inner name: Inner]\n" +
                          "elements not found:\n" +
                          "  [[0] Second$Inner // outer name: Second // inner name: Inner]\n" +
                          "and elements not expected:\n" +
                          "  [[0] First$Inner // outer name: First // inner name: Inner]\n" +
                          "when comparing values using InnerClassNodeComparator");
  }

  @Test
  void testIsEqualToNestHostClass() {
    var firstClassNode = new ClassNode();
    firstClassNode.nestHostClass = toInternalName(Thread.class);
    var secondClassNode = new ClassNode();
    secondClassNode.nestHostClass = toInternalName(Number.class);

    assertThat(firstClassNode)
            .isEqualTo(ClassNodeUtils.copy(firstClassNode));

    assertThat(firstClassNode)
            .addOption(StandardAssertOption.IGNORE_NEST_HOST_CLASS)
            .isEqualTo(secondClassNode);

    Assertions.assertThatThrownBy(() -> assertThat(firstClassNode)
                      .isEqualTo(secondClassNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Class: class null > Has equal nest host class] \n" +
                          "expected: java.lang.Number\n" +
                          " but was: java.lang.Thread\n" +
                          "when comparing values using TypeComparator");
  }

  @Test
  void testIsEqualToNestMembers() {
    var firstClassNode = new ClassNode();
    firstClassNode.nestMembers = List.of(toInternalName(Thread.class));
    var secondClassNode = new ClassNode();
    secondClassNode.nestMembers = List.of(toInternalName(Thread.class), toInternalName(Number.class));

    assertThat(firstClassNode)
            .isEqualTo(ClassNodeUtils.copy(firstClassNode));

    assertThat(firstClassNode)
            .addOption(StandardAssertOption.IGNORE_NEST_MEMBERS)
            .isEqualTo(secondClassNode);

    Assertions.assertThatThrownBy(() -> assertThat(firstClassNode)
                      .isEqualTo(secondClassNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Class: class null > Has equal nest members] \n" +
                          "Expecting actual:\n" +
                          "  [java.lang.Thread]\n" +
                          "to contain exactly in any order:\n" +
                          "  [java.lang.Thread, java.lang.Number]\n" +
                          "but could not find the following elements:\n" +
                          "  [java.lang.Number]\n" +
                          "when comparing values using TypeComparator");
  }

  @Test
  void testIsEqualToPermittedSubclasses() {
    var firstClassNode = new ClassNode();
    firstClassNode.permittedSubclasses = List.of(toInternalName(Thread.class));
    var secondClassNode = new ClassNode();
    secondClassNode.permittedSubclasses = List.of(toInternalName(Thread.class), toInternalName(Number.class));

    assertThat(firstClassNode)
            .isEqualTo(ClassNodeUtils.copy(firstClassNode));

    assertThat(firstClassNode)
            .addOption(StandardAssertOption.IGNORE_PERMITTED_SUBCLASSES)
            .isEqualTo(secondClassNode);

    Assertions.assertThatThrownBy(() -> assertThat(firstClassNode)
                      .isEqualTo(secondClassNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Class: class null > Has equal permitted subclasses] \n" +
                          "Expecting actual:\n" +
                          "  [java.lang.Thread]\n" +
                          "to contain exactly in any order:\n" +
                          "  [java.lang.Thread, java.lang.Number]\n" +
                          "but could not find the following elements:\n" +
                          "  [java.lang.Number]\n" +
                          "when comparing values using TypeComparator");
  }

  @Test
  void testIsEqualToFields() throws IOException {
    @Language("Java")
    String myClass1 = "class MyClass {" +
                      "  int myField1;" +
                      "}";

    ClassNode firstClassNode = create()
            .addJavaInputSource(myClass1)
            .compile()
            .readClassNode("MyClass");


    @Language("Java")
    String myClass2 = "class MyClass {" +
                      "  String myField2;" +
                      "}";

    ClassNode secondClassNode = create()
            .addJavaInputSource(myClass2)
            .compile()
            .readClassNode("MyClass");

    assertThat(firstClassNode)
            .isEqualTo(ClassNodeUtils.copy(firstClassNode));

    assertThat(firstClassNode)
            .addOption(StandardAssertOption.IGNORE_FIELDS)
            .isEqualTo(secondClassNode);

    Assertions.assertThatThrownBy(() -> assertThat(firstClassNode)
                      .isEqualTo(secondClassNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Class: class MyClass > Has equal fields] \n" +
                          "Expecting actual:\n" +
                          "  [[0] int myField1]\n" +
                          "to contain exactly in any order:\n" +
                          "  [[0] java.lang.String myField2]\n" +
                          "elements not found:\n" +
                          "  [[0] java.lang.String myField2]\n" +
                          "and elements not expected:\n" +
                          "  [[0] int myField1]\n" +
                          "when comparing values using FieldNodeComparator");
  }

  @Test
  void testIsEqualToMethods_DeclarationsOnlyMode() throws IOException {
    @Language("Java")
    String myClass1 = "class MyClass {" +
                      "  void myMethod1() {}" +
                      "  void myMethod2() {}" +
                      "}";

    ClassNode firstClassNode = create()
            .addJavaInputSource(myClass1)
            .compile()
            .readClassNode("MyClass");


    @Language("Java")
    String myClass2 = "class MyClass {" +
                      "  void myMethod2() {}" +
                      "  long myMethod3() { return 7; }" +
                      "}";

    ClassNode secondClassNode = create()
            .addJavaInputSource(myClass2)
            .compile()
            .readClassNode("MyClass");

    assertThat(firstClassNode)
            .isEqualTo(ClassNodeUtils.copy(firstClassNode));

    assertThat(firstClassNode)
            .addOption(StandardAssertOption.IGNORE_METHODS)
            .isEqualTo(secondClassNode);

    Assertions.assertThatThrownBy(() -> assertThat(firstClassNode)
                      .setMethodsComparisonMode(ClassNodeAssert.MethodsComparisonMode.DECLARATION_ONLY)
                      .isEqualTo(secondClassNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Class: class MyClass > Has equal method declarations] \n" +
                          "Expecting actual:\n" +
                          "  [\"<init>()V\", \"myMethod1()V\", \"myMethod2()V\"]\n" +
                          "to contain exactly in any order:\n" +
                          "  [\"<init>()V\", \"myMethod2()V\", \"myMethod3()J\"]\n" +
                          "elements not found:\n" +
                          "  [\"myMethod3()J\"]\n" +
                          "and elements not expected:\n" +
                          "  [\"myMethod1()V\"]\n");
  }

  @Test
  void testIsEqualToMethods_OneByOneMode() throws IOException {
    @Language("Java")
    String myClass1 = "class MyClass {" +
                      "  void myMethod1() {}" +
                      "  int myMethod2() { return 1; }" +
                      "}";

    ClassNode firstClassNode = create()
            .addJavaInputSource(myClass1)
            .compile()
            .readClassNode("MyClass");


    @Language("Java")
    String myClass2 = "class MyClass {" +
                      "  void myMethod1() {}" +
                      "  int myMethod2() { return 2; }" +
                      "}";

    ClassNode secondClassNode = create()
            .addJavaInputSource(myClass2)
            .compile()
            .readClassNode("MyClass");

    assertThat(firstClassNode)
            .isEqualTo(ClassNodeUtils.copy(firstClassNode));

    assertThat(firstClassNode)
            .addOption(StandardAssertOption.IGNORE_METHODS)
            .isEqualTo(secondClassNode);

    Assertions.assertThatThrownBy(() -> assertThat(firstClassNode)
                      .isEqualTo(secondClassNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Class: class MyClass > Has equal methods > Method: myMethod2()I > Has equal instructions] \n" +
                          "expected: \n" +
                          "  L0\n" +
                          "    LINENUMBER 1 L0\n" +
                          "    ICONST_2 // opcode: 5\n" +
                          "    IRETURN // opcode: 172\n" +
                          "  L1\n" +
                          " but was: \n" +
                          "  L0\n" +
                          "    LINENUMBER 1 L0\n" +
                          "    ICONST_1 // opcode: 4\n" +
                          "    IRETURN // opcode: 172\n" +
                          "  L1\n" +
                          "when comparing values using InsnListComparator");
  }

  @Test
  void testIsEqualToMethods_FullMode() throws IOException {
    @Language("Java")
    String myClass1 = "class MyClass {" +
                      "  void myMethod1() {}" +
                      "  void myMethod2() {}" +
                      "}";

    ClassNode firstClassNode = create()
            .addJavaInputSource(myClass1)
            .compile()
            .readClassNode("MyClass");


    @Language("Java")
    String myClass2 = "class MyClass {" +
                      "  void myMethod2() {}" +
                      "  long myMethod3() { return 7; }" +
                      "}";

    ClassNode secondClassNode = create()
            .addJavaInputSource(myClass2)
            .compile()
            .readClassNode("MyClass");

    assertThat(firstClassNode)
            .isEqualTo(ClassNodeUtils.copy(firstClassNode));

    assertThat(firstClassNode)
            .addOption(StandardAssertOption.IGNORE_METHODS)
            .isEqualTo(secondClassNode);

    Assertions.assertThatThrownBy(() -> assertThat(firstClassNode)
                      .setMethodsComparisonMode(ClassNodeAssert.MethodsComparisonMode.FULL)
                      .isEqualTo(secondClassNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Class: class MyClass > Has equal methods] \n" +
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
                          "      RETURN // opcode: 177\n" +
                          "    L1\n" +
                          "  // Local variable: #0 MyClass this // range: L0-L1\n" +
                          "  // Max locals: 1\n" +
                          "  // Max stack: 0,\n" +
                          "    [0] void myMethod2()\n" +
                          "    L0\n" +
                          "      LINENUMBER 1 L0\n" +
                          "      RETURN // opcode: 177\n" +
                          "    L1\n" +
                          "  // Local variable: #0 MyClass this // range: L0-L1\n" +
                          "  // Max locals: 1\n" +
                          "  // Max stack: 0]\n" +
                          "to contain exactly in any order:\n" +
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
                          "    [0] void myMethod2()\n" +
                          "    L0\n" +
                          "      LINENUMBER 1 L0\n" +
                          "      RETURN // opcode: 177\n" +
                          "    L1\n" +
                          "  // Local variable: #0 MyClass this // range: L0-L1\n" +
                          "  // Max locals: 1\n" +
                          "  // Max stack: 0,\n" +
                          "    [0] long myMethod3()\n" +
                          "    L0\n" +
                          "      LINENUMBER 1 L0\n" +
                          "      LDC 7 // opcode: 18\n" +
                          "      LRETURN // opcode: 173\n" +
                          "    L1\n" +
                          "  // Local variable: #0 MyClass this // range: L0-L1\n" +
                          "  // Max locals: 1\n" +
                          "  // Max stack: 2]\n" +
                          "elements not found:\n" +
                          "  [[0] long myMethod3()\n" +
                          "    L0\n" +
                          "      LINENUMBER 1 L0\n" +
                          "      LDC 7 // opcode: 18\n" +
                          "      LRETURN // opcode: 173\n" +
                          "    L1\n" +
                          "  // Local variable: #0 MyClass this // range: L0-L1\n" +
                          "  // Max locals: 1\n" +
                          "  // Max stack: 2]\n" +
                          "and elements not expected:\n" +
                          "  [[0] void myMethod1()\n" +
                          "    L0\n" +
                          "      LINENUMBER 1 L0\n" +
                          "      RETURN // opcode: 177\n" +
                          "    L1\n" +
                          "  // Local variable: #0 MyClass this // range: L0-L1\n" +
                          "  // Max locals: 1\n" +
                          "  // Max stack: 0]\n" +
                          "when comparing values using MethodNodeComparator");
  }

  @Test
  void testIsEqualToMethods_ignoreLineNumbers() throws IOException {
    @Language("Java")
    String myClassWithoutLineNumbers = "class MyClass {" +
                                       "   void myMethod1() {" +
                                       "     System.out.println(1);" +
                                       "   }" +
                                       "   void myMethod2() {" +
                                       "     System.out.println(1);" +
                                       "   }" +
                                       " }";

    ClassNode classWithoutLineNumbers = create()
            .addJavaInputSource(myClassWithoutLineNumbers)
            .compile()
            .readClassNode("MyClass");

    @Language("Java")
    String myClassWithLineNumbers = "class MyClass {\n" +
                                    "   void myMethod1() {\n" +
                                    "     System.out.println(1)\n;\n" +
                                    "   }\n" +
                                    "   void myMethod2() {\n" +
                                    "     System.out.println(1)\n\n;\n" +
                                    "   }\n" +
                                    " }\n";

    ClassNode classWithLineNumbers = create()
            .addJavaInputSource(myClassWithLineNumbers)
            .compile()
            .readClassNode("MyClass");

    assertThat(classWithoutLineNumbers)
            .ignoreLineNumbers()
            .isEqualTo(ClassNodeUtils.copy(classWithLineNumbers));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
