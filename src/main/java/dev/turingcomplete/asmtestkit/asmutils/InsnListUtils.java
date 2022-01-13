package dev.turingcomplete.asmtestkit.asmutils;

import org.assertj.core.util.Streams;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
    Objects.requireNonNull(instructions);

    // Collect all labels which are required by an instruction which is not
    // a line number.
    Set<Label> requiredLabels = new HashSet<>();
    Map<LabelNode, LabelNode> clonedLabelNodes = new HashMap<>();
    for (AbstractInsnNode instruction : instructions) {
      if (instruction instanceof TableSwitchInsnNode) {
        TableSwitchInsnNode tableSwitchInsnNode = (TableSwitchInsnNode) instruction;
        requiredLabels.add(tableSwitchInsnNode.dflt.getLabel());
        tableSwitchInsnNode.labels.forEach(labelNode -> requiredLabels.add(labelNode.getLabel()));
      }
      else if (instruction instanceof JumpInsnNode) {
        requiredLabels.add(((JumpInsnNode) instruction).label.getLabel());
      }
      else if (instruction instanceof LookupSwitchInsnNode) {
        ((LookupSwitchInsnNode) instruction).labels.forEach(labelNode -> requiredLabels.add(labelNode.getLabel()));
      }
      else if (instruction instanceof LabelNode) {
        clonedLabelNodes.put(((LabelNode) instruction), new LabelNode(((LabelNode) instruction).getLabel()));
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
      deleteNode = deleteNode || (instruction instanceof LabelNode && !requiredLabels.contains(((LabelNode) instruction).getLabel()));
      if (!deleteNode) {
        filteredInsnList.add(instruction.clone(clonedLabelNodes));
      }
    }

    return filteredInsnList;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
