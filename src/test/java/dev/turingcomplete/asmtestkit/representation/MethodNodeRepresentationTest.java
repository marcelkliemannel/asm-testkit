package dev.turingcomplete.asmtestkit.representation;

import dev.turingcomplete.asmtestkit.asmutils.AnnotationNodeUtils;
import dev.turingcomplete.asmtestkit.__helper.DummyAttribute;
import dev.turingcomplete.asmtestkit.__helper.VisibleAnnotationA;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ParameterNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static dev.turingcomplete.asmtestkit.representation.MethodNodeRepresentation.INSTANCE;
import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;
import static org.assertj.core.api.Assertions.assertThat;

class MethodNodeRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCreateMethodDeclaration() throws IOException {
    @Language("Java")
    String myClass = "class MyClass<S> {" +
                     "  static { System.out.println(2); } " +
                     "  MyClass() {} " +
                     "  /** @noinspection RedundantThrows*/" +
                     "  public Integer fullMethod(final int foo, String bar) throws java.io.IOException, ClassNotFoundException { return 1; }" +
                     "  /** @noinspection RedundantThrows*/ " +
                     "  void singleException() throws java.io.IOException {}" +
                     "  <T> T withSignature(S foo, S bar) { return null; }" +
                     "  void singleParameters(int foo) {}" +
                     "  void arrayParameter(int[] foo) {}" +
                     "  void varargsParameter(int ...foo) {}" +
                     "  void withoutAnything() {}" +
                     "}";
    List<MethodNode> methods = create()
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods;

    Map<String, String> actual = methods.stream().collect(Collectors.toMap(methodNode -> methodNode.name, INSTANCE::createMethodDeclaration));

    Map<String, String> expected = Map.of(
            "<clinit>", "(8) static <clinit>()",
            "<init>", "(0) <init>()",
            "fullMethod", "(1) public java.lang.Integer fullMethod(int foo, java.lang.String bar) throws java.io.IOException java.lang.ClassNotFoundException",
            "singleException", "(0) void singleException() throws java.io.IOException",
            "withSignature", "(0) java.lang.Object withSignature(java.lang.Object foo, java.lang.Object bar) // signature: <T:Ljava/lang/Object;>(TS;TS;)TT;",
            "singleParameters", "(0) void singleParameters(int foo)",
            "arrayParameter", "(0) void arrayParameter(int[] foo)",
            "varargsParameter", "(128) varargs void varargsParameter(int[] foo)",
            "withoutAnything", "(0) void withoutAnything()");

