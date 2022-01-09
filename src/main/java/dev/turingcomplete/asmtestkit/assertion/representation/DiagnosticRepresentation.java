package dev.turingcomplete.asmtestkit.assertion.representation;

import org.assertj.core.presentation.StandardRepresentation;

import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Creates a {@link String} representation of a {@link Diagnostic}.
 *
 * <p>The output is similar to the one of the OpenJDK compiler. Example output:
 * <pre>{@code
 * MyClass.java:41: error: illegal start of expression
 *     FileObject source =. diagnostic.getSource();
 *                        ^
 * }</pre>
 */
public class DiagnosticRepresentation extends StandardRepresentation {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link DiagnosticRepresentation} instance.
   */
  public static final DiagnosticRepresentation INSTANCE = new DiagnosticRepresentation();

  private static final Pattern LINE_BREAK_AT_END = Pattern.compile("(\\r\\n|\\r|\\n)$");

  private static final int SNIPPET_RADIUS = 20;

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private Locale locale = Locale.getDefault();

  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Sets the used {@link Locale}.
   *
   * <p>The default value is {@link Locale#getDefault()}.
   *
   * @param locale a {@link Locale}; never null.
   * @return {@code this} {@link DiagnosticRepresentation}; never null.
   */
  public DiagnosticRepresentation useLocale(Locale locale) {
    this.locale = locale;

    return this;
  }

  public String toStringOf(Diagnostic<? extends JavaFileObject> diagnostic) {
    if (diagnostic == null) {
      return null;
    }

    var text = new StringBuilder();

    FileObject source = diagnostic.getSource();
    if (source != null) {
      text.append(source.toUri().getPath());

      if (diagnostic.getLineNumber() != Diagnostic.NOPOS) {
        text.append(":").append(diagnostic.getLineNumber());
      }

      text.append(": ");
    }

    text.append(diagnostic.getKind().name().toLowerCase(Locale.ENGLISH).replace('_', ' ')).append(": ");
    text.append(diagnostic.getMessage(locale));

    String snippet = createSnippet(diagnostic);
    if (snippet != null) {
      text.append(System.lineSeparator()).append(snippet);
    }

    return text.toString();
  }

  @Override
  protected String fallbackToStringOf(Object object) {
    if (object instanceof Diagnostic) {
      return toStringOf(object);
    }

    return super.fallbackToStringOf(object);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private String createSnippet(Diagnostic<? extends JavaFileObject> diagnostic) {
    int lineNumber = (int) diagnostic.getLineNumber();
    int columnNumber = (int) diagnostic.getColumnNumber();
    if (lineNumber == Diagnostic.NOPOS || columnNumber == Diagnostic.NOPOS) {
      return null;
    }

    // Read source content
    String sourceContent = getSourceContent(diagnostic);
    if (sourceContent == null) {
      return null;
    }

    // Find line in source
    String sourceLine = sourceContent.lines().skip(lineNumber - 1).findFirst().orElse(null);
    if (sourceLine == null) {
      return null;
    }

    // Build snippet with source line and column indicator
    int snippetStart = columnNumber - SNIPPET_RADIUS;
    int columnIndicator = columnNumber - snippetStart;
    if (snippetStart < 0) {
      snippetStart = 0;
      columnIndicator = columnNumber;
    }
    int snippetEnd = Math.min(columnNumber + SNIPPET_RADIUS, sourceLine.length());

    // Add trimmed source line
    var snippet = new StringBuilder(sourceLine.substring(snippetStart, snippetEnd));
    if (!LINE_BREAK_AT_END.matcher(sourceLine).matches()) {
      snippet.append(System.lineSeparator());
    }

    // Add column indicator
    snippet.append(" ".repeat(columnIndicator - 1)).append("^");

    return snippet.toString();
  }

  private String getSourceContent(Diagnostic<? extends JavaFileObject> diagnostic) {
    JavaFileObject source = diagnostic.getSource();
    if (source == null) {
      return null;
    }

    try {
      CharSequence sourceContentSequence = source.getCharContent(true);
      return sourceContentSequence != null ? sourceContentSequence.toString() : null;
    }
    catch (IOException e) {
      throw new UncheckedIOException("Failed to read content from source: " + source.getName(), e);
    }
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
