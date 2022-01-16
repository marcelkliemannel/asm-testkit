package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.asmutils.MethodNodeUtils;
import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleAnnotationA;
import org.assertj.core.api.Assertions;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    List<LocalVariableNode> localVariableNodes = create()
            .addToClasspath(VisibleAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods.get(1).localVariables;

    LocalVariableNode thisLocalVariableNode = localVariableNodes.get(0);
    LocalVariableNode secondLocalVariableNode = localVariableNodes.get(1);
    LocalVariableNode thirdLocalVariableNode = localVariableNodes.get(2);

    Assertions.assertThat(LocalVariableNodeRepresentation.INSTANCE.toStringOf(thisLocalVariableNode))
              .isEqualTo(String.format("#0 MyClass this (L%s-L%s) // signature: LMyClass<TT;>;", thisLocalVariableNode.start.getLabel().hashCode(), thisLocalVariableNode.end.getLabel().hashCode()));

    Assertions.assertThat(LocalVariableNodeRepresentation.INSTANCE.toStringOf(secondLocalVariableNode))
              .isEqualTo(String.format("#1 java.lang.String a (L%s-L%s)", secondLocalVariableNode.start.getLabel().hashCode(), secondLocalVariableNode.end.getLabel().hashCode()));

    Assertions.assertThat(LocalVariableNodeRepresentation.INSTANCE.toStringOf(thirdLocalVariableNode))
              .isEqualTo(String.format("#2 java.lang.Number b (L%s-L%s) // signature: TT;", thirdLocalVariableNode.start.getLabel().hashCode(), thirdLocalVariableNode.end.getLabel().hashCode()));
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

    Map<Label, String> labelNames = MethodNodeUtils.extractLabelNames(methodNode);
    LocalVariableNode localVariableNode = methodNode.localVariables.get(0);

    Assertions.assertThat(LocalVariableNodeRepresentation.INSTANCE.toStringOf(localVariableNode, labelNames))
              .isEqualTo("#0 MyClass this (L0-L2)");
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
