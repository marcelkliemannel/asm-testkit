package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.asmutils.InsnListUtils;
import dev.turingcomplete.asmtestkit.asmutils.MethodNodeUtils;
import dev.turingcomplete.asmtestkit.assertion.option.StandardAssertOption;
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
import java.util.Objects;
import java.util.function.Function;

import static dev.turingcomplete.asmtestkit.asmutils.MethodNodeUtils.extractLabelIndices;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThat;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatAnnotations;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatInstructions;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatLocalVariableAnnotations;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatLocalVariables;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatParameters;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatTryCatchBlocks;
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
public class MethodNodeAssert extends ClassEntityAssert<MethodNodeAssert, MethodNode> {
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

  public MethodNodeAssert ignoreLineNumbers() {
    this.ignoreLineNumbers = true;

    return this;
  }

  @Override
  public MethodNodeAssert isEqualTo(Object expected) {
    super.isEqualTo(expected);

    LabelIndexLookup labelIndexLookup = buildLabelIndexLookup(expected);

    hasEqualDescriptor(expected, labelIndexLookup);
    hasEqualExceptions(expected);
    hasEqualParameters(expected, labelIndexLookup);
    hasEqualVisibleAnnotableParameterCount(expected);
    hasEqualVisibleParameterAnnotations(expected, labelIndexLookup);
    hasEqualInvisibleAnnotableParameterCount(expected);
    hasEqualInvisibleParameterAnnotations(expected, labelIndexLookup);
    hasEqualInstructions(expected, labelIndexLookup);
    hasEqualTryCatchBlocks(expected, labelIndexLookup);
    hasEqualMaxLocals(expected);
    hasEqualMaxStack(expected);
    hasEqualLocalVariables(expected, labelIndexLookup);
    hasEqualVisibleLocalVariableAnnotations(expected, labelIndexLookup);
    hasEqualInvisibleLocalVariableAnnotations(expected, labelIndexLookup);
    hasEqualAnnotationDefault(expected, labelIndexLookup);

    return this;
  }

  /**
   * Checks whether the {@link MethodNode#desc}s are equal.
   *
   * @param expected an {@link Object} expected to be a {@link MethodNode}; may
   *                 be null.
   * @param labelIndexLookup a {@link LabelIndexLookup}; never null.
   */
  protected void hasEqualDescriptor(Object expected, LabelIndexLookup labelIndexLookup) {
    Objects.requireNonNull(labelIndexLookup);

    if (hasOption(StandardAssertOption.IGNORE_DESCRIPTOR)) {
      return;
    }

    assertThat(getFromObjectElseNull(actual, (MethodNode methodNode) -> Type.getType(methodNode.desc)))
            .addOptions(options)
            .useLabelIndexLookup(labelIndexLookup)
            .as(createCrumbDescription("Has equal method descriptor"))
            .isEqualTo(getFromObjectElseNull(expected, MethodNode.class, methodNode -> Type.getType(methodNode.desc)));
  }

