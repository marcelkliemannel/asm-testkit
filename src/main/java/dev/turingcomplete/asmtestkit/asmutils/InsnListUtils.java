package dev.turingcomplete.asmtestkit.asmutils;

import org.assertj.core.util.Streams;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.LocalVariableAnnotationNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;
import org.objectweb.asm.tree.TryCatchBlockNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.ifNotNull;

public final class InsnListUtils {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  private InsnListUtils() {
    throw new UnsupportedOperationException();
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Converts the given {@link Iterable} of {@link AbstractInsnNode} into a
   * {@link InsnList}.
   *
   * @param instructions an {@link Iterable} of {@link AbstractInsnNode}s;
   *                     never null.
   * @return a new {@link InsnList}; never null.
   */
  public static InsnList toInsnList(Iterable<? extends AbstractInsnNode> instructions) {
    return Streams.stream(Objects.requireNonNull(instructions))
                  .collect(InsnList::new, InsnList::add, InsnList::add);
  }

  /**
   * Filters all {@link LineNumberNode}s (and their related {@link LineNumberNode}s)
   * from the given {@link Iterable} of {@link AbstractInsnNode}s.
   *
   * @param instructions an {@link Iterable} of {@link AbstractInsnNode}s;
   *                     never null.
   * @return a new filtered {@link InsnList}; never null.
   */
  public static InsnList filterLineNumbers(Iterable<? extends AbstractInsnNode> instructions) {
    return filterLineNumbers(Objects.requireNonNull(instructions), Set.of());
  }

  /**
   * Creates a new {@link InsnList} by filtering all {@link LineNumberNode}s
   * (and their related {@link LineNumberNode}s) from the given {@link MethodNode}.
   *
   * @param methodNode a {@link MethodNode}; never null.
   * @return a new {@link InsnList} with filtered line numbers; never null.
   */
  public static InsnList filterLineNumbers(MethodNode methodNode) {
    return filterLineNumbers(Objects.requireNonNull(methodNode).instructions, collectRequiredLabels(methodNode));
  }

  /**
   * Creates a mapping of indices for all {@link Label}s in the given {@link Iterable}
   * of {@link AbstractInsnNode}s. The {@link Label}s will be numerated in teir
   * order of occurrence.
   *
   * @param instructions an {@link Iterable} of {@link AbstractInsnNode}s;
   *                     never null.
   * @return a {@link Map} which maps {@link Label} to their {@link Integer}
   * index; never null.
   */
  public static Map<Label, Integer> extractLabelIndices(Iterable<? extends AbstractInsnNode> instructions) {
    Objects.requireNonNull(instructions);

    Map<Label, Integer> result = new HashMap<>();

    int i = 0;
    for (AbstractInsnNode instruction : instructions) {
      if (instruction instanceof LabelNode) {
        result.put(((LabelNode) instruction).getLabel(), i++);
      }
    }

    return result;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private static InsnList filterLineNumbers(Iterable<? extends AbstractInsnNode> instructions, Set<Label> requiredLabels) {
    assert requiredLabels != null;

    // Collect all labels which are required by an instruction which is not
    // a line number.
    Set<Label> _requiredLabels = new HashSet<>(requiredLabels);
    Map<LabelNode, LabelNode> clonedLabelNodes = new HashMap<>();
    for (AbstractInsnNode instruction : instructions) {
      if (instruction instanceof TableSwitchInsnNode) {
        TableSwitchInsnNode tableSwitchInsnNode = (TableSwitchInsnNode) instruction;
        _requiredLabels.add(tableSwitchInsnNode.dflt.getLabel());
        tableSwitchInsnNode.labels.forEach(labelNode -> _requiredLabels.add(labelNode.getLabel()));
      }
      else if (instruction instanceof JumpInsnNode) {
        _requiredLabels.add(((JumpInsnNode) instruction).label.getLabel());
      }
      else if (instruction instanceof LookupSwitchInsnNode) {
        LookupSwitchInsnNode lookupSwitchInsnNode = (LookupSwitchInsnNode) instruction;
        _requiredLabels.add(lookupSwitchInsnNode.dflt.getLabel());
        lookupSwitchInsnNode.labels.forEach(labelNode -> _requiredLabels.add(labelNode.getLabel()));
      }
      else if (instruction instanceof LabelNode) {
        LabelNode labelNode = (LabelNode) instruction;
        clonedLabelNodes.put(labelNode, new LabelNode(labelNode.getLabel()));
      }
    }

    // Remove all line number nodes and label nodes which are referencing to
    // obsolete labels.
    // It is crucial to use a InsnList here, since its add method will set the
    // new prev/next instruction.
    var filteredInsnList = new InsnList();
    for (AbstractInsnNode instruction : instructions) {
      boolean deleteNode = instruction instanceof LineNumberNode;
      // Check if it is an obsolete label (only used by a line number)
      deleteNode = deleteNode || (instruction instanceof LabelNode && !_requiredLabels.contains(((LabelNode) instruction).getLabel()));
      if (!deleteNode) {
        filteredInsnList.add(instruction.clone(clonedLabelNodes));
      }
    }

    return filteredInsnList;
  }

  private static Set<Label> collectRequiredLabels(MethodNode methodNode) {
    Set<Label> requiredLabels = new HashSet<>();

    if (methodNode.localVariables != null) {
      for (LocalVariableNode localVariable : methodNode.localVariables) {
        ifNotNull(localVariable.start, start -> requiredLabels.add(start.getLabel()));
        ifNotNull(localVariable.end, end -> requiredLabels.add(end.getLabel()));
      }
    }
    if (methodNode.tryCatchBlocks != null) {
      for (TryCatchBlockNode tryCatchBlock : methodNode.tryCatchBlocks) {
        ifNotNull(tryCatchBlock.start, start -> requiredLabels.add(start.getLabel()));
        ifNotNull(tryCatchBlock.end, end -> requiredLabels.add(end.getLabel()));
        ifNotNull(tryCatchBlock.handler, handler -> requiredLabels.add(handler.getLabel()));
      }
    }

    Stream.Builder<LocalVariableAnnotationNode> localVariableAnnotations = Stream.builder();
    ifNotNull(methodNode.visibleLocalVariableAnnotations, entry -> entry.forEach(localVariableAnnotations));
    ifNotNull(methodNode.invisibleLocalVariableAnnotations, entry -> entry.forEach(localVariableAnnotations));
    localVariableAnnotations.build().flatMap(localVariableAnnotation -> {
                              Stream.Builder<LabelNode> builder = Stream.builder();
                              ifNotNull(localVariableAnnotation.start, start -> start.forEach(builder));
                              ifNotNull(localVariableAnnotation.end, start -> start.forEach(builder));
                              return builder.build();
                            })
                            .map(LabelNode::getLabel)
                            .forEach(requiredLabels::add);

    return requiredLabels;
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
