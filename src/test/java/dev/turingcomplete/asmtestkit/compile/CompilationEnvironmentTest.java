package dev.turingcomplete.asmtestkit.compile;

import dev.turingcomplete.asmtestkit.assertion.representation.DiagnosticRepresentation;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;

import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CompilationEnvironmentTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCustomDiagnosticRepresentation() throws IOException {
    DiagnosticRepresentation diagnosticRepresentation = DiagnosticRepresentation.INSTANCE;
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

  @Test
  void testAddToClasspathClass() throws IOException {
    @Language("Java")
    String myClass = "class MyClass {" +
                     "  dev.turingcomplete.asmtestkit.compile.CompilationEnvironmentTest.MyClassA myClassA;" +
                     "  dev.turingcomplete.asmtestkit.compile.CompilationEnvironmentTest.MyClassB myClassB;" +
                     "}";

    create()
            // Only include A
            .addToClasspath(MyClassA.class)
            .addJavaInputSource(myClass)
            .compile();
  }

  @Test
  void testAddToClasspathPath() throws IOException {
    @Language("Java")
    String myClass = "class MyClass {" +
                     "  MyClassA myClassA;" +
                     "}";

    create()
            .addToClasspath(Path.of("src/test/resources/dev/turingcomplete/asmtestkit/compile"))
            .addJavaInputSource(myClass)
            .compile();
  }

  @Test
  void testValidateClassPath() throws IOException {
    // Ignore non-existing
    create()
            .addJavaInputSource("class MyClass {}")
            .addToClasspath(Path.of("nonExistingDirectory"))
            .ignoreNonExistingClasspathEntries()
            .compile();

    // Fail non-existing
    assertThatThrownBy(() -> create()
            .addJavaInputSource("class MyClass {}")
            .addToClasspath(Path.of("nonExistingDirectory"))
            .compile())
            .isInstanceOf(AssertionError.class)
            .hasMessage("\nExpecting all elements of:\n" +
                        "  [nonExistingDirectory]\n" +
                        "to satisfy given requirements, but these elements did not:\n" +
                        "\n" +
                        "nonExistingDirectory\n" +
                        "error: \n" +
                        "Expecting path:\n" +
                        "  nonExistingDirectory\n" +
                        "to exist (symbolic links were followed).");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  public static class MyClassA {
  }

  public static class MyClassB {

  }
}
