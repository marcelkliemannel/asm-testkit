package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.asmutils.InsnListUtils;
import dev.turingcomplete.asmtestkit.asmutils.TypeUtils;
import dev.turingcomplete.asmtestkit.assertion.option.StandardAssertOption;
import dev.turingcomplete.asmtestkit.common.IgnoreLineNumbersCapable;
import dev.turingcomplete.asmtestkit.comparator.MethodNodeComparator;
import dev.turingcomplete.asmtestkit.node.AccessNode;
import dev.turingcomplete.asmtestkit.node.AnnotationDefaultNode;
import dev.turingcomplete.asmtestkit.representation.MethodNodeRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import static dev.turingcomplete.asmtestkit.asmutils.MethodNodeUtils.extractLabelIndices;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThat;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatAnnotations;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatInstructions;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatLocalVariableAnnotations;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatLocalVariables;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatParameters;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatTryCatchBlocks;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatTypes;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getFromObjectElseNull;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getIfIndexExists;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getListFromObjectElseNull;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.ifNotNull;
import static org.assertj.core.util.Lists.newArrayList;

/**
 * An AssertJ {@link AbstractAssert} for a {@link MethodNodeAssert}.
 *
 * <p>An instance can be created via
 * {@link AsmAssertions#assertThat(MethodNode)}. Use
 * {@link AsmAssertions#assertThatMethods(Iterable)} for multiple
 * {@code MethodNode}s.
 *
 * <p>To override the used {@link MethodNodeRepresentation} or
 * {@link MethodNodeComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 */
