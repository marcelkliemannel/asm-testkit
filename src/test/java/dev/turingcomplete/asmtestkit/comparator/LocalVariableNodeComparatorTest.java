package dev.turingcomplete.asmtestkit.comparator;

import dev.turingcomplete.asmtestkit.asmutils.LocalVariableNodeUtils;
import dev.turingcomplete.asmtestkit.comparator.LocalVariableNodeComparator;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.LocalVariableNode;

import java.io.IOException;
import java.util.List;

import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;
import static org.assertj.core.api.Assertions.assertThat;

class LocalVariableNodeComparatorTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCompare() throws IOException {
    @Language("Java")
    String myClass = "class MyClass<T extends Number> {" +
                     "   void myMethod() {" +
                     "     String a = \"foo\";" +
                     "     T b = null;" +
                     "   }" +
                     " }";

    List<LocalVariableNode> localVariables = create()
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods.get(1).localVariables;

    LocalVariableNode firstVariable = localVariables.get(0);
    LocalVariableNode copyFirstVariable = LocalVariableNodeUtils.copy(firstVariable);
    LocalVariableNode secondVariable = localVariables.get(1);

    assertThat(LocalVariableNodeComparator.INSTANCE.compare(firstVariable, copyFirstVariable))
            .isEqualTo(0);

    assertThat(LocalVariableNodeComparator.INSTANCE.compare(firstVariable, secondVariable))
            .isEqualTo(-1);

    assertThat(LocalVariableNodeComparator.INSTANCE.compare(secondVariable, firstVariable))
            .isEqualTo(1);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
