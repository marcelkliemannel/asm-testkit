package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.__helper.VisibleAnnotationA;
import org.assertj.core.api.Assertions;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.LocalVariableAnnotationNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;

import static dev.turingcomplete.asmtestkit.asmutils.MethodNodeUtils.extractLabelIndices;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThat;
import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;

class LocalVariableAnnotationNodeAssertTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testIsEqualTo() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA;import dev.turingcomplete.asmtestkit.__helper.*;" +
                     "class MyClass {" +
                     "   String myMethod(String param) {" +
                     "     String @VisibleTypeParameterAnnotationA [] a = { param + 1 };" +
                     "     @VisibleTypeParameterAnnotationA String b = param + 2;" +
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

    AsmAssertions.assertThat(firstLocalVariableAnnotationNode)
                 .isEqualTo(firstLocalVariableAnnotationNode);

    LabelIndexLookup labelIndexLookup = DefaultLabelIndexLookup.create(extractLabelIndices(methodNode));

    Assertions.assertThatThrownBy(() -> assertThat(firstLocalVariableAnnotationNode)
                      .useLabelIndexLookup(labelIndexLookup)
                      .isEqualTo(secondLocalVariableAnnotationNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Local Variable Annotation: @dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA > Has equal ranges] \n" +
                          "Expecting actual:\n" +
                          "  [\"#2 L1-L3\"]\n" +
                          "to contain exactly (and in same order):\n" +
                          "  [\"#3 L2-L3\"]\n" +
                          "but some elements were not found:\n" +
                          "  [\"#3 L2-L3\"]\n" +
                          "and others were not expected:\n" +
                          "  [\"#2 L1-L3\"]\n");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