public class MethodNodeAssert
        extends ClassEntityAssert<MethodNodeAssert, MethodNode>
        implements IgnoreLineNumbersCapable<MethodNodeAssert> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private boolean ignoreLineNumbers = false;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes an {@link LocalVariableNodeAssert}.
   *
   * @param actual the actual {@link LocalVariableNode}; may be null.
   */
  protected MethodNodeAssert(MethodNode actual) {
    super("Method", actual, MethodNodeAssert.class, MethodNode.class, MethodNodeRepresentation.INSTANCE, MethodNodeComparator.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public MethodNodeAssert ignoreLineNumbers() {
    this.ignoreLineNumbers = true;

    return this;
  }

  @Override
  public MethodNodeAssert isEqualTo(Object expected) {
    super.isEqualTo(expected);

    // Filter line numbers if needed
    MethodNode actualPrepared = actual != null ? filterLineNumbers(actual) : null;
    MethodNode expectedPrepared = expected instanceof MethodNode ? filterLineNumbers((MethodNode) expected) : null;

    // Collect label indices
    // Because of the line number filtering, we may have to overwrite existing 
    // indices here as they may have changed.
    ifNotNull(actualPrepared, nonNullActual -> labelIndexLookup().putAll(extractLabelIndices(nonNullActual)));
    ifNotNull(expectedPrepared, nonNullExpected -> labelIndexLookup().putAll(extractLabelIndices(nonNullExpected)));

    hasEqualDescriptor(actualPrepared, expectedPrepared);
    hasEqualExceptions(actualPrepared, expectedPrepared);
    hasEqualParameters(actualPrepared, expectedPrepared);
    hasEqualVisibleAnnotableParameterCount(actualPrepared, expectedPrepared);
    hasEqualVisibleParameterAnnotations(actualPrepared, expectedPrepared);
    hasEqualInvisibleAnnotableParameterCount(actualPrepared, expectedPrepared);
    hasEqualInvisibleParameterAnnotations(actualPrepared, expectedPrepared);
    hasEqualInstructions(actualPrepared, expectedPrepared);
    hasEqualTryCatchBlocks(actualPrepared, expectedPrepared);
    hasEqualMaxLocals(actualPrepared, expectedPrepared);
    hasEqualMaxStack(actualPrepared, expectedPrepared);
    hasEqualLocalVariables(actualPrepared, expectedPrepared);
    hasEqualVisibleLocalVariableAnnotations(actualPrepared, expectedPrepared);
    hasEqualInvisibleLocalVariableAnnotations(actualPrepared, expectedPrepared);
    hasEqualAnnotationDefault(actualPrepared, expectedPrepared);

    return this;
  }

  /**
   * Checks whether the {@link MethodNode#desc}s are equal.
   *
   * @param actualPrepared   the actual {@link MethodNode} potentially filtered
   *                         line numbers; may be null.
   * @param expectedPrepared the expected {@link MethodNode} potentially filtered
   *                         line numbers; may be null.
   */
  protected void hasEqualDescriptor(MethodNode actualPrepared, MethodNode expectedPrepared) {
    if (hasOption(StandardAssertOption.IGNORE_DESCRIPTOR)) {
      return;
    }

    Function<MethodNode, Type> getDescriptorType = (MethodNode methodNode) -> methodNode.desc != null ? Type.getType(methodNode.desc) : null;
    assertThat(getFromObjectElseNull(actualPrepared, getDescriptorType))
            .addOptions(options)
            .useLabelIndexLookup(labelIndexLookup())
            .as(createCrumbDescription("Has equal method descriptor"))
            .isEqualTo(getFromObjectElseNull(expectedPrepared, MethodNode.class, getDescriptorType));
  }

  /**
   * Checks whether the {@link MethodNode#exceptions}s are equal.
   *
   * @param actualPrepared   an {@link MethodNode}; may be null. an {@link MethodNode}; may be null.
   * @param expectedPrepared the expected {@link MethodNode} potentially filtered
   *                         line numbers; may be null.
   */
  protected void hasEqualExceptions(MethodNode actualPrepared, MethodNode expectedPrepared) {
    if (hasOption(StandardAssertOption.IGNORE_EXCEPTIONS)) {
      return;
    }

    assertThatTypes(getListFromObjectElseNull(actualPrepared, (MethodNode methodNode) -> TypeUtils.namesToTypes(methodNode.exceptions)))
            .as(createCrumbDescription("Has equal exceptions"))
            .useLabelIndexLookup(labelIndexLookup())
            .containsExactlyInAnyOrderElementsOf(getListFromObjectElseNull(expectedPrepared, MethodNode.class, methodNode -> TypeUtils.namesToTypes(methodNode.exceptions)));
  }

  /**
   * Checks whether the {@link MethodNode#parameters}s are equal.
   *
   * @param actualPrepared   an {@link MethodNode}; may be null.
   * @param expectedPrepared the expected {@link MethodNode} potentially filtered
   *                         line numbers; may be null.
   */
  protected void hasEqualParameters(MethodNode actualPrepared, MethodNode expectedPrepared) {
    if (hasOption(StandardAssertOption.IGNORE_PARAMETERS)) {
      return;
    }

    assertThatParameters(getListFromObjectElseNull(actualPrepared, (MethodNode methodNode) -> methodNode.parameters))
            .useLabelIndexLookup(labelIndexLookup())
            .as(createCrumbDescription("Has equal parameters"))
            .containsExactlyInAnyOrderElementsOf(getListFromObjectElseNull(expectedPrepared, MethodNode.class, methodNode -> methodNode.parameters));
  }

  /**
   * Checks whether the {@link MethodNode#visibleAnnotableParameterCount}s are
   * equal.
   *
   * @param actualPrepared   an {@link MethodNode}; may be null.
   * @param expectedPrepared an {@link Object} expected to be a {@link MethodNode}; may
   */
  protected void hasEqualVisibleAnnotableParameterCount(MethodNode actualPrepared, MethodNode expectedPrepared) {
    if (hasOption(StandardAssertOption.IGNORE_VISIBLE_ANNOTABLE_PARAMETER_COUNT)) {
      return;
    }

    Assertions.assertThat(getFromObjectElseNull(actualPrepared, (MethodNode methodNode) -> methodNode.visibleAnnotableParameterCount))
              .as(createCrumbDescription("Has equal visible annotate parameter count"))
              .isEqualTo(getFromObjectElseNull(expectedPrepared, MethodNode.class, methodNode -> methodNode.visibleAnnotableParameterCount));
  }

  /**
   * Checks whether the {@link MethodNode#visibleParameterAnnotations}s are equal.
   *
   * @param actualPrepared   an {@link MethodNode}; may be null.
   * @param expectedPrepared the expected {@link MethodNode} potentially filtered
   *                         line numbers; may be null.
   */
  protected void hasEqualVisibleParameterAnnotations(MethodNode actualPrepared, MethodNode expectedPrepared) {
    if (hasOption(StandardAssertOption.IGNORE_VISIBLE_PARAMETER_ANNOTATIONS)) {
      return;
    }

    List<List<AnnotationNode>> actualAllVisibleParameterAnnotations = getParameterAnnotations(actualPrepared, methodNode -> methodNode.visibleParameterAnnotations);
    List<List<AnnotationNode>> expectedAllVisibleParameterAnnotations = getParameterAnnotations(expectedPrepared, methodNode -> methodNode.visibleParameterAnnotations);

    for (int i = 0; i < Math.max(actualAllVisibleParameterAnnotations.size(), expectedAllVisibleParameterAnnotations.size()); i++) {
      assertThatAnnotations(getIfIndexExists(actualAllVisibleParameterAnnotations, i))
              .useLabelIndexLookup(labelIndexLookup())
              .as(createCrumbDescription("Has equal visible parameter annotations at index " + i))
              .containsExactlyInAnyOrderElementsOf(getIfIndexExists(expectedAllVisibleParameterAnnotations, i));

    }
  }

  /**
   * Checks whether the {@link MethodNode#invisibleAnnotableParameterCount}s are
   * equal.
   *
   * @param actualPrepared   an {@link MethodNode}; may be null.
   * @param expectedPrepared an {@link Object} expected to be a {@link MethodNode}; may
   */
  protected void hasEqualInvisibleAnnotableParameterCount(MethodNode actualPrepared, MethodNode expectedPrepared) {
    if (hasOption(StandardAssertOption.IGNORE_INVISIBLE_ANNOTABLE_PARAMETER_COUNT)) {
      return;
    }

    Assertions.assertThat(getFromObjectElseNull(actualPrepared, (MethodNode methodNode) -> methodNode.invisibleAnnotableParameterCount))
              .as(createCrumbDescription("Has equal invisible annotate parameter count"))
              .isEqualTo(getFromObjectElseNull(expectedPrepared, MethodNode.class, methodNode -> methodNode.invisibleAnnotableParameterCount));
  }

  /**
   * Checks whether the {@link MethodNode#invisibleParameterAnnotations}s are
   * equal.
   *
   * @param actualPrepared   an {@link MethodNode}; may be null.
   * @param expectedPrepared the expected {@link MethodNode} potentially filtered
   *                         line numbers; may be null.
   */
  protected void hasEqualInvisibleParameterAnnotations(MethodNode actualPrepared, MethodNode expectedPrepared) {
    if (hasOption(StandardAssertOption.IGNORE_INVISIBLE_PARAMETER_ANNOTATIONS)) {
      return;
    }

    List<List<AnnotationNode>> actualAllInvisibleParameterAnnotations = getParameterAnnotations(actualPrepared, methodNode -> methodNode.invisibleParameterAnnotations);
    List<List<AnnotationNode>> expectedAllInvisibleParameterAnnotations = getParameterAnnotations(expectedPrepared, methodNode -> methodNode.invisibleParameterAnnotations);

    for (int i = 0; i < Math.max(actualAllInvisibleParameterAnnotations.size(), expectedAllInvisibleParameterAnnotations.size()); i++) {
      assertThatAnnotations(getIfIndexExists(actualAllInvisibleParameterAnnotations, i))
              .useLabelIndexLookup(labelIndexLookup())
              .as(createCrumbDescription("Has equal invisible parameter annotations at index " + i))
              .containsExactlyInAnyOrderElementsOf(getIfIndexExists(expectedAllInvisibleParameterAnnotations, i));

    }
  }

  /**
   * Checks whether the {@link MethodNode#instructions}s are equal.
   *
   * @param actualPrepared   an {@link MethodNode}; may be null.
   * @param expectedPrepared the expected {@link MethodNode} potentially filtered
   *                         line numbers; may be null.
   */
  protected void hasEqualInstructions(MethodNode actualPrepared, MethodNode expectedPrepared) {
    if (hasOption(StandardAssertOption.IGNORE_INSTRUCTIONS)) {
      return;
    }

    InsnListAssert insnListAssert = assertThatInstructions(getFromObjectElseNull(actualPrepared, MethodNode.class, (MethodNode methodNode) -> methodNode.instructions))
            .useLabelIndexLookup(labelIndexLookup())
            .as(createCrumbDescription("Has equal instructions"));

    if (ignoreLineNumbers) {
      insnListAssert.ignoreLineNumbers();
    }

    insnListAssert.isEqualTo(getFromObjectElseNull(expectedPrepared, MethodNode.class, (MethodNode methodNode) -> methodNode.instructions));
  }

  /**
   * Checks whether the {@link MethodNode#tryCatchBlocks}s are equal.
   *
   * @param actualPrepared   an {@link MethodNode}; may be null.
   * @param expectedPrepared the expected {@link MethodNode} potentially filtered
   *                         line numbers; may be null.
   */
  protected void hasEqualTryCatchBlocks(MethodNode actualPrepared, MethodNode expectedPrepared) {
    if (hasOption(StandardAssertOption.IGNORE_TRY_CATCH_BLOCKS)) {
      return;
    }

    assertThatTryCatchBlocks(getListFromObjectElseNull(actualPrepared, (MethodNode methodNode) -> methodNode.tryCatchBlocks))
            .useLabelIndexLookup(labelIndexLookup())
            .as(createCrumbDescription("Has equal try-catch blocks"))
            .containsExactlyInAnyOrderElementsOf(getListFromObjectElseNull(expectedPrepared, MethodNode.class, (MethodNode methodNode) -> methodNode.tryCatchBlocks));
  }

  /**
   * Checks whether the {@link MethodNode#maxStack}s are equal.
   *
   * @param actualPrepared   an {@link MethodNode}; may be null.
   * @param expectedPrepared an {@link Object} expected to be a {@link MethodNode}; may
   */
  protected void hasEqualMaxStack(MethodNode actualPrepared, MethodNode expectedPrepared) {
    if (hasOption(StandardAssertOption.IGNORE_MAX_STACK)) {
      return;
    }

    Assertions.assertThat(getFromObjectElseNull(actualPrepared, (MethodNode methodNode) -> methodNode.maxStack))
              .as(createCrumbDescription("Has equal max stack"))
              .isEqualTo(getFromObjectElseNull(expectedPrepared, MethodNode.class, (MethodNode methodNode) -> methodNode.maxStack));
  }

  /**
   * Checks whether the {@link MethodNode#maxLocals}s are equal.
   *
   * @param actualPrepared   an {@link MethodNode}; may be null.
   * @param expectedPrepared an {@link Object} expected to be a {@link MethodNode}; may
   */
  protected void hasEqualMaxLocals(MethodNode actualPrepared, MethodNode expectedPrepared) {
    if (hasOption(StandardAssertOption.IGNORE_MAX_LOCALS)) {
      return;
    }

    Assertions.assertThat(getFromObjectElseNull(actualPrepared, (MethodNode methodNode) -> methodNode.maxLocals))
              .as(createCrumbDescription("Has equal max locals"))
              .isEqualTo(getFromObjectElseNull(expectedPrepared, MethodNode.class, (MethodNode methodNode) -> methodNode.maxLocals));
  }

  /**
   * Checks whether the {@link MethodNode#localVariables}s are equal.
   *
   * @param actualPrepared   an {@link MethodNode}; may be null.
   * @param expectedPrepared the expected {@link MethodNode} potentially filtered
   *                         line numbers; may be null.
   */
  protected void hasEqualLocalVariables(MethodNode actualPrepared, MethodNode expectedPrepared) {
    if (hasOption(StandardAssertOption.IGNORE_LOCAL_VARIABLES)) {
      return;
    }

    assertThatLocalVariables(getListFromObjectElseNull(actualPrepared, (MethodNode methodNode) -> methodNode.localVariables))
            .useLabelIndexLookup(labelIndexLookup())
            .as(createCrumbDescription("Has equal local variables"))
            .containsExactlyInAnyOrderElementsOf(getListFromObjectElseNull(expectedPrepared, MethodNode.class, (MethodNode methodNode) -> methodNode.localVariables));
  }

  /**
   * Checks whether the {@link MethodNode#visibleLocalVariableAnnotations}s are equal.
   *
   * @param actualPrepared   an {@link MethodNode}; may be null.
   * @param expectedPrepared the expected {@link MethodNode} potentially filtered
   *                         line numbers; may be null.
   */
  protected void hasEqualVisibleLocalVariableAnnotations(MethodNode actualPrepared, MethodNode expectedPrepared) {
    if (hasOption(StandardAssertOption.IGNORE_VISIBLE_LOCAL_VARIABLE_ANNOTATIONS)) {
      return;
    }

    assertThatLocalVariableAnnotations(getListFromObjectElseNull(actualPrepared, (MethodNode methodNode) -> methodNode.visibleLocalVariableAnnotations))
            .useLabelIndexLookup(labelIndexLookup())
            .as(createCrumbDescription("Has equal visible local variable annotations"))
            .containsExactlyInAnyOrderElementsOf(getListFromObjectElseNull(expectedPrepared, MethodNode.class, (MethodNode methodNode) -> methodNode.visibleLocalVariableAnnotations));
  }

  /**
   * Checks whether the {@link MethodNode#invisibleLocalVariableAnnotations}s are equal.
   *
   * @param actualPrepared   an {@link MethodNode}; may be null.
   * @param expectedPrepared the expected {@link MethodNode} potentially filtered
   *                         line numbers; may be null.
   */
  protected void hasEqualInvisibleLocalVariableAnnotations(MethodNode actualPrepared, MethodNode expectedPrepared) {
    if (hasOption(StandardAssertOption.IGNORE_INVISIBLE_LOCAL_VARIABLE_ANNOTATIONS)) {
      return;
    }

    assertThatLocalVariableAnnotations(getListFromObjectElseNull(actualPrepared, (MethodNode methodNode) -> methodNode.invisibleLocalVariableAnnotations))
            .useLabelIndexLookup(labelIndexLookup())
            .as(createCrumbDescription("Has equal invisible local variable annotations"))
            .containsExactlyInAnyOrderElementsOf(getListFromObjectElseNull(expectedPrepared, MethodNode.class, (MethodNode methodNode) -> methodNode.invisibleLocalVariableAnnotations));
  }

  /**
   * Checks whether the {@link MethodNode#annotationDefault}s are equal.
   *
   * @param actualPrepared   an {@link MethodNode}; may be null.
   * @param expectedPrepared the expected {@link MethodNode} potentially filtered
   *                         line numbers; may be null.
   */
  protected void hasEqualAnnotationDefault(MethodNode actualPrepared, MethodNode expectedPrepared) {
    if (hasOption(StandardAssertOption.IGNORE_ANNOTATION_DEFAULT)) {
      return;
    }

    AsmAssertions.assertThat(getFromObjectElseNull(actualPrepared, (MethodNode methodNode) -> AnnotationDefaultNode.createOrNull(methodNode.annotationDefault)))
                 .addOptions(options)
                 .useLabelIndexLookup(labelIndexLookup())
                 .as(createCrumbDescription("Has equal annotation default"))
                 .isEqualTo(getFromObjectElseNull(expectedPrepared, MethodNode.class, (MethodNode methodNode) -> AnnotationDefaultNode.createOrNull(methodNode.annotationDefault)));
  }

  @Override
  protected String getName(MethodNode methodNode) {
    return methodNode.name;
  }

  @Override
  protected AccessNode getAccessNode(MethodNode methodNode) {
    return AccessNode.forMethod(methodNode.access);
  }

  @Override
  protected String getSignature(MethodNode methodNode) {
    return methodNode.signature;
  }

  @Override
  protected List<AnnotationNode> getVisibleAnnotations(MethodNode methodNode) {
    return methodNode.visibleAnnotations;
  }

  @Override
  protected List<AnnotationNode> getInvisibleAnnotations(MethodNode methodNode) {
    return methodNode.invisibleAnnotations;
  }

  @Override
  protected List<TypeAnnotationNode> getVisibleTypeAnnotations(MethodNode methodNode) {
    return methodNode.visibleTypeAnnotations;
  }

  @Override
  protected List<TypeAnnotationNode> getInvisibleTypeAnnotations(MethodNode methodNode) {
    return methodNode.invisibleTypeAnnotations;
  }

  @Override
  protected List<Attribute> getAttributes(MethodNode methodNode) {
    return methodNode.attrs;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private List<List<AnnotationNode>> getParameterAnnotations(Object object, Function<MethodNode, List<AnnotationNode>[]> annotationsProvider) {
    List<List<AnnotationNode>> actualVisibleParameterAnnotations = newArrayList(getFromObjectElseNull(object, MethodNode.class, annotationsProvider));
    return actualVisibleParameterAnnotations != null ? actualVisibleParameterAnnotations : List.of();
  }

  private MethodNode filterLineNumbers(MethodNode methodNode) {
    if (ignoreLineNumbers) {
      return InsnListUtils.copyWithFilteredLineNumbers(methodNode);
    }
    else {
      return methodNode;
    }
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