  /**
   * Checks whether the {@link MethodNode#exceptions}s are equal.
   *
   * @param expected an {@link Object} expected to be a {@link MethodNode}; may
   *                 be null.
   */
  protected void hasEqualExceptions(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_EXCEPTIONS)) {
      return;
    }

    Assertions.assertThat(getListFromObjectElseNull(actual, (MethodNode methodNode) -> methodNode.exceptions))
              .as(createCrumbDescription("Has equal exceptions"))
              .isEqualTo(getFromObjectElseNull(expected, MethodNode.class, methodNode -> methodNode.exceptions));
  }

  /**
   * Checks whether the {@link MethodNode#parameters}s are equal.
   *
   * @param expected an {@link Object} expected to be a {@link MethodNode}; may
   *                 be null.
   * @param labelIndexLookup a {@link LabelIndexLookup}; never null.
   */
  protected void hasEqualParameters(Object expected, LabelIndexLookup labelIndexLookup) {
    Objects.requireNonNull(labelIndexLookup);

    if (hasOption(StandardAssertOption.IGNORE_PARAMETERS)) {
      return;
    }

    assertThatParameters(getListFromObjectElseNull(actual, (MethodNode methodNode) -> methodNode.parameters))
            .useLabelNameLookup(labelIndexLookup)
            .as(createCrumbDescription("Has equal parameters"))
            .containsExactlyInAnyOrderElementsOf(getListFromObjectElseNull(expected, MethodNode.class, methodNode -> methodNode.parameters));
  }

  /**
   * Checks whether the {@link MethodNode#visibleAnnotableParameterCount}s are
   * equal.
   *
   * @param expected an {@link Object} expected to be a {@link MethodNode}; may
   *                 be null.
   */
  protected void hasEqualVisibleAnnotableParameterCount(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_VISIBLE_ANNOTABLE_PARAMETER_COUNT)) {
      return;
    }

    Assertions.assertThat(getFromObjectElseNull(actual, (MethodNode methodNode) -> methodNode.visibleAnnotableParameterCount))
              .as(createCrumbDescription("Has equal visible annotate parameter count"))
              .isEqualTo(getFromObjectElseNull(expected, MethodNode.class, methodNode -> methodNode.visibleAnnotableParameterCount));
  }

  /**
   * Checks whether the {@link MethodNode#visibleParameterAnnotations}s are equal.
   *
   * @param expected an {@link Object} expected to be a {@link MethodNode}; may
   *                 be null.
   * @param labelIndexLookup a {@link LabelIndexLookup}; never null.
   */
  protected void hasEqualVisibleParameterAnnotations(Object expected, LabelIndexLookup labelIndexLookup) {
    Objects.requireNonNull(labelIndexLookup);


    if (hasOption(StandardAssertOption.IGNORE_VISISBLE_PARAMETER_ANNOTATIONS)) {
      return;
    }

    List<List<AnnotationNode>> actualAllVisibleParameterAnnotations = getParameterAnnotations(actual, methodNode -> methodNode.visibleParameterAnnotations);
    List<List<AnnotationNode>> expectedAllVisibleParameterAnnotations = getParameterAnnotations(expected, methodNode -> methodNode.visibleParameterAnnotations);

    for (int i = 0; i < Math.max(actualAllVisibleParameterAnnotations.size(), expectedAllVisibleParameterAnnotations.size()); i++) {
      assertThatAnnotations(getIfIndexExists(actualAllVisibleParameterAnnotations, i))
              .useLabelNameLookup(labelIndexLookup)
              .as(createCrumbDescription("Has equal visible parameter annotations at index " + i))
              .containsExactlyInAnyOrderElementsOf(getIfIndexExists(expectedAllVisibleParameterAnnotations, i));

    }
  }

  /**
   * Checks whether the {@link MethodNode#invisibleAnnotableParameterCount}s are
   * equal.
   *
   * @param expected an {@link Object} expected to be a {@link MethodNode}; may
   *                 be null.
   */
  protected void hasEqualInvisibleAnnotableParameterCount(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_INVISISBLE_ANNOTABLE_PARAMETER_COUNT)) {
      return;
    }

    Assertions.assertThat(getFromObjectElseNull(actual, (MethodNode methodNode) -> methodNode.invisibleAnnotableParameterCount))
              .as(createCrumbDescription("Has equal invisible annotate parameter count"))
              .isEqualTo(getFromObjectElseNull(expected, MethodNode.class, methodNode -> methodNode.invisibleAnnotableParameterCount));
  }

  /**
   * Checks whether the {@link MethodNode#invisibleParameterAnnotations}s are
   * equal.
   *
   * @param expected an {@link Object} expected to be a {@link MethodNode}; may
   *                 be null.
   * @param labelIndexLookup a {@link LabelIndexLookup}; never null.
   */
  protected void hasEqualInvisibleParameterAnnotations(Object expected, LabelIndexLookup labelIndexLookup) {
    Objects.requireNonNull(labelIndexLookup);

    if (hasOption(StandardAssertOption.IGNORE_INVISIBLE_PARAMETER_ANNOTATIONS)) {
      return;
    }

    List<List<AnnotationNode>> actualAllInvisibleParameterAnnotations = getParameterAnnotations(actual, methodNode -> methodNode.invisibleParameterAnnotations);
    List<List<AnnotationNode>> expectedAllInvisibleParameterAnnotations = getParameterAnnotations(expected, methodNode -> methodNode.invisibleParameterAnnotations);

    for (int i = 0; i < Math.max(actualAllInvisibleParameterAnnotations.size(), expectedAllInvisibleParameterAnnotations.size()); i++) {
      assertThatAnnotations(getIfIndexExists(actualAllInvisibleParameterAnnotations, i))
              .useLabelNameLookup(labelIndexLookup)
              .as(createCrumbDescription("Has equal invisible parameter annotations at index " + i))
              .containsExactlyInAnyOrderElementsOf(getIfIndexExists(expectedAllInvisibleParameterAnnotations, i));

    }
  }

  /**
   * Checks whether the {@link MethodNode#instructions}s are equal.
   *
   * @param expected an {@link Object} expected to be a {@link MethodNode}; may
   *                 be null.
   * @param labelIndexLookup a {@link LabelIndexLookup}; never null.
   */
  protected void hasEqualInstructions(Object expected, LabelIndexLookup labelIndexLookup) {
    Objects.requireNonNull(labelIndexLookup);

    if (hasOption(StandardAssertOption.IGNORE_INSTRUCTIONS)) {
      return;
    }

    InsnListAssert insnListAssert = assertThatInstructions(getFromObjectElseNull(actual, MethodNode.class, (MethodNode methodNode) -> methodNode.instructions))
            .useLabelNameLookup(labelIndexLookup)
            .as(createCrumbDescription("Has equal instructions"));

    if (ignoreLineNumbers) {
      insnListAssert.ignoreLineNumbers();
    }

    insnListAssert.isEqualTo(getFromObjectElseNull(expected, MethodNode.class, (MethodNode methodNode) -> methodNode.instructions));
  }

  /**
   * Checks whether the {@link MethodNode#tryCatchBlocks}s are equal.
   *
   * @param expected an {@link Object} expected to be a {@link MethodNode}; may
   *                 be null.
   * @param labelIndexLookup a {@link LabelIndexLookup}; never null.
   */
  protected void hasEqualTryCatchBlocks(Object expected, LabelIndexLookup labelIndexLookup) {
    Objects.requireNonNull(labelIndexLookup);

    if (hasOption(StandardAssertOption.IGNORE_TRY_CATCH_BLOCKS)) {
      return;
    }

    assertThatTryCatchBlocks(getListFromObjectElseNull(actual, (MethodNode methodNode) -> methodNode.tryCatchBlocks))
            .useLabelNameLookup(labelIndexLookup)
            .as(createCrumbDescription("Has equal try-catch blocks"))
            .containsExactlyInAnyOrderElementsOf(getListFromObjectElseNull(expected, MethodNode.class, (MethodNode methodNode) -> methodNode.tryCatchBlocks));
  }

  /**
   * Checks whether the {@link MethodNode#maxStack}s are equal.
   *
   * @param expected an {@link Object} expected to be a {@link MethodNode}; may
   *                 be null.
   */
  protected void hasEqualMaxStack(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_MAX_STACK)) {
      return;
    }

    Assertions.assertThat(getFromObjectElseNull(actual, (MethodNode methodNode) -> methodNode.maxStack))
              .as(createCrumbDescription("Has equal max stack"))
              .isEqualTo(getFromObjectElseNull(expected, MethodNode.class, (MethodNode methodNode) -> methodNode.maxStack));
  }

  /**
   * Checks whether the {@link MethodNode#maxLocals}s are equal.
   *
   * @param expected an {@link Object} expected to be a {@link MethodNode}; may
   *                 be null.
   */
  protected void hasEqualMaxLocals(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_MAX_LOCALS)) {
      return;
    }

    Assertions.assertThat(getFromObjectElseNull(actual, (MethodNode methodNode) -> methodNode.maxLocals))
              .as(createCrumbDescription("Has equal max locals"))
              .isEqualTo(getFromObjectElseNull(expected, MethodNode.class, (MethodNode methodNode) -> methodNode.maxLocals));
  }

  /**
   * Checks whether the {@link MethodNode#localVariables}s are equal.
   *
   * @param expected an {@link Object} expected to be a {@link MethodNode}; may
   *                 be null.
   * @param labelIndexLookup a {@link LabelIndexLookup}; never null.
   */
  protected void hasEqualLocalVariables(Object expected, LabelIndexLookup labelIndexLookup) {
    Objects.requireNonNull(labelIndexLookup);

    if (hasOption(StandardAssertOption.IGNORE_LOCAL_VARIABLES)) {
      return;
    }

    assertThatLocalVariables(getListFromObjectElseNull(actual, (MethodNode methodNode) -> methodNode.localVariables))
            .useLabelNameLookup(labelIndexLookup)
            .as(createCrumbDescription("Has equal local variables"))
            .containsExactlyInAnyOrderElementsOf(getListFromObjectElseNull(expected, MethodNode.class, (MethodNode methodNode) -> methodNode.localVariables));
  }

  /**
   * Checks whether the {@link MethodNode#visibleLocalVariableAnnotations}s are equal.
   *
   * @param expected an {@link Object} expected to be a {@link MethodNode}; may
   *                 be null.
   * @param labelIndexLookup a {@link LabelIndexLookup}; never null.
   */
  protected void hasEqualVisibleLocalVariableAnnotations(Object expected, LabelIndexLookup labelIndexLookup) {
    Objects.requireNonNull(labelIndexLookup);

    if (hasOption(StandardAssertOption.IGNORE_VISISBLE_LOCAL_VARIABLE_ANNOTATIONS)) {
      return;
    }

    assertThatLocalVariableAnnotations(getListFromObjectElseNull(actual, (MethodNode methodNode) -> methodNode.visibleLocalVariableAnnotations))
            .useLabelNameLookup(labelIndexLookup)
            .as(createCrumbDescription("Has equal visible local variable annotations"))
            .containsExactlyInAnyOrderElementsOf(getListFromObjectElseNull(expected, MethodNode.class, (MethodNode methodNode) -> methodNode.visibleLocalVariableAnnotations));
  }

  /**
   * Checks whether the {@link MethodNode#invisibleLocalVariableAnnotations}s are equal.
   *
   * @param expected an {@link Object} expected to be a {@link MethodNode}; may
   *                 be null.
   * @param labelIndexLookup a {@link LabelIndexLookup}; never null.
   */
  protected void hasEqualInvisibleLocalVariableAnnotations(Object expected, LabelIndexLookup labelIndexLookup) {
    Objects.requireNonNull(labelIndexLookup);

    if (hasOption(StandardAssertOption.IGNORE_INVISISBLE_LOCAL_VARIABLE_ANNOTATIONS)) {
      return;
    }

    assertThatLocalVariableAnnotations(getListFromObjectElseNull(actual, (MethodNode methodNode) -> methodNode.invisibleLocalVariableAnnotations))
            .useLabelNameLookup(labelIndexLookup)
            .as(createCrumbDescription("Has equal invisible local variable annotations"))
            .containsExactlyInAnyOrderElementsOf(getListFromObjectElseNull(expected, MethodNode.class, (MethodNode methodNode) -> methodNode.invisibleLocalVariableAnnotations));
  }

  /**
   * Checks whether the {@link MethodNode#annotationDefault}s are equal.
   *
   * @param expected an {@link Object} expected to be a {@link MethodNode}; may
   *                 be null.
   * @param labelIndexLookup a {@link LabelIndexLookup}; never null.
   */
  protected void hasEqualAnnotationDefault(Object expected, LabelIndexLookup labelIndexLookup) {
    Objects.requireNonNull(labelIndexLookup);

    if (hasOption(StandardAssertOption.IGNORE_ANNOTATION_DEFAULT)) {
      return;
    }

    AsmAssertions.assertThat(getFromObjectElseNull(actual, (MethodNode methodNode) -> AnnotationDefaultNode.createOrNull(methodNode.annotationDefault)))
                 .addOptions(options)
                 .useLabelIndexLookup(labelIndexLookup)
                 .as(createCrumbDescription("Has equal annotation default"))
                 .isEqualTo(getFromObjectElseNull(expected, MethodNode.class, (MethodNode methodNode) -> AnnotationDefaultNode.createOrNull(methodNode.annotationDefault)));
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

  private LabelIndexLookup buildLabelIndexLookup(Object expected) {
    LabelIndexLookup labelIndexLookup = DefaultLabelIndexLookup.create();

    labelIndexLookup.add(labelNameLookup());

    ifNotNull(actual, nonNullActual -> labelIndexLookup.putAll(extractLabelIndices(filterLineNumbers(nonNullActual))));
    ifNotNull(expected, nonNullExpected -> {
      if (nonNullExpected instanceof MethodNode) {
        labelIndexLookup.putAll(extractLabelIndices(filterLineNumbers((MethodNode) nonNullExpected)));
      }
    });

    return labelIndexLookup;
  }

  private MethodNode filterLineNumbers(MethodNode nonNullActual) {
    if (ignoreLineNumbers) {
      MethodNode copyOfMethodNode = MethodNodeUtils.copy(nonNullActual);
      copyOfMethodNode.instructions = InsnListUtils.filterLineNumbers(nonNullActual);
      return copyOfMethodNode;
    }
    else {
      return nonNullActual;
    }
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
