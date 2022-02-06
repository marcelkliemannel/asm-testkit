package dev.turingcomplete.asmtestkit.assertion;

import org.assertj.core.api.Assertions;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LocalVariableNode;

import java.io.IOException;

import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThat;
import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;

class LocalVariableNodeAssertTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private LocalVariableNode localVariable;
  private LocalVariableNode copyOfLocalVariable;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  @BeforeEach
  void setUp() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.__helper.*;" +
                     "import java.util.Locale;class MyClass {" +
                     "   void myMethod() {" +
                     "     String a = \"foo\";" +
                     "   }" +
                     " }";

    localVariable = create()
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods.get(1).localVariables.get(1);
    copyOfLocalVariable = new LocalVariableNode(localVariable.name, localVariable.desc, localVariable.signature, localVariable.start, localVariable.end, localVariable.index);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testIsEqualTo() {
    assertThat(localVariable)
            .isEqualTo(localVariable);
  }

  @Test
  void testIsEqualToIndex() {
    copyOfLocalVariable.index = 2;

    Assertions.assertThatThrownBy(() -> assertThat(localVariable)
                      .isEqualTo(copyOfLocalVariable))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Local Variable: a > Has equal index] \n" +
                          "expected: 2\n" +
                          " but was: 1");
  }

  @Test
  void testIsEqualToName() {
    copyOfLocalVariable.name = "b";

    Assertions.assertThatThrownBy(() -> assertThat(localVariable)
                      .isEqualTo(copyOfLocalVariable))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Local Variable: a > Has equal name] \n" +
                          "expected: \"b\"\n" +
                          " but was: \"a\"");
  }

  @Test
  void testIsEqualToDescriptor() {
    copyOfLocalVariable.desc = "I";

    Assertions.assertThatThrownBy(() -> assertThat(localVariable)
                      .isEqualTo(copyOfLocalVariable))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Local Variable: a > Has equal descriptor] \n" +
                          "expected: \"I\"\n" +
                          " but was: \"Ljava/lang/String;\"");
  }

  @Test
  void testIsEqualToSignature() {
    copyOfLocalVariable.signature = "TT";

    Assertions.assertThatThrownBy(() -> assertThat(localVariable)
                      .isEqualTo(copyOfLocalVariable))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Local Variable: a > Has equal signature] \n" +
                          "expected: \"TT\"\n" +
                          " but was: null");
  }

  @Test
  void testIsEqualToStart() {
    copyOfLocalVariable.start = new LabelNode(new Label());

    Assertions.assertThatThrownBy(() -> assertThat(localVariable)
                      .isEqualTo(copyOfLocalVariable))
              .isInstanceOf(AssertionError.class)
              .hasMessage(String.format("[Local Variable: a > Has equal start label] \n" +
                                        "expected: L%s\n" +
                                        " but was: L%s\n" +
                                        "when comparing values using LabelNodeComparator",
                                        copyOfLocalVariable.start.getLabel().hashCode(),
                                        localVariable.start.getLabel().hashCode()));
  }

  @Test
  void testIsEqualToEnd() {
    copyOfLocalVariable.end = new LabelNode(new Label());

    Assertions.assertThatThrownBy(() -> assertThat(localVariable)
                      .isEqualTo(copyOfLocalVariable))
              .isInstanceOf(AssertionError.class)
              .hasMessage(String.format("[Local Variable: a > Has equal end label] \n" +
                                        "expected: L%s\n" +
                                        " but was: L%s\n" +
                                        "when comparing values using LabelNodeComparator",
                                        copyOfLocalVariable.end.getLabel().hashCode(),
                                        localVariable.end.getLabel().hashCode()));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
