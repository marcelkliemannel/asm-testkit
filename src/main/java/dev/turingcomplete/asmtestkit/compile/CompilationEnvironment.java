package dev.turingcomplete.asmtestkit.compile;

import dev.turingcomplete.asmtestkit.assertion.representation.DiagnosticRepresentation;
import dev.turingcomplete.asmtestkit.compile._internal.JavaFileStringSource;
import org.assertj.core.api.Assertions;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public final class CompilationEnvironment {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private       JavaCompiler                        compiler                          = ToolProvider.getSystemJavaCompiler();
  private final List<String>                        compilerOptions                   = new ArrayList<>();
  private final List<JavaFileObject>                inputSources                      = new ArrayList<>();
  private final DiagnosticCollector<JavaFileObject> diagnosticsCollector              = new DiagnosticCollector<>();
  private       DiagnosticRepresentation            diagnosticRepresentation          = DiagnosticRepresentation.INSTANCE;
  private       StandardJavaFileManagerProvider     fileManagerProvider               = null;
  private       PrintWriter                         compilerOutput                    = new PrintWriter(System.out, true);
  private       boolean                             ignoreCompilationErrors           = false;
  private final List<Path>                          classpath                         = new ArrayList<>();
  private       boolean                             ignoreNonExistingClasspathEntries = false;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  private CompilationEnvironment() {
    useDefaultFileManager(Locale.getDefault(), Charset.defaultCharset());

    // Default compiler options
    compilerOptions.add("-g"); // Include all debug information
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link CompilationEnvironment}.
   *
   * <p>Call {@link #compile()} to execute the compiler based on the current
   * configuration.
   *
   * @return a new {@link CompilationEnvironment}; never null.
   */
  public static CompilationEnvironment create() {
    return new CompilationEnvironment();
  }

  /**
   * Adds the given Java source code as an input file.
   *
   * <p>The file path of the source file will be derived by parsing the package
   * and the simple class name from the given source code.
   *
   * @param sourceCode the Java source code as a {@link String}; never null.
   * @return {@code this} {@link CompilationEnvironment}; never null.
   */
  public CompilationEnvironment addJavaInputSource(String sourceCode) {
    inputSources.add(new JavaFileStringSource(Objects.requireNonNull(sourceCode)));

    return this;
  }

  /**
   * Adds all given Java source codes as input files.
   *
   * <p>The file path of the source file will be derived by parsing the package
   * and the simple class name from the given source code.
   *
   * @param sourceCodes an {@link Iterable} of Java source codes; never null.
   * @return {@code this} {@link CompilationEnvironment}; never null.
   */
  public CompilationEnvironment addJavaInputSources(Iterable<String> sourceCodes) {
    for (String sourceCode : Objects.requireNonNull(sourceCodes)) {
      inputSources.add(new JavaFileStringSource(sourceCode));
    }

    return this;
  }

  /**
   * Adds the given {@link Path} to the classpath.
   *
   * @param path a {@link Path}; never null.
   * @return {@code this} {@link CompilationEnvironment}; never null.
   */
  public CompilationEnvironment addToClasspath(Path path) {
    classpath.add(path);

    return this;
  }

  /**
   * Adds the directory in which the given {@link Class} is located to the
   * classpath of the compiler. This means that all other classes/resources
   * in the same package as the given class will also be in the classpath.
   *
   * @param aClass a {@link Class}; never null.
   * @return {@code this} {@link CompilationEnvironment}; never null.
   * @throws IllegalArgumentException if it is not possible to get the directory
   *                                  of the given {@link Class}.
   */
  public CompilationEnvironment addToClasspath(Class<?> aClass) {
    ProtectionDomain protectionDomain = Objects.requireNonNull(aClass).getProtectionDomain();
    if (protectionDomain != null) {
      CodeSource codeSource = protectionDomain.getCodeSource();
      if (codeSource != null) {
        URL location = codeSource.getLocation();
        if (location != null) {
          classpath.add(Path.of(location.getPath()));
          return this;
        }
      }
    }

    throw new IllegalArgumentException("Failed to get file path of class: " + aClass.getName());
  }

  /**
   * Will not fail {@link #compile()} if a {@link Path}s which was added to the
   * classpath does not exist.
   *
   * <p>A non-existing {@code Path} is often the root cause of consequential
   * errors, e.g., the compiler complains about a missing class.
   *
   * @return {@code this} {@link CompilationEnvironment}; never null.
   */
  public CompilationEnvironment ignoreNonExistingClasspathEntries() {
    this.ignoreNonExistingClasspathEntries = true;

    return this;
  }

  /**
   * Sets the {@link StandardJavaFileManager} by using the one from
   * {@link JavaCompiler#getStandardFileManager(DiagnosticListener, Locale, Charset)}
   * with the given {@link Locale} and {@link Charset}.
   *
   * <p>This method gets called during the initialization of the
   * {@link CompilationEnvironment} with the default locale of the system and
   * the default charset of the platform.
   *
   * <p>The provided file manager will be initialized during the execution of
   * {@link #compile()}.
   *
   * <p>The classes output directory will be set to a random, temporary directory
   * which may be deleted during the termination of the JVM.
   *
   * @param locale  the {@link Locale} which the {@link StandardJavaFileManager}
   *                should use; if null, the system default will be used.
   * @param charset the {@link Charset} which the {@link StandardJavaFileManager}
   *                should use; if null, the platform default will be used.
   * @return {@code this} {@link CompilationEnvironment}; never null.
   */
  @SuppressWarnings("UnusedReturnValue")
  public CompilationEnvironment useDefaultFileManager(Locale locale, Charset charset) {
    useFileManager((diagnosticsCollector) -> {
      StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(diagnosticsCollector, locale, charset);

      Path compilerOutputDir = Files.createTempDirectory("asm-testkit");
      compilerOutputDir.toFile().deleteOnExit();

      Files.createDirectories(compilerOutputDir);
      standardFileManager.setLocation(StandardLocation.CLASS_OUTPUT, List.of(compilerOutputDir.toFile()));

      return standardFileManager;
    });

    return this;
  }

  /**
   * Sets the {@link StandardJavaFileManager} which will be used by the
   * compiler.
   *
   * <p>The location {@link StandardLocation#CLASS_OUTPUT} must be configured
   * for the classes output directory.
   *
   * <p>The file manager will be initialized during the execution of
   * {@link #compile()}.
   *
   * @param fileManagerProvider a {@link StandardJavaFileManagerProvider} which
   *                            provides a {@link StandardJavaFileManager};
   *                            never null.
   * @return {@code this} {@link CompilationEnvironment}; never null.
   */
  public CompilationEnvironment useFileManager(StandardJavaFileManagerProvider fileManagerProvider) {
    this.fileManagerProvider = Objects.requireNonNull(fileManagerProvider);

    return this;
  }

  /**
   * Sets the {@link JavaCompiler} to be used for compilation.
   *
   * <p>The default value is {@link ToolProvider#getSystemJavaCompiler()}.
   *
   * @param compiler a {@link JavaCompiler}; never null.
   * @return {@code this} {@link CompilationEnvironment}; never null.
   */
  public CompilationEnvironment useCompiler(JavaCompiler compiler) {
    this.compiler = Objects.requireNonNull(compiler);

    return this;
  }

  /**
   * Sets the given {@link DiagnosticRepresentation}.
   *
   * <p>The default value is {@link DiagnosticRepresentation#INSTANCE}.
   *
   * @param diagnosticRepresentation a {@link DiagnosticRepresentation}; never
   *                                 null.
   * @return {@code this} {@link CompilationEnvironment}; never null.
   */
  public CompilationEnvironment useDiagnosticRepresentation(DiagnosticRepresentation diagnosticRepresentation) {
    this.diagnosticRepresentation = Objects.requireNonNull(diagnosticRepresentation);

    return this;
  }

  /**
   * If set, the {@link #compile()} method will not fail if there are
   * compilation errors.
   *
   * @return {@code this} {@link CompilationEnvironment}; never null.
   */
  public CompilationEnvironment ignoreCompilationErrors() {
    ignoreCompilationErrors = true;

    return this;
  }

  /**
   * Sets the {@code -g:none} compiler option.
   *
   * <p>By default, the option {@code -g} will be added which advised the
   * compiler to emit all debug information.
   *
   * @return {@code this} {@link CompilationEnvironment}; never null.
   */
  public CompilationEnvironment disableDebuggingInformation() {
    compilerOptions.removeIf(compilerOption -> compilerOption.equals("-g"));
    compilerOptions.add("-g:none");

    return this;
  }

  /**
   * Adds the given option to the compiler.
   *
   * @param option a compiler option; never null.
   * @return {@code this} {@link CompilationEnvironment}; never null.
   */
  public CompilationEnvironment addCompilerOption(String option) {
    compilerOptions.add(Objects.requireNonNull(option));

    return this;
  }

  /**
   * Sets the {@link PrintStream} which will be used for the compiler output
   * (diagnostics and additional compiler outputs).
   *
   * <p>If null, no output will be printed.
   *
   * <p><strong>WARNING:</strong> the {@code PrintWriter} will <em>not</em> be
   * closed after compilation.
   *
   * @param compilerOutput a {@link PrintWriter} to be used for the compiler
   *                       output; never null.
   * @return {@code this} {@link CompilationEnvironment}; never null.
   */
  public CompilationEnvironment writeCompilerOutputTo(PrintWriter compilerOutput) {
    this.compilerOutput = compilerOutput;

    return this;
  }

  /**
   * Runs the compiler based on the current {@link CompilationEnvironment}
   * configuration.
   *
   * @return the {@link CompilationResult}; never null.
   * @throws IOException if an I/O error occurred.
   */
  public CompilationResult compile() throws IOException {
    try (StandardJavaFileManager fileManager = this.fileManagerProvider.get(diagnosticsCollector)) {
      validateFileManager(fileManager);

      if (!inputSources.isEmpty()) {
        doCompile(fileManager);
      }

      return new CompilationResult(diagnosticsCollector.getDiagnostics(), fileManager, diagnosticRepresentation);
    }
    finally {
      if (compilerOutput != null) {
        for (Diagnostic<? extends JavaFileObject> diagnostic : diagnosticsCollector.getDiagnostics()) {
          compilerOutput.println(diagnosticRepresentation.toStringOf(diagnostic));
        }
      }
    }
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private void validateFileManager(StandardJavaFileManager fileManager) {
    Assertions.assertThat(fileManager.hasLocation(StandardLocation.CLASS_OUTPUT))
              .overridingErrorMessage("File manager should have a location for: " + StandardLocation.CLASS_OUTPUT.name())
              .isTrue();
  }

  private void doCompile(StandardJavaFileManager fileManager) {
    List<String> effectiveCompilerOptions = new ArrayList<>(compilerOptions);
    if (!classpath.isEmpty()) {
      validateClassPath();
      effectiveCompilerOptions.add("-cp");
      effectiveCompilerOptions.add(classpath.stream().map(Path::toString).collect(Collectors.joining(File.pathSeparator)));
    }

    PrintWriter out = compilerOutput != null ? compilerOutput : new PrintWriter(OutputStream.nullOutputStream());
    boolean noErrors = compiler.getTask(out, fileManager, diagnosticsCollector, effectiveCompilerOptions, null, inputSources)
                               .call();

    if (!ignoreCompilationErrors) {
      List<String> errors = diagnosticsCollector.getDiagnostics()
                                                .stream()
                                                .filter(diagnostic -> diagnostic.getKind().equals(Diagnostic.Kind.ERROR))
                                                .map(diagnostic -> diagnosticRepresentation.toStringOf(diagnostic))
                                                .collect(Collectors.toList());
      Assertions.assertThat(errors)
                .overridingErrorMessage("Expected no compilation errors. See output for errors.")
                .isEmpty();

      // If the compiler because of non-code related problems (e.g, invalid
      // options), there might be no error diagnostics.
      Assertions.assertThat(noErrors)
                .as("Expected no compilation errors. See output for errors.")
                .isTrue();
    }
  }

  private void validateClassPath() {
    if (ignoreNonExistingClasspathEntries) {
      return;
    }

    Assertions.assertThat(classpath)
              .allSatisfy(classpathEntry -> Assertions.assertThat(classpathEntry).exists());
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  @FunctionalInterface
  public interface StandardJavaFileManagerProvider {

    StandardJavaFileManager get(DiagnosticListener<JavaFileObject> diagnosticListener) throws IOException;
  }
}
