package dev.turingcomplete.asmtestkit.compile.diagnostic;

import dev.turingcomplete.asmtestkit.representation.DiagnosticRepresentation;
import dev.turingcomplete.asmtestkit.compile.CompilationEnvironment;
import dev.turingcomplete.asmtestkit.compile.CompilationResult;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class DiagnosticRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final DiagnosticRepresentation diagnosticRepresentation = DiagnosticRepresentation.INSTANCE;

  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testSnippetLargerThanRadius() throws IOException {
    CompilationResult result = CompilationEnvironment.create()
                                                     .addJavaInputSource("class F" + "o".repeat(30) + " / { int b" + "a".repeat(30) + " = 1; }")
                                                     .ignoreCompilationErrors()
                                                     .useDiagnosticRepresentation(diagnosticRepresentation)
                                                     .compile();

    assertThat(result.getDiagnostics().stream().map(diagnosticRepresentation::toStringOf).collect(Collectors.toList()))
            .containsExactlyInAnyOrderElementsOf(List.of("/Foooooooooooooooooooooooooooooo.java:1: error: '{' expected\n" +
                                                         "ooooooooooooooooooo / { int baaaaaaaaaaa\n" +
                                                         "                   ^"));
  }

  @Test
  void testSnippetSmallerThanRadius() throws IOException {
    CompilationResult result = CompilationEnvironment.create()
                                                     .addJavaInputSource("class Foo / { int b" + "a".repeat(30) + " = 1; }")
                                                     .ignoreCompilationErrors()
                                                     .useDiagnosticRepresentation(diagnosticRepresentation)
                                                     .compile();

    assertThat(result.getDiagnostics().stream().map(diagnosticRepresentation::toStringOf).collect(Collectors.toList()))
            .containsExactlyInAnyOrderElementsOf(List.of("/Foo.java:1: error: '{' expected\n" +
                                                         "class Foo / { int baaaaaaaaaaa\n" +
                                                         "         ^"));
  }

  @Test
  void testSnippetColumnIndicatorAtStart() throws IOException {
    CompilationResult result = CompilationEnvironment.create()
                                                     .addJavaInputSource("class Foo {\n#\n }")
                                                     .ignoreCompilationErrors()
                                                     .useDiagnosticRepresentation(diagnosticRepresentation)
                                                     .compile();

    assertThat(result.getDiagnostics().stream().map(diagnosticRepresentation::toStringOf).collect(Collectors.toList()))
            .containsExactlyInAnyOrderElementsOf(List.of("/Foo.java:2: error: illegal character: '#'\n" +
                                                         "#\n" +
                                                         "^",
                                                         "/Foo.java:3: error: reached end of file while parsing\n" +
                                                         " }\n" +
                                                         "  ^"));
  }

  @Test
  void testSnippetColumnIndicatorAtEnd() throws IOException {
    CompilationResult result = CompilationEnvironment.create()
                                                     .addJavaInputSource("class Foo {}#")
                                                     .ignoreCompilationErrors()
                                                     .writeCompilerOutputTo(null)
                                                     .useDiagnosticRepresentation(diagnosticRepresentation)
                                                     .compile();

    assertThat(result.getDiagnostics().stream().map(diagnosticRepresentation::toStringOf).collect(Collectors.toList()))
            .containsExactlyInAnyOrderElementsOf(List.of("/Foo.java:1: error: illegal character: '#'\n" +
                                                         "class Foo {}#\n" +
                                                         "            ^",
                                                         "/Foo.java:1: error: reached end of file while parsing\n" +
                                                         "class Foo {}#\n" +
                                                         "             ^"));
  }


  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