    assertThat(actual).containsExactlyInAnyOrderEntriesOf(expected);
  }

  @Test
  void testToSimplifiedStringOf() throws IOException {
    @Language("Java")
    String myClass = "class MyClass<S> {" +
                     "  static { System.out.println(2); } " +
                     "  MyClass() {} " +
                     "  /** @noinspection RedundantThrows*/ " +
                     "  void singleException() throws java.io.IOException {}" +
                     "  <T> T withSignature(S foo, S bar) { return null; }" +
                     "  void singleParameters(int foo) {}" +
                     "  void arrayParameter(int[] foo) {}" +
                     "  void varargsParameter(int ...foo) {}" +
                     "  void withoutAnything() {}" +
                     "}";
    List<MethodNode> methods = create()
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods;

    Map<String, String> actual = methods.stream().collect(Collectors.toMap(methodNode -> methodNode.name, INSTANCE::toSimplifiedStringOf));

    Map<String, String> expected = Map.of(
            "singleException", "singleException()V",
            "varargsParameter", "varargsParameter([I)V",
            "withSignature", "withSignature(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;",
            "<init>", "<init>()V",
            "arrayParameter", "arrayParameter([I)V",
            "singleParameters", "singleParameters(I)V",
            "withoutAnything", "withoutAnything()V",
            "<clinit>", "<clinit>()V");

    assertThat(actual).containsExactlyInAnyOrderEntriesOf(expected);
  }

  @Test
  void testToStringOf() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.__helper.InvisibleAnnotationB;import dev.turingcomplete.asmtestkit.__helper.InvisibleTypeParameterAnnotationA;import dev.turingcomplete.asmtestkit.__helper.VisibleAnnotationA;import dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA;import dev.turingcomplete.asmtestkit.__helper.*;" +

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
    List<MethodNode> methods = create()
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods;

    MethodNode myMethod = methods.get(1);

    myMethod.parameters = List.of(new ParameterNode("param", Opcodes.ACC_FINAL));
    //noinspection unchecked
    myMethod.visibleParameterAnnotations = (List<AnnotationNode>[]) new List<?>[1];
    myMethod.visibleParameterAnnotations[0] = List.of(AnnotationNodeUtils.createAnnotationNode(SuppressWarnings.class));

    myMethod.attrs = new ArrayList<>();
    myMethod.attrs.add(new DummyAttribute("Name1", "Content"));
    myMethod.attrs.add(new DummyAttribute("Name2", "Content"));

    assertThat(INSTANCE.toStringOf(myMethod))
            .isEqualTo("// Attribute: Name1Content\n" +
                       "// Attribute: Name2Content\n" +
                       "@dev.turingcomplete.asmtestkit.__helper.VisibleAnnotationA\n" +
                       "@dev.turingcomplete.asmtestkit.__helper.InvisibleAnnotationB // invisible\n" +
                       "(0) java.lang.Object myMethod((16) final java.lang.String param, java.lang.Integer e) // signature: <T:Ljava/lang/Object;>(Ljava/lang/String;Ljava/lang/Integer;)TT;\n" +
                       "    L0\n" +
                       "      LINENUMBER 2 L0\n" +
                       "      BIPUSH 6 // opcode: 16\n" +
                       "      ALOAD 2 // opcode: 25\n" +
                       "      INVOKEVIRTUAL java/lang/Integer.intValue ()I // opcode: 182\n" +
                       "      IADD // opcode: 96\n" +
                       "      INVOKESTATIC java/lang/Integer.valueOf (I)Ljava/lang/Integer; // opcode: 184\n" +
                       "      ASTORE 3 // opcode: 58\n" +
                       "    L1\n" +
                       "      LINENUMBER 3 L1\n" +
                       "      BIPUSH 6 // opcode: 16\n" +
                       "      ALOAD 2 // opcode: 25\n" +
                       "      INVOKEVIRTUAL java/lang/Integer.intValue ()I // opcode: 182\n" +
                       "      IADD // opcode: 96\n" +
                       "      INVOKESTATIC java/lang/Integer.valueOf (I)Ljava/lang/Integer; // opcode: 184\n" +
                       "      ASTORE 4 // opcode: 58\n" +
                       "    L2\n" +
                       "      LINENUMBER 4 L2\n" +
                       "      ACONST_NULL // opcode: 1\n" +
                       "    L3\n" +
                       "      ARETURN // opcode: 176\n" +
                       "    L4\n" +
                       "      LINENUMBER 5 L4\n" +
                       "    FRAME SAME1 java/lang/IllegalArgumentException\n" +
                       "      ASTORE 3 // opcode: 58\n" +
                       "    L5\n" +
                       "      NEW java/lang/IllegalStateException // opcode: 187\n" +
                       "      DUP // opcode: 89\n" +
                       "      ALOAD 3 // opcode: 25\n" +
                       "      INVOKESPECIAL java/lang/IllegalStateException.<init> (Ljava/lang/Throwable;)V // opcode: 183\n" +
                       "      ATHROW // opcode: 191\n" +
                       "    L6\n" +
                       "  // Parameter: @java.lang.SuppressWarnings\n" +
                       "  //            (16) final param\n" +
                       "  // Local variable: #3 java.lang.Integer first // range: L3-L2\n" +
                       "  // Local variable: #4 java.lang.Integer second // range: L4-L2\n" +
                       "  // Local variable: #3 java.lang.IllegalArgumentException e // range: L5-L6\n" +
                       "  // Local variable: #0 MyClass this // range: L0-L6 // signature: LMyClass<TS;>;\n" +
                       "  // Local variable: #1 java.lang.String bar // range: L0-L6\n" +
                       "  // Local variable: #2 java.lang.Integer foo // range: L0-L6\n" +
                       "  // Local variable annotation: @dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA // reference: local_variable; path: null // range: #3 L3-L2\n" +
                       "  // Local variable annotation: @dev.turingcomplete.asmtestkit.__helper.InvisibleTypeParameterAnnotationA // reference: local_variable; path: null // range: #4 L4-L2 // invisible\n" +
                       "  // Try catch block: @dev.turingcomplete.asmtestkit.__helper.InvisibleTypeParameterAnnotationA // reference: exception_parameter=0; path: null // invisible\n" +
                       "  //                  java.lang.IllegalArgumentException // range: L0-L1; handled in: L2\n" +
                       "  // Max locals: 5\n" +
                       "  // Max stack: 3");
  }

  @Test
  void testDefaultAnnotationValue() throws IOException {
    @Language("Java")
    String myClass = "@interface MyAnnotation  {" +
                     "  String value() default \"foo\";" +
                     "}";
    MethodNode annotationMethod = create()
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyAnnotation")
            .methods.get(0);

    assertThat(INSTANCE.toStringOf(annotationMethod))
            .isEqualTo("(1025) public abstract java.lang.String value()\n" +
                       "  // Annotation default: \"foo\"\n" +
                       "  // Max locals: 0\n" +
                       "  // Max stack: 0");
  }

  @Test
  void testDefaultAnnotationValueOtherAnnotation() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.__helper.VisibleAnnotationA;" +
                     "@interface MyAnnotation  {" +
                     "  VisibleAnnotationA value() default @VisibleAnnotationA;" +
                     "}";
    MethodNode annotationMethod = create()
            .addToClasspath(VisibleAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyAnnotation")
            .methods.get(0);

    assertThat(INSTANCE.toStringOf(annotationMethod))
            .isEqualTo("(1025) public abstract dev.turingcomplete.asmtestkit.__helper.VisibleAnnotationA value()\n" +
                       "  // Annotation default: @dev.turingcomplete.asmtestkit.__helper.VisibleAnnotationA\n" +
                       "  // Max locals: 0\n" +
                       "  // Max stack: 0");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
