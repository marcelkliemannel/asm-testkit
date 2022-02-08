package dev.turingcomplete.asmtestkit.representation;

import dev.turingcomplete.asmtestkit.asmutils.Access;
import dev.turingcomplete.asmtestkit.asmutils.AccessKind;
import dev.turingcomplete.asmtestkit.node.AccessNode;
import org.assertj.core.presentation.Representation;

import java.util.Comparator;
import java.util.Objects;

/**
 * An AssertJ {@link Representation} for {@link AccessNode}s.
 *
 * <p>Example output: {@code (513) public interface}.
 */
public class AccessNodeRepresentation extends AbstractAsmRepresentation<AccessNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link AccessNodeRepresentation} instance.
   */
  public static final AccessNodeRepresentation INSTANCE = new AccessNodeRepresentation();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AccessNodeRepresentation() {
    super(AccessNode.class);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link AnnotationNodeRepresentation} instance.
   *
   * @return a new {@link AnnotationNodeRepresentation}; never null;
   */
  public static AccessNodeRepresentation create() {
    return new AccessNodeRepresentation();
  }

  @Override
  protected String doToStringOf(AccessNode accessNode) {
    return doToSimplifiedStringOf(accessNode);
  }

  @Override
  protected String doToSimplifiedStringOf(AccessNode accessNode) {
    int access = accessNode.access();
    String textifiedAccess = "[" + access;
    String[] elements = toJavaSourceCodeRepresentations(accessNode);
    if (elements.length > 0) {
      textifiedAccess += ": " + String.join(", ", elements);
    }
    textifiedAccess += "]";
    return textifiedAccess;
  }

  /**
   * Creates an array of readable access representations, like in the Java
   * source code, from the given {@code access} flags;
   *
   * @param accessNode the {@link AccessNode}; may be null.
   * @return an array of {@link String}s with Java source code like
   * representation of the access flags; never null. An empty array if
   * {@code access} is null.
   */
  public String[] toJavaSourceCodeRepresentations(AccessNode accessNode) {
    if (accessNode == null) {
      return new String[0];
    }

    int access = accessNode.access();
    return accessNode.accessKind().getAccesses().stream()
                     .filter(_access -> _access.check(access))
                     .sorted(Comparator.comparingInt(Access::getOpcode))
                     .map(Access::toJavaSourceCodeRepresentation)
                     .toArray(String[]::new);
  }

  /**
   * Creates a combined readable access representation, like in the Java
   * source code, from the given {@code access} flags;
   *
   * @param accessNode the {@link AccessNode}; may be null.
   * @return a {@link String} representing a Java source code like representation
   * of the access flags; may be null if {@code access} is null.
   */
  public String toJavaSourceCodeRepresentation(AccessNode accessNode) {
    if (accessNode == null) {
      return null;
    }

    return String.join(" ", toJavaSourceCodeRepresentations(accessNode));
  }


  /**
   * Gets a representation of the class kind like in the Java source code {e.g.,
   * {@code class} or {@code interface}}.
   *
   * @param accessNode the {@link AccessNode}; never null.
   * @return a {@link String} representing a Java source code like representation
   * of the class kind.
   * @throws IllegalArgumentException if {@code AccessNode} is not of kind
   *                                  {@link AccessKind#CLASS}.
   */
  public String toJavaSourceCodeClassKindRepresentation(AccessNode accessNode) {
    Objects.requireNonNull(accessNode);

    if (!accessNode.accessKind().equals(AccessKind.CLASS)) {
      throw new IllegalArgumentException("Expected flags to be of kind: " + accessNode.accessKind());
    }

    int access = accessNode.access();
    if (Access.ENUM.check(access)) {
      return "enum";
    }
    // Order is important: an annotation is also an interface
    else if (Access.ANNOTATION.check(access)) {
      return "@interface";
    }
    else if (Access.INTERFACE.check(access)) {
      return "interface";
    }
    else if (Access.RECORD.check(access)) {
      return "record";
    }
    else if (Access.MODULE.check(access)) {
      return "module";
    }
    else {
      return "class";
    }
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
