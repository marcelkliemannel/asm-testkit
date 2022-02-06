package dev.turingcomplete.asmtestkit.asmutils;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import java.util.Objects;

public final class FieldNodeUtils {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  private FieldNodeUtils() {
    throw new UnsupportedOperationException();
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a copy of the given {@link FieldNode}.
   *
   * @param fieldNode the {@link FieldNode} to be copied; never null.
   * @return a new {@link FieldNode}; never null.
   */
  public static FieldNode copy(FieldNode fieldNode) {
    Objects.requireNonNull(fieldNode);

    var classNode = new ClassNode();
    fieldNode.accept(classNode);

    FieldNode copy = classNode.fields.get(0);
    assert Objects.equals(copy.name, fieldNode.name);

    return copy;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
