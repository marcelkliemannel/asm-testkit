package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.asmutils.TypeUtils;
import dev.turingcomplete.asmtestkit.assertion.option.StandardAssertOption;
import dev.turingcomplete.asmtestkit.common.IgnoreLineNumbersCapable;
import dev.turingcomplete.asmtestkit.comparator.ClassNodeComparator;
import dev.turingcomplete.asmtestkit.node.AccessNode;
import dev.turingcomplete.asmtestkit.representation.AsmRepresentation;
import dev.turingcomplete.asmtestkit.representation.ClassNodeRepresentation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.presentation.Representation;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThat;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatFields;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatInnerClasses;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatMethods;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatTypes;
import static dev.turingcomplete.asmtestkit.assertion.ClassNodeAssert.MethodsComparisonMode.FULL;
import static dev.turingcomplete.asmtestkit.assertion.ClassNodeAssert.MethodsComparisonMode.ONE_BY_ONE;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getFromObjectElseNull;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getIntegerFromObjectElseNull;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getListFromObjectElseNull;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getStringFromObjectElseNull;

/**
 * An AssertJ {@link AbstractAssert} for a {@link ClassNode}.
 *
 * <p>An instance can be created via {@link AsmAssertions#assertThat(ClassNode)}.
 * Use {@link AsmAssertions#assertThatClasses(Iterable)} for multiple
 * {@code ClassNode}s.
 *
 * <p>To override the used {@link ClassNodeRepresentation} or
 * {@link ClassNodeComparator} call {@link #withRepresentation(Representation)}
 * or {@link #usingComparator(Comparator)}.
 */
