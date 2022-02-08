package dev.turingcomplete.asmtestkit.asmutils;

import dev.turingcomplete.asmtestkit.asmutils._internal.TextifierUtils;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.TraceMethodVisitor;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class MethodNodeUtils {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  private MethodNodeUtils() {
    throw new UnsupportedOperationException();
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Extract names of all {@link Label}s in the given {@link MethodNode}s.
   *
   * @param methodNodes an array of {@link MethodNode}s; never null.
   * @return a {@link Map} which maps a {@link Label} to its {@link String} name;
   * never null.
   */
  public static Map<Label, Integer> extractLabelIndices(MethodNode... methodNodes) {
    Objects.requireNonNull(methodNodes);

    return Arrays.stream(methodNodes)
                 .flatMap(methodNode -> {
                   var extendedTextifier = new TextifierUtils.ExtendedTextifier();
                   methodNode.accept(new TraceMethodVisitor(extendedTextifier));
                   return extendedTextifier.labelIndices().entrySet().stream();
                 }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  /**
   * Creates a copy of the given {@link MethodNode}.
   *
   * @param methodNode the {@link MethodNode} to be copied; never null.
   * @return a new {@link MethodNode}; never null.
   */
  public static MethodNode copy(MethodNode methodNode) {
    Objects.requireNonNull(methodNode);

    String[] exceptions = methodNode.exceptions != null ? methodNode.exceptions.toArray(String[]::new) : null;
    var copy = new MethodNode(methodNode.access, methodNode.name, methodNode.desc, methodNode.signature, exceptions);
    methodNode.accept(copy);

    return copy;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
