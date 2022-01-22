package dev.turingcomplete.asmtestkit.asmutils;

import dev.turingcomplete.asmtestkit.asmutils._internal.TextifierUtils;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.TraceMethodVisitor;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
   * @param firstMethodNode       a {@link MethodNode}; never null.
   * @param additionalMethodNodes an array of additional {@link MethodNode}s;
   *                              never null.
   * @return a {@link Map} which maps a {@link Label} to its {@link String} name;
   * never null.
   */
  public static Map<Label, String> extractLabelNames(MethodNode firstMethodNode, MethodNode... additionalMethodNodes) {
    Objects.requireNonNull(firstMethodNode);
    Objects.requireNonNull(additionalMethodNodes);

    return Stream.concat(Stream.of(firstMethodNode), Arrays.stream(additionalMethodNodes))
                 .flatMap(methodNode -> {
                   var extendedTextifier = new TextifierUtils.ExtendedTextifier();
                   methodNode.accept(new TraceMethodVisitor(extendedTextifier));
                   return extendedTextifier.getLabelNames().entrySet().stream();
                 }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
