package dev.turingcomplete.asmtestkit.asmutils;

import dev.turingcomplete.asmtestkit.asmutils._internal.TextifierUtils;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.TraceMethodVisitor;

import java.util.Map;
import java.util.Objects;

public final class MethodNodeUtils {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  private MethodNodeUtils() {
    throw new UnsupportedOperationException();
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Extract names of all {@link Label}s in the given {@link MethodNode}.
   *
   * @param methodNode a {@link MethodNode}; never null.
   * @return a {@link Map} which maps a {@link Label} to its {@link String} name;
   * never null.
   */
  public static Map<Label, String> extractLabelNames(MethodNode methodNode) {
    Objects.requireNonNull(methodNode);

    var extendedTextifier = new TextifierUtils.ExtendedTextifier();
    methodNode.accept(new TraceMethodVisitor(extendedTextifier));
    return extendedTextifier.getLabelNames();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
