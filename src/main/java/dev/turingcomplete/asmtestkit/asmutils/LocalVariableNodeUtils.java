package dev.turingcomplete.asmtestkit.asmutils;

import org.objectweb.asm.Label;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LocalVariableNode;

import java.util.Objects;

import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getFromObjectElseNull;

public final class LocalVariableNodeUtils {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  private LocalVariableNodeUtils() {
    throw new UnsupportedOperationException();
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a copy of the given {@link LocalVariableNode}.
   *
   * @param localVariableNode the {@link LocalVariableNode} to be copied;
   *                         never null.
   * @return a new {@link LocalVariableNode}; never null.
   */
  public static LocalVariableNode copy(LocalVariableNode localVariableNode) {
    Objects.requireNonNull(localVariableNode);

    Label startLabel = getFromObjectElseNull(localVariableNode.start, LabelNode::getLabel);
    Label endLabel = getFromObjectElseNull(localVariableNode.end, LabelNode::getLabel);

    return new LocalVariableNode(localVariableNode.name, localVariableNode.desc, localVariableNode.signature,
                                 new LabelNode(startLabel), new LabelNode(endLabel), localVariableNode.index);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
