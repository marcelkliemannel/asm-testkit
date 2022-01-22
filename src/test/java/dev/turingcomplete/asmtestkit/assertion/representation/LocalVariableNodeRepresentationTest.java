package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.asmutils.MethodNodeUtils;
import dev.turingcomplete.asmtestkit.assertion.LabelNameLookup;
import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleAnnotationA;
import org.assertj.core.api.Assertions;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;

import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;

class LocalVariableNodeRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testToStringOf() throws IOException {
    @Language("Java")
    String myClass = "class MyClass<T extends Number> {" +
                     "   void myMethod() {" +
                     "     String a = \"foo\";" +
                     "     T b = null;" +
                     "   }" +
                     " }";

    MethodNode methodNode = create()
            .addToClasspath(VisibleAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods.get(1);

    LocalVariableNode thisLocalVariableNode = methodNode.localVariables.get(0);
    LocalVariableNode secondLocalVariableNode = methodNode.localVariables.get(1);
    LocalVariableNode thirdLocalVariableNode = methodNode.localVariables.get(2);

    LabelNameLookup labelNameLookup = LabelNameLookup.create(MethodNodeUtils.extractLabelNames(methodNode));

    Assertions.assertThat(LocalVariableNodeRepresentation.INSTANCE.toStringOf(thisLocalVariableNode, labelNameLookup))
              .isEqualTo("#0 MyClass this // range: L0-L3 // signature: LMyClass<TT;>;");

    Assertions.assertThat(LocalVariableNodeRepresentation.INSTANCE.toStringOf(secondLocalVariableNode, labelNameLookup))
              .isEqualTo("#1 java.lang.String a // range: L1-L3");

    Assertions.assertThat(LocalVariableNodeRepresentation.INSTANCE.toStringOf(thirdLocalVariableNode, labelNameLookup))
              .isEqualTo("#2 java.lang.Number b // range: L2-L3 // signature: TT;");
  }

  @Test
  void testToStringOfWithLabelNames() throws IOException {
    @Language("Java")
    String myClass = "class MyClass {" +
                     "   void myMethod() {" +
                     "     String a = \"foo\";" +
                     "   }" +
                     " }";

    MethodNode methodNode = create()
            .addToClasspath(VisibleAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods.get(1);

    LabelNameLookup labelNameLookup = LabelNameLookup.create(MethodNodeUtils.extractLabelNames(methodNode));
    LocalVariableNode localVariableNode = methodNode.localVariables.get(0);

    Assertions.assertThat(LocalVariableNodeRepresentation.INSTANCE.toStringOf(localVariableNode, labelNameLookup))
              .isEqualTo("#0 MyClass this // range: L0-L2");
  }

  @Test
  void testToSimplifiedStringOf() throws IOException {
    @Language("Java")
    String myClass = "class MyClass<T extends Number> {" +
                     "   void myMethod() {" +
                     "     String a = \"foo\";" +
                     "   }" +
                     " }";

    LocalVariableNode localVariableNode = create()
            .addToClasspath(VisibleAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods.get(1).localVariables.get(1);

    Assertions.assertThat(LocalVariableNodeRepresentation.INSTANCE.toSimplifiedStringOf(localVariableNode))
              .isEqualTo("a");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
