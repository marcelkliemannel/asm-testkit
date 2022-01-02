package dev.turingcomplete.asmtestkit.compile._internal;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.tools.JavaFileObject;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class JavaFileStringSourceTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  private static final List<Function<String, String>> SEMICOLON_FUZZERS = List.of(testSource -> testSource.replace(";", "  ;"),
                                                                                  testSource -> testSource.replace(";", "\t\t;"),
                                                                                  testSource -> testSource.replace(";", "\n\n;"));

  private static final List<Function<String, String>> SPACE_FUZZERS = List.of(testSource -> testSource.replace(" ", "  "),
                                                                              testSource -> testSource.replace(" ", "\t\t"),
                                                                              testSource -> testSource.replace(" ", "\n\n"));

  private static final List<Function<String, String>> LINE_START_FUZZERS = List.of(testSource -> testSource.lines().map(line -> "  " + line).collect(Collectors.joining(System.lineSeparator())),
                                                                                   testSource -> testSource.lines().map(line -> "\t\t" + line).collect(Collectors.joining(System.lineSeparator())),
                                                                                   testSource -> testSource.lines().map(line -> "\n\n" + line).collect(Collectors.joining(System.lineSeparator())));

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @ParameterizedTest
  @MethodSource("createTestClassNameExtractionTestSources")
  void testClassNameExtraction(String expectedSourceFileName, String sourceCode) {
    var javaFileStringSource = new JavaFileStringSource(sourceCode);
    Assertions.assertThat(javaFileStringSource.toUri())
              .isEqualTo(URI.create("string:///" + expectedSourceFileName));
  }

  @Test
  void testModuleDescriptorDetection() {
    Stream.of("module foo {}", "open module foo {}", "import foo;\nmodule foo {}")
          .flatMap(moduleDefinition -> {
            //noinspection CodeBlock2Expr
            return SPACE_FUZZERS.stream().flatMap(spaceFuzzer -> {
              //noinspection CodeBlock2Expr
              return LINE_START_FUZZERS.stream().map(lineStartFuzzer -> {
                //noinspection CodeBlock2Expr
                return spaceFuzzer.andThen(lineStartFuzzer).apply(moduleDefinition);
              });
            });
          }).forEach(testSource -> {
            var javaFileStringSource = new JavaFileStringSource(testSource);
            Assertions.assertThat(javaFileStringSource.toUri())
                      .isEqualTo(URI.create("string:///module-info.java"));
          });
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private static Stream<Arguments> createTestClassNameExtractionTestSources() {
    Map<String, String> sourceDirToPackageDefinition = Map.of("/foo/bar", "package foo.bar;",
                                                              "/foo", "package foo;",
                                                              "", "");

    Set<String> classDefinitions = Set.of("public class Foo {}",
                                          "public abstract class Foo {}",
                                          "import java.lang.Thread; class Foo extends Thread implements Foo {}",
                                          "public class Foo<T> implements Bar<T> {}",
                                          "record Foo {}",
                                          "interface Foo {}",
                                          "enum Foo {}");

    return sourceDirToPackageDefinition.entrySet().stream().flatMap(entry -> {
      String sourceFileName = (entry.getKey().isBlank() ? "" : (entry.getKey() + "/")) + "Foo" + JavaFileObject.Kind.SOURCE.extension;
      String packageDefinition = entry.getValue();
      return classDefinitions.stream()
                             .flatMap(classDefinition -> {
                               //noinspection CodeBlock2Expr
                               return SEMICOLON_FUZZERS.stream().flatMap(semicolonFuzzer -> {
                                 //noinspection CodeBlock2Expr
                                 return SPACE_FUZZERS.stream().flatMap(spaceFuzzer -> {
                                   //noinspection CodeBlock2Expr
                                   return LINE_START_FUZZERS.stream().map(lineStartFuzzer -> {
                                     //noinspection CodeBlock2Expr
                                     return semicolonFuzzer.andThen(spaceFuzzer)
                                                              .andThen(lineStartFuzzer)
                                                              .apply(packageDefinition + classDefinition);
                                   });
                                 });
                               });
                             })
                             .map(testSource -> Arguments.of(sourceFileName, testSource));
    });
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
