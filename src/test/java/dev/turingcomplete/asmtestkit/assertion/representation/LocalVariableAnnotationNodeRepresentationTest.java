package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.asmutils.MethodNodeUtils;
import dev.turingcomplete.asmtestkit.assertion.LabelNameLookup;
import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleAnnotationA;
import org.assertj.core.api.Assertions;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.LocalVariableAnnotationNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.util.List;

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
                     "import java.util.Locale;" +
                     "class MyClass {" +
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
              .isEqualTo(String.format("@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA // reference: local_variable; path: null // range: #2 L%s-L%s",
                                       firstLocalVariableAnnotationNode.start.get(0).getLabel().hashCode(), firstLocalVariableAnnotationNode.end.get(0).getLabel().hashCode()));

    Assertions.assertThat(LocalVariableAnnotationNodeRepresentation.INSTANCE.toStringOf(secondLocalVariableAnnotationNode))
              .isEqualTo(String.format("@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationB // reference: local_variable; path: null // range: #3 L%s-L%s",
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

    LabelNameLookup labelNameLookup = LabelNameLookup.create(MethodNodeUtils.extractLabelNames(myMethod));
    LocalVariableAnnotationNode firstLocalVariableAnnotationNode = myMethod.visibleLocalVariableAnnotations.get(0);
    LocalVariableAnnotationNode secondLocalVariableAnnotationNode = myMethod.visibleLocalVariableAnnotations.get(1);

    Assertions.assertThat(LocalVariableAnnotationNodeRepresentation.INSTANCE.toStringOf(firstLocalVariableAnnotationNode, labelNameLookup))
              .isEqualTo("@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA // reference: local_variable; path: null // range: #2 L1-L3");

    Assertions.assertThat(LocalVariableAnnotationNodeRepresentation.INSTANCE.toStringOf(secondLocalVariableAnnotationNode, labelNameLookup))
              .isEqualTo("@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationB // reference: local_variable; path: null // range: #3 L2-L3");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