public class ClassNodeAssert
        extends ClassEntityAssert<ClassNodeAssert, ClassNode>
        implements IgnoreLineNumbersCapable<ClassNodeAssert> {

  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  protected MethodsComparisonMode methodsComparisonMode = ONE_BY_ONE;

  private boolean ignoreLineNumbers = false;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected ClassNodeAssert(ClassNode actual) {
    super("Class", actual, ClassNodeAssert.class, ClassNode.class, ClassNodeRepresentation.INSTANCE, ClassNodeComparator.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public ClassNodeAssert isEqualTo(Object expected) {
    super.isEqualTo(expected);

    hasEqualVersion(expected);
    hasEqualSuperName(expected);
    hasEqualInterfaces(expected);
    hasEqualSourceFile(expected);
    hasEqualSourceDebug(expected);
    hasEqualOuterClass(expected);
    hasEqualOuterMethod(expected);
    hasEqualOuterMethodDescriptor(expected);
    hasEqualInnerClasses(expected);
    hasEqualNestHostClass(expected);
    hasEqualNestMembers(expected);
    hasEqualPermittedSubclasses(expected);
    hasEqualFields(expected);
    hasEqualMethods(expected);

    return this;
  }

  /**
   * Checks whether the {@link ClassNode#interfaces} are equal.
   *
   * @param expected an {@link Object} expected to be a {@link ClassNode}; may
   *                 be null.
   */
  protected void hasEqualVersion(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_VERSION)) {
      return;
    }

    Assertions.assertThat(getIntegerFromObjectElseNull(actual, (ClassNode classNode) -> classNode.version))
              .as(createCrumbDescription("Has equal version"))
              .isEqualTo(getIntegerFromObjectElseNull(expected, ClassNode.class, (ClassNode classNode) -> classNode.version));
  }

  /**
   * Checks whether the {@link ClassNode#superName} are equal.
   *
   * @param expected an {@link Object} expected to be a {@link ClassNode}; may
   *                 be null.
   */
  protected void hasEqualSuperName(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_SUPER_NAME)) {
      return;
    }

    assertThat(getFromObjectElseNull(actual, (ClassNode classNode) -> TypeUtils.nameToTypeElseNull(classNode.superName)))
            .as(createCrumbDescription("Has equal super name"))
            .addOptions(options)
            .isEqualTo(getFromObjectElseNull(expected, ClassNode.class, (ClassNode classNode) -> TypeUtils.nameToTypeElseNull(classNode.superName)));
  }

  /**
   * Checks whether the {@link ClassNode#interfaces} are equal.
   *
   * @param expected an {@link Object} expected to be a {@link ClassNode}; may
   *                 be null.
   */
  protected void hasEqualInterfaces(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_INTERFACES)) {
      return;
    }

    Function<ClassNode, List<Type>> getTypesOfInterfaces = (ClassNode classNode) -> classNode.interfaces.stream().map(TypeUtils::nameToTypeElseNull).collect(Collectors.toList());
    assertThatTypes(getListFromObjectElseNull(actual, getTypesOfInterfaces))
            .as(createCrumbDescription("Has equal interfaces"))
            .addOptions(options)
            .containsExactlyInAnyOrderElementsOf(getFromObjectElseNull(expected, ClassNode.class, getTypesOfInterfaces));
  }

  /**
   * Checks whether the {@link ClassNode#sourceFile} are equal.
   *
   * @param expected an {@link Object} expected to be a {@link ClassNode}; may
   *                 be null.
   */
  protected void hasEqualSourceFile(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_SOURCE_FILE)) {
      return;
    }

    Assertions.assertThat(getStringFromObjectElseNull(actual, (ClassNode classNode) -> classNode.sourceFile))
              .as(createCrumbDescription("Has equal source file"))
              .isEqualTo(getStringFromObjectElseNull(expected, ClassNode.class, (ClassNode classNode) -> classNode.sourceFile));
  }

  /**
   * Checks whether the {@link ClassNode#sourceDebug} are equal.
   *
   * @param expected an {@link Object} expected to be a {@link ClassNode}; may
   *                 be null.
   */
  protected void hasEqualSourceDebug(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_SOURCE_DEBUG)) {
      return;
    }

    Assertions.assertThat(getStringFromObjectElseNull(actual, (ClassNode classNode) -> classNode.sourceDebug))
              .as(createCrumbDescription("Has equal source debug"))
              .isEqualTo(getStringFromObjectElseNull(expected, ClassNode.class, (ClassNode classNode) -> classNode.sourceDebug));
  }

  /**
   * Checks whether the {@link ClassNode#outerClass} are equal.
   *
   * @param expected an {@link Object} expected to be a {@link ClassNode}; may
   *                 be null.
   */
  protected void hasEqualOuterClass(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_OUTER_CLASS)) {
      return;
    }

    assertThat(getFromObjectElseNull(actual, (ClassNode classNode) -> TypeUtils.nameToTypeElseNull(classNode.outerClass)))
            .as(createCrumbDescription("Has equal outer class"))
            .addOptions(options)
            .isEqualTo(getFromObjectElseNull(expected, ClassNode.class, (ClassNode classNode) -> TypeUtils.nameToTypeElseNull(classNode.outerClass)));
  }

  /**
   * Checks whether the {@link ClassNode#outerMethod} are equal.
   *
   * @param expected an {@link Object} expected to be a {@link ClassNode}; may
   *                 be null.
   */
  protected void hasEqualOuterMethod(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_OUTER_METHOD)) {
      return;
    }

    Assertions.assertThat(getStringFromObjectElseNull(actual, (ClassNode classNode) -> classNode.outerMethod))
              .as(createCrumbDescription("Has equal outer method"))
              .isEqualTo(getStringFromObjectElseNull(expected, ClassNode.class, (ClassNode classNode) -> classNode.outerMethod));
  }

  /**
   * Checks whether the {@link ClassNode#outerMethodDesc} are equal.
   *
   * @param expected an {@link Object} expected to be a {@link ClassNode}; may
   *                 be null.
   */
  protected void hasEqualOuterMethodDescriptor(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_OUTER_METHOD_DESCRIPTOR)) {
      return;
    }

    Assertions.assertThat(getStringFromObjectElseNull(actual, (ClassNode classNode) -> classNode.outerMethodDesc))
              .as(createCrumbDescription("Has equal outer method descriptor"))
              .isEqualTo(getStringFromObjectElseNull(expected, ClassNode.class, (ClassNode classNode) -> classNode.outerMethodDesc));
  }

  /**
   * Checks whether the {@link ClassNode#innerClasses} are equal.
   *
   * @param expected an {@link Object} expected to be a {@link ClassNode}; may
   *                 be null.
   */
  protected void hasEqualInnerClasses(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_INNER_CLASSES)) {
      return;
    }

    assertThatInnerClasses(getListFromObjectElseNull(actual, (ClassNode classNode) -> classNode.innerClasses))
            .as(createCrumbDescription("Has equal outer class"))
            .addOptions(options)
            .containsExactlyInAnyOrderElementsOf(getListFromObjectElseNull(expected, ClassNode.class, (ClassNode classNode) -> classNode.innerClasses));
  }

  /**
   * Checks whether the {@link ClassNode#nestHostClass} are equal.
   *
   * @param expected an {@link Object} expected to be a {@link ClassNode}; may
   *                 be null.
   */
  protected void hasEqualNestHostClass(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_NEST_HOST_CLASS)) {
      return;
    }

    assertThat(getFromObjectElseNull(actual, (ClassNode classNode) -> TypeUtils.nameToTypeElseNull(classNode.nestHostClass)))
            .as(createCrumbDescription("Has equal nest host class"))
            .addOptions(options)
            .isEqualTo(getFromObjectElseNull(expected, ClassNode.class, (ClassNode classNode) -> TypeUtils.nameToTypeElseNull(classNode.nestHostClass)));
  }

  /**
   * Checks whether the {@link ClassNode#nestHostClass} are equal.
   *
   * @param expected an {@link Object} expected to be a {@link ClassNode}; may
   *                 be null.
   */
  protected void hasEqualNestMembers(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_NEST_MEMBERS)) {
      return;
    }

    assertThatTypes(getListFromObjectElseNull(actual, classNode -> TypeUtils.namesToTypes(classNode.nestMembers)))
            .as(createCrumbDescription("Has equal nest members"))
            .addOptions(options)
            .containsExactlyInAnyOrderElementsOf(getListFromObjectElseNull(expected, ClassNode.class, classNode -> TypeUtils.namesToTypes(classNode.nestMembers)));
  }

  /**
   * Checks whether the {@link ClassNode#permittedSubclasses} are equal.
   *
   * @param expected an {@link Object} expected to be a {@link ClassNode}; may
   *                 be null.
   */
  protected void hasEqualPermittedSubclasses(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_PERMITTED_SUBCLASSES)) {
      return;
    }

    assertThatTypes(getListFromObjectElseNull(actual, classNode -> TypeUtils.namesToTypes(classNode.permittedSubclasses)))
            .as(createCrumbDescription("Has equal permitted subclasses"))
            .addOptions(options)
            .containsExactlyInAnyOrderElementsOf(getListFromObjectElseNull(expected, ClassNode.class, classNode -> TypeUtils.namesToTypes(classNode.permittedSubclasses)));
  }

  /**
   * Checks whether the {@link ClassNode#fields} are equal.
   *
   * @param expected an {@link Object} expected to be a {@link ClassNode}; may
   *                 be null.
   */
  protected void hasEqualFields(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_FIELDS)) {
      return;
    }

    assertThatFields(getListFromObjectElseNull(actual, classNode -> classNode.fields))
            .as(createCrumbDescription("Has equal fields"))
            .addOptions(options)
            .containsExactlyInAnyOrderElementsOf(getListFromObjectElseNull(expected, ClassNode.class, classNode -> classNode.fields));
  }

  /**
   * Sets the {@link MethodsComparisonMode} which is used in
   * {@link #hasEqualMethods(Object)}.
   *
   * <p>The default mode is {@link MethodsComparisonMode#ONE_BY_ONE}.
   *
   * @return {@code this} {@link ClassNodeAssert}s; never null.
   */
  public ClassNodeAssert setMethodsComparisonMode(MethodsComparisonMode methodsComparisonMode) {
    this.methodsComparisonMode = methodsComparisonMode;

    return this;
  }

  @Override
  public ClassNodeAssert ignoreLineNumbers() {
    this.ignoreLineNumbers = true;

    return this;
  }

  /**
   * Checks whether the {@link ClassNode#methods} are equal.
   *
   * @param expected an {@link Object} expected to be a {@link ClassNode}; may
   *                 be null.
   */
  protected void hasEqualMethods(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_METHODS)) {
      return;
    }

    if (MethodsComparisonMode.DECLARATION_ONLY.equals(methodsComparisonMode)) {
      hasEqualMethodsUseDeclarationsOnly(expected);
      return;
    }

    MethodNodesAssert methodNodesAssert = assertThatMethods(getListFromObjectElseNull(actual, classNode -> classNode.methods))
            .useLabelIndexLookup(labelIndexLookup())
            .addOptions(options)
            .as(createCrumbDescription("Has equal methods"));

    if (ignoreLineNumbers) {
      methodNodesAssert.ignoreLineNumbers();
    }

    if (ONE_BY_ONE.equals(methodsComparisonMode)) {
      methodNodesAssert.containsExactlyInAnyOrderCompareOneByOneElementsOf(getListFromObjectElseNull(expected, ClassNode.class, classNode -> classNode.methods));
    }
    else if (FULL.equals(methodsComparisonMode)) {
      methodNodesAssert.containsExactlyInAnyOrderElementsOf(getListFromObjectElseNull(expected, ClassNode.class, classNode -> classNode.methods));
    }
    else {
      throw new IllegalStateException("snh: Unknown " + MethodsComparisonMode.class.getSimpleName() + ": " + methodsComparisonMode);
    }
  }

  @Override
  protected String getName(ClassNode classNode) {
    return classNode.name;
  }

  @Override
  protected AccessNode getAccessNode(ClassNode classNode) {
    return AccessNode.forClass(classNode.access);
  }

  @Override
  protected String getSignature(ClassNode classNode) {
    return classNode.signature;
  }

  @Override
  protected List<AnnotationNode> getVisibleAnnotations(ClassNode classNode) {
    return classNode.visibleAnnotations;
  }

  @Override
  protected List<AnnotationNode> getInvisibleAnnotations(ClassNode classNode) {
    return classNode.invisibleAnnotations;
  }

  @Override
  protected List<TypeAnnotationNode> getVisibleTypeAnnotations(ClassNode classNode) {
    return classNode.visibleTypeAnnotations;
  }

  @Override
  protected List<TypeAnnotationNode> getInvisibleTypeAnnotations(ClassNode classNode) {
    return classNode.invisibleTypeAnnotations;
  }

  @Override
  protected List<Attribute> getAttributes(ClassNode classNode) {
    return classNode.attrs;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private void hasEqualMethodsUseDeclarationsOnly(Object expected) {
    AsmRepresentation<MethodNode> methodNodeRepresentation = asmRepresentations.getAsmRepresentation(MethodNode.class);
    Function<ClassNode, List<String>> toSimplifiedMethodRepresentations = classNode -> {
      //noinspection CodeBlock2Expr
      return Optional.ofNullable(classNode.methods)
                     .stream()
                     .flatMap(Collection::stream).map(methodNodeRepresentation::toSimplifiedStringOf)
                     .collect(Collectors.toList());
    };

    Assertions.assertThat(getListFromObjectElseNull(actual, toSimplifiedMethodRepresentations))
              .as(createCrumbDescription("Has equal method declarations"))
              .containsExactlyInAnyOrderElementsOf(getListFromObjectElseNull(expected, ClassNode.class, toSimplifiedMethodRepresentations));
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  public enum MethodsComparisonMode {

    /**
     * Compares only the method declarations (e.g., name and descriptor) as
     * defined by the {@link AsmRepresentation#toSimplifiedStringOf(Object)}
     * for {@link MethodNode}s.
     */
    DECLARATION_ONLY,

    /**
     * Uses {@link AsmIterableAssert#containsExactlyInAnyOrderCompareOneByOneElementsOf(Iterable)}
     * for comparison.
     */
    ONE_BY_ONE,

    /**
     * Uses {@link AsmIterableAssert#containsExactlyInAnyOrderElementsOf(Iterable)}
     * for comparison.
     *
     * <p>The output of this mode may not be very readable, since the entire
     * representation of the methods (including instructions) gets issued.
     */
    FULL
  }
}
