package dev.turingcomplete.asmtestkit.compile._internal;

import org.assertj.core.api.Assertions;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a {@link SimpleJavaFileObject} source file as an in-memory
 * {@link String}.
 *
 * <p>The file name will be derivative by parsing the package name and the
 * simple class, interface, enum, record or module name from the source code.
 *
 * <p>A known limitation is that if "module XY", "package XY" or "class XY"
 * appears in a comment before the actual definition the derivation of the
 * file name will not work properly.
 */
public final class JavaFileStringSource extends SimpleJavaFileObject {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  private static final Pattern MODULE_DESCRIPTOR_PATTERN = Pattern.compile("\\s?module\\s+[^\\s;]+", Pattern.MULTILINE);
  private static final Pattern PACKAGE_NAME_PATTERN      = Pattern.compile("\\s?package\\s+(?<packageName>[^\\s;]+)", Pattern.MULTILINE);
  private static final Pattern SIMPLE_CLASS_NAME_PATTERN = Pattern.compile("[^{]*(?:class|interface|enum|record)\\s+(?<simpleClassName>[^\\s{<]+).*\\{", Pattern.MULTILINE);

  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final String sourceCode;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public JavaFileStringSource(String sourceCode) {
    super(URI.create("string:///" + extractFilePath(sourceCode)), Kind.SOURCE);
    this.sourceCode = sourceCode;
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public CharSequence getCharContent(boolean ignoreEncodingErrors) {
    return sourceCode;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private static String extractFilePath(String sourceCode) {
    // Remove line breaks
    sourceCode = sourceCode.replace("\n", " ");

    // Check if module descriptor
    if (MODULE_DESCRIPTOR_PATTERN.matcher(sourceCode).find()) {
      return "module-info" + Kind.SOURCE.extension;
    }

    // Check if class/enum/interface/record
    Matcher packageNameMatcher = PACKAGE_NAME_PATTERN.matcher(sourceCode);
    String packageName = packageNameMatcher.find() ? packageNameMatcher.group("packageName") : null;

    Matcher simpleClassNameMatcher = SIMPLE_CLASS_NAME_PATTERN.matcher(sourceCode);
    if (!simpleClassNameMatcher.find()) {
      Assertions.fail("Can't extract class name using pattern '%s' from source file:%s%s",
                      SIMPLE_CLASS_NAME_PATTERN.pattern(), System.lineSeparator(), sourceCode);
    }
    String simpleClassName = simpleClassNameMatcher.group("simpleClassName");

    // Prepend a slash at the beginning if the file path contains at least
    // one directory.
    return ((packageName != null ? "/" + packageName.replace('.', '/') + "/" : "") + simpleClassName) + Kind.SOURCE.extension;
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
