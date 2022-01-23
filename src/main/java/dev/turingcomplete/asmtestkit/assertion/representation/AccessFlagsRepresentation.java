package dev.turingcomplete.asmtestkit.assertion.representation;

import dev.turingcomplete.asmtestkit.asmutils.Access;
import dev.turingcomplete.asmtestkit.node.AccessFlags;
import org.assertj.core.presentation.Representation;

import java.util.Comparator;

/**
 * An AssertJ {@link Representation} for {@link AccessFlags}s.
 *
 * <p>Example output: {@code (513) public interface}.
 */
public class AccessFlagsRepresentation extends AbstractAsmRepresentation<AccessFlags> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link AccessFlagsRepresentation} instance.
   */
  public static final AccessFlagsRepresentation INSTANCE = new AccessFlagsRepresentation();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AccessFlagsRepresentation() {
    super(AccessFlags.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link AnnotationNodeRepresentation} instance.
   *
   * @return a new {@link AnnotationNodeRepresentation}; never null;
   */
  public static AccessFlagsRepresentation create() {
    return new AccessFlagsRepresentation();
  }

  @Override
  protected String doToStringOf(AccessFlags accessFlags) {
    return doToSimplifiedStringOf(accessFlags);
  }

  @Override
  protected String doToSimplifiedStringOf(AccessFlags accessFlags) {
    int access = accessFlags.access();
    String textifiedAccess = "(" + access + ")";
    if (access > 0) {
      String javaSourceCodeRepresentation = toJavaSourceCodeRepresentation(accessFlags);
      if (!javaSourceCodeRepresentation.isBlank()) {
        textifiedAccess += " " + javaSourceCodeRepresentation;
      }
    }
    return textifiedAccess;
  }

  /**
   * Creates an array of readable access representations, like in the Java
   * source code, from the given {@code access} flags;
   *
   * @param accessFlags the {@link AccessFlags}; may be null.
   * @return an array of {@link String}s with Java source code like
   * representation of the access flags; never null. An empty array if
   * {@code access} is null.
   */
  public String[] toJavaSourceCodeRepresentations(AccessFlags accessFlags) {
    if (accessFlags == null) {
      return new String[0];
    }

    int access = accessFlags.access();
    return accessFlags.accessKind().getAccesses().stream()
                      .filter(_access -> _access.check(access))
                      .sorted(Comparator.comparingInt(Access::getOpcode))
                      .map(Access::toJavaSourceCodeRepresentation)
                      .toArray(String[]::new);
  }

  /**
   * Creates a combined readable access representation, like in the Java
   * source code, from the given {@code access} flags;
   *
   * @param accessFlags the {@link AccessFlags}; may be null.
   * @return a {@link String} representing a Java source code like representation
   * of the access flags; may be null if {@code access} is null.
   */
  public String toJavaSourceCodeRepresentation(AccessFlags accessFlags) {
    if (accessFlags == null) {
      return null;
    }

    return String.join(" ", toJavaSourceCodeRepresentations(accessFlags));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
