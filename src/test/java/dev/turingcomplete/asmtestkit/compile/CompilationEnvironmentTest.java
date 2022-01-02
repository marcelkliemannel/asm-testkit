package dev.turingcomplete.asmtestkit.compile;

import dev.turingcomplete.asmtestkit.assertion.representation.DiagnosticRepresentation;
import org.junit.jupiter.api.Test;

import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CompilationEnvironmentTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCustomDiagnosticRepresentation() throws IOException {
    DiagnosticRepresentation diagnosticRepresentation = DiagnosticRepresentation.defaultInstance();
    CompilationResult result = create()
            .useDiagnosticRepresentation(diagnosticRepresentation)
            .compile();

    assertThat(result.getDiagnosticRepresentation()).isSameAs(diagnosticRepresentation);
  }

  @Test
  void testCustomFileManager() throws IOException {
    StandardJavaFileManager standardFileManager = ToolProvider.getSystemJavaCompiler().getStandardFileManager(null, null, null);

    // Test validation for missing compiler output directory
    assertThatThrownBy(() -> create()
            .useFileManager(diagnosticListener -> standardFileManager)
            .compile())
            .isInstanceOf(AssertionError.class)
            .hasMessage("File manager should have a location for: " + StandardLocation.CLASS_OUTPUT.name());

    // Test instance which will be passed to the result
    standardFileManager.setLocation(StandardLocation.CLASS_OUTPUT, List.of(new File("")));
    assertThat(create()
                       .useFileManager(diagnosticListener -> standardFileManager)
                       .compile()
                       .getFileManager())
            .isSameAs(standardFileManager);

  }

  @Test
  void testDebugInformation() throws IOException {
    {
      // default = All debug information
      String sourceFile = create()
              .addJavaInputSource("class Foo { }")
              .compile()
              .readClassNode("Foo").sourceFile;
      assertThat(sourceFile).isNotNull();
    }

    {
      // No debug information
      String sourceFile = create()
              .addJavaInputSource("class Foo { }")
              .disableDebuggingInformation()
              .compile()
              .readClassNode("Foo").sourceFile;
      assertThat(sourceFile).isNull();
    }
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
