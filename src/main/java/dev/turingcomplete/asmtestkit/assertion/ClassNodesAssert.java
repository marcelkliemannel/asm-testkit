package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.common.IgnoreLineNumbersCapable;
import dev.turingcomplete.asmtestkit.comparator.ClassNodeComparator;
import dev.turingcomplete.asmtestkit.representation.ClassNodeRepresentation;
import org.assertj.core.api.AbstractIterableAssert;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;

import java.util.Comparator;

/**
 * An AssertJ {@link AbstractIterableAssert} for an {@link Iterable} of
 * {@link ClassNode}s which will use the {@link ClassNodeComparator} to
 * determine the equality.
 *
 * <p>An instance can be created via {@link AsmAssertions#assertThatClasses(Iterable)}.
 *
 * <p>To override the used {@link ClassNodeRepresentation} or
 * {@link ClassNodeComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 */
public class ClassNodesAssert
        extends AsmIterableAssert<ClassNodesAssert, ClassNode, ClassNodeAssert>
        implements IgnoreLineNumbersCapable<ClassNodesAssert> {
  
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  
  /**
   * Initializes an {@link ClassNodesAssert}.
   *
   * @param actual the actual {@link Iterable} of {@link ClassNode}s; may be
   *               null.
   */
  @SuppressWarnings("ResultOfMethodCallIgnored")
  public ClassNodesAssert(Iterable<? extends ClassNode> actual) {
    super(actual, ClassNodesAssert.class, AsmAssertions::assertThat);
    
    as("Classes");
    withRepresentation(ClassNodeRepresentation.INSTANCE);
    setComparators(false);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Exclude {@link LineNumberNode}s (and its associated {@link LabelNode}s)
   * from the comparison.
   *
   * <p>This method will overwrite the previously set {@link Comparator}s.
   *
   * @return {@code this} {@link ClassNodesAssert}; never null.
   */
  @Override
  public ClassNodesAssert ignoreLineNumbers() {
    setComparators(true);
    setElementAssertCreator(classNode -> AsmAssertions.assertThat(classNode).ignoreLineNumbers());

    return this;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  @SuppressWarnings("ResultOfMethodCallIgnored")
  private void setComparators(boolean ignoreLineNumbers) {
    if (!ignoreLineNumbers) {
      usingElementComparator(ClassNodeComparator.INSTANCE);
      usingComparator(ClassNodeComparator.ITERABLE_INSTANCE);
    }
    else {
      usingElementComparator(ClassNodeComparator.INSTANCE_IGNORE_LINE_NUMBERS);
      usingComparator(ClassNodeComparator.ITERABLE_INSTANCE_IGNORE_LINE_NUMBERS);
    }
  }
  
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
