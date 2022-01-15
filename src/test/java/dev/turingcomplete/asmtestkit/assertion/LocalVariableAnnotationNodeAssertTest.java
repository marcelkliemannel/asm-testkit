package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleAnnotationA;
import org.assertj.core.api.Assertions;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.LocalVariableAnnotationNode;

import java.io.IOException;
import java.util.List;

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
    String myClass = "import dev.turingcomplete.asmtestkit.assertion.__helper.*;" +
                     "import java.util.Locale;class MyClass {" +
                     "   String myMethod(String param) {" +
                     "     String @VisibleTypeParameterAnnotationA [] a = { param + 1 };" +
                     "     @VisibleTypeParameterAnnotationA String b = param + 2;" +
                     "     return b + 3;" +
                     "   }" +
                     " }";

    List<LocalVariableAnnotationNode> localVariableAnnotationNodes = create()
            .addToClasspath(VisibleAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods.get(1).visibleLocalVariableAnnotations;

    LocalVariableAnnotationNode firstLocalVariableAnnotationNode = localVariableAnnotationNodes.get(0);
    LocalVariableAnnotationNode secondLocalVariableAnnotationNode = localVariableAnnotationNodes.get(1);

    AsmAssertions.assertThat(firstLocalVariableAnnotationNode)
                 .isEqualTo(firstLocalVariableAnnotationNode);

    Assertions.assertThatThrownBy(() -> assertThat(firstLocalVariableAnnotationNode)
                      .isEqualTo(secondLocalVariableAnnotationNode))
              .isInstanceOf(AssertionError.class)
              .hasMessage(String.format("[Local Variable Annotation: @dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA > Are equal ranges] \n" +
                                        "Expecting actual:\n" +
                                        "  [\"L%1$s-L%2$s-2\"]\n" +
                                        "to contain exactly (and in same order):\n" +
                                        "  [\"L%3$s-L%4$s-3\"]\n" +
                                        "but some elements were not found:\n" +
                                        "  [\"L%3$s-L%4$s-3\"]\n" +
                                        "and others were not expected:\n" +
                                        "  [\"L%1$s-L%2$s-2\"]\n",
                                        firstLocalVariableAnnotationNode.start.get(0).getLabel().hashCode(),
                                        firstLocalVariableAnnotationNode.end.get(0).getLabel().hashCode(),
                                        secondLocalVariableAnnotationNode.start.get(0).getLabel().hashCode(),
                                        secondLocalVariableAnnotationNode.end.get(0).getLabel().hashCode()));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
