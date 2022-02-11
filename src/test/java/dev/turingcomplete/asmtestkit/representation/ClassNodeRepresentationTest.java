package dev.turingcomplete.asmtestkit.representation;

import dev.turingcomplete.asmtestkit.__helper.DummyAttribute;
import dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA;
import dev.turingcomplete.asmtestkit.compile.CompilationResult;
import org.assertj.core.api.Assertions;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;

public class ClassNodeRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @ParameterizedTest
  @MethodSource("testCreateClassDeclarationArguments")
  void testCreateClassDeclaration(String classSource, String expected) throws IOException {
    ClassNode Foo = create()
            .addJavaInputSource("import " + Supplier.class.getName() + ";" + classSource)
            .compile()
            .readClassNode("Foo");

    Assertions.assertThat(ClassNodeRepresentation.INSTANCE.createClassDeclaration(Foo))
              .isEqualTo(expected);
  }

  private static Stream<Arguments> testCreateClassDeclarationArguments() {
    return Stream.of(Arguments.of("public class Foo {}", "[33: public, super] class Foo extends java.lang.Object"),
                     Arguments.of("abstract class Foo extends Thread {}", "[1056: super, abstract] class Foo extends java.lang.Thread"),
                     Arguments.of("abstract class Foo implements Supplier<String> {}", "[1056: super, abstract] class Foo extends java.lang.Object implements java.util.function.Supplier // signature: Ljava/lang/Object;Ljava/util/function/Supplier<Ljava/lang/String;>;"),
                     Arguments.of("interface Foo {}", "[1536: interface, abstract] interface Foo"),
                     Arguments.of("interface Foo extends Supplier<String> {}", "[1536: interface, abstract] interface Foo extends java.util.function.Supplier // signature: Ljava/lang/Object;Ljava/util/function/Supplier<Ljava/lang/String;>;"),
                     Arguments.of("@interface Foo {}", "[9728: interface, abstract, annotation] @interface Foo extends java.lang.annotation.Annotation"),
                     Arguments.of("enum Foo { FOO; }", "[16432: final, super, enum] enum Foo extends java.lang.Enum // signature: Ljava/lang/Enum<LFoo;>;"));
  }

  @ParameterizedTest
  @MethodSource("testToSimplifiedStringArguments")
  void testToSimplifiedString(String classSource, String expected) throws IOException {
    ClassNode Foo = create()
            .addJavaInputSource("package foo.bar; import " + Supplier.class.getName() + ";" + classSource)
            .compile()
            .readClassNode("foo.bar.Foo");

    Assertions.assertThat(ClassNodeRepresentation.INSTANCE.toSimplifiedStringOf(Foo))
              .isEqualTo(expected);
  }

  private static Stream<Arguments> testToSimplifiedStringArguments() {
    return Stream.of(Arguments.of("public class Foo {}", "class foo.bar.Foo"),
                     Arguments.of("abstract class Foo extends Thread {}", "class foo.bar.Foo"),
                     Arguments.of("abstract class Foo implements Supplier<String> {}", "class foo.bar.Foo"),
                     Arguments.of("interface Foo {}", "interface foo.bar.Foo"),
                     Arguments.of("interface Foo extends Supplier<String> {}", "interface foo.bar.Foo"),
                     Arguments.of("@interface Foo {}", "@interface foo.bar.Foo"),
                     Arguments.of("enum Foo { FOO; }", "enum foo.bar.Foo"));
  }

  @Test
  void testToString() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.__helper.*;" +
                     "import java.util.function.Supplier;" +

                     "@VisibleTypeParameterAnnotationA\n" +
                     "@VisibleAnnotationA\n" +
                     "public abstract class MyClass<T> extends Thread implements Supplier<String> {" +

                     "  private int field1 = 4;" +
                     "  private T field2;" +

                     "  public void myMethod() {" +
                     "    System.out.print(4);" +
                     "  }" +

                     "  public enum InnerEnum {" +
                     "    A, B" +
                     "  }" +

                     "  public class MyClassInner {}" +

                     "}";

    CompilationResult result = create()
            .addToClasspath(VisibleTypeParameterAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile();

    ClassNode myClassNode = result.readClassNode("MyClass");
    myClassNode.attrs = new ArrayList<>();
    myClassNode.attrs.add(new DummyAttribute("Name1", "Content"));
    myClassNode.attrs.add(new DummyAttribute("Name2", "Content"));

    Assertions.assertThat(ClassNodeRepresentation.INSTANCE.toStringOf(myClassNode))
            .isEqualTo("// Class version: 55\n" +
                       "// Attribute: Name1Content\n" +
                       "// Attribute: Name2Content\n" +
                       "@dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA\n" +
                       "@dev.turingcomplete.asmtestkit.__helper.VisibleAnnotationA\n" +
                       "[1057: public, super, abstract] class MyClass extends java.lang.Thread implements java.util.function.Supplier // signature: <T:Ljava/lang/Object;>Ljava/lang/Thread;Ljava/util/function/Supplier<Ljava/lang/String;>;\n" +
                       "\n" +
                       "    [2: private] int field1\n" +
                       "    [2: private] java.lang.Object field2 // signature: TT;\n" +
                       "\n" +
                       "    [1: public] <init>()\n" +
                       "        L0\n" +
                       "          LINENUMBER 3 L0\n" +
                       "          ALOAD 0 // opcode: 25\n" +
                       "          INVOKESPECIAL java/lang/Thread.<init> ()V // opcode: 183\n" +
                       "          ALOAD 0 // opcode: 25\n" +
                       "          ICONST_4 // opcode: 7\n" +
                       "          PUTFIELD MyClass.field1 : I // opcode: 181\n" +
                       "          RETURN // opcode: 177\n" +
                       "        L1\n" +
                       "      // Local variable: #0 MyClass this // range: L0-L1 // signature: LMyClass<TT;>;\n" +
                       "      // Max locals: 1\n" +
                       "      // Max stack: 2\n" +
                       "\n" +
                       "    [1: public] void myMethod()\n" +
                       "        L0\n" +
                       "          LINENUMBER 3 L0\n" +
                       "          GETSTATIC java/lang/System.out : Ljava/io/PrintStream; // opcode: 178\n" +
                       "          ICONST_4 // opcode: 7\n" +
                       "          INVOKEVIRTUAL java/io/PrintStream.print (I)V // opcode: 182\n" +
                       "          RETURN // opcode: 177\n" +
                       "        L1\n" +
                       "      // Local variable: #0 MyClass this // range: L0-L1 // signature: LMyClass<TT;>;\n" +
                       "      // Max locals: 1\n" +
                       "      // Max stack: 2\n" +
                       "\n" +
                       "  // Source file: MyClass.java\n" +
                       "  // Inner classes: [1: public] MyClass$MyClassInner // outer name: MyClass // inner name: MyClassInner\n" +
                       "                    [16409: public, final, enum] MyClass$InnerEnum // outer name: MyClass // inner name: InnerEnum\n" +
                       "  // Nest members: MyClass$MyClassInner\n" +
                       "                   MyClass$InnerEnum\n");

    ClassNode innerEnumNode = result.readClassNode("MyClass$InnerEnum");
    Assertions.assertThat(ClassNodeRepresentation.INSTANCE.toStringOf(innerEnumNode))
              .isEqualTo("// Class version: 55\n" +
                         "[16433: public, final, super, enum] enum MyClass$InnerEnum extends java.lang.Enum // signature: Ljava/lang/Enum<LMyClass$InnerEnum;>;\n" +
                         "\n" +
                         "    [16409: public, static, final, enum] MyClass$InnerEnum A\n" +
                         "    [16409: public, static, final, enum] MyClass$InnerEnum B\n" +
                         "    [4122: private, static, final, synthetic] MyClass$InnerEnum[] $VALUES\n" +
                         "\n" +
                         "    [9: public, static] MyClass$InnerEnum[] values()\n" +
                         "        L0\n" +
                         "          LINENUMBER 3 L0\n" +
                         "          GETSTATIC MyClass$InnerEnum.$VALUES : [LMyClass$InnerEnum; // opcode: 178\n" +
                         "          INVOKEVIRTUAL [LMyClass$InnerEnum;.clone ()Ljava/lang/Object; // opcode: 182\n" +
                         "          CHECKCAST [LMyClass$InnerEnum; // opcode: 192\n" +
                         "          ARETURN // opcode: 176\n" +
                         "      // Max locals: 0\n" +
                         "      // Max stack: 1\n" +
                         "\n" +
                         "    [9: public, static] MyClass$InnerEnum valueOf(java.lang.String name)\n" +
                         "        L0\n" +
                         "          LINENUMBER 3 L0\n" +
                         "          LDC LMyClass$InnerEnum;.class // opcode: 18\n" +
                         "          ALOAD 0 // opcode: 25\n" +
                         "          INVOKESTATIC java/lang/Enum.valueOf (Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum; // opcode: 184\n" +
                         "          CHECKCAST MyClass$InnerEnum // opcode: 192\n" +
                         "          ARETURN // opcode: 176\n" +
                         "        L1\n" +
                         "      // Local variable: #0 java.lang.String name // range: L0-L1\n" +
                         "      // Max locals: 1\n" +
                         "      // Max stack: 2\n" +
                         "\n" +
                         "    [2: private] <init>(java.lang.String var0, int var1) // signature: ()V\n" +
                         "        L0\n" +
                         "          LINENUMBER 3 L0\n" +
                         "          ALOAD 0 // opcode: 25\n" +
                         "          ALOAD 1 // opcode: 25\n" +
                         "          ILOAD 2 // opcode: 21\n" +
                         "          INVOKESPECIAL java/lang/Enum.<init> (Ljava/lang/String;I)V // opcode: 183\n" +
                         "          RETURN // opcode: 177\n" +
                         "        L1\n" +
                         "      // Local variable: #0 MyClass$InnerEnum this // range: L0-L1\n" +
                         "      // Max locals: 3\n" +
                         "      // Max stack: 3\n" +
                         "\n" +
                         "    [8: static] <clinit>()\n" +
                         "        L0\n" +
                         "          LINENUMBER 3 L0\n" +
                         "          NEW MyClass$InnerEnum // opcode: 187\n" +
                         "          DUP // opcode: 89\n" +
                         "          LDC \"A\" // opcode: 18\n" +
                         "          ICONST_0 // opcode: 3\n" +
                         "          INVOKESPECIAL MyClass$InnerEnum.<init> (Ljava/lang/String;I)V // opcode: 183\n" +
                         "          PUTSTATIC MyClass$InnerEnum.A : LMyClass$InnerEnum; // opcode: 179\n" +
                         "          NEW MyClass$InnerEnum // opcode: 187\n" +
                         "          DUP // opcode: 89\n" +
                         "          LDC \"B\" // opcode: 18\n" +
                         "          ICONST_1 // opcode: 4\n" +
                         "          INVOKESPECIAL MyClass$InnerEnum.<init> (Ljava/lang/String;I)V // opcode: 183\n" +
                         "          PUTSTATIC MyClass$InnerEnum.B : LMyClass$InnerEnum; // opcode: 179\n" +
                         "          ICONST_2 // opcode: 5\n" +
                         "          ANEWARRAY MyClass$InnerEnum // opcode: 189\n" +
                         "          DUP // opcode: 89\n" +
                         "          ICONST_0 // opcode: 3\n" +
                         "          GETSTATIC MyClass$InnerEnum.A : LMyClass$InnerEnum; // opcode: 178\n" +
                         "          AASTORE // opcode: 83\n" +
                         "          DUP // opcode: 89\n" +
                         "          ICONST_1 // opcode: 4\n" +
                         "          GETSTATIC MyClass$InnerEnum.B : LMyClass$InnerEnum; // opcode: 178\n" +
                         "          AASTORE // opcode: 83\n" +
                         "          PUTSTATIC MyClass$InnerEnum.$VALUES : [LMyClass$InnerEnum; // opcode: 179\n" +
                         "          RETURN // opcode: 177\n" +
                         "      // Max locals: 0\n" +
                         "      // Max stack: 4\n" +
                         "\n" +
                         "  // Source file: MyClass.java\n" +
                         "  // Inner classes: [16409: public, final, enum] MyClass$InnerEnum // outer name: MyClass // inner name: InnerEnum\n" +
                         "  // Nest host class: MyClass\n");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
