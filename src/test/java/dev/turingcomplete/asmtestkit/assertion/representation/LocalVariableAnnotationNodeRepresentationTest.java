package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.asmutils.MethodNodeUtils;
import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleAnnotationA;
import org.assertj.core.api.Assertions;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.LocalVariableAnnotationNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;

class LocalVariableAnnotationNodeRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testToStringOf() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.assertion.__helper.*;" +
                     "import java.util.Locale;class MyClass {" +
                     "   String myMethod(String param) {" +
                     "     String @VisibleTypeParameterAnnotationA [] a = { param + 1 };" +
                     "     @VisibleTypeParameterAnnotationB String b = param + 2;" +
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

    Assertions.assertThat(LocalVariableAnnotationNodeRepresentation.INSTANCE.toStringOf(firstLocalVariableAnnotationNode))
            .isEqualTo(String.format("@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA // reference: local_variable; path: null // range: L%s-L%s-2",
                                     firstLocalVariableAnnotationNode.start.get(0).getLabel().hashCode(), firstLocalVariableAnnotationNode.end.get(0).getLabel().hashCode()));

    Assertions.assertThat(LocalVariableAnnotationNodeRepresentation.INSTANCE.toStringOf(secondLocalVariableAnnotationNode))
              .isEqualTo(String.format("@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationB // reference: local_variable; path: null // range: L%s-L%s-3",
                                       secondLocalVariableAnnotationNode.start.get(0).getLabel().hashCode(), secondLocalVariableAnnotationNode.end.get(0).getLabel().hashCode()));
  }

  @Test
  void testToStringOfWithNames() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.assertion.__helper.*;" +
                     "import java.util.Locale;class MyClass {" +
                     "   String myMethod(String param) {" +
                     "     String @VisibleTypeParameterAnnotationA [] a = { param + 1 };" +
                     "     @VisibleTypeParameterAnnotationB String b = param + 2;" +
                     "     return b + 3;" +
                     "   }" +
                     " }";

    MethodNode myMethod = create()
            .addToClasspath(VisibleAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods.get(1);

    Map<Label, String> labelNames = MethodNodeUtils.extractLabelNames(myMethod);
    LocalVariableAnnotationNode firstLocalVariableAnnotationNode = myMethod.visibleLocalVariableAnnotations.get(0);
    LocalVariableAnnotationNode secondLocalVariableAnnotationNode = myMethod.visibleLocalVariableAnnotations.get(1);

    Assertions.assertThat(LocalVariableAnnotationNodeRepresentation.INSTANCE.toStringOf(firstLocalVariableAnnotationNode, labelNames))
              .isEqualTo("@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA // reference: local_variable; path: null // range: L1-L3-2");

    Assertions.assertThat(LocalVariableAnnotationNodeRepresentation.INSTANCE.toStringOf(secondLocalVariableAnnotationNode, labelNames))
              .isEqualTo("@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationB // reference: local_variable; path: null // range: L2-L3-3");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
