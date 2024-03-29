package dev.turingcomplete.asmtestkit.representation;

import dev.turingcomplete.asmtestkit.common.DefaultLabelIndexLookup;
import dev.turingcomplete.asmtestkit.common.LabelIndexLookup;
import dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA;
import org.assertj.core.api.Assertions;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.util.List;

import static dev.turingcomplete.asmtestkit.asmutils.MethodNodeUtils.extractLabelIndices;
import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;

class TryCatchBlockNodeRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testToStringOf() throws IOException {
    @Language("Java")
    String myClass = "import dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA;import dev.turingcomplete.asmtestkit.__helper.*;" +
                     "import java.io.IOException;" +
                     "class MyClass {" +
                     "   void myMethod(String param) {" +
                     "    try {" +
                     "       throw new IOException(\"fooo\");" +
                     "    }" +
                     "    catch(@VisibleTypeParameterAnnotationA IOException | IllegalArgumentException e) {" +
                     "       throw new IllegalStateException(e);" +
                     "    }" +
                     "    finally {" +
                     "       System.out.println();" +
                     "    }" +
                     "  }" +
                     "}";

    MethodNode methodNode = create()
            .addToClasspath(VisibleTypeParameterAnnotationA.class)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .methods.get(1);

    LabelIndexLookup labelIndexLookup = DefaultLabelIndexLookup.create(extractLabelIndices(methodNode));

    List<String> expectedRepresentation = List.of("@dev.turingcomplete.asmtestkit.__helper.VisibleTypeParameterAnnotationA // reference: exception_parameter=0; path: null\n" +
                                                  "java.io.IOException // range: L0-L1; handled in: L1",
                                                  "java.lang.IllegalArgumentException // range: L0-L1; handled in: L1",
                                                  "finally // range: L0-L2; handled in: L3");

    for (int i = 0; i < methodNode.tryCatchBlocks.size(); i++) {
      Assertions.assertThat(TryCatchBlockNodeRepresentation.INSTANCE.toStringOf(methodNode.tryCatchBlocks.get(i), labelIndexLookup))
                .isEqualTo(expectedRepresentation.get(i));
    }
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
