package dev.turingcomplete.asmtestkit.comparator;

import dev.turingcomplete.asmtestkit.__helper.VisibleAnnotationA;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.LocalVariableAnnotationNode;

import java.io.IOException;
import java.util.List;

import static dev.turingcomplete.asmtestkit.comparator.LocalVariableAnnotationNodeComparator.INSTANCE;
import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;
import static org.assertj.core.api.Assertions.assertThat;

class LocalVariableAnnotationNodeComparatorTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCompare() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA;import dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationB;import dev.turingcomplete.asmtestkit.__helper.*;" +
                     "class MyClass {" +
                     "   String myMethod(String param) {" +
                     "     @VisibleTypeParameterAnnotationA String a = param + 1;" +
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

    //noinspection EqualsWithItself
    assertThat(INSTANCE.compare(firstLocalVariableAnnotationNode, firstLocalVariableAnnotationNode))
            .isEqualTo(0);

    assertThat(INSTANCE.compare(firstLocalVariableAnnotationNode, secondLocalVariableAnnotationNode))
            .isLessThanOrEqualTo(-1);

    assertThat(INSTANCE.compare(secondLocalVariableAnnotationNode, firstLocalVariableAnnotationNode))
            .isGreaterThanOrEqualTo(1);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
